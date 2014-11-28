package social.aggregator.awesome

import akka.actor.ActorSystem
import scala.concurrent.ExecutionContext
import akka.actor.Props
import akka.actor.ActorRef

object SocialAggregator30 extends App{
  val actorSystem = ActorSystem("SistemaActoresSocialAggregator3")
  implicit val _: ExecutionContext = actorSystem.dispatcher
  
  val aggregator: ActorRef = actorSystem.actorOf(Props[FriendsAggregator], "FriendsAggregator")
  val worker: ActorRef = actorSystem.actorOf(Props[FriendFinder], "FriendFinder")
  aggregator ! FriendsMessages
  
      /* End example */
  Thread.sleep(5000)
  actorSystem.shutdown()
  
}


case class FriendsMessage30(user: String)
case class TwitterMessage30(user: String)
case class FacebookMessage30(user: String)
case class GmailMessage30(user: String)

case class TwitterResponse30(followers: List[String])
case class FacebookResponse30(friends: List[String])
case class CompleteResponse(friends: List[String])