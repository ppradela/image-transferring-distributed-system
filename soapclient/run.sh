#!/bin/bash

#do args wpisz lokalizację zdjęcia

mvn exec:java -Dexec.mainClass=soapclient.SoapClient -Dexec.args='/home/user/Pobrane/image.jpg'