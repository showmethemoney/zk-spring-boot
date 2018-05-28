package zk.springboot.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.HiLoModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listitem;
import zk.springboot.bean.TWIndustry;
import zk.springboot.bean.TWStock;
import zk.springboot.bean.TWStockDailyTrading;
import zk.springboot.charts.candlestick.CandlestickChartEngine;
import zk.springboot.service.StockType;
import zk.springboot.service.TWStcokChartService;
import zk.springboot.service.TWStockService;

@VariableResolver(DelegatingVariableResolver.class)
public class TWStockChartController {

    protected static final Logger logger = LoggerFactory.getLogger(TWStockChartController.class);
    @WireVariable
    private TWStockService stockService = null;
    @WireVariable
    private TWStcokChartService stockChartService = null;

    private CandlestickChartEngine engine = null;
    private HiLoModel model = null;

    private ListModelList<Listitem> stockTypes = null;
    private ListModelList<Listitem> industries = null;
    private ListModelList<Listitem> stocks = null;

    private Listitem selectedStockType = null;
    private Listitem selectedIndustry = null;
    private Listitem selectedStock = null;

    private Date startDate = null;
    private Date endDate = null;

    private boolean show = false;
    private List<TWStockDailyTrading> stockDailyTrading = null;

    @Init
    public void init() {
        engine = new CandlestickChartEngine();

        stockTypes = new ListModelList<Listitem>();
        for (StockType stockType : StockType.values()) {
            stockTypes.add(new Listitem(stockType.getName(), stockType.getType()));
        }

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -2);
        startDate = calendar.getTime();
        endDate = Calendar.getInstance().getTime();
    }

    @NotifyChange({"industries"})
    @Command("selectStockType")
    public void selectStockType() {
        logger.info("onSelectStockType {} : {} ", getSelectedStockType().getLabel(), getSelectedStockType().getValue());

        // 設定產業
        List<TWIndustry> twIndustries = stockService.getIndustry(getSelectedStockType().getValue());
        logger.info("TW Industry size : {}", twIndustries.size());

        industries = new ListModelList<Listitem>();
        for (TWIndustry industry : twIndustries) {
            industries.add(new Listitem(industry.getName(), industry.getCode()));
        }
    }

    @NotifyChange({"stocks"})
    @Command("selectIndustry")
    public void selectIndustry() {
        logger.info("onSelectIndustry {} : {} ", getSelectedIndustry().getLabel(), getSelectedIndustry().getValue());

        // 設定股票
        List<TWStock> twStocks = stockService.getIndustryStocksByType(getSelectedStockType().getValue(), getSelectedIndustry().getValue());
        logger.info("TW Stock size : {}", twStocks.size());

        stocks = new ListModelList<Listitem>();
        for (TWStock stock : twStocks) {
            stocks.add(new Listitem(stock.getName() + "(" + stock.getId() + ")", stock.getId()));
        }
    }

    @Command("selectStock")
    public void selectStock() {
        logger.info("onSelectStock {} : {} ", getSelectedStock().getLabel(), getSelectedStock().getValue());
    }

    @NotifyChange({"model", "stockDailyTrading", "show"})
    @Command("show")
    public void show() {
        try {
            logger.info("stock type : {}, stock : {}, start date : {}, end date : {}", getSelectedStockType().getValue(), StringUtils.substringBefore(((String) getSelectedStock().getValue()), "."),
                    DateFormatUtils.format(getStartDate(), "yyyy/MM/dd hh:mm:ss"), DateFormatUtils.format(getEndDate(), "yyyy/MM/dd hh:mm:ss"));

            stockDailyTrading = stockService.getDailyTrade(getSelectedStockType().getValue(), StringUtils.substringBefore(((String) getSelectedStock().getValue()), "."), getStartDate(), getEndDate());

            model = stockChartService.getCandlestickChartModel(stockDailyTrading);

            setShow(true);
            logger.info("stockDailyTrading : {}, model : {}", stockDailyTrading.size(), model.getDataCount());

        } catch (Throwable cause) {
            logger.error(cause.getMessage(), cause);
        }
    }

    public CandlestickChartEngine getEngine() {
        return engine;
    }

    public void setEngine(CandlestickChartEngine engine) {
        this.engine = engine;
    }

    public HiLoModel getModel() {
        return model;
    }

    public void setModel(HiLoModel model) {
        this.model = model;
    }

    public ListModelList<Listitem> getStockTypes() {
        return stockTypes;
    }

    public void setStockTypes(ListModelList<Listitem> stockTypes) {
        this.stockTypes = stockTypes;
    }

    public ListModelList<Listitem> getIndustries() {
        return industries;
    }

    public void setIndustries(ListModelList<Listitem> industries) {
        this.industries = industries;
    }

    public ListModelList<Listitem> getStocks() {
        return stocks;
    }

    public void setStocks(ListModelList<Listitem> stocks) {
        this.stocks = stocks;
    }

    public Listitem getSelectedStockType() {
        return selectedStockType;
    }

    public void setSelectedStockType(Listitem selectedStockType) {
        this.selectedStockType = selectedStockType;
    }

    public Listitem getSelectedIndustry() {
        return selectedIndustry;
    }

    public void setSelectedIndustry(Listitem selectedIndustry) {
        this.selectedIndustry = selectedIndustry;
    }

    public Listitem getSelectedStock() {
        return selectedStock;
    }

    public void setSelectedStock(Listitem selectedStock) {
        this.selectedStock = selectedStock;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }

    public List<TWStockDailyTrading> getStockDailyTrading() {
        return stockDailyTrading;
    }

    public void setStockDailyTrading(List<TWStockDailyTrading> stockDailyTrading) {
        this.stockDailyTrading = stockDailyTrading;
    }

}
