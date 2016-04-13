package com.stotem.lib;

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
}