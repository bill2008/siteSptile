package com.accenture.sptile.service.impl;

import org.springframework.stereotype.Component;

@Component(value = "nanjingSptileServiceImpl")
public class NanjingSptileServiceImpl extends AbstractSptileService {
    private String url = "http://www.ntwzpt.com/mainPageNoticeList.do?method=list&cur=1";
    private String name = "南昌";

    @Override
    public String getName() {
        return name;
    }

    @Override
    protected String getUrl() {
        return url;
    }
}
