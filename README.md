## Table of contents
* [General info](#general-info)
* [Technologies](#technologies)
* [Setup](#setup)

## General info
Simple distributed system for image transferring, running on Linux.

## Technologies
Project is created with:
* Java version: 11
* JAX-WS version: 2.3.1
* Apache Commons IO version: 1.3.2
* Apache Log4j version: 2.12.1
* Maven version: 3.6
* PHP version : 7.3
* Apache log4php version: 2.3
* Composer version: 1.9.1
* C

## Setup
1. Install Java Runtime Environment
2. Install [Maven](https://maven.apache.org/install.html)
3. Install PHP and enable extensions: `soap` and `xml`
   - For transferring large images, change properties `post_max_size` and `upload_max_filesize` in `php.ini` according to your own needs
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
5. Install [GCC](https://gcc.gnu.org)
6. Clone this repository
7. Download Composer dependencies
```bash
$ cd ../rmi-distributed-mutual-exclusion/soap-server
$ composer require apache/log4php
```
8. To run this project:
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
        $ cd ../rmi-distributed-mutual-exclusion/soap-server/scr/socket/tcp
        $ php -S 127.0.0.1:8080 soapserver.php
        ```
        *Socket UDP*
        ```bash
        $ cd ../rmi-distributed-mutual-exclusion/soap-server/src/socket/udp
        $ php -S 127.0.0.1:8080 soapserver.php
        ```
    - Run soap client with argument (path to JPEG image which will be send)
        ```bash
        $ cd ../rmi-distributed-mutual-exclusion/soap-client
        $ mvn compile
        $ mvn exec:java -Dexec.mainClass=image.transfer.soap.client.SoapClient -Dexec.args='/path/to/image.jpg'
        ```