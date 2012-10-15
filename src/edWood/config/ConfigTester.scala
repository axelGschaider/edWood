package edWood.config

object ConfigTester {

  def testGlobalConfig(conf:GlobalConfig) = {
    
    testGeneralConfig(conf.general)
    conf.jobs.foreach(testJobConfig(_))
    testIds(conf.jobs)

  }

  def testIds(confs:List[JobConfig]) = {
    confs.map(_.id).foldLeft(List.empty[String])( (list, id) => {
      if(list contains id)
        throw new Exception("duplicate id: " + id)
      id :: list
    } )
  }

  def testGeneralConfig(conf:GeneralConfig) = {
    if(conf.maxJobs < 1) throw new Exception("maxJobs < 1")
  }

  def testJobConfig(conf:JobConfig) = {
    
    val data = conf.reader.getDummyData

    conf.writers.foreach( _ testConversion data )

  }

}


