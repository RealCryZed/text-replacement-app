package app;

import java.io.IOException;
import java.util.logging.*;

class MyLogger {
    private static MyLogger instance;
    private Logger logger = Logger.getLogger("MyLog");

    private MyLogger() throws IOException {
        Handler fileHandler = new FileHandler("src/main/resources/logs/logs.log");
        logger.addHandler(fileHandler);
        SimpleFormatter formatter = new SimpleFormatter();
        fileHandler.setFormatter(formatter);
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

    public void log(Level level, String msg){
        logger.log(level, msg);
    }
}
