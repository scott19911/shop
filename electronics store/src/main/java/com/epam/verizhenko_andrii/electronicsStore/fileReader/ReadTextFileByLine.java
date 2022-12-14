package com.epam.verizhenko_andrii.electronicsStore.fileReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Scanner;

/**
 * File rider line by line
 */
public class ReadTextFileByLine implements Iterable<String> {
    private final File file;
    private Scanner sc;

    /**
     * Initializes a file at the given path
     * @param fileName - file path
     */
    public ReadTextFileByLine(String fileName) {
        file = new File(fileName);
    }

    @Override
    public Iterator<String> iterator() {
        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return new Iterator<String>() {
            @Override
            public boolean hasNext() {
                return sc.hasNext();
            }

            @Override
            public String next() {
                return sc.nextLine();
            }
        };
    }
}
