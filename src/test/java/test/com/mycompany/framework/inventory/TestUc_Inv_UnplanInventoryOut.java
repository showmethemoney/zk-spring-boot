package test.com.mycompany.framework.inventory;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import framework.inventory.Uc_Inv_UnplanInventoryOut;
import framework.login.LCLoginRecord;
import framework.login.UC_ADM_LO_USERLOGIN;
import zk.springboot.bean.TWStockPrice;

public class TestUc_Inv_UnplanInventoryOut {
    protected static final Logger logger = LoggerFactory.getLogger(TestUc_Inv_UnplanInventoryIn.class);

    @Test
    public void testQryUnplanInventoryOut() {
        try {
            LCLoginRecord loginRecord = new LCLoginRecord();

            loginRecord.setTargetpassword("");
            loginRecord.setUsername("R14");
            loginRecord.setPassword("lancer123");
            loginRecord.setTargetIP("140.136.155.8:1299");
            loginRecord.setTenant("007_CERPS_SOLOMO");

            logger.info("{}", new UC_ADM_LO_USERLOGIN().isAllowed(loginRecord));

            loginRecord.loginSuccess("127.0.0.1");

            TWStockPrice stock = new TWStockPrice();
            stock.setCode("2418.tw"); // ****
            stock.setLastPrice("100.2");

            Uc_Inv_UnplanInventoryOut out = new Uc_Inv_UnplanInventoryOut();
            
            String uono = out.crtUnplanInventoryOut(stock, 100, loginRecord);
            logger.info( out.confirm(uono, loginRecord) );
        } catch (Throwable cause) {
            logger.error(cause.getMessage(), cause);
        }
    }
}
