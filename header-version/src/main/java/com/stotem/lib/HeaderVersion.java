package com.stotem.lib;

import michael.utils.ClassPathScanHandler;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * Created by JianjunWu on 16/4/12.
 */
public class HeaderVersion {
    private static HeaderVersion instance = new HeaderVersion();

    private Map<String, Version> versionMapping = new Hashtable<String, Version>();

    public static HeaderVersion getInstance() {
        return instance;
    }

    /**
     * scanner class of packages
     * @param packages packages list
     */
    public void scanner(String... packages) {
        ClassPathScanHandler scanHandler = new ClassPathScanHandler();
        for (String pkg : packages) {
            Set<Class<?>> packageAllClasses = scanHandler.getPackageAllClasses(pkg, true);
            for (Class<?> packageAllClass : packageAllClasses) {
                Map<String, Version> stringVersionMap = scannerMethod(packageAllClass);
                if (stringVersionMap.isEmpty()) {
                    continue;
                }
                versionMapping.putAll(stringVersionMap);
            }
        }
    }

    /**
     * scanner method of class
     * @param packageClass
     * @return
     */
    private Map<String, Version> scannerMethod(Class<?> packageClass) {
        Map<String, Version> versions = new HashMap<String, Version>();
        Method[] declaredMethods = packageClass.getDeclaredMethods();
        String namespace = null;
        Version classAnnotation = packageClass.getAnnotation(Version.class);
        if (!Tools.isNull(classAnnotation) && !Tools.isEmpty(classAnnotation.uri())) {
            namespace = classAnnotation.uri().trim();
            if (!Tools.isEmpty(namespace) && !namespace.endsWith("/")) {
                namespace += "/";
            }
        }
        for (Method declaredMethod : declaredMethods) {
            int modifiers = declaredMethod.getModifiers();
            // filter public method
            if (!Modifier.isPublic(modifiers)) {
                continue;
            }
            Version annotation = declaredMethod.getAnnotation(Version.class);
            if (Tools.isNull(annotation)) {
                continue;
            }
            // check the config of version
            String chkMessage = annotation.type().getPolicy().checkConfig(annotation);
            if (!Tools.isEmpty(chkMessage)) {
                System.err.println(String.format("[%s.%s] %s", packageClass, declaredMethod.getName(), chkMessage));
                continue;
            }

            // put the result map
            String uri = annotation.uri();
            String key = (Tools.isEmpty(namespace) ? "":namespace) + (uri.startsWith("/") ? uri :"/"+ uri);
            versions.put(key, annotation);
        }
        return versions;
    }

    /**
     * check version of request
     * @param uri request uri
     * @param version header version of request
     */
    public void checkRequest(String uri, float version) throws RequestVersionException {
        Version versionConfig = versionMapping.get(uri);
        if (Tools.isNull(versionConfig)) {
            return;
        }
        versionConfig.type().getPolicy().checkRequestVersion(version);
    }

}
