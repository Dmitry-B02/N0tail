import org.junit.Test;
import Tail.N0tailLauncher;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

public class test {

    private final String[] args1 = {"-c", "7", "-o", "testtest/resources/output1.txt", "testtest/resources/test1.txt"};
    private final String[] args2 = {"-o", "testtest/resources/output2.txt", "testtest/resources/test1.txt"};
    private final String[] args3 = {"-n", "14", "-o", "testtest/resources/output3.txt", "testtest/resources/test1.txt"};

    @Test
    public void testing() throws IOException {
        N0tailLauncher.main(args1);
        assertEquals(Files.readAllLines(Paths.get("testtest/resources/output1.txt")),
                Files.readAllLines(Paths.get("testtest/resources/expectedOutput1.txt")));
        N0tailLauncher.main(args2);
        assertEquals(Files.readAllLines(Paths.get("testtest/resources/output2.txt")),
                Files.readAllLines(Paths.get("testtest/resources/expectedOutput2.txt")));
        N0tailLauncher.main(args3);
        assertEquals(Files.readAllLines(Paths.get("testtest/resources/output3.txt")),
                Files.readAllLines(Paths.get("testtest/resources/expectedOutput3.txt")));
    }
}
