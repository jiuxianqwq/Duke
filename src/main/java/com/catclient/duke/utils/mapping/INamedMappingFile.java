/*
 * Copyright (c) Forge Development LLC and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */
package com.catclient.duke.utils.mapping;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;

/*
 * 一个简单的asm注入框架
 * 原作者: Loratadine (Cherish)
 * 修改: 玖弦, 手淫
 */

public interface INamedMappingFile {
    static INamedMappingFile load(File path) throws IOException {
        try (InputStream in = new FileInputStream(path)) {
            return load(in);
        }
    }

    static INamedMappingFile load(InputStream in) throws IOException {
        return InternalUtils.loadNamed(in);
    }

    List<String> getNames();

    IMappingFile getMap(String from, String to);

    default void write(Path path, IMappingFile.Format format) throws IOException {
        write(path, format, getNames().toArray(new String[getNames().size()]));
    }

    void write(Path path, IMappingFile.Format format, String... order) throws IOException;
}
