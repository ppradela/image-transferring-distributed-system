<?php
function sendImageHexString($imageHexString){
    fwrite( fopen('php://stderr', 'w'), $imageHexString);
}

$server=new SoapServer(dirname(__DIR__, 2)."/soap.wsdl");
$server->addFunction("sendImageHexString");
$server->handle();
?>