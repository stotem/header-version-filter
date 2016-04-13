package com.stotem.lib.policy;

import com.stotem.lib.RequestVersionException;
import com.stotem.lib.Version;

/**
 * Created by JianjunWu on 16/4/12.
 */
public interface VersionTypePolicy {
    /**
     * Check configuration items when loading configuration
     * @return error message
     * @param annotation
     */
    String checkConfig(Version annotation);

    /**
     * Check version of request when receive request
     * @param requestV
     * @throws RequestVersionException
     */
    void checkRequestVersion(float requestV) throws RequestVersionException;
}
