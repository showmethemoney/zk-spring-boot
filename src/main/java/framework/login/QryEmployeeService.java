/*
 ********************************************************************
 * Copyright (c) 1993-1998 by Lancer Systems Co.,Ltd.
 *
 * All rights reserved. No part of this program or document may be reproduced in any form or by any
 * means without permission in writing from Lancer Systems Corp.
 *
 * ���{�����g�_���t�α��v,���o�ϥΩνƻs. �x(83)���ۦr��8383902��.
 ********************************************************************
 */

/*
 * Modifications: $Log:$
 */
package framework.login;

import java.util.HashMap;
import java.util.Map;

public class QryEmployeeService {
    private String SYSID;
    private String MINDEPARTMENTID;
    private String MAXDEPARTMENTID;

    public String getSYSID() {
        return SYSID;
    }

    public void setSYSID(String sYSID) {
        SYSID = sYSID;
    }

    public String getMINDEPARTMENTID() {
        return MINDEPARTMENTID;
    }

    public void setMINDEPARTMENTID(String mINDEPARTMENTID) {
        MINDEPARTMENTID = mINDEPARTMENTID;
    }

    public String getMAXDEPARTMENTID() {
        return MAXDEPARTMENTID;
    }

    public void setMAXDEPARTMENTID(String mAXDEPARTMENTID) {
        MAXDEPARTMENTID = mAXDEPARTMENTID;
    }

    public Map QRYCUSTOMER(LCLoginRecord loginRecord) {
        Map response = null;
        try {
            UC_CORE_EMPLOYEE uC_CORE_EMPLOYEE = new UC_CORE_EMPLOYEE();
            HashMap params = new HashMap();
            params.put("MAXDEPARTMENTID", this.getMAXDEPARTMENTID());
            params.put("MINDEPARTMENTID", this.getMINDEPARTMENTID());
            params.put("SYSID", this.getSYSID());
            params.put("ENTITYID", loginRecord.getENTITYID());

            System.err.println("params==" + params);

            response = uC_CORE_EMPLOYEE.QRYEMPLOYEE(loginRecord, params);

            System.err.println("response==" + response);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("!!!!! QryEmployeeService.QRYCUSTOMER FAIL !!!!!");
        }
        return response;
    }
}
