package zk.springboot.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

// ▲ rika ▼ discord

@JsonIgnoreProperties(ignoreUnknown = true)
public class TWStockPrice implements Serializable {
    @JsonProperty("ch")
    private String id = null;
    @JsonProperty("tv") // 當盤 成交量
    private String lastTradeCount = null;
    @JsonProperty("v") // 累積 成交量
    private String totalTradeCount = null;
    @JsonProperty("d") // 最後交易日
    private String lastTradeDate = null;
    @JsonProperty("f") // 賣出數量
    private String waitForSellCounts = null;
    @JsonProperty("a") // 賣出價格
    private String waitForSellPrices = null;
    @JsonProperty("g") // 買入數量
    private String waitForBuyCounts = null;
    @JsonProperty("b") // 買入價格
    private String waitForBuyPrices = null;
    @JsonProperty("o") // 開盤
    private String openPrice = null;
    @JsonProperty("l") // 最低
    private String lowPrice = null;
    @JsonProperty("h") // 最高
    private String highPrice = null;
    @JsonProperty("u")
    private String lowestPrice = null;
    @JsonProperty("w")
    private String highestPrice = null;
    @JsonProperty("z") // 最近 成交價
    private String lastPrice = null;
    @JsonProperty("y")
    private String yesterdayPrice = null;
    @JsonProperty("n") // 名稱
    private String name = null;
    @JsonProperty("c") // 股票代號
    private String code = null;
    @JsonProperty("t") // 揭示時間
    private String lastTradeTime = null;
    @JsonIgnore
    private List<StockRecord> waitForSells = null;
    @JsonIgnore
    private List<StockRecord> waitForBuys = null;
    @JsonIgnore // lastPrice - openPrice
    private String priceDifference = null;
    @JsonIgnore // (lastPrice - openPrice) / openPrice
    private String priceDifferencePercent = null;

    public TWStockPrice() {}

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLastTradeTime() {
        return lastTradeTime;
    }

    public void setLastTradeTime(String lastTradeTime) {
        this.lastTradeTime = lastTradeTime;
    }

    public String getPriceDifference() {
        return StringUtils.isBlank(getLastPrice()) ? null : new BigDecimal(getLastPrice()).subtract(new BigDecimal(getOpenPrice())).toPlainString();
    }

    public void setPriceDifference(String priceDifference) {
        this.priceDifference = priceDifference;
    }

    public String getPriceDifferencePercent() {
        return null == getPriceDifference() ? null : new BigDecimal(getPriceDifference()).divide(new BigDecimal(getOpenPrice()), 4, RoundingMode.HALF_UP).toPlainString();
    }

    public void setPriceDifferencePercent(String priceDifferencePercent) {
        this.priceDifferencePercent = priceDifferencePercent;
    }

    public String getPriceDifferenceText() {
        if (StringUtils.isBlank(getPriceDifference())) {
            return "";
        } else {
            return (isPositive() ? "▲ " : "▼ ") + getPriceDifference() + String.format(" (%,.2f%%)", Float.valueOf(getPriceDifferencePercent()) * 100);
        }
    }

    public boolean isPositive() {
        BigDecimal difference = new BigDecimal(StringUtils.isBlank(getPriceDifference()) ? "0" : getPriceDifference());
        return difference.compareTo(BigDecimal.ZERO) > 0;
    }

    public String toString() {
        return new ToStringBuilder(this).append("id", id).append("lastTradeCount", lastTradeCount).append("totalTradeCount", totalTradeCount).append("lastTradeDate", lastTradeDate)
                .append("waitForSellCounts", waitForSellCounts).append("waitForSellPrices", waitForSellPrices).append("waitForBuyCounts", waitForBuyCounts).append("waitForBuyPrices", waitForBuyPrices)
                .append("openPrice", openPrice).append("lowPrice", lowPrice).append("highPrice", highPrice).append("lowestPrice", lowestPrice).append("highestPrice", highestPrice)
                .append("lastPrice", lastPrice).append("yesterdayPrice", yesterdayPrice).toString();
    }

}
