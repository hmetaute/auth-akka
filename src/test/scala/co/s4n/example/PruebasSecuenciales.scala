package co.s4n.example

import org.scalatest.FunSuite
import scala.concurrent.Future

class PruebasSecuenciales extends FunSuite {
  
  test("eso si esta en paralelo"){
    val fsa = new FutureSocialAggregator
    //val amigos: Future[Seq[String]] = fsa.mapAmigos
  }

}