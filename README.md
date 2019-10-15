## Table of contents
* [General info](#general-info)
* [Technologies](#technologies)
* [Setup](#setup)

## General info
Simple distributed system for image transferring.
	
## Technologies
Project is created with:
* Java version: 11
	* JAX-WS framework version: 2.3.2
	* Apache Commons IO library version: 2.4
	* Apache Log4j library version: 1.2.15
	* Maven version: 3.6.2
* PHP version : 7.3.4
	* Apache log4php library version: 2.3
* C
	
## Setup
1. Install Java Runtime Environment
2. Install [Maven](https://maven.apache.org/install.html)
3. Install PHP and enable extensions: *soap* and *xml*
4. Install GCC
3. Clone this repository
4. To run this project:
    - Run socket server with argument (name for file which will be saved as PNG)
    
    	*Socket TCP*
    	```
    	$ cd ../rmi-distributed-mutual-exclusion/socketserver/tcp
    	$ gcc socketserver.c -o socketserver
    	$ ./socketserver
    	```
	
    	*Socket UDP*
    	```
    	$ cd ../rmi-distributed-mutual-exclusion/socketserver/udp
    	$ gcc socketserver.c -o socketserver
    	$ ./socketserver
    	```

    - Run soap server/socket client 
    
    	*Socket TCP*
    	```
    	$ cd ../rmi-distributed-mutual-exclusion/soapserver/socketclient/tcp
    	$ php -S 127.0.0.1:8080 soapserver.php
    	```
    
    	*Socket UDP*
    	```
    	$ cd ../rmi-distributed-mutual-exclusion/soapserver/socketclient/udp
    	$ php -S 127.0.0.1:8080 soapserver.php
    	```

    - Run soap client with argument (path to JPEG image which will be send)
    
    	```
    	$ cd ../rmi-distributed-mutual-exclusion/soapclient
    	$ mvn compile
    	$mvn exec:java -Dexec.mainClass=soapclient.SoapClient -Dexec.args='/path/to/image.jpg'
    	```
