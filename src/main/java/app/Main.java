package app;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;

public class Main {

    public static void main(String[] args) {
        MyLogger logger = MyLogger.getLogger();
        logger.log(Level.INFO, "App started...\n");

        Scanner scanner = new Scanner(System.in);
        WorkFile file = new WorkFile();
        file.setPath("");

        while (!file.doesFileExist()) {
            System.out.println("Write down path to file: ");
            file.setPath(scanner.nextLine());
            logger.log(Level.INFO, "Path to the file: " + file.getPath() + "\n");

            System.out.println("Write down the text you want to find and change: ");
            file.setReplacedText(scanner.nextLine());
            logger.log(Level.INFO, "Text user wants to find and change: " + file.getReplacedText() + "\n");

            System.out.println("Write down the text you want: ");
            file.setNewText(scanner.nextLine());
            logger.log(Level.INFO, "Text user wants to have instead: " + file.getNewText() + "\n");
        }

        BufferedInputStream fileInput = null;
        try {
            fileInput = new BufferedInputStream(new FileInputStream(file.getPath()));

            file.checkPlacementsOfText(fileInput, file.getReplacedText());
            file.logTextAndAdditional5Characters("Text, that will be replaced: ", file.getReplacedText());
            file.logPositions();
            file.replaceWords();

            fileInput.close();
            fileInput = new BufferedInputStream(new FileInputStream(file.getPath()));
            file.clearAllLists();
            file.checkPlacementsOfText(fileInput, file.getNewText());
            file.logTextAndAdditional5Characters("Text after replacement: ", file.getNewText());
        } catch (IOException e) {
            logger.log(Level.INFO, "Couldn't open the file\n");
            e.printStackTrace();
        }
    }
}
