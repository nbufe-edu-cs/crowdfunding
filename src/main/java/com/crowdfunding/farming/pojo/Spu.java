package com.crowdfunding.farming.pojo;

import lombok.Data;

import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * @author Jiang-gege
 * 2020/6/2313:00
 */
@Data
@Table(name = "t_spu")
public class Spu {
    private Long id;

    private String title;

    private Integer saleable;

    private String name;

    private BigDecimal price;

    private String description;

    private String unit;

    private Integer stock;
}