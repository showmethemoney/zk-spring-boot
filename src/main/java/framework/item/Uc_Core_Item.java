package framework.item;

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

public class Uc_Core_Item {
    protected static final Logger logger = LoggerFactory.getLogger(Uc_Core_Item.class);

    public HashMap crtItem(TWStockPrice stock, LCLoginRecord loginRecord) {
        HashMap result = null;

        try {
            Service service = new Service();
            HashMap parameters = new HashMap();
            parameters.put("SITEID", Uc_Core_Constant.NAMED_PARAMTER_VALUE_SITEID);
            parameters.put("ITEMID", stock.getCode()); 

            Map response = service.request("UC_CORE_ITEM ", "QRYITEM", loginRecord, parameters);
            List<HashMap> responseData = (List<HashMap>) response.get("DATA");
            
            if (0 != responseData.size()) {
                result = responseData.get(0);
            } else {
                parameters.put("SITEID", Uc_Core_Constant.NAMED_PARAMTER_VALUE_SITEID);
                parameters.put("ITEMID", "0001"); // 固定抓 0001 當作 Template
                
                response = service.request("UC_CORE_ITEM ", "QRYITEM", loginRecord, parameters);
                responseData = (List<HashMap>) response.get("DATA");
                
                result = responseData.get(0);

                Calendar calendar = Calendar.getInstance();
                // 覆蓋原本資訊
                result.put("SITEID", Uc_Core_Constant.NAMED_PARAMTER_VALUE_SITEID);
                result.put("ITEMID", stock.getCode());
                result.put("ITEMNAME", stock.getName());
                result.put("ITEMENGNAME", stock.getName());
                result.put("SPECIFICATION", stock.getLastPrice()); // 價格

                response = service.request("UC_CORE_ITEM ", "CRTITEM", loginRecord, result);

                logger.info("RETURNVALUE: {}, RETURNMSG: {}, ITEMID: {}", new Object[] {response.get("RETURNVALUE"), response.get("RETURNMSG"), response.get("ITEMID")});
            }
        } catch (Throwable cause) {
            logger.error(cause.getMessage(), cause);
        }

        return result;
    }

}
