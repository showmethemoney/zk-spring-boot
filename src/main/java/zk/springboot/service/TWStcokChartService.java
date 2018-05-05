package zk.springboot.service;

import java.text.NumberFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.time.DateUtils;
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

			while (iterator.hasNext()) {
				trading = (TWStockDailyTrading) iterator.next();
				 
				result.addValue((DateUtils.parseDate(trading.getTradingDate(), new String[] {"yyyy/MM/dd", "yyyyMMdd"})), 
						Double.parseDouble(trading.getOpeningPrice()), 
						Double.parseDouble(trading.getDayHigh()),
						Double.parseDouble(trading.getDayLow()), 
						Double.parseDouble(trading.getClosingPrice()),
						format.parse(trading.getTotalVolume()).doubleValue());
			}
		} catch (Throwable cause) {
			logger.error(cause.getMessage(), cause);
		}

		return result;
	}
}
