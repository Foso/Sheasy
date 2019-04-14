import org.gradle.api.Plugin
import org.gradle.api.Project

import org.gradle.kotlin.dsl.*
import java.io.File


class GreetPlugin : Plugin<Project> {

    override fun apply(project: Project): Unit = project.run {

        println("I'm Compiling")

       // File("/home/jens/Code/Android/Sheasy/buildSrc/src/hallo.txt").writeText(Libs.test)

    }
}