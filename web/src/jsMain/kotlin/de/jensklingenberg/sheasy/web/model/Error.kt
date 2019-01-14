package de.jensklingenberg.sheasy.web.model

sealed class Error(var message:String){
   class NetworkError() : Error(StringRes.MESSAGE_NO_CONNECTION)
  class  NotAuthorizedError() : Error("NotAuthorizedError")
  class  UNKNOWNERROR() : Error("Unknown Error")
}