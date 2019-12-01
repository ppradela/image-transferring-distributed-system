<?php

include dirname(__DIR__, 3) . '/vendor/autoload.php';
Logger::configure(dirname(__DIR__, 3) . '/config/log4php_udp.xml');

function sendImageHexString($imageHexString)
{
    $logger = Logger::getLogger("");
    error_reporting(~E_WARNING);

    $server = '127.0.0.1';
    $port = 9999;

    $logger->info("Creating socket");
    if (!($sock = socket_create(AF_INET, SOCK_DGRAM, 0))) {

        $logger->error("Couldn't create socket");
        die();
    }

    $logger->info("Socket created");

    $parts = str_split($imageHexString, 1024);
    array_push($parts, 'STOP');

    $logger->info("Sending data to server");
    foreach ($parts as $part) {
        if (!socket_sendto($sock, $part, strlen($part), 0, $server, $port)) {

            $logger->error("Could not send data");
            die();
        }
        usleep(1000);
    }
    $logger->info("Sent successfully");

    $logger->info("Closing socket");
    socket_close($sock);
    $logger->info("Socket closed");
}

$server = new SoapServer(dirname(__DIR__, 3) . "/config/service.wsdl");
$server->addFunction("sendImageHexString");
$server->handle();