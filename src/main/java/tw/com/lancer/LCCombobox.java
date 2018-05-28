package tw.com.lancer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.SimpleListModel;
import framework.login.LCLoginRecord;
import framework.web.LCWindow;

public class LCCombobox extends LCWindow {
    private LCLoginRecord loginRecord = super.getLoginRecord();
    private Service service = new Service();
    private String useCase = "";
    private String serviceId = "";
    private String dataKey = "";
    public List<String> outputKeys = new ArrayList();// 其中有一個是valueKey
    public String value = "";
    public String dataItem = "";
    public HashMap param = new HashMap();
    public ListModel dataModel;

    // 因不分頁,初始化就讀進所有displayItemArray建立dataModel,再用prefix搜尋
    public LCCombobox(String useCase, String serviceId, String dataKey, HashMap param, List<String> outputKeys) {
        this.useCase = useCase;
        this.serviceId = serviceId;
        this.dataKey = dataKey;
        this.param = param;
        this.outputKeys = outputKeys;
        List<Map> comboboxListFull = service.request(useCase, serviceId, dataKey, loginRecord, param);
        List<Map> comboboxList = new ArrayList();
        if (comboboxListFull.size() > 0) {
            for (Map eachMap : comboboxListFull) {
                HashMap aMap = new HashMap();
                for (String eachOutputKey : outputKeys) {
                    aMap.put(eachOutputKey, (String) eachMap.get(eachOutputKey));
                }
                comboboxList.add(aMap);
            }
            for (Map eaMap : comboboxList) {
                String str = "";
                for (String outputKey : outputKeys) {
                    str = str + (String) eaMap.get(outputKey) + "|";
                }
                eaMap.put("displayItem", str.substring(0, str.length() - 1));
            }
        }
        String[] displayItemArray = new String[comboboxList.size()];
        for (int i = 0; i < comboboxList.size(); i++) {
            displayItemArray[i] = (String) comboboxList.get(i).get("displayItem");
        }
        dataModel = new SimpleListModel(displayItemArray);
    }

    // Getters and setters
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDataItem() {
        return dataItem;
    }

    public void setDataItem(String dataItem) {
        this.dataItem = dataItem;
    }

    public ListModel getDataModel() {
        return dataModel;
    }

    public void setDataModel(ListModel dataModel) {
        this.dataModel = dataModel;
    }
}
