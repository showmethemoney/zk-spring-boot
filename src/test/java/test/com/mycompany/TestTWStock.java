package test.com.mycompany;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;
import zk.springboot.bean.TWIndustry;
import zk.springboot.bean.TWStockPrice;
import zk.springboot.bean.TWStockPriceInfo;
import zk.springboot.service.StockType;

public class TestTWStock {
    protected static final Logger logger = LoggerFactory.getLogger(TestTWStock.class);
    public static final String URL_GET_INDUSTRY = "http://mis.twse.com.tw/stock/api/getIndustry.jsp";
    public static final String URL_GET_STOCKS_BY_INDUSTRY = "http://mis.twse.com.tw/stock/api/getCategory.jsp?ex=%s&i=%s";
    public static final String KEY_TSE = "tse";
    public static final String KEY_OTC = "otc";
    public static final String KEY_CODE = "code";
    public static final String KEY_NAME = "name";

    @Ignore
    @Test
    public void testCalendar() {
        Calendar calendar = Calendar.getInstance();

        logger.info(DateFormatUtils.format(calendar, "yyyyMM"));
    }

    @Ignore
    @Test
    public void testEnum() {
        logger.info(StockType.TSE.getName());
        logger.info(StockType.TSE.getType());

        for (StockType type : StockType.values()) {
            logger.info(type.getType());
        }
    }

    @Ignore
    @Test
    public void testGetStocksByIndustry() {
        try {
            // {"msgArray":[{"ex":"tse","nf":"彰化商業銀行股份有限公司","n":"彰銀","ch":"2801.tw","key":"tse_2801.tw_20180331"},{"ex":"tse","nf":"京城商業銀行股份有限公司","n":"京城銀","ch":"2809.tw","key":"tse_2809.tw_20180331"},{"ex":"tse","nf":"台中商業銀行股份有限公司","n":"台中銀","ch":"2812.tw","key":"tse_2812.tw_20180331"},{"ex":"tse","nf":"旺旺友聯產物保險股份有限公司","n":"旺旺保","ch":"2816.tw","key":"tse_2816.tw_20180331"},{"ex":"tse","nf":"中華票券金融股份有限公司","n":"華票","ch":"2820.tw","key":"tse_2820.tw_20180331"},{"ex":"tse","nf":"中國人壽保險股份有限公司","n":"中壽","ch":"2823.tw","key":"tse_2823.tw_20180331"},{"ex":"tse","nf":"台灣產物保險股份有限公司","n":"台產","ch":"2832.tw","key":"tse_2832.tw_20180331"},{"ex":"tse","nf":"臺灣中小企業銀行股份有限公司","n":"臺企銀","ch":"2834.tw","key":"tse_2834.tw_20180331"},{"ex":"tse","nf":"高雄銀行股份有限公司","n":"高雄銀","ch":"2836.tw","key":"tse_2836.tw_20180331"},{"ex":"tse","nf":"聯邦商業銀行股份有限公司","n":"聯邦銀","ch":"2838.tw","key":"tse_2838.tw_20180331"},{"ex":"tse","nf":"聯邦銀行股份有限公司甲種特別股","n":"聯邦銀甲特","ch":"2838A.tw","key":"tse_2838A.tw_20180331"},{"ex":"tse","nf":"遠東國際商業銀行股份有限公司","n":"遠東銀","ch":"2845.tw","key":"tse_2845.tw_20180331"},{"ex":"tse","nf":"安泰商業銀行股份有限公司","n":"安泰銀","ch":"2849.tw","key":"tse_2849.tw_20180331"},{"ex":"tse","nf":"新光產物保險股份有限公司","n":"新產","ch":"2850.tw","key":"tse_2850.tw_20180331"},{"ex":"tse","nf":"中央再保險股份有限公司","n":"中再保","ch":"2851.tw","key":"tse_2851.tw_20180331"},{"ex":"tse","nf":"第一產物保險股份有限公司","n":"第一保","ch":"2852.tw","key":"tse_2852.tw_20180331"},{"ex":"tse","nf":"統一綜合證券股份有限公司","n":"統一證","ch":"2855.tw","key":"tse_2855.tw_20180331"},{"ex":"tse","nf":"元富證券股份有限公司","n":"元富證","ch":"2856.tw","key":"tse_2856.tw_20180331"},{"ex":"tse","nf":"三商美邦人壽保險股份有限公司","n":"三商壽","ch":"2867.tw","key":"tse_2867.tw_20180331"},{"ex":"tse","nf":"華南金融控股股份有限公司","n":"華南金","ch":"2880.tw","key":"tse_2880.tw_20180331"},{"ex":"tse","nf":"富邦金融控股股份有限公司","n":"富邦金","ch":"2881.tw","key":"tse_2881.tw_20180331"},{"ex":"tse","nf":"富邦金甲種特別股","n":"富邦特","ch":"2881A.tw","key":"tse_2881A.tw_20180331"},{"ex":"tse","nf":"國泰金融控股股份有限公司","n":"國泰金","ch":"2882.tw","key":"tse_2882.tw_20180331"},{"ex":"tse","nf":"國泰金甲種特別股","n":"國泰特","ch":"2882A.tw","key":"tse_2882A.tw_20180331"},{"ex":"tse","nf":"中華開發金融控股股份有限公司","n":"開發金","ch":"2883.tw","key":"tse_2883.tw_20180331"},{"ex":"tse","nf":"玉山金融控股股份有限公司","n":"玉山金","ch":"2884.tw","key":"tse_2884.tw_20180331"},{"ex":"tse","nf":"元大金融控股股份有限公司","n":"元大金","ch":"2885.tw","key":"tse_2885.tw_20180331"},{"ex":"tse","nf":"兆豐金融控股股份有限公司","n":"兆豐金","ch":"2886.tw","key":"tse_2886.tw_20180331"},{"ex":"tse","nf":"台新金融控股股份有限公司","n":"台新金","ch":"2887.tw","key":"tse_2887.tw_20180331"},{"ex":"tse","nf":"台新金戊種特別股","n":"台新戊特","ch":"2887E.tw","key":"tse_2887E.tw_20180331"},{"ex":"tse","nf":"新光金融控股股份有限公司","n":"新光金","ch":"2888.tw","key":"tse_2888.tw_20180331"},{"ex":"tse","nf":"國票金融控股股份有限公司","n":"國票金","ch":"2889.tw","key":"tse_2889.tw_20180331"},{"ex":"tse","nf":"永豐金融控股股份有限公司","n":"永豐金","ch":"2890.tw","key":"tse_2890.tw_20180331"},{"ex":"tse","nf":"中國信託金融控股股份有限公司","n":"中信金","ch":"2891.tw","key":"tse_2891.tw_20180331"},{"ex":"tse","nf":"中國信託金融控股股份有限公司乙種特別股","n":"中信金乙特","ch":"2891B.tw","key":"tse_2891B.tw_20180331"},{"ex":"tse","nf":"第一金融控股股份有限公司","n":"第一金","ch":"2892.tw","key":"tse_2892.tw_20180331"},{"ex":"tse","nf":"臺灣工業銀行股份有限公司","n":"王道銀行","ch":"2897.tw","key":"tse_2897.tw_20180331"},{"ex":"tse","nf":"合作金庫金融控股股份有限公司","n":"合庫金","ch":"5880.tw","key":"tse_5880.tw_20180331"},{"ex":"tse","nf":"群益金鼎證券股份有限公司","n":"群益證","ch":"6005.tw","key":"tse_6005.tw_20180331"},{"ex":"tse","nf":"群益期貨股份有限公司","n":"群益期","ch":"6024.tw","key":"tse_6024.tw_20180331"}],"rtmessage":"OK","queryTime":{"stockDetail":4151,"totalMicroTime":4151},"rtcode":"0000","size":40}
            RestTemplate template = new RestTemplate();
            String response = template.getForEntity(String.format(URL_GET_STOCKS_BY_INDUSTRY, KEY_TSE, "17"), String.class).getBody();
            logger.info(response);
        } catch (Throwable cause) {
            logger.error(cause.getMessage(), cause);
        }
    }

    @Ignore
    @Test
    public void testGetIndustry() {
        try {
            RestTemplate template = new RestTemplate();
            ObjectMapper objectMapper = new ObjectMapper();

            ResponseEntity<String> responseEntity = template.getForEntity(URL_GET_INDUSTRY, String.class);

            String response = responseEntity.getBody();

            logger.info(response);

            Map<String, List<Map<String, String>>> industry = objectMapper.readValue(response, Map.class);

            List<TWIndustry> tse = new ArrayList<TWIndustry>();
            List<TWIndustry> otc = new ArrayList<TWIndustry>();

            industry.keySet().stream().forEach(k -> {
                industry.get(k).stream().forEach(m -> {
                    String name = m.get(KEY_CODE);
                    String value = k + "." + m.get(KEY_CODE);

                    if (KEY_TSE.equalsIgnoreCase(k)) {
                        tse.add(new TWIndustry(name, value));
                    } else {
                        otc.add(new TWIndustry(name, value));
                    }
                });
            });

        } catch (Throwable cause) {
            logger.error(cause.getMessage(), cause);
        }
    }

//    @Ignore
    @Test
    public void testGetStockInfo() {
        HttpGet request = null;
        HttpResponse response = null;
        try {
            // BasicCookieStore cookieStore = new BasicCookieStore();
            HttpClient client = HttpClientBuilder.create().build();

            request = new HttpGet("http://mis.twse.com.tw/stock/index.jsp");

            response = client.execute(request);

            request = new HttpGet("http://mis.twse.com.tw/stock/api/getStockInfo.jsp?ex_ch=tse_2881.tw&_=" + Calendar.getInstance().getTimeInMillis());
            response = client.execute(request);

            // {"msgArray":[{"ts":"0","fv":"23","tk0":"2330.tw_tse_20180331_B_9999331832","tk1":"2330.tw_tse_20180331_B_9999312753","oa":"248.50","ob":"248.00","tlong":"1522477800000","ot":"14:30:00","f":"14_161_394_813_784_","ex":"tse","g":"101_189_356_149_315_","ov":"11912","d":"20180331","it":"12","b":"247.00_246.50_246.00_245.50_245.00_","c":"2330","mt":"000000","a":"247.50_248.00_248.50_249.00_249.50_","n":"台積電","o":"247.50","l":"246.50","oz":"248.00","h":"249.00","ip":"0","i":"24","w":"221.50","v":"3569","u":"270.50","t":"13:30:00","s":"110","pz":"247.50","tv":"110","p":"0","nf":"台灣積體電路製造股份有限公司","ch":"2330.tw","z":"247.50","y":"246.00","ps":"106"}],"userDelay":5000,"rtmessage":"OK","referer":"","queryTime":{"sysTime":"09:32:27","sessionLatestTime":-1,"sysDate":"20180401","sessionKey":"tse_2330.tw_20180331|","sessionFromTime":-1,"stockInfoItem":910,"showChart":false,"sessionStr":"UserSession","stockInfo":115760},"rtcode":"0000"}
            // logger.info( StringUtils.trim( IOUtils.toString( response.getEntity().getContent(), "UTF-8" ) )
            // );

            TWStockPriceInfo stockPriceInfo = new ObjectMapper()
                    .readValue(StringUtils.trim(IOUtils.toString(response.getEntity().getContent(), "UTF-8")), TWStockPriceInfo.class);

            for (TWStockPrice price : stockPriceInfo.getStockPrices()) {
                logger.info(price.getPriceDifference());
                logger.info(price.getPriceDifferencePercent());
                logger.info(price.getPriceDifferenceText());
            }
            
            
        } catch (Throwable cause) {
            logger.error(cause.getMessage(), cause);
        }
    }
}
