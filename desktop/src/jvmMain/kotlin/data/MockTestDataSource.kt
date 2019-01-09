package main

import de.jensklingenberg.sheasy.model.AppInfo
import de.jensklingenberg.sheasy.model.FileResponse


class MockTestDataSource{
    companion object {
        val mockAppList = listOf(
            AppInfo("TestData", "11111", "test.package",""),
            AppInfo("ABC", "11111", "test.package","")
        )

        val sharedFolders = listOf(FileResponse("TestFolder","/"),FileResponse("Storage","/storage"))


    }
}