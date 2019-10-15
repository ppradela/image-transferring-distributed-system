<?php
require_once dirname(__DIR__, 2) . '/log4php/Logger.php';
Logger::configure(dirname(__DIR__, 2) . '/config.xml');

function sendImageHexString($imageHexString)
{
    $logger = Logger::getLogger("");
    error_reporting(~E_WARNING);
    
    $server = '127.0.0.1';
    $port   = 9999;
    
    $logger->info("Creating socket");
    if (!($sock = socket_create(AF_INET, SOCK_DGRAM, 0))) {
        $errorcode = socket_last_error();
        $errormsg  = socket_strerror($errorcode);
        
        $logger->error("Couldn't create socket: [$errorcode] $errormsg");
        die("Couldn't create socket: [$errorcode] $errormsg \n");
    }
    
    $logger->info("Socket created");
    $parts = str_split($imageHexString, 16);
    
    $logger->info("Sending data to server");
    foreach ($parts as $part) {
        if (!socket_sendto($sock, $part, strlen($part), 0, $server, $port)) {
            $errorcode = socket_last_error();
            $errormsg  = socket_strerror($errorcode);
            
            $logger->error("Could not send data: [$errorcode] $errormsg");
            die("Could not send data: [$errorcode] $errormsg \n");
        }
    }
    $logger->info("Sent successfully");
    
    $logger->info("Closing socket");
    socket_close($sock);
    $logger->info("Socket closed");
}

$server = new SoapServer(dirname(__DIR__, 2) . "/soap.wsdl");
$server->addFunction("sendImageHexString");
$server->handle();
?>