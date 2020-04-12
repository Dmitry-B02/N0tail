import org.junit.Test;
import n0tail.n0tail_Launcher;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertTrue;

public class test {

    private boolean equality(String output) throws IOException {
        return Files.readAllLines(Paths.get(output)).equals(Files.readAllLines(Paths.get("testtest/resources/expectedOutput.txt")));
    }

    private final String[] args = {"-c", "7", "-o", "testtest/resources/output.txt", "testtest/resources/test1.txt"};

    @Test
    public void testing() throws IOException {
        n0tail_Launcher.main(args);
        String output = "testtest/resources/output.txt";
        assertTrue(equality(output));
    }
}
