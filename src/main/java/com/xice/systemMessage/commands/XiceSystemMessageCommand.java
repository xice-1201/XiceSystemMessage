package com.xice.systemMessage.commands;

import com.xice.mclib.command.XiceCommand;
import com.xice.mclib.command.XiceCommandSender;
import com.xice.mclib.interfaces.XiceCommandExecutor;
import com.xice.systemMessage.util.SettingsUtil;
import java.util.Arrays;
import java.util.List;

public class XiceSystemMessageCommand implements XiceCommandExecutor {
  @Override
  public boolean onCommand(XiceCommandSender sender, XiceCommand command, List<String> args) {
    if (args.size() < 1) {
      sender.sendMessage("缺失必需字段！");
      return true;
    }
    // xice systemMessage reload
    if (args.get(0).equals("reload")) {
      if (!sender.hasPermission("xice.systemMessage.reload")) {
        sender.sendMessage("无权限！");
        return true;
      }
      SettingsUtil.reload();
      sender.sendMessage("重载完毕！");
      return true;
    } else {
      sender.sendMessage("未知的指令：" + args.get(0));
      return true;
    }
  }

  @Override
  public List<String> onTabComplete(XiceCommandSender sender, XiceCommand command, List<String> args) {
    List<String> suggestions = null;
    if (args.size() == 1) {
      suggestions = Arrays.asList("reload");
      suggestions.removeIf(s -> !s.startsWith(args.get(0)));
    }
    return suggestions;
  }
}