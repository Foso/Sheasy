package de.jensklingenberg.sheasy.model

data class FileResponse(val name: String, val path: String){
    fun isFile(): Boolean {
        return path.contains(".")
    }
}

