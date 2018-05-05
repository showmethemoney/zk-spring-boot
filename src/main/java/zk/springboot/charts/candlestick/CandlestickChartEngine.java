package zk.springboot.charts.candlestick;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.CandlestickRenderer;
import org.zkoss.zkex.zul.impl.JFreeChartEngine;
import org.zkoss.zul.Chart;

import zk.springboot.charts.candlestick.model.ChartColors;

public class CandlestickChartEngine extends JFreeChartEngine {


    protected boolean prepareJFreeChart(JFreeChart jfchart, Chart chart) {
        XYPlot xyplot = (XYPlot) jfchart.getPlot();
        
        // Define margin of y-axis
        NumberAxis numberaxis = (NumberAxis) xyplot.getRangeAxis();
        numberaxis.setUpperMargin(0.0D);
        numberaxis.setLowerMargin(0.0D);

        // Second axis shows volume
        NumberAxis rangeAxis2 = new NumberAxis("TotalVolume");
        rangeAxis2.setUpperMargin(78410);
        numberaxis.setAutoRangeIncludesZero(false);
        xyplot.setRangeAxis(1, rangeAxis2);

        CandlestickRenderer renderer = (CandlestickRenderer) xyplot.getRenderer();
        renderer.setAutoWidthMethod(CandlestickRenderer.WIDTHMETHOD_SMALLEST);

        renderer.setSeriesPaint(0, ChartColors.COLOR_1);
        renderer.setDownPaint(ChartColors.COLOR_5);
        renderer.setUpPaint(ChartColors.COLOR_2);

        return false;
    }

}
