package com.crowdfunding.farming.pojo;

import lombok.Data;

import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * @author Jiang-gege
 * 2020/6/2313:10
 */
@Data
@Table(name = "t_crowd_funding")
public class CrowdFunding {

    private String id;

    private String goodsName;

    private Long goodsId;

    private String users;

    private Integer total;

    private Integer sell;

    private String unit;

    private BigDecimal price;

    private Integer status;

    private Integer type;
}