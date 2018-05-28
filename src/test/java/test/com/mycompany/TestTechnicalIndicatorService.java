package test.com.mycompany;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.Chronology;
import java.time.chrono.MinguoChronology;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DecimalStyle;
import java.time.temporal.TemporalAccessor;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.ta4j.core.BaseBar;
import org.ta4j.core.BaseTimeSeries;
import org.ta4j.core.TimeSeries;
import org.ta4j.core.indicators.EMAIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import zk.springboot.Application;
import zk.springboot.bean.TWStockDailyTrading;
import zk.springboot.service.StockType;
import zk.springboot.service.TWStockService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class TestTechnicalIndicatorService {
    protected static final Logger logger = LoggerFactory.getLogger(TestTechnicalIndicatorService.class);
    @Autowired
    private TWStockService service = null;

    @Ignore
    @Test
    public void testZonedDateTime() throws ParseException {
        ZonedDateTime current = ZonedDateTime.now();
        logger.info("{}", current);
        current = ZonedDateTime.of(LocalDateTime.of(2018, 01, 02, 00, 00, 00, 00), ZoneId.of("Asia/Taipei"));
        logger.info("{}", current);

        DecimalFormat format = new DecimalFormat("#,###,###.##");
        logger.info("{}", format.parse("1,264.5"));

        // NumberFormat nf_in = NumberFormat.getNumberInstance( Locale.TAIWAN );
        // logger.info( "!!! {}", nf_in.parse("1,264").doubleValue() );
    }

    protected List<TWStockDailyTrading> getDailyTradings() {
        List<TWStockDailyTrading> result = null;

        try {
            Calendar start = Calendar.getInstance();
            start.add(Calendar.MONTH, -6);
            Calendar end = Calendar.getInstance();

            result = service.getDailyTrade(StockType.TSE.getType(), "1101", start.getTime(), end.getTime());
        } catch (Throwable cause) {
            logger.error(cause.getMessage(), cause);
        }

        return result;
    }

    // @Ignore
    @Test
    public void testTechnicalIndicator() {
        try {
            List<TWStockDailyTrading> tradings = getDailyTradings();

            ZonedDateTime zonedDateTime = null;
            DecimalFormat format = new DecimalFormat("#,###,###.##");
            TimeSeries series = new BaseTimeSeries();

            Locale locale = Locale.getDefault(Locale.Category.FORMAT);
            Chronology chrono = MinguoChronology.INSTANCE;
            DateTimeFormatter df = new DateTimeFormatterBuilder().parseLenient().appendPattern("yy/MM/dd").toFormatter().withChronology(chrono).withDecimalStyle(DecimalStyle.of(locale));

            for (TWStockDailyTrading trading : tradings) {
                TemporalAccessor temporal = df.parse(trading.getTradingDate());
                ChronoLocalDate localDate = chrono.date(temporal);
                // ;
                zonedDateTime = ZonedDateTime.ofInstant(LocalDate.from(localDate).atStartOfDay(ZoneId.of("UTC+8")).toInstant(), ZoneId.of("UTC+8"));
                // String[] tradingDateArr = trading.getTradingDate().split("/");

                // zonedDateTime = ZonedDateTime.of(LocalDateTime.of(1911 + Integer.valueOf(tradingDateArr[0]),
                // Integer.valueOf(tradingDateArr[1]),
                // Integer.valueOf(tradingDateArr[2]), 00, 00, 00, 00), ZoneId.of("Asia/Taipei"));
                series.addBar(new BaseBar(zonedDateTime, format.parse(trading.getOpeningPrice()).toString(), format.parse(trading.getDayHigh()).toString(),
                        format.parse(trading.getDayLow()).toString(), format.parse(trading.getClosingPrice()).toString(), format.parse(trading.getTotalVolume()).toString()));
            }

            ClosePriceIndicator closePrice = new ClosePriceIndicator(series);
            // Exponential moving averages
            EMAIndicator shortEma = new EMAIndicator(closePrice, 8);
            EMAIndicator longEma = new EMAIndicator(closePrice, 20);

            for (int i = 0; i < series.getBarCount(); i++) {
                logger.info("time series : {}, close price : {}, EMA Indicator [8] : {}, EMA Indicator  [20] : {}", series.getBar(i).getEndTime(), closePrice.getValue(i), shortEma.getValue(i),
                        longEma.getValue(i));
            }
        } catch (Throwable cause) {
            logger.error(cause.getMessage(), cause);
        }
    }
}
