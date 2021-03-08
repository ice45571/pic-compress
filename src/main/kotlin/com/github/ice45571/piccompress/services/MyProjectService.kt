package com.github.ice45571.piccompress.services

import com.github.ice45571.piccompress.MyBundle
import com.intellij.openapi.project.Project

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
