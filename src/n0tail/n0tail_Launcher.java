package n0tail;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class n0tail_Launcher {

    @Option(name = "-o", metaVar = "OFile", usage = "Output file name")
    private String name;

    @Option(name = "-c", metaVar = "SymbolsAm", usage = "Amount of output symbols", forbids = "-n")
    private int symbolsAmount;

    @Option(name = "-n", metaVar = "RowsAm", usage = "Amount of output rows")
    private int rowsAmount;

    @Argument(metaVar = "InputName", usage = "Input file name")
    private ArrayList<String> filesList = new ArrayList<String>();

    public static void main(String[] args) {
        new n0tail_Launcher().launch(args);
    }

    private void launch(String[] args) {
        CmdLineParser parser = new CmdLineParser(this);

        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            System.err.println("java -jar n0tail.jar -o OFile -c SymbolsAm -n RowsAm InputName");
            parser.printUsage(System.err);
            return;
        }
        try {
            n0tail tailObj = new n0tail(name, symbolsAmount, rowsAmount);
            tailObj.getTail(filesList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
