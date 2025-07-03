package com.ecommerce.microcommerce.web.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "commands")
public class Command {
  @Id
  private String id;
  private String customerName;
  private String productName;
  private int quantity;

  public Command() {
  }

  public Command(String id, String customerName, String productName, int quantity) {
    this.id = id;
    this.customerName = customerName;
    this.productName = productName;
    this.quantity = quantity;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getCustomerName() {
    return customerName;
  }

  public void setCustomerName(String customerName) {
    this.customerName = customerName;
  }

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }
}
