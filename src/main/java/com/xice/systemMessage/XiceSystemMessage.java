package com.xice.systemMessage;

import com.xice.mclib.XiceMCLib;
import com.xice.mclib.api.XiceMCLibCommandExecutor;
import com.xice.mclib.enums.MessageEnum;
import com.xice.mclib.exceptions.XicePluginDisabledException;
import com.xice.mclib.plugin.XicePlugin;
import com.xice.systemMessage.commands.XiceSystemMessageCommand;
import com.xice.systemMessage.messages.LoginMessage;
import com.xice.systemMessage.util.LogUtil;
import com.xice.systemMessage.util.SettingsUtil;

public class XiceSystemMessage extends XicePlugin {
  // 当插件被启动时
  @Override
  public void onEnable() {
    // 检查 XiceMCLib 是否可用
    if (!isXiceMCLibEnabled()) {
      disableSelf("依赖库缺失！XiceSystemMessage 启动失败！");
      return;
    }
    // 初始化配置
    SettingsUtil.init();
    // 用户登录消息定义
    LoginMessage.startUp();
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
    LogUtil.writeInfo("XiceSystemMessage 卸载成功！");
  }
}