package at.axelGschaider.loggsNProperties {
  import org.apache.log4j.{Logger, PropertyConfigurator}
  import java.io.File
  import java.util.Properties

  trait Logs {

    def getName():String;

    private[this] val logger = Logger.getLogger(getName());

    import org.apache.log4j.Level._

    private implicit def string2CleanableString(s:String):CleanableString = CleanableString(s)
    
    def debug(message: => String) = if (logger.isEnabledFor(DEBUG)) logger.debug(message.clean)
    def debug(message: => String, ex:Throwable) = if (logger.isEnabledFor(DEBUG)) logger.debug(message,ex)
    def debugValue[T](valueName: String, value: => T):T = {
      val result:T = value
      debug(valueName + " == " + result.toString)
      result
    }

    def info(message: => String) = if (logger.isEnabledFor(INFO)) logger.info(message.clean)
    def info(message: => String, ex:Throwable) = if (logger.isEnabledFor(INFO)) logger.info(message.clean,ex)

    def warn(message: => String) = if (logger.isEnabledFor(WARN)) logger.warn(message.clean)
    def warn(message: => String, ex:Throwable) = if (logger.isEnabledFor(WARN)) logger.warn(message.clean,ex)

    def error(ex:Throwable) = if (logger.isEnabledFor(ERROR)) logger.error(ex.toString,ex)
    def error(message: => String) = if (logger.isEnabledFor(ERROR)) logger.error(message.clean)
    def error(message: => String, ex:Throwable) = if (logger.isEnabledFor(ERROR)) logger.error(message.clean,ex)

    def fatal(ex:Throwable) = if (logger.isEnabledFor(FATAL)) logger.fatal(ex.toString,ex)
    def fatal(message: => String) = if (logger.isEnabledFor(FATAL)) logger.fatal(message.clean)
    def fatal(message: => String, ex:Throwable) = if (logger.isEnabledFor(FATAL)) logger.fatal(message.clean,ex)



  }

  trait DefaultLogs extends Logs {

    override def getName() = getClass().getName()
    
  }

  trait LogsWithId extends Logs {
    
    def loggingId:String;
    def getName() = getClass().getName() + " {" + loggingId + "}"
    
  }

  trait LogsWithLazyId extends LogsWithId {
    private var lid:Option[String] = None

    def setLoggingId(i:String) = lid = Some(i)

    override def loggingId = lid.getOrElse("°no id°")

  }

  private case class CleanableString(s:String) {
    def clean = s.replace("\r\n", "\t").replace("\n\r", "\t").replace("\n", "\t").replace("\r", "\t")
  }

  abstract class PropertyManager extends DefaultLogs{
  
    val defaultPropertiesFile: String
    protected def internalInit():Unit

    private lazy val properties:Option[/*Map[String,String]*/(String, Properties)] = loadProperties(defaultPropertiesFile)

    def propertiesFile = defaultPropertiesFile
  
    def fileExists(file:String) : Boolean = new File(file).exists()
    
    //def getPropertiesInstance(fname:String):Option[]
    
    def storeProperty(key:String, value:String):Boolean = properties match {
      case None       => false
      case Some((f, p)) => {
        try {
          p.setProperty(key, value)
          
          val fos = new java.io.FileOutputStream(f)
          
          p.store(fos, "")
          
          fos.close
          
          true
          
        } catch {
          case e => {
            error("Error while writing properties to file '" + f + "': " + e.getMessage)
            false
          }
        }
      }
    }
    
    def loadProperties(fname:String):Option[(String, Properties)] = {
      try {
        info("Loading properties from '" + fname + "' . . . ")
        val file = new java.io.FileInputStream(fname)
        val props = new Properties
        props.load(file)
        file.close
        info(". . . Loading properties done.")
        Some((fname, props))
      } catch {
        case e:Exception => error("Properties.loadFile: " )
        None
      }
    }

    

    private var initialized: Boolean = false

    def getProperty(prop:String):Option[String] = properties match {
      case Some((_,pr)) => Option(pr getProperty prop)
      case None     => None
    }

    def getPropertyWithDefault(prop:String, default:String):String = (getProperty(prop)) match {
      case Some(pr) => pr
      case None     => {
        debug("property '" + prop + "' was not found. Fallback to default.")
        default
      }
    }

    def getIntPropertyWithDefault(prop:String, default:Int):Int = (getProperty(prop)) match {
      case Some(pr) => try {
                          pr.toInt
                        } catch {
                          case _ : java.lang.NumberFormatException => {
                            debug("property '" + prop + "' could not be cast to Int. Fallback to default.")
                            default
                          }
                        }
      case None     => {
        debug("property '" + prop + "' was not found. Fallback to default.")
        default
      }
    }


    def initialize: Unit = {
      if (initialized) {
        debug("intialisation abborted (allready initialized)")
        return
      }

    
      PropertyConfigurator.configure(defaultPropertiesFile)

      info("Logger initialized with main properties file '" + defaultPropertiesFile + "'.")
      getProperty("test")
      info("Continue initialization . . . ")

      internalInit()

      info(". . . finished initialization.")

    }
  }
}



