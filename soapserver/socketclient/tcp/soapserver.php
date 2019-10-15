<?php
require_once dirname(__DIR__, 2) . '/log4php/Logger.php';
Logger::configure(dirname(__DIR__, 2) . '/config.xml');

function sendImageHexString($imageHexString)
{
    $logger = Logger::getLogger("");
    
    $host = "127.0.0.1";
    $port = 25003;
    
    $logger->info("Creating socket");
    $socket = socket_create(AF_INET, SOCK_STREAM, 0) or die("Could not create socket\n") and $logger->error("Could not create socket");
    $logger->info("Socket created");
    
    $logger->info("Connecting to server");
    $result = socket_connect($socket, $host, $port) or die("Could not connect to server\n") and $logger->error("Could not connect to server");
    
    $logger->info("Sending data to server");
    $parts = str_split($imageHexString, 16);
    foreach ($parts as $part) {
        socket_write($socket, $part, strlen($part)) or die("Could not send data to server\n") and $logger->error("Could not send data to server");
    }
    $logger->info("Sent successfully");
    
    $logger->info("Closing socket");
    socket_close($socket);
    $logger->info("Socket closed");
}

$server = new SoapServer(dirname(__DIR__, 2) . "/soap.wsdl");
$server->addFunction("sendImageHexString");
$server->handle();
?>