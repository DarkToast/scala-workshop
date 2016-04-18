package akka.actors

import java.text.SimpleDateFormat
import java.util.Date

import akka.actor.Actor
import akka.model.{RegisterData, WorkerResult}

class WorkerActor extends Actor {
  override def receive: Receive = {
    case data: RegisterData => {
      val time: String = new SimpleDateFormat("HH:mm:ss").format(new Date)
      val id: Long = Thread.currentThread.getId

      println(s"[WorkerActor, $id, $time] - Munching some data and get some work done: id: ${data.id}, name: ${data.name}, street: ${data.street}")
      Thread.sleep(2000)

      sender() ! WorkerResult(data.id, Math.random < 0.7)
    }
  }
}
