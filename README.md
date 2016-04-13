# header-version-filter
此项目为公开接口Api的管理库，通过@Version配置Api的版本。 

理论来源:http://blog.cnvip.hk/2016/03/21/header-version/
## 版本配置方法及使用
在对应有类方法（非private）上添加@Version注解配置对应的参数来实现。可在类上通过@Version配置namespace来限定类中各接口方法的api前缀Path。
配置方法如：
```java
@Version(uri = "/service")
public class AllServiceServlet extends HttpServlet {

    @Override
    @Version(uri = "all.do")
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().print("All service servlet");
        resp.getWriter().flush();
    }
}
```
`注意：`uri为请求路径，应整个api接口平台惟一。

具体使用方法如下：
 * 启动时指定扫描的api包，通过扫描完成类api的配置加载。
```
HeaderVersion.getInstance().scanner("com.stotem.header.version.servlet");
```
 * 在每次需要检测版本时添加检查代码，一般为filter或者拦截器中。
```
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
```
## 版本配置策略
版本配置策略相关包: com.stotem.lib.policy

所有策略处理均实现com.stotem.lib.policy.VersionTypePolicy接口，提供配置检查与版本检查处理两个方法。

策略方式与Version.VersionType对应。

 1. AllVersionTypePolicy：对所有的版本服务
 2. LowLimitVersionTypePolicy：Api提供了最低版本，为大于此最低版本的请求提供服务。检查不通过返回HTTPCode：505
 3. RangeVersionTypePolicy：Api提供了最低版本，为大于此最低版本的请求提供服务。检查不通过返回HTTPCode：505
 4. TransferredVersionTypePolicy：Api已过期，由其它的Api代替。检查不通过返回HTTPCode：302,message为转移的uri地址。
 5. DeprecatedVersionTypePolicy：Api已废弃不再提供服务。检查不通过返回HTTPCode：301


__详细请下载demo查看filter+servlet配置及使用__
