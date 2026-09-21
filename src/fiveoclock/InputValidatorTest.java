
package fiveoclock;

import java.io.PrintStream;
import static org.junit.Assert.*;
import java.time.LocalTime;
import java.time.ZoneId;
import student.TestCase;



/**
 * Tests the InputValidator class.
 *
 * @author Aidan Southwick
 * @version 2026.09.16
 */
public class InputValidatorTest extends TestCase {
    private InputValidator validator;

    /** 
     * Creates the validator used in every test.
     */
    public void setUp() {
        validator = new InputValidator();
    }
    
    /**
     * 12- hour input with an uppercase parses
     */
    public void testParseTimeTwelveHour() {
        assertEquals(LocalTime.of(17, 30), validator.parseTime("5:30 PM"));
    }
    
    /**
     * The meridiem is case insensitive
     */
    public void testParseTimeLowercaseMeridiem () {
        assertEquals(LocalTime.of(17, 30), validator.parseTime("5:30 pm"));
    }
    
    /**
     * 24 hr input parses
     */
    public void testParseTimeTwentyFourHour() {
        assertEquals(LocalTime.of(17, 30), validator.parseTime("17:30"));
    }
    
    /**
     * Morning times parse in both formats 
     */
    public void testParseTimeMorning() {
        assertEquals(LocalTime.of(9, 5),validator.parseTime("9:05 AM"));
        assertEquals(LocalTime.of(9, 5), validator.parseTime("09:05"));
    }
    
    /**
     * Midnight and noon parse correctly
     */
    public void testParseTimeMidnightAndNoon() {
        assertEquals(LocalTime.of(0, 0), validator.parseTime("12:00 AM"));
        assertEquals(LocalTime.of(12, 0), validator.parseTime("12:00 PM"));
        assertEquals(LocalTime.of(0, 0), validator.parseTime("00:00"));
    }
    
    /**
     * Extra surrounding or internal whitespace is tolerated
     */
    public void testParseTimeExtraWhitespace() {
        assertEquals(LocalTime.of(17, 30),
            validator.parseTime("   5:30    PM   "));
    }

    /**
     * A missing colon gets rejected
     */
    public void testParseTimeRejectsMissingColon() {
        assertParseTimeFails("530PM");
    }
    
    /**
     * letters are rejected
     */
    public void testParseTimeRejectsLetters() {
        assertParseTimeFails ("five o'clock");
    }
    
    /**
     * Negative numbers are rejected
     */
    public void testParseTimeRejectsNegative() {
        assertParseTimeFails("-5:30");
    }
    
    /**
     * Out of range hr and min are rejected
     */
    public void testParseTimeRejectsOutOfRange() {
        assertParseTimeFails("25:00");
        assertParseTimeFails("17:75");
        assertParseTimeFails("13:00 PM");
    }
    
    /**
     * Two times in one entry are not allowed
     */
    public void testParseTimeRejectsMultipleTimes() {
        assertParseTimeFails("5:30 PM 6:30 PM");
    }
    
    /** A bare time with no meridiem is ambiguous and rejected. */
    public void testParseTimeRejectsAmbiguousInput() {
        assertParseTimeFails("5:30");
    }

    /** Blank and null input are rejected. */
    public void testParseTimeRejectsBlank() {
        assertParseTimeFails("");
        assertParseTimeFails("    ");
        assertParseTimeFails(null);
    }

    /** A known zone name parses. */
    public void testParseZoneId() {
        assertEquals(ZoneId.of("America/New_York"),
            validator.parseZoneId("America/New_York"));
    }

    /** Surrounding whitespace is trimmed. */
    public void testParseZoneIdTrimsWhitespace() {
        assertEquals(ZoneId.of("Asia/Tokyo"),
            validator.parseZoneId("  Asia/Tokyo  "));
    }

    /** An unknown zone is rejected. */
    public void testParseZoneIdRejectsUnknownZone() {
        assertParseZoneFails("America/Blacksburg");
    }

    /** Zone names are case-sensitive, so wrong casing is rejected. */
    public void testParseZoneIdRejectsWrongCase() {
        assertParseZoneFails("america/new_york");
    }

    /** Extra characters are rejected. */
    public void testParseZoneIdRejectsExtraCharacters() {
        assertParseZoneFails("America/New_York!!");
    }

    /** Blank and null zones are rejected. */
    public void testParseZoneIdRejectsBlank() {
        assertParseZoneFails("");
        assertParseZoneFails("   ");
        assertParseZoneFails(null);
    }

    /** isBlank is true for null and whitespace only. */
    public void testIsBlank() {
        assertTrue(validator.isBlank(null));
        assertTrue(validator.isBlank(""));
        assertTrue(validator.isBlank("     "));
        assertTrue(validator.isBlank("\t\n"));
    }

    /** isBlank is false for real content. */
    public void testIsBlankFalseForContent() {
        assertFalse(validator.isBlank("17:30"));
        assertFalse(validator.isBlank("  x  "));
    }

    /** Helper asserting parseTime rejects the given text. */
    private void assertParseTimeFails(String text) {
        try {
            validator.parseTime(text);
            fail("Expected parseTime to reject: " + text);
        }
        catch (IllegalArgumentException e) {
            assertNotNull(e.getMessage());
        }
    }

    /** Helper asserting parseZoneId rejects the inputted text. */
    private void assertParseZoneFails(String text) {
        try {
            validator.parseZoneId(text);
            fail("Expected parseZoneId to reject: " + text);
        }
        catch (IllegalArgumentException e) {
            assertNotNull(e.getMessage());
        }
    }
}

    


