package edWood

import edWood.data._
import edWood.writer._
import edWood.config._
import edWood.exceptions._
import edWood.reader._
//import edWood.structures._
import edWood.run.KickOff
import edWood.properties.PropertyManager

import at.axelGschaider.loggsNProperties.DefaultLogs

import java.io.File

/**
 * Created by IntelliJ IDEA.
 * User: axel
 * Date: 26.03.12
 * Time: 13:47
 * To change this template use File | Settings | File Templates.
 */

object EdWood extends DefaultLogs {

  def main(args:Array[String]) {
    
    if(args.length != 1) {
      println("usage: EdWood config.xml")
      System.exit(1)
    }

    PropertyManager.initialize

    val config = ConfigReader.read(args(0))

    info("testing config " + args(0) + ". . .")
    ConfigValidator.testGlobalConfig(config)
    info(". . . testing config finished")


    KickOff.start(config)
    //KickOff.forceShutdown

  }


}
