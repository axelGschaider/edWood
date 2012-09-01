
import java.io._

object Piper {

  def getProcess(command:String):Process = {
    val commandArray = Array("/bin/sh", "-c", command)
    Runtime.getRuntime().exec(commandArray)
  }

  def getStdOut(process:Process):String = this getText process.getInputStream

  def getErrOut(process:Process):String = this getText process.getErrorStream

  private def getText(stream:InputStream):String = 
    this getTxt this.streamToReader(stream)

  private def streamToReader(stream:InputStream):BufferedReader = 
    new BufferedReader( new InputStreamReader( stream) )

  private def getTxt(reader:BufferedReader):String = {
    var txt = ""
    var newline:String = reader.readLine

    while( newline  != null) {
      txt += newline + "\n"
      newline = reader.readLine
    }

    return txt

  }

}

