//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.1-b02-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2006.12.31 at 06:28:58 AM CET 
//


package org.dhcpcluster.config.xml.data;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice maxOccurs="unbounded" minOccurs="0">
 *         &lt;element ref="{}option"/>
 *         &lt;group ref="{}group-option-inets"/>
 *         &lt;group ref="{}group-option-inet"/>
 *         &lt;group ref="{}group-option-bytes"/>
 *         &lt;group ref="{}group-option-byte"/>
 *         &lt;group ref="{}group-option-int"/>
 *         &lt;group ref="{}group-option-short"/>
 *         &lt;group ref="{}group-option-shorts"/>
 *         &lt;group ref="{}group-option-string"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "optionOrOptionTimeServersOrOptionRouters"
})
@XmlRootElement(name = "options")
public class Options {

    @XmlElementRefs({
        @XmlElementRef(name = "option-dhcp-parameter-request-list", type = JAXBElement.class),
        @XmlElementRef(name = "option-dhcp-message", type = JAXBElement.class),
        @XmlElementRef(name = "option-time-offset", type = JAXBElement.class),
        @XmlElementRef(name = "option-ip-forwarding", type = JAXBElement.class),
        @XmlElementRef(name = "option-routers", type = JAXBElement.class),
        @XmlElementRef(name = "option-time-servers", type = JAXBElement.class),
        @XmlElementRef(name = "option", type = Option.class),
        @XmlElementRef(name = "option-boot-size", type = JAXBElement.class),
        @XmlElementRef(name = "option-path-mtu-plateau-table", type = JAXBElement.class),
        @XmlElementRef(name = "option-host-name", type = JAXBElement.class),
        @XmlElementRef(name = "option-domain-name", type = JAXBElement.class),
        @XmlElementRef(name = "option-subnet-mask", type = JAXBElement.class)
    })
    protected List<Object> optionOrOptionTimeServersOrOptionRouters;

    /**
     * Gets the value of the optionOrOptionTimeServersOrOptionRouters property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the optionOrOptionTimeServersOrOptionRouters property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOptionOrOptionTimeServersOrOptionRouters().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link TypeOptionBytes }{@code >}
     * {@link JAXBElement }{@code <}{@link TypeOptionInt }{@code >}
     * {@link JAXBElement }{@code <}{@link TypeOptionString }{@code >}
     * {@link JAXBElement }{@code <}{@link TypeOptionInets }{@code >}
     * {@link JAXBElement }{@code <}{@link TypeOptionInets }{@code >}
     * {@link JAXBElement }{@code <}{@link TypeOptionByte }{@code >}
     * {@link Option }
     * {@link JAXBElement }{@code <}{@link TypeOptionShort }{@code >}
     * {@link JAXBElement }{@code <}{@link TypeOptionString }{@code >}
     * {@link JAXBElement }{@code <}{@link TypeOptionShorts }{@code >}
     * {@link JAXBElement }{@code <}{@link TypeOptionInet }{@code >}
     * {@link JAXBElement }{@code <}{@link TypeOptionString }{@code >}
     * 
     * 
     */
    public List<Object> getOptionOrOptionTimeServersOrOptionRouters() {
        if (optionOrOptionTimeServersOrOptionRouters == null) {
            optionOrOptionTimeServersOrOptionRouters = new ArrayList<Object>();
        }
        return this.optionOrOptionTimeServersOrOptionRouters;
    }

}
