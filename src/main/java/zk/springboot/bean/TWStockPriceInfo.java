package zk.springboot.bean;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TWStockPriceInfo implements Serializable
{
	@JsonProperty("msgArray")
	private List<TWStockPrice> stockPrices = null;

	public TWStockPriceInfo() {
	}

	public List<TWStockPrice> getStockPrices() {
		return stockPrices;
	}

	public void setStockPrices(List<TWStockPrice> stockPrices) {
		this.stockPrices = stockPrices;
	}

}
