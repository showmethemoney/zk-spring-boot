package test.com.mycompany.framework.inventory;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import framework.Uc_Core_Constant;
import framework.inventory.Uc_Inv_UnplanInventoryIn;
import framework.login.LCLoginRecord;
import framework.login.UC_ADM_LO_USERLOGIN;
import tw.com.lancer.Service;
import zk.springboot.bean.TWStockPrice;

public class TestUc_Inv_UnplanInventoryIn {
    protected static final Logger logger = LoggerFactory.getLogger(TestUc_Inv_UnplanInventoryIn.class);

    @Test
    public void testQryUnplanInventoryIn() {
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
            Map response = service.request(Uc_Core_Constant.NAMED_SERVICE_UC_INV_UNPLANINVENTORYIN, "QRYUNPLANINVENTORYIN", loginRecord, new HashMap());

            logger.info("RETURNVALUE: {}, RETURNMSG: {}", response.get("RETURNVALUE"), response.get("RETURNMSG"));
            List<HashMap> records = (List<HashMap>) response.get("DATA");

            for (HashMap record : records) {
                String sitedId = (String) record.get("SITEID");
                String siteName = (String) record.get("SITENAME");
                String uino = (String) record.get("UINO");
                
                HashMap detailParameters = new HashMap();
                detailParameters.put("SITEID", "R14");
                detailParameters.put("UINO", uino);
                
                logger.info("SITEID : {}, SITENAME : {}, UINO : {}", new Object[] {sitedId, siteName, uino});
                
                Map detailResponse = service.request(Uc_Core_Constant.NAMED_SERVICE_UC_INV_UNPLANINVENTORYIN, "QRYUNPLANINVENTORYININBYSYSID", loginRecord, detailParameters);
                List<HashMap> detailRecords = (List<HashMap>) detailResponse.get("DATA");
                
                for(HashMap detailRecord : detailRecords) {
                    logger.info("SEQUENCENO: {}, ITEMID: {}, INVENTORYINQUANTITY :{}, REFERENCEUNITPRICE : {}", new Object[] {
                            detailRecord.get("SEQUENCENO"),detailRecord.get("ITEMID"),detailRecord.get(""),detailRecord.get("INVENTORYINQUANTITY"),
                            detailRecord.get("REFERENCEUNITPRICE")                          
                    });
                }
                
            }

        } catch (Throwable cause) {
            logger.error(cause.getMessage(), cause);
        }
    }

    @Ignore
    @Test
    public void testCrtUnplanInventoryIn() {
        try {
            LCLoginRecord loginRecord = new LCLoginRecord();

            loginRecord.setTargetpassword("");
            loginRecord.setUsername("R14");
            loginRecord.setPassword("lancer123");
            loginRecord.setTargetIP("140.136.155.8:1299");
            loginRecord.setTenant("007_CERPS_SOLOMO");

            logger.info("{}", new UC_ADM_LO_USERLOGIN().isAllowed(loginRecord));

            loginRecord.loginSuccess("127.0.0.1");

            Uc_Inv_UnplanInventoryIn in = new Uc_Inv_UnplanInventoryIn();
            TWStockPrice stock = new TWStockPrice();
            stock.setCode("2418.tw"); // ****
            stock.setLastPrice("100.2");
            String uino = in.crtUnplanInventoryIn(stock, 100, loginRecord);
            logger.info("{}", in.confirm(uino, loginRecord));

        } catch (Throwable cause) {
            logger.error(cause.getMessage(), cause);
        }
    }

    @Ignore
    @Test
    public void testQuery() {
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

            Map response = service.request("UC_INV_UNPLANINVENTORYIN", "QRYSTANDARDUNPLANINVENTORYIN", loginRecord, new HashMap());

            List<Map<String, Object>> result = (List<Map<String, Object>>) response.get("UIDATA");

            for (Map record : result) {
                logger.info("TRANSACTIONTYPEID: {}, ISSUEEMPLOYEEID: {}, SITEID: {}, ISSUEDATE: {}, TRANSACTIONDATE: {}",
                        new Object[] {record.get("TRANSACTIONTYPEID"), record.get("ISSUEEMPLOYEEID"), record.get("SITEID"), record.get("ISSUEDATE"), record.get("TRANSACTIONDATE")});

                List<Map<String, Object>> data = (List<Map<String, Object>>) record.get("UIINVENTORYINDATA");

                for (Map<String, Object> d : data) {
                    logger.info("     SEQUENCENO: {}, ITEMID: {}, WAREHOUSEID: {}, INVENTORYINQUANTITY: {}, REFERENCEUNITPRICE: {},", new Object[] {d.get("SEQUENCENO"), d.get("ITEMID"),
                            d.get("WAREHOUSEID"), ((BigDecimal) d.get("INVENTORYINQUANTITY")).toPlainString(), ((BigDecimal) d.get("REFERENCEUNITPRICE")).toPlainString()});
                }

                // TRANSACTIONTYPEID: UnplanIn, ISSUEEMPLOYEEID: R14, SITEID: R14,
            }
        } catch (Throwable cause) {
            logger.error(cause.getMessage(), cause);
        }
    }
}
