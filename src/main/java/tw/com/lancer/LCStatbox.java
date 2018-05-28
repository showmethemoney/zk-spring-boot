package tw.com.lancer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import framework.login.LCLoginRecord;
import framework.web.LCWindow;

public class LCStatbox extends LCWindow {
    private int VALUE_KEYS_SIZE = 5;
    private int OUTPUT_KEYS_SIZE = 20;
    private LCLoginRecord loginRecord = super.getLoginRecord();
    // private String chartTitle ="";
    // private String chartType = "bvg";
    // private String categoryLegendPosition = "b";
    // private int width = 0;
    // private int height = 0;
    private Service service = new Service();
    private String useCase = "";
    private String serviceId = "";
    private String dataKey = "";
    // private HashMap param = new HashMap();//input
    private String[] outputKeys = new String[OUTPUT_KEYS_SIZE];// 其中有一部分是valueKeys其餘為所有categoryKey
    // private String categoryLabel = "";
    // private String[] valueLabels = new String[VALUE_KEYS_SIZE];
    // private String categoryKey = "";//outputKeys之中的文字欄位
    private String[] valueKeys = new String[VALUE_KEYS_SIZE];// 本類別通常valueKeys=outputKeys
    // private String statKey = "";//valueKeys之一
    // private int topSize = 0;
    private List<Map> dataList = new ArrayList();
    private String chartUrl = "";
    private List<Map> statList = new ArrayList();
    private List<Map> allList = new ArrayList();

    public LCStatbox(String useCase, String serviceId, String dataKey, HashMap param, String[] outputKeys, String[] valueKeys) {
        this.useCase = useCase;
        this.serviceId = serviceId;
        this.dataKey = dataKey;
        this.valueKeys = valueKeys;
        this.outputKeys = outputKeys;
        LCStat s = new LCStat(useCase, serviceId, dataKey, outputKeys, valueKeys);
        List<Map> tempList = s.makeDataList(param);
        for (Map eachMap : tempList) {
            for (String valueKey : valueKeys) {
                eachMap.put(valueKey, ((BigDecimal) eachMap.get(valueKey)).setScale(2));
            }
        }
        this.dataList = tempList;
    }


    public void makeStatListAndChartUrl(String chartTitle, String chartType, int width, int height, String categoryLegendPosition, String categoryLabel, String[] valueLabels, String categoryKey,
            String statKey, int topSize, int categoryLabelIndexEnd) {
        LCChart c = new LCChart();
        LCStat s = new LCStat(useCase, serviceId, dataKey, outputKeys, valueKeys);// s只設變數,未產生自己的dataList
        s.setDataList(dataList);
        String[] topGroupKeys = new String[1];
        if (topSize > 0)// 排行
        {
            topGroupKeys[0] = categoryKey;
        }
        s.makeStatAndAllList(categoryKey, statKey, valueKeys, topGroupKeys, topSize);// s已有dataList
        statList = s.getStatList();
        allList = s.getAllList();
        String[] valueKeys = {statKey};
        chartUrl = c.getLCChartUrl(chartTitle, chartType, statList, categoryLabel, valueLabels, categoryKey, valueKeys, width, height, categoryLegendPosition, categoryLabelIndexEnd);
    }

    public void makeStatListAndChartUrl(String chartTitle, String chartType, int width, int height, String categoryLegendPosition, String categoryLabel, String[] valueLabels, String categoryKey,
            String statKey, int topSize, int categoryLabelIndexBegin, int categoryLabelIndexEnd) {
        LCChart c = new LCChart();
        LCStat s = new LCStat(useCase, serviceId, dataKey, outputKeys, valueKeys);// s只設變數,未產生自己的dataList
        s.setDataList(dataList);
        String[] topGroupKeys = new String[1];
        if (topSize > 0)// 排行
        {
            topGroupKeys[0] = categoryKey;
        }
        s.makeStatAndAllList(categoryKey, statKey, valueKeys, topGroupKeys, topSize);// s已有dataList
        statList = s.getStatList();
        allList = s.getAllList();
        String[] valueKeys = {statKey};
        chartUrl = c.getLCChartUrl(chartTitle, chartType, statList, categoryLabel, valueLabels, categoryKey, valueKeys, width, height, categoryLegendPosition, categoryLabelIndexBegin,
                categoryLabelIndexEnd);
    }


    public String getChartUrl() {
        return chartUrl;
    }

    public void setChartUrl(String chartUrl) {
        this.chartUrl = chartUrl;
    }

    public List<Map> getStatList() {
        return statList;
    }

    public void setStatList(List<Map> statList) {
        this.statList = statList;
    }

    public List<Map> getAllList() {
        return allList;
    }

    public void setAllList(List<Map> allList) {
        this.allList = allList;
    }
}
