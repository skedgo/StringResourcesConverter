package com.skedgo.tools.localization.model;

/**
 * StringDefinition: string definition name = value pairs
 *
 */
public class StringDefinition {

  private String name;
  private String value;

  public StringDefinition(String name, String value) {
    super();
    this.name = name;
    this.value = value;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getValue() {
    return value;
  }
  public void setValue(String value) {
    this.value = value;
  }



}
