package app;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Level;

public class Main {

    public static void main(String[] args) {
        MyLogger logger = MyLogger.getLogger();
        logger.log(Level.INFO, "App started...");

        Scanner scanner = new Scanner(System.in);
        WorkFile file = new WorkFile();
        file.setPath("");

        while (!file.doesFileExist()) {
            System.out.println("Write down path to file: ");
            file.setPath(scanner.nextLine());
            logger.log(Level.INFO, "Path to the file: " + file.getPath());

            System.out.println("Write down the text you want to find and change: ");
            file.setBeforeText(scanner.nextLine());
            logger.log(Level.INFO, "Text user wants to find and change: " + file.getBeforeText());

            System.out.println("Write down the text you want: ");
            file.setAfterText(scanner.nextLine());
            logger.log(Level.INFO, "Text user wants to have instead: " + file.getAfterText());
        }

        BufferedInputStream fileInput = null;
        try {
            fileInput = new BufferedInputStream(new FileInputStream(file.getPath()));
            file.checkPlacementsOfText(fileInput);
            file.writeTextAndAdditional5Characters();
            file.replaceWords(fileInput);
        } catch (FileNotFoundException e) {
            logger.log(Level.INFO, "Couldn't open the file");
            e.printStackTrace();
        }
    }
}
