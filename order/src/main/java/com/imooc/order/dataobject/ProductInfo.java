package com.imooc.order.dataobject;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name = "product_info")
public class ProductInfo {
  @Id
  private String productId;
  private String productName;
  private BigDecimal productPrice;
  private Integer productStock;
  private String productDescription;
  private String productIcon;
  private Integer productStatus;
  private Integer categoryType;
  private Date createTime;
  private Date updateTime;



}