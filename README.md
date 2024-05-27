

This project is build with\
Intellij IDEA 2023.1 (Community Edition)\
java 1.7 (7)\
Derby Database\
and use next additional libs\
derbyclient.jar / used for derby database operations / \
itext-pdfa-5.5.1.jar / used to create pdf documents / \
itext-xtra-5.5.1.jar  / used to create pdf documents / \
itextpdf-5.5.1.jar  / used to create pdf documents / \
jxl.jar / used to create and load xls data sheet / \
pdf-renderer-1.0.5.jar / used to render pdf documents / 

located in the root folder on the projects, for
use of runnable jar file (to create runnable jar file click Build->Build Artifacts->Rebuild)
and from Project Settings select Artifacts and choose Output directory where will be created runnable jar file.

And imported from IDE -> Files -> Project Setting -> Libraries -> click on the plus button 
and added to project, to use from Intelij project.

to start network derby database set derby *.jar files to CLASSPATH on your computer
Right Click on Computer , click Properties , click Advanced System Settings , click Environment Variables , 
select Path , click  Edit and Add Path to Derby lib folder as Home Varaible to Path
where are contained derby .jar files\

to start network server click start.bat file in root folder on the project, you may 
editing *.bat files to run/stop network or local host if needed..
if you need to run network server without open cmd window use *.vbs script and locate them in startup folder of the PC.

For more details about configuring derby database read below....

# Embedded Derby
When an application accesses a Derby database using the Embedded Derby JDBC driver, the Derby engine does not run in a separate process, and there are no separate database processes to start up and shut down. Instead, the Derby database engine runs inside the same Java Virtual Machine (JVM) as the application. So, Derby becomes part of the application just like any other jar file that the application uses. Figure 1 depicts this embedded architecture.
![image](https://github.com/mittko/Personal-Protective-Equipment/assets/6568414/41ddcdee-db1a-42e4-bf9c-df412e43b875)
# Configure Embedded Derby
To use Derby in its embedded mode set your CLASSPATH to include the jar files listed below\
Right Click on Computer , click Properties , click Advanced System Settings , click Environment Variables , select Path , click  Edit and Add Path to Derby lib folder as Home Varaible to Path
where are contained derby .jar files\
derby.jar: contains the Derby engine and the Derby Embedded JDBC driver\
derbytools.jar: optional, provides the ij tool that is used by a couple of sections in this tutorial

You can set your CLASSPATH explicitly with the command shown below\
Windows:
C:\> set CLASSPATH=%DERBY_INSTALL%\lib\derby.jar;%DERBY_INSTALL%\lib\derbytools.jar;%DERBY_INSTALL%\lib\derbyoptionaltools.jar;%DERBY_INSTALL%\lib\derbyshared.jar;.
# A quick look at the code
# Load the Embedded JDBC Driver
The SimpleApp application loads the Derby Embedded JDBC driver and starts Derby up with this code:

public String driver = "org.apache.derby.jdbc.EmbeddedDriver";
...
Class.forName(driver).newInstance();
# Get an Embedded Connection
he SimpleApp application creates and connects to the derbyDB database with this code:

public String protocol = "jdbc:derby:";
...
conn = DriverManager.getConnection(protocol + "path/to/derbyDB;create=true", props);

That embedded connection URL, fully constructed, looks like this:

jdbc:derby:path/to/derbyDB;create=true

# Shut Derby down
A clean shutdown performs a checkpoint and releases resources. If an embedded application doesn't shut down Derby, a checkpoint won't be performed. Nothing bad will happen; it just means that the next connection will be slower because Derby will run its recovery code.

Code to shut down a specific database looks like this:

DriverManager.getConnection("jdbc:derby:MyDbTest;shutdown=true");

Code to shut down all databases and the Derby engine looks like this:

DriverManager.getConnection("jdbc:derby:;shutdown=true");

______________________________________________________________________________________________________________________________________________________________\
As explained in the above section, an application can embed Derby, which means that the Derby engine runs in the same JVM as the application.

However, an application can also access a Derby database using the more familiar client/server mode. This is achieved via a framework that embeds Derby and handles database requests from applications, including applications running in different JVMs on the same machine or on remote machines. The Derby Network Server is such a framework. It embeds Derby and manages requests from network clients, as depicted in Figure 2.

![image](https://github.com/mittko/Personal-Protective-Equipment/assets/6568414/df11c83f-d821-47ed-bdc4-209ec82b4e1c)

# Derby Network Server
# Configure environment

To start or stop the Network Server, set your CLASSPATH to include the jar files listed below\
Right Click on Computer , click Properties , click Advanced System Settings , click Environment Variables , select Path , click  Edit and Add Path to Derby lib folder as Home Varaible to Path
where are contained derby .jar files\
derbynet.jar: contains the code for the Derby Network Server and a reference to the engine jar file (derby.jar)\
derbytools.jar: contains Derby tools\
You can set your CLASSPATH explicitly with the command shown below

Windows:
C:\> set CLASSPATH=%DERBY_INSTALL%\lib\derbytools.jar;%DERBY_INSTALL%\lib\derbynet.jar;.
# Start Network Server
Start the Network server by executing the startNetworkServer.bat (Windows) or startNetworkServer (UNIX) script. This will start the Network Server up on port 1527 and echo a startup message

Windows:
C:\Apache\db-derby-10.4.1.3-bin\bin> startNetworkServer.bat\
.....\
Security manager installed using the Basic server security policy.
Apache Derby Network Server - 10.4.1.3 - (648739) started and ready to 
	accept connections on port 1527 at 2008-04-28 17:13:13.921 GMT

Messages will continue to be output to this window as the Network Server processes connection requests.

The Network Server starts Derby, so you'll find the derby.log error log in the directory where you start the Network Server.

# An easier way: derbyrun.jar
Furthermore, it is much easier to start Network Server now than before, due to various jar file improvements. With the latest releases, the entire sections "Configure environment" and "Start Network Server" could actually be replaced with just one command line:

Windows:
C:\Apache\db-derby-10.4.1.3-bin\lib> java -jar derbyrun.jar server start

# Configure environment to use Derby Network Client JDBC driver
To use the Derby Network Client JDBC driver, set your CLASSPATH to include the jar files listed below\
Right Click on Computer , click Properties , click Advanced System Settings , click Environment Variables , select Path , click  Edit and Add Path to Derby lib folder as Home Varaible to Path
where are contained derby .jar files\
derbyclient.jar: contains the JDBC driver\
derbytools.jar: optional, provides the ij tool\
You can set your CLASSPATH explicitly with the command shown below

Windows:
C:\> set CLASSPATH=%DERBY_INSTALL%\lib\derbyclient.jar;%DERBY_INSTALL%\lib\derbytools.jar;.

# A quick look at the code
# Load the Client JDBC Driver
When executed with the derbyclient argument, the SimpleApp application loads the Derby Network Client driver with this code

driver = "org.apache.derby.jdbc.ClientDriver";\
...
Class.forName(driver).newInstance();

# Get a Network Server Connection
When executed with the derbyclient argument, the SimpleApp application creates and connects to the derbyDB database with this code

protocol = "jdbc:derby://localhost:1527/";
...
conn = DriverManager.getConnection(protocol + "derbyDB;create=true", props);

That connection URL, fully constructed, looks like this

jdbc:derby://localhost:1527/derbyDB;create=true

# Don't shut Derby down
If you look at the SimpleApp.java code you'll notice that it only shuts Derby down if it's running in embedded mode. When connecting via the Network Server, other applications might be accessing the same database you are; so, don't shut down the databases or Derby.

# Stop Network Server
Stop the Network server by executing the stopNetworkServer.bat (Windows) or stopNetworkServer (UNIX) script, as shown below

Windows:
C:\> cd %DERBY_INSTALL%\bin
C:\Apache\db-derby-10.4.1.3-bin\bin> setNetworkServerCP.bat
C:\Apache\db-derby-10.4.1.3-bin\bin> stopNetworkServer.bat

The window running the NetworkServer should output a message confirming the shutdown.

Note that, as with starting the server, there is also an easier way to shut down the server. For example

Windows:
C:\> java -jar %DERBY_INSTALL%\lib\derbyrun.jar server shutdown

# Embedded Server
Up until this point, this section has focused on how to start and stop the Network Server as an independent process.

Another option, called the "embedded server", allows an application to load the embedded JDBC driver for its own use and start the Network Server to allow remote access by applications running in other JVMs. The application that does this can access the database with either the embedded driver or the client driver.

Figure 3 depicts an application called MyEmbedSrvApp that loads the embedded driver and also starts up the Network Server. This allows other applications, such as ij or SimpleApp using the Derby Network Client, to connect to the same database via a client JDBC driver.

Figure 3: Derby Embedded Server Architecture

![image](https://github.com/mittko/Personal-Protective-Equipment/assets/6568414/04bb54f2-bd2c-4045-aa03-6a8cb36c76b0)


While it is certainly possible for MyEmbedSrvApp to use the Derby Network Client JDBC driver, it's probably better for it to use the embedded driver because Derby Network Client JDBC database requests incur the overhead of going out through the network. The embedded server architecture lets the application that embeds Derby retain the advantages of embedded access while also enabling remote access to the same database using another tool, such as ij. Thus, you end up with the best of both worlds: embedded and client/server.
