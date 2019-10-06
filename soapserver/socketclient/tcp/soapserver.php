<?php
function sendImageHexString($imageHexString)
{    
    $host    = "127.0.0.1";
    $port    = 25003;
    // create socket
    $socket = socket_create(AF_INET, SOCK_STREAM, 0) or die("Could not create socket\n");
    // connect to server
    $result = socket_connect($socket, $host, $port) or die("Could not connect to server\n");
    // send string to server
    $parts = str_split($imageHexString, 16);
    foreach ($parts as $part) {
        socket_write($socket, $part, strlen($part)) or die("Could not send data to server\n");
    }
    // close socket
    socket_close($socket);
}

$server = new SoapServer(dirname(__DIR__, 2) . "/soap.wsdl");
$server->addFunction("sendImageHexString");
$server->handle();
?>