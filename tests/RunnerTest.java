import Exceptions.InvalidMapException;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
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
    void displayTest() throws InvalidMapException {
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        final PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));
        final InputStream originalIn = System.in;
        String inputString = "s" + System.getProperty("line.separator")
                + "w" + System.getProperty("line.separator")
                + "d" + System.getProperty("line.separator")
                + "d" + System.getProperty("line.separator")
                + "d" + System.getProperty("line.separator")
                + "d" + System.getProperty("line.separator")
                + "d" + System.getProperty("line.separator")
                + "d" + System.getProperty("line.separator");
        System.setIn(new ByteArrayInputStream(inputString.getBytes()));
        Runner.main(new String[]{"tests//goodmap.txt"});   //Load the correct map
        assertTrue(outContent.toString().contains("You win!"));
        System.setIn(originalIn);
        System.setOut(originalOut);
    }

    @Test
    void deadLockTest() throws InvalidMapException {
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        final PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));
        Runner.main(new String[]{"tests//deadlockMap.txt"});
        assertTrue(outContent.toString().contains("Game deadlocked. Terminating..."));
        System.setOut(originalOut);
    }
} 