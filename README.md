This project implements a Hadoop MapReduce job to count word occurrences in a text file.  
It is designed for people who are new to Hadoop and distributing computing  
It consists of three Java classes written in IntelliJ:  
  1. WordCountMapper - Maps the input text into key-value pairs (<word, 1>).
  2. WordCountReducer - Aggregates word counts and produces final results (<word, count>).
  3. WordCountDriver - Configures and runs the Hadoop job. It defines the input and output files, the mapper and reducer classes responsible for the data transformations, and the data types involved.


Prerequisites
Apache Hadoop installed and configured.
Java installed (JDK 8 or higher).
The project packaged as a JAR file.

## Create the Java class with the variable dependencies. Compile and package(create a jar file)
1. Set Up Your Java Project in IntelliJ
   
   a. open IntelliJ -->File --> New --> Project --> choose a project name and location. Select Maven as the build system. my project's name is `wordcount`
   
   b. Once it opens up, in src/main/java directory(left hand side) use the package called org.example / or you can create your own. I did a new package called neiu.wordcount
   
   c. Right click on the package --> new -->Java class. Write the Java classes as follows: WordCountMapper, WordCountReducer, WordCountDriver
3. Configure Dependencies (pom.xml)
    Because we choose Maven, a file called pom.xml was created by default.
    Go in pom.xml and all the way at the bottom, after the </property> add the following dependencies:
   
```
<dependencies>
  <!-- https://mvnrepository.com/artifact/org.apache.hadoop/hadoop-client -->
  <dependency>
      <groupId>org.apache.hadoop</groupId>
      <artifactId>hadoop-client</artifactId>
      <version>3.4.1</version>
  </dependency>
</dependencies>
```
    
3. Build and Run the Project
a. open the terminal --> navigate to the root directory of your project that contains the pom.xml file.
    * you navigate using cd command: cd path/to/your/project: eg `cd/apache-spark/spark-3.5.5-bin-hadoop3/java-work/word-count`
b. compiling the project by typing in the terminal: `mvn clean compile`
c. package the project into a JAR file: run `mvn clean package`
    * this command generate a JAR file from the compiled class in newly created  /target folder in our wordcount directory
    * Check your work by going to the root directory, and check for the jar file that should be called wordcount-1.0-SNAPSHOT.jar




# **Steps to run the Project**:
## 1. Download the input file that we will work with 
- I had already a text file in my PC that I worked with

## 2. Set up Hadoop:  Make sure Hadoop is installed and running on your Mac.

## 3. Start Hadoop(HDFS and YARN):
* open terminal(on Mac: Cmd + space bar -->Terminal)
* start Hadoop by running on the terminal: "start-dfs.sh" and "start-yarn.sh"
* open the Hadoop web interface/browser to check if HDFS started: http://localhost:9870 -->
* you should see a green header with the message: "Overview: localhost:9000 active"
* If the page doesn't open up, HDFS wasn't configure properly

## 4. Upload the text file to HDFS(Hadoop Distributed File System):
* create a directory called "test" in HDFS by running in the terminal: "hadoop fs -mkdir /test"
* go to the Hadoop web interface to check your work: . -->Utilities(top right hand side) -->Browse the file system --> you should see your newly created directory called test
* copy the text file from your local machine to HDFS: in the terminal run `hadoop fs -copyFromLocal <localFilePath>  <hdfsPath>`
  * replace `<LocalFilePath>` with your text file path. If you don't know the file path, go in the folder where text file is, right click on it -->get info-->the path is displayed there
  * eg. /Users/anedelcu/Lavinia_Nedelcu/School/datasets-for-project/textsample.txt
  * replace `<hdfsPath>` with /test (= you want to copy the text file to the directory /test)
  * should look like: `hadoop fs -copyFromLocal /Users/anedelcu/Lavinia_Nedelcu/School/datasets-for-project/textsample.txt /test`

- a. Verify if the transfer from local to HDFS was successful: 2 ways
    * check the web interface again, http://localhost:9870: in the /test should be the uploaded text file that you worked with
    * in the terminal, run `hadoop fs -ls /test` = this will display the content of the test directory
- b. Extra: display the content of the /test directory on the terminal: in the terminal run:
    * `hadoop fs -cat<fileName>` eg: "Hadoop fs -cat<textsample.txt>"

## 5.Run the MapReduce job:  
a.in the terminal run the job using "hadoop jar" command:  `hadoop jar <JARpath> <mainclass> <inputfile> <outputdirectory> `  
    * replace `<JARpath>` with full path to the Jar  
    * replace `<mainclass>`: must include its full path. If a package was created, ensure the package name is included.
        eg: my main class is WordCountDriver.java within the package neiu.wordcount, so it should be specified as neiu.wordcount.WordCountDriver  
    * replace `<outputdirectory>`: this outputdirectory doesn't have to exist beforehand, Hadoop will create it automatically. Just give it a name such as output
        eg. "test/output" = put the output file in the "/output" directory located in the test directory we created  
    * full command in my case: `hadoop jar /User/anedelcu/hadoop-install/hadoop-3.4.1/Java-work/wordcount/target/symptomfrequency.jar
        com.neiu.symptomfrequency.SymptomFrequencyDriver /test/Dry_Eye.Dataset.csv /test/output ` 
      * !!   If the job fails during execution, you must change the output directory name before re-running the command; otherwise, it will not work  
      
b. check the Hadoop web browser:  http://localhost:9870: in the /test you should have the "output" directory.
          Open it up and if the job was successful you should see 2 or more file: _SUCCESS, part-r-0000 and part-r-0001(if multiple reducers are used)

## 6. View the output on the terminal
* in the terminal run "hadoop fs -cat /test/output/part-r-00000"

## 7.Copy the output to your local system(if you want)
* The Reducer writes the final aggregated results to HDFS, so you can copy to local machine if you want
* type in the terminal `hdfs dfs -copyToLocal /test/output /local/path`

## 8. If re-running the job
* HDFS does not allow overwriting directories.
* before re-running the job, delete the previous output directory: "hdfs dfs -rm -r /test/output"



