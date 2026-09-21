package fiveoclock;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Optional;
/**
 * // -------------------------------------------------------------------------
/**
 *  Write a one-sentence summary of your class here.
 *  Follow it with additional details about its purpose, what abstraction
 *  it represents, and how to use it.
 * 
 *  @author connorbo
 *  @version Sep 16, 2026
 */



public class TimeFinderService
{
    private static final int FIVE_PM_HOUR = 0;
    //~ Fields ................................................................
    private final LocationRepository repository;
    private final Random random;
    
    // ----------------------------------------------------------
    /**
     * Create a new TimeFinderService object.
     * @param repository the source of locations, cannot be null 
     */
    //~ Constructors ..........................................................
    public TimeFinderService(LocationRepository repository) {
        this(repository, new Random());
    }
    //~Public  Methods ........................................................
    // ----------------------------------------------------------
    /**
     * Create a new TimeFinderService object.
     * @param rep the source of saved locations, should not be null(i willbesad)
     * @param rand randomizer
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
    // ----------------------------------------------------------
    /**
     * Place a description of your method here.
     * @param m where to evaluate
     * @return the matching locations, could be empty
     */
    public List<Location>findLocationsAtFive(Instant m){
        if (m == null)
        {
            throw new IllegalArgumentException("Moment must not be null");

        }
        
        List<Location> matches = new ArrayList<>();
        for (Location l : repository.getAllLocations()) {
            if (getLocalTime(l, m).getHour() == FIVE_PM_HOUR)
            {
                matches.add(l);
            }
        }
        
        return matches;
            
    }
    
    // ----------------------------------------------------------
    /**
     * Place a description of your method here.
     * @param locations the candidate can choose from
     * @return  one randomly chosen location, or a empty one if null
     */
    public Optional<Location> chooseRandomLocation(List<Location> locations){
        if (locations == null || locations.isEmpty()) {
            return Optional.empty();
        }
        
        return Optional.of(locations.get(random.nextInt(locations.size())));
    }
    
    // ----------------------------------------------------------
    /**
     * Place a description of your method here.
     * @param l location whose zone should be used
     * @param m the instant used to convert
     * @return  the local date and time
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
