package com.accenture.sptile.service.impl;

import com.accenture.sptile.config.MailProperties;
import com.accenture.sptile.entity.PubInfo;
import com.accenture.sptile.entity.User;
import com.accenture.sptile.mapper.PubInfoMapper;
import com.accenture.sptile.mapper.UserSiteMapper;
import com.accenture.sptile.service.SptileService;
import com.accenture.sptile.util.HttpClientDownPage;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import com.gargoylesoftware.htmlunit.util.NameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

@Component
public abstract class AbstractSptileService implements SptileService {

    @Autowired
    protected PubInfoMapper pubInfoMapper;

    @Autowired
    protected MailProperties mailProperties;

    @Autowired
    protected MailSender mailSender;

    @Autowired
    protected UserSiteMapper userSiteMapper;

    protected abstract String getUrl();

    protected abstract String getName();

    private String getToList() {
        List<User> userList = userSiteMapper.getUserBySiteName(getName());
        StringBuilder sb = new StringBuilder();
        if (!CollectionUtils.isEmpty(userList)) {
            for (User user: userList) {
                sb.append(user.getEmail()).append(";");
            }
        }
        return sb.toString();
    }

    protected HtmlTable getDataTable(HtmlPage htmlPage) {
        List<HtmlTable> divs = htmlPage.getByXPath("//table[@class='listInfoTable']");
        return divs.get(0);
    }

    protected HtmlTableRow getFirstRow(HtmlTable htmlTable) {
        return htmlTable.getRow(1);
    }

    protected String getTitle(HtmlTableRow row) {
        return row.getCell(0).getTextContent();
    }

    protected String getId(HtmlTableRow row) {
        return row.getCell(1).getTextContent();
    }

    protected String getPubTime(HtmlTableRow row) {
        return row.getCell(4).getTextContent();
    }

    @Override
    public boolean process() throws Exception {
        String url = getUrl();
        String name = getName();

        //1.生成httpclient，相当于该打开一个浏览器
        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        List<HtmlDivision> htmlDivisions;

        //final HtmlPage htmlPage = webClient.getPage("http://www.ntwzpt.com/mainPageNoticeList.do?method=init&id=1000001&cur=1&keyword=变压器&inforCode=&time0=&time1=");
        HtmlPage htmlPage = webClient.getPage(url);
//        HtmlInput input = (HtmlInput)htmlPage.getElementById("searchKeyValue");
//        input.setValueAttribute("变压器");
//        List<HtmlDivision> htmlDivisions1 = htmlPage.getByXPath("//div[@class='searchBtn2']");
//
//        htmlPage = htmlDivisions1.get(0).click();
//        for (NameValuePair nameValuePair:htmlPage.getWebResponse().getResponseHeaders()) {
//            System.out.println("name:" + nameValuePair.getName() + "    value:" + nameValuePair.getValue());
//        }
        HtmlTable table = getDataTable(htmlPage);
        HtmlTableRow htmlTableRow = getFirstRow(table);

        String title = getTitle(htmlTableRow);
        String pubId = getId(htmlTableRow);
        String pubTime = getPubTime(htmlTableRow);
        System.out.println("Tilte:" + title);
        System.out.println("ID:" + pubId);
        System.out.println("Time:" + pubTime);

        PubInfo pubInfo = pubInfoMapper.selectPubInfo(name);

        if (pubInfo == null) {
            pubInfo = new PubInfo();
            pubInfo.setName(name);
            pubInfo.setTitle(title);
            pubInfo.setPubId(pubId);
            pubInfo.setPubTime(pubTime);
            pubInfoMapper.createPubInfo(pubInfo);
            sendMail();
            return true;
        } else {
            if (pubId.equals(pubInfo.getPubId())) {
                return false;
            } else {
                pubInfo = new PubInfo();
                pubInfo.setName(name);
                pubInfo.setTitle(title);
                pubInfo.setPubId(pubId);
                pubInfo.setPubTime(pubTime);
                pubInfoMapper.updatePubInfo(pubInfo);
                sendMail();
                return true;
            }
        }
    }

    protected void sendMail() {
        String to = getToList();
        if (!StringUtils.isEmpty(to)) {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(mailProperties.getFrom());
            mailMessage.setTo(to);

            mailMessage.setSubject("网站更新提醒");
            mailMessage.setText(getName() + "网站有更新，请去查看！");

            mailSender.send(mailMessage);
        }
    }
}
