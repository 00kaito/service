package com.bednarz.usmobile.domain.cycle;

import com.bednarz.usmobile.domain.dto.CycleDataResponse;
import com.bednarz.usmobile.domain.dto.mapper.CycleMapper;
import com.bednarz.usmobile.infrastructure.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CycleService {
    static final int phoneNumberLength = 10;

    private final CycleRepository cycleRepository;
    private final CycleMapper cycleMapper;

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

    private void validateMdn(String mdn) {
        if (mdn == null || mdn.isBlank() || mdn.length() != phoneNumberLength) {
            throw new IllegalArgumentException("MDN must not be null or blank");
        }
    }

    private void validateUserId(String userId) {
        if (userId == null || userId.isBlank()) {
            throw new IllegalArgumentException("UserId must not be null or blank");
        }
    }
}

