
package edWood.run

trait Stopable {
  
  def stop()

}

trait Runable extends Stopable {
  
  def run()

  def execCode():Int
}

case class StopableHub(s:List[Stopable]) extends Stopable {
  def stop() = s.foreach(_.stop)
}
