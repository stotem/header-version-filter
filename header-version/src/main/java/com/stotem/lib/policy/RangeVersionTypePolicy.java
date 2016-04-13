package com.stotem.lib.policy;

import com.stotem.lib.ConfigException;
import com.stotem.lib.RequestVersionException;
import com.stotem.lib.Tools;
import com.stotem.lib.Version;

/**
 * Created by JianjunWu on 16/4/12.
 */
public class RangeVersionTypePolicy implements VersionTypePolicy {
    public void checkConfig(Version annotation) throws ConfigException {
        if (Tools.isEmpty(annotation.uri())) {
            throw new ConfigException("The uri is empty");
        }
        if (annotation.min() <= 0) {
            throw new ConfigException("The min is invalid");
        }
        if (annotation.max() <= 0) {
            throw new ConfigException("The max is invalid");
        }
        if (annotation.min() >= annotation.max()) {
            throw new ConfigException("The max is smaller than min");
        }
    }

    public void checkRequestVersion(Version versionConfig, float requestV) throws RequestVersionException {
        if (versionConfig.max() < requestV) {
            throw new RequestVersionException(505, "The version of request is bigger than max");
        }
        if (versionConfig.min() > requestV) {
            throw new RequestVersionException(505, "The version of request is smaller than min");
        }
    }
}
