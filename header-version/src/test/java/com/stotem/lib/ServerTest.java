package com.stotem.lib;

import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * Created by JianjunWu on 16/4/13.
 */
public class ServerTest extends TestCase {

    public void setUp() throws Exception {
        super.setUp();
        HeaderVersion.getInstance().scanner("com.stotem.lib");
    }

    public void testHello() throws Exception {
        HeaderVersion.getInstance().checkRequest("/server/hello.do", 1.0f);
    }

    public void testHello2() throws Exception {
        HeaderVersion.getInstance().checkRequest("/server/hello2.do", 1.0f);
    }

    public void testHello3() throws Exception {
        try {
            HeaderVersion.getInstance().checkRequest("/server/hello3.do", 1.0f);
        }
        catch (RequestVersionException e) {
            Assert.assertEquals(e.getCode(), 302);
        }
    }

    public void testHello4() throws Exception {
        try {
            HeaderVersion.getInstance().checkRequest("/server/hello4.do", 1.0f);
        }
        catch (RequestVersionException e) {
            Assert.assertEquals(e.getCode(), 301);
        }
    }

    public void testHello5() throws Exception {
        HeaderVersion.getInstance().checkRequest("/server/hello5.do", 1.0f);
    }
}