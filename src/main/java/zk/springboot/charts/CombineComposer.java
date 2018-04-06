package zk.springboot.charts;

import java.util.Date;
import java.util.Iterator;

import org.zkoss.chart.Legend;
import org.zkoss.chart.model.CategoryModel;
import org.zkoss.chart.model.DefaultCategoryModel;
import org.zkoss.zul.HiLoModel;
import org.zkoss.zul.SimpleHiLoModel;

import zk.springboot.charts.candlestick.CandlestickChartEngine;
import zk.springboot.charts.candlestick.model.ChartData;

public class CombineComposer
{
	private CandlestickChartEngine engine = null;
	private HiLoModel hiLoModel = null;
	private CategoryModel lineModel = null;
	private Legend legend = null;

	public CandlestickChartEngine getEngine() {
		return new CandlestickChartEngine();
	}

	public void setEngine(CandlestickChartEngine engine) {
		this.engine = engine;
	}

	public HiLoModel getHiLoModel() {
		hiLoModel = new SimpleHiLoModel();
		Iterator<Object[]> it = ChartData.get().iterator();
		while (it.hasNext()) {
			Object[] data = it.next();
			hiLoModel.addValue( (Date) data[0], (Double) data[1], (Double) data[2], (Double) data[3], (Double) data[4], (Double) data[5] );
		}
		
		return hiLoModel;
	}

	public void setHiLoModel(HiLoModel hiLoModel) {
		this.hiLoModel = hiLoModel;
	}

	public Legend getLegend() {
		legend = new Legend();
		legend.setLayout( "vertical" );
		legend.setAlign( "right" );
		legend.setVerticalAlign( "middle" );
		legend.setBorderWidth( 0 );

		return legend;
	}

	public CategoryModel getLineModel() {
		lineModel = new DefaultCategoryModel();

		lineModel.setValue( "Tokyo", "Jan", 7.0 );
		lineModel.setValue( "Tokyo", "Feb", 6.9 );
		lineModel.setValue( "Tokyo", "Mar", 9.5 );
		lineModel.setValue( "Tokyo", "Apr", 14.5 );
		lineModel.setValue( "Tokyo", "May", 18.2 );
		lineModel.setValue( "Tokyo", "Jun", 21.5 );
		lineModel.setValue( "Tokyo", "Jul", 25.2 );
		lineModel.setValue( "Tokyo", "Aug", 26.5 );
		lineModel.setValue( "Tokyo", "Sep", 23.3 );
		lineModel.setValue( "Tokyo", "Oct", 18.3 );
		lineModel.setValue( "Tokyo", "Nov", 13.9 );
		lineModel.setValue( "Tokyo", "Dec", 9.6 );
		lineModel.setValue( "New York", "Jan", -0.2 );
		lineModel.setValue( "New York", "Feb", 0.8 );
		lineModel.setValue( "New York", "Mar", 5.7 );
		lineModel.setValue( "New York", "Apr", 11.3 );
		lineModel.setValue( "New York", "May", 17.0 );
		lineModel.setValue( "New York", "Jun", 22.0 );
		lineModel.setValue( "New York", "Jul", 24.8 );
		lineModel.setValue( "New York", "Aug", 24.1 );
		lineModel.setValue( "New York", "Sep", 20.1 );
		lineModel.setValue( "New York", "Oct", 14.1 );
		lineModel.setValue( "New York", "Nov", 8.6 );
		lineModel.setValue( "New York", "Dec", 2.5 );
		lineModel.setValue( "Berlin", "Jan", -0.9 );
		lineModel.setValue( "Berlin", "Feb", 0.6 );
		lineModel.setValue( "Berlin", "Mar", 3.5 );
		lineModel.setValue( "Berlin", "Apr", 8.4 );
		lineModel.setValue( "Berlin", "May", 13.5 );
		lineModel.setValue( "Berlin", "Jun", 17.0 );
		lineModel.setValue( "Berlin", "Jul", 18.6 );
		lineModel.setValue( "Berlin", "Aug", 17.9 );
		lineModel.setValue( "Berlin", "Sep", 14.3 );
		lineModel.setValue( "Berlin", "Oct", 9.0 );
		lineModel.setValue( "Berlin", "Nov", 3.9 );
		lineModel.setValue( "Berlin", "Dec", 1.0 );
		lineModel.setValue( "London", "Jan", 3.9 );
		lineModel.setValue( "London", "Feb", 4.2 );
		lineModel.setValue( "London", "Mar", 5.7 );
		lineModel.setValue( "London", "Apr", 8.5 );
		lineModel.setValue( "London", "May", 11.9 );
		lineModel.setValue( "London", "Jun", 15.2 );
		lineModel.setValue( "London", "Jul", 17.0 );
		lineModel.setValue( "London", "Aug", 16.6 );
		lineModel.setValue( "London", "Sep", 14.2 );
		lineModel.setValue( "London", "Oct", 10.3 );
		lineModel.setValue( "London", "Nov", 6.6 );
		lineModel.setValue( "London", "Dec", 4.8 );

		return lineModel;
	}

	public void setLineModel(CategoryModel lineModel) {
		this.lineModel = lineModel;
	}

	public void setLegend(Legend legend) {
		this.legend = legend;
	}
}
