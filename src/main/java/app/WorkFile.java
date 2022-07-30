package app;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.*;
import java.util.logging.Level;

@Data
@NoArgsConstructor
@AllArgsConstructor
class WorkFile {

    private String path;
    private String beforeText;
    private String afterText;

    public boolean checkData() {
        MyLogger logger = MyLogger.getLogger();
        File file = new File(path);

        if (!file.exists()) {
            logger.log(Level.INFO, "File wasn't found");
            return false;
        }

        logger.log(Level.INFO, "File with filename: " + file.getName() + " was found!");

        char[] beforeTextArray = beforeText.toCharArray();
        boolean isTextEqual = false;

        try {
            BufferedInputStream fileInput = new BufferedInputStream(new FileInputStream(path));
            int ch = 0;
            int pos = 0;

            while ((ch = fileInput.read()) != -1) {
                if (pos < beforeTextArray.length) {
                    if (ch == beforeTextArray[pos]) {
                        isTextEqual = true;
                        pos++;
                    }
                } else pos = 0;
            }
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Thrown IOException");
        }

        if (!isTextEqual) return false;

        return true;
    }
}
