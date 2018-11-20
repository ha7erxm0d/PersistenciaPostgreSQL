package com.company;

import java.io.IOException;
import java.util.List;
import org.xml.sax.*;

import javax.xml.parsers.ParserConfigurationException;

public class Reader extends DomReader{



    public Reader(String xml) throws ParserConfigurationException, SAXException, IOException {
        super(xml);
    }

    public List<String> nombreCountry() {
        return(super.extractList("//country/name[1]/text()"));

    }
    public List<String> nombreProvincias(String Country) {
        return(super.extractList("//country[name[1] ='"+Country+"']//province/name[1]/text()"));

    }
    public List<String> nombreCiudades(String Provincia) {
        return(super.extractList("//country[name[1] ='"+Provincia+"']//province/name[1]/text()"));

    }








}