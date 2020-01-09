package com.company;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = null;
        Scanner scanner1 = new Scanner(System.in);
        boolean foundFile = false;

        String path = "";
        String word = "";
        List<Row> rowList = new ArrayList<>();
        int isNotAWord = 0;

        while(true)
        {
            if (foundFile)
            {
                break;
            }
            path = scanner1.nextLine();
            try {
                scanner = new Scanner(new FileInputStream(path));
                while (scanner.hasNextLine())
                {
                    Row row = new Row(new ArrayList<>());
                    String currentLine = scanner.nextLine();
                    for (int i = 0; i < currentLine.length(); i++) {
                        char symbol = currentLine.charAt(i);
                        if (Character.isLetterOrDigit(symbol) || symbol == '\'')
                        {
                            word += String.valueOf(symbol);
                        }
                        else if (!Character.isLetterOrDigit(symbol) && symbol != ' ')
                        {
                            if (!word.equals("")) {
                                row.getWordsPerRow().add(word);
                                word = "";
                                word += String.valueOf(symbol);
                            }
                        }
                        else if (symbol == ' ')
                        {
                            if (!word.equals("")) {
                                row.getWordsPerRow().add(word);
                                word = "";
                            }
                        }
                    }
                    if (!word.equals("")) {
                        row.getWordsPerRow().add(word);
                        word = "";
                    }

                    rowList.add(row);
                    foundFile = true;
                }
            } catch (FileNotFoundException e) {
                   e.printStackTrace();
                System.out.println("Wrong directory!");
                System.out.print("Enter the new directory: ");
            } finally {
                if (scanner != null) {
                    scanner.close();
                }
            }
        }


        int fileRows = rowList.size();
        System.out.printf("Number of rows: %d", fileRows);
        System.out.println();

        for (int i = 0; i < rowList.size(); i++) {
            var currentRow = rowList.get(i);
            for (int j = 0; j < currentRow.getWordsPerRow().size(); j++) {
                var element = currentRow.getWordsPerRow().get(j);
                if (!Character.isLetterOrDigit(element.charAt(0)))
                {
                    isNotAWord++;
                }
            }
            int wordsPerRow = currentRow.getWordsPerRow().size() - isNotAWord;
            int indexOfRow = rowList.indexOf(currentRow) + 1;
            System.out.println(String.format("Row %d has -> %d words!",indexOfRow, wordsPerRow));
            isNotAWord = 0;
        }
        System.out.println();

        scanner = new Scanner(System.in);

        String command = "";
        System.out.print("Choose your option: ");

        System.out.println("Add/Delete/Swap/End");

        while (!"End".equals(command = scanner.nextLine()))
        {
            if (command.equals("Add")){
                add(scanner, rowList);
            }
            else if (command.equals("Delete"))
            {
               delete(scanner, rowList);
            }
            else if (command.equals("Swap"))
            {
                swap(command, scanner, rowList);
            }

        }

        PrintStream writer = null;
        String newFilePath = "D:\\text2.txt";
        String fileRow = "";

        try {
            writer = new PrintStream(newFilePath);
            for (int i = 0; i < rowList.size(); i++) {
                var currentRow = rowList.get(i);
                for (int j = 0; j < currentRow.getWordsPerRow().size(); j++) {
                    var element = currentRow.getWordsPerRow().get(j);
                    if (j == 0)
                    {
                        fileRow += element;
                    }
                    else if (Character.isLetterOrDigit(element.charAt(0)))
                    {
                        fileRow += ' ';
                        fileRow += element;
                    }
                    else if (!Character.isLetterOrDigit(element.charAt(0)))
                    {
                        fileRow += element;
                    }


                }
                writer.println(fileRow);
                fileRow = "";
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            writer.close();
        }
    }

    public static void add(Scanner scanner, List<Row> rowList)
    {
        System.out.print("Choose the line where you want to add some words: ");
        int myLine = Integer.parseInt(scanner.nextLine());
        while (myLine > rowList.size())
        {
            System.out.println("Invalid row!");
            myLine = Integer.parseInt(scanner.nextLine());
        }
        var wantedLine = rowList.get(myLine - 1);

        System.out.print(String.format("Choose the words you want to add at line %d: ", myLine));
        String[] myWords = scanner.nextLine().split("\\s+");

        for (int i = 0; i < myWords.length; i++)
        {
            String myWord = myWords[i];
            System.out.printf("Choose the position of the word %s -> ", myWord);
            int myPosition = Integer.parseInt(scanner.nextLine());
            while (myPosition > rowList.get(myLine - 1).getWordsPerRow().size())
            {
                System.out.println("Invalid position!");
                myPosition = Integer.parseInt(scanner.nextLine());
            }
            wantedLine.getWordsPerRow().add(myPosition - 1, myWord);
        }
    }
    public static void delete(Scanner scanner, List<Row> rowList)
    {
        System.out.print("Choose the line where you want to delete some words: ");
        int myLine = Integer.parseInt(scanner.nextLine());
        while (myLine > rowList.size())
        {
            System.out.println("Invalid row!");
            myLine = Integer.parseInt(scanner.nextLine());
        }
        var wantedLine = rowList.get(myLine - 1);

        System.out.print(String.format("Choose the words you want to delete at line %d: ", myLine));
        int[] wantedPos = Arrays.stream(scanner.nextLine().split("\\s+"))
                .mapToInt(value -> Integer.parseInt(value))
                .toArray();
        for (int i = 0; i < wantedPos.length; i++) {
            if (wantedPos[i] > rowList.get(myLine - 1).getWordsPerRow().size())
            {
                System.out.println(String.format("%d is an invalid position!", wantedPos[i]));
                int newPos = Integer.parseInt(scanner.nextLine());
                while (newPos > rowList.get(myLine - 1).getWordsPerRow().size())
                {
                    System.out.println("Invalid position!");
                    newPos = Integer.parseInt(scanner.nextLine());
                }
                wantedPos[i] = newPos;
            }
        }
        String[] data = new String[wantedPos.length];
        for (int i = 0; i < wantedPos.length; i++) {
            data[i] = wantedLine.getWordsPerRow().get(wantedPos[i] - 1);
        }

        for (String element : data) {
            wantedLine.getWordsPerRow().remove(element);
        }
    }
    public static void swap(String command, Scanner scanner, List<Row> rowList)
    {
        System.out.print(command + " -> ");
        int[] parameters = Arrays.stream(scanner.next().split(","))
                .mapToInt(value -> Integer.parseInt(value))
                .toArray();

        int FWfileRow = parameters[0] - 1;
        int FWwordPos = parameters[1] - 1;
        int SWfileRow = parameters[2] - 1;
        int SWwordPos = parameters[3] - 1;

        String fW = rowList.get(FWfileRow).getWordsPerRow().get(FWwordPos);
        String sW = rowList.get(SWfileRow).getWordsPerRow().get(SWwordPos);
        rowList.get(FWfileRow).getWordsPerRow().set(FWwordPos, sW);
        rowList.get(SWfileRow).getWordsPerRow().set(SWwordPos, fW);
    }
}

