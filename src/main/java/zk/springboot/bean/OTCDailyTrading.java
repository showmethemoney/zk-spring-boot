package zk.springboot.bean;

import java.io.Serializable;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OTCDailyTrading implements Serializable {
    @JsonProperty("stkNo")
    private String stockNo = null;
    @JsonProperty("stkName")
    private String stockName = null;
    @JsonProperty("reportDate")
    private String reportDate = null;
    @JsonProperty("iTotalRecords")
    private String totalRecords = null;
    @JsonProperty("aaData")
    private List<List<String>> data = null;

    public OTCDailyTrading() {}

    public String getStockNo() {
        return stockNo;
    }

    public void setStockNo(String stockNo) {
        this.stockNo = stockNo;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public String getReportDate() {
        return reportDate;
    }

    public void setReportDate(String reportDate) {
        this.reportDate = reportDate;
    }

    public String getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(String totalRecords) {
        this.totalRecords = totalRecords;
    }

    public List<List<String>> getData() {
        return data;
    }

    public void setData(List<List<String>> data) {
        this.data = data;
    }

}
