<?php
// set some variables
$host = "127.0.0.1";
$port = 25005;
// don't timeout!
set_time_limit(0);
// create socket
$socket = socket_create(AF_INET, SOCK_STREAM, 0) or die("Could not create socket\n");
// bind socket to port
$result = socket_bind($socket, $host, $port) or die("Could not bind to socket\n");
// start listening for connections
$result = socket_listen($socket, 3) or die("Could not set up socket listener\n");

// accept incoming connections
// spawn another socket to handle communication
$spawn = socket_accept($socket) or die("Could not accept incoming connection\n");
// read client input
$input = socket_read($spawn, 16) or die("Could not read input\n");
$bin=pack("C*", $input);
file_put_contents("/home/superuser/Dokumenty/image-transferring-distributed-system/obrazek.png", $bin);
// clean up input string
$input = trim($input);
echo "Client Message : ".$input;
// close sockets
socket_close($spawn);
socket_close($socket);
?>