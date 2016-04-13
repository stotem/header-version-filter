<html>
<body>
<h2>Header-Filter</h2>
此处为省事，采用了parameter的方式传递请求版本号<br />
<a href="<%=request.getContextPath()%>/service/transferred.do?v=1.0">transferred</a> <br />
<a href="<%=request.getContextPath()%>/service/range.do?v=7.1">range</a> <br />
<a href="<%=request.getContextPath()%>/service/deprecated.do?v=1.0">deprecated</a> <br />
<a href="<%=request.getContextPath()%>/service/all.do?v=1.0">all</a> <br />
<a href="<%=request.getContextPath()%>/service/limit.do?v=1.0">limit</a> <br />
</body>
</html>
