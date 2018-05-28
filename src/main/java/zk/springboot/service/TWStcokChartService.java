package zk.springboot.service;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.Chronology;
import java.time.chrono.MinguoChronology;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DecimalStyle;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.zkoss.zul.SimpleHiLoModel;
import zk.springboot.bean.TWStockDailyTrading;

@Service("stockChartService")
public class TWStcokChartService {
    protected static final Logger logger = LoggerFactory.getLogger(TWStcokChartService.class);

    public SimpleHiLoModel getCandlestickChartModel(List<TWStockDailyTrading> twStockDailyTradings) {
        SimpleHiLoModel result = null;

        try {
            NumberFormat format = NumberFormat.getInstance(Locale.getDefault());

            Iterator<TWStockDailyTrading> iterator = twStockDailyTradings.iterator();
            result = new SimpleHiLoModel();
            TWStockDailyTrading trading = null;

            Locale locale = Locale.getDefault(Locale.Category.FORMAT);
            Chronology chrono = MinguoChronology.INSTANCE;
            DateTimeFormatter df = new DateTimeFormatterBuilder().parseLenient().appendPattern("yy/MM/dd").toFormatter().withChronology(chrono).withDecimalStyle(DecimalStyle.of(locale));

            while (iterator.hasNext()) {
                trading = (TWStockDailyTrading) iterator.next();

                TemporalAccessor temporal = df.parse(trading.getTradingDate());
                ChronoLocalDate localDate = chrono.date(temporal);

                result.addValue(Date.from(LocalDate.from(localDate).atStartOfDay(ZoneId.of("UTC+8")).toInstant()), Double.parseDouble(trading.getOpeningPrice()),
                        Double.parseDouble(trading.getDayHigh()), Double.parseDouble(trading.getDayLow()), Double.parseDouble(trading.getClosingPrice()),
                        format.parse(trading.getTotalVolume()).doubleValue());
            }
        } catch (Throwable cause) {
            logger.error(cause.getMessage(), cause);
        }

        return result;
    }
}
