package com.stotem.lib;

/**
 * Created by JianjunWu on 16/4/13.
 */
@Version(uri = "/server")
public class Server {

    @Version(uri = "hello.do", type = Version.VersionType.LowLimit, min = 0.5f)
    public void hello() {
        System.out.println("hello method");
    }

    @Version(uri = "hello2.do", type = Version.VersionType.Range, min = 1.0f, max = 1.5f)
    public void hello2() {
        System.out.println("hello2 method");
    }

    @Version(uri = "hello3.do", type = Version.VersionType.Transferred, backupURI = "hello2.do")
    public void hello3() {
        System.out.println("hello3 method");
    }

    @Version(uri = "hello4.do", type = Version.VersionType.Deprecated)
    public void hello4() {
        System.out.println("hello4 method");
    }

    @Version(uri = "hello5.do")
    public void hello5() {
        System.out.println("hello5 method");
    }
}
