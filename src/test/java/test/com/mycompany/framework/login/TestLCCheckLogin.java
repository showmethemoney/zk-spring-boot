package test.com.mycompany.framework.login;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import framework.login.LCLoginRecord;
import framework.login.UC_ADM_LO_USERLOGIN;
import framework.login.UC_TOOL_FT_VOCABULARYFREEUPDATE;

public class TestLCCheckLogin {

    protected static Logger logger = LoggerFactory.getLogger(TestLCCheckLogin.class);

    @Test
    public void testLogin() {
        try {
            LCLoginRecord loginRecord = new LCLoginRecord();

            loginRecord.setTargetpassword("");
            loginRecord.setUsername("R14");
            loginRecord.setPassword("lancer123");
            loginRecord.setTargetIP("140.136.155.8:1299");
            loginRecord.setTenant("007_CERPS_SOLOMO");

            logger.info("{}", new UC_ADM_LO_USERLOGIN().isAllowed(loginRecord));

            loginRecord.loginSuccess("127.0.0.1");

//            session.setAttribute("loginRecord", loginRecord);
            new UC_TOOL_FT_VOCABULARYFREEUPDATE().QRYVOCABULARYFREEUPDATE(loginRecord);
        } catch (Throwable cause) {
            logger.error(cause.getMessage(), cause);
        }
    }
}
