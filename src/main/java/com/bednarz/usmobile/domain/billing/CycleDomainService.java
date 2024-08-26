package com.bednarz.usmobile.domain.billing;

import com.bednarz.usmobile.domain.dto.CycleDataResponse;
import com.bednarz.usmobile.domain.dto.mapper.CycleMapper;
import com.bednarz.usmobile.infrastructure.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CycleDomainService {
    public static final int phoneNumberLength = 10;

    private final CycleRepository cycleRepository;
    private final CycleMapper cycleMapper;
    private final MongoTemplate mongoTemplate;

    public List<CycleDataResponse> getCycleHistoryByMdn(String mdn) {
        validateMdn(mdn);
        List<Cycle> cycles = cycleRepository.findByMdn(mdn);
        if (cycles.isEmpty()) {
            throw new ResourceNotFoundException("No cycles found for MDN: " + mdn);
        }
        return cycles.stream()
                .map(cycleMapper::cycleToCycleHistoryResponse)
                .collect(Collectors.toList());
    }

    public List<CycleDataResponse> getCurrentCycleUsage(String mdn, String userId) {
        validateMdn(mdn);
        validateUserId(userId);
        List<Cycle> cycles = cycleRepository.findByMdnAndUserId(mdn, userId);
        if (cycles.isEmpty()) {
            throw new ResourceNotFoundException("No current cycle usage found for MDN: " + mdn + " and UserId: " + userId);
        }
        return cycles.stream()
                .map(cycleMapper::cycleToCycleHistoryResponse)
                .collect(Collectors.toList());
    }

    public CycleDataResponse createCycle(CycleDataResponse cycleRequest) {
        Cycle cycle = cycleMapper.cycleHistoryResponseToCycle(cycleRequest);
        Cycle savedCycle = cycleRepository.save(cycle);
        return cycleMapper.cycleToCycleHistoryResponse(savedCycle);
    }

    public void transferMdn(String mdn, String fromUserId, String toUserId) {
        Query query = new Query(Criteria.where("mdn").is(mdn).and("userId").is(fromUserId));
        Cycle oldCycle = mongoTemplate.findOne(query, Cycle.class);

        if (oldCycle != null) {
            oldCycle = oldCycle.withEndDate(new Date()).withActive(false);
            mongoTemplate.save(oldCycle);
        } else {
            throw new ResourceNotFoundException(String.format("No cycles found for mdn %s and userId %s", mdn, fromUserId));
        }

        Date now = new Date();
        Cycle newCycle = Cycle.builder()
                .mdn(mdn)
                .userId(toUserId)
                .startDate(now)
                .endDate(null)
                .active(true)
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

