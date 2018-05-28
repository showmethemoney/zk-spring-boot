package tw.com.lancer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.zkoss.zul.Messagebox;
import framework.login.LCLoginRecord;
import framework.web.LCWindow;

public class LCBandbox extends LCWindow {
    private LCLoginRecord loginRecord = super.getLoginRecord();
    private Service service = new Service();
    public int pageSize = 0;
    private String useCase = "";
    private String serviceId = "";
    private String dataKey = "";
    private String minId = "";
    private String maxId = "";
    public List<String> outputKeys = new ArrayList();
    public String valueKey = "";
    public String value = "";
    public int currentPageNumber = 0;
    public int lastPageNumber = 0;
    public List<Map> dataList = new ArrayList();
    public HashMap dataMap = new HashMap();
    public HashMap param = new HashMap();
    public boolean opened = false;
    public boolean quickSearch = true;
    public boolean disabled = false;

    public LCBandbox(String useCase, String serviceId, String dataKey, HashMap param, String minId, String maxId, List<String> outputKeys, int pageSize, String valueKey, boolean quickSearch) {
        this.useCase = useCase;
        this.serviceId = serviceId;
        this.dataKey = dataKey;
        this.param = param;
        this.minId = minId;
        this.maxId = maxId;
        this.outputKeys = outputKeys;
        this.pageSize = pageSize;
        this.valueKey = valueKey;
        this.quickSearch = quickSearch;
        currentPageNumber = 1;
        List<Map> initialDataList = this.searchListStartingWith("");
        for (Map eaMap : initialDataList) {
            String str = "";
            for (String outputKey : outputKeys) {
                str = str + (String) eaMap.get(outputKey) + "|";
            }
            eaMap.put("displayItem", str.substring(0, str.length() - 1));
        }
        dataList = initialDataList;
    }

    public List<Map> searchListStartingWith(String prefix) {
        if (prefix == null) {
            prefix = "";
        }
        param.put("LCPAGESIZE", pageSize);
        param.put("LCPAGENUMBER", currentPageNumber);
        if (!prefix.equals("")) {
            param.put(minId, prefix);
            param.put(maxId, prefix + "zzz");
        } else {
            param.put(minId, "");
            param.put(maxId, "");
        }
        List<Map> bandboxListFull = service.request(useCase, serviceId, dataKey, loginRecord, param);
        List<Map> list = new ArrayList();
        if (bandboxListFull.size() > 0) {
            for (Map eachMap : bandboxListFull) {
                HashMap aMap = new HashMap();
                for (String eachOutputKey : outputKeys) {
                    aMap.put(eachOutputKey, (String) eachMap.get(eachOutputKey));
                }
                list.add(aMap);
            }
            dataList = list;
            lastPageNumber = getLastPageNumber(prefix);
            if (currentPageNumber == -1) {
                currentPageNumber = lastPageNumber;
            }
            for (Map eaMap : dataList) {
                String str = "";
                for (String outputKey : outputKeys) {
                    str = str + (String) eaMap.get(outputKey) + "|";
                }
                eaMap.put("displayItem", str.substring(0, str.length() - 1));
            }
        } else {
            Messagebox.show("没有以'" + prefix + "'開頭的資料.");
        }
        return list;
    }

    private int getLastPageNumber(String prefix) {
        param.put("LCPAGESIZE", pageSize);
        param.put("LCPAGENUMBER", -1);
        if (!prefix.equals("")) {
            param.put(minId, prefix);
            param.put(maxId, prefix + "zzz");
        }
        HashMap returnMap = (HashMap) service.request(useCase, serviceId, loginRecord, param);
        // LCPAGENUMBER=-1且param(ID)包含中文時,中間層實作出問題。
        return (Integer) returnMap.get("LCPAGENUMBER");
    }

    public void search(String page) {
        if (page.equals("first")) {
            searchFirstPageList();
        } else if (page.equals("previous")) {
            searchPreviousPageList();
        } else if (page.equals("next")) {
            searchNextPageList();
        } else if (page.equals("last")) {
            searchLastPageList();
        } else if (page.equals("specific")) {
            searchSpecificPageList();
        }
    }

    public void searchFirstPageList() {
        String prefix = this.getValue();
        currentPageNumber = 1;
        this.setDataList(this.searchListStartingWith(prefix));
    }

    public void searchLastPageList() {
        String prefix = this.getValue();
        currentPageNumber = -1;
        this.setDataList(this.searchListStartingWith(prefix));
    }

    public void searchNextPageList() {
        if (currentPageNumber < lastPageNumber) {
            String prefix = this.getValue();
            currentPageNumber = currentPageNumber + 1;
            this.setDataList(this.searchListStartingWith(prefix));
        }
    }

    public void searchPreviousPageList() {
        if (currentPageNumber > 1) {
            String prefix = this.getValue();
            currentPageNumber = currentPageNumber - 1;
            this.setDataList(this.searchListStartingWith(prefix));
        }
    }

    public void searchSpecificPageList() {
        String prefix = this.getValue();
        this.setDataList(this.searchListStartingWith(prefix));
    }

    public void selectBandboxDataMap(LCBandbox aBandbox) {
        aBandbox.setValue((String) aBandbox.dataMap.get(aBandbox.valueKey));
        aBandbox.setOpened(false);
        // 改變欄位值,用bandbox的dataMap的值改變UI上的欄位值(本project無)
        // 選完後bandbox應回到初始狀態
        aBandbox.setCurrentPageNumber(1);
        aBandbox.searchListStartingWith("");
        aBandbox.setDataMap(null);
    }

    // Getters and setters
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getCurrentPageNumber() {
        return currentPageNumber;
    }

    public void setCurrentPageNumber(int currentPageNumber) {
        this.currentPageNumber = currentPageNumber;
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

    public int getLastPageNumber() {
        return lastPageNumber;
    }

    public void setLastPageNumber(int lastPageNumber) {
        this.lastPageNumber = lastPageNumber;
    }

    public boolean isOpened() {
        return opened;
    }

    public void setOpened(boolean opened) {
        this.opened = opened;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public boolean isQuickSearch() {
        return quickSearch;
    }

    public void setQuickSearch(boolean quickSearch) {
        this.quickSearch = quickSearch;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }
}
