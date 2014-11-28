package social.aggregator.awesome

import scala.concurrent.ExecutionContext
import akka.actor.Actor
import co.s4n.sa.SocialWeb20Adapter
import scala.collection.JavaConversions._
class FriendFinder extends Actor{
  private implicit val _: ExecutionContext = context.dispatcher
  private val swa: SocialWeb20Adapter = new SocialWeb20Adapter
  
  override def receive: Receive = {
    case TwitterMessage => 
      println("requested twitter messages")
      val twitterFollowers = swa.twitterFollowers().toList
      sender ! TwitterResponse(twitterFollowers)
    case FacebookMessage => 
      println("requested facebook friends")
      val facebookFriends = swa.facebookFriends().toList
      sender ! FacebookResponse(facebookFriends)
    case GmailMessage =>
      println("requested gmail contacts")
      val gmailContacts = swa.gmailContacts()
      sender ! GmailResponse(gmailContacts.toList)
    case _ => println("not supported message")
  }

}