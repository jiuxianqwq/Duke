/*
 * Copyright (c) Forge Development LLC and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */
package com.catclient.duke.utils.mapping;

import com.catclient.duke.utils.mapping.IMappingFile.*;

/*
 * 一个简单的asm注入框架
 * 原作者: Loratadine (Cherish)
 * 修改: 玖弦, 手淫
 */

public interface IRenamer {
    default String rename(IPackage value) {
        return value.getMapped();
    }

    default String rename(IClass value) {
        return value.getMapped();
    }

    default String rename(IField value) {
        return value.getMapped();
    }

    default String rename(IMethod value) {
        return value.getMapped();
    }

    default String rename(IParameter value) {
        return value.getMapped();
    }
}
