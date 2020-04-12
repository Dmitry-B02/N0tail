package n0tail;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class n0tail {

    String outputFileName;

    int symTailLength;

    int rowTailLength;

    public n0tail(String outputFileName, Integer symTailLength, Integer rowTailLength) {
        this.outputFileName = outputFileName;
        this.symTailLength = symTailLength;
        this.rowTailLength = rowTailLength;
    }

    // флаг -n из файла
    private void filesRow(ArrayList<String> currentFile, BufferedWriter writer) throws IOException {
        for (int j = currentFile.size() - rowTailLength; j < currentFile.size(); j++) {
            writer.write(currentFile.get(j));
            writer.write(System.lineSeparator());
        }
    }

    // флаг -c из файла
    private void filesSym(ArrayList<String> currentFile, BufferedWriter writer, StringBuilder auxBuilder) throws IOException {
        int currentSymAmount = 0;
        while (currentSymAmount != symTailLength) {
            for (int j = currentFile.size() - 1; j >= 0; j--)
                first:{
                    String row = currentFile.get(j);
                    for (int k = row.length() - 1; k >= 0; k--) {
                        if (currentSymAmount < symTailLength) {
                            auxBuilder.append(row.charAt(k));
                            currentSymAmount++;
                        } else break first;
                    }
                    auxBuilder.append("\n");  // касательно этой строчки у меня есть один вопрос
                }
            writer.write(auxBuilder.reverse().toString());
        }
    }

    // без флага из файла
    private void filesDefault(ArrayList<String> currentFile, BufferedWriter writer) throws IOException {
        for (int j = currentFile.size() - 10; j >= 0 && j < currentFile.size(); j++) {
            writer.write(currentFile.get(j));
            writer.write(System.lineSeparator());
        }
    }

    private void choice(ArrayList<String> currentFile, BufferedWriter writer, StringBuilder auxBuilder) throws IOException {
        if (rowTailLength != 0) {
            filesRow(currentFile, writer);
        } else if (symTailLength != 0) {
            filesSym(currentFile, writer, auxBuilder);
        } else {
            filesDefault(currentFile, writer);
        }
    }

    public void getTail(ArrayList<String> filesList) throws IOException {
        StringBuilder auxBuilder = new StringBuilder();
        BufferedWriter writer = outputFileName != null ? Files.newBufferedWriter(Paths.get(outputFileName)) :
                new BufferedWriter(new OutputStreamWriter(System.out)); // если не задан outputFile, то вывод на консоль
        if (filesList != null) {
            for (String s : filesList) {         // рассматриваю каждый из файлов
                ArrayList<String> currentFile = (ArrayList<String>) Files.readAllLines(Paths.get(s));
                writer.write(s);                               // пишу имя файла, из которого взят хвост
                writer.write(System.lineSeparator());         // (для одного файла тоже пишу имя для удобства)
                choice(currentFile, writer, auxBuilder);
            }
        } else {   // в консоли вместо currentFile будет список строк с консоли
            ArrayList<String> currentText = new ArrayList<>();
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            while (reader.readLine() != null) {
                currentText.add(reader.readLine());
            }
            choice(currentText, writer, auxBuilder);
        }
        writer.close();
    }

}
