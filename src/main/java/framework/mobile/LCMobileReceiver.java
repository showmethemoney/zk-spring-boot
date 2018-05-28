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
package framework.mobile;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class MobileReceiver
 */
public class LCMobileReceiver extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public LCMobileReceiver() {
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

    // http://localhost:8080/LCShippingOrderReservation/MobileReceiver?page=QryShippingOrderByMergeForReservation.zul&billno=SD1211000001&content={ITEMID=HN20043A,AMOUNT=1000}
    private final void doRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().setAttribute("content", new String(request.getParameter("content").getBytes("ISO-8859-1"), "UTF-8"));
        String path = java.net.URLDecoder.decode(request.getParameter("page"), "utf-8");
        response.sendRedirect(path + "?BILLNO=" + request.getParameter("billno"));
    }

}
