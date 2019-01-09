package generator

import com.squareup.kotlinpoet.*
import java.io.File

val basePath= "web/src/jsMain/kotlin/"

var appCompon= ""

fun main(args: Array<String>) {

    findAppComponent()


    test@ File(basePath+ appCompon).useLines {

        it.forEach {
            if(it.contains("inject")){
                println(it)
            }
        }

    }





    val greeterClass = ClassName("", "Greeter")
    val file = FileSpec.builder("de.tt", "HelloWorld")
            .addType(TypeSpec.classBuilder("Greeter")
                    .primaryConstructor(FunSpec.constructorBuilder()
                            .addParameter("name", String::class)
                            .build())
                    .addProperty(PropertySpec.builder("name", String::class)
                            .initializer("name")
                            .build())
                    .addFunction(FunSpec.builder("greet")
                            .addStatement("println()")
                            .build())
                    .build())
            .addFunction(FunSpec.builder("main")
                    .addParameter("args", String::class, KModifier.VARARG)
                    .addStatement("%T(args[0]).greet()", greeterClass)
                    .build())
            .build()

        File("web/src/jsMain/kotlin/generated").mkdir()
        file.writeTo(File("web/src/jsMain/kotlin/generated"))
}

fun findAppComponent() : AppClass {

var list = emptyList<String>()
    test@ File("web/src/jsMain/kotlin/de/jensklingenberg/sheasy/web/Application.kt").useLines {

        it.forEach {
            if(it.contains("AppComponent")){
            appCompon =it.replace("import ","").replace(".","/")+".kt"
                println(appCompon)

                return@useLines
            }

        }

    }
    return AppClass("dd",list)
}

open class MyClass(open val ppackage:String, open  val imports:List<String>?= emptyList())

class AppClass(override val ppackage:String, override val imports:List<String>) : MyClass(ppackage,imports)