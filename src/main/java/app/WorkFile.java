package app;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;

@Data
@NoArgsConstructor
@AllArgsConstructor
class WorkFile {

    private String path;
    private String beforeText;
    private String afterText;

    private ArrayList<Integer> arrayOfPositions = new ArrayList<>();
    private MyLogger logger = MyLogger.getLogger();

    public void checkPlacementsOfText(BufferedInputStream fileInput) {
        char[] beforeTextArray = beforeText.toCharArray();

        try {
            int character = 0;
            int beforeTextPosition = 0;
            int commonTextPosition = 0;

            while ((character = fileInput.read()) != -1) {
                commonTextPosition++;
                if (beforeTextPosition < beforeTextArray.length) {
                    if (character == beforeTextArray[beforeTextPosition]) {
                        beforeTextPosition++;
                    } else beforeTextPosition = 0;
                } else {
                    arrayOfPositions.add(commonTextPosition - beforeTextPosition);
                    beforeTextPosition = 0;
                }
            }
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Thrown IOException");
        }
        System.out.println(arrayOfPositions);
    }

    public boolean doesFileExist() {
        File file = new File(path);

        if (!file.exists()) {
            logger.log(Level.INFO, "File wasn't found");
            return false;
        }
        logger.log(Level.INFO, "File with filename: " + file.getName() + " was found!");

        return true;
    }

    public void replaceWords(BufferedInputStream fileInput) {
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
            logger.log(Level.SEVERE, "Error with reading the line from file!");
        }
        ArrayList<String> newText = new ArrayList<>();
        for (String ln : lines){
            newText.add(ln.replaceAll(beforeText, afterText));
        }
        changeTextInFile(newText);
    }

    private void changeTextInFile(ArrayList<String> newText) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(path);
            for (String str : newText) {
                writer.write(str);
                writer.write("\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
