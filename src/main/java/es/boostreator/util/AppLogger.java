package es.boostreator.util;

import es.boostreator.BoostreatorApp;

import java.util.logging.Level;
import java.util.logging.Logger;

public class AppLogger {

    private final static Logger LOGGER = Logger.getLogger(BoostreatorApp.class.getName());

    private static AppLogger appLogger = null;
    private static Boolean debug = false;

    private AppLogger(){}

    public static AppLogger getAppLogger() {
        if (appLogger == null) appLogger = new AppLogger();
        return appLogger;
    }

    public static void log(Level level, String log){
        if(debug) LOGGER.log(level, log);
    }

    public static void debugMode(){
        debug = true;
    }

}
