//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.1-b02-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2006.12.29 at 10:03:17 PM CET 
//


package org.dhcpcluster.config.xml.data;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class Adapter1
    extends XmlAdapter<String, Byte>
{


    public Byte unmarshal(String value) {
        return (javax.xml.bind.DatatypeConverter.parseByte(value));
    }

    public String marshal(Byte value) {
        if (value == null) {
            return null;
        }
        return (javax.xml.bind.DatatypeConverter.printByte(value));
    }

}
