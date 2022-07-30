package app;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;

@Data
@NoArgsConstructor
class WorkFile {

    private String path;
    private String replacedText;
    private String newText;

    private ArrayList<Integer> positions = new ArrayList<>();
    private ArrayList<Integer> characters = new ArrayList<>();
    private ArrayList<String> textList = new ArrayList<>();

    private MyLogger logger = MyLogger.getLogger();

    public boolean doesFileExist() {
        File file = new File(path);

        if (!file.exists()) {
            return false;
        }

        return true;
    }

    public String getFileName() {
        return new File(path).getName();
    }

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

    public void logTextAndAdditional5Characters(String call, String text) {
        int startPosition = 0;
        int endPosition = 0;

        for (int i = 0; i < positions.size(); i++) {
            startPosition = positions.get(i) - 1 - 5;
            endPosition = positions.get(i) + text.length() - 1 + 5;
            String str = "";

            if (startPosition < 5) startPosition = 0;
            if (endPosition > characters.size()) endPosition = characters.size();

            for (int j = startPosition; j < endPosition; j++) {
                int character = characters.get(j);
                str = str + (char)character;
            }
            textList.add(str);
        }
        logger.log(Level.INFO, call + textList + "\n");
    }

    public void logPositions() {
        logger.log(Level.INFO, "Positions of the text that was changed: " + positions + " \n");
    }

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

    private void changeTextInFile(ArrayList<String> newTextList) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(path);
            for (int i = 0; i < newTextList.size(); i++) {
                writer.write(newTextList.get(i));
                if ((newTextList.size() - 1 == i && newTextList.get(i).equals(""))) {
                    writer.write("\n");
                }
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clearAllLists() {
        textList.clear();
        characters.clear();
        positions.clear();
    }
}