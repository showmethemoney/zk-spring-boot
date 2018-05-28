package framework.component;

import java.lang.reflect.Method;
import framework.login.LCLoginRecord;

public abstract class LCWSDLService {
    protected void fillLoginRecord(Object stub, LCLoginRecord loginRecord) {
        try {
            Class[] StringClass = {String.class};
            Method setSENDERTENANTID = stub.getClass().getMethod("setSENDERTENANTID", StringClass);
            Method setSENDERACCOUNT = stub.getClass().getMethod("setSENDERACCOUNT", StringClass);
            Method setSENDERPASSWORD = stub.getClass().getMethod("setSENDERPASSWORD", StringClass);
            Method setHOSTINFO = stub.getClass().getMethod("setHOSTINFO", StringClass);
            Method setHOSTLANGUAGE = stub.getClass().getMethod("setHOSTLANGUAGE", StringClass);

            setSENDERTENANTID.invoke(stub, loginRecord.getTenant());
            setSENDERACCOUNT.invoke(stub, loginRecord.getUsername());
            setSENDERPASSWORD.invoke(stub, loginRecord.getPassword());
            setHOSTINFO.invoke(stub, "ABC");
            setHOSTLANGUAGE.invoke(stub, "ABC");
        } catch (Exception e) {
            System.out.println(this.getClass().getName() + " " + e);
        }
    }
}
