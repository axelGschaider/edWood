package edWood.properties

import at.axelGschaider.loggsNProperties.{PropertyManager => PM}

object PropertyManager extends PM {
  lazy val defaultPropertiesFile: String =  "edWood.properties"


  override def internalInit():Unit = {}
  
}