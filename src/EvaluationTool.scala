package evaluationTool

import evaluationTool.data._
import evaluationTool.writer._
import evaluationTool.config._
import evaluationTool.exceptions._
import evaluationTool.reader._
//import evaluationTool.structures._
import evaluationTool.run.KickOff
import evaluationTool.properties.PropertyManager

import at.axelGschaider.loggsNProperties.Logs

import java.io.File

/**
 * Created by IntelliJ IDEA.
 * User: axel
 * Date: 26.03.12
 * Time: 13:47
 * To change this template use File | Settings | File Templates.
 */

object EvaluationTool extends Logs {

  def main(args:Array[String]) {
    
    if(args.length != 1) {
      println("usage: EvaluationTool config.xml")
      System.exit(1)
    }

    PropertyManager.initialize

    val config = ConfigReader.read(args(0))

    info("testing config " + args(0) + ". . .")
    ConfigTester.testGlobalConfig(config)
    info(". . . testing config finished")


    KickOff.start(config)
    //KickOff.forceShutdown

  }


}
