package de.jensklingenberg.sheasy.network.generator

import com.squareup.kotlinpoet.*
import java.io.File

val basePath = "web/src/jsMain/kotlin/"
val appCompoPath = "web/src/jsMain/kotlin/de/jensklingenberg/sheasy/web/Application.kt"

var appCompon = ""

fun main(args: Array<String>) {

    val appPath = find(appCompoPath)

    val appClass = findAppComponent(basePath + appPath)


    val injectedComponent = mutableListOf<InjectedObject>()

    appClass.imports.forEach {
        injectedComponent.add(parseInjectedView(basePath + it))
    }

    appClass.modules.forEach {
        println("H"+it.name)
        println("H"+it.path)



    }

    injectedComponent.forEach {


    }

    val greeterClass = ClassName("", "Greeter")
    val file = FileSpec.builder("de.tt", "HelloWorld")
        .addType(
            TypeSpec.classBuilder("Greeter")
                .primaryConstructor(
                    FunSpec.constructorBuilder()
                        .addParameter("name", String::class)
                        .build()
                )
                .addProperty(
                    PropertySpec.builder("name", String::class)
                        .initializer("name")
                        .build()
                )
                .addFunction(
                    FunSpec.builder("greet")
                        .addStatement("println()")
                        .build()
                )
                .build()
        )
        .addFunction(
            FunSpec.builder("main")
                .addParameter("args", String::class, KModifier.VARARG)
                .addStatement("%T(args[0]).greet()", greeterClass)
                .build()
        )
        .build()

    File("web/src/jsMain/kotlin/generated").mkdir()
    file.writeTo(File("web/src/jsMain/kotlin/generated"))
}

fun parseInjectedView(it: String): InjectedObject {
    var injectFound = false

    var name = ""
    var type = ""
    val path = it.replace(".", "/") + ".kt"


    test@ File(path).useLines {

        it.forEach {

            var string = it.replace("\\s".toRegex(), "")

            if (injectFound) {
                name = string.trim().substringAfter("var").substringBefore(":")
                type = string.replace("\\s".toRegex(), "")
                type = type.substringAfter(":")
                // println(type)
                injectFound = false
            }


            if (it.contains("@KodeinInject")) {
                injectFound = true
            }


        }

    }

    return InjectedObject(name, type)
}

data class InjectedObject(val name: String, val type: String)

fun find(path2: String): String {
    var path = ""

    test@ File(path2).useLines {

        it.forEach {
            if (it.contains(".AppComponent")) {
                path = it.replace("import ", "").replace(".", "/") + ".kt"
                return@useLines
            }


        }

    }
    return path

}

fun findAppComponent(path: String): AppClass {

    var packageKey = ""
    var imports = mutableListOf<String>()
    var injectedVies = mutableListOf<String>()
    var modules = mutableListOf<Module>()




    test@ File(path).useLines {

        it.forEach {
            if (it.startsWith("package")) {
                packageKey = it.substringAfter("package ")
            }
            if (it.startsWith("import")) {
                val import = it.substringAfter("import ")
                if (!import.contains("KodeinModul")) {
                    imports.add(import)

                }
            }

            if (it.contains("@KodeinModul")) {
                val moduleName = it.substringAfter("[").substringBefore(":")

                var modulePath = ""
                if (imports.none { it.contains(moduleName) }) {
                    modulePath = packageKey + "." + moduleName
                }else{
                    modulePath = packageKey + "." + moduleName
                }
                modules.add(Module(moduleName, modulePath))

            }


            if (it.contains("inject")) {
                val injectedView = it.substring(it.indexOf(":") + 1, it.indexOf(")")).trim()
                injectedVies.add(injectedView)
                if (imports.none { it.contains(injectedView) }) {
                    imports.add(packageKey + "." + injectedView)
                }


            }

        }

    }
    return AppClass(packageKey, imports, injectedVies, modules)
}

data class Module(val name: String, val path: String)

open class MyClass(open val ppackage: String, open val imports: List<String>? = emptyList())

class AppClass(
    override val ppackage: String,
    override val imports: List<String>,
    val injectedClass: List<String>,
    val modules: List<Module>
) :
    MyClass(ppackage, imports)

