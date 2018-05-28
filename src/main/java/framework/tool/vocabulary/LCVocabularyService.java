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
package framework.tool.vocabulary;

import java.util.HashMap;
import framework.login.LCLoginRecord;


// �t�d�޲z���web application���r�J�A�̷ӨC��tenant���r�J�ۥѭק�h�@�Ϲj�C
public class LCVocabularyService {
    private final static HashMap<String, HashMap<String, String>> VOCABULARY_MAP = new HashMap<String, HashMap<String, String>>();

    private LCVocabularyService() {}

    /**
     * �̷�Tenant�x�snewVocMap
     * 
     * @param loginRecord
     * @param newVocMap
     */
    public static void appendVocabulary(LCLoginRecord loginRecord, HashMap<String, String> newVocMap) {
        // �N�d�ߨ쪺�r�J�ۥѭק諸map�A�̷�tenant�s��_VOCABULARY_MAP���C
        if (loginRecord != null && loginRecord.getTenant() != null) {
            VOCABULARY_MAP.put(loginRecord.getTenant(), newVocMap);
        }
    }

    /**
     * ��o��Tenant���r�J
     * 
     * @param loginRecord
     * @param stringNo
     * @return
     */
    public static String getDisplayString(LCLoginRecord loginRecord, String stringNo) {
        if (loginRecord == null || loginRecord.getTenant() == null || stringNo == null) {
            return null;
        }

        // �̷�tenant��XvocMap�����r�J�ۥѭק諸map�C
        HashMap<String, String> tenantVocMap = VOCABULARY_MAP.get(loginRecord.getTenant());
        if (tenantVocMap == null || tenantVocMap.isEmpty() || tenantVocMap.get(stringNo) == null) {
            // �^�ǹw�]��
            return tw.com.lancer.team.vocabulary.LCVocabulary.getDisplayString(stringNo);
        } else {
            // �^�Ǧr�J�ۥѭק�᪺map
            return tenantVocMap.get(stringNo);
        }
    }
}
