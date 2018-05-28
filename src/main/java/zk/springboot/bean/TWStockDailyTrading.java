package zk.springboot.bean;

public class TWStockDailyTrading {
    // 交易日期
    private String tradingDate = null;
    // 成交股數
    private String tradingShares = null;
    // 成交金額
    private String turnover = null;
    // 開盤價
    private String openingPrice = null;
    // 收盤價
    private String closingPrice = null;
    // 當日最高價
    private String dayHigh = null;
    // 當日最低價
    private String dayLow = null;
    // 漲跌價差
    private String priceDifference = null;
    // 成交筆數
    private String totalVolume = null;

    public TWStockDailyTrading() {}

    public TWStockDailyTrading(String tradingDate, String tradingShares, String turnover, String openingPrice, String closingPrice, String dayHigh, String dayLow, String priceDifference,
            String totalVolume) {
        this.tradingDate = tradingDate;
        this.tradingShares = tradingShares;
        this.turnover = turnover;
        this.openingPrice = openingPrice;
        this.closingPrice = closingPrice;
        this.dayHigh = dayHigh;
        this.dayLow = dayLow;
        this.priceDifference = priceDifference;
        this.totalVolume = totalVolume;
    }

    public String getTradingDate() {
        return tradingDate;
    }

    public void setTradingDate(String tradingDate) {
        this.tradingDate = tradingDate;
    }

    public String getTradingShares() {
        return tradingShares;
    }

    public void setTradingShares(String tradingShares) {
        this.tradingShares = tradingShares;
    }

    public String getTurnover() {
        return turnover;
    }

    public void setTurnover(String turnover) {
        this.turnover = turnover;
    }

    public String getOpeningPrice() {
        return openingPrice;
    }

    public void setOpeningPrice(String openingPrice) {
        this.openingPrice = openingPrice;
    }

    public String getClosingPrice() {
        return closingPrice;
    }

    public void setClosingPrice(String closingPrice) {
        this.closingPrice = closingPrice;
    }

    public String getDayHigh() {
        return dayHigh;
    }

    public void setDayHigh(String dayHigh) {
        this.dayHigh = dayHigh;
    }

    public String getDayLow() {
        return dayLow;
    }

    public void setDayLow(String dayLow) {
        this.dayLow = dayLow;
    }

    public String getPriceDifference() {
        return priceDifference;
    }

    public void setPriceDifference(String priceDifference) {
        this.priceDifference = priceDifference;
    }

    public String getTotalVolume() {
        return totalVolume;
    }

    public void setTotalVolume(String totalVolume) {
        this.totalVolume = totalVolume;
    }

}
