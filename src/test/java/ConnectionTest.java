import org.junit.Test;
import static org.junit.Assert.*;

public class ConnectionTest {
    @Test
    public void testSubnet() {
        String expected = "192.168.3.";

        String actual = Connection.getSubnet("192.168.3.273");
        assertEquals("192.168.3.273", expected, actual);

        actual = Connection.getSubnet("192.168.3.32");
        assertEquals("192.168.3.32", expected, actual);
    }
    
}