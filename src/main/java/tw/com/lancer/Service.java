package tw.com.lancer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.zkoss.zul.Messagebox;
import framework.component.ServiceEJBBridge;
import framework.login.LCLoginRecord;

public class Service extends ServiceEJBBridge {
    /**
     * 使用一般服務
     * 
     * @param serviceComponentId 服務元件
     * @param service 服務
     * @param returnDataKey 回傳資料第二層資料群組KEY
     * @param loginRecord 登入者資訊
     * @param params 其他額外參數
     * @return
     */
    public List<Map> request(String serviceComponentId, String service, String returnDataKey, LCLoginRecord loginRecord, HashMap params) {
        // 宣告參數
        Map response = null;

        // 判別 serviceComponentId service returnDataKey loginRecord params 五個參數是否有正確輸入
        if (serviceComponentId.equals("") || service.equals("") || returnDataKey.equals("") || loginRecord == null || params == null) {
            Messagebox.show("參數輸入不足");
            return null;
        }

        try {
            // 呼叫服務
            response = super.sendServiceRequest(serviceComponentId, service, loginRecord, params);
        } catch (Exception e) {
            Messagebox.show("服務執行失敗");
        }
        // 判別服務回傳值
        if (response == null) {
            Messagebox.show("回傳值為空");
            return new ArrayList(); // 回傳一個空的List
        }
        if (response.get("RETURNVALUE").equals("N")) {
            Messagebox.show((String) response.get("RETURNMSG"));
            return new ArrayList(); // 回傳一個空的List
        } else {
            return (List) response.get(returnDataKey); // 回傳服務資料List
        }
    }

    /**
     * 使用一般服務
     * 
     * @param serviceComponentId 服務元件
     * @param service 服務
     * @param returnDataKey 回傳資料第二層資料群組KEY
     * @param loginRecord 登入者資訊
     * @param params 其他額外參數
     * @return
     */
    public List<Map> jrequest(String serviceComponentId, String service, String returnDataKey, LCLoginRecord loginRecord, HashMap params) {
        // 宣告參數
        Map response = null;

        // 判別 serviceComponentId service returnDataKey loginRecord params 五個參數是否有正確輸入
        if (serviceComponentId.equals("") || service.equals("") || returnDataKey.equals("") || loginRecord == null || params == null) {
            Messagebox.show("參數輸入不足");
            return null;
        }

        try {
            params.put("USECASE", serviceComponentId);
            params.put("SERVICEID", service);

            response = super.doHttpUrlConnectionAction(loginRecord, params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 判別服務回傳值
        if (response.get("RETURNVALUE").equals("N")) {
            Messagebox.show((String) response.get("RETURNMSG"));

            return new ArrayList(); // 回傳一個空的List
        } else {
            return (List) response.get(returnDataKey); // 回傳服務資料List
        }
    }

    /**
     * 使用一般服務
     * 
     * @param serviceComponentId 服務元件
     * @param service 服務
     * @param loginRecord 登入者資訊
     * @param params 其他額外參數
     * @return
     */
    public Map request(String serviceComponentId, String service, LCLoginRecord loginRecord, HashMap params) {
        // 宣告參數
        Map response = null;

        // 判別 serviceComponentId service loginRecord params 四個參數是否有正確輸入
        if (serviceComponentId.equals("") || service.equals("") || loginRecord == null || params == null) {
            Messagebox.show("參數輸入不足");
            return null;
        }

        try {
            // 呼叫服務
            response = super.sendServiceRequest(serviceComponentId, service, loginRecord, params);
        } catch (Exception e) {
            Messagebox.show("服務執行失敗");
        }


        // 判別服務回傳值
        // if (response.get("RETURNVALUE").equals("N"))
        // {
        // Messagebox.show((String)response.get("RETURNMSG"));
        // }
        return response; // 回傳服務資料HashMap
    }

    /**
     * 使用SQL指令執行服務
     * 
     * @param table 表格名稱
     * @param columns 保留的欄位List
     * @param loginRecord 登入者資訊
     * @param conditions 其他額外參數
     * @return
     */
    public List<Map> retrieve(String table, String columns, LCLoginRecord loginRecord, String conditions) {
        // 宣告參數
        Map response = null;

        // 判別 tableName keepColumnKeyList loginRecord 三個參數是否有正確輸入
        if (table.equals("") || columns.equals("") || loginRecord == null) {
            Messagebox.show("參數輸入不足");
            return null;
        }

        // 組合SQL指令字串
        String sqlStr = "select " + columns + " from " + table;

        if (conditions != null && !conditions.equals("")) {
            sqlStr = sqlStr + " where " + conditions;
        }

        // 傳送服務的PARAMETER
        HashMap sendExeSQLCommandMap = new HashMap();
        sendExeSQLCommandMap.put("EXECUTIONCONTENT", sqlStr);

        try {
            // 執行SQL指令執行服務
            response = super.sendServiceRequest("UC_ADM_CT_SQLCOMMANDEXECUTION", "EXESQLCOMMAND", loginRecord, sendExeSQLCommandMap);
        } catch (Exception e) {
            Messagebox.show("服務執行失敗");
        }

        // 判別服務回傳値
        if (response.get("RETURNVALUE").equals("N")) {
            Messagebox.show((String) response.get("RETURNMSG"));

            return new ArrayList();
        } else {
            return (List) response.get("DATA");
        }
    }


    /**
     * 呼叫服務.
     * 
     * @param loginRecord
     * @param serviceComponentId 服務元件名稱.
     * @param service 服務名稱.
     * @param sendMap 傳入參數.
     * @return
     */
    public Map callService(LCLoginRecord loginRecord, String serviceComponentId, String service, HashMap sendMap) {
        Map response = null;

        try {
            response = super.sendServiceRequest(serviceComponentId, service, loginRecord, sendMap);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    /**
     * 呼叫服務.
     * 
     * @param loginRecord
     * @param serviceComponentId 服務元件名稱.
     * @param service 服務名稱.
     * @param sendMap 傳入參數.
     * @return
     */
    public Map callService02(LCLoginRecord loginRecord, String serviceComponentId, String service, HashMap sendMap) {
        Map response = null;

        try {
            sendMap.put("USECASE", serviceComponentId);
            sendMap.put("SERVICEID", service);

            response = super.doHttpUrlConnectionAction(loginRecord, sendMap);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }
}
