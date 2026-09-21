package fiveoclock;

import java.time.LocalTime;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.Locale;


 /**
  * Checks and then converts the user's tie and time-zone input
  * 
  * @author Aidan Southwick
  * @version 2026.09.16
  */
public class InputValidator {
    private final DateTimeFormatter twelveHourFormat;
    private final DateTimeFormatter twentyFourHourFormat;
    
    /**
     * Prepares the supported 12 hr and 24 hr time formats
     */
 public InputValidator() {
    this.twelveHourFormat = new DateTimeFormatterBuilder()
        .parseCaseInsensitive()
        .appendPattern("h:mm a")
        .toFormatter(Locale.US);
    this.twentyFourHourFormat = new DateTimeFormatterBuilder()
        .appendPattern("HH:mm")
        .toFormatter(Locale.US);
    }
 
 /**
  * Phrases text such as "5:30 PM" or "5:30pm", or  "17:30"
  * 
  * @param text the users input
  * @return the time it represents
  * @throws IllegalArgumentException if the text is blank or isnt a time 
  * in right format
  * 
  */
 public LocalTime parseTime(String text) {
     if (isBlank(text)) {
         throw new IllegalArgumentException("Time must not be blank.");
     }
     String cleaned = text.trim().replaceAll("\\s+", " ");


try {
    return LocalTime.parse(cleaned, twentyFourHourFormat);
}
catch (DateTimeParseException ignored) {
    // Not 24-hour form fall through and try the 12-hour form.
}
try {
    return LocalTime.parse(cleaned, twelveHourFormat);
}
catch (DateTimeParseException e) {
    throw new IllegalArgumentException(
        "'" + text + "' is not a valid time.", e);
}
 }
 
/**
 * @param text a time-zone name such as "America/New_York"
 * @return the matching ZoneId
 * @throws IllegalArgumentException if the text is blank or not a known time zone
 * 
 */
public ZoneId parseZoneId(String text) {
    if (isBlank(text)) {
        throw new IllegalArgumentException("Time zone must not be blank");
    }
    String cleaned = text.trim();
    if (!ZoneId.getAvailableZoneIds().contains(cleaned)) {
        throw new IllegalArgumentException(
            "'" + text + "' is not a known time zone ");
    }
    return ZoneId.of(cleaned);
        
    }
/**
 * @param text the text to check
 * @return true when text is null or contains only whitespace
 */
public boolean isBlank(String text) {
    return text == null || text.trim().isEmpty();
}
}






