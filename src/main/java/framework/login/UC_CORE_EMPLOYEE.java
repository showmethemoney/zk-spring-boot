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
import framework.component.ServiceEJBBridge;

public class UC_CORE_EMPLOYEE extends ServiceEJBBridge {
    private final String serviceComponentId = this.getClass().getName().substring(this.getClass().getName().lastIndexOf('.') + 1);

    public Map QRYEMPLOYEE(LCLoginRecord loginRecord, HashMap params) {
        return super.sendServiceRequest(serviceComponentId, "QRYEMPLOYEE", loginRecord, params);
    }
}
