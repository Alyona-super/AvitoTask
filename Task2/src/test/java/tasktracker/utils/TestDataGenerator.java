package tasktracker.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TestDataGenerator {

    public static String generateUniqueTaskName() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS");
        return "ТестоваяЗадача_" + LocalDateTime.now().format(formatter);
    }

    public static String generateSearchTerm() {
        return "УникальнаяЗадача_" + System.currentTimeMillis();
    }
}