import java.io.BufferedReader

object Versions {

    val dagger = "2.22.1"
    val kotlin = "1.3.30"
    val rxAndroid = "2.1.0"
    val rxjava = "2.1.7"

    val web = object {
        val rxJS = "6.3.3"
        val rxCommonJS = "0.5.2"
    }

}

object Config {

}

object Libs {

    val android = object {
        val rxjava = "io.reactivex.rxjava2:rxjava:${Versions.rxjava}"
        val dagger = "com.google.dagger:dagger-android:${Versions.dagger}"
        val rxAndroid = "io.reactivex.rxjava2:rxandroid:${Versions.rxAndroid}"

    }

    val web = object {

    }


    val gitSha =  Runtime
        .getRuntime()
        .exec("git rev-parse --short HEAD")
        .let<Process, String> { process ->
            process.waitFor()
            val output = process.inputStream.use {
                it.bufferedReader().use(BufferedReader::readText)
            }
            process.destroy()
            output.trim()
        }

}

