package com.stotem.header.version.filter;

import com.stotem.lib.HeaderVersion;
import com.stotem.lib.RequestVersionException;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by JianjunWu on 16/4/13.
 */
public class HeaderVersionFilter implements Filter {

    public void init(FilterConfig filterConfig) throws ServletException {
        HeaderVersion.getInstance().scanner("com.stotem.header.version.servlet");
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String uri = request.getRequestURI();
        uri = uri.replaceFirst(request.getContextPath(),"");
        try {
            HeaderVersion.getInstance().checkRequest(uri, Float.parseFloat(request.getHeader("api-version")));
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (RequestVersionException e) {
            if (e.getCode() == 302) {
                request.getRequestDispatcher(e.getMessage()).forward(request, response);
            }
            response.setStatus(e.getCode());
            response.getWriter().print(e.getMessage());
            response.getWriter().flush();
        }
    }

    public void destroy() {

    }
}
