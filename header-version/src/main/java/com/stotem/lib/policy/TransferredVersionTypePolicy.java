package com.stotem.lib.policy;

import com.stotem.lib.ConfigException;
import com.stotem.lib.RequestVersionException;
import com.stotem.lib.Tools;
import com.stotem.lib.Version;

/**
 * Created by JianjunWu on 16/4/12.
 */
public class TransferredVersionTypePolicy implements VersionTypePolicy {

    public void checkConfig(Version annotation) throws ConfigException {
        if (Tools.isEmpty(annotation.uri())) {
            throw new ConfigException("The uri is empty");
        }
        if (Tools.isEmpty(annotation.backupURI())) {
            throw new ConfigException("The backupURI is empty");
        }
    }

    public void checkRequestVersion(Version versionConfig, float requestV) throws RequestVersionException {
        throw new RequestVersionException(302, null, versionConfig.backupURI());
    }
}
