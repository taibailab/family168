package com.family168;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 订单
 *
 * @author cac，calvin
 */
public class Order {
    public static final String STATUS_ORDERED = "1";
    public static final String STATUS_SHIPPED = "2";
    public static Map statusEnum = new HashMap();
    private Integer id;
    private List orderItems;
    private Date orderDate;

    /**
     * 送货地区
     */
    private String region;
    private String shipAddr;

    /**
     * 实际售价
     */
    private Double totalPrice;

    /**
     * 折扣前的原价
     */
    private Double originalPrice;
    private Double discountPrice;

    /**
     * 计算折扣时使用的促销规则id.格式为以,号分隔的字符串
     */
    private String applyRules = "";
    private Date shipDate;
    private String status;

    {
        //now store the message directly.if use i18n,the map store  i18n key.
        statusEnum.put(STATUS_ORDERED, "未发货");
        statusEnum.put(STATUS_SHIPPED, "已发货");
    }

    public Order() {
    }

    public Order(Integer id) {
        this.setId(id);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getOrderDate() {
        return this.orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getShipAddr() {
        return this.shipAddr;
    }

    public void setShipAddr(String shipAddr) {
        this.shipAddr = shipAddr;
    }

    public Double getTotalPrice() {
        return this.totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Double getOriginalPrice() {
        return this.originalPrice;
    }

    public void setOriginalPrice(Double price) {
        this.originalPrice = price;
    }

    public Double getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(Double discountPrice) {
        this.discountPrice = discountPrice;
    }

    public String getApplyRules() {
        return this.applyRules;
    }

    public void setApplyRules(String rules) {
        this.applyRules = rules;
    }

    public void addApplyRule(String rule) {
        applyRules += (rule + ";");
    }

    public Date getShipDate() {
        return this.shipDate;
    }

    public void setShipDate(Date shipDate) {
        this.shipDate = shipDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List getOrderItems() {
        return this.orderItems;
    }

    public void setOrderItems(List orderItems) {
        this.orderItems = orderItems;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}
