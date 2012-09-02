package edWood.returnCodes

trait ReturnCodeRange {
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

case class RangeList(x:List[ReturnCodeRange]) extends ReturnCodeRange {
  def contains(i:Int) = x.exists(_.contains(i))
}


