package cakepattern

object Main extends App {
  val user = new ServiceUser with RuntimeEnvironment

  val testEnv = new ServiceUser with MockedTestEnvironment

  user.someUsage
  testEnv.someUsage
}
