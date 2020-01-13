## Table of contents
* [General info](#general-info)
* [Technologies](#technologies)
* [Setup](#setup)

## General info
Simple distributed system for image transferring, running on Linux.

## Technologies
Project is created with:
* Java version: 8
* Apache Commons IO version: 1.3.2
* Apache Log4j version: 2.12.1
* Maven version: 3.6.3
* PHP version : 7.4.1
* Apache log4php version: 2.3
* Composer version: 1.9.1
* C

## Setup
1. Install Java Runtime Environment
2. Install [Maven](https://maven.apache.org/install.html)
3. Install PHP and enable extensions: `soap` and `xml`
   - For transferring large images, change properties `post_max_size` and `upload_max_filesize` in `php.ini` according to your own needs
   ```bash
   $ sudo nano /etc/php/php.ini
   ```
4. Install [PHP Composer](https://getcomposer.org)
```bash
$ sudo curl -s https://getcomposer.org/installer | php
$ sudo mv composer.phar /usr/local/bin/composer
```
5. Install [GCC](https://gcc.gnu.org)
6. Install MAKE for running Makefiles
7. Clone this repository
8. Download Composer dependencies
```bash
$ cd ../image-transferring-distributed-system/soap-server
$ composer require apache/log4php
```
9. To run this project:
    - Run socket server with argument (name for file which will be saved as PNG)
    
        *Socket TCP*
        ```bash
        $ cd ../image-transferring-distributed-system/socket-tcp-server
        $ mkdir bin
        $ make
        $ ./bin/main filename_for_downloading_image
        ```
        *Socket UDP*
        ```bash
        $ cd ../image-transferring-distributed-system/socket-udp-server
        $ mkdir bin
        $ make
        $ ./bin/main filename_for_downloading_image
        ```
    - Run soap server/socket client
    
        *Socket TCP*
        ```bash
        $ cd ../image-transferring-distributed-system/soap-server/src/socket/tcp
        $ php -S 127.0.0.1:8080 soapserver.php
        ```
        *Socket UDP*
        ```bash
        $ cd ../image-transferring-distributed-system/soap-server/src/socket/udp
        $ php -S 127.0.0.1:8080 soapserver.php
        ```
    - Run soap client with argument (path to JPEG image which will be send)
        ```bash
        $ cd ../image-transferring-distributed-system/soap-client
        $ mvn compile
        $ mvn exec:java -Dexec.mainClass=imagetransferring.soapclient.SoapClient -Dexec.args='/path/to/image.jpg'
        ```
