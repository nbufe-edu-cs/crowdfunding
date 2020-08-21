package com.crowdfunding.farming.service.impl;

import com.crowdfunding.farming.mapper.CrowdFundingMapper;
import com.crowdfunding.farming.pojo.CrowdFunding;
import com.crowdfunding.farming.service.CrowdFundingService;
import com.crowdfunding.farming.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Jiang-gege
 * 2020/6/2417:08
 */
@Service
public class CrowdFundingServiceImpl implements CrowdFundingService {

  private final IdWorker idWorker;

  private final CrowdFundingMapper crowdFundingMapper;

  public CrowdFundingServiceImpl(IdWorker idWorker,
                                 CrowdFundingMapper crowdFundingMapper) {
    this.idWorker = idWorker;
    this.crowdFundingMapper = crowdFundingMapper;
  }

  @Override
  public String createCrowdingFunding(CrowdFunding crowdFunding) {
    String crowdFundingId = String.valueOf(idWorker.nextId());
    crowdFunding.setId(crowdFundingId);
    crowdFunding.setSell(0);
    crowdFunding.setStatus(0);
    crowdFundingMapper.insertSelective(crowdFunding);
    return crowdFundingId;
  }
}