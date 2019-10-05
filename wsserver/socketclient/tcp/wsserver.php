<?php
$ROOT=dirname(__DIR__, 2);

function sendData($data){    
}


$server=new SoapServer("$ROOT/soap.wsdl");
$server->addFunction("sendData");
$server->handle();
?>