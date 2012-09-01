package edWood.dummy

import java.io.{File, FileWriter}

import edWood.reader.ReaderAdapter
import edWood.writer.WriterAdapter
import edWood.data._

import at.axelGschaider.loggsNProperties.Logs


case class DummyData1(msg:String)
case class DummyData2(msg:String)

class DummyWriter1() extends WriterAdapter with Logs {

  var file:Option[File] = None

  override def init(args:Array[String]) = {
    this.file = Some(new File(args(0)))
  }
  
  override def write(data:java.lang.Object) = {
    val d = this.convert(data)
    
    info("writing to file " + this.file.map(_.toString()).getOrElse("°NONE°") )

    this.file foreach (f => {
      f.createNewFile
      val writer = new FileWriter(f)
      writer write d.msg
      writer.flush
      writer.close
    })
  }

  override def testConversion(data:java.lang.Object):Unit = convert(data)

  private def convert(data:java.lang.Object):DummyData1 = data match {
    case ret:DummyData1 => ret
    case _              => throw new ClassCastException
  }
    

  override def getDescription() = "DummyWriter1"

}

class DummyWriter2() extends WriterAdapter with Logs {
  override def init(args:Array[String]) = {
    info("nothing to init")
  }
  
  override def write(data:java.lang.Object) = 
    info( "writing message to log:" + convert(data).msg )

  override def testConversion(data:java.lang.Object):Unit = convert(data)

  private def convert(data:java.lang.Object):DummyData2 = data match {
    case ret:DummyData2 => ret
    case _              => throw new ClassCastException
  }
    

  override def getDescription() = "DummyWriter2"

}


class DummyReader1() extends ReaderAdapter with Logs {

  private var txt = ""
  
  override def getDescription() = "DummyReader1"

  override def init(args:Array[String]) = {}

  override def readLife( input:LifeInput ) = {

    info("reading lifeInput: " + input.getIdentifier)
    val it = input.getStdInput.getIterator
    while(it.hasNext) txt = txt + "\n" + it.next

    debug("finished reading lifeInput: " + input.getIdentifier)
    this
  }

  override def readPostRunSources(iterators:Array[Input]) = {
    for {
      input <- iterators.toList
    } yield debug("got postIterator: " + input.getIdentifier)
   
    this
  }

  override def getData:java.lang.Object = new DummyData1(this.txt)

  override def getDummyData:java.lang.Object = new DummyData1("dummy data")
}

class DummyReader2() extends ReaderAdapter with Logs {

  private var txt = ""
  
  override def getDescription() = "DummyReader2"

  override def init(args:Array[String]) = {}

  override def readLife( input:LifeInput ) = {

    info("reading lifeInput: " + input.getIdentifier)
    val it = input.getStdInput.getIterator
    while(it.hasNext) txt = txt + "\n" + it.next

    debug("finished reading lifeInput: " + input.getIdentifier)

    this
  }

  override def readPostRunSources(iterators:Array[Input]) = {
    for {
      input <- iterators.toList
    } yield info("reading postInput: " + input.getIdentifier)
   
    this
  }

  override def getData:java.lang.Object = new DummyData2(this.txt)

  override def getDummyData:java.lang.Object = new DummyData2("dummy data")
}


class DummyWriter3() extends WriterAdapter with Logs {
  override def init(args:Array[String]) = {
    info("nothing to init")
  }
  
  override def write(data:java.lang.Object) = 
    info( "writing message to log:" + convert(data).msg )

  override def testConversion(data:java.lang.Object):Unit = convert(data)

  private def convert(data:java.lang.Object):DummyData2 = data match {
    case ret:DummyData2 => ret
    case _              => throw new ClassCastException
  }
    

  override def getDescription() = "DummyWriter3"

}



class DummyReader3() extends ReaderAdapter with Logs {

  private var txt = ""
  
  override def getDescription() = "DummyReader3"

  override def init(args:Array[String]) = {}

  override def readLife( input:LifeInput ) = {

    info("not interested in life output")

    this
  }

  override def readPostRunSources(inputs:Array[Input]) = {
    
    if(inputs.size != 1) throw new Exception("there should be only one file input")
    
    val input = inputs(0)
    info("reading file " + input.getIdentifier())
    val it = input.getIterator

    while( it.hasNext ) {
      txt += it.next
    }

    this
  }

  override def getData:java.lang.Object = new DummyData2(this.txt)

  override def getDummyData:java.lang.Object = new DummyData2("dummy data")
}

