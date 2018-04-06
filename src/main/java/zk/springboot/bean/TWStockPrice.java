package zk.springboot.bean;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TWStockPrice implements Serializable
{
	@JsonProperty("ch")
	private String id = null;
	@JsonProperty("tv")
	private String lastTradeCount = null;
	@JsonProperty("v")
	private String totalTradeCount = null;
	@JsonProperty("tlong")
	private String lastTradeDate = null;
	@JsonProperty("f")
	private String waitForSellCounts = null;
	@JsonProperty("a")
	private String waitForSellPrices = null;
	@JsonProperty("g")
	private String waitForBuyCounts = null;
	@JsonProperty("b")
	private String waitForBuyPrices = null;
	@JsonProperty("o")
	private String openPrice = null;
	@JsonProperty("l")
	private String lowPrice = null;
	@JsonProperty("h")
	private String highPrice = null;
	@JsonProperty("u")
	private String lowestPrice = null;
	@JsonProperty("w")
	private String highestPrice = null;
	@JsonProperty("z")
	private String lastPrice = null;
	@JsonProperty("y")
	private String yesterdayPrice = null;
	@JsonIgnore
	private List<StockRecord> waitForSells = null;
	@JsonIgnore
	private List<StockRecord> waitForBuys = null;

	public TWStockPrice() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLastTradeCount() {
		return lastTradeCount;
	}

	public void setLastTradeCount(String lastTradeCount) {
		this.lastTradeCount = lastTradeCount;
	}

	public String getTotalTradeCount() {
		return totalTradeCount;
	}

	public void setTotalTradeCount(String totalTradeCount) {
		this.totalTradeCount = totalTradeCount;
	}

	public String getLastTradeDate() {
		return lastTradeDate;
	}

	public void setLastTradeDate(String lastTradeDate) {
		this.lastTradeDate = lastTradeDate;
	}

	public String getWaitForSellCounts() {
		return waitForSellCounts;
	}

	public void setWaitForSellCounts(String waitForSellCounts) {
		this.waitForSellCounts = waitForSellCounts;
	}

	public String getWaitForSellPrices() {
		return waitForSellPrices;
	}

	public void setWaitForSellPrices(String waitForSellPrices) {
		this.waitForSellPrices = waitForSellPrices;
	}

	public String getWaitForBuyCounts() {
		return waitForBuyCounts;
	}

	public void setWaitForBuyCounts(String waitForBuyCounts) {
		this.waitForBuyCounts = waitForBuyCounts;
	}

	public String getWaitForBuyPrices() {
		return waitForBuyPrices;
	}

	public void setWaitForBuyPrices(String waitForBuyPrices) {
		this.waitForBuyPrices = waitForBuyPrices;
	}

	public String getOpenPrice() {
		return openPrice;
	}

	public void setOpenPrice(String openPrice) {
		this.openPrice = openPrice;
	}

	public String getLowPrice() {
		return lowPrice;
	}

	public void setLowPrice(String lowPrice) {
		this.lowPrice = lowPrice;
	}

	public String getHighPrice() {
		return highPrice;
	}

	public void setHighPrice(String highPrice) {
		this.highPrice = highPrice;
	}

	public String getLowestPrice() {
		return lowestPrice;
	}

	public void setLowestPrice(String lowestPrice) {
		this.lowestPrice = lowestPrice;
	}

	public String getHighestPrice() {
		return highestPrice;
	}

	public void setHighestPrice(String highestPrice) {
		this.highestPrice = highestPrice;
	}

	public String getLastPrice() {
		return lastPrice;
	}

	public void setLastPrice(String lastPrice) {
		this.lastPrice = lastPrice;
	}

	public String getYesterdayPrice() {
		return yesterdayPrice;
	}

	public void setYesterdayPrice(String yesterdayPrice) {
		this.yesterdayPrice = yesterdayPrice;
	}

	public List<StockRecord> getWaitForSells() {
		return waitForSells;
	}

	public void setWaitForSells(List<StockRecord> waitForSells) {
		this.waitForSells = waitForSells;
	}

	public List<StockRecord> getWaitForBuys() {
		return waitForBuys;
	}

	public void setWaitForBuys(List<StockRecord> waitForBuys) {
		this.waitForBuys = waitForBuys;
	}

	public String toString() {
		return new ToStringBuilder( this ).append( "id", id ).append( "lastTradeCount", lastTradeCount ).append( "totalTradeCount", totalTradeCount )
		        .append( "lastTradeDate", lastTradeDate ).append( "waitForSellCounts", waitForSellCounts ).append( "waitForSellPrices", waitForSellPrices )
		        .append( "waitForBuyCounts", waitForBuyCounts ).append( "waitForBuyPrices", waitForBuyPrices ).append( "openPrice", openPrice )
		        .append( "lowPrice", lowPrice ).append( "highPrice", highPrice ).append( "lowestPrice", lowestPrice ).append( "highestPrice", highestPrice )
		        .append( "lastPrice", lastPrice ).append( "yesterdayPrice", yesterdayPrice ).toString();
	}

}
