/*
 *	This file is part of dhcp4java, a DHCP API for the Java language.
 *	(c) 2006 Stephan Hadinger
 *
 *	This library is free software; you can redistribute it and/or
 *	modify it under the terms of the GNU Lesser General Public
 *	License as published by the Free Software Foundation; either
 *	version 2.1 of the License, or (at your option) any later version.
 *
 *	This library is distributed in the hope that it will be useful,
 *	but WITHOUT ANY WARRANTY; without even the implied warranty of
 *	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *	Lesser General Public License for more details.
 *
 *	You should have received a copy of the GNU Lesser General Public
 *	License along with this library; if not, write to the Free Software
 *	Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */
package org.dhcpcluster.config.xml;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.bind.JAXBElement;

import org.dhcp4java.DHCPConstants;
import org.dhcp4java.DHCPOption;
import org.dhcp4java.HardwareAddress;
import org.dhcp4java.InetCidr;
import org.dhcpcluster.config.ConfigException;
import org.dhcpcluster.config.TopologyConfig;
import org.dhcpcluster.config.xml.data.DhcpServer;
import org.dhcpcluster.config.xml.data.Option;
import org.dhcpcluster.config.xml.data.OptionGeneric;
import org.dhcpcluster.config.xml.data.Options;
import org.dhcpcluster.struct.AddressRange;
import org.dhcpcluster.struct.DHCPRichOption;
import org.dhcpcluster.struct.Subnet;

/**
 * 
 * @author Stephan Hadinger
 * @version 0.71
 */
public final class TopologyConfigReader {

    private static final Logger logger = Logger.getLogger(TopologyConfigReader.class.getName().toLowerCase());

    public static TopologyConfig xmlTopologyReader(DhcpServer.Topology topologyData) {
    	TopologyConfig topologyConfig = new TopologyConfig();
    	
    	// parsing global "options"
    	if (topologyData.getOptions() != null) {
    		Options options = topologyData.getOptions();
    		LinkedList<DHCPOption> optList = new LinkedList<DHCPOption>();
    		byte code;
    		
    		for (Object optObj : options.getOptionOrOptionTimeServersOrOptionRouters()) {
    			code = 0;
    			// first check for code
    			if (optObj instanceof JAXBElement<?>) {
    				// parse name for code resolution
    				String optName = ((JAXBElement)optObj).getName().getLocalPart();
    				String optName2 = "DHO_" + optName.substring(OPTION_PREFIX.length());
    				optName2 = optName2.replace("-", "_").toUpperCase();
    				Byte codeByte = DHCPConstants.getDhoNamesReverse(optName2);
    				if (codeByte == null) {
    					logger.warning("Unrecognized option name: "+optName);
    					continue;
    				}
    				code = codeByte;
    				
    				// now force value parsing
    				optObj = ((JAXBElement)optObj).getValue();
    			} else if (optObj instanceof Option) {
    				code = ((Option)optObj).getCode();
    			}
    			// TODO mirror ? 
    			if (optObj instanceof OptionGeneric) {
    				OptionGeneric opt = (OptionGeneric)optObj;
    				makeOptionValue(code, ((OptionGeneric)optObj).getValueByteOrValueShortOrValueInt());
    			} else {
    				logger.warning("Unknown option object: "+optObj);
    			}
    		}
    		
    		
//    		for (Object optObj : options.getOptionRoutersOrOptionOrOptionTimeServers()) {
//    			if (optObj instanceof Option) {
//    				Option opt = (Option)optObj;
//    				makeOptionValue(opt.getValueByteOrValueInetOrValueShort());
//    				for (JAXBElement valueElt : opt.getValueByteOrValueInetOrValueShort()) {
//    					System.out.println(valueElt);
//    				}
////    				DHCPOption resOption = makeOptionValue(opt.getValueByteOrValueInetOrValueShort());
//    				optList.add(new DHCPOption((byte)opt.getCode(), new byte[0]));
//    			} else if (optObj instanceof Options.OptionRouters) {
//    				Options.OptionRouters opt = (Options.OptionRouters)optObj;
//    				//DHCPOption resOption = makeOptionValue(opt.getValueInet());
//    			}
//    		}
    	}
    	
    	return topologyConfig;
    }
    
//    public static List<Object> decapsulateJaxbList(List<JAXBElement<?>> list) {
//    	LinkedList<Object> res = new LinkedList<Object>();
//    	for (JAXBElement<?> o : list) {
//    		res.add(o.getValue());
//    	}
//    	return res;
//    }
    
//	public static TopologyConfig xmlTopologyReader1(Element subnetsElt) throws ConfigException {
//		try {
//			TopologyConfig topologyConfiguration = new TopologyConfig();
//			
//			// parse subnets
//			Elements subnets = subnetsElt.getChildElements("subnet");
//			if (logger.isLoggable(Level.FINE)) {
//				logger.fine("subnet: "+subnets.size()+" found");
//			}
//			
//			for (int i=0; i<subnets.size(); i++) {
//				Subnet subnet = null;
//				try {
//					Element subnetElt = subnets.get(i);
//					getElementPath(subnetElt);
//					
//					String address = get1Attribute(subnetElt, "address");
//					logger.finest("address: "+address);
//					String mask = get1Attribute(subnetElt, "mask");
//					logger.finest("mask: "+mask);
//					
//					String comment = getOptAttribute(subnetElt, "comment");
//					
//					InetCidr cidr = new InetCidr(InetAddress.getByName(address),
//												 InetAddress.getByName(mask));
//					
//					// instantiate the Subnet object
//					subnet = new Subnet(cidr);
//					subnet.setComment(comment);
//					
//					// check for giaddrs
//					Elements giaddrs = subnetElt.getChildElements("giaddr");
//					
//					for (int j=0; j<giaddrs.size(); j++) {
//						Element giaddr = giaddrs.get(j);
//						subnet.getGiaddrs().add(InetAddress.getByName(giaddr.getValue()));
//					}
//					
//					// look for ranges
//					Elements ranges = subnetElt.getChildElements("range");
//					readAddressRanges(subnet, ranges);
//					
//					// look for static ip addresses
//					Elements statics= subnetElt.getChildElements("static");
//					readStaticAddresses(subnet, statics);
//
//					// look for options
//					Elements optionsRoot = subnetElt.getChildElements("options");
//					if (optionsRoot.size() > 1) {
//						throw new ConfigException("too many options sections: "+optionsRoot.size());
//					}
//					if (optionsRoot.size() == 1) {
//						readOptionElements(subnet, optionsRoot.get(0).getChildElements());
//					}
//				} catch (ConfigException e) {
//					logger.log(Level.WARNING, "error reading subnet configuration", e);
//				} finally {
//					// do we do something with the subnet ?
//					if (subnet != null) {
//						topologyConfiguration.addSubnet(subnet);
//					}
//				}
//			}
//			
//			return topologyConfiguration;
//		} catch (IOException e) {
//			logger.log(Level.FINE, "ioerror", e);
//			throw new ConfigException("IO exception", e);
//		}
//	}

	/**
	 * Read address ranges from the XML configuration file.
	 * 
	 * @param subnet the <tt>Subnet</tt> object being created 
	 * @param ranges list of "range" xml elements
	 */
//	private static void readAddressRanges(Subnet subnet, Elements ranges) {
//		for (int j=0; j<ranges.size(); j++) {
//			AddressRange range = null;
//			try {
//				Element rangeElt = ranges.get(j);
//				String rangeStart = rangeElt.getAttributeValue("start");
//				String rangeEnd = rangeElt.getAttributeValue("end");
//				if (rangeStart == null) {
//					throw new ConfigException("range @start missing in "+getElementPath(rangeElt));
//				}
//				if (rangeEnd == null) {
//					throw new ConfigException("range @end missing in "+getElementPath(rangeElt));
//				}
//				if (logger.isLoggable(Level.FINEST)) {
//					logger.finest("range start: "+rangeStart+", range end: "+rangeEnd+", from "+getElementPath(rangeElt));
//				}
//				
//				range = new AddressRange(InetAddress.getByName(rangeStart),
//										 InetAddress.getByName(rangeEnd));
//			} catch (ConfigException e) {
//				logger.log(Level.WARNING, "address range is invalid", e);
//			} catch (UnknownHostException e) {
//				logger.log(Level.WARNING, "error parsing address", e);
//			} finally {
//				if (range != null) {
//					subnet.getAddrRanges().add(range);
//				}
//			}
//		}
//
//	}
	
	/**
	 * Reads the <tt>static</tt> element and adds it to the <tt>Subnet</tt>.
	 * 
	 * @param subnet
	 * @param statics
	 */
//	private static void readStaticAddresses(Subnet subnet, Elements statics) throws ConfigException {
//		for (int i=0; i<statics.size(); i++) {
//			HardwareAddress macAddr = null;
//			InetAddress ipAddr = null;
//			try {
//				Element staticElt = statics.get(i);
//				String addressAttr = staticElt.getAttributeValue("address");
//				String ethernetAttr = staticElt.getAttributeValue("ethernet");
//				if (addressAttr == null) {
//					throw new ConfigException("static @address missing in "+getElementPath(staticElt));
//				}
//				ipAddr = InetAddress.getByName(addressAttr);
//				if (addressAttr == null) {
//					throw new ConfigException("static @ethernet missing in "+getElementPath(staticElt));
//				}
//				macAddr = HardwareAddress.getHardwareAddressByString(ethernetAttr);
//			} catch (ConfigException e) {
//				logger.log(Level.WARNING, "static is invalid", e);
//			} catch (UnknownHostException e) {
//				logger.log(Level.WARNING, "error parsing address", e);
//			} catch (Exception e) {
//				logger.log(Level.WARNING, "general exception", e);
//			} finally {
//				if ((macAddr != null) && (ipAddr != null)) {
//					subnet.addStaticAddress(macAddr, ipAddr);
//				}
//			}
//		}
//	}
//	
	
	/**
	 * Read the "option" elements from the "options" section in the XML config file.
	 * 
	 * @param subnet the <tt>Subnet</tt> object being created 
	 * @param options list of "option" xml elements
	 */
//	private static void readOptionElements(Subnet subnet, Elements options) {
//		List<DHCPOption> dhcpOptions = new LinkedList<DHCPOption>();
//		optionloop: for (int j=0; j<options.size(); j++) {
//			try {
//				Element option = options.get(j);
//				String optionName = option.getLocalName();
//				byte code;
//				DHCPOption dhcpOption;
//
//				// ===== <option>
//				if (optionName.equals("option")) {
//					// get "code" attribute
//					String codeAttr = option.getAttributeValue("code");
//					if (codeAttr == null) {
//						throw new ConfigException("no code attrtibute for "+getElementPath(option));
//					}
//					code = (byte) Integer.parseInt(codeAttr);
//				} else if (optionName.startsWith("option-")) {		// option prefixed with "option"
//					String dhcpOptionName = "DHO_"+optionName.substring("option-".length()).toUpperCase().replace('-', '_');
//					Byte codeByte = DHCPConstants.getDhoNamesReverse(dhcpOptionName);
//					if (codeByte != null) {
//						code = codeByte;
//					} else {
//						throw new ConfigException("unknow dhcp option: "+optionName);
//					}
//				} else {			// ignoring anything else
//					logger.warning("ignoring non-option: "+getElementPath(option));
//					continue optionloop;
//				}
//				logger.finest("option: code="+code);
//				
//				dhcpOption = readOptionValue(code, option);
//				if (dhcpOption != null) {
//					dhcpOptions.add(dhcpOption);
//				}
//			} catch (NumberFormatException e) {
//				logger.log(Level.WARNING, "bad code attribute format", e);
//			} catch (ConfigException e) {
//				logger.log(Level.WARNING, "error parsing option", e);
//			} catch (IOException e) {
//				logger.log(Level.WARNING, "IO error", e);
//			}
//		}
//		subnet.setDhcpOptions(dhcpOptions.toArray(DHCPOPTION_0));
//	}
	
	private static DHCPOption makeOptionValue(byte code, List<JAXBElement<?>> optList) {
		DHCPOption resOption = null;
		ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
		DataOutputStream outputStream = new DataOutputStream(byteOutput);
		int mirrorDetected = 0;
		try {
			for (JAXBElement<?> optElement : optList) {
				// switch according to base type
				Object obj = optElement.getValue();
				if (obj instanceof InetAddress) {
					outputStream.write(((InetAddress)obj).getAddress());
				} else if (obj instanceof Byte) {
					outputStream.writeByte(((Byte)obj).intValue());
				} else if (obj instanceof Short) {
					outputStream.writeShort(((Short)obj).intValue());
				} else if (obj instanceof Integer) {
					outputStream.writeInt((Integer)obj);
				} else if (obj instanceof String) {
					outputStream.writeBytes((String)obj);
				} else if (obj instanceof byte[]) {
					outputStream.write((byte[])obj);
				} else {
					logger.warning("Unsupported value type: "+obj.toString());
				}
			}
			resOption = new DHCPRichOption(code, byteOutput.toByteArray(), false);
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Unexpected IOException", e);
			return null;
		}
		
		return resOption;
	}
	
	/**
	 * Read the value sub-portion of an option element
	 * 
	 * @param option
	 * @return
	 * @throws ConfigException
	 * @throws IOException
	 */
//	private static DHCPOption readOptionValue(byte code, Element option) throws ConfigException, IOException {
//		if (option == null) {
//			throw new NullPointerException("option is null");
//		}
//		Elements optionValueElts = option.getChildElements();
//		ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
//		DataOutputStream outputStream = new DataOutputStream(byteOutput);
//		int mirrorDetected = 0;
//		
//		// TODO change 'mirror' into an attribute and not a child element
//		for (int i=0; i<optionValueElts.size(); i++) {
//			Element valueElt = optionValueElts.get(i);
//			String valueName = valueElt.getLocalName();
//			if (valueName.equals("value-byte")) {
//				outputStream.writeByte((byte)Integer.parseInt(valueElt.getValue()));
//			} else if (valueName.equals("value-short")) {
//				outputStream.writeShort((short)Integer.parseInt(valueElt.getValue()));
//			} else if (valueName.equals("value-int")) {
//				outputStream.writeInt(Integer.parseInt(valueElt.getValue()));
//			} else if (valueName.equals("value-inet")) {
//				outputStream.write(InetAddress.getByName(valueElt.getValue()).getAddress());
//			} else if (valueName.equals("value-string")) {
//				outputStream.writeBytes(valueElt.getValue());
//			} else if (valueName.equals("value-string-item")) {
//				String stringItem = valueElt.getValue();
//				if (stringItem.length() > 255) {
//					throw new ConfigException("length of string-item is > 255: "+valueElt.getValue());
//				}
//				outputStream.writeByte(stringItem.length());
//				outputStream.writeBytes(stringItem);
//			} else if (valueName.equals("mirror")) {
//				mirrorDetected++;
//			}
//		}
//
//		if (mirrorDetected > 1) {
//			throw new ConfigException("too many mirror elements ("+mirrorDetected+") at "+getElementPath(option));
//		}
//		return new DHCPOption(code, byteOutput.toByteArray(), mirrorDetected > 0);
//	}
	
	private static final String OPTION_PREFIX = "option-";
    private static final DHCPOption[] DHCPOPTION_0 = new DHCPOption[0];
}