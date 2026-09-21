package fiveoclock;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

/**
 * This class calculates the locations that are inside the 5PM hour.
 *
 * @author Connor Bo
 * @version Sep 16, 2026
 */
public class TimeFinderService
{
    // ~ Fields ...............................................................

    /** The hour, on a 24-hour clock, that counts as "five o'clock". */
    private static final int FIVE_PM_HOUR = 17;

    private final LocationRepository repository;
    private final Random random;

    // ~ Constructors .........................................................

    /**
     * Creates a service that draws its locations from the given repository.
     *
     * @param repository
     *            the source of saved locations; must not be null
     * @throws IllegalArgumentException
     *             if repository is null
     */
    public TimeFinderService(LocationRepository repository)
    {
        this(repository, new Random());
    }


    /**
     * Creates a service with a caller-supplied Random, which lets tests make
     * the random choice predictable.
     *
     * @param rep
     *            the source of saved locations; must not be null
     * @param rand
     *            the randomness source; must not be null
     * @throws IllegalArgumentException
     *             if either argument is null
     */
    public TimeFinderService(LocationRepository rep, Random rand)
    {
        if (rep == null)
        {
            throw new IllegalArgumentException("Repository must not be null");
        }

        if (rand == null)
        {
            throw new IllegalArgumentException("Random must not be null");
        }

        this.repository = rep;
        this.random = rand;
    }


    // ~ Public Methods ......................................................

    /**
     * Finds every saved location whose local time at the given moment falls
     * between 5:00 PM and 5:59 PM.
     *
     * @param m
     *            the instant to evaluate; must not be null
     * @return the matching locations, possibly empty
     * @throws IllegalArgumentException
     *             if m is null
     */
    public List<Location> findLocationsAtFive(Instant m)
    {
        if (m == null)
        {
            throw new IllegalArgumentException("Moment must not be null");
        }

        List<Location> matches = new ArrayList<>();
        for (Location l : repository.getAllLocations())
        {
            if (getLocalTime(l, m).getHour() == FIVE_PM_HOUR)
            {
                matches.add(l);
            }
        }

        return matches;
    }


    /**
     * Chooses one location at random from the given candidates.
     *
     * @param locations
     *            the candidates to choose from
     * @return one randomly chosen location, or an empty Optional when the
     *         list is null or empty
     */
    public Optional<Location> chooseRandomLocation(List<Location> locations)
    {
        if (locations == null || locations.isEmpty())
        {
            return Optional.empty();
        }

        return Optional.of(locations.get(random.nextInt(locations.size())));
    }


    /**
     * Converts an instant into the local date and time for a location.
     *
     * @param l
     *            the location whose zone should be used
     * @param m
     *            the instant to convert
     * @return the local date and time
     * @throws IllegalArgumentException
     *             if either argument is null
     */
    public ZonedDateTime getLocalTime(Location l, Instant m)
    {
        if (l == null)
        {
            throw new IllegalArgumentException("Location cannot be null");
        }

        if (m == null)
        {
            throw new IllegalArgumentException("moment cannot be null");
        }

        return m.atZone(l.getZoneId());
    }
}
