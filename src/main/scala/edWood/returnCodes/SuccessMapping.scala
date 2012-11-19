
package edWood.returnCodes

import edWood.returnCodes.RangeImplicits._

trait SuccessMapping {
  def success(code:Int):Boolean
}

case object DefaultSuccess extends SuccessMapping {
  def success(code:Int) = code == 0
}

case class FullMapping(successes:List[ReturnCodeRange], errors:List[ReturnCodeRange]) extends SuccessMapping {
  def success(code:Int) = 
    if(errors ? code) false
    else if (successes ? code) true
    else DefaultSuccess success code
}

case class JustSuccesses(override val successes:List[ReturnCodeRange]) extends FullMapping(successes, Nil)

case class JustErrors(override val errors:List[ReturnCodeRange]) extends FullMapping(Nil, errors)


