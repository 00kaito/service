package com.bednarz.usmobile.domain.billing;

import java.time.LocalDate;
import java.util.List;

public interface CycleRepository {
    List<Cycle> findByMdnAndUserId(String mdn, String userId);

    List<Cycle> findByMdn(String mdn);

    Cycle findByMdnAndUserIdAndEndDateGreaterThanEqual(String mdn, String userId, LocalDate currentDate);

}
