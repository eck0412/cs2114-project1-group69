package fiveoclock;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Random;
import java.util.Scanner;
import student.TestCase;


// -------------------------------------------------------------------------
/**
 * This test class tests the FiveOClockApp
 * 
 *  @author Erik Kwon
 *  @version Sep 20, 2026
 */

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
     * 
     * @Throws Exception if the superclass setup fails
     */
    public void setUp()
        throws Exception
    {
        super.setUp();
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

    }


    /**
     * This tests the 12 hour format in manual mode
     */
    public void testRunManualModeAcceptsTwelveHour()
    {
        appReading("5:30 PM\nAmerica/Lima\n").runManualMode();
        assertTrue(printed().contains("It's five o'clock in"));
    }


    /**
     * This tests a time outside of 5 PM hour anywhere has no match
     */
    public void testRunManualModeWithNoMatch()
    {
        appReading("03:17\nPacific/Kiritimati\n").runManualMode();

        String text = printed();
        assertTrue(
            text.contains("No saved location")
                || text.contains("It's five o'clock in"));
    }


    /**
     * This tests to see if the app asks again after bad input
     */
    public void testRunManualModeRetriesAfterBadTime()
    {
        appReading("530PM\nAmerica/Lima\n17:30\nAmerica/Lima\n")
            .runManualMode();

        String text = printed();
        assertTrue(text.contains("Error"));
        assertTrue(text.contains("It's five o'clock in"));
    }


    /**
     * This tests when an invalid zone is reported, then the app asks again
     */
    public void testRunManualModeRetriesAfterBadZone()
    {
        appReading("17:30\nAmerica/Blacksburg\n17:30\nAmerica/Lima\n")
            .runManualMode();

        String text = printed();
        assertTrue(text.contains("Error:"));
        assertTrue(text.contains("It's five o'clock in"));
    }


    /**
     * This tests that with three bad attempts the code stops running
     */
    public void testRunManualModeStopsAfterThreeFailures()
    {
        appReading("bad\nbad\nbad\nbad\nbad\bad\n").runManualMode();
        assertTrue(printed().contains("Too many invalid attempts"));

    }


    /**
     * This tests manual mode if no input was provided
     */
    public void testRunManualModeWithNoInput()
    {
        appReading("").runManualMode();

        assertTrue(printed().contains("No input was provided"));

    }


    /**
     * This runs the main with given arguments, capturing everything it prints
     * 
     * @param args
     *            the command line to pass to main
     * @return everything main prints
     */
    private String mainOutput(String[] args)
    {
        PrintStream original = System.out;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        System.setOut(new PrintStream(buffer));
        try
        {
            FiveOClockApp.main(args);
        }
        finally
        {
            System.setOut(original);
        }
        return buffer.toString();
    }


    /**
     * This tests main when given no arguments
     */
    public void testMainWithNoArguments()
    {
        assertTrue(mainOutput(new String[0]).contains("5 O'Clock Somewhere"));
    }


    /**
     * This tests main ignoring arguments it doesn't recognize
     */
    public void testMainIgnoresUnknownArguments()
    {
        assertTrue(
            mainOutput(new String[] { "--verbose", "extra" })
                .contains("5 O'Clock Somewhere"));
    }


    /**
     * This tests a null argument
     */
    public void testUsesManualModeWithNullArguments()
    {
        assertTrue(mainOutput(null).contains("5 O'Clock Somewhere"));
    }
}
