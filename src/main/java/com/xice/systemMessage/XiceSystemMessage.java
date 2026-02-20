package com.xice.systemMessage;

import com.xice.mclib.XiceMCLib;
import com.xice.mclib.api.XiceMCLibCommandExecutor;
import com.xice.mclib.configuration.resp.XiceCodeResp;
import com.xice.mclib.enums.MessageEnum;
import com.xice.mclib.exceptions.XicePluginDisabledException;
import com.xice.mclib.plugin.XicePlugin;
import com.xice.systemMessage.commands.XiceSystemMessageCommand;
import com.xice.systemMessage.messages.LoginMessage;
import com.xice.systemMessage.messages.QuitMessage;
import com.xice.systemMessage.util.LogUtil;
import com.xice.systemMessage.util.SettingsUtil;

public class XiceSystemMessage extends XicePlugin {
  // 当插件被启动时
  @Override
  public void onEnable() {
    // 检查 XiceMCLib 是否可用
    if (!isXiceMCLibEnabled()) {
      disableSelf(MessageEnum.MSG_PLUGIN_DISABLED.getContent());
      return;
    }
    // 初始化配置
    SettingsUtil.init();
    // 用户登录消息定义
    if (!XiceCodeResp.isSuccess(LoginMessage.startUp())) {
      throw new XicePluginDisabledException(MessageEnum.MSG_PLUGIN_DISABLED.getContent());
    }
    // 用户退出消息定义
    if (!XiceCodeResp.isSuccess(QuitMessage.startUp())) {
      throw new XicePluginDisabledException(MessageEnum.MSG_PLUGIN_DISABLED.getContent());
    }
    // 指令注册
    XiceMCLibCommandExecutor commandExecutor = XiceMCLib.getXiceMCLibCommandExecutor();
    if (commandExecutor == null) {
      throw new XicePluginDisabledException(MessageEnum.MSG_PLUGIN_DISABLED.getContent());
    }
    commandExecutor.addExecutor(SettingsUtil.SETTINGS_FILE_NAME, new XiceSystemMessageCommand());
    LogUtil.writeInfo("插件启动成功！");
  }

  // 当插件被关闭时
  @Override
  public void onDisable() {
    // 卸载指令
    XiceMCLibCommandExecutor commandExecutor = XiceMCLib.getXiceMCLibCommandExecutor();
    // 若指令管理器为 null，表示 XiceMCLib 已卸载，无需再调用对应的卸载语句
    if (commandExecutor != null) {
      commandExecutor.dropExecutor(SettingsUtil.SETTINGS_FILE_NAME);
    }
    // 关闭所有监听器
    QuitMessage.end();
    LoginMessage.end();
    LogUtil.writeInfo("XiceSystemMessage 卸载成功！");
  }
}