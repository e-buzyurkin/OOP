package ru.nsu.buzyurkin;

import java.io.BufferedReader;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * The {@code StdinReader} class facilitates reading a line of input from the standard input stream.
 */
public class StdinReader {
    /**
     * Reads a line of input from the standard input stream.
     *
     * @return A string containing the line of input read from the standard input.
     * @throws IOError If an I/O error occurs while reading the input.
     */
    public static String readLine() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String line;

        try {
            line = reader.readLine();
        } catch (IOException e) {
            throw new IOError(e);
        }

        return line;
    }
}
