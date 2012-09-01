
This README is only valid for developers and will be subject to
frequent change.

1) FOLDERS AND FILES

    bin/        holds the compiled java class files

    config.xml  a config file for the Evaluation Tool. Usually filled
                with debugging and demonstration purposes in mind.

    custom/     holds the sources and compiled class files of custom
                Reader- and WriterAdapters. This folders holds a src/
                folder (for sources) and a bin/ folder (for the 
                compiled class files)

    doc/        Currently not in use but will hold javadocs and
                scaladocs.

    evaluationTool.properties
                the config file for log4j

    lib/        holds all needed libraries

    makefile    the make file

    README      this file
    
    src/        holds all the source files


2) ON UNIX AND WINDOWS

Note that that several parts of this distribution assume to be used
on a UNIX (or MacOS) system. To be run on a windows system installing
cygwin (www.cygwin.com) should do fine. Note that this aproach has
not been tested.


3) INSTALLATION AND RUNNING

Large parts of the EvaluationTool are written in Scala. Nontheless the
scala library is provided in the lib/ and all that is needed to run
the EvaluationTool is Java 1.6 (or higher) and make.
EvaluationTool can be run with the following command:
    
    make et

This will run EvaluationTool with the provided config.xml. You can 
change this file or run EvaluationTool with your own file:

    java -cp ./bin/:./lib/commons-exec-1.1.jar:./lib/scala-library.jar:./lib/akka-actor-2.0.1.jar evaluationTool.EvaluationTool °yourOwnConfig.xml°

Note that the provided config.xml relies on several standard unix
tools like date, ls and find.


4) COMPILING EVALUATIONTOOL

In order to compile the EvaluationTool will need to have scala 2.9.*
installed. Go to www.scala-lang.org/downloads an follow the
instructions. Alternatively you can check the repositories of your
Linux distribution. Note that the scala version in these repositories
are usually a little out of date and the version 2.8 (or lower) will
NOT work.
Once scala is installed you can compile the full project by running

    make

or
    make all


5) COMPILING CUSTOM ADAPTERS

To develope and run custom adapters it is not required to install
scala. Just put your sources in ./custom/src/ and run

    make customAdapters

The compiled classes will be available to be used in your config.xml.
Of course in this case you will be restricted to use java. If you
want to create scala adapters you can run

    make customScala

ReaderAdapters have to implement the following interface 
    
    evaluationTool.reader.ReaderAdapter

WriterAdapters have to implement the following interface

    evaluationTool.writer.WriterAdapter


6) MAKE COMMANDS

    et              runnes EvaluationTool with config.xml

    all             compiles all java and scala source in src/

    clean           cleans the src/ folder

    cleanCustom     cleans the custom/ folder

    todos           prints out all occurences of "todo" in all files
                    in src/

    prints          prints out all occurences of "println" in all
                    files in src/

    java            compiles all java sources in the /src

    customAdapters  compiles all custom java dapter


