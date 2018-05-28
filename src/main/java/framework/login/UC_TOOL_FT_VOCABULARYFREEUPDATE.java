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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import framework.component.ServiceEJBBridge;
import framework.tool.vocabulary.LCVocabularyService;

public class UC_TOOL_FT_VOCABULARYFREEUPDATE extends ServiceEJBBridge {

    /**
     * �d�ߦr�J�ۥѭק�(�w�]�y�t���c�餤��)
     * 
     * @param loginRecord
     * @return
     */
    public void QRYVOCABULARYFREEUPDATE(LCLoginRecord loginRecord) {
        // �w�]�Τ���
        HashMap sendMap = new HashMap();
        sendMap.put("LANGUAGETYPE", "TraditionalChinese");

        // �I�s�A��

        Map reply = super.sendServiceRequest("UC_TOOL_FT_VOCABULARYFREEUPDATE", "QRYVOCABULARYFREEUPDATE", loginRecord, sendMap);

        if (reply != null) {
            if (!reply.containsKey("DATA")) {
                return;
            }

            ArrayList<HashMap<String, String>> list = (ArrayList<HashMap<String, String>>) reply.get("DATA");

            HashMap<String, String> dataMap = new HashMap<String, String>();

            for (int i = 0; i < list.size(); i++) {
                HashMap<String, String> map = (HashMap<String, String>) list.get(i);
                dataMap.put((String) map.get("VOCABULARYKEY"), (String) map.get("VOCABULARYVALUE"));
            }

            // �ܧ�{���r�J��--(�A�Ω�swing ��������A��O�p�G�Aweb server �ϥ�singleton �|�v�T��������ϥΪ�)
            // tw.com.lancer.team.vocabulary.LCVocabulary.appendVocabulary(dataMap);

            LCVocabularyService.appendVocabulary(loginRecord, dataMap);
        }
    }
}
