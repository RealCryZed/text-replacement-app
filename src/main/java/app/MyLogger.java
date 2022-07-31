package app;

import java.io.IOException;
import java.util.logging.*;

/**
 * MyLogger is a custom logger. It has Singleton Pattern.
 */
class MyLogger {
    private static MyLogger instance;
    private Logger logger = Logger.getLogger("MyLog");

    private MyLogger() throws IOException {
        Handler fileHandler = new FileHandler("src/main/resources/logs/logs.log");
        logger.addHandler(fileHandler);
        fileHandler.setFormatter(new MyCustomFormatter());
    }

    public static MyLogger getLogger(){
        if(instance == null){
            try {
                instance = new MyLogger();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    /**
     * Custom log method.
     * @param level logging level
     * @param msg information about this log
     */
    public void log(Level level, String msg){
        logger.log(level, msg);
    }

    /**
     * Custom class for formatting logs. Removes the lines from log file with time and logger information
     */
    private static class MyCustomFormatter extends Formatter {

        @Override
        public String format(LogRecord record) {
            StringBuffer sb = new StringBuffer();
            sb.append(record.getMessage());
            sb.append("\n");
            return sb.toString();
        }

    }
}
