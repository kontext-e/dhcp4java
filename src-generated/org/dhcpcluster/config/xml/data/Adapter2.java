//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.1-b02-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2006.12.30 at 09:40:34 AM CET 
//


package org.dhcpcluster.config.xml.data;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class Adapter2
    extends XmlAdapter<String, Short>
{


    public Short unmarshal(String value) {
        return (javax.xml.bind.DatatypeConverter.parseShort(value));
    }

    public String marshal(Short value) {
        if (value == null) {
            return null;
        }
        return (javax.xml.bind.DatatypeConverter.printShort(value));
    }

}
