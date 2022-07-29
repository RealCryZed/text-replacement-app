package app;

import java.util.logging.Level;

public class Main {

    public static void main(String[] args) {
        MyLogger logger = MyLogger.getLogger();
        logger.log(Level.INFO, "App started...");
    }
}
