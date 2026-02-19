package com.xice.systemMessage.messages;

import com.xice.mclib.XiceMCLib;
import com.xice.mclib.api.XiceMCLibListener;
import com.xice.mclib.configuration.file.XiceYamlConfiguration;
import com.xice.mclib.entity.XicePlayer;
import com.xice.mclib.enums.MessageEnum;
import com.xice.mclib.event.XicePlayerJoinEvent;
import com.xice.mclib.exceptions.XicePluginDisabledException;
import com.xice.mclib.util.XiceMiniMessageParseUtil;
import com.xice.systemMessage.enums.MessageVisibilityEnum;
import com.xice.systemMessage.util.SettingsUtil;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class LoginMessage {
  private static boolean started = false;

  public static void startUp() {
    if (started) {
      return;
    }
    // 自定义登录消息
    XiceMCLibListener listener = XiceMCLib.getXiceMCLibListener();
    if (listener == null) {
      throw new XicePluginDisabledException(MessageEnum.MSG_PLUGIN_DISABLED.getContent());
    }
    listener.doWhenUserLogin(xiceEvent -> {
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
      String message = XiceMiniMessageParseUtil.parseMessageSourcePlayer(configuration.getString(SettingsUtil.LOGIN_MESSAGE_CONTENT_KEY, SettingsUtil.LOGIN_MESSAGE_CONTENT_VALUE), xicePlayerJoinEvent.getPlayer());
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
      CompletableFuture<List<XicePlayer>> future;
      if (visibility == MessageVisibilityEnum.NEARBY) {
        double range = configuration.getDouble(SettingsUtil.LOGIN_MESSAGE_RANGE_KEY, SettingsUtil.LOGIN_MESSAGE_RANGE_VALUE);
        future = player.getNearbyPlayersAsync(range);
      } else if (visibility == MessageVisibilityEnum.WORLD) {
        future = player.getWorldPlayersAsync();
      // 默认为 global
      } else {
        future = player.getOnlinePlayersAsync();
      }
      // 发送消息
      future.thenAccept(targetPlayers -> {
        for (XicePlayer target : targetPlayers) {
          target.sendMessage(message);
        }
      });
    });
    started = true;
  }
}