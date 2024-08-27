package com.bednarz.usmobile.domain.billing;

import java.util.List;

public interface CycleRepository {
    List<Cycle> findByMdnAndUserId(String mdn, String userId);

    List<Cycle> findByMdn(String mdn);
}
