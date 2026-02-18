package com.xice.systemMessage.messages;

import com.xice.mclib.XiceMCLib;
import com.xice.mclib.configuration.file.XiceYamlConfiguration;
import com.xice.mclib.entity.XicePlayer;
import com.xice.mclib.event.XicePlayerJoinEvent;
import com.xice.systemMessage.util.SettingsUtil;
import java.util.List;

public class LoginMessage {
  public static void startUp() {
    // 自定义登录消息
    XiceMCLib.getXiceMCLibListener().doWhenUserLogin(xiceEvent -> {
      XicePlayerJoinEvent xicePlayerJoinEvent = (XicePlayerJoinEvent) xiceEvent;
      // 删除原版自带登录消息
      xicePlayerJoinEvent.setJoinMessage(null);
      // 读取配置
      XiceYamlConfiguration configuration = SettingsUtil.getConfiguration();
      // 获取范围配置
      double range = configuration.getDouble(SettingsUtil.LOGIN_MESSAGE_RANGE, SettingsUtil.getDefaultSettingsDouble(SettingsUtil.LOGIN_MESSAGE_RANGE));
      // 若为负数，不显示任何消息
      if (range < 0.0) {
        return;
      }
      // 准备消息文本
      String message = configuration.getString(SettingsUtil.LOGIN_MESSAGE, SettingsUtil.getDefaultSettingsString(SettingsUtil.LOGIN_MESSAGE)).replace("%player%", xicePlayerJoinEvent.getPlayer().getName());
      XicePlayer player = xicePlayerJoinEvent.getPlayer();
      // 若为0，仅自己可见
      player.sendMessage(message);
      if (range == 0.0) {
        return;
      }
      // 检查范围内所有玩家发送
      List<XicePlayer> nearbyPlayers = player.getNearbyPlayers(range);
      for (XicePlayer target : nearbyPlayers) {
        target.sendMessage(message);
      }
    });
  }
}