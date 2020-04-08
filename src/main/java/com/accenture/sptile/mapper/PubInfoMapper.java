package com.accenture.sptile.mapper;

import com.accenture.sptile.entity.PubInfo;
import org.springframework.stereotype.Repository;

@Repository
public interface PubInfoMapper {
    PubInfo selectPubInfo(String name);
    void createPubInfo(PubInfo info);
    void updatePubInfo(PubInfo info);
}
