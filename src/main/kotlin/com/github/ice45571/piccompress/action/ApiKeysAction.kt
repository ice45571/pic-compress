package com.github.ice45571.piccompress.action

import com.github.ice45571.piccompress.Helper
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent


/**
 * @Description: From one bug to another bug
 * @Author: icewei
 * @Date: 2021/1/14 4:21 下午
 */
class ApiKeysAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        InputApiKeysDialog(Helper.getApiKeysFile()).showAndGet()
    }
}