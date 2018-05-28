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


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


public class LCCheckLogin {
    /**
     * �P�_request����username,password,tenant,targetIP�O�_�i�H�n�J
     * 
     * @param request
     * @return
     */
    public final static boolean checkLogin(HttpServletRequest request) {
        // ��osession,request
        final HttpSession session = request.getSession();

        // ��Xsession����loginRecord�C
        LCLoginRecord loginRecord = (LCLoginRecord) session.getAttribute("loginRecord");
        if (loginRecord == null) {
            loginRecord = new LCLoginRecord();
        }

        // �YloginRecord�w�n�J�A�N�^�еn�J���\
        if (loginRecord.isLogined()) {
            return true;
        }

        // �YloginRecord�Q��w �N�^�еn�J����
        if (loginRecord.isLocked()) {
            System.out.println(loginRecord.getFromIP() + " is locked");
            return false;
        }

        final String enteredTargetpassword = request.getParameter("targetpassword");
        final String enteredUseraccount = request.getParameter("useraccount");
        final String enteredPassword = request.getParameter("password");
        final String enteredTenant = request.getParameter("tenant");
        final String enteredTargetIP = request.getParameter("targetIP");

        // enteredPassword為空值~ enteredUsername為空值~ enteredTenant為空值~ enteredTargetIP為空值~
        // enteredTargetpassword為空值~
        if (enteredPassword != null && enteredUseraccount != null && enteredTenant != null && enteredTargetIP != null && enteredTargetpassword != null) {
            loginRecord.setTargetpassword(enteredTargetpassword);
            loginRecord.setUsername(enteredUseraccount);
            loginRecord.setPassword(enteredPassword);
            loginRecord.setTargetIP(enteredTargetIP);
            loginRecord.setTenant(enteredTenant);

            if (new UC_ADM_LO_USERLOGIN().isAllowed(loginRecord)) {
                loginRecord.loginSuccess(request.getRemoteAddr());

                session.setAttribute("loginRecord", loginRecord);
                new UC_TOOL_FT_VOCABULARYFREEUPDATE().QRYVOCABULARYFREEUPDATE(loginRecord);
                return true;
            } else {
                loginRecord.setTargetpassword("");
                loginRecord.setUsername("");
                loginRecord.setPassword("");
                loginRecord.setTargetIP("");
                loginRecord.setTenant("");
            }
        }

        loginRecord.loginFail(enteredUseraccount, enteredPassword, request.getRemoteAddr());
        session.setAttribute("loginRecord", loginRecord);
        return false;
    }
}
