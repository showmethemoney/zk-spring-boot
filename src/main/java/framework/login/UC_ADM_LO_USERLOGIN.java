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

import java.util.List;
import java.util.Map;
import framework.component.ServiceEJBBridge;
import tw.com.lancer.team.framework.facade.ejbfacade.LCSessionFacade;

// UC_ADM_LO_USERLOGIN �A�Ȥ���
public final class UC_ADM_LO_USERLOGIN extends ServiceEJBBridge {
    /**
     * �P�_loginRecord�O�_���\�n�J�C
     * 
     * @param loginRecord
     * @return
     */
    public boolean isAllowed(LCLoginRecord loginRecord) {
        boolean isLogined = false;

        Map result = this.login(loginRecord);

        if (result.get("RETURNVALUE").equals("N")) {
            loginRecord.setLoginFailMsg(result.get("RETURNMSG").toString());

            return false;
        }

        if (result != null && result.size() > 0) {
            loginRecord.setLoginFailMsg("");
            loginRecord.setSessionFacade((LCSessionFacade) result.get("sf"));
            loginRecord.setALLOWEDSITELIST((List<Map>) result.get("ALLOWEDSITELIST"));
            loginRecord.setALLOWEDENTITYLIST((List<Map>) result.get("ALLOWEDENTITYLIST"));
            QryEmployeeService qryEmployeeService = new QryEmployeeService();
            qryEmployeeService.setSYSID((String) result.get("EMPLOYEE"));

            Map employee = ((List<Map>) qryEmployeeService.QRYCUSTOMER(loginRecord).get("DATA")).get(0);

            loginRecord.setENTITYID((String) employee.get("ENTITYID"));
            loginRecord.setENTITYNAME((String) employee.get("ENTITYNAME"));
            loginRecord.setEMPLOYEEID((String) employee.get("EMPLOYEEID"));
            loginRecord.setEMPLOYEENAME((String) employee.get("EMPLOYEENAME"));
            loginRecord.setEMPLOYEESTATUS((String) employee.get("EMPLOYEESTATUS"));
            loginRecord.setDEPARTMENTID((String) employee.get("DEPARTMENTID"));
            loginRecord.setDEPARTMENTNAME((String) employee.get("DEPARTMENTNAME"));
            loginRecord.setPOSITIONID((String) employee.get("POSITIONID"));
            loginRecord.setPOSITIONNAME((String) employee.get("POSITIONNAME"));
            loginRecord.setPROFESSIONALTITLE((String) employee.get("PROFESSIONALTITLE"));
            loginRecord.setDIRECTSUPERVISORID((String) employee.get("DIRECTSUPERVISORID"));
            loginRecord.setDIRECTSUPERVISORNAME((String) employee.get("DIRECTSUPERVISORNAME"));

            for (Map ea : (List<Map>) result.get("ALLOWEDSITELIST")) {
                if (result.get("DEFAULTSITESYSID").equals(ea.get("SITESYSID"))) {
                    loginRecord.setSITEID((String) ea.get("SITEID"));
                    loginRecord.setENTITYID((String) ea.get("ENTITYID"));
                    break;
                }
            }

            isLogined = true;
        }

        return isLogined;
    }
}
