package zk.springboot.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import zk.springboot.bean.OTCDailyTrading;
import zk.springboot.bean.TWStockDailyTrading;

@Service("otcTradingService")
public class OTCDailyTradingService implements DailyTradingService {
    // 日 期 成交仟股 成交仟元 開盤 最高 最低 收盤 漲跌 筆數
    // {"stkNo":"5820","stkName":"\u65e5\u76db\u91d1","showListPriceNote":false,"showListPriceLink":false,"reportDate":"107\/01","iTotalRecords":22,"aaData":[["107\/01\/02","575","4,812","8.36","8.39","8.33","8.37","-0.01","168"],["107\/01\/03","1,187","9,964","8.37","8.41","8.37","8.41","0.04","279"],["107\/01\/04","861","7,235","8.43","8.43","8.39","8.40","-0.01","172"],["107\/01\/05","589","4,952","8.40","8.42","8.39","8.42","0.02","187"],["107\/01\/08","1,208","10,208","8.43","8.47","8.42","8.45","0.03","337"],["107\/01\/09","1,349","11,336","8.45","8.46","8.35","8.38","-0.07","414"],["107\/01\/10","1,496","12,497","8.38","8.42","8.32","8.33","-0.05","351"],["107\/01\/11","2,056","17,027","8.30","8.30","8.25","8.25","-0.08","512"],["107\/01\/12","787","6,537","8.30","8.33","8.25","8.30","0.05","355"],["107\/01\/15","1,665","13,981","8.33","8.44","8.33","8.44","0.14","559"],["107\/01\/16","868","7,323","8.47","8.47","8.41","8.44","0.00","407"],["107\/01\/17","1,359","11,473","8.43","8.47","8.40","8.47","0.03","494"],["107\/01\/18","3,449","29,573","8.49","8.65","8.48","8.59","0.12","1,171"],["107\/01\/19","1,338","11,521","8.67","8.68","8.59","8.59","0.00","627"],["107\/01\/22","1,843","15,794","8.62","8.62","8.54","8.55","-0.04","664"],["107\/01\/23","1,954","16,603","8.56","8.57","8.45","8.49","-0.06","694"],["107\/01\/24","1,390","11,790","8.49","8.50","8.46","8.47","-0.02","615"],["107\/01\/25","1,409","11,994","8.50","8.55","8.47","8.50","0.03","460"],["107\/01\/26","1,326","11,270","8.51","8.53","8.47","8.53","0.03","337"],["107\/01\/29","2,302","19,672","8.53","8.62","8.50","8.52","-0.01","590"],["107\/01\/30","3,198","26,878","8.49","8.49","8.34","8.37","-0.15","734"],["107\/01\/31","1,679","13,953","8.30","8.34","8.29","8.33","-0.04","605"]]}
    protected static final Logger logger = LoggerFactory.getLogger(OTCDailyTradingService.class);
    public static final String URL_OTC_DAILY_TRADING = "http://www.tpex.org.tw/web/stock/aftertrading/daily_trading_info/st43_result.php";
    @Autowired
    private ObjectMapper objectMapper = null;

    public List<TWStockDailyTrading> getDailyTrading(String stockNo, Date start) {
        List<TWStockDailyTrading> result = new ArrayList<TWStockDailyTrading>();
        URIBuilder builder = null;
        HttpResponse response = null;
        HttpClient client = HttpClientBuilder.create().build();

        try {
            builder = new URIBuilder(URL_OTC_DAILY_TRADING);
            // {'d': '%d/%d' % (year - 1911, month), 'stkno': sid}
            // ( "d", "107/01" ) ( "stkno", "5820" );
            builder.setParameter("d",
                    String.format("%1$d/%2$s", Integer.parseInt(DateFormatUtils.format(start, "yyyy")) - 1911, DateFormatUtils.format(start, "MM")))
                    .setParameter("stkno", stockNo);
            response = client.execute(new HttpGet(builder.build()));

            OTCDailyTrading otcDailyTrading =
                    objectMapper.readValue(IOUtils.toString(response.getEntity().getContent(), "UTF-8"), OTCDailyTrading.class);

            result = otcDailyTrading.getData().stream().map(new Function<List<String>, TWStockDailyTrading>() {
                @Override
                public TWStockDailyTrading apply(List<String> list) {
                    TWStockDailyTrading entity = new TWStockDailyTrading();
                    // String[] arr = StringUtils.split( s, DailyTradingService.SEPERATOR );
                    //
                    // entity.setTradingDate( arr[0] );
                    // entity.setTradingShares( arr[1] );
                    // entity.setTurnover( arr[2] );
                    // entity.setOpeningPrice( arr[3] );
                    // entity.setDayHigh( arr[4] );
                    // entity.setClosingPrice( arr[5] );
                    // entity.setDayLow( arr[6] );
                    // entity.setPriceDifference( arr[7] );
                    // entity.setTotalVolume( arr[8] );

                    entity.setTradingDate(list.get(0));
                    entity.setTradingShares(list.get(1));
                    entity.setTurnover(list.get(2));
                    entity.setOpeningPrice(list.get(3));
                    entity.setDayHigh(list.get(4));
                    entity.setClosingPrice(list.get(5));
                    entity.setDayLow(list.get(6));
                    entity.setPriceDifference(list.get(7));
                    entity.setTotalVolume(list.get(8));

                    return entity;
                }
            }).collect(Collectors.toList());

        } catch (Throwable cause) {
            logger.error(cause.getMessage(), cause);
        }

        return result;
    }

}
