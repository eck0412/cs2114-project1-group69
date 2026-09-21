package fiveoclock;

import static org.junit.Assert.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Scanner;
import student.TestCase;

/**
 * Tests the ConsoleView class.
 *
 * @author Aidan Southwick
 * @version 2026.09.16
 */
public class ConsoleViewTest extends TestCase {
    private ByteArrayOutputStream captured;
    private PrintStream output;
    private Location lima;

    /** 
     * Prepares a capture stream and a sample location.
     */
    public void setUp() {
        captured = new ByteArrayOutputStream();
        output = new PrintStream(captured);
        lima = new Location("Lima", "Peru", ZoneId.of("America/Lima"),
            "Lima is a desert city.", "");
    }

    /** 
     * Builds a view reading from the supplied simulated input.
     */
    private ConsoleView viewReading(String input) {
        return new ConsoleView(new Scanner(input), output);
    }

    /** 
     * Everything printed so far.
     */
    private String printed() {
        output.flush();
        return captured.toString();
    }

    /** 
     * A Scanner and PrintStream produce a usable view.
     */
    public void testConstructor() {
        assertNotNull(viewReading(""));
    }

    /** 
     * A null Scanner is rejected.
     */
    public void testConstructorRejectsNullScanner() {
        try {
            new ConsoleView(null, output);
            fail("Expected an IllegalArgumentException for a null Scanner.");
        }
        catch (IllegalArgumentException e) {
            assertNotNull(e.getMessage());
        }
    }

    /** 
     * A null PrintStream is rejected.
     */
    public void testConstructorRejectsNullOutput() {
        try {
            new ConsoleView(new Scanner(""), null);
            fail("Expected an IllegalArgumentException for null output.");
        }
        catch (IllegalArgumentException e) {
            assertNotNull(e.getMessage());
        }
    }

    /** 
     * The welcome banner names the program.
     */
    public void testShowWelcome() {
        viewReading("").showWelcome();

        assertTrue(printed().contains("5 O'Clock Somewhere"));
    }

    /** 
     * readTime returns the entered text and prompts first.
     */
    public void testReadTime() {
        String entered = viewReading("5:30 PM\n").readLine();

        assertEquals("5:30 PM", entered);
        assertTrue(printed().contains("Enter a time"));
    }

    /** 
     * Blank input is returned as-is for the validator to reject.
     */
    public void testReadTimeReturnsBlankInput() {
        assertEquals("   ", viewReading("   \n").readLine());
    }

    /** 
     * Exhausted input returns an empty string instead of throwing. 
     */
    public void testReadTimeWithNoInput() {
        assertEquals("", viewReading("").readLine());
    }

    /** 
     * readZoneId returns the entered text and prompts first.
     */
    public void testReadZoneId() {
        String entered = viewReading("America/New_York\n").readZoneId();

        assertEquals("America/New_York", entered);
        assertTrue(printed().contains("Enter a time zone"));
    }

    /** 
     * An unknown zone name is still returned for the validator to reject.
     */
    public void testReadZoneIdReturnsUnknownName() {
        assertEquals("Mars/Olympus",
            viewReading("Mars/Olympus\n").readZoneId());
    }

    /** 
     * Exhausted input returns an empty string.
     */
    public void testReadZoneIdWithNoInput() {
        assertEquals("", viewReading("").readZoneId());
    }

    /** 
     * showLocation prints the city, country, zone, and local time
     */
    public void testShowLocation() {
        ZonedDateTime localTime = ZonedDateTime.of(2026, 9, 16, 17, 30, 0, 0,
            ZoneId.of("America/Lima"));

        viewReading("").showLocation(lima, localTime);

        String text = printed();
        assertTrue(text.contains("Lima"));
        assertTrue(text.contains("Peru"));
        assertTrue(text.contains("America/Lima"));
        assertTrue(text.contains("5:30 PM"));
        assertTrue(text.contains("Lima is a desert city."));
    }

    /** 
     * A location with no fun fact prints no fun-fact line.
     */
    public void testShowLocationWithoutFunFact() {
        Location bare = new Location("Tokyo", "Japan",
            ZoneId.of("Asia/Tokyo"));
        ZonedDateTime localTime = ZonedDateTime.of(2026, 9, 16, 17, 0, 0, 0,
            ZoneId.of("Asia/Tokyo"));
        viewReading("").showLocation(bare, localTime);
        String text = printed();
        assertTrue(text.contains("Tokyo"));
        assertFalse(text.contains("Fun fact"));
    }

    /** 
     * A null location is rejected.
     */
    public void testShowLocationRejectsNullLocation() {
        try {
            viewReading("").showLocation(null, ZonedDateTime.now());
            fail("Expected an IllegalArgumentException for a null location.");
        }
        catch (IllegalArgumentException e) {
            assertNotNull(e.getMessage());
        }
    }

    /** 
     * A null time is rejected.
     */
    public void testShowLocationRejectsNullTime() {
        try {
            viewReading("").showLocation(lima, null);
            fail("Expected an IllegalArgumentException for a null time.");
        }
        catch (IllegalArgumentException e) {
            assertNotNull(e.getMessage());
        }
    }

    /** 
     * showError prints message plus valid examples.
     */
    public void testShowError() {
        viewReading("").showError("'530PM' is not a valid time.");
        String text = printed();
        assertTrue(text.contains("'530PM' is not a valid time."));
        assertTrue(text.contains("5:30 PM"));
        assertTrue(text.contains("America/New_York"));
    }

    /** 
     * A blank message falls back to a general error.
     */
    public void testShowErrorWithBlankMessage() {
        viewReading("").showError("   ");
        assertTrue(printed().contains("Something went wrong"));
    }

    /** 
     * A null message also falls back to a general error.
     */
    public void testShowErrorWithNullMessage() {
        viewReading("").showError(null);
        assertTrue(printed().contains("Something went wrong"));
    }

    /** 
     * showNoMatch explains that nothing matched.
     */
    public void testShowNoMatch() {
        viewReading("").showNoMatch();
        assertTrue(printed().contains("No saved location"));
    }
}
