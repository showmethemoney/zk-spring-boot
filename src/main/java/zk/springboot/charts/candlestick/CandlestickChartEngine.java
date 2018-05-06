package zk.springboot.charts.candlestick;

import java.text.SimpleDateFormat;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickMarkPosition;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.SegmentedTimeline;
import org.jfree.chart.plot.XYPlot;
import org.zkoss.zkex.zul.impl.JFreeChartEngine;
import org.zkoss.zul.Chart;

public class CandlestickChartEngine extends JFreeChartEngine {


    protected boolean prepareJFreeChart(JFreeChart jfchart, Chart chart) {
        XYPlot xyplot = (XYPlot) jfchart.getPlot();

        NumberAxis numberAxis = (NumberAxis) xyplot.getRangeAxis();
        numberAxis.setAutoRangeIncludesZero(false);
        numberAxis.setAutoRangeStickyZero(false);

        DateAxis dateAxis = (DateAxis) xyplot.getDomainAxis();
        dateAxis.setDateFormatOverride(new SimpleDateFormat("MM/dd"));
        dateAxis.setTickUnit(new DateTickUnit(DateTickUnit.DAY, 2), true, true);
        dateAxis.setTickMarkPosition(DateTickMarkPosition.MIDDLE);

        dateAxis.setTimeline(SegmentedTimeline.newMondayThroughFridayTimeline());


        // // Define margin of y-axis
        // NumberAxis numberaxis = (NumberAxis) xyplot.getRangeAxis();
        // numberaxis.setUpperMargin(0.0D);
        // numberaxis.setLowerMargin(0.0D);
        //
        // // Second axis shows volume
        // NumberAxis rangeAxis2 = new NumberAxis("總交易量");
        //// rangeAxis2.setUpperMargin(10000);
        // numberaxis.setAutoRangeIncludesZero(false);
        // xyplot.setRangeAxis(1, rangeAxis2);
        //
        // CandlestickRenderer renderer = (CandlestickRenderer) xyplot.getRenderer();
        // renderer.setAutoWidthMethod(CandlestickRenderer.WIDTHMETHOD_SMALLEST);
        //
        // renderer.setSeriesPaint(0, ChartColors.COLOR_1);
        // renderer.setDownPaint(ChartColors.COLOR_5);
        // renderer.setUpPaint(ChartColors.COLOR_2);

        return false;
    }

}
