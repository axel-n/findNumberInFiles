package com.example.findNumber.generateData;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

class GenerateData {

    private static Random rnd = new Random();
    private static final String PATH = "./files/";

    // ~ 1Gb для каждого файла
    private static final int MAX_LINES_IN_FILE = 10_000_000;
    private static final int MAX_NUMBERS_IN_LINE = 10;

    private static final int MAX_FILES = 20;


    /**
     * управляет созданием файлов
     */
    static void generateFiles() {

        StringBuilder line;
        StringBuilder file;

        // generate files
        for (int i = 0; i < MAX_FILES; i++) {

            file = new StringBuilder();

            // generate lines for 1 file
            for (int j = 0; j < MAX_LINES_IN_FILE; j++) {

                line = new StringBuilder();

                // generate line
                for (int k = 0; k < MAX_NUMBERS_IN_LINE; k++) {
                    line.append(generateNumber()).append(",");
                }
                file.append(line).append("\n");
            }

            save(file, i + ".txt");
        }
    }


    /**
     * @param fileContent содержимое для файла
     * @param fileName имя будущего файла
     */
    private static void save(StringBuilder fileContent, String fileName) {

        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter(PATH + fileName));
            writer.write(fileContent.toString());
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * @return случайное число
     */
    private static int generateNumber() {
        return rnd.nextInt();
    }


}
