package zk.springboot.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;
import zk.springboot.bean.StockRecord;
import zk.springboot.bean.TWIndustry;
import zk.springboot.bean.TWIndustryStock;
import zk.springboot.bean.TWStock;
import zk.springboot.bean.TWStockDailyTrading;
import zk.springboot.bean.TWStockPrice;
import zk.springboot.bean.TWStockPriceInfo;

@Service("stockService")
public class TWStockService {
    protected static final Logger logger = LoggerFactory.getLogger(TWStockService.class);
    public static final String URL_GET_INDUSTRY = "http://mis.twse.com.tw/stock/api/getIndustry.jsp";
    public static final String URL_GET_STOCKS_BY_INDUSTRY = "http://mis.twse.com.tw/stock/api/getCategory.jsp?ex=%s&i=%s";
    public static final String URL_TWSE_INDEX = "http://mis.twse.com.tw/stock/index.jsp";
    // http://mis.twse.com.tw/stock/api/getStockInfo.jsp?ex_ch=tse_2330.tw&_=
    public static final String URL_GET_STOCK_PRICE = "http://mis.twse.com.tw/stock/api/getStockInfo.jsp?ex_ch=%1$s&_=%2$s";
    public static final String URL_TSE_DAILY_TRADING = "http://www.twse.com.tw/exchangeReport/STOCK_DAY";
    public static final String URL_OTC_DAILY_TRADING = "http://www.tpex.org.tw/web/stock/aftertrading/daily_trading_info/st43_result.php";
    protected static final String KEY_CODE = "code";
    protected static final String KEY_NAME = "name";

    @Autowired
    private RestTemplate restTemplate = null;
    @Autowired
    private ObjectMapper objectMapper = null;
    @Autowired
    private OTCDailyTradingService otcTradingService = null;
    @Autowired
    private TSEDailyTradingService tseTradingService = null;

    public List<TWStockDailyTrading> getDailyTrade(String type, String stock, Date start, Date end) {
        List<TWStockDailyTrading> result = new ArrayList<TWStockDailyTrading>();

        try {
            DailyTradingService dailyTradingService = StockType.TSE.getType().equalsIgnoreCase(type) ? tseTradingService : otcTradingService;

            // set day of month = 01
            Calendar startCalendar = Calendar.getInstance();
            startCalendar.setTime(start);
            startCalendar.set(Calendar.DAY_OF_MONTH, 1);

            Calendar endCalendar = Calendar.getInstance();
            endCalendar.setTime(end);
            endCalendar.set(Calendar.DAY_OF_MONTH, 1);

            while (startCalendar.before(endCalendar)) {
                result.addAll(dailyTradingService.getDailyTrading(stock, startCalendar.getTime()));

                startCalendar.add(Calendar.MONTH, 1);
            }

        } catch (Throwable cause) {
            logger.error(cause.getMessage(), cause);
        }

        return result;
    }

    public List<TWStock> getIndustryStocksByType(String type, String industry) {
        TWIndustryStock result = null;

        try {
            // {"msgArray":[{"ex":"tse","nf":"彰化商業銀行股份有限公司","n":"彰銀","ch":"2801.tw","key":"tse_2801.tw_20180331"}]
            String response = restTemplate.getForEntity(String.format(URL_GET_STOCKS_BY_INDUSTRY, type, industry), String.class).getBody();

            result = objectMapper.readValue(response, TWIndustryStock.class);
        } catch (Throwable cause) {
            logger.error(cause.getMessage(), cause);
        }

        return result.getStocks();
    }

    // type = tse, otc
    public List<TWIndustry> getIndustry(String type) {
        List<TWIndustry> result = null;

        try {
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(URL_GET_INDUSTRY, String.class);

            // {"tse":[{"name":"封閉式基金","code":"A0"}],"otc":[{"name":"ETF","code":"B0"}]}
            String response = responseEntity.getBody();

            Map<String, List<Map<String, String>>> industry = objectMapper.readValue(response, Map.class);

            List<TWIndustry> tse = new ArrayList<TWIndustry>();
            List<TWIndustry> otc = new ArrayList<TWIndustry>();

            industry.keySet().stream().forEach(k -> {
                industry.get(k).stream().forEach(m -> {
                    String name = m.get(KEY_NAME);
                    // String value = k + "." + m.get( KEY_CODE );
                    String value = m.get(KEY_CODE);

                    if (StockType.TSE.getType().equalsIgnoreCase(k)) {
                        tse.add(new TWIndustry(name, value));
                    } else {
                        otc.add(new TWIndustry(name, value));
                    }
                });
            });

            result = StockType.TSE.getType().equalsIgnoreCase(type) ? tse : otc;
        } catch (Throwable cause) {
            logger.error(cause.getMessage(), cause);
        }

        return result;
    }

    public TWStockPrice getTodayStockPrice(String stock) {
        TWStockPrice result = null;
        HttpGet request = null;
        HttpResponse response = null;

        try {
            HttpClient client = HttpClientBuilder.create().build();

            request = new HttpGet(URL_TWSE_INDEX);
            response = client.execute(request);

            request = new HttpGet(String.format(URL_GET_STOCK_PRICE, stock, Calendar.getInstance().getTimeInMillis()));
            response = client.execute(request);
            // {"msgArray":[{"ts":"0","fv":"23","tk0":"2330.tw_tse_20180331_B_9999331832","tk1":"2330.tw_tse_20180331_B_9999312753","oa":"248.50","ob":"248.00","tlong":"1522477800000","ot":"14:30:00","f":"14_161_394_813_784_","ex":"tse","g":"101_189_356_149_315_","ov":"11912","d":"20180331","it":"12","b":"247.00_246.50_246.00_245.50_245.00_","c":"2330","mt":"000000","a":"247.50_248.00_248.50_249.00_249.50_","n":"台積電","o":"247.50","l":"246.50","oz":"248.00","h":"249.00","ip":"0","i":"24","w":"221.50","v":"3569","u":"270.50","t":"13:30:00","s":"110","pz":"247.50","tv":"110","p":"0","nf":"台灣積體電路製造股份有限公司","ch":"2330.tw","z":"247.50","y":"246.00","ps":"106"}],"userDelay":5000,"rtmessage":"OK","referer":"","queryTime":{"sysTime":"09:32:27","sessionLatestTime":-1,"sysDate":"20180401","sessionKey":"tse_2330.tw_20180331|","sessionFromTime":-1,"stockInfoItem":910,"showChart":false,"sessionStr":"UserSession","stockInfo":115760},"rtcode":"0000"}
            
            TWStockPriceInfo stockPriceInfo = objectMapper.readValue(StringUtils.trim(IOUtils.toString(response.getEntity().getContent(), "UTF-8")), TWStockPriceInfo.class);

            if (null != stockPriceInfo.getStockPrices() && !stockPriceInfo.getStockPrices().isEmpty()) {
                result = stockPriceInfo.getStockPrices().get(0);

                String[] waitForBuyCounts = result.getWaitForBuyCounts().split("_");
                String[] waitForBuyPrices = result.getWaitForBuyPrices().split("_");
                String[] waitForSellcounts = result.getWaitForSellCounts().split("_");
                String[] waitForSellPrices = result.getWaitForSellPrices().split("_");

                List<StockRecord> waitForBuys = new ArrayList<StockRecord>();
                List<StockRecord> waitForSell = new ArrayList<StockRecord>();

                // waitForBuyCounts <-> waitForBuyPrices 資料從前面開始呈現
                for (int i = 0; i < waitForBuyCounts.length; i++) {
                    String waitForBuyCount = waitForBuyCounts[i];
                    String waitForBuyPrice = waitForBuyPrices[i];

                    if (StringUtils.isNotBlank(waitForBuyCount) && StringUtils.isNotBlank(waitForBuyPrice)) {
                        waitForBuys.add(new StockRecord(waitForBuyPrice, waitForBuyCount));
                    }
                }

                // waitForSellCounts <-> waitForSellPrices 資料由後面開始呈現
                for (int i = 0; i < waitForSellcounts.length; i++) {
                    String waitForSellcount = waitForSellcounts[i];
                    String waitForSellPrice = waitForSellPrices[i];

                    if (StringUtils.isNotBlank(waitForSellcount) && StringUtils.isNotBlank(waitForSellPrice)) {
                        waitForSell.add(new StockRecord(waitForSellPrice, waitForSellcount));
                    }
                }
                Collections.reverse(waitForSell); // reverse order

                result.setWaitForBuys(waitForBuys);
                result.setWaitForSells(waitForSell);
            }
        } catch (Throwable cause) {
            logger.error(cause.getMessage(), cause);
        }

        return result;
    }

}
