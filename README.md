## Table of contents
* [General info](#general-info)
* [Technologies](#technologies)
* [Setup](#setup)

## General info
Simple distributed system for image transferring, running on Linux.
	
## Technologies
Project is created with:
* Java version: 11
	* JAX-WS framework version: 2.3.2
	* Apache Commons IO library version: 2.4
	* Apache Log4j library version: 1.2.15
	* Maven version: 3.6.2
* PHP version : 7.2
	* Apache log4php library version: 2.3
* C
	
## Setup
1. Install Java Runtime Environment
2. Install [Maven](https://maven.apache.org/install.html)
3. Install PHP and enable extensions: *soap* and *xml*
    - For transferring large images, change properties *post_max_size* and *upload_max_filesize* in *php.ini* according to your own needs
    ```bash
    $ sudo nano /etc/php/7.2/apache2/php.ini 
    $ sudo nano /etc/php/7.2/cli/php.ini 
    $ sudo /etc/init.d/apache2 restart
    ```
4. Install [PHP Composer](https://getcomposer.org)
	```bash
    $ sudo curl -s https://getcomposer.org/installer | php
    $ sudo mv composer.phar /usr/local/bin/composer
    ```
5. Install GCC
6. Clone this repository
7. To run this project:
    - Run socket server with argument (name for file which will be saved as PNG)
    
    	*Socket TCP*
    	```bash
    	$ cd ../rmi-distributed-mutual-exclusion/socketserver/tcp
    	$ gcc socketserver.c -o socketserver
    	$ ./socketserver
    	```
	
    	*Socket UDP*
    	```bash
    	$ cd ../rmi-distributed-mutual-exclusion/socketserver/udp
    	$ gcc socketserver.c -o socketserver
    	$ ./socketserver
    	```

    - Run soap server/socket client 
    
    	*Socket TCP*
    	```bash
		$ cd ../rmi-distributed-mutual-exclusion/soapserver
		$ composer require apache/log4php
    	$ cd ../rmi-distributed-mutual-exclusion/soapserver/socketclient/tcp
    	$ php -S 127.0.0.1:8080 soapserver.php
    	```
    
    	*Socket UDP*
    	```bash
		$ cd ../rmi-distributed-mutual-exclusion/soapserver
		$ composer require apache/log4php
    	$ cd ../rmi-distributed-mutual-exclusion/soapserver/socketclient/udp
    	$ php -S 127.0.0.1:8080 soapserver.php
    	```bash

    - Run soap client with argument (path to JPEG image which will be send)
    
    	```bash
    	$ cd ../rmi-distributed-mutual-exclusion/soapclient
    	$ mvn compile
    	$ mvn exec:java -Dexec.mainClass=soapclient.SoapClient -Dexec.args='/path/to/image.jpg'
    	```
