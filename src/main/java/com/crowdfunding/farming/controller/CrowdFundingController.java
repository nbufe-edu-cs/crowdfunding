package com.crowdfunding.farming.controller;

import com.crowdfunding.farming.pojo.CrowdFunding;
import com.crowdfunding.farming.service.CrowdFundingService;
import com.crowdfunding.farming.service.impl.CrowdFundingServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Jiang-gege
 * 2020/6/2417:14
 */
@RestController
@RequestMapping("crowdFunding")
public class CrowdFundingController {

    @Resource
    private CrowdFundingService crowdFundingService;

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody CrowdFunding crowdFunding) {
        String id = crowdFundingService.createCrowdingFunding(crowdFunding);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }
}