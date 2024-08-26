package com.bednarz.usmobile.domain.mdnManagement;

/*

@Service
@RequiredArgsConstructor
//move to application
//
*/
/*
*     @Transactional
    public void transferMdn(String mdn, String fromUserId, String toUserId) {
        // Logika transferu MDN - koordynacja serwisów
        cycleService.transferMdn(mdn, fromUserId, toUserId);
        dailyUsageService.transferMdn(mdn, fromUserId, toUserId);
        //
    }
* *//*

public class MdnService {

    private final CycleRepository cycleRepository;
    private final DailyUsageRepository dailyUsageRepository;

    @Transactional
    public void transferMdn(String mdn, String fromUserId, String toUserId) {
        boolean cycleExists = cycleRepository.existsByMdnAndUserId(mdn, fromUserId);
        if (!cycleExists) {
            throw new ResourceNotFoundException("MDN " + mdn + " not found for user " + fromUserId);
        }

        int updatedCycles = cycleRepository.updateUserIdByMdn(mdn, toUserId);
        if (updatedCycles == 0) {
            throw new ResourceNotFoundException("Failed to update Cycle records for MDN: " + mdn);
        }

        boolean updatedDailyUsages = dailyUsageRepository.updateUserIdByMdn(mdn, toUserId);
        if (updatedDailyUsages) {
            throw new ResourceNotFoundException("Failed to update DailyUsage records for MDN: " + mdn);
        }
    }
}
*/
