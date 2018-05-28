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
package framework.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import framework.login.LCCheckLogin;

public class CheckLoginFilter implements Filter {
    private static String contextPath;
    private static List<String> notRestrictedUrlList;

    public final void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) throws IOException, ServletException {
        final String url = ((HttpServletRequest) req).getRequestURI();

        if (notRestrictedUrlList.contains(url) || LCCheckLogin.checkLogin((HttpServletRequest) req)) {

            filterChain.doFilter(req, resp);
        } else {

            doLoginFail(req);
            ((HttpServletResponse) resp).sendRedirect(req.getServletContext().getContextPath());
        }
    }

    private final static List<String> getNotRestrictedUrlList(final String contextPath) {
        ArrayList<String> urlList = new ArrayList<String>();
        // �n�J����
        urlList.add(contextPath + "/");

        // �n�J���}
        urlList.add(contextPath + "/CheckLogin.action");

        // �U��app.apk
        urlList.add(contextPath + "/app.apk");
        return urlList;
    }

    public void destroy() {}

    public void init(final FilterConfig arg0) throws ServletException {
        contextPath = arg0.getServletContext().getContextPath();
        notRestrictedUrlList = getNotRestrictedUrlList(contextPath);
    }

    private void doLoginFail(final ServletRequest req) {
        System.out.println("Login Fail: " + ((HttpServletRequest) req).getRemoteAddr());
        // TODO login fail
    }
}
