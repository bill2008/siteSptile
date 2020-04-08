package com.accenture.sptile.controller;

import com.accenture.sptile.common.CommonResult;
import com.accenture.sptile.entity.Site;
import com.accenture.sptile.entity.User;
import com.accenture.sptile.entity.UserSite;
import com.accenture.sptile.mapper.SiteMapper;
import com.accenture.sptile.mapper.UserMapper;
import com.accenture.sptile.mapper.UserSiteMapper;
import com.accenture.sptile.vo.InsertUserSiteVO;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class LoginController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private SiteMapper siteMapper;

    @Autowired
    private UserSiteMapper userSiteMapper;

    @RequestMapping(value = "/admin/login", method = RequestMethod.POST)
    public CommonResult login(@RequestBody User user) {
        User result = userMapper.checkUser(user);
        if (result != null) {
            return CommonResult.success(result);
        } else {
            return CommonResult.validateFailed();
        }
//        if (user.getUserName().equals("admin") && user.getPassword().equals("123456"))
//            return CommonResult.success("admin");
//        else
//            return CommonResult.validateFailed();
    }

    @GetMapping(value = "/admin/getSiteList")
    public CommonResult getSiteList() {
        List<Site> siteList = siteMapper.getSiteList();
        return CommonResult.success(siteList);
    }

    @GetMapping(value = "/admin/getSelectedSite")
    public CommonResult getSelectedSite(@RequestParam("userId") String userId) {
        List<Site> siteList = userSiteMapper.getSiteByUserId(userId);
        List<Integer> siteIdList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(siteList)) {
            siteIdList = siteList.stream().map(item -> item.getId()).collect(Collectors.toList());
        }
        return CommonResult.success(siteIdList);
    }

    @PostMapping(value = "/admin/insertOrUpdateUserSite")
    public CommonResult insertOrUpdateUserSite(@RequestBody InsertUserSiteVO insertUserSiteVO) {
        List<UserSite> userSites = new ArrayList<>();
        insertUserSiteVO.getSiteIds().forEach(item -> {
            UserSite userSite = new UserSite();
            userSite.setSiteId(item);
            userSite.setUserId(insertUserSiteVO.getUserId());
            userSites.add(userSite);
        });
        userSiteMapper.deleteByUserId(insertUserSiteVO.getUserId());
        userSiteMapper.insertOrUpdateUserSite(userSites);
        return CommonResult.success(null);
    }
}