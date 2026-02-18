package com.xice.systemMessage;

import com.xice.mclib.XiceMCLib;
import com.xice.systemMessage.commands.XiceSystemMessageCommand;
import com.xice.systemMessage.messages.LoginMessage;
import com.xice.systemMessage.util.LogUtil;
import com.xice.systemMessage.util.SettingsUtil;
import org.bukkit.plugin.java.JavaPlugin;

public class XiceSystemMessage extends JavaPlugin {
  // 当插件被启动时
  @Override
  public void onEnable() {
    // 检查 XiceMCLib 是否可用
    if (getServer().getPluginManager().getPlugin("XiceMCLib") == null) {
      getLogger().severe("依赖库缺失！XiceSystemMessage 启动失败！");
      getServer().getPluginManager().disablePlugin(this);
      return;
    }
    // 初始化配置
    SettingsUtil.init();
    // 用户登录消息定义
    LoginMessage.startUp();
    // 指令注册
    XiceMCLib.getXiceMCLibCommandExecutor().addExecutor(SettingsUtil.SETTINGS_FILE_NAME, new XiceSystemMessageCommand());
    LogUtil.writeLog("插件启动成功！");
  }

  // 当插件被关闭时
  @Override
  public void onDisable() {
    LogUtil.writeLog("XiceSystemMessage 卸载成功！");
  }
}