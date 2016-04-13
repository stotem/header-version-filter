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
@Version(uri = "/service")
public class RangeServiceServlet extends HttpServlet {

    @Override
    @Version(uri = "range.do", type = Version.VersionType.Range,min = 10f, max = 11.2f)
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().print("range service servlet");
        resp.getWriter().flush();
    }
}
