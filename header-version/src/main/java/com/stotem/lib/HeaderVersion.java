package com.stotem.lib;

import michael.utils.ClassPathScanHandler;
import org.apache.log4j.Logger;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * Created by JianjunWu on 16/4/12.
 */
public class HeaderVersion {
    private static HeaderVersion instance = new HeaderVersion();
    private Logger logger = Logger.getLogger(getClass());

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
            if (logger.isDebugEnabled()) {
                logger.debug(String.format("Scanner all of the class. [%s]", pkg));
            }
            Set<Class<?>> packageAllClasses = scanHandler.getPackageAllClasses(pkg, true);
            if (logger.isInfoEnabled()) {
                logger.info(String.format("Find %s classes that need to be scanned. [%s]",packageAllClasses.size(), pkg));
            }
            for (Class<?> packageClass : packageAllClasses) {
                Map<String, Version> stringVersionMap = scannerMethod(packageClass);
                if (stringVersionMap.isEmpty()) {
                    continue;
                }
                if (logger.isInfoEnabled()) {
                    logger.info(String.format("Find %s mapping of method. [%s]",stringVersionMap.size(), packageClass.getName()));
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
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Scanner all of the method. [%s]", packageClass.getName()));
        }
        Map<String, Version> versions = new HashMap<String, Version>();
        Method[] declaredMethods = packageClass.getDeclaredMethods();
        String namespace = null;
        Version classAnnotation = packageClass.getAnnotation(Version.class);
        if (!Tools.isNull(classAnnotation)) {
            namespace = Tools.isEmpty(classAnnotation.uri()) ? "/":classAnnotation.uri().trim();
            if (!Tools.isEmpty(namespace) && !namespace.endsWith("/")) {
                namespace += "/";
            }
            if (logger.isInfoEnabled()) {
                logger.info(String.format("Found namespace %s. [%s]", namespace, packageClass.getName()));
            }
        }
        else {
            if (logger.isDebugEnabled()) {
                logger.debug(String.format("Not found namespace.[%s]", packageClass.getName()));
            }
        }
        for (Method declaredMethod : declaredMethods) {
            if (logger.isDebugEnabled()) {
                logger.debug(String.format("Check method [%s]", declaredMethod.getName()));
            }
            int modifiers = declaredMethod.getModifiers();
            // filter public method
            if (!Modifier.isPublic(modifiers)) {
                if (logger.isDebugEnabled()) {
                    logger.debug(String.format("Not public method [%s]", declaredMethod.getName()));
                }
                continue;
            }
            Version annotation = declaredMethod.getAnnotation(Version.class);
            if (Tools.isNull(annotation)) {
                if (logger.isDebugEnabled()) {
                    logger.debug(String.format("Not configured version [%s]", declaredMethod.getName()));
                }
                continue;
            }
            // check the config of version
            try {
                annotation.type().getPolicy().checkConfig(annotation);
            } catch (ConfigException e) {
                String msg = String.format("[%s.%s] %s", packageClass.getName(), declaredMethod.getName(), e.getMessage());
                logger.warn(msg);
                System.err.println(msg);
                continue;
            }
            // put the result map
            String uri = annotation.uri();
            String key = (Tools.isEmpty(namespace) ? "":namespace) + (uri.startsWith("/") ? uri.replaceFirst("/","") :uri);
            if (logger.isInfoEnabled()) {
                logger.info(String.format("Found version mapping.[%s]", key));
            }
            if (versions.containsKey(key)) {
                logger.error(String.format("Repeat version mapping.[%s]", key));
            }
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
        if (logger.isInfoEnabled()) {
            logger.info(String.format("Processing check version of request.[%s->%s]", uri, version));
        }
        Version versionConfig = versionMapping.get(uri);
        if (Tools.isNull(versionConfig)) {
            if (logger.isInfoEnabled()) {
                logger.info(String.format("Not found mapping.[%s->%s]", uri, version));
            }
            return;
        }
        versionConfig.type().getPolicy().checkRequestVersion(versionConfig, version);
    }

}
