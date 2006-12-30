//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.1-b02-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2006.12.30 at 10:32:07 AM CET 
//


package org.dhcpcluster.config.xml.data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for type-subnet complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="type-subnet">
 *   &lt;complexContent>
 *     &lt;extension base="{}type-node-subnet">
 *       &lt;all>
 *         &lt;element ref="{}filter" minOccurs="0"/>
 *         &lt;element ref="{}lease" minOccurs="0"/>
 *         &lt;element ref="{}options" minOccurs="0"/>
 *         &lt;element ref="{}giaddrs" minOccurs="0"/>
 *         &lt;element ref="{}pools" minOccurs="0"/>
 *       &lt;/all>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "type-subnet", propOrder = {
    "filter",
    "lease",
    "options",
    "giaddrs",
    "pools"
})
@XmlSeeAlso({
    Subnet.class
})
public class TypeSubnet
    extends TypeNodeSubnet
{

    protected Filter filter;
    protected Lease lease;
    protected Options options;
    protected Giaddrs giaddrs;
    protected Pools pools;

    /**
     * Gets the value of the filter property.
     * 
     * @return
     *     possible object is
     *     {@link Filter }
     *     
     */
    public Filter getFilter() {
        return filter;
    }

    /**
     * Sets the value of the filter property.
     * 
     * @param value
     *     allowed object is
     *     {@link Filter }
     *     
     */
    public void setFilter(Filter value) {
        this.filter = value;
    }

    /**
     * Gets the value of the lease property.
     * 
     * @return
     *     possible object is
     *     {@link Lease }
     *     
     */
    public Lease getLease() {
        return lease;
    }

    /**
     * Sets the value of the lease property.
     * 
     * @param value
     *     allowed object is
     *     {@link Lease }
     *     
     */
    public void setLease(Lease value) {
        this.lease = value;
    }

    /**
     * Gets the value of the options property.
     * 
     * @return
     *     possible object is
     *     {@link Options }
     *     
     */
    public Options getOptions() {
        return options;
    }

    /**
     * Sets the value of the options property.
     * 
     * @param value
     *     allowed object is
     *     {@link Options }
     *     
     */
    public void setOptions(Options value) {
        this.options = value;
    }

    /**
     * Gets the value of the giaddrs property.
     * 
     * @return
     *     possible object is
     *     {@link Giaddrs }
     *     
     */
    public Giaddrs getGiaddrs() {
        return giaddrs;
    }

    /**
     * Sets the value of the giaddrs property.
     * 
     * @param value
     *     allowed object is
     *     {@link Giaddrs }
     *     
     */
    public void setGiaddrs(Giaddrs value) {
        this.giaddrs = value;
    }

    /**
     * Gets the value of the pools property.
     * 
     * @return
     *     possible object is
     *     {@link Pools }
     *     
     */
    public Pools getPools() {
        return pools;
    }

    /**
     * Sets the value of the pools property.
     * 
     * @param value
     *     allowed object is
     *     {@link Pools }
     *     
     */
    public void setPools(Pools value) {
        this.pools = value;
    }

}
