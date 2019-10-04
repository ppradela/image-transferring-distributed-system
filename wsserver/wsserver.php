<?php
function sendData($data){    
}

$server=new SoapServer("soap.wsdl");
$server->addFunction("sendData");
$server->handle();
?>