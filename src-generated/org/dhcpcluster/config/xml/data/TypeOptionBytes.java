//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.1-b02-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2006.12.30 at 08:16:40 AM CET 
//


package org.dhcpcluster.config.xml.data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for type-option-bytes complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="type-option-bytes">
 *   &lt;complexContent>
 *     &lt;restriction base="{}option-generic">
 *       &lt;choice maxOccurs="unbounded">
 *         &lt;element ref="{}value-byte"/>
 *         &lt;element ref="{}value-rawhex"/>
 *         &lt;element ref="{}value-raw64"/>
 *         &lt;element ref="{}value-string"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "type-option-bytes")
public class TypeOptionBytes
    extends OptionGeneric
{


}
