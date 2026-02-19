package com.xice.systemMessage.messages;

import com.xice.mclib.XiceMCLib;
import com.xice.mclib.configuration.file.XiceYamlConfiguration;
import com.xice.mclib.entity.XicePlayer;
import com.xice.mclib.event.XicePlayerJoinEvent;
import com.xice.mclib.util.XiceMiniMessageParseUtil;
import com.xice.systemMessage.enums.MessageVisibilityEnum;
import com.xice.systemMessage.util.SettingsUtil;
import java.util.List;

public class LoginMessage {
  private static boolean started = false;

  public static void startUp() {
    if (started) {
      return;
    }
    // 自定义登录消息
    XiceMCLib.getXiceMCLibListener().doWhenUserLogin(xiceEvent -> {
      if (!(xiceEvent instanceof XicePlayerJoinEvent xicePlayerJoinEvent)) {
        return;
      }
      // 读取配置
      XiceYamlConfiguration configuration = SettingsUtil.getConfiguration();
      if (configuration.getBoolean(SettingsUtil.LOGIN_MESSAGE_ENABLE_KEY, SettingsUtil.LOGIN_MESSAGE_ENABLE_VALUE)) {
        // 删除原版自带登录消息
        xicePlayerJoinEvent.setJoinMessage(null);
      } else {
        return;
      }
      // 获取可见性
      MessageVisibilityEnum visibility = MessageVisibilityEnum.getByValue(configuration.getString(SettingsUtil.LOGIN_MESSAGE_VISIBILITY_KEY, SettingsUtil.LOGIN_MESSAGE_VISIBILITY_VALUE.getValue()));
      // 若可见性为 disable，不发送给任何人
      if (visibility == MessageVisibilityEnum.DISABLE) {
        return;
      }
      // 准备消息文本
      String message = XiceMiniMessageParseUtil.parseSourceMessage(configuration.getString(SettingsUtil.LOGIN_MESSAGE_CONTENT_KEY, SettingsUtil.LOGIN_MESSAGE_CONTENT_VALUE), xicePlayerJoinEvent.getPlayer());
      if(message.isEmpty()) {
        return;
      }
      XicePlayer player = xicePlayerJoinEvent.getPlayer();
      // 若可见性为 self，仅自己可见
      player.sendMessage(message);
      if (visibility == MessageVisibilityEnum.SELF) {
        return;
      }
      // 准备发送消息列表
      List<XicePlayer> targetPlayers;
      if (visibility == MessageVisibilityEnum.NEARBY) {
        targetPlayers = player.getNearbyPlayers(configuration.getDouble(SettingsUtil.LOGIN_MESSAGE_RANGE_KEY, SettingsUtil.LOGIN_MESSAGE_RANGE_VALUE));
      } else if (visibility == MessageVisibilityEnum.WORLD) {
        targetPlayers = player.getWorldPlayers();
      // 默认为 global
      } else {
        targetPlayers = player.getOnlinePlayers();
      }
      // 发送消息
      for (XicePlayer target : targetPlayers) {
        target.sendMessage(message);
      }
    });
    started = true;
  }
}