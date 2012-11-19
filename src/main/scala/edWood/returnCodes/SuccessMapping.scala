
package edWood.returnCodes

import edWood.returnCodes.RangeImplicits._

trait SuccessMapping {
  def success(code:Int):Boolean
}

case object DefaultSuccess extends SuccessMapping {
  def success(code:Int) = code == 0
}

trait SuccessMappingImpl extends SuccessMapping {
  val successes:List[ReturnCodeRange]
  val errors:List[ReturnCodeRange]

  def success(code:Int) = 
    if(errors ? code) false
    else if (successes ? code) true
    else DefaultSuccess success code
}

case class FullMapping(successes:List[ReturnCodeRange], errors:List[ReturnCodeRange]) extends SuccessMappingImpl

case class JustSuccesses(override val successes:List[ReturnCodeRange]) extends SuccessMappingImpl {
  override val errors = Nil
}

case class JustErrors(override val errors:List[ReturnCodeRange]) extends SuccessMappingImpl {
  override val successes = Nil
}


