package com.github.ice45571.piccompress.action

import com.intellij.openapi.ui.DialogWrapper
import java.awt.BorderLayout
import java.awt.Dimension
import java.io.File
import javax.swing.Action
import javax.swing.JComponent
import javax.swing.JPanel
import javax.swing.JTextArea

/**
 * @Description: From one bug to another bug
 * @Author: icewei
 * @Date: 2021/1/14 7:30 下午
 */
class InputApiKeysDialog(val apiKeysFile: File) : DialogWrapper(true) {

    private lateinit var apiKeysTextArea: JTextArea

    override fun createCenterPanel(): JComponent? {
        val dialogPanel = JPanel(BorderLayout())
        apiKeysTextArea = JTextArea(apiKeysFile.readText())
        apiKeysTextArea.preferredSize = Dimension(500, 500)
        dialogPanel.add(apiKeysTextArea, BorderLayout.CENTER)
        return dialogPanel
    }

    init {
        init()
        title = "配置apikey(每一个key独占一行)"
        //将ok按钮的文案设置为保存
        setOKButtonText("保存")
    }

    //点击保存按钮时弹出保存成功信息
    override fun doOKAction() {
        apiKeysFile.writeText(apiKeysTextArea.text)
        super.doOKAction()
    }

    //只显示一个ok按钮
    override fun createActions(): Array<Action> {
        return arrayOf(okAction)
    }
}