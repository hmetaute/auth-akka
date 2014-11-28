package social.aggregator.awesome2

import akka.actor.Actor
import scala.concurrent.ExecutionContext
import akka.actor.ActorSelection
import social.aggregator.awesome.GmailResponse
import social.aggregator.awesome.TwitterResponse
import social.aggregator.awesome.FriendsMessages
import social.aggregator.awesome.FacebookMessage
import social.aggregator.awesome.FacebookResponse
import social.aggregator.awesome.TwitterMessage
import social.aggregator.awesome.GmailMessage

class FriendsAggregator30 extends Actor{
  import akka.actor.SupervisorStrategy._
  
  private var amigosConsolidados: Seq[String] = List()
  private implicit val _: ExecutionContext = context.dispatcher
  private val friendFinder: ActorSelection = context.actorSelection("akka://SistemaActoresSocialAggregator/user/FriendFinder")
  
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