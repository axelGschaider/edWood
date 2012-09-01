
package edWood.loadingHelpers

import java.lang.{ClassLoader, Class}

import edWood.reader.ReaderAdapter
import edWood.writer.WriterAdapter

import at.axelGschaider.loggsNProperties.Logs

object AnyLoader extends Logs {
  
  def loadNewObject(classPath:String):Any = {
   val classLoader =  AnyLoader.getClass.getClassLoader()
   info("trying to load " + classPath)
   val daClass = classLoader loadClass classPath
   daClass.newInstance 
  }

}

object AdapterLoader {
  
  implicit def string2ReaderAdapter(s:String):ReaderAdapter =
    AnyLoader.loadNewObject(s) match {
      case x:ReaderAdapter  =>  x
      case _                =>  throw new ClassCastException
    }

  implicit def string2WriterAdapter(s:String):WriterAdapter = 
    AnyLoader.loadNewObject(s) match {
      case x:WriterAdapter  =>  x
      case _                =>  throw new ClassCastException
    }

  implicit def stringList2WriterAdapterList(s:List[String]):List[WriterAdapter] =
    s.map(string2WriterAdapter(_))

}



