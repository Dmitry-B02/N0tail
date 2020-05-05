package Tail;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

public class N0tail {

    private final File outputFileName;

    private int symTailLength;

    private int rowTailLength;

    public N0tail(File outputFileName, Integer symTailLength, Integer rowTailLength) {
        this.outputFileName = outputFileName;
        this.symTailLength = symTailLength;
        this.rowTailLength = rowTailLength;
    }

    // флаг -n из файла
    private void filesRow(ArrayList<String> currentFile, BufferedWriter writer) throws IOException {
        if (rowTailLength > currentFile.size()) rowTailLength = currentFile.size();
        for (int j = currentFile.size() - rowTailLength; j < currentFile.size(); j++) {
            writer.write(currentFile.get(j));
            writer.write(System.lineSeparator());
        }
    }

    // флаг -c из файла
    private void filesSym(ArrayList<String> currentFile, BufferedWriter writer) throws IOException {
        ArrayDeque<Character> deque = new ArrayDeque<>();
        for (int i = currentFile.size() - 1; i >= 0; i--) {
            String row = currentFile.get(i);
            for (int j = row.length() - 1; j >= 0; j--) {
                if (symTailLength != 0) {
                    deque.addFirst(row.charAt(j));
                    symTailLength--;
                } else {
                    break;
                }
            }
            deque.addFirst('\n');
        }
        deque.pollFirst();   // убираю из очереди первый элемент, т.к. он всегда будет '\n'
        while (!deque.isEmpty()) {
            if (deque.peekFirst() == '\n') {
                writer.write(System.lineSeparator());
                deque.pollFirst();
            } else {
                writer.write(deque.pollFirst());
            }
        }
        writer.write(System.lineSeparator()); // переход на новую строку на случай, если > 1 файла
    }

    private void choice(ArrayList<String> currentFile, BufferedWriter writer) throws IOException {
        if (rowTailLength != 0) {
            filesRow(currentFile, writer);
        } else if (symTailLength != 0) {
            filesSym(currentFile, writer);
        } else {
            rowTailLength = 10;
            filesRow(currentFile, writer);
        }
    }

    public void getTail(List<String> filesList) throws IOException {
        BufferedWriter writer = outputFileName != null ? new BufferedWriter(new FileWriter(outputFileName)) :
                new BufferedWriter(new OutputStreamWriter(System.out)); // если не задан outputFile, то вывод на консоль
        if (!filesList.isEmpty()) {
            for (String s : filesList) {         // рассматриваю каждый из файлов
                ArrayList<String> currentFile = (ArrayList<String>) Files.readAllLines(Paths.get(s));
                writer.write(s);                               // пишу имя файла, из которого взят хвост
                writer.write(System.lineSeparator());         // (для одного файла тоже пишу имя для удобства)
                choice(currentFile, writer);
            }
            writer.close();
        } else {   // в консоли вместо currentFile будет список строк с консоли
            ArrayList<String> currentText = new ArrayList<>();
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            currentText.add(String.valueOf(reader.readLine()));
            reader.close();
            choice(currentText, writer);
        }
    }
}
