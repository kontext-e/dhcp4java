//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.1-b02-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2007.01.09 at 10:36:36 PM CET 
//


package org.dhcpcluster.config.xml.data;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for type-option-bytes complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="type-option-bytes">
 *   &lt;complexContent>
 *     &lt;extension base="{}option-generic">
 *       &lt;choice maxOccurs="unbounded">
 *         &lt;element ref="{}value-byte"/>
 *         &lt;element ref="{}value-rawhex"/>
 *         &lt;element ref="{}value-raw64"/>
 *         &lt;element ref="{}value-string"/>
 *       &lt;/choice>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "type-option-bytes", propOrder = {
    "valueByteOrValueRawhexOrValueRaw64"
})
public class TypeOptionBytes
    extends OptionGeneric
{

    @XmlElementRefs({
        @XmlElementRef(name = "value-rawhex", type = JAXBElement.class),
        @XmlElementRef(name = "value-raw64", type = JAXBElement.class),
        @XmlElementRef(name = "value-string", type = JAXBElement.class),
        @XmlElementRef(name = "value-byte", type = JAXBElement.class)
    })
    protected List<JAXBElement<?>> valueByteOrValueRawhexOrValueRaw64;

    /**
     * Gets the value of the valueByteOrValueRawhexOrValueRaw64 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the valueByteOrValueRawhexOrValueRaw64 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getValueByteOrValueRawhexOrValueRaw64().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link byte[]}{@code >}
     * {@link JAXBElement }{@code <}{@link byte[]}{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link Byte }{@code >}
     * 
     * 
     */
    public List<JAXBElement<?>> getValueByteOrValueRawhexOrValueRaw64() {
        if (valueByteOrValueRawhexOrValueRaw64 == null) {
            valueByteOrValueRawhexOrValueRaw64 = new ArrayList<JAXBElement<?>>();
        }
        return this.valueByteOrValueRawhexOrValueRaw64;
    }

}
