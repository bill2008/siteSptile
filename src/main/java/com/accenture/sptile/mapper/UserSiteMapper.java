package com.accenture.sptile.mapper;

import com.accenture.sptile.entity.Site;
import com.accenture.sptile.entity.User;
import com.accenture.sptile.entity.UserSite;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserSiteMapper {
    List<Site> getSiteByUserId(String userId);
    List<User> getUserBySiteName(String siteName);
    Integer insertOrUpdateUserSite(List<UserSite> userSites);
    Integer deleteByUserId(Integer userId);
}
