### Java Project Structure
```
src
├── main
│   ├── java
│   │   └── com
│   │       └── example
|   |       └── bookstore // these are packages
|   |           └── shelfs  // subpackage, this will hold classes related to shelfs
|   |── resources
|
└── test
    ├── java
    │   └── com
    │       └── example
    └── resources
```

Java packages basically hold java codes, that is .class files and .java files. The resources folder holds non-java files like configuration files, images, etc.

Java codes are compiled and packaged into a compressed file called a .jar or .war file for production deployment.


### Java Modules
A module is one to one with a jar, meaning one module produces one jar file. A module can contain multiple packages. Modules help in encapsulating code and managing dependencies between different parts of a large application. 

A module definition has src folder with packages inside it. 
A module-info.java file is present inside the src folder which defines the module name and its dependencies on other modules.