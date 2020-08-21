package com.crowdfunding.farming.service;

import com.crowdfunding.farming.pojo.Order;
import com.crowdfunding.farming.pojo.User;
import com.crowdfunding.farming.vo.PageResult;

/**
 * @author: Kevin
 * @date: 2020/6/27
 * @time: 上午1:21
 * @description:
 **/

public interface OrderService {

  /**
   *
   * @param order
   * @param user
   * @return
   */
  Long createOrder(Order order, User user);

  /**
   *
   * @param id
   * @return
   */
  Order queryById(Long id);

  /**
   *
   * @param page
   * @param rows
   * @param status
   * @param userId
   * @return
   */
  PageResult<Order> queryUserOrderList(Integer page,
                                       Integer rows,
                                       Integer status,
                                       Integer userId);

  /**
   *
   * @param id
   * @param status
   * @return
   */
  Boolean updateStatus(Long id, Integer status);
}
