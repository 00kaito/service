package com.bednarz.usmobile.domain.billing;

import com.bednarz.usmobile.application.dto.CreateCycleRequest;
import com.bednarz.usmobile.application.dto.CycleDataResponse;
import com.bednarz.usmobile.application.dto.mapper.CycleMapper;
import com.bednarz.usmobile.infrastructure.exception.ResourceNotFoundException;
import com.bednarz.usmobile.infrastructure.persistence.CycleMongoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CycleDomainService {
    public static final int phoneNumberLength = 10;

    private final CycleMongoRepository cycleRepository;
    private final CycleMapper cycleMapper;
    private final MongoTemplate mongoTemplate;

    public List<CycleDataResponse> getCycleHistoryByMdn(String mdn) {
        validateMdn(mdn);
        List<Cycle> cycles = cycleRepository.findByMdn(mdn);
        if (cycles.isEmpty()) {
            log.warn("No cycles found for MDN: {}", mdn);
            throw new ResourceNotFoundException("No cycles found for MDN: " + mdn);
        }
        return cycles.stream()
                .map(cycleMapper::cycleToCycleDataResponse)
                .collect(Collectors.toList());
    }

    public CycleDataResponse getCurrentCycle(String mdn, String userId) {
        validateMdn(mdn);
        validateUserId(userId);
        Cycle currentCycle = cycleRepository.findByMdnAndUserIdAndEndDateGreaterThanEqual(mdn, userId, LocalDate.now());

        if (currentCycle == null) {
            log.warn("No current cycle found for MDN: {} and UserId: {}", mdn, userId);
            throw new ResourceNotFoundException("No current cycle found for MDN: " + mdn + " and UserId: " + userId);
        }
        return cycleMapper.cycleToCycleDataResponse(currentCycle);
    }

    public CycleDataResponse createCycle(CreateCycleRequest cycleRequest) {
        Cycle cycle = cycleMapper.cycleHistoryResponseToCycle(cycleRequest);
        Cycle savedCycle = cycleRepository.save(cycle);
        return cycleMapper.cycleToCycleDataResponse(savedCycle);
    }

    public void transferMdn(String mdn, String fromUserId, String toUserId) {
        Query query = new Query(Criteria.where("mdn").is(mdn).and("userId").is(fromUserId));
        Cycle oldCycle = mongoTemplate.findOne(query, Cycle.class);

        if (oldCycle != null) {
            oldCycle = oldCycle.withEndDate(LocalDate.now());
            mongoTemplate.save(oldCycle);
        } else {
            throw new ResourceNotFoundException(String.format("No cycles found for mdn %s and userId %s", mdn, fromUserId));
        }

        Cycle newCycle = Cycle.builder()
                .mdn(mdn)
                .userId(toUserId)
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(30))
                .build();

        mongoTemplate.save(newCycle, "cycle");
    }

    private void validateMdn(String mdn) {
        if (mdn == null || mdn.isBlank() || mdn.length() != phoneNumberLength) {
            throw new IllegalArgumentException("MDN must be 10 digits long");
        }
    }

    private void validateUserId(String userId) {
        if (userId == null || userId.isBlank()) {
            throw new IllegalArgumentException("UserId must not be null or blank");
        }
    }
}

