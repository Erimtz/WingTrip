package com.wingtrip.user.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class DateTimeUtil {

    public static LocalDate now() {
        return LocalDate.now(ZoneId.of("America/Mexico/Ciudad_De_Mexico"));
    }

    public static LocalDateTime nowDateTime() {
        return LocalDateTime.now(ZoneId.of("America/Mexico/Ciudad_De_Mexico"));
    }
}
