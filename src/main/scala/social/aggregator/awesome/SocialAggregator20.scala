package social.aggregator.awesome

import akka.actor.ActorSystem
import scala.concurrent.ExecutionContext
import akka.actor.Props
import akka.actor.ActorRef

object SocialAggregator20 extends App{
  val actorSystem = ActorSystem("SistemaActoresSocialAggregator")
  implicit val _: ExecutionContext = actorSystem.dispatcher
  
  val aggregator: ActorRef = actorSystem.actorOf(Props[FriendsAggregator], "FriendsAggregator")
  
  aggregator ! FriendsMessages
  
      /* End example */
  Thread.sleep(3000)
  actorSystem.shutdown()
  
}


case class FriendsMessages()
case class TwitterMessage()
case class FacebookMessage()
case class GmailMessage()

case class TwitterResponse(followers: List[String])
case class FacebookResponse(friends: List[String])
case class GmailResponse(friends: List[String])