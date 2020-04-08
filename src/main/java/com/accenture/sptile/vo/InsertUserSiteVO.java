package com.accenture.sptile.vo;

import java.util.List;

public class InsertUserSiteVO {
    private Integer userId;
    private List<Integer> siteIds;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public List<Integer> getSiteIds() {
        return siteIds;
    }

    public void setSiteIds(List<Integer> siteIds) {
        this.siteIds = siteIds;
    }
}
