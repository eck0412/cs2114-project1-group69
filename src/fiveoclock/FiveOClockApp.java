package fiveoclock;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/**
 * // -------------------------------------------------------------------------
 * /** This class connects the other classes together and runs the program in
 * order
 * 
 * @author Erik Kwon
 * @version Sep 19, 2026
 */
public class FiveOClockApp
{
    // ~ Fields ................................................................
    private static final int MAX_ATTEMPTS = 3;
    private final TimeFinderService finder;
    private final InputValidator validator;
    private final ConsoleView view;

    // ~ Constructors ..........................................................

    /**
     * @param finder
     *            calculates the matching locations; must not be null
     * @param validator
     *            checks user input; must not be null
     * @param view
     *            shows the prompts and results; must not be null
     * @throws IllegalArgumentException
     *             if any argument is null
     */

    public FiveOClockApp(
        TimeFinderService finder,
        InputValidator validator,
        ConsoleView view)
    {
        if (finder == null)
        {
            throw new IllegalArgumentException("Finder must not be null");
        }

        if (validator == null)
        {
            throw new IllegalArgumentException("Validator must not be null");
        }

        if (view == null)
        {
            throw new IllegalArgumentException("View must not be null");
        }

        this.finder = finder;
        this.validator = validator;
        this.view = view;

    }


    // ~Public Methods ........................................................
    /**
     * Runs the automatic search using the computer's current time and displays
     * one matching location, or a message when nothing matches
     */
    public void run()
    {
        view.showWelcome();
        showMatchFor(Instant.now());
    }


    /**
     * This method asks the user for a time and time zone, then reports where it
     * is five o'clock at that same moment. Invalid input is reported and
     * re-prompted up to three times.
     */
    public void runManualMode()
    {
        view.showWelcome();
        for (int attempt = 0; attempt < MAX_ATTEMPTS; attempt++)
        {
            String timeText = view.readTime();
            String zoneText = view.readZoneId();

            if (validator.isBlank(timeText) && validator.isBlank(zoneText))
            {
                view.showError("No input was provided");
                return;
            }
            try
            {
                LocalTime time = validator.parseTime(timeText);
                ZoneId zone = validator.parseZoneId(zoneText);
                showMatchFor(toInstant(time, zone));
                return;
            }
            catch (IllegalArgumentException e)
            {
                view.showError(e.getMessage());
            }
        }
        view.showError("Too many invalid attempts. Stopping.");
    }


    /**
     * This method starts the program.
     *
     * @param args
     *            pass "--manual" to enter time and zone by hand; any other
     *            arguments are ignored
     */

    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        FiveOClockApp app = new FiveOClockApp(
            new TimeFinderService(new LocationRepository()),
            new InputValidator(),
            new ConsoleView(scanner, System.out));
        if (usesManualMode(args))
        {
            app.runManualMode();
        }

        else
        {
            app.run();
        }
        scanner.close();
    }


    /**
     * This method finds the matches for a certain moment and displays one of
     * them
     * 
     * @param moment
     *            the instant to search at
     */
    private void showMatchFor(Instant moment)
    {
        List<Location> matches = finder.findLocationsAtFive(moment);
        Optional<Location> chosen = finder.chooseRandomLocation(matches);

        if (chosen.isPresent())
        {
            Location location = chosen.get();
            view.showLocation(location, finder.getLocalTime(location, moment));
        }

        else
        {
            view.showNoMatch();
        }
    }


    /**
     * This method combines the date with a local time and zone into an instant
     * 
     * @param time
     *            is the local time of the day
     * @param zone
     *            is the time in that zone
     * @return Returns the instant at which it is that time in that zone today
     */
    private Instant toInstant(LocalTime time, ZoneId zone)
    {
        return ZonedDateTime.of(LocalDate.now(zone), time, zone).toInstant();
    }


    /**
     * This method checks the command-line arguments in the request manual mode
     * 
     * @param args
     *            the command line argument, which may be null
     *            
     * @return true when"--manual" appears, returning false otherwise
     */
    private static boolean usesManualMode(String[] args)
    {
        if (args == null)
        {
            return false;
        }
        for (String arg : args)
        {
            if ("--manual".equalsIgnoreCase(arg))
            {
                return true;
            }
        }
        return false;
    }
}
