package com.xice.systemMessage.util;

import com.xice.mclib.XiceMCLib;
import com.xice.mclib.api.XiceMCLibLogger;

public class LogUtil {
  private static final XiceMCLibLogger logger = XiceMCLib.getXiceMCLibLogger();

  public static void writeLog(String message) {
    logger.writeLog("XiceSystemMessage", message);
  }
}