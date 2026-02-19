package com.xice.systemMessage.enums;

public enum MessageVisibilityEnum {
  DISABLE("disable"),
  SELF("self"),
  NEARBY("nearby"),
  WORLD("world"),
  GLOBAL("global");

  private final String value;

  MessageVisibilityEnum(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  public static MessageVisibilityEnum getByValue(String value) {
    if (value == null) {
      return GLOBAL;
    }
    for (MessageVisibilityEnum messageVisibilityEnum : MessageVisibilityEnum.values()) {
      if (messageVisibilityEnum.value.equals(value.trim().toLowerCase())) {
        return messageVisibilityEnum;
      }
    }
    return GLOBAL;
  }
}