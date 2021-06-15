package ru.vsu.kudinov.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileUtils
{
    public static void writeFile(String extractedElements) throws IOException
    {
        FileWriter outputFile = new FileWriter("src\\ru\\vsu\\kudinov\\files\\result.txt");
        outputFile.write(extractedElements);
        outputFile.close();
    }


    public static String readFile() throws FileNotFoundException
    {
        Scanner scanner = new Scanner(new File("src\\ru\\vsu\\kudinov\\files\\input.txt"), "UTF-8");
        scanner.useDelimiter("\\Z");
        return scanner.next();
    }
}
