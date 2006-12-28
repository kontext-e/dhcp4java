//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.1-b02-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2006.12.28 at 06:14:30 AM CET 
//


package org.dhcpcluster.config.xml.data;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;group ref="{}filter-group"/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "filterAnd",
    "filterOr",
    "filterNot",
    "filterAlwaysTrue",
    "filterAlwaysFalse",
    "filterNumOption"
})
@XmlRootElement(name = "filter")
public class Filter {

    @XmlElement(name = "filter-and")
    protected Filter.FilterAnd filterAnd;
    @XmlElement(name = "filter-or")
    protected Filter.FilterOr filterOr;
    @XmlElement(name = "filter-not")
    protected Filter.FilterNot filterNot;
    @XmlElement(name = "filter-always-true")
    protected TypeFilterRoot filterAlwaysTrue;
    @XmlElement(name = "filter-always-false")
    protected TypeFilterRoot filterAlwaysFalse;
    @XmlElement(name = "filter-num-option")
    protected Filter.FilterNumOption filterNumOption;

    /**
     * Gets the value of the filterAnd property.
     * 
     * @return
     *     possible object is
     *     {@link Filter.FilterAnd }
     *     
     */
    public Filter.FilterAnd getFilterAnd() {
        return filterAnd;
    }

    /**
     * Sets the value of the filterAnd property.
     * 
     * @param value
     *     allowed object is
     *     {@link Filter.FilterAnd }
     *     
     */
    public void setFilterAnd(Filter.FilterAnd value) {
        this.filterAnd = value;
    }

    /**
     * Gets the value of the filterOr property.
     * 
     * @return
     *     possible object is
     *     {@link Filter.FilterOr }
     *     
     */
    public Filter.FilterOr getFilterOr() {
        return filterOr;
    }

    /**
     * Sets the value of the filterOr property.
     * 
     * @param value
     *     allowed object is
     *     {@link Filter.FilterOr }
     *     
     */
    public void setFilterOr(Filter.FilterOr value) {
        this.filterOr = value;
    }

    /**
     * Gets the value of the filterNot property.
     * 
     * @return
     *     possible object is
     *     {@link Filter.FilterNot }
     *     
     */
    public Filter.FilterNot getFilterNot() {
        return filterNot;
    }

    /**
     * Sets the value of the filterNot property.
     * 
     * @param value
     *     allowed object is
     *     {@link Filter.FilterNot }
     *     
     */
    public void setFilterNot(Filter.FilterNot value) {
        this.filterNot = value;
    }

    /**
     * Gets the value of the filterAlwaysTrue property.
     * 
     * @return
     *     possible object is
     *     {@link TypeFilterRoot }
     *     
     */
    public TypeFilterRoot getFilterAlwaysTrue() {
        return filterAlwaysTrue;
    }

    /**
     * Sets the value of the filterAlwaysTrue property.
     * 
     * @param value
     *     allowed object is
     *     {@link TypeFilterRoot }
     *     
     */
    public void setFilterAlwaysTrue(TypeFilterRoot value) {
        this.filterAlwaysTrue = value;
    }

    /**
     * Gets the value of the filterAlwaysFalse property.
     * 
     * @return
     *     possible object is
     *     {@link TypeFilterRoot }
     *     
     */
    public TypeFilterRoot getFilterAlwaysFalse() {
        return filterAlwaysFalse;
    }

    /**
     * Sets the value of the filterAlwaysFalse property.
     * 
     * @param value
     *     allowed object is
     *     {@link TypeFilterRoot }
     *     
     */
    public void setFilterAlwaysFalse(TypeFilterRoot value) {
        this.filterAlwaysFalse = value;
    }

    /**
     * Gets the value of the filterNumOption property.
     * 
     * @return
     *     possible object is
     *     {@link Filter.FilterNumOption }
     *     
     */
    public Filter.FilterNumOption getFilterNumOption() {
        return filterNumOption;
    }

    /**
     * Sets the value of the filterNumOption property.
     * 
     * @param value
     *     allowed object is
     *     {@link Filter.FilterNumOption }
     *     
     */
    public void setFilterNumOption(Filter.FilterNumOption value) {
        this.filterNumOption = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;extension base="{}type-filter-root">
     *       &lt;group ref="{}filter-group" maxOccurs="unbounded" minOccurs="0"/>
     *     &lt;/extension>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "filterGroup"
    })
    public static class FilterAnd
        extends TypeFilterRoot
    {

        @XmlElementRefs({
            @XmlElementRef(name = "filter-not", type = JAXBElement.class),
            @XmlElementRef(name = "filter-always-true", type = JAXBElement.class),
            @XmlElementRef(name = "filter-always-false", type = JAXBElement.class),
            @XmlElementRef(name = "filter-num-option", type = JAXBElement.class),
            @XmlElementRef(name = "filter-and", type = JAXBElement.class),
            @XmlElementRef(name = "filter-or", type = JAXBElement.class)
        })
        protected List<JAXBElement<?>> filterGroup;

        /**
         * Gets the value of the filterGroup property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the filterGroup property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getFilterGroup().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link JAXBElement }{@code <}{@link Filter.FilterNot }{@code >}
         * {@link JAXBElement }{@code <}{@link TypeFilterRoot }{@code >}
         * {@link JAXBElement }{@code <}{@link TypeFilterRoot }{@code >}
         * {@link JAXBElement }{@code <}{@link Filter.FilterOr }{@code >}
         * {@link JAXBElement }{@code <}{@link Filter.FilterNumOption }{@code >}
         * {@link JAXBElement }{@code <}{@link Filter.FilterAnd }{@code >}
         * 
         * 
         */
        public List<JAXBElement<?>> getFilterGroup() {
            if (filterGroup == null) {
                filterGroup = new ArrayList<JAXBElement<?>>();
            }
            return this.filterGroup;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;extension base="{}type-filter-root">
     *       &lt;group ref="{}filter-group"/>
     *     &lt;/extension>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "filterAnd",
        "filterOr",
        "filterNot",
        "filterAlwaysTrue",
        "filterAlwaysFalse",
        "filterNumOption"
    })
    public static class FilterNot
        extends TypeFilterRoot
    {

        @XmlElement(name = "filter-and")
        protected Filter.FilterAnd filterAnd;
        @XmlElement(name = "filter-or")
        protected Filter.FilterOr filterOr;
        @XmlElement(name = "filter-not")
        protected Filter.FilterNot filterNot;
        @XmlElement(name = "filter-always-true")
        protected TypeFilterRoot filterAlwaysTrue;
        @XmlElement(name = "filter-always-false")
        protected TypeFilterRoot filterAlwaysFalse;
        @XmlElement(name = "filter-num-option")
        protected Filter.FilterNumOption filterNumOption;

        /**
         * Gets the value of the filterAnd property.
         * 
         * @return
         *     possible object is
         *     {@link Filter.FilterAnd }
         *     
         */
        public Filter.FilterAnd getFilterAnd() {
            return filterAnd;
        }

        /**
         * Sets the value of the filterAnd property.
         * 
         * @param value
         *     allowed object is
         *     {@link Filter.FilterAnd }
         *     
         */
        public void setFilterAnd(Filter.FilterAnd value) {
            this.filterAnd = value;
        }

        /**
         * Gets the value of the filterOr property.
         * 
         * @return
         *     possible object is
         *     {@link Filter.FilterOr }
         *     
         */
        public Filter.FilterOr getFilterOr() {
            return filterOr;
        }

        /**
         * Sets the value of the filterOr property.
         * 
         * @param value
         *     allowed object is
         *     {@link Filter.FilterOr }
         *     
         */
        public void setFilterOr(Filter.FilterOr value) {
            this.filterOr = value;
        }

        /**
         * Gets the value of the filterNot property.
         * 
         * @return
         *     possible object is
         *     {@link Filter.FilterNot }
         *     
         */
        public Filter.FilterNot getFilterNot() {
            return filterNot;
        }

        /**
         * Sets the value of the filterNot property.
         * 
         * @param value
         *     allowed object is
         *     {@link Filter.FilterNot }
         *     
         */
        public void setFilterNot(Filter.FilterNot value) {
            this.filterNot = value;
        }

        /**
         * Gets the value of the filterAlwaysTrue property.
         * 
         * @return
         *     possible object is
         *     {@link TypeFilterRoot }
         *     
         */
        public TypeFilterRoot getFilterAlwaysTrue() {
            return filterAlwaysTrue;
        }

        /**
         * Sets the value of the filterAlwaysTrue property.
         * 
         * @param value
         *     allowed object is
         *     {@link TypeFilterRoot }
         *     
         */
        public void setFilterAlwaysTrue(TypeFilterRoot value) {
            this.filterAlwaysTrue = value;
        }

        /**
         * Gets the value of the filterAlwaysFalse property.
         * 
         * @return
         *     possible object is
         *     {@link TypeFilterRoot }
         *     
         */
        public TypeFilterRoot getFilterAlwaysFalse() {
            return filterAlwaysFalse;
        }

        /**
         * Sets the value of the filterAlwaysFalse property.
         * 
         * @param value
         *     allowed object is
         *     {@link TypeFilterRoot }
         *     
         */
        public void setFilterAlwaysFalse(TypeFilterRoot value) {
            this.filterAlwaysFalse = value;
        }

        /**
         * Gets the value of the filterNumOption property.
         * 
         * @return
         *     possible object is
         *     {@link Filter.FilterNumOption }
         *     
         */
        public Filter.FilterNumOption getFilterNumOption() {
            return filterNumOption;
        }

        /**
         * Sets the value of the filterNumOption property.
         * 
         * @param value
         *     allowed object is
         *     {@link Filter.FilterNumOption }
         *     
         */
        public void setFilterNumOption(Filter.FilterNumOption value) {
            this.filterNumOption = value;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;attribute name="op" use="required" type="{}stype-filter-num" />
     *       &lt;attribute name="code" use="required" type="{}wByte" />
     *       &lt;attribute name="value-int" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class FilterNumOption {

        @XmlAttribute(required = true)
        protected StypeFilterNum op;
        @XmlAttribute(required = true)
        @XmlJavaTypeAdapter(Adapter1 .class)
        protected Byte code;
        @XmlAttribute(name = "value-int", required = true)
        protected int valueInt;

        /**
         * Gets the value of the op property.
         * 
         * @return
         *     possible object is
         *     {@link StypeFilterNum }
         *     
         */
        public StypeFilterNum getOp() {
            return op;
        }

        /**
         * Sets the value of the op property.
         * 
         * @param value
         *     allowed object is
         *     {@link StypeFilterNum }
         *     
         */
        public void setOp(StypeFilterNum value) {
            this.op = value;
        }

        /**
         * Gets the value of the code property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public Byte getCode() {
            return code;
        }

        /**
         * Sets the value of the code property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCode(Byte value) {
            this.code = value;
        }

        /**
         * Gets the value of the valueInt property.
         * 
         */
        public int getValueInt() {
            return valueInt;
        }

        /**
         * Sets the value of the valueInt property.
         * 
         */
        public void setValueInt(int value) {
            this.valueInt = value;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;extension base="{}type-filter-root">
     *       &lt;group ref="{}filter-group" maxOccurs="unbounded" minOccurs="0"/>
     *     &lt;/extension>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "filterGroup"
    })
    public static class FilterOr
        extends TypeFilterRoot
    {

        @XmlElementRefs({
            @XmlElementRef(name = "filter-not", type = JAXBElement.class),
            @XmlElementRef(name = "filter-always-true", type = JAXBElement.class),
            @XmlElementRef(name = "filter-num-option", type = JAXBElement.class),
            @XmlElementRef(name = "filter-and", type = JAXBElement.class),
            @XmlElementRef(name = "filter-or", type = JAXBElement.class),
            @XmlElementRef(name = "filter-always-false", type = JAXBElement.class)
        })
        protected List<JAXBElement<?>> filterGroup;

        /**
         * Gets the value of the filterGroup property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the filterGroup property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getFilterGroup().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link JAXBElement }{@code <}{@link Filter.FilterNumOption }{@code >}
         * {@link JAXBElement }{@code <}{@link Filter.FilterNot }{@code >}
         * {@link JAXBElement }{@code <}{@link Filter.FilterOr }{@code >}
         * {@link JAXBElement }{@code <}{@link Filter.FilterAnd }{@code >}
         * {@link JAXBElement }{@code <}{@link TypeFilterRoot }{@code >}
         * {@link JAXBElement }{@code <}{@link TypeFilterRoot }{@code >}
         * 
         * 
         */
        public List<JAXBElement<?>> getFilterGroup() {
            if (filterGroup == null) {
                filterGroup = new ArrayList<JAXBElement<?>>();
            }
            return this.filterGroup;
        }

    }

}
