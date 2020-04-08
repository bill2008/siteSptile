package com.accenture.sptile.controller;

import com.accenture.sptile.service.SptileService;
import com.accenture.sptile.util.HttpClientGetPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;

@RestController
public class SptileController {
//    public static final String URL = "http://www.ntwzpt.com/mainPageNoticeList.do?method=list&cur=";

    @Resource
    private SptileService nanjingSptileServiceImpl;

    @GetMapping(value = "test")
    public String getSptileData() throws Exception {
        //httpClientGetPage.sendGet(URL);

        nanjingSptileServiceImpl.process();
        return "";
    }
}
