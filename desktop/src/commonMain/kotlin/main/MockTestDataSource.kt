package main

import de.jensklingenberg.sheasy.web.model.AppResponse
import de.jensklingenberg.sheasy.web.model.FileResponse

class MockTestDataSource{
    companion object {
        val mockAppList = listOf(
            AppResponse("TestData", "11111", "test.package"),
            AppResponse("ABC", "11111", "test.package")
        )

        val fileList = listOf(FileResponse("TestFolder","/storage/"))


    }
}