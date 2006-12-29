//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.1-b02-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2006.12.29 at 07:37:11 AM CET 
//


package org.dhcpcluster.config.xml.data;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for option-generic complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="option-generic">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element ref="{}mirror"/>
 *         &lt;choice maxOccurs="unbounded" minOccurs="0">
 *           &lt;element ref="{}value-byte"/>
 *           &lt;element ref="{}value-short"/>
 *           &lt;element ref="{}value-int"/>
 *           &lt;element ref="{}value-inet"/>
 *           &lt;element ref="{}value-rawhex"/>
 *           &lt;element ref="{}value-raw64"/>
 *         &lt;/choice>
 *       &lt;/choice>
 *       &lt;attribute name="mode" type="{}stype-option-mode" default="replace" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "option-generic", propOrder = {
    "mirror",
    "valueByteOrValueShortOrValueInt"
})
@XmlSeeAlso({
    TypeOptionInets.class,
    TypeOptionInet.class,
    Option.class
})
public class OptionGeneric {

    protected EmptyType mirror;
    @XmlElementRefs({
        @XmlElementRef(name = "value-rawhex", type = JAXBElement.class),
        @XmlElementRef(name = "value-byte", type = JAXBElement.class),
        @XmlElementRef(name = "value-int", type = JAXBElement.class),
        @XmlElementRef(name = "value-short", type = JAXBElement.class),
        @XmlElementRef(name = "value-raw64", type = JAXBElement.class),
        @XmlElementRef(name = "value-inet", type = JAXBElement.class)
    })
    protected List<JAXBElement<?>> valueByteOrValueShortOrValueInt;
    @XmlAttribute
    protected OptionMode mode;

    /**
     * Gets the value of the mirror property.
     * 
     * @return
     *     possible object is
     *     {@link EmptyType }
     *     
     */
    public EmptyType getMirror() {
        return mirror;
    }

    /**
     * Sets the value of the mirror property.
     * 
     * @param value
     *     allowed object is
     *     {@link EmptyType }
     *     
     */
    public void setMirror(EmptyType value) {
        this.mirror = value;
    }

    /**
     * Gets the value of the valueByteOrValueShortOrValueInt property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the valueByteOrValueShortOrValueInt property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getValueByteOrValueShortOrValueInt().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link byte[]}{@code >}
     * {@link JAXBElement }{@code <}{@link byte[]}{@code >}
     * {@link JAXBElement }{@code <}{@link Byte }{@code >}
     * {@link JAXBElement }{@code <}{@link Integer }{@code >}
     * {@link JAXBElement }{@code <}{@link InetAddress }{@code >}
     * {@link JAXBElement }{@code <}{@link Short }{@code >}
     * 
     * 
     */
    public List<JAXBElement<?>> getValueByteOrValueShortOrValueInt() {
        if (valueByteOrValueShortOrValueInt == null) {
            valueByteOrValueShortOrValueInt = new ArrayList<JAXBElement<?>>();
        }
        return this.valueByteOrValueShortOrValueInt;
    }

    /**
     * Gets the value of the mode property.
     * 
     * @return
     *     possible object is
     *     {@link OptionMode }
     *     
     */
    public OptionMode getMode() {
        if (mode == null) {
            return OptionMode.REPLACE;
        } else {
            return mode;
        }
    }

    /**
     * Sets the value of the mode property.
     * 
     * @param value
     *     allowed object is
     *     {@link OptionMode }
     *     
     */
    public void setMode(OptionMode value) {
        this.mode = value;
    }

}
