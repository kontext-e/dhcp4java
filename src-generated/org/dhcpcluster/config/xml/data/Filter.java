//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.1-b02-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2007.01.27 at 02:16:30 PM CET 
//


package org.dhcpcluster.config.xml.data;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
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
    "filterGroup"
})
@XmlRootElement(name = "filter")
public class Filter {

    @XmlElements({
        @XmlElement(name = "filter-always-true", type = Filter.FilterAlwaysTrue.class),
        @XmlElement(name = "filter-always-false", type = Filter.FilterAlwaysFalse.class),
        @XmlElement(name = "filter-string-option", type = Filter.FilterStringOption.class),
        @XmlElement(name = "filter-and", type = Filter.FilterAnd.class),
        @XmlElement(name = "filter-or", type = Filter.FilterOr.class),
        @XmlElement(name = "filter-num-option", type = Filter.FilterNumOption.class),
        @XmlElement(name = "filter-not", type = Filter.FilterNot.class)
    })
    protected TypeFilterRoot filterGroup;

    /**
     * Gets the value of the filterGroup property.
     * 
     * @return
     *     possible object is
     *     {@link Filter.FilterAlwaysTrue }
     *     {@link Filter.FilterAlwaysFalse }
     *     {@link Filter.FilterStringOption }
     *     {@link Filter.FilterAnd }
     *     {@link Filter.FilterOr }
     *     {@link Filter.FilterNumOption }
     *     {@link Filter.FilterNot }
     *     
     */
    public TypeFilterRoot getFilterGroup() {
        return filterGroup;
    }

    /**
     * Sets the value of the filterGroup property.
     * 
     * @param value
     *     allowed object is
     *     {@link Filter.FilterAlwaysTrue }
     *     {@link Filter.FilterAlwaysFalse }
     *     {@link Filter.FilterStringOption }
     *     {@link Filter.FilterAnd }
     *     {@link Filter.FilterOr }
     *     {@link Filter.FilterNumOption }
     *     {@link Filter.FilterNot }
     *     
     */
    public void setFilterGroup(TypeFilterRoot value) {
        this.filterGroup = value;
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
     *     &lt;/extension>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class FilterAlwaysFalse
        extends TypeFilterRoot
    {


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
     *     &lt;/extension>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class FilterAlwaysTrue
        extends TypeFilterRoot
    {


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

        @XmlElements({
            @XmlElement(name = "filter-and", type = Filter.FilterAnd.class),
            @XmlElement(name = "filter-always-false", type = Filter.FilterAlwaysFalse.class),
            @XmlElement(name = "filter-not", type = Filter.FilterNot.class),
            @XmlElement(name = "filter-num-option", type = Filter.FilterNumOption.class),
            @XmlElement(name = "filter-or", type = Filter.FilterOr.class),
            @XmlElement(name = "filter-always-true", type = Filter.FilterAlwaysTrue.class),
            @XmlElement(name = "filter-string-option", type = Filter.FilterStringOption.class)
        })
        protected List<TypeFilterRoot> filterGroup;

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
         * {@link Filter.FilterAnd }
         * {@link Filter.FilterAlwaysFalse }
         * {@link Filter.FilterNot }
         * {@link Filter.FilterNumOption }
         * {@link Filter.FilterOr }
         * {@link Filter.FilterAlwaysTrue }
         * {@link Filter.FilterStringOption }
         * 
         * 
         */
        public List<TypeFilterRoot> getFilterGroup() {
            if (filterGroup == null) {
                filterGroup = new ArrayList<TypeFilterRoot>();
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
        "filterGroup"
    })
    public static class FilterNot
        extends TypeFilterRoot
    {

        @XmlElements({
            @XmlElement(name = "filter-num-option", type = Filter.FilterNumOption.class),
            @XmlElement(name = "filter-always-true", type = Filter.FilterAlwaysTrue.class),
            @XmlElement(name = "filter-not", type = Filter.FilterNot.class),
            @XmlElement(name = "filter-and", type = Filter.FilterAnd.class),
            @XmlElement(name = "filter-string-option", type = Filter.FilterStringOption.class),
            @XmlElement(name = "filter-always-false", type = Filter.FilterAlwaysFalse.class),
            @XmlElement(name = "filter-or", type = Filter.FilterOr.class)
        })
        protected TypeFilterRoot filterGroup;

        /**
         * Gets the value of the filterGroup property.
         * 
         * @return
         *     possible object is
         *     {@link Filter.FilterNumOption }
         *     {@link Filter.FilterAlwaysTrue }
         *     {@link Filter.FilterNot }
         *     {@link Filter.FilterAnd }
         *     {@link Filter.FilterStringOption }
         *     {@link Filter.FilterAlwaysFalse }
         *     {@link Filter.FilterOr }
         *     
         */
        public TypeFilterRoot getFilterGroup() {
            return filterGroup;
        }

        /**
         * Sets the value of the filterGroup property.
         * 
         * @param value
         *     allowed object is
         *     {@link Filter.FilterNumOption }
         *     {@link Filter.FilterAlwaysTrue }
         *     {@link Filter.FilterNot }
         *     {@link Filter.FilterAnd }
         *     {@link Filter.FilterStringOption }
         *     {@link Filter.FilterAlwaysFalse }
         *     {@link Filter.FilterOr }
         *     
         */
        public void setFilterGroup(TypeFilterRoot value) {
            this.filterGroup = value;
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
     *       &lt;attribute name="op" use="required" type="{}stype-filter-num" />
     *       &lt;attribute name="code" use="required" type="{}wByte" />
     *       &lt;attribute name="value-int" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
     *     &lt;/extension>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class FilterNumOption
        extends TypeFilterRoot
    {

        @XmlAttribute(required = true)
        protected StypeFilterNum op;
        @XmlAttribute(required = true)
        @XmlJavaTypeAdapter(Adapter2 .class)
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

        @XmlElements({
            @XmlElement(name = "filter-always-false", type = Filter.FilterAlwaysFalse.class),
            @XmlElement(name = "filter-num-option", type = Filter.FilterNumOption.class),
            @XmlElement(name = "filter-or", type = Filter.FilterOr.class),
            @XmlElement(name = "filter-string-option", type = Filter.FilterStringOption.class),
            @XmlElement(name = "filter-not", type = Filter.FilterNot.class),
            @XmlElement(name = "filter-always-true", type = Filter.FilterAlwaysTrue.class),
            @XmlElement(name = "filter-and", type = Filter.FilterAnd.class)
        })
        protected List<TypeFilterRoot> filterGroup;

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
         * {@link Filter.FilterAlwaysFalse }
         * {@link Filter.FilterNumOption }
         * {@link Filter.FilterOr }
         * {@link Filter.FilterStringOption }
         * {@link Filter.FilterNot }
         * {@link Filter.FilterAlwaysTrue }
         * {@link Filter.FilterAnd }
         * 
         * 
         */
        public List<TypeFilterRoot> getFilterGroup() {
            if (filterGroup == null) {
                filterGroup = new ArrayList<TypeFilterRoot>();
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
     *       &lt;attribute name="mode" type="{}stype-filter-string" default="exact" />
     *       &lt;attribute name="code" use="required" type="{}wByte" />
     *       &lt;attribute name="value-string" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
     *     &lt;/extension>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class FilterStringOption
        extends TypeFilterRoot
    {

        @XmlAttribute
        protected StypeFilterString mode;
        @XmlAttribute(required = true)
        @XmlJavaTypeAdapter(Adapter2 .class)
        protected Byte code;
        @XmlAttribute(name = "value-string", required = true)
        protected String valueString;

        /**
         * Gets the value of the mode property.
         * 
         * @return
         *     possible object is
         *     {@link StypeFilterString }
         *     
         */
        public StypeFilterString getMode() {
            if (mode == null) {
                return StypeFilterString.EXACT;
            } else {
                return mode;
            }
        }

        /**
         * Sets the value of the mode property.
         * 
         * @param value
         *     allowed object is
         *     {@link StypeFilterString }
         *     
         */
        public void setMode(StypeFilterString value) {
            this.mode = value;
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
         * Gets the value of the valueString property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getValueString() {
            return valueString;
        }

        /**
         * Sets the value of the valueString property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setValueString(String value) {
            this.valueString = value;
        }

    }

}
