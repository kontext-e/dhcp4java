//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.1-b02-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2007.01.09 at 09:55:34 PM CET 
//


package org.dhcpcluster.config.xml.data;

import java.net.InetAddress;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for type-option-inet complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="type-option-inet">
 *   &lt;complexContent>
 *     &lt;extension base="{}option-generic">
 *       &lt;sequence>
 *         &lt;element ref="{}value-inet"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "type-option-inet", propOrder = {
    "valueInet"
})
public class TypeOptionInet
    extends OptionGeneric
{

    @XmlElement(name = "value-inet", required = true, type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    protected InetAddress valueInet;

    /**
     * Gets the value of the valueInet property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public InetAddress getValueInet() {
        return valueInet;
    }

    /**
     * Sets the value of the valueInet property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValueInet(InetAddress value) {
        this.valueInet = value;
    }

}
