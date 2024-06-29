package com.wingtrip.account.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class DateTimeUtil {

    public static LocalDate now() {
        return LocalDate.now(ZoneId.of("America/Mexico_City"));
    }

    public LocalDateTime nowDateTime() {
        return LocalDateTime.now(ZoneId.of("America/Mexico/Ciudad_De_Mexico"));
    }
}
