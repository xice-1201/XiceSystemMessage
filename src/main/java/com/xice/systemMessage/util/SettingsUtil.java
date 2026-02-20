package com.xice.systemMessage.util;

import com.xice.mclib.XiceMCLib;
import com.xice.mclib.api.XiceMCLibYAMLLoader;
import com.xice.mclib.configuration.file.XiceYamlConfiguration;
import com.xice.systemMessage.enums.SettingsEnum;

public class SettingsUtil {
  // 配置文件名
  public static final String SETTINGS_FILE_NAME = "systemMessage";
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
    for (SettingsEnum settingsEnum : SettingsEnum.values()) {
      ret.set(settingsEnum.getKey(), settingsEnum.getDefaultValue());
      if (withComments) {
        ret.setComments(settingsEnum.getKey(), settingsEnum.getComments());
      }
    }
    return ret;
  }

  public synchronized static XiceYamlConfiguration getConfiguration() {
    if (configuration == null) {
      init();
    }
    return configuration;
  }
}