package framework.inventory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import framework.Uc_Core_Constant;
import framework.login.LCLoginRecord;
import tw.com.lancer.Service;
import zk.springboot.bean.TWStockPrice;

/**
 * http://140.136.155.9:8280/LCServiceDoc/tw/com/lancer/team/constant/usecase/inventory/inventory/UC_INV_UNPLANINVENTORYIN.html#CONFIRM
 *
 */

public class Uc_Inv_UnplanInventoryIn {
    protected static final Logger logger = LoggerFactory.getLogger(Uc_Inv_UnplanInventoryIn.class);

    public String crtUnplanInventoryIn(TWStockPrice stock, int quantity, LCLoginRecord loginRecord) {
        String result = null;

        try {
            // TRANSACTIONTYPEID: UnplanIn, ISSUEEMPLOYEEID: R14, SITEID: R14, ISSUEDATE: 20180429,
            // TRANSACTIONDATE: 20180429
            // SEQUENCENO: 0010, ITEMID: C201704011, WAREHOUSEID: FJUG17004, INVENTORYINQUANTITY: 1000.00000000,
            // REFERENCEUNITPRICE: 0.00000000,
            // SEQUENCENO: 0020, ITEMID: C201704015, WAREHOUSEID: FJUG17004, INVENTORYINQUANTITY: 1000.00000000,
            // REFERENCEUNITPRICE: 0.00000000,
            
            Calendar calendar = Calendar.getInstance();
            
            HashMap<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("SITEID", Uc_Core_Constant.NAMED_PARAMTER_VALUE_SITEID); // 營運點代號
            parameters.put("ISSUEEMPLOYEEID", Uc_Core_Constant.NAMED_PARAMTER_VALUE_ISSUEEMPLOYEEID); // 開立人員職號
            parameters.put("TRANSACTIONTYPEID", Uc_Core_Constant.NAMED_PARAMTER_VALUE_TRANSACTIONTYPEID_UNPLANIN); // 交易類別代號
            parameters.put("ISSUEDATE", DateFormatUtils.format(calendar, Uc_Core_Constant.DATE_FORMAT)); // 開立日期
            parameters.put("TRANSACTIONDATE", DateFormatUtils.format(calendar, Uc_Core_Constant.DATE_FORMAT)); // 交易日期

            HashMap<String, Object> dataParameters = new HashMap<String, Object>();
            dataParameters.put("SEQUENCENO", DateFormatUtils.format(calendar, "mmss")); // 序號
            dataParameters.put("ITEMID", stock.getCode()); // 件號代號
            dataParameters.put("WAREHOUSEID", Uc_Core_Constant.NAMED_PARAMTER_VALUE_WAREHOUSEID); // 倉庫代號

            dataParameters.put("INVENTORYINQUANTITY", new BigDecimal(quantity)); // 入庫數量
            dataParameters.put("REFERENCEUNITPRICE", new BigDecimal(Math.floor(Double.parseDouble(stock.getLastPrice())))); // 參考單價
           
            List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
            data.add(dataParameters);
            parameters.put("DATA", data);
           
            Service service = new Service();
           
            Map response = service.request(Uc_Core_Constant.NAMED_SERVICE_UC_INV_UNPLANINVENTORYIN, Uc_Core_Constant.NAMED_METHOD_CRTUNPLANINVENTORYIN, loginRecord, parameters);
            
            logger.info("RETURNVALUE: {}, RETURNMSG: {}", response.get("RETURNVALUE"), response.get("RETURNMSG"));
            logger.info("SITEID: {}, UINO: {}, STATUS: {}", response.get("SITEID"), response.get("UINO"), response.get("STATUS"));

            result = (String) response.get("UINO");
        } catch (Throwable cause) {
            logger.error(cause.getMessage(), cause);
        }
        
        return result;
    }

    public String confirm(String uino, LCLoginRecord loginRecord) {
        String result = null;
        try {
            HashMap<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("SITEID", Uc_Core_Constant.NAMED_PARAMTER_VALUE_SITEID); // 營運點代號
            parameters.put("UINO", uino); // 非計劃入庫單號

            Service service = new Service();
           
            Map response = service.request(Uc_Core_Constant.NAMED_SERVICE_UC_INV_UNPLANINVENTORYIN, Uc_Core_Constant.NAMED_METHOD_CONFIRM, loginRecord, parameters);
            
            logger.info("RETURNVALUE: {}, RETURNMSG: {}", response.get("RETURNVALUE"), response.get("RETURNMSG"));
            logger.info("SITEID: {}, UINO: {}, STATUS: {}", response.get("SITEID"), response.get("UINO"), response.get("STATUS"));
            
            result = (String) response.get("STATUS");
        } catch (Throwable cause) {
            logger.error(cause.getMessage(), cause);
        }
        
        return result;
    }
    
    public void qryUnplanInventoryIn() {}

}
