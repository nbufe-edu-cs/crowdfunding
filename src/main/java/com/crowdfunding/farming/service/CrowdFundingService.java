package com.crowdfunding.farming.service;

import com.crowdfunding.farming.pojo.CrowdFunding;

/**
 * @author: Kevin
 * @date: 2020/6/27
 * @time: 上午1:21
 * @description:
 **/

public interface CrowdFundingService {

  /**
   * create
   *
   * @param crowdFunding
   * @return
   */
  String createCrowdingFunding(CrowdFunding crowdFunding);
}
