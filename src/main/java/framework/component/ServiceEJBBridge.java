/*
 ********************************************************************
 * Copyright (c) 1993-1998 by Lancer Systems Co.,Ltd.
 *
 * All rights reserved. No part of this program or document may be reproduced in any form or by any
 * means without permission in writing from Lancer Systems Corp.
 *
 * 本程式未經寶盛系統授權,不得使用或複製. 台(83)內著字第8383902號
 ********************************************************************
 */

/*
 * Modifications: $Log:$
 */

package framework.component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.naming.Context;
import javax.naming.InitialContext;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import framework.login.LCLoginRecord;
import tw.com.lancer.team.framework.constant.LCFrameworkConstant;
import tw.com.lancer.team.framework.domain.LCDomainException;
import tw.com.lancer.team.framework.facade.ejbfacade.LCSessionFacade;
import tw.com.lancer.team.framework.facade.ejbfacade.LCSessionFacadeHome;
import tw.com.lancer.team.framework.message.LCMessageSet;
import tw.com.lancer.team.framework.utility.LCDataSecurity;
import tw.com.lancer.team.framework.utility.LCPasswordEncrypter;
import tw.com.lancer.team.framework.utility.LCSerializableObjectCompressor;

public abstract class ServiceEJBBridge {
    private Serializable serial = null;
    protected final String SERVICE_COMPONENT = this.getClass().getName().substring(this.getClass().getName().lastIndexOf('.') + 1);

    protected final Map sendServiceRequest(String serviceComponentId, String serviceId, LCLoginRecord loginRecord, HashMap params) {
        // 檢核資料的正確性.
        String errorMsg = "";

        if (serviceComponentId == null || serviceComponentId.length() == 0) {
            errorMsg += "ServiceComponentId is empty. ";
        }
        if (serviceId == null || serviceComponentId.length() == 0) {
            errorMsg += "ServiceId is empty. ";
        }
        if (params == null) {
            errorMsg += "params is null. ";
        }
        if (loginRecord == null) {
            errorMsg += "LoginRecord is null. ";
        } else {
            if (loginRecord.getUsername() == null || loginRecord.getUsername().length() < 1) {
                errorMsg += "Username is empty. ";
            }
            if (loginRecord.getPassword() == null || loginRecord.getPassword().length() < 1) {
                errorMsg += "Password is empty. ";
            }
            if (loginRecord.getTargetIP() == null || loginRecord.getTargetIP().length() < 1) {
                errorMsg += "TargetIP is empty. ";
            }
            if (loginRecord.getTargetpassword() == null) {
                errorMsg += "Targetpassword is null. ";
            }
            if (loginRecord.getTenant() == null || loginRecord.getTenant().length() < 1) {
                errorMsg += "Tenant is empty. ";
            }
        }

        if (errorMsg != null && errorMsg.length() > 1) {
            return null;
        }

        params.put("SENDERACCOUNT", loginRecord.getUsername());

        try {
            LCDataSecurity.setKey(loginRecord.getTargetpassword());
        } catch (LCDomainException e1) {
            e1.printStackTrace();
        }

        params.put("SENDERPASSWORD", LCDataSecurity.getHashedPassword(LCPasswordEncrypter.encrypt(loginRecord.getPassword())));
        params.put("SENDERTENANTID", loginRecord.getTenant());
        params.put("HOSTINFO", loginRecord.getHOSTINFO());
        params.put("HOSTLANGUAGE", loginRecord.getHOSTLANGUAGE());

        LCSessionFacade sf = loginRecord.getSessionFacade();
        LCMessageSet returnMap = null;
        try {
            if (sf == null) {
                sf = this.getLoginedSessionFacade(loginRecord);
                loginRecord.setSessionFacade(sf);
            }

            Serializable serial = sf.send(serviceComponentId, serviceId, params);

            if (serial instanceof HashMap) {
                returnMap = new LCMessageSet((HashMap) serial);
            } else if (serial instanceof byte[]) {
                returnMap = new LCMessageSet((HashMap) LCSerializableObjectCompressor.decompressFromBytes(LCDataSecurity.decrypt((byte[]) serial)));
            }

            // returnMap = new LCMessageSet((HashMap)
            // LCSerializableObjectCompressor.decompressFromBytes(LCDataSecurity.decrypt((byte[]) serial)));
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (returnMap != null) {
            if ("N".equals(returnMap.getReturnValue())) {

            }
        } else {

        }

        return returnMap;
    }

    /**
     * 取得 登入的LCSessionFacade物件.
     * 
     * @param loginRecord
     * @return
     */
    private final LCSessionFacade getLoginedSessionFacade(LCLoginRecord loginRecord) {
        Hashtable p = new Hashtable();
        p.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
        p.put(Context.PROVIDER_URL, loginRecord.getTargetIP());
        LCSessionFacade sf = null;
        try {
            // get SessionFacade begins
            Context jndiContext = new InitialContext(p);
            Object obj = jndiContext.lookup("LCSessionFacade");
            Hashtable ht = jndiContext.getEnvironment();

            LCSessionFacadeHome home = (LCSessionFacadeHome) javax.rmi.PortableRemoteObject.narrow(obj, LCSessionFacadeHome.class);
            sf = home.create();
            // get SessionFacade ends

            // Login SessionFacade begins
            HashMap loginParam = new HashMap();
            loginParam.put("SENDERACCOUNT", loginRecord.getUsername());

            try {
                System.err.println("getTargetpassword===" + loginRecord.getTargetpassword());
                LCDataSecurity.setKey(loginRecord.getTargetpassword());
            } catch (LCDomainException e1) {
                e1.printStackTrace();
            }

            loginParam.put("SENDERPASSWORD", LCDataSecurity.getHashedPassword(LCPasswordEncrypter.encrypt(loginRecord.getPassword())));
            loginParam.put("SENDERTENANTID", loginRecord.getTenant());
            loginParam.put("HOSTINFO", loginRecord.getHOSTINFO());
            loginParam.put("LANGUAGETYPE", loginRecord.getHOSTLANGUAGE());
            loginParam.put("HOSTLANGUAGE", loginRecord.getHOSTLANGUAGE());
            loginParam.put("USERACCOUNT", loginRecord.getUsername());
            loginParam.put("PASSWORD", LCDataSecurity.getHashedPassword(LCPasswordEncrypter.encrypt(loginRecord.getPassword())));

            serial = sf.send("UC_ADM_LO_USERLOGIN", "QRYUSERLOGIN", loginParam);
            System.out.println("================================================" + serial);
            // Login SessionFacade ends
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sf;
    }

    /**
     * 
     * @param loginRecord
     * @return
     */
    protected final Map login(LCLoginRecord loginRecord) {
        Map result = null;
        try {
            LCSessionFacade sf = this.getLoginedSessionFacade(loginRecord);


            if (serial instanceof HashMap) {
                result = new LCMessageSet((HashMap) serial);
            } else if (serial instanceof byte[]) {
                result = new LCMessageSet((HashMap) LCSerializableObjectCompressor.decompressFromBytes(LCDataSecurity.decrypt((byte[]) serial)));
            }

            // result = new LCMessageSet((HashMap)
            // LCSerializableObjectCompressor.decompressFromBytes(LCDataSecurity.decrypt((byte[]) serial)));

            loginRecord.setSessionFacade((LCSessionFacade) result.get("sf"));
        } catch (Exception e) {
            e.printStackTrace();
            result = null;
        }

        return result;
    }


    /**
     * Returns the output from the given URL.
     * 
     * I tried to hide some of the ugliness of the exception-handling in this method, and just return a
     * high level Exception from here. Modify this behavior as desired.
     * 
     * @param desiredUrl
     * @return
     * @throws Exception
     */
    public HashMap doHttpUrlConnectionAction(LCLoginRecord loginRecord, HashMap gsonStrParameter) throws Exception {
        // 檢核資料的正確性.
        String errorMsg = "";

        if (gsonStrParameter.get("USECASE") == null || gsonStrParameter.get("USECASE").equals("")) {
            errorMsg += "ServiceComponentId is empty. ";
        }
        if (gsonStrParameter.get("SERVICEID") == null || gsonStrParameter.get("SERVICEID").equals("")) {
            errorMsg += "ServiceId is empty. ";
        }

        if (loginRecord == null) {
            errorMsg += "LoginRecord is null. ";
        } else {
            if (loginRecord.getUsername() == null || loginRecord.getUsername().length() < 1) {
                errorMsg += "Username is empty. ";
            }
            if (loginRecord.getPassword() == null || loginRecord.getPassword().length() < 1) {
                errorMsg += "Password is empty. ";
            }
            if (loginRecord.getTargetIP() == null || loginRecord.getTargetIP().length() < 1) {
                errorMsg += "TargetIP is empty. ";
            }
            if (loginRecord.getTargetpassword() == null) {
                errorMsg += "Targetpassword is null. ";
            }
            if (loginRecord.getTenant() == null || loginRecord.getTenant().length() < 1) {
                errorMsg += "Tenant is empty. ";
            }
        }

        if (errorMsg != null && errorMsg.length() > 1) {
            return null;
        }

        gsonStrParameter.put("SENDERACCOUNT", loginRecord.getUsername());
        gsonStrParameter.put("SENDERPASSWORD", loginRecord.getPassword());
        gsonStrParameter.put("SENDERTENANTID", loginRecord.getTenant());
        gsonStrParameter.put("HOSTINFO", loginRecord.getHOSTINFO());

        Gson gson = new Gson();
        String gsonStrParameters = gson.toJson(gsonStrParameter);

        URL url = null;
        BufferedReader reader = null;
        StringBuffer stringBuffer;

        try {
            String ipStr = loginRecord.getTargetIP();
            ipStr = ipStr.substring(0, ipStr.lastIndexOf(":"));
            String port = "8280";

            if (loginRecord.getTargetIP().indexOf("1199") != -1) {
                port = "8180";
            }

            url = new URL("http://" + ipStr + ":" + port + "/LCJsonBridge/JsonService.do");

            System.err.println("TEST===" + url.toString());


            // url = new URL("http://211.75.139.35:8180/LCJsonBridge/JsonService.do");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            connection.setRequestProperty("Accept", "application/json");
            // connection.setRequestProperty("Accept-Charset", "utf-8");
            connection.setRequestMethod("POST");

            OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
            // new OutputStreamWriter(connection.getOutputStream());
            wr.write(gsonStrParameters);
            wr.flush();

            reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8")); // 設置編碼,否則中文亂碼

            stringBuffer = new StringBuffer();
            String line = null;

            while ((line = reader.readLine()) != null) {
                byte[] b = line.getBytes("utf-8");
                stringBuffer.append(new String(b, "utf-8") + "\n"); // , "UTF-8"
            }


            HashMap paramJson = new HashMap();
            paramJson = gson.fromJson(stringBuffer.toString(), HashMap.class);

            HashMap param = getNeoMap(paramJson);

            return param;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            // close the reader; this can throw an exception too, so
            // wrap it in another try/catch block.
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }
    }

    private HashMap getNeoMap(HashMap jsonMap) {
        HashMap reply = new HashMap();
        Set keySet = jsonMap.keySet();
        Iterator it = keySet.iterator();
        while (it.hasNext()) {
            String oneKey = (String) it.next();
            Object oneEntry = jsonMap.get(oneKey);
            if (LCFrameworkConstant.LCPAGESIZE.equals(oneKey) || LCFrameworkConstant.LCPAGENUMBER.equals(oneKey)) {
                reply.put(oneKey, ((Double) oneEntry).intValue());
                continue;
            }

            if (oneEntry instanceof List) {
                reply.put(oneKey, new ArrayList(getNeoList((List) oneEntry)));
            } else if (oneEntry instanceof Double) {
                reply.put(oneKey, new BigDecimal((Double) oneEntry).setScale(8, BigDecimal.ROUND_HALF_UP));
            } else {
                reply.put(oneKey, oneEntry);
            }
        }
        return reply;
    }

    private List getNeoList(List inputList) {
        if (inputList.size() == 0) {
            return new ArrayList();
        }
        ArrayList replyList = new ArrayList();
        List subOutterList = new ArrayList(inputList);
        for (int i = 0; i < inputList.size(); i++) {
            Object oneOutterEntry = subOutterList.get(i);
            HashMap innerReplyMap = new HashMap();
            if (oneOutterEntry instanceof LinkedTreeMap) {
                Set keySet = ((LinkedTreeMap) oneOutterEntry).keySet();
                Iterator it = keySet.iterator();
                while (it.hasNext()) {
                    String oneKey = (String) it.next();
                    Object oneEntry = ((LinkedTreeMap) oneOutterEntry).get(oneKey);
                    if (oneEntry instanceof List) {
                        // 20141009-ADD01
                        // if(((List)oneEntry).size() == 0)
                        // {
                        // continue;
                        // }
                        // /20141009-ADD01
                        innerReplyMap.put(oneKey, getNeoList((List) oneEntry));
                    } else if (oneEntry instanceof Double) {
                        innerReplyMap.put(oneKey, new BigDecimal((Double) oneEntry).setScale(8, BigDecimal.ROUND_HALF_UP));
                    } else {
                        innerReplyMap.put(oneKey, oneEntry);
                    }
                }
                replyList.add(innerReplyMap);
            } else if (oneOutterEntry instanceof List) {
                List middleList = getNeoList((List) oneOutterEntry);
                if (middleList.size() == 0) {
                    continue;
                } else {
                    replyList.add(middleList);
                }

            } else {
                replyList.add(oneOutterEntry);
            }
        }
        return replyList;
    }

    public static void main(String[] args) {
        LCLoginRecord loginRecord = new LCLoginRecord();
        loginRecord.setUsername("ADMIN");
        loginRecord.setPassword("ADMIN");
        loginRecord.setTenant("0167");
        loginRecord.setTargetIP("192.168.2.26");
    }

}
