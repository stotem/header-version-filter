package com.stotem.header.version.servlet;

import com.stotem.lib.Version;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by JianjunWu on 16/4/13.
 */
public class DeprecatedServiceServlet extends HttpServlet {

    @Override
    @Version(uri = "/service/deprecated.do", type = Version.VersionType.Deprecated)
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().print("deprecated Service");
        resp.getWriter().flush();
    }
}
