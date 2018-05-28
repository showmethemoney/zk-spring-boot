package tw.com.lancer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.zkoss.zul.Messagebox;
import framework.login.LCLoginRecord;
import framework.web.LCWindow;

public class LCListbox extends LCWindow {
    private LCLoginRecord loginRecord = super.getLoginRecord();
    private Service service = new Service();
    private String useCase = "";
    private String serviceId = "";
    private String dataKey = "";
    public List<String> outputKeys = new ArrayList();// 其中有一個是valueKey
    public String valueKey = "";
    public String value = "";
    public List<Map> dataList = new ArrayList();
    public List<Map> initialDataList = new ArrayList();
    public HashMap dataMap = new HashMap();
    public HashMap param = new HashMap();
    public boolean quickSearch = true;

    public LCListbox(String useCase, String serviceId, String dataKey, HashMap param, List<String> outputKeys, String valueKey, boolean quickSearch) {
        this.useCase = useCase;
        this.serviceId = serviceId;
        this.dataKey = dataKey;
        this.outputKeys = outputKeys;
        this.valueKey = valueKey;
        this.param = param;
        this.quickSearch = quickSearch;
        List<Map> initialDataListFull = service.request(useCase, serviceId, dataKey, loginRecord, param);
        List<Map> list = new ArrayList();
        if (initialDataListFull.size() > 0) {
            for (Map eachMap : initialDataListFull) {
                HashMap aMap = new HashMap();
                for (String eachOutputKey : outputKeys) {
                    aMap.put(eachOutputKey, (String) eachMap.get(eachOutputKey));
                }
                list.add(aMap);
            }
            initialDataList = list;
            for (Map eaMap : initialDataList) {
                String str = "";
                for (String outputKey : outputKeys) {
                    str = str + (String) eaMap.get(outputKey) + "|";
                }
                eaMap.put("displayItem", str.substring(0, str.length() - 1));
            }
            dataList = initialDataList;
        } else {
            Messagebox.show("初始化" + useCase + "." + serviceId + "查無資料.");
        }
    }

    public List<Map> search() {
        String content = this.getValue();
        if (content == null) {
            content = "";
        }
        List<Map> list = new ArrayList();
        List<Map> tempList = initialDataList;
        if (!content.equals("")) {
            for (Map eachMap : tempList) {
                if (((String) eachMap.get("displayItem")).contains(content)) {
                    list.add(eachMap);
                }
            }
            if (list.size() == 0) {
                Messagebox.show("没有包含'" + content + "'的資料.");
            }
        } else {
            list = initialDataList;
        }
        dataList = list;
        return list;
    }

    // Getters and setters
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<Map> getDataList() {
        return dataList;
    }

    public void setDataList(List<Map> dataList) {
        this.dataList = dataList;
    }

    public HashMap getDataMap() {
        return dataMap;
    }

    public void setDataMap(HashMap dataMap) {
        this.dataMap = dataMap;
    }

    public boolean isQuickSearch() {
        return quickSearch;
    }

    public void setQuickSearch(boolean quickSearch) {
        this.quickSearch = quickSearch;
    }
}
