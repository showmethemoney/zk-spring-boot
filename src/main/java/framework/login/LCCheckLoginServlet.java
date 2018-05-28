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

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LCCheckLoginServlet
 */
public class LCCheckLoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public LCCheckLoginServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doRequest(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doRequest(request, response);
    }

    private void doRequest(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        final HttpSession session = request.getSession();
        if (session.getAttribute("loginRecord") != null && ((LCLoginRecord) session.getAttribute("loginRecord")).isLogined()) {
            session.removeAttribute("loginRecord");
        }

        final String enteredTargetpassword = request.getParameter("targetpassword"); // 應用伺服器密碼
        final String enteredUseraccount = request.getParameter("useraccount");
        final String enteredPassword = request.getParameter("password");
        final String enteredTenant = request.getParameter("tenant");
        final String enteredTargetIP = request.getParameter("targetIP");

        if (enteredUseraccount == null || enteredPassword == null || enteredTenant == null || enteredTargetIP == null || enteredUseraccount.length() == 0 || enteredPassword.length() == 0
                || enteredTenant.length() == 0 || enteredTargetIP.length() == 0) {
            response.sendRedirect(request.getContextPath());
        } else {
            if (LCCheckLogin.checkLogin(request)) {
                final String[] a = request.getParameterValues("remember");
                if (a == null) {
                    Cookie cookieTargetpassword = new Cookie("targetpassword", ""); // 應用伺服器密碼
                    Cookie cookieUseraccount = new Cookie("useraccount", "");
                    Cookie cookiePassword = new Cookie("password", "");
                    Cookie cookieTenant = new Cookie("tenant", "");
                    Cookie cookieTargetIP = new Cookie("targetIP", "");
                    Cookie cookieRemember = new Cookie("remember", "");

                    cookieTargetpassword.setMaxAge(0); // 應用伺服器密碼
                    cookieUseraccount.setMaxAge(0);
                    cookiePassword.setMaxAge(0);
                    cookieTenant.setMaxAge(0);
                    cookieTargetIP.setMaxAge(0);
                    cookieRemember.setMaxAge(0);

                    response.addCookie(cookieTargetpassword); // 應用伺服器密碼
                    response.addCookie(cookieUseraccount);
                    response.addCookie(cookieTenant);
                    response.addCookie(cookieTargetIP);
                    response.addCookie(cookieRemember);
                } else {
                    Cookie rememberCookie = null;
                    for (Cookie ea : request.getCookies()) {
                        if (ea.getName().equals("remember")) {
                            rememberCookie = ea;
                            break;
                        }
                    }
                    if (rememberCookie == null || (!rememberCookie.getValue().equals("Y"))) {

                        Cookie cookieTargetpassword = new Cookie("targetpassword", enteredTargetpassword); // 應用伺服器密碼
                        Cookie cookieUsername = new Cookie("useraccount", enteredUseraccount);
                        Cookie cookiePassword = new Cookie("password", enteredPassword);
                        Cookie cookieTenant = new Cookie("tenant", enteredTenant);
                        Cookie cookieTargetIP = new Cookie("targetIP", enteredTargetIP);
                        Cookie cookieRemember = new Cookie("remember", "Y");

                        // 7 days
                        int expiry = 604800;

                        cookieTargetpassword.setMaxAge(expiry); // 應用伺服器密碼
                        cookieUsername.setMaxAge(expiry);
                        cookiePassword.setMaxAge(expiry);
                        cookieTenant.setMaxAge(expiry);
                        cookieTargetIP.setMaxAge(expiry);
                        cookieRemember.setMaxAge(expiry);

                        response.addCookie(cookieTargetpassword); // 應用伺服器密碼
                        response.addCookie(cookieUsername);
                        response.addCookie(cookieTenant);
                        response.addCookie(cookieTargetIP);
                        response.addCookie(cookieRemember);
                    }
                }

                response.sendRedirect("QryMfgWorkpickFilterView.zul");
            } else {
                response.sendRedirect(request.getContextPath());
            }

        }

    }

}
