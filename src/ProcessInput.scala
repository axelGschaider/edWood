package evaluationTool.run

import java.io._
import java.util.Iterator
import evaluationTool.data._


trait ProcessInput extends LifeInput {
  
  def getProcess:Process

  def getStdInput() = StdStreamInput( getProcess )
  def getErrInput() = ErrStreamInput( getProcess )

  def getCombinedInput() = StdStreamInput(getProcess) //TODO


}


class StreamInput(identifier:String, stream:InputStream) extends Input {
  def getIdentifier = identifier

  private lazy val iterator:Iterator[String] = StreamIterator(stream)

  def getIterator = iterator
}

case class StdStreamInput(process:Process) extends StreamInput("std", process.getInputStream())

case class ErrStreamInput(process:Process) extends StreamInput("err", process.getErrorStream())

case class StreamIterator(stream:InputStream) extends Iterator[String] {
    val reader = new BufferedReader(new InputStreamReader(stream))

    var nextLine = reader.readLine
    
    def hasNext():Boolean = nextLine != null
    
    def next():String = {
      val ret = this.nextLine
      this.nextLine = reader.readLine
      ret
    }


    def remove = { }


}

