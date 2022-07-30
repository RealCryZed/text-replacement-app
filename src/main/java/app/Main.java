package app;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;

public class Main {

    public static void main(String[] args) {
        MyLogger logger = MyLogger.getLogger();

        Scanner scanner = new Scanner(System.in);
        WorkFile file = new WorkFile();
        file.setPath("");

        while (!file.doesFileExist()) {
            System.out.println("Write down path to file: ");
            file.setPath(scanner.nextLine());

            System.out.println("Write down the text you want to find and change: ");
            file.setReplacedText(scanner.nextLine());

            System.out.println("Write down the text you want: ");
            file.setNewText(scanner.nextLine());
        }

        logger.log(Level.INFO, "Filename: " + file.getFileName() + "\n");

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
