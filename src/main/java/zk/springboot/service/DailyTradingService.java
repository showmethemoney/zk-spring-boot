package zk.springboot.service;

import java.util.Date;
import java.util.List;

import zk.springboot.bean.TWStockDailyTrading;

public interface DailyTradingService
{
	public List<TWStockDailyTrading> getDailyTrading(String stockNo, Date start);
}
