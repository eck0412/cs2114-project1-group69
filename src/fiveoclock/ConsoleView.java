package fiveoclock;

import java.io.PrintStream;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Scanner;

/**
 * Shows the promts, the results, and the error messages.
 *
 * @author Aidan Southwick
 * @version 2026.09.16
 */
public class ConsoleView
{
    private static final DateTimeFormatter DISPLAY_FORMAT =
        DateTimeFormatter.ofPattern("h:mm a 'on' EEEE, MMMM d", Locale.US);

    private final Scanner scanner;
    private final PrintStream output;

    /**
     * @param scanner
     *            reads the user's input; must not be null
     * @param output
     *            displays text to the users; must not be null
     * @throws IllegalArgumentException
     *             if either argument is null
     */
    public ConsoleView(Scanner scanner, PrintStream output)
    {
        if (scanner == null)
        {
            throw new IllegalArgumentException("Scanner must not be null.");
        }
        if (output == null)
        {
            throw new IllegalArgumentException("Output must not be null.");
        }
        this.scanner = scanner;
        this.output = output;
    }


    /**
     * Prints the programs opening banner
     */
    public void showWelcome()
    {
        output.println("=================================");
        output.println("  It's 5 O'Clock Somewhere");
        output.println("=================================");

    }


    /**
     * Prompts for and then reads a time.
     * 
     * @return the raw text the user entered or ""input has run out
     */
    public String readZoneId()
    {
        output.print("Enter a time zone (for example America/New_York):");
        return readLine();
    }


    /**
     * Displays a matching location with the local time
     * 
     * @param location
     *            the matching location
     * @param localTime
     *            the locations local time and date
     * @throws IllegalArgument
     *             Exception if either argument is null
     */
    public void showLocation(Location location, ZonedDateTime localTime)
    {
        if (location == null)
        {
            throw new IllegalArgumentException("Location must not be null.");
        }
        if (localTime == null)
        {
            throw new IllegalArgumentException("Local time must not be null.");
        }
        output.println();
        output.println(
            "It's five o'clock in " + location.getCity() + ", "
                + location.getCountry() + "!");
        output.println("  Time zone:  " + location.getZoneId().getId());
        output.println("  Local time: " + localTime.format(DISPLAY_FORMAT));
        if (!location.getFunFact().isEmpty())
        {
            output.println(" Fun fact: " + location.getFunFact());
        }
        output.println();
    }


    /**
     * Displays error message with an example of a valid input.
     * 
     * @param message
     *            the description of what went wrong; a blank message produces a
     *            general error instead
     */
    public void showError(String message)
    {
        String text = (message == null || message.trim().isEmpty())
            ? "Something went wrong with that input."
            : message.trim();
        output.println("Error: " + text);
        output.println(" Valid time examples: 5:30 PM, 17:30");
        output.println(" Valid zone examples: America/New_York, Asia/Tokyo");
    }


    /**
     * Explains that no saved location is currently in the 5PM hour.
     */
    public void showNoMatch()
    {
        output.println();
        output.println(
            "No saved location is in the 5 o'clock hour "
                + "right now. Try again a little later!");
        output.println();
    }


    /**
     * Prompts for and reads a time
     * 
     * @return the raw text the user entered, or "" if input has run out
     */
    public String readTime()
    {
        output.print("Enter a time (for example 5:30 PM or 17:30)");
        return readLine();
    }


    /**
     * Reads one line returning "" when there is no more input
     */
    private String readLine()
    {
        if (!scanner.hasNextLine())
        {
            output.println();
            return "";
        }
        return scanner.nextLine();
    }
}
