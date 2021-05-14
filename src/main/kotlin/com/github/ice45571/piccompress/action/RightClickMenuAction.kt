package com.github.ice45571.piccompress.action

import com.github.ice45571.piccompress.log
import com.intellij.ide.util.PropertiesComponent
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.progress.ProgressIndicator
import com.intellij.openapi.vfs.VirtualFile
import com.tinify.*

import java.lang.Exception

import com.intellij.openapi.progress.ProgressManager
import com.intellij.openapi.progress.Task
import com.intellij.openapi.ui.Messages
import com.tinify.Tinify
import java.awt.EventQueue


/**
 * @Description: From one bug to another bug
 * @Author: icewei
 * @Date: 2021/1/15 2:31 下午
 */
class RightClickMenuAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val apiKeysList =
            PropertiesComponent.getInstance().getValue("keys", "J9vrxwT9vppN03X2KgCP2LHJ22KC1m3v").split("\n")
        val files = e.getData(CommonDataKeys.VIRTUAL_FILE_ARRAY)
        if (files != null && e.project != null) {
//            "当前线程1 ${Thread.currentThread()}".log() //Thread[AWT-EventQueue-0,6,Idea Thread Group]
            ProgressManager.getInstance().run(object : Task.Backgroundable(e.project, "正在压缩图片...") {
                private lateinit var mProgressIndicator: ProgressIndicator
                private var compressSuccessCount = 0
                private var compressFailedCount = 0

                override fun run(progressIndicator: ProgressIndicator) {
//                    "当前线程2 ${Thread.currentThread()}".log() //Thread[ApplicationImpl pooled thread 9,4,Idea Thread Group]
                    mProgressIndicator = progressIndicator

                    mProgressIndicator.isIndeterminate = false //这里我们知道总工作量所以显示精确的进度

                    val toCompressFiles = mutableListOf<VirtualFile>()

                    files.forEach {
                        if (it.isDirectory) {//暂时只支持一级目录
                            toCompressFiles.addAll(it.children)
                        } else {
                            toCompressFiles.add(it)
                        }
                    }

                    val totalToCompressFilesSize = toCompressFiles.size
                    val startTime = System.currentTimeMillis()
                    for ((curCount, file) in toCompressFiles.withIndex()) {
                        if (file.isDirectory) {
                            file.children
                        }

                        mProgressIndicator.fraction = ((curCount + 1) * 1.0f / totalToCompressFilesSize).toDouble()
                        mProgressIndicator.text = "正在处理第${curCount + 1}/${totalToCompressFilesSize}张图片"
                        compress(file, apiKeysList).let {
                            if (it) compressSuccessCount++ else compressFailedCount++
                        }
                    }

                    mProgressIndicator.fraction = 1.0
                    mProgressIndicator.text = "处理完成"

                    //切换主线程 要切换子线程可以使用ApplicationManager.getApplication().executeOnPooledThread()
                    EventQueue.invokeLater {
//                        "当前线程3 ${Thread.currentThread()}".log() //Thread[AWT-EventQueue-0,6,Idea Thread Group]
//                        val notificationGroup =
//                            NotificationGroup("notificationGroup", NotificationDisplayType.BALLOON, true)
//                        val notification: Notification =
//                            notificationGroup.createNotification(
//                                "压缩完成",
//                                "总共耗时${(System.currentTimeMillis() - startTime) / 1000}秒\n  成功$compressSuccessCount  失败$compressFailedCount",
//                                NotificationType.INFORMATION
//                            )
//                        Notifications.Bus.notify(notification)
                        Messages.showInfoMessage(
                            "压缩完成", "总共耗时${(System.currentTimeMillis() - startTime) / 1000}秒\n" +
                                    "成功数$compressSuccessCount  失败数$compressFailedCount  "
                        )
                    }
                }
            })
        }
    }

    fun compress(file: VirtualFile, apiKeysList: List<String>): Boolean {
        if (!checkFormatValid(file.path)) {
            return false
        }
        for (apiKey in apiKeysList) {

            Tinify.setKey(apiKey)
            try {
                // Use the Tinify API client.
                "compress inner Tinify.fromFile start  path=${file.path}".log()
                Tinify.fromFile(file.path).toFile(file.path)  // TODO: 2021/3/5 耗时  1.7mb的图花了19s/12s/10s
                "compress inner Tinify.fromFile after  path=${file.path}".log()
                return true
            } catch (e: AccountException) {// Verify your API key and account limit.
                "The error message is: ${e.message}".log()
                continue
            } catch (e: ClientException) {// Check your source image and request options.
                "ClientException The error message is: ${e.message}".log()
                break
            } catch (e: ServerException) {// Temporary issue with the Tinify API.
                "ServerException The error message is: ${e.message}".log()
                break
            } catch (e: ConnectionException) {// A network connection error occurred.
                "ConnectionException The error message is: ${e.message}".log()
                break
            } catch (e: Exception) {// Something else went wrong, unrelated to the Tinify API.
                "Exception The error message is: ${e.message}".log()
                break
            }
        }
        return false
    }


    private fun checkFormatValid(path: String): Boolean {
        return path.endsWith("png") || path.endsWith("jpeg") || path.endsWith("jpg")
    }

    /**
     * 只有选中了    文件夹或png、jpeg文件    后右键才会显示压缩图标
     */
    override fun update(e: AnActionEvent) {
        val files = e.getData(CommonDataKeys.VIRTUAL_FILE_ARRAY)
        if (files != null && e.project != null) {
            for (file in files) {
                if (file.isDirectory || checkFormatValid(file.path)) {
                    e.presentation.isEnabledAndVisible = true
                    return
                }
            }
        }
        e.presentation.isEnabledAndVisible = false
    }
}