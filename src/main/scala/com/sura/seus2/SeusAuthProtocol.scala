package com.sura.seus2

class SeusAuthProtocol {
  
case class LoginMessage(usr: String, pwd: String)
case class LoginSuccessfulMessage(token: String)
case class LoginFailedMessage(reason: String)

case class CloseSessionMessage(token: String)
case class SessionClosedMessage()

case class LoggedUsersMessage()
case class LoggedUsersResponse(loggedUsers: Seq[String])

}