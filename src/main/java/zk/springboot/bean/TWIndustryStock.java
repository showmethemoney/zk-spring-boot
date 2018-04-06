package zk.springboot.bean;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TWIndustryStock implements Serializable
{
	@JsonProperty("msgArray")
	private List<TWStock> stocks = null;

	public TWIndustryStock() {
	}

	public List<TWStock> getStocks() {
		return stocks;
	}

	public void setStocks(List<TWStock> stocks) {
		this.stocks = stocks;
	}

}
