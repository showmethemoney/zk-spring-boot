package test.com.mycompany.service;

import static org.junit.Assert.assertNotNull;
import java.util.Calendar;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import zk.springboot.Application;
import zk.springboot.bean.TWStockPrice;
import zk.springboot.service.StockType;
import zk.springboot.service.TWStockService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class TestTWStockService {
    protected static final Logger logger = LoggerFactory.getLogger(TestTWStockService.class);
    @Autowired
    private TWStockService service = null;

    @Ignore
    @Test
    public void testGetIndustry() {
        assertNotNull(service);

        service.getIndustry(StockType.TSE.getType()).stream().forEach(e -> {
            logger.info("{} ===> name : {}, code : {}", StockType.TSE.getType(), e.getName(), e.getCode());
        });

        service.getIndustry(StockType.OTC.getType()).stream().forEach(e -> {
            logger.info("{} ===> name : {}, code : {}", StockType.OTC.getType(), e.getName(), e.getCode());
        });
    }

    @Ignore
    @Test
    public void testGetIndustryStocksByType() {
        assertNotNull(service);

        service.getIndustryStocksByType(StockType.TSE.getType(), "01").stream().forEach(e -> {
            logger.info("{} ===> name : {}, fullname : {}, industryCode : {}", StockType.TSE.getType(), e.getName(), e.getFullName(), e.getIndustryCode());
        });

        service.getIndustryStocksByType(StockType.OTC.getType(), "17").stream().forEach(e -> {
            logger.info("{} ===> name : {}, fullname : {}, industryCode : {}", StockType.OTC.getType(), e.getName(), e.getFullName(), e.getIndustryCode());
        });
    }

    @Ignore
    @Test
    public void testGetTodayStockPrice() {
        assertNotNull(service);
        // tse_2330.tw
        // otc_5820.tw

        TWStockPrice price = service.getTodayStockPrice("tse_2330.tw");
        logger.info("tse : {}", price);

        price.getWaitForBuys().stream().forEach(o -> {
            logger.info("wait for buy : {} {}", o.getCount(), o.getPrice());
        });

        price.getWaitForSells().stream().forEach(o -> {
            logger.info("wait for sell : {} {}", o.getCount(), o.getPrice());
        });
    }

    // @Ignore
    @Test
    public void testGetDailyTrade() {
        Calendar start = Calendar.getInstance();
        start.add(Calendar.MONTH, -1);
        Calendar end = Calendar.getInstance();

        // service.getDailyTrade(StockType.TSE.getType(), "1101", start.getTime(),
        // end.getTime()).stream().forEach(e -> {
        // logger.info("{} --> getOpeningPrice: {}, getDayHigh: {}, getDayLow: {}, getClosingPrice: {}",
        // StockType.TSE.getType(),
        // e.getOpeningPrice(),
        // e.getDayHigh(),
        // e.getDayLow(),
        // e.getClosingPrice());
        // });

        service.getDailyTrade(StockType.OTC.getType(), "5820", start.getTime(), end.getTime()).stream().forEach(e -> {
            logger.info("{} --> getOpeningPrice: {}, getDayHigh: {}, getDayLow: {}, getClosingPrice: {}", StockType.TSE.getType(), e.getOpeningPrice(), e.getDayHigh(), e.getDayLow(),
                    e.getClosingPrice());
        });
    }

}
