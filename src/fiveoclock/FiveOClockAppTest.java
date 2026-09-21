package fiveoclock;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Random;
import java.util.Scanner;
import student.TestCase;

public class FiveOClockAppTest
    extends TestCase
{
    // ~ Fields ................................................................
    private ByteArrayOutputStream captured;
    private PrintStream output;
    private TimeFinderService finder;
    private InputValidator validator;

    // ~ Constructors ..........................................................

    // ~Public Methods ........................................................

    /**
     * This sets up the shared collaborators and capture stream
     */
    public void setUp()
    {
        captured = new ByteArrayOutputStream();
        output = new PrintStream(captured);
        finder =
            new TimeFinderService(new LocationRepository(), new Random(42));
        validator = new InputValidator();

    }


    /**
     * This method builds the app with simulated input
     * 
     * @param input
     *            the text the view should read
     * @return an app with a view reading the input
     */
    private FiveOClockApp appReading(String input)
    {
        return new FiveOClockApp(
            finder,
            validator,
            new ConsoleView(new Scanner(input), output));
    }


    /**
     * This method gets everything printed so far
     * 
     * @return the captued string
     */
    private String printed()
    {
        output.flush();
        return captured.toString();
    }


    /**
     * This method tests the constructor
     */
    public void testConstructor()
    {
        assertNotNull(appReading(""));
    }


    /**
     * This method tests the constructor if it rejects null finder
     */

    public void testConstructorRejectsNullFinder()
    {
        try
        {
            new FiveOClockApp(
                finder,
                null,
                new ConsoleView(new Scanner(""), output));
            fail("Expected an IllegalArgumentException for a null validator");
        }
        catch (IllegalArgumentException e)
        {
            assertNotNull(e.getMessage());
        }
    }


    /**
     * This tests the null view
     */
    public void testConstructorRejectsNullValidator()
    {
        try
        {
            new FiveOClockApp(finder, validator, null);
            fail("Expected an IllegalArgumentException for a null view");
        }
        catch (IllegalArgumentException e)
        {
            assertNotNull(e.getMessage());
        }
    }


    /**
     * This method tests run
     */

    public void testRun()
    {
        appReading("").run();

        String text = printed();
        assertTrue(text.contains("5 O'Clock Somewhere"));
        assertTrue(
            text.contains("It's five o'clock in")
                || text.contains("No saved location"));
    }


    /**
     * This tests the manual mode with valid input
     */
    public void testRunManualModeWithValidInput()
    {
        appReading("17:30\nAmerica/Lima\n").runManualMode();

        String text = printed();
        assertTrue(text.contains("It's five o'clock in"));
        assertTrue(text.contains("Lima"));
    }


    /**
     * This tests the 12 hour format in manual mode
     */
    public void testRunManualModeAcceptsTwelveHour()
    {
        appReading("5:30 PM\nAmerica/Lima\n").runManualMode();
        assertTrue(printed().contains("Lima"));
    }

    /**
     * This tests a time outside of 5 PM hour anywhere has no match 
     */
    public void testRunManualModeWithNoMatch()
    {
        appReading("3:17\nPacific/Kiritmati\n").runManualMode();

        String text = printed();
        assertTrue(
            text.contains("No saved location")
                || text.contains("It's five o'clock in"));
    }


    /**
     * This tests to see if the app asks again after bad input
     */
    public void testRunManualModeRetiresAfterBadTime()
    {
        appReading("530PM\nAmerica/Lima\n17:30\nAmerica/Lima\n")
            .runManualMode();

        String text = printed();
        assertTrue(text.contains("Error."));
        assertTrue(text.contains("Lima"));
    }
    
    

}
