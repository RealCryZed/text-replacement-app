package app;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;

/**
 * WorkFile is a class, that we use to do all the work with the file.
 */

@Data
@NoArgsConstructor
class WorkFile {

    /**
     * Path to the file
     */
    private String path;
    /**
     * Text that user wants to find and then replace
     */
    private String replacedText;
    /**
     * Text that should be in file instead of replacedText
     */
    private String newText;

    /**
     * List of positions where we find replacedText
     */
    private ArrayList<Integer> positions = new ArrayList<>();
    /**
     * List with all characters that is in file
     */
    private ArrayList<Integer> characters = new ArrayList<>();
    /**
     * List with found text and 5 characters before and after it
     */
    private ArrayList<String> textList = new ArrayList<>();

    private MyLogger logger = MyLogger.getLogger();

    /**
     * Returns true if file exists, false if file doesn't exist.
     * This method is needed to check whether the user has entered the correct path.
     * @return true\false depending on file existence
     */
    public boolean fileExists() {
        File file = new File(path);

        if (!file.exists()) {
            return false;
        }

        return true;
    }

    /**
     * Returns true if path has file, false if it doesn't.
     * This method is needed to check whether the user has entered the path directly to the file.
     * @return true\false depending on whether path has file or not
     */
    public boolean pathIsFile() {
        return new File(path).isFile();
    }

    /**
     * Simple method to get filename by path.
     * @return filename
     */
    public String getFileName() {
        return new File(path).getName();
    }

    /**
     * Method takes opened file and text, then reads every byte of the text inside file.
     * Collects all the characters in the file into the 'characters' array.
     * Collects all the initial positions, in which the given text is present, in the array "positions".
     * @param fileInput opened file instance
     * @param text replacedText or newText depending on when the method is run
     */
    public void checkPlacementsOfText(BufferedInputStream fileInput, String text) {
        char[] beforeTextArray = text.toCharArray();

        try {
            int character = 0;
            int beforeTextPosition = 0;
            int commonTextPosition = 0;

            while ((character = fileInput.read()) != -1) {
                commonTextPosition++;
                characters.add(character);
                if (beforeTextPosition < beforeTextArray.length) {
                    if (character == beforeTextArray[beforeTextPosition]) {
                        beforeTextPosition++;
                    } else beforeTextPosition = 0;
                } else {
                    positions.add(commonTextPosition - beforeTextPosition);
                    beforeTextPosition = 0;
                }
            }
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Thrown IOException in checkPlacementOfText() function\n");
        }
    }

    /**
     * Method takes text (replacedText or newText), finds it in the file and then takes 5 characters
     * from the beginning and from the end and put this String into textList.
     * Logs what've been put in textList.
     * @param call string given for logging purposes
     * @param text replacedText or newText depending on when the method is run
     */
    public void logTextAndAdditional5Characters(String call, String text) {
        int startPosition = 0;
        int endPosition = 0;

        for (int i = 0; i < positions.size(); i++) {
            startPosition = positions.get(i) - 1 - 5;
            endPosition = positions.get(i) + text.length() - 1 + 5;
            String str = "";

            if (startPosition < 5) startPosition = 0;
            if (endPosition > characters.size()) endPosition = characters.size() - 1;

            for (int j = startPosition; j < endPosition; j++) {
                int character = characters.get(j);
                if (character != 10) str = str + (char)character;
            }
            textList.add(str);
        }
        logger.log(Level.INFO, call);
        for (int i = 0; i < textList.size(); i++) {
            logger.log(Level.INFO, i+1 + ") '" + textList.get(i) + "'");
        }
    }

    /**
     * Logs all positions in log file.
     */
    public void logPositions() {
        logger.log(Level.INFO, "\nPositions of the text that was changed: " + positions + " \n");
    }

    /**
     * Takes the old text from the file and writes it to the list. A new list is created, in which
     * all the lines with the replaced text are placed.
     * Calls method changeTextInFile()
     */
    public void replaceWords() {
        ArrayList<String> lines = new ArrayList<>();

        try {
            FileReader fileReader = new FileReader(path);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }
            bufferedReader.close();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error with reading the line from file!\n");
        }
        ArrayList<String> newTextList = new ArrayList<>();
        for (String ln : lines){
            newTextList.add(ln.replaceAll(replacedText, newText));
        }
        changeTextInFile(newTextList);
    }

    /**
     * Takes list with new text, creates new file. New text is placed in this file line by line.
     * @param newTextList list of strings with changed text
     */
    private void changeTextInFile(ArrayList<String> newTextList) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(path);
            for (int i = 0; i < newTextList.size(); i++) {
                writer.write(newTextList.get(i));
                writer.write("\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Clear all the lists of class for later use.
     */
    public void clearAllLists() {
        textList.clear();
        characters.clear();
        positions.clear();
    }
}