package de.jensklingenberg.sheasy.model
sealed class State(val message:String){
    class Loading:State("Loading")
    class Succes:State("Success")
    class Error:State("Error")
}