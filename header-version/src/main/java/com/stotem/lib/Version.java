package com.stotem.lib;

import com.stotem.lib.policy.*;

import java.lang.annotation.*;

/**
 * Created by JianjunWu on 16/3/24.
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Version {

    String uri();

    VersionType type() default VersionType.All;

    float min() default 0;

    float max() default 0;

    String destURI() default "";

    enum VersionType {
        All(new AllVersionTypePolicy()),
        LowLimit(new LowLimitVersionTypePolicy()),
        Range(new RangeVersionTypePolicy()),
        Transferred(new TransferredVersionTypePolicy()),
        Deprecated(new DeprecatedVersionTypePolicy());

        private VersionTypePolicy policy;

        VersionType(VersionTypePolicy p) {
            this.policy = p;
        }

        public VersionTypePolicy getPolicy() {
            return policy;
        }
    }
}
