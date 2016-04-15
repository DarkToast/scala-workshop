package cakepattern

class ServiceUser {
  this: AAComponent with BBServiceComponent =>

  def someUsage = {
    aaService.callService("lala")
    bbService.callService("huhu")
  }
}
