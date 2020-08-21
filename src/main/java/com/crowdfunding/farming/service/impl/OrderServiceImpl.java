package com.crowdfunding.farming.service.impl;

import com.crowdfunding.farming.mapper.*;
import com.crowdfunding.farming.pojo.*;
import com.crowdfunding.farming.service.OrderService;
import com.crowdfunding.farming.utils.IdWorker;
import com.crowdfunding.farming.utils.JsonUtils;
import com.crowdfunding.farming.vo.PageResult;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Jiang-gege
 * 2020/6/2312:36
 */
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

  private final IdWorker idWorker;

  private final OrderMapper orderMapper;

  private final OrderDetailMapper detailMapper;

  private final OrderStatusMapper statusMapper;

  private final CrowdFundingMapper crowdFundingMapper;

  public OrderServiceImpl(CrowdFundingMapper crowdFundingMapper,
                          IdWorker idWorker,
                          OrderMapper orderMapper,
                          OrderDetailMapper detailMapper,
                          OrderStatusMapper statusMapper) {
    this.crowdFundingMapper = crowdFundingMapper;
    this.idWorker = idWorker;
    this.orderMapper = orderMapper;
    this.detailMapper = detailMapper;
    this.statusMapper = statusMapper;
  }

  @Override
  @Transactional
  public Long createOrder(Order order, User user) {
    // 生成orderId
    long orderId = idWorker.nextId();

    // 校验用户 todo

    // 初始化数据
    order.setBuyerNick(user.getWeixinNickname());
    order.setBuyerRate(false);
    order.setCreateTime(new Date());
    order.setOrderId(orderId);
    order.setUserId(user.getUserId().longValue());
    // 保存数据
    this.orderMapper.insertSelective(order);

    // 保存订单状态
    OrderStatus orderStatus = new OrderStatus();
    orderStatus.setOrderId(orderId);
    orderStatus.setCreateTime(order.getCreateTime());
    orderStatus.setStatus(1);// 初始状态为未付款

    this.statusMapper.insertSelective(orderStatus);

    // 订单详情中添加orderId
    OrderDetail orderDetail = order.getOrderDetail();
    orderDetail.setOrderId(orderId);
    this.detailMapper.insert(orderDetail);

    log.debug("生成订单，订单编号：{}，用户id：{}", orderId, user.getUserId());

    //添加众筹表
    CrowdFunding record = new CrowdFunding();
    record.setId(order.getCrowdFundingId());
    CrowdFunding crowdFunding = crowdFundingMapper.selectOne(record);

    //判断众筹id  todo

    //判断购买数量
    int total = order.getOrderDetail().getNum();
    int residue = crowdFunding.getTotal() - crowdFunding.getSell();
    String users = crowdFunding.getUsers();

    if (total > residue) {
      throw new RuntimeException("剩余数量不足");
    } else {
      //添加售卖数量
      crowdFunding.setSell(crowdFunding.getSell() + total);
      if (StringUtils.isEmpty(users)) {
        List<Integer> usersList = new ArrayList<>();
        usersList.add(user.getUserId());
        String result = JsonUtils.serialize(usersList);
        crowdFunding.setUsers(result);
      } else {
        List<Integer> result = JsonUtils.parseList(users, Integer.class);
        result.add(user.getUserId());
        String userList = JsonUtils.serialize(result);
        crowdFunding.setUsers(userList);
      }
    }
    if (residue == total) {
      crowdFunding.setStatus(1);
    }
    crowdFundingMapper.updateByPrimaryKeySelective(crowdFunding);
    return orderId;
  }

  @Override
  public Order queryById(Long id) {
    // 查询订单
    Order order = this.orderMapper.selectByPrimaryKey(id);
    // 查询订单详情
    OrderDetail detail = new OrderDetail();
    detail.setOrderId(id);
    OrderDetail result = this.detailMapper.selectOne(detail);
    order.setOrderDetail(result);
    // 查询订单状态
    OrderStatus status = this.statusMapper.selectByPrimaryKey(order.getOrderId());
    order.setStatus(status.getStatus());
    return order;
  }

  @Override
  public PageResult<Order> queryUserOrderList(Integer page, Integer rows, Integer status, Integer userId) {
    try {
      // 分页
      PageHelper.startPage(page, rows);
      // 获取登录用户
      // 创建查询条件
      Page<Order> pageInfo = (Page<Order>) this.orderMapper.queryOrderList(userId, status);

      return new PageResult<>(pageInfo.getTotal(), pageInfo);
    } catch (Exception e) {
      log.error("查询订单出错", e);
      return null;
    }
  }

  @Override
  @Transactional
  public Boolean updateStatus(Long id, Integer status) {
    OrderStatus record = new OrderStatus();
    record.setOrderId(id);
    record.setStatus(status);
    // 根据状态判断要修改的时间
    switch (status) {
      case 2:
        record.setPaymentTime(new Date());// 付款
        break;
      case 3:
        record.setConsignTime(new Date());// 发货
        break;
      case 4:
        record.setEndTime(new Date());// 确认收获，订单结束
        break;
      case 5:
        record.setCloseTime(new Date());// 交易失败，订单关闭
        break;
      case 6:
        record.setCommentTime(new Date());// 评价时间
        break;
      default:
        return null;
    }
    int count = this.statusMapper.updateByPrimaryKeySelective(record);
    return count == 1;
  }

}
