package com.xice.systemMessage.util;

import com.xice.mclib.XiceMCLib;
import com.xice.mclib.api.XiceMCLibYAMLLoader;
import com.xice.mclib.configuration.file.XiceYamlConfiguration;
import com.xice.systemMessage.enums.MessageVisibilityEnum;

public class SettingsUtil {
  // 配置文件名
  public static final String SETTINGS_FILE_NAME = "systemMessage";
  // 配置 key
  public static final String LOGIN_MESSAGE_ENABLE_KEY = "login-message.enable";
  public static final String LOGIN_MESSAGE_VISIBILITY_KEY = "login-message.visibility";
  public static final String LOGIN_MESSAGE_CONTENT_KEY = "login-message.content";
  public static final String LOGIN_MESSAGE_RANGE_KEY = "login-message.range";
  // 配置 value
  public static final boolean LOGIN_MESSAGE_ENABLE_VALUE = false;
  public static final MessageVisibilityEnum LOGIN_MESSAGE_VISIBILITY_VALUE = MessageVisibilityEnum.GLOBAL;
  public static final String LOGIN_MESSAGE_CONTENT_VALUE = "<green>欢迎 %player% 加入服务器！";
  public static final double LOGIN_MESSAGE_RANGE_VALUE = 10.0;
  // 配置对象
  private static XiceMCLibYAMLLoader yamlLoader;
  private static XiceYamlConfiguration configuration;

  public synchronized static void init() {
    // 读取配置文件
    yamlLoader = XiceMCLib.getXiceMCLibYAMLLoader();
    reload();
  }

  public synchronized static void reload() {
    // 读取配置文件
    configuration = yamlLoader.readYAMLSettings(SETTINGS_FILE_NAME);
    if (configuration == null) {
      yamlLoader.writeYAMLSettings(SETTINGS_FILE_NAME, getDefaultSettings(true));
      configuration = yamlLoader.readYAMLSettings(SETTINGS_FILE_NAME);
    }
  }

  public static XiceYamlConfiguration getDefaultSettings(boolean withComments) {
    XiceYamlConfiguration ret = new XiceYamlConfiguration();
    ret.set(LOGIN_MESSAGE_ENABLE_KEY, LOGIN_MESSAGE_ENABLE_VALUE);
    ret.set(LOGIN_MESSAGE_VISIBILITY_KEY, LOGIN_MESSAGE_VISIBILITY_VALUE.getValue());
    ret.set(LOGIN_MESSAGE_CONTENT_KEY, LOGIN_MESSAGE_CONTENT_VALUE);
    ret.set(LOGIN_MESSAGE_RANGE_KEY, LOGIN_MESSAGE_RANGE_VALUE);
    if (withComments) {
      ret.setComments(LOGIN_MESSAGE_ENABLE_KEY, getDefaultSettingsComments(LOGIN_MESSAGE_ENABLE_KEY));
      ret.setComments(LOGIN_MESSAGE_VISIBILITY_KEY, getDefaultSettingsComments(LOGIN_MESSAGE_VISIBILITY_KEY));
      ret.setComments(LOGIN_MESSAGE_CONTENT_KEY, getDefaultSettingsComments(LOGIN_MESSAGE_CONTENT_KEY));
      ret.setComments(LOGIN_MESSAGE_RANGE_KEY, getDefaultSettingsComments(LOGIN_MESSAGE_RANGE_KEY));
    }
    return ret;
  }

  public static String getDefaultSettingsComments(String key) {
    if (LOGIN_MESSAGE_ENABLE_KEY.equals(key)) {
      return "是否启用自定义玩家登录消息";
    } else if (LOGIN_MESSAGE_VISIBILITY_KEY.equals(key)) {
      return "自定义登录消息可见性（disable：不可见；self：仅自己；nearby：附近玩家；world：当前世界；global：全服务器）";
    } else if (LOGIN_MESSAGE_CONTENT_KEY.equals(key)) {
      return "自定义登录消息内容（支持 MiniMessage 和占位符）";
    } else if (LOGIN_MESSAGE_RANGE_KEY.equals(key)) {
      return "若 " + LOGIN_MESSAGE_VISIBILITY_KEY + " 为 nearby，该值决定登录者多少格范围内的玩家可以看到登录消息";
    }
    return null;
  }

  public synchronized static XiceYamlConfiguration getConfiguration() {
    if (configuration == null) {
      init();
    }
    return configuration;
  }
}