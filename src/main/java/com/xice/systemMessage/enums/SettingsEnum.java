package com.xice.systemMessage.enums;

import javax.annotation.Nonnull;

public enum SettingsEnum {
  LOGIN_MESSAGE_ENABLE("login-message.enable", false, "是否启用自定义玩家登录消息"),
  LOGIN_MESSAGE_VISIBILITY("login-message.visibility", MessageVisibilityEnum.GLOBAL.getValue(), "自定义登录消息可见性（disable：不可见；self：仅自己；nearby：附近玩家；world：当前世界；global：全服务器）"),
  LOGIN_MESSAGE_CONTENT("login-message.content", "<green>欢迎 %player% 加入服务器！", "自定义登录消息内容（支持 MiniMessage 和占位符）"),
  LOGIN_MESSAGE_RANGE("login-message.range", 10.0, "若 " + SettingsEnum.LOGIN_MESSAGE_VISIBILITY.getKey() + " 为 nearby，该值决定登录者多少格范围内的玩家可以看到登录消息"),
  QUIT_MESSAGE_ENABLE("quit-message.enable", false, "是否启用自定义玩家退出消息"),
  QUIT_MESSAGE_VISIBILITY("quit-message.visibility", MessageVisibilityEnum.GLOBAL.getValue(), "自定义登录消息可见性（disable：不可见；self：仅自己；nearby：附近玩家；world：当前世界；global：全服务器）"),
  QUIT_MESSAGE_CONTENT("quit-message.content", "<red>%player% 离开了服务器！", "自定义登录消息内容（支持 MiniMessage 和占位符）"),
  QUIT_MESSAGE_RANGE("quit-message.range", 10.0, "若 " + SettingsEnum.QUIT_MESSAGE_VISIBILITY.getKey() + " 为 nearby，该值决定退出者多少格范围内的玩家可以看到退出消息");

  private final String key;
  private final Object defaultValue;
  private final String defaultSettingsComments;

  SettingsEnum(String key, Object defaultValue, String defaultSettingsComments) {
    this.key = key;
    this.defaultValue = defaultValue;
    this.defaultSettingsComments = defaultSettingsComments;
  }

  public String getKey() {
    return key;
  }

  public @Nonnull Object getDefaultValue() {
    return defaultValue;
  }

  public boolean getDefaultBoolean() {
    if (defaultValue == null || !(defaultValue instanceof Boolean)) {
      return false;
    }
    return (boolean) defaultValue;
  }

  public double getDefaultDouble() {
    if (defaultValue == null || !(defaultValue instanceof Double)) {
      return 0.0;
    }
    return (double) defaultValue;
  }

  public @Nonnull String getDefaultString() {
    if (defaultValue == null || !(defaultValue instanceof String)) {
      return "";
    }
    return (String) defaultValue;
  }

  public @Nonnull MessageVisibilityEnum getDefaultMessageVisibilityEnum() {
    if (defaultValue == null || !(defaultValue instanceof MessageVisibilityEnum)) {
      return MessageVisibilityEnum.GLOBAL;
    }
    return (MessageVisibilityEnum) defaultValue;
  }

  public @Nonnull String getComments() {
    return defaultSettingsComments;
  }
}