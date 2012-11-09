#Installing and running edWood
Currently there is only information about how to run edWood from source. A fat
jar and instructions how to run it will be added at some point in the future.
##SBT
edWood is being developed using [SBT](http://www.scala-sbt.org/) so this is the
recommended and best tested way to install and use the developement branch.
Download and isntall SBT as described on the 
[homepage](http://www.scala-sbt.org/release/docs/Getting-Started/Setup.html).
The rest of this chapter adresses unexpereinced SBT users:

In a terminal go to the base directory of edWood and run `sbt`. This starts an
interactive shell that allowes you to run some commands:

    `compile` resolves all dependencies and compiles the programm

    `test`    resolves all dependencies and runnes all tests

    `run` *configFile* runnes edWood with the given config file

    `clean`   cleanes the workspace

If this is the your first run of sbt it will start downloading some 
dependencies right away.
The first steps should be to run `compile` to see if everything is ok and to
download all needed project specific libraries. Then run `test` to run the tests
and download additional dependencies.
From now on you are ready to run `run config.xml`. This will run edWood with the
provided `config.xml`.
##Eclipse
Feel free to use any scala plugin for eclipse but I highly recommend the 
"official" [Scala IDE](http://scala-ide.org/) for the best out-of-the box
experience.
###Eclipse & SBT
If you want to develope edWood with Eclipse and the easiest way is to use the
[sbteclipse plugin](https://github.com/typesafehub/sbteclipse). This is a 
plugin for sbt that allowes you to create the `.classpath` and `.project` files
for eclipse.

This plugin is added to the project dependencies. So all you need to do after
compiling is to run the command `eclipse` in the sbt shell (you might have to
restart sbt after the dependencies have been resolved). After this you can
import the base directory of edWood as Scala project into eclipse. Don't choose
the "copy to workspace" option (you can move the base directory to your 
workspace before importing).
Now you can follow your usual workflow without needing sbt. 
###Eclipse without sbt
This is absolutely untested but might provide some information for you if you,
for some reason, do not want to install sbt. There is a `.project` and a
`.classpath` file included in the folder `eclipse/`. Copy these files to the 
base folder of edWood you should be a able to import the base folder as project
right away. The problem is that you will have lots of unresolved dependencies
that you will have to download and reset manually.


__EVERYTHING BELLOW THIS POINT IS OUTDATED AND WILL BE REMOVED SOON__

This README is only valid for developers and will be subject to
frequent change.

1) FOLDERS AND FILES

The sbt defaults are respected. Additional files are:

    config.xml  a config file for edWood. Usually filled with debugging
                and demonstration purposes in mind.

    custom/     holds the sources and compiled class files of custom
                Reader- and WriterAdapters. This folders holds a src/
                folder (for sources) and a bin/ folder (for the 
                compiled class files)

    edWood.properties
                the config file for log4j

    README      this file

    *.log       log files. Can be savely deleted after edWood has stopped

2) ON UNIX AND WINDOWS

Note that that several parts of this distribution assume to be used
on a UNIX (or MacOS) system. To be run on a windows system installing
cygwin (www.cygwin.com) should do fine. Note that this aproach has
not been tested.


3) INSTALLATION AND RUNNING

TODO: update the chapter!!! Now sbt is needed and make no longer supported

Large parts of edWood are written in Scala. Nontheless the scala library 
is provided as sbt dependecy and all that is needed to run the edWood is 
Java 1.6 (or higher) and sbt.
edWood can be run with the following command:
    
    make ed

This will run edWood with the provided config.xml. You can change this 
file or run edWood with your own file:

    java -cp ./bin/:./lib/commons-exec-1.1.jar:./lib/scala-library.jar:./lib/akka-actor-2.0.1.jar edWood.edWood °yourOwnConfig.xml°

Note that the provided config.xml relies on several standard unix
tools like date, ls and find.


4) COMPILING EDWOOD

TODO: update the chapter!!! Now sbt is needed and make no longer supported

In order to compile the edWood will need to have scala 2.9.\*
installed. Go to www.scala-lang.org/downloads and follow the
instructions. Alternatively you can check the repositories of your
Linux distribution. Note that the scala version in these repositories
are usually a little out of date and the version 2.8 (or lower) will
NOT work.
Once scala is installed you can compile the full project by running

    make

or

    make all


5) COMPILING CUSTOM ADAPTERS

TODO: update the chapter!!! Now sbt is needed and make no longer supported

To develope and run custom adapters it is not required to install
scala. Just put your sources in ./custom/src/ and run

    make customAdapters

The compiled classes will be available to be used in your config.xml.
Of course in this case you will be restricted to use java. If you
want to create scala adapters you can run

    make customScala

ReaderAdapters have to implement the following interface 
    
    edWood.reader.ReaderAdapter

WriterAdapters have to implement the following interface

    edWood.writer.WriterAdapter

