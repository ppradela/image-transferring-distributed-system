<?php
function sendImageHexString($imageHexString){

error_reporting(~E_WARNING);

$server = '127.0.0.1';
$port = 9999;

if(!($sock = socket_create(AF_INET, SOCK_DGRAM, 0)))
{
	$errorcode = socket_last_error();
    $errormsg = socket_strerror($errorcode);
    
    die("Couldn't create socket: [$errorcode] $errormsg \n");
}

echo "Socket created \n";

$parts=str_split($imageHexString,16);

foreach ($parts as $part) {
    if( ! socket_sendto($sock, $part , strlen($part) , 0 , $server , $port)){
		$errorcode = socket_last_error();
		$errormsg = socket_strerror($errorcode);
		
		die("Could not send data: [$errorcode] $errormsg \n");
    }
}
    
}

$server=new SoapServer(dirname(__DIR__, 2)."/soap.wsdl");
$server->addFunction("sendImageHexString");
$server->handle();
?>