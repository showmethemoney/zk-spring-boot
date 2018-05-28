package test.com.mycompany;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestLocalDate {
    protected static final Logger logger = LoggerFactory.getLogger(TestLocalDate.class);

    @Test
    public void testLocal() {
        // Locale locale = Locale.getDefault(Locale.Category.FORMAT);
        // Chronology chrono = MinguoChronology.INSTANCE;
        // DateTimeFormatter df = new
        // DateTimeFormatterBuilder().parseLenient().appendPattern("yy/MM/dd").toFormatter().withChronology(chrono).withDecimalStyle(DecimalStyle.of(locale));
        // TemporalAccessor temporal = df.parse("107/06/23");
        // ChronoLocalDate cDate = chrono.date(temporal);
        // System.out.println(Date.from(LocalDate.from(cDate).atStartOfDay(ZoneId.of("UTC+8")).toInstant()));

    }
}
