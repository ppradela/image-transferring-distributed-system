package soapclient;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.Action;

@WebService(name = "Soap", targetNamespace = "http://localhost/SoapService")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface Soap {

    @WebMethod
    @Action(input = "http://localhost/SoapService/Soap/sendImageHexStringRequest", output = "http://localhost/SoapService/Soap/sendImageHexStringResponse")
    public void sendImageHexString(
            @WebParam(name = "imageHexString", partName = "imageHexString") String imageHexString);
}