package akka.actors

import java.text.SimpleDateFormat
import java.util.Date

import akka.actor.{Actor, ActorRef}
import akka.model.{RegisterData, WorkerResult};

class DatabaseActor(workerActorRef: ActorRef) extends Actor {

  override def receive: Receive = {

    case "Tick" => {
      val time: String = new SimpleDateFormat("HH:mm:ss").format(new Date)
      val id: Long = Thread.currentThread.getId

      Thread.sleep(500)
      val rand: Int = (Math.random * 5).toInt
      println(s"[DatabaseActor, $id, $time] - Getting a TICK: Reading database and get $rand registrations.")

      for(i <- 0 to rand) {
        workerActorRef ! RegisterData( (Math.random * 1000).toInt, s"Max_$i", s"Mainstreet_$i" )
      }
    }

    case result: WorkerResult => {
      val time: String = new SimpleDateFormat("HH:mm:ss").format(new Date)
      val id: Long = Thread.currentThread.getId

      if (result.success) println(s"[DatabaseActor, $id, $time] - Getting RESULT from worker for id ${result.id}. It was a SUCCESS!")
      else println(s"[DatabaseActor, $id, $time] - Getting result from worker for id ${result.id}. It has FAILED!")
    }
  }
}
