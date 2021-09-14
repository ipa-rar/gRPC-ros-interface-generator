# ROS2 interface generator

## Build

The interface generator can be imported as an existing project into an Eclipse workspace. The xtend SDK must be installed in Eclipse and UTF-8 configured as workspace character encoding. The com.google.protobuf library needs to present in your .m2 repository (the maven build below downloads it automatically, if not already present)

In docker, the interface generator is build with maven. 

	# compile, result in subfolder org.acumos.gen.ros/target/classes
	$ mvn clean compile

	# create jars, including a single jar with all dependencies
	$ mvn clean package

## Run
The generator can be manually started with

	$ java -jar org.acumos.gen.ros/target/org.acumos.gen.ros-0.7.0-SNAPSHOT-jar-with-dependencies.jar
		<location of a pre-compiled proto descriptor file> 

The manual invocation is used for testing purposes and will be replaced by an integration of the service in a chain.
