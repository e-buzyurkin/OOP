package ru.nsu.buzyurkin;

import java.io.*;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * The SubstringInFile class is used to find all occurrences of a given substring within a text file.
 * It uses the Z-function algorithm to efficiently locate the positions of the substring within the file's content.
 */

public class SubstringInFile {
    private BufferedReader streamReader;

    /**
     * Constructs a SubstringInFile object to read a specified file and initialize the streamReader.
     *
     * @param filename The name of the file to be processed.
     * @throws IOException If an I/O error occurs while opening or reading the file.
     */
    public SubstringInFile(String filename) throws IOException {
        streamReader = new BufferedReader(new InputStreamReader(
                new FileInputStream(filename), StandardCharsets.UTF_8));
    }

    /**
     * Finds all occurrences of the specified substring in the file's content.
     *
     * @param substring The substring to search for in the file.
     * @return A list of integer positions where the substring is found in the file.
     * @throws IOException If an I/O error occurs while reading the file.
     */
    public List<Integer> findSubstring(String substring) throws IOException {
        char[] currentBuffer = new char[substring.length() * 2];

        int bytesRead;
        int offset = 0;
        streamReader.read(currentBuffer);

        List<Integer> startIndexes = new ArrayList<>(
                        zFunction(new String(currentBuffer),
                        substring)
        );

        char[] tempBuffer = new char[substring.length()];

        while ((bytesRead = streamReader.read(tempBuffer)) != -1) {
            System.arraycopy(currentBuffer, substring.length() ,
                             currentBuffer, 0,
                      substring.length());

            System.arraycopy(tempBuffer, 0,
                             currentBuffer, substring.length(),
                             bytesRead);
            offset += substring.length();

            List<Integer> temp = zFunction(
                    new String(currentBuffer),
                    substring);
            
            for (var ind : temp) {
                if (!startIndexes.contains(ind + offset)) {
                    startIndexes.add(ind + offset);
                }
            }
        }


        return startIndexes;
    }

    /**
     * Computes the Z-function for a given string and a substring to efficiently find all occurrences of the substring in the string.
     *
     * @param str The source string in which to search for the substring.
     * @param substr The substring to search for in the source string.
     * @return A list of integer positions where the substring is found in the source string.
     */
    private List<Integer> zFunction(String str, String substr) {
        int[] zf = new int[str.length()];
        List<Integer> startIndexes = new ArrayList<>();

        for (int i = 0; i < str.length(); i++) {
            while (i + zf[i] < str.length() &&
                    zf[i] < substr.length() &&
                    substr.charAt(zf[i]) == str.charAt(i + zf[i])) {
                zf[i]++;
            }
            if (zf[i] == substr.length()) {
                startIndexes.add(i);
            }
        }

        return startIndexes;
    }

}
