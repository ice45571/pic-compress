package com.github.ice45571.piccompress

import com.intellij.openapi.application.PathManager
import java.io.File

/**
 * @Description: From one bug to another bug
 * @Author: icewei
 * @Date: 2021/1/15 3:39 下午
 */
object Helper {
    private var KEYS_FILE_PATH = "apikeys.txt"
    private var ROOT_DIR = "${PathManager.getPreInstalledPluginsPath()}/pic-compress"

    fun getApiKeysFile(): File {
        val rootDir = File(ROOT_DIR)
        if (!rootDir.exists()) {
            rootDir.mkdirs()
        }
        val apiKeysFile = File(rootDir, KEYS_FILE_PATH)
        if (!apiKeysFile.exists()) {
            apiKeysFile.createNewFile()
            apiKeysFile.writeText("J9vrxwT9vppN03X2KgCP2LHJ22KC1m3v\n")
        }
        return apiKeysFile
    }
}

fun String.log() {
    org.jetbrains.rpc.LOG.info("调试日志: $this")
}