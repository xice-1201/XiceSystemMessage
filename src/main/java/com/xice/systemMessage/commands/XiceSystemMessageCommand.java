package com.xice.systemMessage.commands;

import com.xice.mclib.command.XiceCommand;
import com.xice.mclib.command.XiceCommandSender;
import com.xice.mclib.enums.MessageEnum;
import com.xice.mclib.interfaces.XiceCommandExecutor;
import com.xice.systemMessage.util.SettingsUtil;
import java.util.Arrays;
import java.util.List;

public class XiceSystemMessageCommand implements XiceCommandExecutor {
  private boolean shutingDown;

  public XiceSystemMessageCommand() {
    shutingDown = false;
  }

  @Override
  public boolean onCommand(XiceCommandSender sender, XiceCommand command, List<String> args) {
    if (shutingDown) {
      sender.sendMessage(MessageEnum.MSG_PLUGIN_DISABLED.getContent());
    }
    if (args.isEmpty()) {
      sender.sendMessage("缺失必需字段！");
      return true;
    }
    // xice systemMessage reload
    if (args.getFirst().equals("reload")) {
      if (!sender.hasPermission("xice.systemMessage.reload")) {
        sender.sendMessage("无权限！");
        return true;
      }
      SettingsUtil.reload();
      sender.sendMessage("重载完毕！");
      return true;
    } else {
      sender.sendMessage("未知的指令：" + args.getFirst());
      return true;
    }
  }

  @Override
  public List<String> onTabComplete(XiceCommandSender sender, XiceCommand command, List<String> args) {
    if (shutingDown) {
      sender.sendMessage(MessageEnum.MSG_PLUGIN_DISABLED.getContent());
    }
    List<String> suggestions = null;
    if (args.size() == 1) {
      suggestions = Arrays.asList("reload");
      suggestions.removeIf(s -> !s.startsWith(args.getFirst()));
    }
    return suggestions;
  }

  @Override
  public void shutdown() {
    shutingDown = true;
  }
}