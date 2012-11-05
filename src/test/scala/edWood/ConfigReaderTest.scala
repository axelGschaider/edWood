package edWood

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import edWood.config.ConfigReader

@RunWith(classOf[JUnitRunner])
class ConfigReaderTest extends FunSuite {
  
  
  test("minimal xml without jobs") {
    val data = 
<edWood>
  <instanceId>idid</instanceId>
  <maxJobs>666</maxJobs>
</edWood>
      
    val config = ConfigReader.read(data)
    
    assert(config.general.actorSystem == "idid")
    assert(config.general.maxJobs == 666)
    assert(config.general.interpreter.size == 2) // was set to default
    assert(config.general.interpreter(0) == "/bin/sh")
    assert(config.general.interpreter(1) == "-c")
    assert(!config.general.workingDirectory.isDefined)
    assert(config.jobs.size == 0)
      
  }
  
  test("maximal xml without jobs") {
    
    val data = 
<edWood>
  <instanceId>idi</instanceId>
  <maxJobs>665</maxJobs>
  <interpreter>
    <part>lalala</part>
    <part>lululu</part>
    <part>lilili</part>
  </interpreter>
  <workingDirectory>somewhere</workingDirectory>
  <unknownTag>i will be ignored</unknownTag>
</edWood>
      
    val config = ConfigReader.read(data)
    
    assert(config.general.actorSystem == "idi")
    assert(config.general.maxJobs == 665)
    assert(config.general.interpreter.size == 3) 
    assert(config.general.interpreter(0) == "lalala")
    assert(config.general.interpreter(1) == "lululu")
    assert(config.general.interpreter(2) == "lilili")
    assert(config.general.workingDirectory.isDefined)
    assert(config.general.workingDirectory.get == "somewhere")
    assert(config.jobs.size == 0)
  }
  
  test("single minimal job") {
    val data = 
<edWood>
  <instanceId>idid</instanceId>
  <maxJobs>666</maxJobs>
  <job>
    <id>some id</id>
    <command>compile</command>
    <reader><classPath>edWood.dummy.DummyReader2</classPath></reader>
    <writer><classPath>edWood.dummy.DummyWriter2</classPath></writer>
  </job>
</edWood>
      
    val config = ConfigReader.read(data)
    assert(config.jobs.length == 1)
    
    val job = config.jobs.head
    assert(job.id == "some id")
    assert(job.command.main == "compile")
    assert(job.command.pre.length == 0)
    assert(job.command.post.length == 0)
    assert(!job.command.workingDirectory.isDefined)
    assert(job.reader.getDescription() == "DummyReader2")
    assert(job.writers.length == 1)
    assert(job.writers.head.getDescription() == "DummyWriter2")
    
      
  } 
  
  test("single maximal job") {
    val data = 
<edWood>
  <instanceId>idid</instanceId>
  <maxJobs>666</maxJobs>
  <job>
    <id>some other id</id>
    <pre>hard work</pre>
    <pre>spilling tears</pre>
    <command>do what needs to be done</command>
    <post>massive profit</post>
    <post>love for everyone</post>
    <post>world peace</post>
    <reader><classPath>edWood.dummy.DummyReader1</classPath></reader>
    <writer>
      <classPath>edWood.dummy.DummyWriter1</classPath>
      <arg>output.ls</arg>
    </writer>
    <writer><classPath>edWood.dummy.DummyWriter2</classPath></writer>
    <workingDirectory>neverland</workingDirectory>
  </job>
</edWood>
      
    val config = ConfigReader.read(data)
    assert(config.jobs.length == 1)
    
    val job = config.jobs.head
    assert(job.id == "some other id")
    assert(job.command.main == "do what needs to be done")
    
    assert(job.command.pre.length == 2)
    assert(job.command.pre.head == "hard work")
    assert(job.command.pre.tail.head == "spilling tears")
    
    assert(job.command.post.length == 3)
    assert(job.command.post.head == "massive profit")
    assert(job.command.post(2) == "world peace")
    
    assert(job.command.workingDirectory.isDefined)
    assert(job.command.workingDirectory.get == "neverland")
    
    assert(job.reader.getDescription() == "DummyReader1")
    assert(job.writers.length == 2)
    assert(job.writers.head.getDescription() == "DummyWriter1")
    assert(job.writers.head.getDescription() == "DummyWriter2")
    
      
  } 
}