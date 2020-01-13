package imagetransferring.soapclient;

import javax.xml.namespace.QName;
import javax.xml.ws.*;
import java.net.MalformedURLException;
import java.net.URL;

@WebServiceClient(name = "SoapService", targetNamespace = "http://localhost/SoapService", wsdlLocation = "http://localhost:8080/soapserver/SoapService?wsdl")
class SoapService extends Service {

    private final static URL SOAPSERVICE_WSDL_LOCATION;
    private final static WebServiceException SOAPSERVICE_EXCEPTION;
    private final static QName SOAPSERVICE_QNAME = new QName("http://localhost/SoapService", "SoapService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://localhost:8080/soapserver/SoapService?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        SOAPSERVICE_WSDL_LOCATION = url;
        SOAPSERVICE_EXCEPTION = e;
    }

    SoapService() {
        super(__getWsdlLocation(), SOAPSERVICE_QNAME);
    }

    public SoapService(WebServiceFeature... features) {
        super(__getWsdlLocation(), SOAPSERVICE_QNAME, features);
    }

    public SoapService(URL wsdlLocation) {
        super(wsdlLocation, SOAPSERVICE_QNAME);
    }

    public SoapService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, SOAPSERVICE_QNAME, features);
    }

    public SoapService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public SoapService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    private static URL __getWsdlLocation() {
        if (SOAPSERVICE_EXCEPTION != null) {
            throw SOAPSERVICE_EXCEPTION;
        }
        return SOAPSERVICE_WSDL_LOCATION;
    }

    @WebEndpoint(name = "SoapPort")
    Soap getSoapPort() {
        return super.getPort(new QName("http://localhost/SoapService", "SoapPort"), Soap.class);
    }
}