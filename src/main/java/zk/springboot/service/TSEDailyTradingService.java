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

import zk.springboot.bean.TSEDailyTrading;
import zk.springboot.bean.TWStockDailyTrading;

@Service
public class TSEDailyTradingService implements DailyTradingService
{
	// {"stat":"OK","date":"20180101","title":"107年01月 2330 台積電
	// 各日成交資訊","fields":["日期","成交股數","成交金額","開盤價","最高價","最低價","收盤價","漲跌價差","成交筆數"],"data":[["107/01/02","18,055,269","4,188,555,408","231.50","232.50","231.00","232.50","+3.00","9,954"],["107/01/03","31,706,091","7,504,382,512","236.00","238.00","235.50","237.00","+4.50","13,633"],["107/01/04","29,179,613","6,963,192,636","240.00","240.00","236.50","239.50","+2.50","10,953"],["107/01/05","23,721,255","5,681,934,695","240.00","240.00","238.00","240.00","+0.50","8,659"],["107/01/08","21,846,692","5,281,823,362","242.00","242.50","240.50","242.00","+2.00","10,251"],["107/01/09","19,043,123","4,588,314,012","242.00","242.00","239.50","242.00","
	// 0.00","7,124"],["107/01/10","25,716,220","6,118,683,273","241.50","242.00","236.00","236.50","-5.50","10,534"],["107/01/11","32,070,338","7,500,674,455","235.00","236.00","232.50","235.00","-1.50","9,199"],["107/01/12","23,141,291","5,463,302,207","234.50","238.00","233.50","237.00","+2.00","7,905"],["107/01/15","28,576,533","6,832,851,887","240.00","240.00","238.00","240.00","+3.00","9,756"],["107/01/16","23,407,632","5,609,009,540","240.00","240.50","238.00","240.50","+0.50","8,156"],["107/01/17","38,118,119","9,207,582,787","240.50","243.00","239.00","242.00","+1.50","12,593"],["107/01/18","50,119,952","12,406,562,364","245.00","250.00","245.00","248.50","+6.50","19,482"],["107/01/19","55,061,292","13,975,174,348","253.50","255.50","251.50","255.50","+7.00","18,801"],["107/01/22","45,907,509","11,934,882,643","257.50","262.00","257.00","261.50","+6.00","13,558"],["107/01/23","34,606,444","9,155,080,569","262.50","266.00","262.50","266.00","+4.50","13,993"],["107/01/24","42,600,813","11,022,372,004","263.00","263.00","256.50","258.00","-8.00","17,287"],["107/01/25","46,214,756","11,981,089,514","258.00","264.00","256.50","258.00","
	// 0.00","15,826"],["107/01/26","43,514,523","11,101,977,348","256.50","257.50","253.50","255.00","-3.00","12,821"],["107/01/29","31,306,234","8,067,780,117","259.00","261.50","255.00","258.50","+3.50","12,211"],["107/01/30","37,410,591","9,523,980,994","256.00","257.50","252.50","253.00","-5.50","12,987"],["107/01/31","45,807,808","11,670,582,000","253.00","257.50","252.00","255.00","+2.00","13,553"]],"notes":["符號說明:+/-/X表示漲/跌/不比價","當日統計資訊含一般、零股、盤後定價、鉅額交易，不含拍賣、標購。","ETF證券代號第六碼為K、M、S、C者，表示該ETF以外幣交易。"]}

	protected static final Logger logger = LoggerFactory.getLogger( TSEDailyTradingService.class );
	public static final String URL_TSE_DAILY_TRADING = "http://www.twse.com.tw/exchangeReport/STOCK_DAY";
	@Autowired
	private ObjectMapper objectMapper = null;

	public List<TWStockDailyTrading> getDailyTrading(String stockNo, Date start) {
		List<TWStockDailyTrading> result = new ArrayList<TWStockDailyTrading>();
		URIBuilder builder = null;
		HttpResponse response = null;
		HttpClient client = HttpClientBuilder.create().build();

		try {
			builder = new URIBuilder( URL_TSE_DAILY_TRADING );
			// {'date': '%d%02d01' % (year, month), 'stockNo': sid} {'date': '20180101', 'stockNo': '2801'}
			builder.setParameter( "date", DateFormatUtils.format( start, "yyyyMM" ) + "01" ).setParameter( "stockNo", stockNo );
			response = client.execute( new HttpGet( builder.build() ) );

			TSEDailyTrading tseDailyTrading = objectMapper.readValue( IOUtils.toString( response.getEntity().getContent(), "UTF-8" ), TSEDailyTrading.class );

			result = tseDailyTrading.getData().stream().map( new Function<List<String>, TWStockDailyTrading>() {
				@Override
				public TWStockDailyTrading apply(List<String> list) {
					TWStockDailyTrading entity = new TWStockDailyTrading();
//					String[] arr = StringUtils.split( s, DailyTradingService.SEPERATOR );
//					entity.setTradingDate( arr[0] );
//					entity.setTradingShares( arr[1] );
//					entity.setTurnover( arr[2] );
//					entity.setOpeningPrice( arr[3] );
//					entity.setClosingPrice( arr[4] );
//					entity.setDayHigh( arr[5] );
//					entity.setDayLow( arr[6] );
//					entity.setPriceDifference( arr[7] );
//					entity.setTotalVolume( arr[8] );

					entity.setTradingDate( list.get( 0 ) );
					entity.setTradingShares( list.get( 1 ) );
					entity.setTurnover( list.get( 2 ) );
					entity.setOpeningPrice( list.get( 3 ) );
					entity.setClosingPrice( list.get( 4 ) );
					entity.setDayHigh( list.get( 5 ) );
					entity.setDayLow( list.get( 6 ) );
					entity.setPriceDifference( list.get( 7 ) );
					entity.setTotalVolume( list.get( 8 ) );
					
					return entity;
				}
			} ).collect( Collectors.toList() );

		} catch (Throwable cause) {
			logger.error( cause.getMessage(), cause );
		}

		return result;
	}
}
