<?php

include dirname(__DIR__, 3) . '/vendor/autoload.php';
Logger::configure(dirname(__DIR__, 3) . '/config/log4php_tcp.xml');

function sendImageHexString($imageHexString)
{
    $logger = Logger::getLogger("");

    $host = "127.0.0.1";
    $port = 25003;

    $logger->info("Creating socket");
    $socket = socket_create(AF_INET, SOCK_STREAM, 0) or ($logger->error("Could not create socket") && die());
    $logger->info("Socket created");

    $logger->info("Connecting to server");
    $result = socket_connect($socket, $host, $port) or ($logger->error("Could not connect to server") && die());

    $logger->info("Sending data to server");
    $parts = str_split($imageHexString, 16);
    foreach ($parts as $part) {
        socket_write($socket, $part, strlen($part)) or ($logger->error("Could not send data to server") && die());
    }
    $logger->info("Sent successfully");

    $logger->info("Closing socket");
    socket_close($socket);
    $logger->info("Socket closed");
}

$server = new SoapServer(dirname(__DIR__, 3) . "/config/service.wsdl");
$server->addFunction("sendImageHexString");
$server->handle();
