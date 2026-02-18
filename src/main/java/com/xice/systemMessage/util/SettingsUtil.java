package com.xice.systemMessage.util;

import com.xice.mclib.XiceMCLib;
import com.xice.mclib.api.XiceMCLibYAMLLoader;
import com.xice.mclib.configuration.file.XiceYamlConfiguration;

public class SettingsUtil {
  // 配置文件名
  public static final String SETTINGS_FILE_NAME = "systemMessage";
  // 配置 key
  public static final String LOGIN_MESSAGE = "login-message";
  public static final String LOGIN_MESSAGE_RANGE = "login-message-range";
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
      yamlLoader.writeYAMLSettings(SETTINGS_FILE_NAME, getDefaultSettings());
      configuration = yamlLoader.readYAMLSettings(SETTINGS_FILE_NAME);
    }
  }

  public static XiceYamlConfiguration getDefaultSettings() {
    XiceYamlConfiguration ret = new XiceYamlConfiguration();
    ret.set(LOGIN_MESSAGE, getDefaultSettingsString(LOGIN_MESSAGE));
    ret.set(LOGIN_MESSAGE_RANGE, getDefaultSettingsDouble(LOGIN_MESSAGE_RANGE));
    return ret;
  }

  public static double getDefaultSettingsDouble(String key) {
    if (LOGIN_MESSAGE_RANGE.equals(key)) {
      return 10.0;
    }
    return 0.0;
  }

  public static String getDefaultSettingsString(String key) {
    if (LOGIN_MESSAGE.equals(key)) {
      return "<green>欢迎 %player% 加入服务器！";
    }
    return null;
  }

  public static XiceYamlConfiguration getConfiguration() {
    return configuration;
  }
}