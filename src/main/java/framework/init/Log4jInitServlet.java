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
package framework.init;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import org.apache.log4j.PropertyConfigurator;

public class Log4jInitServlet extends HttpServlet {
    public void service(ServletRequest req, ServletResponse resp) throws ServletException, IOException {}

    public void init() throws ServletException {
        // System.out.println("Log4jInitServlet Initing!");
        System.setProperty("webappRoot", getServletContext().getRealPath("/"));
        PropertyConfigurator.configure(getServletContext().getRealPath("/") + getInitParameter("configfile"));
    }
}
