package tw.com.lancer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.zkoss.zul.Messagebox;
// import org.jfree.chart.ChartUtilities;
// import org.jfree.chart.JFreeChart;
import framework.web.LCWindow;

public class LCChart extends LCWindow {
    private int xLabelSubstringBegin = 0;// LCChart自行規定
    private int xLabelSubstringEnd = 8;// LCChart自行規定
    private int width = 0;
    private int height = 0;
    private String categoryLegendPosition = "";

    public LCChart(int width, int height, String categoryLegendPosition) {
        this.width = width;
        this.height = height;
        this.categoryLegendPosition = categoryLegendPosition;
    }

    public LCChart() {}

    // 非Google Chr API。https://developers.google.com/chart/interactive/docs/gallery
    // https://www.apptha.com/blog/how-to-create-a-chart-using-google-api/
    // https://developers.google.com/chart/image/docs/gallery/pie_charts#pie_chart_label
    // https://code.tutsplus.com/tutorials/easy-graphs-with-google-chart-tools--net-11771
    // Google Chart API也已於2012年deprecated.用以下這個:
    // https://www.zkoss.org/zk-angular-demo/
    public String getGoogleChartUrl(String chartTitle, List<Map> sourceList, String chartType, String categoryKey, String[] valueKeys) {
        if (width * height > 300000) {
            Messagebox.show("長x寛超過300,000px.");
            return "";
        }
        String categoryLabel = categoryKey;
        String[] valueLabels = valueKeys;
        return getGoogleChartUrl(chartTitle, chartType, sourceList, categoryLabel, valueLabels, categoryKey, valueKeys, width, height, categoryLegendPosition);
    }

    public String getLCChartUrl(String chartTitle, String chartType, List<Map> sourceList, String categoryLabel, String[] valueLabels, String categoryKey, String[] valueKeys, int width, int height,
            String categoryLegendPosition, int categoryLableSubstringEnd) {
        xLabelSubstringBegin = 0;
        xLabelSubstringEnd = categoryLableSubstringEnd;
        return getGoogleChartUrl(chartTitle, chartType, sourceList, categoryLabel, valueLabels, categoryKey, valueKeys, width, height, categoryLegendPosition);
    }

    public String getLCChartUrl(String chartTitle, String chartType, List<Map> sourceList, String categoryLabel, String[] valueLabels, String categoryKey, String[] valueKeys, int width, int height,
            String categoryLegendPosition, int categoryLableSubstringBegin, int categoryLableSubstringEnd) {
        xLabelSubstringBegin = categoryLableSubstringBegin;
        xLabelSubstringEnd = categoryLableSubstringEnd;
        return getGoogleChartUrl(chartTitle, chartType, sourceList, categoryLabel, valueLabels, categoryKey, valueKeys, width, height, categoryLegendPosition);
    }

    public String getGoogleChartUrl(String chartTitle, String chartType, List<Map> sourceList, String categoryLabel, /* String seriesLabel, */ String[] valueLabels, String categoryKey,
            /* String seriesKey, */ String[] valueKeys, int width, int height, String categoryLegendPosition) {
        // categoryLegendPosition = r,b,bv,t,tv
        // chartType = p, p3, bhg, bhs, bvg, bvs, lc
        // Pie chart's width must be at least 2.5 times as wide as its height.
        if (width * height > 300000) {
            Messagebox.show("長x寛超過300,000px.");
            return "";
        }

        String color = "3366CC,DC3912,FF9900,109618,990099,3B3EAC,0099C6,DD4477,66AA00,B82E2E,316395,994499,22AA99,AAAA11,6633CC,E67300,8B0707,329262,5574A6,3B3EAC";
        String[] colors = color.split(",");
        List<Integer> indexes = this.getRandomNonRepeatingIntegers(20, 0, 19);
        String temp = "";
        for (Integer i : indexes) {
            temp = temp + colors[i] + ",";
        }
        color = temp.substring(0, temp.length() - 1);
        String url = "http://chart.apis.google.com/chart?cht=" + chartType + "&chbh=a&chtt=" + chartTitle;
        String valueLableString = "";// itemListString in MIT AI2
        for (int i = 0; i < valueLabels.length; i++) {
            valueLableString = valueLableString + valueLabels[i] + "|";
        }
        valueLableString = valueLableString.substring(0, valueLableString.length() - 1);
        String dataListString = "";
        String categoryListString = "";// axis x label, chxl=1:|EPSON色帶|K牌啤酒|...
        for (int k = 0; k < sourceList.size(); k++)// Map eachMap: sourceList)
        {
            String xlabel = "";
            // bhg或bhs時categoryListString倒置
            if (chartType.equals("bhg") || chartType.equals("bhs")) {
                xlabel = ((String) sourceList.get(sourceList.size() - 1 - k).get(categoryKey)).replace(" ", "").replace("_", "");
            } else {
                xlabel = ((String) sourceList.get(k).get(categoryKey)).replace(" ", "").replace("_", "");
            }
            if (xlabel.length() > xLabelSubstringEnd) {
                xlabel = xlabel.substring(xLabelSubstringBegin, xLabelSubstringEnd);
            } else {
                xlabel = xlabel.substring(xLabelSubstringBegin, xlabel.length());
            }
            categoryListString = categoryListString + xlabel + "|";
            for (int i = 0; i < valueKeys.length; i++) {
                dataListString = dataListString + (BigDecimal) sourceList.get(k).get(valueKeys[i]) + ",";
            }
        }
        if (categoryListString.length() == 0) {
            return "";
        }
        categoryListString = categoryListString.substring(0, categoryListString.length() - 1);
        dataListString = dataListString.substring(0, dataListString.length() - 1);
        if (categoryListString.length() == 0 || dataListString.length() == 0) {
            return "";
        }
        BigDecimal minValue = findMinMaxValues(dataListString)[0].setScale(0, BigDecimal.ROUND_HALF_UP);
        BigDecimal maxValue = findMinMaxValues(dataListString)[1].setScale(0, BigDecimal.ROUND_HALF_UP);
        if (chartType.equals("p") || chartType.equals("p3")) {
            url = url + "&chd=t:" + dataListString + "&chdl=" + categoryListString + "&chl=" + dataListString.replace(",", "|");
        } else {
            if (chartType.equals("bhg") || chartType.equals("bhs")) {
                url = url + "&chxl=1:|" + categoryListString + "&chxr=0," + minValue + "," + maxValue;// maxChxr;
            } else if (chartType.equals("lc") || chartType.equals("bvg") || chartType.equals("bvs"))// lc, bvg, bvs
            {
                url = url + "&chxl=0:|" + categoryListString + "&chxr=1,0" + /* minValue + */ "," + maxValue;// maxChxr;
            }
            url = url + "&chxt=x,y&chdl=" + valueLableString;
        }
        int noOfCategory = (categoryListString.split("[|]")).length;
        url = url + "&chd=t:" + dataListString + "&chds=" + "0" + "," + maxValue + "&chdlp=" + categoryLegendPosition + "&chls=3,1,0" + "&chs=" + width + "x" + height
                + "&chg=0,25,5,5"/* &chbh=20,5,"+width/noOfCategory */ + "&chco=" + color;
        return url;
    }

    private BigDecimal[] findMinMaxValues(String dataStr) {
        BigDecimal[] minMaxValues = new BigDecimal[2];
        BigDecimal minValue = new BigDecimal("99999999");
        BigDecimal maxValue = new BigDecimal("0");
        String[] dataArray = dataStr.split(",");// |是java的特殊符號,故須加[].
        for (int i = 0; i < dataArray.length; i++) {
            BigDecimal data = new BigDecimal(dataArray[i]);
            if (data.compareTo(minValue) < 0) {
                minValue = data;
            }
            if (data.compareTo(maxValue) > 0) {
                maxValue = data;
            }
        }
        minMaxValues[0] = minValue;
        minMaxValues[1] = maxValue;
        return minMaxValues;
    }

    public static int getRandomInt(int min, int max) {
        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }

    public List<Integer> getRandomNonRepeatingIntegers(int size, int min, int max) {
        List<Integer> numbers = new ArrayList();
        while (numbers.size() < size) {
            int random = getRandomInt(min, max);
            if (!numbers.contains(random)) {
                numbers.add(random);
            }
        }
        return numbers;
    }
}
