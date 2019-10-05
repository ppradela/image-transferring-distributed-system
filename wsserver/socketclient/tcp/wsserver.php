<?php
function sendData($data){
    $input = pack("C*", $data);
    $host    = "127.0.0.1";
    $port    = 25005;
    $message = "Hello Server";
    echo "Message To server :".$message;
    // create socket
    $socket = socket_create(AF_INET, SOCK_STREAM, 0) or die("Could not create socket\n");
    // connect to server
    $result = socket_connect($socket, $host, $port) or die("Could not connect to server\n");  
    // send string to server
    socket_write($socket, $input, 16) or die("Could not send data to server\n");
    // close socket
    socket_close($socket);
}


$server=new SoapServer(dirname(__DIR__, 2)."/soap.wsdl");
$server->addFunction("sendData");
$server->handle();
?>