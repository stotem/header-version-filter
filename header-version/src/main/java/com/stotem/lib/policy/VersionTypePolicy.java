package com.stotem.lib.policy;

import com.stotem.lib.ConfigException;
import com.stotem.lib.RequestVersionException;
import com.stotem.lib.Version;

/**
 * Created by JianjunWu on 16/4/12.
 */
public interface VersionTypePolicy {
    /**
     * Check configuration items when loading configuration
     * @param annotation
     */
    void checkConfig(Version annotation) throws ConfigException;

    /**
     * Check version of request when receive request
     *
     * @param versionConfig
     * @param requestV
     * @throws RequestVersionException
     */
    void checkRequestVersion(Version versionConfig, float requestV) throws RequestVersionException;
}
