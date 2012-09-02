package edWood.returnCodes

object ReturnCodeReaderReader {
  
  def readSave(s:String):Option[ReturnCodeRange] = None

  def read(s:String):ReturnCodeRange = readSave(s) match {
    case Some(r)    => r
    case None       => throw new Excveption("could not read '" + s + "' as ReturnCodeRange")
  }

}

