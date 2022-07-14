package com.tlc.sky.pingen.arch.beans;

import com.tlc.gui.modules.common.Model;

public class Product extends Model
{
  protected String name;
  protected int id;
  protected String templateId;
  protected String skuId;
  protected String description;
  protected String value;
  protected String status;

  public String getTemplateId()
  {
    return this.templateId;
  }
  public void setTemplateId(String templateId) {
    this.templateId = templateId;
  }
  public String getSkuId() {
    return this.skuId;
  }
  public void setSkuId(String skuId) {
    this.skuId = skuId;
  }
  public String getValue() {
    return this.value;
  }
  public void setValue(String value) {
    this.value = value;
  }
  public String getName() {
    return this.name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public int getId() {
    return this.id;
  }
  public void setId(Integer id) {
    this.id = id.intValue();
  }
  public String getStatus() {
    return this.status;
  }
  public void setStatus(String status) {
    this.status = status;
  }
  public void setId(int id) {
    this.id = id;
  }
  public String getDescription() {
    return this.description;
  }
  public void setDescription(String description) {
    this.description = description;
  }
}
