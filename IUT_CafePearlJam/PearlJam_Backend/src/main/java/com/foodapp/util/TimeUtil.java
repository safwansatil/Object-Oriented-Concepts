package com.foodapp.util;

import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Handles ISO-8601 formatting and time comparisons.
 */
public final class TimeUtil {
    private static final DateTimeFormatter ISO_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    private TimeUtil() {}

    public static String nowISO() {
        return ZonedDateTime.now(ZoneId.of("UTC")).format(ISO_FORMATTER);
    }

    public static LocalTime parseTime(String time) {
        return LocalTime.parse(time, TIME_FORMATTER);
    }

    public static boolean isCurrentlyOpen(String opensAt, String closesAt) {
        LocalTime now = LocalTime.now(ZoneId.of("UTC")); // Adjust zone if needed
        LocalTime openTime = parseTime(opensAt);
        LocalTime closeTime = parseTime(closesAt);
        
        if (closeTime.isBefore(openTime)) { // Handle overnight hours
            return now.isAfter(openTime) || now.isBefore(closeTime);
        }
        return now.isAfter(openTime) && now.isBefore(closeTime);
    }
}
