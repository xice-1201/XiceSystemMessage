package com.xice.systemMessage.messages;

import com.xice.mclib.XiceMCLib;
import com.xice.mclib.api.XiceMCLibListener;
import com.xice.mclib.configuration.file.XiceYamlConfiguration;
import com.xice.mclib.configuration.resp.XiceCodeResp;
import com.xice.mclib.entity.XicePlayer;
import com.xice.mclib.event.XicePlayerQuitEvent;
import com.xice.mclib.util.XiceMiniMessageParseUtil;
import com.xice.systemMessage.enums.MessageVisibilityEnum;
import com.xice.systemMessage.enums.SettingsEnum;
import com.xice.systemMessage.util.SettingsUtil;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class QuitMessage {
  private static boolean started = false;

  public static int startUp() {
    if (started) {
      return XiceCodeResp.SUCCESS_WITHOUT_DOING_ANYTHING;
    }
    // 自定义退出消息
    XiceMCLibListener listener = XiceMCLib.getXiceMCLibListener();
    if (listener == null) {
      return XiceCodeResp.PLUGIN_DISABLED;
    }
    listener.doWhenUserQuit(SettingsUtil.SETTINGS_FILE_NAME, xiceEvent -> {
      if (!(xiceEvent instanceof XicePlayerQuitEvent xicePlayerQuitEvent)) {
        return;
      }
      // 读取配置
      XiceYamlConfiguration configuration = SettingsUtil.getConfiguration();
      if (configuration.getBoolean(SettingsEnum.QUIT_MESSAGE_ENABLE.getKey(), SettingsEnum.QUIT_MESSAGE_ENABLE.getDefaultBoolean())) {
        // 删除原版自带退出消息
        xicePlayerQuitEvent.setQuitMessage(null);
      } else {
        return;
      }
      // 获取可见性
      MessageVisibilityEnum visibility = MessageVisibilityEnum.getByValue(
          configuration.getString(SettingsEnum.QUIT_MESSAGE_VISIBILITY.getKey(), SettingsEnum.QUIT_MESSAGE_VISIBILITY.getDefaultMessageVisibilityEnum().getValue()));
      // 若可见性为 disable，不发送给任何人
      if (visibility == MessageVisibilityEnum.DISABLE) {
        return;
      }
      // 准备消息文本
      String message = XiceMiniMessageParseUtil.parseMessageSourcePlayer(configuration.getString(SettingsEnum.QUIT_MESSAGE_CONTENT.getKey(), SettingsEnum.QUIT_MESSAGE_CONTENT.getDefaultString()),
          xicePlayerQuitEvent.getPlayer());
      if (message.isEmpty()) {
        return;
      }
      XicePlayer player = xicePlayerQuitEvent.getPlayer();
      // 若可见性为 self，仅自己可见
      player.sendMessage(message);
      if (visibility == MessageVisibilityEnum.SELF) {
        return;
      }
      // 准备发送消息列表
      CompletableFuture<List<XicePlayer>> future;
      if (visibility == MessageVisibilityEnum.NEARBY) {
        double range = configuration.getDouble(SettingsEnum.QUIT_MESSAGE_RANGE.getKey(), SettingsEnum.QUIT_MESSAGE_RANGE.getDefaultDouble());
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
    return XiceCodeResp.SUCCESS;
  }

  public static void end() {
    started = false;
    XiceMCLibListener listener = XiceMCLib.getXiceMCLibListener();
    if (listener == null) {
      return;
    }
    listener.undoWhenUserQuit(SettingsUtil.SETTINGS_FILE_NAME);
  }
}