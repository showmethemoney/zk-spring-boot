package test.com.mycompany.framework.item;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import framework.item.Uc_Core_Item;
import framework.login.LCLoginRecord;
import framework.login.UC_ADM_LO_USERLOGIN;
import tw.com.lancer.Service;
import zk.springboot.bean.TWStockPrice;

public class TestUc_Core_Item {
    protected static final Logger logger = LoggerFactory.getLogger(TestUc_Core_Item.class);

    @Ignore
    @Test
    public void testCRTITEM() {
        try {
            LCLoginRecord loginRecord = new LCLoginRecord();

            loginRecord.setTargetpassword("");
            loginRecord.setUsername("R14");
            loginRecord.setPassword("lancer123");
            loginRecord.setTargetIP("140.136.155.8:1299");
            loginRecord.setTenant("007_CERPS_SOLOMO");

            logger.info("{}", new UC_ADM_LO_USERLOGIN().isAllowed(loginRecord));

            loginRecord.loginSuccess("127.0.0.1");

            Uc_Core_Item item = new Uc_Core_Item();

            TWStockPrice stock = new TWStockPrice();
            stock.setCode("2418.tw");
            stock.setName("Hello World");
            stock.setLastPrice("50.11");

            Map result = item.crtItem(stock, loginRecord);

            logger.info("ITEMID : {}", result.get("ITEMID"));

        } catch (Throwable cause) {
            logger.error(cause.getMessage(), cause);
        }
    }

//     @Ignore
    @Test
    public void testQRYITEM() {
        try {
            LCLoginRecord loginRecord = new LCLoginRecord();

            loginRecord.setTargetpassword("");
            loginRecord.setUsername("R14");
            loginRecord.setPassword("lancer123");
            loginRecord.setTargetIP("140.136.155.8:1299");
            loginRecord.setTenant("007_CERPS_SOLOMO");

            logger.info("{}", new UC_ADM_LO_USERLOGIN().isAllowed(loginRecord));

            loginRecord.loginSuccess("127.0.0.1");

            Service service = new Service();
            HashMap parameters = new HashMap();
            parameters.put("SITEID", "R14");
            parameters.put("ITEMID", "2418.tw");

            Map response = service.request("UC_CORE_ITEM ", "QRYITEM", loginRecord, parameters);

            List<HashMap> result = (List<HashMap>) response.get("DATA");

            logger.info("size : {}", result.size());
            if (0 == result.size()) {
            } else {
                HashMap record = result.get(0);

                logger.info("SITEID : {}", record.get("SITEID"));
                logger.info("ENTITYID : {}", record.get("ENTITYID"));
                logger.info("ITEMID : {}", record.get("ITEMID"));
                logger.info("ITEMNAME : {}", record.get("ITEMNAME"));
                logger.info("ITEMENGNAME : {}", record.get("ITEMENGNAME"));
                logger.info("SPECIFICATION : {}", record.get("SPECIFICATION"));
                logger.info("ITEMSTATUS : {}", record.get("ITEMSTATUS"));
                logger.info("ISINVENTORYCONTROLITEM : {}", record.get("ISINVENTORYCONTROLITEM"));
                logger.info("ISPHANTOMITEM : {}", record.get("ISPHANTOMITEM"));
                logger.info("ISBOMCHILDITEMDOCUMENT : {}", record.get("ISBOMCHILDITEMDOCUMENT"));
                logger.info("ISPROJECTCONTROL : {}", record.get("ISPROJECTCONTROL"));
                logger.info("INVENTORYUMID : {}", record.get("INVENTORYUMID"));
                logger.info("NETWEIGHT : {}", record.get("NETWEIGHT"));
                logger.info("INVENTORYWAREHOUSEID : {}", record.get("INVENTORYWAREHOUSEID"));
                logger.info("SHIPPINGWAREHOUSEID : {}", record.get("SHIPPINGWAREHOUSEID"));
                logger.info("LOTREFCONTROL : {}", record.get("LOTREFCONTROL"));
                logger.info("STOREOUTSORTINGRULE : {}", record.get("STOREOUTSORTINGRULE"));
                logger.info("ABCTYPE : {}", record.get("ABCTYPE"));
                logger.info("ISBACKFLUSH : {}", record.get("ISBACKFLUSH"));
                logger.info("PLANSOURCETYPE : {}", record.get("PLANSOURCETYPE"));
                logger.info("ISMPSITEM : {}", record.get("ISMPSITEM"));
                logger.info("ORDERPOLICY : {}", record.get("ORDERPOLICY"));
                logger.info("ISFORECASTITEM : {}", record.get("ISFORECASTITEM"));
                logger.info("ISPLANORDERITEM : {}", record.get("ISPLANORDERITEM"));
                logger.info("ISCRITICALITEM : {}", record.get("ISCRITICALITEM"));
                logger.info("LOTSIZINGRULE : {}", record.get("LOTSIZINGRULE"));
                logger.info("STOCKTYPEID : {}", record.get("STOCKTYPEID"));
                logger.info("CYCLECOUNTPERIOD : {}", ((BigDecimal) record.get("CYCLECOUNTPERIOD")).toPlainString());
                logger.info("INVENTORYLIFEDAYS : {}", ((BigDecimal) record.get("INVENTORYLIFEDAYS")).toPlainString());
                logger.info(" YIELDRATE: {}", ((BigDecimal) record.get("YIELDRATE")).toPlainString());
                logger.info("ORDERPERIOD : {}", ((BigDecimal) record.get("ORDERPERIOD")).toPlainString());
                logger.info("FIXORDERQUANTITY : {}", ((BigDecimal) record.get("FIXORDERQUANTITY")).toPlainString());
                logger.info("MINORDERQUANTITY : {}", ((BigDecimal) record.get("MINORDERQUANTITY")).toPlainString());
                logger.info("MAXORDERQUANTITY : {}", ((BigDecimal) record.get("MAXORDERQUANTITY")).toPlainString());
                logger.info("ORDERMULTIPLYER : {}", ((BigDecimal) record.get("ORDERMULTIPLYER")).toPlainString());
                logger.info("LEADTIME : {}", ((BigDecimal) record.get("LEADTIME")).toPlainString());
                logger.info("SAFETYTIME : {}", ((BigDecimal) record.get("SAFETYTIME")).toPlainString());
                logger.info("SAFETYQUANTITY : {}", ((BigDecimal) record.get("SAFETYQUANTITY")).toPlainString());


            }
        } catch (Throwable cause) {

        }
    }

}
