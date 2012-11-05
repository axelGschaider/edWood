package edWood.data

import java.util.Iterator;
import java.io.{File,BufferedReader,FileReader}


case class InputImpl(identifier:String, iterator:Iterator[String] ) extends Input {
  override def getIdentifier:String = identifier
  override def getIterator:Iterator[String] = iterator
}


case class FileInputImpl(identifier:String, file:File) extends Input {
  
  def getIdentifier:String = identifier

  def getIterator():Iterator[String] = {
    
    FileInput(file)

  }
  
}

case class FileInput(file:File) extends Iterator[String] {

  val in = new BufferedReader(new FileReader(file))

  var nextLine = in.readLine();

  def hasNext():Boolean = nextLine != null

  def next():String = {
    val ret = nextLine

    if ( nextLine != null ) {
      nextLine = in.readLine()
      if (nextLine == null) in.close()
    }
    
    return ret
    

  }

  def remove = {}

}
