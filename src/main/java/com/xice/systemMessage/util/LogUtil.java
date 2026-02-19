package com.xice.systemMessage.util;

import com.xice.mclib.XiceMCLib;
import com.xice.mclib.api.XiceMCLibLogger;
import com.xice.mclib.enums.MessageEnum;
import com.xice.mclib.exceptions.XicePluginDisabledException;

public class LogUtil {
  private static XiceMCLibLogger logger;

  static {
    logger = XiceMCLib.getXiceMCLibLogger();
    if (logger == null) {
      throw new XicePluginDisabledException(MessageEnum.MSG_PLUGIN_DISABLED.getContent());
    }
  }

  public static void writeInfo(String message) {
    logger.writeInfo("XiceSystemMessage", message);
  }
}