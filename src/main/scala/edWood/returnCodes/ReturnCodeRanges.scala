package edWood.returnCodes

sealed trait ReturnCodeRange {
  def contains(i:Int):Boolean
}

case class SingleVal(x:Int) extends ReturnCodeRange {
  def contains(i:Int) = x == i
}

case class Range(start:Int, end:Int) extends ReturnCodeRange {
  if(start > end) throw new Exception("start (" + start + ") must not be bigger than end("+ end +".")
  def contains(i:Int) = (i >= start) && (i <= end)
}

case class Negator(x:ReturnCodeRange) extends ReturnCodeRange {
  def contains(i:Int) = ! x.contains(i)
}

case object AlwaysSuccess extends ReturnCodeRange {
  def contains(i:Int) = true
}

case object AlwayFail extends ReturnCodeRange {
  def contains(i:Int) = false
}

abstract class ReturnCodeTester(successRange:Option[ReturnCodeRange], errorRange: Option[ReturnCodeRange]) {
  def success(i:Int) =  {
    if(this.isSuccess(i)) true
    else if(this.isError(i)) false
    else this.isDefaultSuccess(i)
  }

  def isSuccess(i:Int) = successRange.map(_.contains(i)).getOrElse(false)
  def isError(i:Int) = errorRange.map(_.contains(i)).getOrElse(false)
  def isDefaultSuccess(i:Int) = i <= 0

}

case object DefaultCodeTester extends ReturnCodeTester(None, None)
case class SuccessTester(successRange:ReturnCodeRange) extends ReturnCodeTester(Some(successRange), None)
case class ErrorTester(errorRange:ReturnCodeRange) extends ReturnCodeTester(None, Some(errorRange))
case class FullTester(successRange:ReturnCodeRange, errorRange:ReturnCodeRange) extends ReturnCodeTester(Some(successRange), Some(errorRange))