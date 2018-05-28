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
package framework.web;

import java.util.HashMap;
import java.util.Map;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zul.Window;
import framework.login.LCLoginRecord;

public abstract class LCWindow extends Window {

    private static final long serialVersionUID = 1L;
    final private Session session = Executions.getCurrent().getSession();
    final private LCLoginRecord loginRecord = (LCLoginRecord) session.getAttribute("loginRecord");

    /**
     * get Default Entity ID
     * 
     * @return
     */
    protected String getDefaultEntityid() {
        return loginRecord.getENTITYID();
    }

    /**
     * get Default Site ID
     * 
     * @return
     */
    protected String getDefaultSiteid() {
        return loginRecord.getSITEID();
    }

    /**
     * get Employee ID
     * 
     * @return
     */
    protected String getEmployeeID() {
        return loginRecord.getEMPLOYEEID();
    }

    /**
     * get Session
     * 
     * @return
     */
    protected Session getSession() {
        return session;
    }

    /**
     * get LoginRecord
     * 
     * @return
     */
    protected LCLoginRecord getLoginRecord() {
        return loginRecord;
    }

    /**
     * get the Map of content which is sent from Mobile device
     * 
     * @return
     */
    protected Map<String, String> getMobileContentMap() {
        Map<String, String> contentMap = new HashMap<String, String>();
        if (session.getAttribute("content") != null) {
            contentMap = toMap(((String) session.getAttribute("content")).replace("{", "").replace("}", ""));
        }

        session.removeAttribute("content");
        return contentMap;
    }

    /**
     * get the string of content which is sent from Mobile device
     * 
     * @return
     */
    protected String getMobileContentString() {
        String contentString = "";
        if (session.getAttribute("content") != null) {
            contentString = (String) session.getAttribute("content");
        }

        session.removeAttribute("content");
        return contentString;
    }

    /**
     * to convert string into a map.
     * 
     * @param input
     * @return
     */
    protected Map<String, String> toMap(String input) {
        Map<String, String> map = new HashMap<String, String>();
        String[] array = input.split(",");
        for (String str : array) {
            String[] pair = str.trim().split("=");
            if (pair == null) {
                return null;
            }
            if (pair.length == 2) {
                map.put(pair[0], pair[1]);
            } else if (pair.length == 1)

            {
                map.put(pair[0], null);
            } else {
                return null;
            }
        }
        return map;
    }
}
