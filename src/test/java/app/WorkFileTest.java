package app;

import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class WorkFileTest {

    private WorkFile workFile = new WorkFile();
    BufferedInputStream fileInput = null;

    @Before
    public void initialize() {
        workFile.setPath("src/main/resources/test/data.txt");
        workFile.setReplacedText("oldText");
        workFile.setNewText("newText");
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(workFile.getPath(), StandardCharsets.UTF_8);
            writer.print("In this first line there is oldText!\n");
            writer.print("oldText in the second line!\n");
            writer.print("In 3 line oldText, and this is okay!\n");
            writer.close();
            fileInput = new BufferedInputStream(new FileInputStream(workFile.getPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void doesFileExistTest() {
        File file = new File(workFile.getPath());
        assertTrue(file.exists());
    }

    @Test
    public void checkPlacementsOfTextTest() {
        ArrayList<Integer> positions = new ArrayList<>(Arrays.asList(29, 38, 76)); //positions are manually calculated
        workFile.checkPlacementsOfText(fileInput, workFile.getReplacedText());

        assertEquals(positions, workFile.getPositions());
    }

    @Test
    public void logTextAndAdditional5CharactersTest() {
        workFile.checkPlacementsOfText(fileInput, workFile.getReplacedText());
        workFile.logTextAndAdditional5Characters("Text, that will be replaced: ", workFile.getReplacedText());
        ArrayList<String> textList = new ArrayList<>();
        textList.add("e is oldText!old");
        textList.add("ext!oldText in t");
        textList.add("line oldText, and");

        assertEquals(textList, workFile.getTextList());
    }

    @Test
    public void replaceWordsTest() {
        workFile.replaceWords();

        ArrayList<String> actualList = new ArrayList<>();
        ArrayList<String> expectedList = new ArrayList<>();
        expectedList.add("In this first line there is newText!");
        expectedList.add("newText in the second line!");
        expectedList.add("In 3 line newText, and this is okay!");

        try {
            BufferedReader br = new BufferedReader(new FileReader(workFile.getPath()));
            String string;
            while ((string = br.readLine()) != null)
                actualList.add(string);
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertEquals(expectedList, actualList);
    }
}