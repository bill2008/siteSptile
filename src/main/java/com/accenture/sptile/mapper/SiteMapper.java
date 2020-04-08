package com.accenture.sptile.mapper;

import com.accenture.sptile.entity.Site;
import com.accenture.sptile.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SiteMapper {
    List<Site> getSiteList();
}
