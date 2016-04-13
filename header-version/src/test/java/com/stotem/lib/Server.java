package com.stotem.lib;

/**
 * Created by JianjunWu on 16/4/13.
 */
@Version(uri = "/server")
public class Server {

    @Version(uri = "hello.do", type = Version.VersionType.LowLimit, min = 1.1f)
    public void hello() {
        System.out.println("hello method");
    }
    @Version(uri = "hello2.do", type = Version.VersionType.Range, min = 1.0f, max = 1.5f)
    public void hello2() {
        System.out.println("hello method");
    }
}
