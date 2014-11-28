package social.aggregator.awesome

import akka.actor.Actor
import scala.concurrent.ExecutionContext
import akka.actor.ActorSelection
import akka.actor.Props
import akka.actor.ActorRef
import akka.actor.OneForOneStrategy
import java.io.IOException
import java.sql.SQLException

class FriendsAggregator extends Actor{
  import akka.actor.SupervisorStrategy._
  
  private var amigosConsolidados: Seq[String] = List()
  private implicit val _: ExecutionContext = context.dispatcher
  private val friendFinder: ActorRef = context.actorOf(Props[FriendFinder], "FriendFinder")
  
  
  /* Let it crash */
  override val supervisorStrategy = OneForOneStrategy(maxNrOfRetries = 10) {
    case e: IOException =>
      println( "El actor falla por aqui" )
      friendFinder ! TwitterMessage
      Resume      
    case e: SQLException =>
      self ! FriendsMessages
      Restart
    case e: Exception =>
      println( "El actor falla" )
      Stop
  }
  
  override def receive: Receive ={
    case FriendsMessages => 
      println("me pidieron todos los amigos")
      friendFinder ! GmailMessage
      friendFinder ! FacebookMessage
      friendFinder ! TwitterMessage
    case GmailResponse(listaAmigos) =>
      amigosConsolidados  = amigosConsolidados  ++ listaAmigos
      validarAmigos()
    case FacebookResponse(listaContactos) =>
      amigosConsolidados = amigosConsolidados ++ listaContactos
      validarAmigos()
    case TwitterResponse(listaFollowers) =>
      amigosConsolidados  = amigosConsolidados ++ listaFollowers
      validarAmigos()
    case _ => println("mensaje que no conozco")
  }
  
  def validarAmigos(): Unit = {
    if(amigosConsolidados.length == 3)
      println(amigosConsolidados)
  }

}