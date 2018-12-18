package main

import model.AppResponse

class MockTestDataSource{
    companion object {
        val mockList = listOf(
            AppResponse("TestData", "11111", "test.package"),
            AppResponse("ABC", "11111", "test.package")
        )
    }
}