package tw.com.lancer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import framework.login.LCLoginRecord;
import framework.web.LCWindow;
import tw.com.lancer.team.framework.utility.LCSort;

public class LCStat extends LCWindow {
    private int VALUE_KEYS_SIZE = 5;
    private int OUTPUT_KEYS_SIZE = 20;
    private LCLoginRecord loginRecord = super.getLoginRecord();
    private Service service = new Service();
    private String useCase = "";
    private String serviceId = "";
    private String dataKey = "";
    private List<Map> dataList = new ArrayList();// 包含所有outputKeys欄位,没有subtotalMap也没有totalMap.
    private List<Map> statList = new ArrayList();// 包含groupKey和所有valueKeys欄位,只含subtotalMap.
    private List<Map> allList = new ArrayList();// 包含所有outputKeys欄位,含dataList和subtotalMap及totalMap.
    private List<Map> topStatList = new ArrayList();
    private String[] valueKeys = new String[VALUE_KEYS_SIZE];
    private String[] outputKeys = new String[OUTPUT_KEYS_SIZE];// 所有欄位,其中有部分是valueKeys. outputKeys[0]和outputKeys[1]必須是String

    public LCStat(String useCase, String serviceId, String dataKey, String[] outputKeys, String[] valueKeys) {
        this.useCase = useCase;
        this.serviceId = serviceId;
        this.dataKey = dataKey;
        this.outputKeys = outputKeys;
        this.valueKeys = valueKeys;
    }

    public List<Map> makeDataList(HashMap param) {
        dataList.clear();
        List<Map> dataListFull = service.request(useCase, serviceId, dataKey, loginRecord, param);
        if (dataListFull.size() > 0) {
            for (Map eachMap : dataListFull) {
                HashMap aMap = new HashMap();
                for (String eachOutputKey : outputKeys) {
                    aMap.put(eachOutputKey, eachMap.get(eachOutputKey));
                }
                dataList.add(aMap);
            }
        }
        return dataList;
    }

    // 利用dataList算出statList和allList,要統計就要先sort by groupKey並計所有valueKeys得statList(小計).得到statList後,通常要排序.
    // 但有些groupKey不能依valueKeys值的大小排序(例如日期, 應依日期排序, 即statList不應再sort),只有包含在topGroupKeys中的groupKey才要排序
    // 排序後若要排前幾(topSize)名則要再加總topSize以後的其他小計,共有topSize+1列.
    // 使用本方法前先setDataList用後再getDataList(因本方法對dataList排序), getStatList, getAllList
    public String makeStatAndAllList(String groupKey, String statKey, String[] valueKeys, String[] topGroupKeys, int topSize) {
        String retMsg = "";
        statList.clear();
        allList.clear();
        if (dataList == null) {
            retMsg = "尚未生成資料,請按查詢鈕";
            return retMsg;
        } else if (dataList.size() == 0) {
            retMsg = "尚未生成資料或無資料,請重下條件再按查詢鈕";
            return retMsg;
        }
        this.dataList = dataList;
        // makeStatAndAllList之前dataList必須已經sorted by groupKey.
        String[] sortKeys = {groupKey};
        dataList = LCSort.sortData(sortKeys, dataList, true);// 依群排序由小而大
        BigDecimal[] subtotalValues = new BigDecimal[valueKeys.length];
        for (int i = 0; i < subtotalValues.length; i++) {
            subtotalValues[i] = new BigDecimal(0);
        }
        BigDecimal[] totalValues = new BigDecimal[valueKeys.length];
        for (int i = 0; i < totalValues.length; i++) {
            totalValues[i] = new BigDecimal(0);
        }
        String currentGroupValue = (String) ((HashMap) dataList.get(0)).get(groupKey);
        for (int i = 0; i < dataList.size(); i++) {
            HashMap currentMap = (HashMap) dataList.get(i);
            String thisGroupValue = (String) currentMap.get(groupKey);
            BigDecimal[] thisValues = new BigDecimal[valueKeys.length];
            for (int j = 0; j < thisValues.length; j++) {
                thisValues[j] = (BigDecimal) currentMap.get(valueKeys[j]);
            }
            if (thisGroupValue.equals(currentGroupValue))// The first time must be equal,若新的和目前的group相同則繼續累加
            {
                for (int j = 0; j < subtotalValues.length; j++)// BigDecimal subtotalValue: subtotalValues)
                {
                    subtotalValues[j] = subtotalValues[j].add(thisValues[j]);
                    totalValues[j] = totalValues[j].add(thisValues[j]);
                }
                allList.add(currentMap);
            } else// add a subtotal Map,若新的和目前的group不同則新的為下一個group的第一個值.
            {
                HashMap subtotalMap = new HashMap();
                for (String key : outputKeys) {
                    if (key.equals(groupKey)) {
                        subtotalMap.put(groupKey, currentGroupValue);
                    } else if (key.equals(obtainAnotherGroupKey(groupKey))) {
                        subtotalMap.put((String) key, "*小計*");
                    } else {
                        for (int j = 0; j < valueKeys.length; j++) {
                            if (key.equals(valueKeys[j])) {
                                subtotalMap.put(valueKeys[j], subtotalValues[j]);
                            }
                        }
                    }
                }
                allList.add(subtotalMap);
                statList.add(subtotalMap);
                // add the first Map of the new group value.
                allList.add(currentMap);
                for (int j = 0; j < valueKeys.length; j++) {
                    subtotalValues[j] = thisValues[j];// subtotalValues更新為此新的值,但totalValues不能動這樣做,否則totalValues會變成最後group的第個group值
                    totalValues[j] = totalValues[j].add(thisValues[j]);// totalValues繼續累加
                }
                currentGroupValue = thisGroupValue;
            }
        }
        // Add the last subtotal item
        HashMap subtotalMap = new HashMap();
        for (String key : outputKeys) {
            if (key.equals(groupKey)) {
                subtotalMap.put(groupKey, currentGroupValue);
            } else if (key.equals(obtainAnotherGroupKey(groupKey))) {
                subtotalMap.put(key, "*小計*");
            } else {
                for (int j = 0; j < valueKeys.length; j++) {
                    if (key.equals(valueKeys[j])) {
                        subtotalMap.put(valueKeys[j], subtotalValues[j]);
                    }
                }
            }
        }
        allList.add(subtotalMap);
        statList.add(subtotalMap);
        // Add the total Map
        HashMap totalMap = new HashMap();
        for (String key : outputKeys) {
            if (key.equals(groupKey)) {
                totalMap.put(groupKey, "<<Total>>");
            } else {
                for (int j = 0; j < valueKeys.length; j++) {
                    if (key.equals(valueKeys[j])) {
                        totalMap.put(valueKeys[j], totalValues[j]);
                    }
                }
            }
        }
        allList.add(totalMap);
        // 選前topSize大
        topStatList = statList;
        List<String> list = Arrays.asList(topGroupKeys);
        if (list.contains(groupKey)) {
            topStatList = obtainTopStatList(topStatList, groupKey, statKey, topSize);
        }
        return retMsg;
    }

    private String obtainAnotherGroupKey(String groupKey)// 為了顯示小計
    {
        String anotherGroupKey = "";// outputKeys[0]和outputKeys[1]必須是String
        if (groupKey.equals(outputKeys[0])) {
            anotherGroupKey = outputKeys[1];// 第一則第二
        } else {
            anotherGroupKey = outputKeys[0];// 非第一則第一
        }
        return anotherGroupKey;
    }

    private List<Map> obtainTopStatList(List<Map> statList, String groupKey, String statKey, int topSize) {
        if (topSize <= 0) {
            return statList;
        }
        String[] sortKeys = {statKey};
        statList = LCSort.sortData(sortKeys, statList, false);// 只排序statList
        List<Map> newList = new ArrayList();
        int endIndex = 0;
        if (statList.size() > topSize)// pureList already sorted
        {
            endIndex = topSize;
        } else // statList.size()<=topSize
        {
            // endIndex = statList.size();
            return statList;
        }
        for (int i = 0; i < endIndex; i++)// statList.size()>topSize
        {
            newList.add(statList.get(i));// index from 0 to topSize-1
        }
        if (statList.size() > topSize)// 這行應是多餘
        {
            BigDecimal[] otherValueSums = new BigDecimal[valueKeys.length];
            for (int i = 0; i < otherValueSums.length; i++) {
                otherValueSums[i] = new BigDecimal(0);// 排行外所有值欄位歸零
            }
            for (int i = endIndex; i < statList.size(); i++) {
                HashMap statMap = (HashMap) statList.get(i);
                for (int j = 0; j < valueKeys.length; j++) {
                    otherValueSums[j] = otherValueSums[j].add((BigDecimal) statMap.get(valueKeys[j]));// 所有值欄位累加排行外的值
                }
            }
            HashMap otherMap = new HashMap();
            otherMap.put(groupKey, "其他小計");
            for (int j = 0; j < valueKeys.length; j++) {
                otherMap.put(valueKeys[j], otherValueSums[j]);
            }
            newList.add(otherMap);
        }
        return newList;
    }

    // Getters and setters
    public List<Map> getDataList() {
        return dataList;
    }

    public void setDataList(List<Map> dataList) {
        this.dataList = dataList;
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

    public List<Map> getTopStatList() {
        return topStatList;
    }

    public void setTopStatList(List<Map> topStatList) {
        this.topStatList = topStatList;
    }

}
