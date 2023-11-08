package ru.nsu.buzyurkin;


import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SubstringInFileTests {

    @Test
    void testAbrakadabra() throws IOException {
        SubstringInFile string = new SubstringInFile("./src/test/resources/1.txt");

        List<Integer> indexes = string.findSubstring("бра");

        assertEquals(indexes,
                List.of(0, 3, 6, 9, 13, 20, 28, 32, 39, 47, 51, 58, 66, 70, 77, 85));
    }

    @Test
    void testAbaba() throws IOException {
        SubstringInFile string = new SubstringInFile("./src/test/resources/2.txt");

        List<Integer> indexes = string.findSubstring("аба");

        assertEquals(indexes, List.of(0, 2));
    }

    @Test
    void testRandomText() throws IOException {
        SubstringInFile string = new SubstringInFile("./src/test/resources/3.txt");

        List<Integer> indexes = string.findSubstring("обстояТЕльство");

        assertEquals(indexes, Collections.emptyList());
    }

    @Test
    void testTaskDesc() throws IOException {
        SubstringInFile string = new SubstringInFile("./src/test/resources/4.txt");

        List<Integer> indexes = string.findSubstring("оперативной");

        assertEquals(indexes, List.of(60));
    }

    @Test
    void testDifferentLanguages() throws IOException {
        SubstringInFile string = new SubstringInFile("./src/test/resources/5.txt");

        List<Integer> indexes = string.findSubstring("abc");

        assertEquals(indexes, List.of(61));
    }


}
