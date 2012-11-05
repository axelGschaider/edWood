__README HAS NOT BEEN UPDATED SINCE THE MOVE TO SBT__

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

