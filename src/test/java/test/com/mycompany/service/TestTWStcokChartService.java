package test.com.mycompany.service;

import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Locale;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.zkoss.zul.SimpleHiLoModel;
import zk.springboot.Application;
import zk.springboot.service.StockType;
import zk.springboot.service.TWStcokChartService;
import zk.springboot.service.TWStockService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class TestTWStcokChartService {
    protected static final Logger logger = LoggerFactory.getLogger(TestTWStockService.class);
    @Autowired
    private TWStockService service = null;
    @Autowired
    private TWStcokChartService chartService = null;

    @Ignore
    @Test
    public void testDecimal() {
        try {
            NumberFormat format = NumberFormat.getInstance(Locale.getDefault());
            logger.info("{}", format.parse("1,789").doubleValue());
        } catch (Throwable cause) {
            logger.error(cause.getMessage(), cause);
        }
    }

    @Test
    public void testGetCandlestickChartModel() {
        try {
            Calendar start = Calendar.getInstance();
            start.add(Calendar.MONTH, -6);
            Calendar end = Calendar.getInstance();

            SimpleHiLoModel tseSimpleHiLoModel = chartService.getCandlestickChartModel(service.getDailyTrade(StockType.TSE.getType(), "1101", start.getTime(), end.getTime()));

            SimpleHiLoModel otcSimpleHiLoModel = chartService.getCandlestickChartModel(service.getDailyTrade(StockType.OTC.getType(), "5820", start.getTime(), end.getTime()));

            logger.info("tse : {}", tseSimpleHiLoModel.getDataCount());

            logger.info("otc : {}", otcSimpleHiLoModel.getDataCount());
        } catch (Throwable cause) {
            logger.error(cause.getMessage(), cause);
        }
    }
}
