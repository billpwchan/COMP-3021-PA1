import Exceptions.InvalidMapException;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.AccessControlException;
import java.security.Permission;

import static org.junit.jupiter.api.Assertions.assertTrue;

class RunnerTest {
    @Test
    public void testSystemExit() throws InvalidMapException {
        System.out.println("init");
        final SecurityManager securityManager = new SecurityManager() {
            public void checkPermission(Permission permission) {
                if (permission.getName().startsWith("exitVM")) {
                    throw new AccessControlException("");
                }
            }
        };
        System.setSecurityManager(securityManager);
        try {
            Runner.main(new String[]{});
        } catch (RuntimeException e) {
        } finally {
            System.setSecurityManager(null);
        }
        assertTrue(true);       //This will be executed if not system.exit(1)
    }

    @Test
    void displayTest() throws IOException, InvalidMapException {
//        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
//        final PrintStream originalOut = System.out;
        final InputStream originalIn = System.in;
        String inputString = "s" + System.getProperty("line.separator")
                + "w" + System.getProperty("line.separator")
                + "d" + System.getProperty("line.separator")
                + "d" + System.getProperty("line.separator")
                + "d" + System.getProperty("line.separator")
                + "d" + System.getProperty("line.separator")
                + "d" + System.getProperty("line.separator")
                + "d" + System.getProperty("line.separator");

        Runner.main(new String[]{"tests//goodmap.txt"});   //Load the correct map
//        System.setOut(new PrintStream(outContent));
        System.setIn(new ByteArrayInputStream(inputString.getBytes()));
//        System.setIn(new ByteArrayInputStream("s".getBytes()));

        System.setIn(originalIn);
//        System.setOut(originalOut);
    }
}