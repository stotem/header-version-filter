package com.stotem.lib.policy;

import com.stotem.lib.RequestVersionException;
import com.stotem.lib.Version;

/**
 * Created by JianjunWu on 16/4/12.
 */
public class LowLimitVersionTypePolicy implements VersionTypePolicy {


    public String checkConfig(Version annotation) {
        return null;
    }

    public void checkRequestVersion(float requestV) throws RequestVersionException {

    }
}
