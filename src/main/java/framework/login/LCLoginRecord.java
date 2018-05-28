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

import java.util.Date;
import java.util.List;
import java.util.Map;
import tw.com.lancer.team.framework.facade.ejbfacade.LCSessionFacade;

public class LCLoginRecord {
    private long lastRequestTime;
    private String SITEID;
    private String username;
    private String password;
    private String fromIP;
    private long loginTime;
    private int countFailureTimes;
    private boolean locked;
    private boolean logined;
    private String tenant;
    private String targetIP;
    private String targetpassword; // 應用伺服器密碼
    private String HOSTINFO = "ABC";
    private String HOSTLANGUAGE = "ABC";
    private LCSessionFacade sessionFacade;
    private List<Map> ALLOWEDSITELIST;
    private List<Map> ALLOWEDENTITYLIST;
    private String ENTITYID;
    private String ENTITYNAME;
    private String EMPLOYEEID;
    private String EMPLOYEENAME;
    private String EMPLOYEESTATUS;
    private String DEPARTMENTID;
    private String DEPARTMENTNAME;
    private String POSITIONID;
    private String POSITIONNAME;
    private String PROFESSIONALTITLE;
    private String DIRECTSUPERVISORID;
    private String DIRECTSUPERVISORNAME;

    private String loginFailMsg = "";

    /**
     * �B�zLogin Fail
     * 
     * @param username
     * @param password
     * @param loginIP
     */
    public void loginFail(final String username, final String password, final String loginIP) {

        // �x�sip,loginTime
        this.setFromIP(loginIP);
        this.setLoginTime(new Date().getTime());

        // �Nlogined�]�w��false
        this.setLogined(false);

        // �p�GcountFailureTimes�[1�C
        countFailureTimes++;

        // �YcountFailureTimes����ζW�L�T�A�N�Nlocked�אּtrue
        if (countFailureTimes >= 3) {
            System.out.println("Start Lock:" + loginIP);
            this.setLocked(true);
        }
    }

    /**
     * �B�zLogin Success
     * 
     * @param loginIP
     */
    public void loginSuccess(String loginIP) {

        // �x�sip
        this.setFromIP(loginIP);

        // �x�sloginTime
        this.setLoginTime(new Date().getTime());

        // �Nlogined�]�w��true
        this.setLogined(true);

        // �Nlock�]�w��false
        this.setLocked(false);

        // �NcountFailureTimes�M�šC
        countFailureTimes = 0;

    }

    public boolean isLocked() {
        final long minUnlockInterval = 10000; // ms
        if (locked == true && this.getLoginTime() != 0 && (new Date().getTime() - this.getLoginTime()) > minUnlockInterval) {
            System.out.println("Unlock Interval :" + (new Date().getTime() - this.getLoginTime()) + " ms");
            this.setCountFailureTimes(0);
            this.setLoginTime(0);
            this.setLoginTime(new Date().getTime());
            this.setLocked(false);
        }

        return locked;
    }

    public String getFromIP() {
        return fromIP;
    }

    public void setFromIP(String fromIP) {
        this.fromIP = fromIP;
    }

    public String getEMPLOYEEID() {
        return EMPLOYEEID;
    }

    public void setEMPLOYEEID(String eMPLOYEEID) {
        EMPLOYEEID = eMPLOYEEID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(long loginTime) {
        this.loginTime = loginTime;
    }

    public int getCountFailureTimes() {
        return countFailureTimes;
    }

    public void setCountFailureTimes(int countFailureTimes) {
        this.countFailureTimes = countFailureTimes;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public boolean isLogined() {
        return logined;
    }

    public void setLogined(boolean logined) {
        this.logined = logined;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
    }

    public String getTenant() {
        return tenant;
    }

    public String getTargetIP() {
        return targetIP;
    }

    public void setTargetIP(String targetIP) {
        this.targetIP = targetIP;
    }

    public String getTargetpassword() {
        return targetpassword;
    }

    public void setTargetpassword(String targetpassword) {
        this.targetpassword = targetpassword;
    }

    public long getLastRequestTime() {
        return lastRequestTime;
    }

    public void setLastRequestTime(long lastRequestTime) {
        this.lastRequestTime = lastRequestTime;
    }

    public String getHOSTINFO() {
        return HOSTINFO;
    }

    public void setHOSTINFO(String hOSTINFO) {
        HOSTINFO = hOSTINFO;
    }

    public String getHOSTLANGUAGE() {
        return HOSTLANGUAGE;
    }

    public void setHOSTLANGUAGE(String hOSTLANGUAGE) {
        HOSTLANGUAGE = hOSTLANGUAGE;
    }

    public LCSessionFacade getSessionFacade() {
        return sessionFacade;
    }

    public void setSessionFacade(LCSessionFacade sessionFacade) {
        this.sessionFacade = sessionFacade;
    }

    public List<Map> getALLOWEDSITELIST() {
        return ALLOWEDSITELIST;
    }

    public void setALLOWEDSITELIST(List<Map> aLLOWEDSITELIST) {
        ALLOWEDSITELIST = aLLOWEDSITELIST;
    }

    public List<Map> getALLOWEDENTITYLIST() {
        return ALLOWEDENTITYLIST;
    }

    public void setALLOWEDENTITYLIST(List<Map> aLLOWEDENTITYLIST) {
        ALLOWEDENTITYLIST = aLLOWEDENTITYLIST;
    }

    public String getSITEID() {
        return SITEID;
    }

    public void setSITEID(String sITEID) {
        SITEID = sITEID;
    }

    public String getENTITYID() {
        return ENTITYID;
    }

    public void setENTITYID(String eNTITYID) {
        ENTITYID = eNTITYID;
    }

    public String getENTITYNAME() {
        return ENTITYNAME;
    }

    public void setENTITYNAME(String eNTITYNAME) {
        ENTITYNAME = eNTITYNAME;
    }

    public String getEMPLOYEENAME() {
        return EMPLOYEENAME;
    }

    public void setEMPLOYEENAME(String eMPLOYEENAME) {
        EMPLOYEENAME = eMPLOYEENAME;
    }

    public String getEMPLOYEESTATUS() {
        return EMPLOYEESTATUS;
    }

    public void setEMPLOYEESTATUS(String eMPLOYEESTATUS) {
        EMPLOYEESTATUS = eMPLOYEESTATUS;
    }

    public String getDEPARTMENTID() {
        return DEPARTMENTID;
    }

    public void setDEPARTMENTID(String dEPARTMENTID) {
        DEPARTMENTID = dEPARTMENTID;
    }

    public String getDEPARTMENTNAME() {
        return DEPARTMENTNAME;
    }

    public void setDEPARTMENTNAME(String dEPARTMENTNAME) {
        DEPARTMENTNAME = dEPARTMENTNAME;
    }

    public String getPOSITIONID() {
        return POSITIONID;
    }

    public void setPOSITIONID(String pOSITIONID) {
        POSITIONID = pOSITIONID;
    }

    public String getPOSITIONNAME() {
        return POSITIONNAME;
    }

    public void setPOSITIONNAME(String pOSITIONNAME) {
        POSITIONNAME = pOSITIONNAME;
    }

    public String getPROFESSIONALTITLE() {
        return PROFESSIONALTITLE;
    }

    public void setPROFESSIONALTITLE(String pROFESSIONALTITLE) {
        PROFESSIONALTITLE = pROFESSIONALTITLE;
    }

    public String getDIRECTSUPERVISORID() {
        return DIRECTSUPERVISORID;
    }

    public void setDIRECTSUPERVISORID(String dIRECTSUPERVISORID) {
        DIRECTSUPERVISORID = dIRECTSUPERVISORID;
    }

    public String getDIRECTSUPERVISORNAME() {
        return DIRECTSUPERVISORNAME;
    }

    public void setDIRECTSUPERVISORNAME(String dIRECTSUPERVISORNAME) {
        DIRECTSUPERVISORNAME = dIRECTSUPERVISORNAME;
    }

    public void setLoginFailMsg(String loginFailMsg) {
        this.loginFailMsg = loginFailMsg;
    }

    public String getLoginFailMsg() {
        return loginFailMsg;
    }
}
