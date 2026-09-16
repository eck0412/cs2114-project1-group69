package fiveoclock;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Stores all the locations and organizes them by time zone
 * 
 * @author Vivaan Dutt
 * @version 09/16/2026
 * 
 **/

public class LocationRepository {

    private final Map<ZoneId, List<Location>> locationsByZone;
    
}