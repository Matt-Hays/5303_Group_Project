# MAVEN
Maven is a build system by Apache that helps in the packaging of Java projects. It helps to resolve dependencies and can optionally utilize plugins for further build customizations. For this project, we will primarily use for dependency management and JAR file generation.

## Installation
### Debian
`sudo apt install maven`

Once installed run: `mvn --version` to display the maven and java versions.

### Windows
Directions can be found [here](https://maven.apache.org/install.html). Essentially, you're going to download a zip file from Apache that contains the maven binary.  You will extract it and add it to your path.

[VSCode](https://code.visualstudio.com/docs/java/java-build) support Maven through plugins.

[IntelliJ Community Eddition](https://www.jetbrains.com/idea/) also has build in support for maven. 

## Building
Ordinarily, all you have to do is run `mvn package` to compile and package up the project.  Maven relies on the [pom.xml](./pom.xml) files to describe dependencies as well as the build process.

You can also optionally run `mvn compile` to just compile the **.java** files, but you'll then need to run `mvn package` separately to package the **.class** files into the final JAR.

When built, the project JAR file will be in the **target** folder.

The first build may take some time as it will download any dependencies required.  There is an option to build the entire project to include all dependencies into the JAR itself.  This will inflate the size of the JAR file but will mean that we don't have to worry about missing a dependency if all we distribute is the JAR file and not the source.  I don't see that being required during this project but wanted to throw it out there.

## Run the program
To run the JAR file, `java -jar target/NAME_OF_JAR`.  As of this writing, the test setup I have is compiling the JAR file as **hr2s-0.1.0.jar** so you would run `java -jar target/hr2s-0.1.0.jar`.

The name of the resulting JAR is defined within the [pom.xml](./pom.xml) file.