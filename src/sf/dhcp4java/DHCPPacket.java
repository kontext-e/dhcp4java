/*
 *	This file is part of dhcp4java.
 *
 *	dhcp4java is free software; you can redistribute it and/or modify
 *	it under the terms of the GNU General Public License as published by
 *	the Free Software Foundation; either version 2 of the License, or
 *	(at your option) any later version.
 *
 *	dhcp4java is distributed in the hope that it will be useful,
 *	but WITHOUT ANY WARRANTY; without even the implied warranty of
 *	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *	GNU General Public License for more details.
 *
 *	You should have received a copy of the GNU General Public License
 *	along with Foobar; if not, write to the Free Software
 *	Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 *
 * (c) 2006 Stephan Hadinger
 */

package sf.dhcp4java;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static sf.dhcp4java.DHCPConstants.*;

/**
 * The basic class for manipulating DHCP packets.
 * 
 * @author Stephan Hadinger
 * @version 0.50
 *
 * <p>There are two basic ways to build a new DHCPPacket object.
 * <p>First one is to build an object from scratch using the constructor and setters.
 * If you need to set repeatedly the same set of parameters and options,
 * you can create a "master" object and clone it many times.
 * 
 * <pre>
 * DHCPPacket discover = new DHCPPacket();
 * discover.setOp(DHCPPacket.BOOTREQUEST);
 * discover.setHtype(DHCPPacket.HTYPE_ETHER);
 * discover.setHlen((byte) 6);
 * discover.setHops((byte) 0);
 * discover.setXid( (new Random()).nextInt() );
 * ...
 * </pre>
 * Second is to decode a DHCP datagram received from the network.
 * In this case, the object is created through a factory.
 * 
 * <p>Example: simple DHCP sniffer
 * <pre>
 * DatagramSocket socket = new DatagramSocket(67);
 * while (true) {
 *     DatagramPacket pac = new DatagramPacket(new byte[1500], 1500);
 *     socket.receive(pac);
 *     DHCPPacket dhcp = DHCPPacket.getPacket(pac);
 *     System.out.println(dhcp.toString());
 * }
 * </pre>
 * In this second way, beware that a <tt>BadPacketExpcetion</tt> is thrown
 * if the datagram contains invalid DHCP data.
 * 
 * 
 * <p><b>Getters and Setters</b>: methods are provided with high-level data structures
 * wherever it is possible (String, InetAddress...). However there are also low-overhead
 * version (suffix <tt>Raw</tt>) dealing directly with <tt>byte[]</tt> for maximum performance.
 * They are useful in servers for copying parameters in a servers from a request to a response without
 * any type conversion. All parameters are copies, you may modify them as you like without
 * any side-effect on the <tt>DHCPPacket</tt> object.
 * 
 * <h4>DHCP datagram format description:</h4>
 * <blockquote><table cellspacing=2>
 * 	<tr><th>Field</th><th>Octets</th><th>Description</th></tr>
 * 	<tr><td valign=top><tt>op</tt></td><td valign=top>1</td>
 * 						<td>Message op code / message type.<br>
 * 						use constants
 * 						<tt>BOOTREQUEST</tt>,
 * 						<tt>BOOTREPLY</tt></td></tr>
 * 	<tr><td valign=top><tt>htype</tt></td>
 * 						<td valign=top>1</td><td>Hardware address type, see ARP section in
 * 						"Assigned Numbers" RFC<br>
 * 						use constants
 * 						<tt>HTYPE_ETHER</tt>,
 * 						<tt>HTYPE_IEEE802</tt>,
 * 						<tt>HTYPE_FDDI</tt></td></tr>
 * 	<tr><td valign=top><tt>hlen</tt></td><td>1</td><td>Hardware address length 
 * 						(e.g.  '6' for ethernet).</td></tr>
 * 	<tr><td valign=top><tt>hops</tt></td><td valign=top>1</td><td>Client sets to zero, optionally used 
 * 						by relay agents when booting via a relay agent.</td></tr>
 * 	<tr><td valign=top><tt>xid</tt></td><td valign=top>4</td>
 * 						<td>Transaction ID, a random number chosen by the 
 * 						client, used by the client and server to associate
 * 						messages and responses between a client and a
 * 						server.</td></tr>
 * 	<tr><td valign=top><tt>secs</tt></td><td valign=top>2</td>
 * 						<td>Filled in by client, seconds elapsed since client
 * 						began address acquisition or renewal process.</td></tr>
 * 	<tr><td valign=top><tt>flags</tt></td><td valign=top>2</td>
 * 						<td>Flags (see below).</td></tr>
 * 	<tr><td valign=top><tt>ciaddr</tt></td><td valign=top>4</td>
 * 						<td>Client IP address; only filled in if client is in
 * 						BOUND, RENEW or REBINDING state and can respond
 * 						to ARP requests.</td></tr>
 * 	<tr><td valign=top><tt>yiaddr</tt></td><td valign=top>4</td>
 * 						<td>'your' (client) IP address.</td></tr>
 * 	<tr><td valign=top><tt>siaddr</tt></td><td valign=top>4</td>
 * 						<td>IP address of next server to use in bootstrap;
 * 						returned in DHCPOFFER, DHCPACK by server.</td></tr>
 * 	<tr><td valign=top><tt>giaddr</tt></td><td valign=top>4</td>
 * 						<td>Relay agent IP address, used in booting via a
 * 						relay agent.</td></tr>
 * 	<tr><td valign=top><tt>chaddr</tt></td><td valign=top>16</td>
 * 						<td>Client hardware address.</td></tr>
 * 	<tr><td valign=top><tt>sname</tt></td><td valign=top>64</td>
 * 						<td>Optional server host name, null terminated string.</td></tr>
 * 	<tr><td valign=top><tt>file</tt></td><td valign=top>128</td>
 * 						<td>Boot file name, null terminated string; "generic"
 * 						name or null in DHCPDISCOVER, fully qualified
 * 						directory-path name in DHCPOFFER.</td></tr>
 * 	<tr><td valign=top><tt>isDhcp</tt></td><td valign=top>4</td>
 * 						<td>Controls whether the packet is BOOTP or DHCP.
 * 						DHCP conatains the "magic cookie" of 4 bytes.
 * 						0x63 0x82 0x53 0x63.</td></tr>
 * 	<tr><td valign=top><tt>DHO_*code*</tt></td><td valign=top>*</td>
 * 						<td>Optional parameters field.  See the options
 * 						documents for a list of defined options. See below.</td></tr>
 * 	<tr><td valign=top><tt>padding</tt></td><td valign=top>*</td>
 * 						<td>Optional padding at the end of the packet.</td></tr>
 * </table></blockquote>
 * 
 * <h4>DHCP Option</h4>
 * 
 * The following options are codes are supported:
 * <pre>
 * DHO_SUBNET_MASK(1)
 * DHO_TIME_OFFSET(2)
 * DHO_ROUTERS(3)
 * DHO_TIME_SERVERS(4)
 * DHO_NAME_SERVERS(5)
 * DHO_DOMAIN_NAME_SERVERS(6)
 * DHO_LOG_SERVERS(7)
 * DHO_COOKIE_SERVERS(8)
 * DHO_LPR_SERVERS(9)
 * DHO_IMPRESS_SERVERS(10)
 * DHO_RESOURCE_LOCATION_SERVERS(11)
 * DHO_HOST_NAME(12)
 * DHO_BOOT_SIZE(13)
 * DHO_MERIT_DUMP(14)
 * DHO_DOMAIN_NAME(15)
 * DHO_SWAP_SERVER(16)
 * DHO_ROOT_PATH(17)
 * DHO_EXTENSIONS_PATH(18)
 * DHO_IP_FORWARDING(19)
 * DHO_NON_LOCAL_SOURCE_ROUTING(20)
 * DHO_POLICY_FILTER(21)
 * DHO_MAX_DGRAM_REASSEMBLY(22)
 * DHO_DEFAULT_IP_TTL(23)
 * DHO_PATH_MTU_AGING_TIMEOUT(24)
 * DHO_PATH_MTU_PLATEAU_TABLE(25)
 * DHO_INTERFACE_MTU(26)
 * DHO_ALL_SUBNETS_LOCAL(27)
 * DHO_BROADCAST_ADDRESS(28)
 * DHO_PERFORM_MASK_DISCOVERY(29)
 * DHO_MASK_SUPPLIER(30)
 * DHO_ROUTER_DISCOVERY(31)
 * DHO_ROUTER_SOLICITATION_ADDRESS(32)
 * DHO_STATIC_ROUTES(33)
 * DHO_TRAILER_ENCAPSULATION(34)
 * DHO_ARP_CACHE_TIMEOUT(35)
 * DHO_IEEE802_3_ENCAPSULATION(36)
 * DHO_DEFAULT_TCP_TTL(37)
 * DHO_TCP_KEEPALIVE_INTERVAL(38)
 * DHO_TCP_KEEPALIVE_GARBAGE(39)
 * DHO_NIS_SERVERS(41)
 * DHO_NTP_SERVERS(42)
 * DHO_VENDOR_ENCAPSULATED_OPTIONS(43)
 * DHO_NETBIOS_NAME_SERVERS(44)
 * DHO_NETBIOS_DD_SERVER(45)
 * DHO_NETBIOS_NODE_TYPE(46)
 * DHO_NETBIOS_SCOPE(47)
 * DHO_FONT_SERVERS(48)
 * DHO_X_DISPLAY_MANAGER(49)
 * DHO_DHCP_REQUESTED_ADDRESS(50)
 * DHO_DHCP_LEASE_TIME(51)
 * DHO_DHCP_OPTION_OVERLOAD(52)
 * DHO_DHCP_MESSAGE_TYPE(53)
 * DHO_DHCP_SERVER_IDENTIFIER(54)
 * DHO_DHCP_PARAMETER_REQUEST_LIST(55)
 * DHO_DHCP_MESSAGE(56)
 * DHO_DHCP_MAX_MESSAGE_SIZE(57)
 * DHO_DHCP_RENEWAL_TIME(58)
 * DHO_DHCP_REBINDING_TIME(59)
 * DHO_VENDOR_CLASS_IDENTIFIER(60)
 * DHO_DHCP_CLIENT_IDENTIFIER(61)
 * DHO_NWIP_DOMAIN_NAME(62)
 * DHO_NWIP_SUBOPTIONS(63)
 * DHO_NIS_DOMAIN(64)
 * DHO_NIS_SERVER(65)
 * DHO_TFTP_SERVER(66)
 * DHO_BOOTFILE(67)
 * DHO_MOBILE_IP_HOME_AGENT(68)
 * DHO_SMTP_SERVER(69)
 * DHO_POP3_SERVER(70)
 * DHO_NNTP_SERVER(71)
 * DHO_WWW_SERVER(72)
 * DHO_FINGER_SERVER(73)
 * DHO_IRC_SERVER(74)
 * DHO_STREETTALK_SERVER(75)
 * DHO_STDA_SERVER(76)
 * DHO_USER_CLASS(77)
 * DHO_FQDN(81)
 * DHO_DHCP_AGENT_OPTIONS(82)
 * DHO_NDS_SERVERS(85)
 * DHO_NDS_TREE_NAME(86)
 * DHO_USER_AUTHENTICATION_PROTOCOL(98)
 * DHO_AUTO_CONFIGURE(116)
 * DHO_NAME_SERVICE_SEARCH(117)
 * DHO_SUBNET_SELECTION(118)
 * </pre>
 * 
 * <p>These options can be set and get through basic low-level <tt>getOptionRaw</tt> and
 * <tt>setOptionRaw</tt> passing <tt>byte[]</tt> structures. Using these functions, data formats
 * are under your responsibility. Arrays are always passed by copies (clones) so you can modify
 * them freely without side-effects. These functions allow maximum performance, especially
 * when copying options from a request datagram to a response datagram.
 * 
 * <h4>Special case: DHO_DHCP_MESSAGE_TYPE</h4>
 * The DHCP Message Type (option 53) is supported for the following values
 * <pre>
 * DHCPDISCOVER(1)
 * DHCPOFFER(2)
 * DHCPREQUEST(3)
 * DHCPDECLINE(4)
 * DHCPACK(5)
 * DHCPNAK(6)
 * DHCPRELEASE(7)
 * DHCPINFORM(8)
 * DHCPFORCERENEW(9)
 * DHCPLEASEQUERY(13)
 * </pre>
 * 
 * <h4>DHCP option formats</h4>
 * 
 * A limited set of higher level data-structures are supported. Type checking is enforced
 * according to rfc 2132. Check corresponding methods for a list of option codes allowed for
 * each datatype.
 * 
 * <blockquote>
 * <br>Inet (4 bytes - IPv4 address)
 * <br>Inets (X*4 bytes - list of IPv4 addresses)
 * <br>Short (2 bytes - short)
 * <br>Shorts (X*2 bytes - list of shorts)
 * <br>Byte (1 byte)
 * <br>Bytes (X bytes - list of 1 byte parameters)
 * <br>String (X bytes - ASCII string)
 * <br>
 * </blockquote>
 * 
 * 
 * <p><b>Note</b>: this class is not synchronized for maximum performance.
 * However, it is unlikely that the same <tt>DHCPPacket</tt> is used in two different 
 * threads in real life DHPC servers or clients. Multi-threading acces
 * to an instance of this class is at your own risk.
 * 
 * <p><b>Limitations</b>: this class doesn't support spanned options or options longer than 256 bytes.
 * It does not support options stored in <tt>sname</tt> or <tt>file</tt> fields.
 * 
 * <p>This API is originally a port from my PERL
 * <tt><a href="http://search.cpan.org/~shadinger/">Net::DHCP</a></tt> api.
 * 
 * <p><b>Future extensions</b>: IPv6 support, extended data structure TODO...
 * 
 */
public class DHCPPacket implements Cloneable, Serializable {
	private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger("sf.dhcp4java.dhcppacket");
	
    // ----------------------------------------------------------------------
    // user defined comment
    private String comment = "";			// Free user-defined comment

    // ----------------------------------------------------------------------
    // static structure of the packet
    private byte op = BOOTREPLY;			// Op code
    private byte htype = HTYPE_ETHER;		// HW address Type
    private byte hlen = 6;					// hardware address length
    private byte hops = 0;					// Hw options
    private int xid = 0;					// transaction id
    private short secs = 0;	 				// elapsed time from trying to boot
    private short flags = 0;				// flags
    private byte ciaddr[] = new byte[4];	// client IP
    private byte yiaddr[] = new byte[4];	// your client IP
    private byte siaddr[] = new byte[4];	// Server IP
    private byte giaddr[] = new byte[4];	// relay agent IP
    private byte chaddr[] = new byte[16];   // Client HW address
    private byte sname[] = new byte[64];	// Optional server host name
    private byte file[] = new byte[128];	// Boot file name
    
    // ----------------------------------------------------------------------
    // options part of the packet

    // DHCP options
    // Invariant 1: K is identical to V.getCode()
    // Invariant 2: V.value is never <tt>null</tt>
    // Invariant 3; K is not 0 (PAD) and not -1 (END)
    private LinkedHashMap<Byte, DHCPOption> options = new LinkedHashMap<Byte, DHCPOption>();
    private boolean isDhcp = true;			// well-formed DHCP Packet ?
    private boolean truncated = false;		// are the option truncated
    // ----------------------------------------------------------------------
    // extra bytes for padding
    private byte padding[] = new byte[0];	// end of packet padding
    
    // ----------------------------------------------------------------------
    // Address/port address of the machine to which this datagram is being sent 
    // or from which the datagram was received
    private InetAddress address = null;
    private int port = 0;
    
    
    /**
     * Constructor for the <tt>DHCPPacket</tt> class.
     * 
     * <p>This creates an empty <tt>DHCPPacket</tt> datagram.
     * All data is default values and the packet is still lacking key data
     * to be sent on the wire.
     */
    public DHCPPacket() {
    }

    /**
     * Factory for creating <tt>DHCPPacket</tt> objects by parsing a
     * <tt>DatagramPacket</tt> object.
     * 
     * @param datagram the UDP datagram received to be parsed
     * @return the newly create <tt>DHCPPacket</tt> instance
     * @throws DHCPBadPacketException the datagram is malformed and cannot be parsed properly.
     * @throws IllegalArgumentException datagram is <tt>null</tt>
     * @throws IOException
     */
    public static DHCPPacket getPacket(DatagramPacket datagram) throws DHCPBadPacketException, IOException {
    	if (datagram == null)
    		throw new IllegalArgumentException("datagram is null");
        DHCPPacket packet = new DHCPPacket();
        // all parameters are checked in marshall()
        packet.marshall(datagram.getData(), datagram.getOffset(), datagram.getLength(),
                		datagram.getAddress(), datagram.getPort());
        return packet;
    }

    /**
     * Factory for creating <tt>DHCPPacket</tt> objects by parsing a
     * <tt>byte[]</tt> e.g. from a datagram.
     * 
     * @param buf buffer for holding the incoming datagram.
     * @param offset the offset for the buffer.
     * @param length the number of bytes to read.
     * @return the newly create <tt>DHCPPacket</tt> instance
     * @throws DHCPBadPacketException the datagram is malformed.
     */
    public static DHCPPacket getPacket(byte[] buf, int offset, int length) throws DHCPBadPacketException {
        DHCPPacket packet = new DHCPPacket();
        // all parameters are checked in marshall()
        packet.marshall(buf, offset, length, null, 0);
        return packet;
    }

    /**
     * Returns a copy of this <tt>DHCPPacket</tt>.
     * 
     * <p>The <tt>truncated</tt> flag is reset.
     * TODO is it a shallow copy of arrays?
     * 
     * @return a copy of the <tt>DHCPPacket</tt> instance.
     */
    public DHCPPacket clone() {
    	DHCPPacket p;
    	try {
    		p = (DHCPPacket) super.clone();

    		// specifically cloning arrays to avoid side-effects
    		p.ciaddr = this.ciaddr.clone();
    		p.yiaddr = this.yiaddr.clone();
    		p.siaddr = this.siaddr.clone();
    		p.giaddr = this.giaddr.clone();
    		p.chaddr = this.chaddr.clone();
    		p.sname = this.sname.clone();
    		p.file = this.file.clone();
    		//p.options = (LinkedHashMap<Byte, DHCPOption>) this.options.clone();
    		p.options = new LinkedHashMap<Byte, DHCPOption>(this.options);
    		p.padding = this.padding.clone();
    		
            p.truncated = false;	// freshly new object, it is not considered as corrupt
    		
            return p;
    	} catch (CloneNotSupportedException e) {
    		// this shouldn't happen, since we are Cloneable
    		throw new InternalError();
    	}
    }
    /**
     * Assert all the invariants of the object. For debug purpose only.
     *
     */
    private final void assertInvariants() {
    	// TODO
    }
    /** 
     * Convert a specified byte array containing a DHCP message into a
     * DHCPMessage object.
     * 
     * @return a DHCPMessage object with information from byte array.
     * @param  buffer  byte array to convert to a DHCPMessage object
     * @throws IllegalArgumentException if buffer is <tt>null</tt>...
     * @throws IndexOutOfBoundsException offset..offset+length is out of buffer bounds
     * @throws DHCPBadPacketException datagram is malformed
     */
    protected DHCPPacket marshall(byte[] buffer, int offset, int length,
            					  InetAddress address, int port) {
        // do some basic sanity checks
        // ibuff, offset & length are valid?
        if (buffer == null)
            throw new IllegalArgumentException("null buffer not allowed");
        if (offset < 0)
            throw new IndexOutOfBoundsException("negative offset not allowed");
        if (length < 0)
            throw new IllegalArgumentException("negative length not allowed");
        if (buffer.length < offset + length)
            throw new IndexOutOfBoundsException("offset+length exceeds buffer length");
        
        // absolute minimum size for a valid packet
        if (length < _BOOTP_ABSOLUTE_MIN_LEN) {
            throw new DHCPBadPacketException("DHCP Packet too small (" + length +
                    ") absolute minimum is " + _BOOTP_ABSOLUTE_MIN_LEN);
        }
        // minimum size for a BOOTP packet (could be a warning?)
        if (length < _BOOTP_MIN_LEN) {
            throw new DHCPBadPacketException("DHCP Packet too small (" + length +
                    ") minimum is " + _BOOTP_MIN_LEN);
        }
        // maximum size for a valid DHCP packet
        if (length > _DHCP_MAX_MTU) {
            throw new DHCPBadPacketException("DHCP Packet too big (" + length +
                    ") max MTU is " + _DHCP_MAX_MTU);
        }
        
        // copy address and port
        this.address = address;		// no need to clone, InetAddress is immutable
        this.port = port;
        
        try {
	        // turn buffer into a readable stream
	        ByteArrayInputStream inBStream = new ByteArrayInputStream(buffer, offset, length);
	        DataInputStream inStream = new DataInputStream (inBStream);
	        
	        // parse static part of packet
	        op = inStream.readByte();
	        htype = inStream.readByte();
	        hlen = inStream.readByte();
	        hops = inStream.readByte();
	        xid = inStream.readInt();
	        secs = inStream.readShort();
	        flags = inStream.readShort();
	        inStream.readFully(ciaddr, 0, 4);
	        inStream.readFully(yiaddr, 0, 4);
	        inStream.readFully(siaddr, 0, 4);
	        inStream.readFully(giaddr, 0, 4);
	        inStream.readFully(chaddr, 0, 16);
	        inStream.readFully(sname, 0, 64);
	        inStream.readFully(file, 0, 128);
	        
	        // get remaining bytes
	        // TODO we don't need to copy the rest of the buffer
	        byte opt_buf[] = new byte[inBStream.available()];
	        inBStream.read(opt_buf);
	        
	        // check for DHCP MAGIC_COOKIE
	        isDhcp = false;
	        if (opt_buf.length > 4) {
	            isDhcp = (	(opt_buf[0] == _MAGIC_COOKIE[0]) &&
	                    	(opt_buf[1] == _MAGIC_COOKIE[1]) &&
	                    	(opt_buf[2] == _MAGIC_COOKIE[2]) &&
	                    	(opt_buf[3] == _MAGIC_COOKIE[3]) );
	        }
	        
	        if (isDhcp) {	// is it a full DHCP packet or a simple BOOTP?
	            // DHCP Packet: parsing options
	            // skip 4 bytes for MAGIC_COOKIE
	            ByteArrayInputStream inOStream = new ByteArrayInputStream(opt_buf, 4, opt_buf.length-4);
	            int type = 0;
	            
	            while (true) {
	                int r = inOStream.read();
	                if (r < 0) break; // EOF
	                
	                type = (byte) r;
	                
	                if (type == DHO_PAD) continue; // skip Padding
	                if (type == DHO_END) break; // break if end of options
	                
	                r = inOStream.read();
	                if (r < 0) break; // EOF
	                
	                int len = r;
	                if (len > inOStream.available()) len = inOStream.available();
	                byte unit_opt[] = new byte[len];
	                inOStream.read(unit_opt);
	                
	                setOption(new DHCPOption((byte)type, unit_opt));	 // store option
	            }
	            truncated = (type != DHO_END); // truncated options?
	            
	            padding = new byte[inOStream.available()];
	            inOStream.read(padding); // resulting padding
	        } else {	// only BOOTP
	            padding = opt_buf;
	        }
	        assertInvariants();
	        return this;
        } catch (IOException e) {
            // unlikely with ByteArrayInputStream
            throw new DHCPBadPacketException("IOException: "+e.toString(), e);
        }
    }
    
    
    /** 
     * Converts the object to a byte array ready to be sent on the wire.
     * 
     * @return a byte array with information from DHCPMessage object.
     * @throws DHCPBadPacketException the datagram would be malformed (too small, too big...)
     */
    public synchronized byte[] serialize() {
    	assertInvariants();
        // prepare output buffer, pre-sized to maximum buffer length
        // default buffer is half the maximum size of possible packet
        // (this seams reasonable for most uses, worst case only doubles the buffer size once
        ByteArrayOutputStream outBStream = new ByteArrayOutputStream(_DHCP_MAX_MTU / 2);
        DataOutputStream outStream = new DataOutputStream (outBStream);
        try {
	        outStream.writeByte(op);
	        outStream.writeByte(htype);
	        outStream.writeByte(hlen);
	        outStream.writeByte(hops);
	        outStream.writeInt(xid);
	        outStream.writeShort(secs);
	        outStream.writeShort(flags);
	        outStream.write(ciaddr, 0, 4);
	        outStream.write(yiaddr, 0, 4);
	        outStream.write(siaddr, 0, 4);
	        outStream.write(giaddr, 0, 4);
	        outStream.write(chaddr, 0, 16);
	        outStream.write(sname, 0, 64);
	        outStream.write(file, 0, 128);
	        
	        if (isDhcp) {
	            // DHCP and not BOOTP -> magic cookire required
	            outStream.write(_MAGIC_COOKIE, 0, 4);
	            
	            // output options in creation order (LinkedHashMap)
	            for (DHCPOption opt : getAllOptions()) {
	            	assert (opt != null);
	            	assert (opt.getCode() != DHO_PAD);
	            	assert (opt.getCode() != DHO_END);
	            	assert (opt.getValueFast() != null);
	            	outStream.writeByte(opt.getCode());		// output option code
	            	outStream.writeByte(opt.getValueFast().length);	// output option length
	            	outStream.write(opt.getValueFast());	// output option data
	            }
	            // mark end of options
	            outStream.writeByte(DHO_END);
	        }
	        
	        // write padding
	        outStream.write(padding);
	        
	        // add padding if the packet is too small
	        int min_padding = _BOOTP_MIN_LEN - outBStream.size();
	        if (min_padding > 0) {
	            byte add_padding[] = new byte[min_padding];
	            outStream.write(add_padding);
	        }
	        
	        // final packet is here
	        byte data[] = outBStream.toByteArray();
	        
	        // do some post sanity checks
	        if (data.length > _DHCP_MAX_MTU)
	            throw new DHCPBadPacketException("serialize: packet too big ("+data.length+" greater than max MAX_MTU ("+_DHCP_MAX_MTU+")");
	        
	        return data;
        } catch (IOException e) {
            // nomrally impossible with ByteArrayOutputStream
            logger.log(Level.SEVERE, "Unexpected Exception", e);
            throw new DHCPBadPacketException("IOException raised: "+e.toString());
        }
    }

    // ========================================================================
    // debug functions
    
    /**
     * Returns a detailed string representation of the DHCP datagram.
     * 
     * <p>This multi-line string details: the static, options and padding parts
     * of the object. This is useful for debugging, but not efficient.
     * 
     * @return a string representation of the object.
     */
    public String toString() {
        StringBuffer s = new StringBuffer(); // output buffer
        try {
	        if (isDhcp) {
	            s.append("DHCP Packet");
	        } else {
	            s.append("BOOTP Packet");
	        }
	        s.append("\ncomment=").append(comment);
	        s.append("\naddress=").append(address).append('(').append(port).append(')');
	        
	        s.append("\nop=");
	        Byte opByte = new Byte(op);
	        if (_BOOT_NAMES.containsKey(opByte)) {
	            s.append(_BOOT_NAMES.get(opByte)).append("(").append(op).append(")");
	        } else {
	            s.append(op);
	        }

	        s.append("\nhtype=");
	        Byte htypeByte = new Byte(htype);
	        if (_HTYPE_NAMES.containsKey(htypeByte)) {
	            s.append(_HTYPE_NAMES.get(htypeByte)).append("(").append(htype).append(")");
	        } else {
	            s.append(htype);
	        }
	        
	        s.append("\nhlen=").append(hlen);
	        s.append("\nhops=").append(hops);
	        s.append("\nxid=").append("0x").append(toHex(xid));
	        s.append("\nsecs=").append(secs);
	        s.append("\nflags=").append("0x").append(Integer.toHexString(flags));
            s.append("\nciaddr=").append(getHostAddress(InetAddress.getByAddress(ciaddr)));
            s.append("\nyiaddr=").append(getHostAddress(InetAddress.getByAddress(yiaddr)));
            s.append("\nsiaddr=").append(getHostAddress(InetAddress.getByAddress(siaddr)));
            s.append("\ngiaddr=").append(getHostAddress(InetAddress.getByAddress(giaddr)));
            s.append("\nchaddr=").append("0x").append(getChaddrAsHex());
            s.append("\nsname=").append(getSname());
            s.append("\nfile=").append(getFile());
            
            if (isDhcp) {
                s.append("\nOptions follows:");

                // parse options in creation order (LinkedHashMap)
                for (DHCPOption opt : getAllOptions()) {
                	s.append("\n").append(opt.toString());
                }                
            }
            
            // padding
            s.append("\npadding[").append(padding.length).append("]=").append(toHex(padding));
        } catch (Exception e) {
            // what to do ???
        }
        
        return s.toString();
    }
    
    // ========================================================================
    // getters and setters
    
    /**
     * Returns the comment associated to this packet.
     * 
     * <p>This field can be used freely and has no influence on the real network datagram.
     * It can be used to store a transaction number or any other information
     * 
     * @return the _comment field.
     */
    public String getComment() {
        return comment;
    }
    /**
     * Sets the comment associated to this packet.
     * 
     * <p>This field can be used freely and has no influence on the real network datagram.
     * It can be used to store a transaction number or any other information
     * 
     * @param comment The comment to set.
     */
    public void setComment(String comment) {
        this.comment = comment;
    }
    /**
     * Returns the chaddr field (Client hardware address - typically MAC address).
     * 
     * <p>Returns the byte[16] raw buffer. Only the first <tt>hlen</tt> bytes are valid.
     * 
     * @return the chaddr field.
     */
    public byte[] getChaddr() {
        return (byte[]) chaddr.clone();
    }
    
    /**
     * Returns the chaddr field (Client hardware address - typically MAC address) as 
     * a hex string.
     * 
     * <p>Only first <tt>hlen</tt> bytes are printed, as uppercase hex string.
     * 
     * @return the chaddr field as hex string.
     */
    public String getChaddrAsHex() {
        return toHex(chaddr, 0, hlen);
    }
    /**
     * Sets the chaddr field (Client hardware address - typically MAC address).
     * 
     * <p>The buffer length should be between 0 and 16, otherwise an
     * <tt>IllegalArgumentException</tt> is thrown.
     * 
     * <p>If chaddr is null, the field is filled with zeros.
     * 
     * @param chaddr The chaddr to set.
     * @throws IllegalArgumentException chaddr buffer is longer than 16 bytes.
     */
    public void setChaddr(byte[] chaddr) {
        if (chaddr != null) {
            if (chaddr.length > this.chaddr.length)
                throw new IllegalArgumentException("chaddr is too long:"+chaddr.length+" max is:"+this.chaddr.length);
            Arrays.fill(this.chaddr, (byte) 0);
            System.arraycopy(chaddr, 0, this.chaddr, 0, chaddr.length);
        } else {
            Arrays.fill(this.chaddr, (byte) 0);
        }
    }
    /**
     * Returns the ciaddr field (Client IP Address).
     * 
     * @return the ciaddr field converted to <tt>InetAddress</tt> object.
     */
    public InetAddress getCiaddr() {
        try {
            return InetAddress.getByAddress(getCiaddrRaw());
        } catch (UnknownHostException e) {
        	logger.log(Level.SEVERE, "Unexpected UnknownHostException", e);
            return null;	// normaly impossible
        }
    }
    
    /**
     * Returns the ciaddr field (Client IP Address).
     * 
     * <p>This is the low-level maximum performance getter for this field.

     * 
     * @return Returns the ciaddr as raw byte[4].
     */
    public byte[] getCiaddrRaw() {
        return (byte[]) ciaddr.clone();
    }

    /**
     * Sets the ciaddr field (Client IP Address).
     * 
     * <p>Ths <tt>ciaddr</tt> field must be of <tt>Inet4Address</tt> class or
     * an <tt>IllegalArgumentException</tt> is thrown.
     * 
     * @param ciaddr The ciaddr to set.
     */
    public void setCiaddr(InetAddress ciaddr) {
        if (!(ciaddr instanceof Inet4Address))
            throw new IllegalArgumentException("Inet4Address required");
        setCiaddrRaw(ciaddr.getAddress());
    }
    /**
     * Sets the ciaddr field (Client IP Address).
     * 
     * @param ciaddr The ciaddr to set.
     * @throws UnknownHostException
     */
    public void setCiaddr(String ciaddr) throws UnknownHostException {
        setCiaddr(InetAddress.getByName(ciaddr));
    }
    /**
     * Sets the ciaddr field (Client IP Address).
     * 
     * <p><tt>ciaddr</tt> must be a 4 bytes array, or an <tt>IllegalArgumentException</tt>
     * is thrown.
     * 
     * <p>This is the low-level maximum performance setter for this field.
     * The array is internally copied so any further modification to <tt>ciaddr</tt>
     * parameter has no side effect.
     * 
     * @param ciaddr The ciaddr to set.
     */
    public void setCiaddrRaw(byte[] ciaddr) {
        if (ciaddr.length != 4)
            throw new IllegalArgumentException("4 bytes array required");
        System.arraycopy(ciaddr, 0, this.ciaddr, 0, 4);
    }
    /**
     * Returns the file field (Boot File Name).
     * 
     * <p>Returns the raw byte[128] buffer, containing a null terminated string.
     * 
     * <p>This is the low-level maximum performance getter for this field.
     * 
     * @return the file field.
     */
    public byte[] getFileRaw() {
        return (byte[]) file.clone();
    }
    
    /**
     * Returns the file field (Boot File Name) as String.
     * 
     * @return the file converted to a String (transparent encoding).
     */
    public String getFile() {
        return bytesToString(getFileRaw());		// TODO remove leading zeroes
    }
    /**
     * Sets the file field (Boot File Name) as String.
     * 
     * <p>The string is first converted to a byte[] array using transparent
     * encoding. If the resulting buffer size is > 128, an <tt>IllegalArgumentException</tt>
     * is thrown.
     * 
     * <p>If <tt>file</tt> parameter is null, the buffer is filled with zeros.
     * 
     * @param file The file field to set.
     * @throws IllegalArgumentException string too long
     */
    public void setFile(String file) {
    	setFileRaw(stringToBytes(file));
    }
    
    /**
     * Sets the file field (Boot File Name) as String.
     * 
     * <p>If the buffer size is > 128, an <tt>IllegalArgumentException</tt>
     * is thrown.
     * 
     * <p>If <tt>file</tt> parameter is null, the buffer is filled with zeros.
     * 
     * <p>This is the low-level maximum performance setter for this field.
     * 
     * @param file The file field to set.
     * @throws IllegalArgumentException string too long
     */
    public void setFileRaw(byte[] file) {
        if (file != null) {
	        if (file.length > this.file.length)
	            throw new IllegalArgumentException("File is too long:"+file.length+" max is:"+this.file.length);
	        Arrays.fill(this.file, (byte) 0);
	        System.arraycopy(file, 0, this.file, 0, file.length);
        } else {
            Arrays.fill(this.file, (byte) 0);
        }
    }
    
    /**
     * Returns the flags field.
     * 
     * @return the flags field.
     */
    public short getFlags() {
        return flags;
    }
    /**
     * Sets the flags field.
     * 
     * @param flags The flags field to set.
     */
    public void setFlags(short flags) {
        this.flags = flags;
    }
    /**
     * Returns the giaddr field (Relay agent IP address).
     * 
     * @return the giaddr field converted to <tt>InetAddress</tt> object.
     */
    public InetAddress getGiaddr() {
        try {
            return InetAddress.getByAddress(getGiaddrRaw());
        } catch (UnknownHostException e) {
			logger.log(Level.SEVERE, "Unexpected UnknownHostException", e);
            return null;	// normaly impossible
        }
    }
    
    /**
     * Returns the giaddr field (Relay agent IP address).
     * 
     * <p>This is the low-level maximum performance getter for this field.
     * 
     * @return Returns the giaddr as raw byte[4].
     */
    public byte[] getGiaddrRaw() {
        return (byte[]) giaddr.clone();
    }

    /**
     * Sets the giaddr field (Relay agent IP address).
     * 
     * <p>Ths <tt>giaddr</tt> field must be of <tt>Inet4Address</tt> class or
     * an <tt>IllegalArgumentException</tt> is thrown.
     * 
     * @param giaddr The giaddr to set.
     */
    public void setGiaddr(InetAddress giaddr) {
        if (!(giaddr instanceof Inet4Address))
            throw new IllegalArgumentException("Inet4Address required");
        setGiaddrRaw(giaddr.getAddress());
    }
    /**
     * Sets the giaddr field (Relay agent IP address).
     * 
     * @param giaddr The giaddr to set.
     * @throws UnknownHostException
     */
    public void setGiaddr(String giaddr) throws UnknownHostException {
        setGiaddr(InetAddress.getByName(giaddr));
    }
    /**
     * Sets the giaddr field (Relay agent IP address).
     * 
     * <p><tt>giaddr</tt> must be a 4 bytes array, or an <tt>IllegalArgumentException</tt>
     * is thrown.
     * 
     * <p>This is the low-level maximum performance setter for this field.
     * The array is internally copied so any further modification to <tt>ciaddr</tt>
     * parameter has no side effect.
     * 
     * @param giaddr The giaddr to set.
     */
    public void setGiaddrRaw(byte[] giaddr) {
        if (giaddr.length != 4)
            throw new IllegalArgumentException("4 bytes array required");
        System.arraycopy(giaddr, 0, this.giaddr, 0, 4);
    }
    /**
     * Returns the hlen field (Hardware address length).
     * 
     * <p>Typical value is 6 for ethernet - 6 bytes MAC address.
     * 
     * @return the hlen field.
     */
    public byte getHlen() {
        return hlen;
    }
    /**
     * Sets the hlen field (Hardware address length).
     * 
     * <p>Typical value is 6 for ethernet - 6 bytes MAC address.
     * 
     * <p>hlen value should be between 0 and 16, but no control is done here.
     * 
     * @param hlen The hlen to set.
     */
    public void setHlen(byte hlen) {
        this.hlen = hlen;
    }
    /**
     * Returns the hops field.
     * 
     * @return the hops field.
     */
    public byte getHops() {
        return hops;
    }
    /**
     * Sets the hops field.
     * 
     * @param hops The hops to set.
     */
    public void setHops(byte hops) {
        this.hops = hops;
    }
    /**
     * Returns the htype field (Hardware address length).
     * 
     * <p>Predefined values are:
     * <pre>
     * HTYPE_ETHER (1)
     * HTYPE_IEEE802 (6)
     * HTYPE_FDDI (8)
     * </pre>
     * 
     * <p>Typical value is <tt>HTYPE_ETHER</tt>.
     * 
     * @return the htype field.
     */
    public byte getHtype() {
        return htype;
    }
    /**
     * Sets the htype field (Hardware address length).
     * 
     * <p>Predefined values are:
     * <pre>
     * HTYPE_ETHER (1)
     * HTYPE_IEEE802 (6)
     * HTYPE_FDDI (8)
     * </pre>
     * 
     * <p>Typical value is <tt>HTYPE_ETHER</tt>.
     * 
     * @param htype The htype to set.
     */
    public void setHtype(byte htype) {
        this.htype = htype;
    }
    /**
     * Returns whether the packet is DHCP or BOOTP.
     * 
     * <p>It indicates the presence of the DHCP Magic Cookie at the end
     * of the BOOTP portion.
     * 
     * <p>Default is <tt>true</tt> for a freshly new object.
     * 
     * @return Returns the isDhcp.
     */
    public boolean isDhcp() {
        return isDhcp;
    }
    /**
     * Sets the isDhcp flag.
     * 
     * <p>Indicates whether to generate a DHCP or a BOOTP packet. If <tt>true</tt>
     * the DHCP Magic Cookie is added after the BOOTP portion and before the 
     * DHCP Options.
     * 
     * <p>If <tt>isDhcp</tt> if false, all DHCP options are ignored when calling
     * <tt>serialize()</tt>.
     * 
     * <p>Default value is <tt>true</tt>.
     * 
     * @param isDhcp The isDhcp to set.
     */
    public void setDhcp(boolean isDhcp) {
        this.isDhcp = isDhcp;
    }
    /**
     * Returns the op field (Message op code).
     * 
     * <p>Predefined values are:
     * <pre>
     * BOOTREQUEST (1)
     * BOOTREPLY (2)
     * </pre>
     * 
     * @return the op field.
     */
    public byte getOp() {
        return op;
    }
    /**
     * Sets the op field (Message op code).
     * 
     * <p>Predefined values are:
     * <pre>
     * BOOTREQUEST (1)
     * BOOTREPLY (2)
     * </pre>
     * 
     * <p>Default value is <tt>BOOTREPLY</tt>, suitable for server replies.
     * 
     * @param op The op to set.
     */
    public void setOp(byte op) {
        this.op = op;
    }
    /**
     * Returns the padding portion of the packet.
     * 
     * <p>This byte array follows the DHCP Options.
     * Normally, its content is irrelevant.
     * 
     * @return Returns the padding.
     */
    public byte[] getPadding() {
        return (byte[]) padding.clone();
    }
    /**
     * Sets the padding buffer.
     * 
     * <p>This byte array follows the DHCP Options.
     * Normally, its content is irrelevant.
     * 
     * <p>If <tt>paddig</tt> is null, it is set to an empty buffer.
     * 
     * <p>Padding is automatically added at the end of the datagram when calling
     * <tt>serialize()</tt> to match DHCP minimal packet size.
     * 
     * @param padding The padding to set.
     */
    public void setPadding(byte[] padding) {
        if (padding == null) {
            this.padding = new byte[0];
        } else {
            this.padding = (byte[]) padding.clone();
        }
    }
    /**
     * Sets the padding buffer with <tt>length</tt> zero bytes.
     * 
     * <p>This is a short cut for <tt>setPadding(new byte[length])</tt>.
     * 
     * @param length size of the padding buffer
     */
    public void setPaddingWithZeroes(int length) {
    	if (length < 0)
    		length = 0;
    	if (length > _DHCP_MAX_MTU)
    		throw new IllegalArgumentException("length is > "+_DHCP_MAX_MTU);
    	setPadding(new byte[length]);
    }
    /**
     * Returns the secs field (seconds elapsed).
     * 
     * @return the secs field.
     */
    public short getSecs() {
        return secs;
    }
    /**
     * Sets the secs field (seconds elapsed).
     * 
     * @param secs The secs to set.
     */
    public void setSecs(short secs) {
        this.secs = secs;
    }
    /**
     * Returns the siaddr field (IP address of next server).
     * 
     * @return the siaddr field converted to <tt>InetAddress</tt> object.
     */
    public InetAddress getSiaddr() {
        try {
            return InetAddress.getByAddress(getSiaddrRaw());
        } catch (UnknownHostException e) {
			logger.log(Level.SEVERE, "Unexpected UnknownHostException", e);
            return null;	// normaly impossible
        }
    }
    
    /**
     * Returns the siaddr field (IP address of next server).
     * 
     * <p>This is the low-level maximum performance getter for this field.
     * 
     * @return Returns the siaddr as raw byte[4].
     */
    public byte[] getSiaddrRaw() {
        return (byte[]) siaddr.clone();
    }

    /**
     * Sets the siaddr field (IP address of next server).
     * 
     * <p>Ths <tt>siaddr</tt> field must be of <tt>Inet4Address</tt> class or
     * an <tt>IllegalArgumentException</tt> is thrown.
     * 
     * @param siaddr The siaddr to set.
     */
    public void setSiaddr(InetAddress siaddr) {
        if (!(siaddr instanceof Inet4Address))
            throw new IllegalArgumentException("Inet4Address required");
        setSiaddrRaw(siaddr.getAddress());
    }
    /**
     * Sets the siaddr field (IP address of next server).
     * 
     * @param siaddr The siaddr to set.
     * @throws UnknownHostException
     */
    public void setSiaddr(String siaddr) throws UnknownHostException {
        setSiaddr(InetAddress.getByName(siaddr));
    }
    /**
     * Sets the siaddr field (IP address of next server).
     * 
     * <p><tt>siaddr</tt> must be a 4 bytes array, or an <tt>IllegalArgumentException</tt>
     * is thrown.
     * 
     * <p>This is the low-level maximum performance setter for this field.
     * The array is internally copied so any further modification to <tt>ciaddr</tt>
     * parameter has no side effect.
     * 
     * @param siaddr The siaddr to set.
     */
    public void setSiaddrRaw(byte[] siaddr) {
        if (siaddr.length != 4)
            throw new IllegalArgumentException("4 bytes array required");
        System.arraycopy(siaddr, 0, this.siaddr, 0, 4);
    }
    /**
     * Returns the sname field (Optional server host name).
     * 
     * <p>Returns the raw byte[64] buffer, containing a null terminated string.
     * 
     * <p>This is the low-level maximum performance getter for this field.
     * 
     * @return the sname field.
     */
    public byte[] getSnameRaw() {
        return (byte[]) sname.clone();
    }
    
    /**
     * Returns the sname field (Optional server host name) as String.
     * 
     * @return the sname converted to a String (transparent encoding).
     */
    public String getSname() {
        return bytesToString(getSnameRaw());
    }
    /**
     * Sets the sname field (Optional server host name) as String.
     * 
     * <p>The string is first converted to a byte[] array using transparent 
     * encoding. If the resulting buffer size is > 64, an <tt>IllegalArgumentException</tt>
     * is thrown.
     * 
     * <p>If <tt>sname</tt> parameter is null, the buffer is filled with zeros.
     * 
     * @param sname The sname field to set.
     * @throws IllegalArgumentException string too long
     */
    public void setSname(String sname) {
    	setSnameRaw(stringToBytes(sname));
    }
    /**
     * Sets the sname field (Optional server host name) as String.
     * 
     * <p>If the buffer size is > 64, an <tt>IllegalArgumentException</tt>
     * is thrown.
     * 
     * <p>If <tt>sname</tt> parameter is null, the buffer is filled with zeros.
     * 
     * <p>This is the low-level maximum performance setter for this field.
     * 
     * @param sname The sname field to set.
     * @throws IllegalArgumentException string too long
     */
    public void setSnameRaw(byte[] sname) {
        if (sname != null) {
            if (sname.length > this.sname.length)
                throw new IllegalArgumentException("Sname is too long:"+sname.length+" max is:"+this.sname.length);
            Arrays.fill(this.sname, (byte) 0);
            System.arraycopy(sname, 0, this.sname, 0, sname.length);
        } else {
            Arrays.fill(this.sname, (byte) 0);
        }
    }
    /**
     * Returns the xid field (Transaction ID).
     * 
     * @return Returns the xid.
     */
    public int getXid() {
        return xid;
    }
    /**
     * Sets the xid field (Transaction ID).
     * 
     * <p>This field is random generated by the client, and used by the client and
     * server to associate requests and responses for the same transaction.
     * 
     * @param xid The xid to set.
     */
    public void setXid(int xid) {
        this.xid = xid;
    }
    /**
     * Returns the yiaddr field ('your' IP address).
     * 
     * @return the yiaddr field converted to <tt>InetAddress</tt> object.
     */
    public InetAddress getYiaddr() {
        try {
            return InetAddress.getByAddress(getYiaddrRaw());
        } catch (UnknownHostException e) {
			logger.log(Level.SEVERE, "Unexpected UnknownHostException", e);
            return null;	// normaly impossible
        }
    }
    
    /**
     * Returns the yiaddr field ('your' IP address).
     * 
     * <p>This is the low-level maximum performance getter for this field.
     * 
     * @return Returns the yiaddr as raw byte[4].
     */
    public byte[] getYiaddrRaw() {
        return (byte[]) yiaddr.clone();
    }
    /**
     * Sets the yiaddr field ('your' IP address).
     * 
     * <p>Ths <tt>yiaddr</tt> field must be of <tt>Inet4Address</tt> class or
     * an <tt>IllegalArgumentException</tt> is thrown.
     * 
     * @param yiaddr The yiaddr to set.
     */
    public void setYiaddr(InetAddress yiaddr) {
        if (!(yiaddr instanceof Inet4Address))
            throw new IllegalArgumentException("Inet4Address required");
        setYiaddrRaw(yiaddr.getAddress());
    }
    /**
     * Sets the yiaddr field ('your' IP address).
     * 
     * @param yiaddr The yiaddr to set.
     * @throws UnknownHostException
     */
    public void setYiaddr(String yiaddr) throws UnknownHostException {
        setYiaddr(InetAddress.getByName(yiaddr));
    }
    /**
     * Sets the yiaddr field ('your' IP address).
     * 
     * <p><tt>yiaddr</tt> must be a 4 bytes array, or an <tt>IllegalArgumentException</tt>
     * is thrown.
     * 
     * <p>This is the low-level maximum performance setter for this field.
     * The array is internally copied so any further modification to <tt>ciaddr</tt>
     * parameter has no side effect.
     * 
     * @param yiaddr The yiaddr to set.
     */
    public void setYiaddrRaw(byte[] yiaddr) {
        if (yiaddr.length != 4)
            throw new IllegalArgumentException("4 bytes array required");
        System.arraycopy(yiaddr, 0, this.yiaddr, 0, 4);
    }
    /**
     * Return the DHCP Option Type.
     * 
     * <p>This is a short-cut for <tt>getOptionAsByte(DHO_DHCP_MESSAGE_TYPE)</tt>.
     * 
     * @return option type, of <tt>null</tt> if not present.
     */
    public Byte getDHCPMessageType() {
    	return getOptionAsByte(DHO_DHCP_MESSAGE_TYPE);
    }
    /**
     * Sets the DHCP Option Type.
     * 
     * <p>This is a short-cur for <tt>setOptionAsByte(DHO_DHCP_MESSAGE_TYPE, optionType);</tt>.
     * 
     * @param optionType
     */
    public void setDHCPMessageType(byte optionType) {
    	setOptionAsByte(DHO_DHCP_MESSAGE_TYPE, optionType);
    }
    /**
     * Indicates that the DHCP packet has been truncated and did not finished
     * with a 0xFF option.
     * 
     * <p>This field is read-only and can be <tt>true</tt> only with objects created
     * by parsing a Datagram - getPacket() methods.
     * 
     * <p>This field is cleared if the object is cloned.
     * 
     * @return the truncated field.
     */
    public boolean isTruncated() {
        return truncated;
    }
    
    /**
     * Returns a DHCP Option as Byte format.
     * 
     * This method is only allowed for the following option codes:
     * <pre>
	 * DHO_IP_FORWARDING(19)
	 * DHO_NON_LOCAL_SOURCE_ROUTING(20)
	 * DHO_DEFAULT_IP_TTL(23)
	 * DHO_ALL_SUBNETS_LOCAL(27)
	 * DHO_PERFORM_MASK_DISCOVERY(29)
	 * DHO_MASK_SUPPLIER(30)
	 * DHO_ROUTER_DISCOVERY(31)
	 * DHO_TRAILER_ENCAPSULATION(34)
	 * DHO_IEEE802_3_ENCAPSULATION(36)
	 * DHO_DEFAULT_TCP_TTL(37)
	 * DHO_TCP_KEEPALIVE_GARBAGE(39)
	 * DHO_NETBIOS_NODE_TYPE(46)
	 * DHO_DHCP_OPTION_OVERLOAD(52)
	 * DHO_DHCP_MESSAGE_TYPE(53)
	 * DHO_AUTO_CONFIGURE(116)
     * </pre>
     * 
     * @param code the option code.
     * @return the option value, <tt>null</tt> if option is not present.
     * @throws IllegalArgumentException the option code is not in the list above.
     * @throws DHCPBadPacketException the option value in packet is of wrong size.
     */
    public Byte getOptionAsByte(byte code) throws IllegalArgumentException {
    	DHCPOption opt = getOption(code);
    	return (opt != null) ? opt.getValueAsByte() : null;
    }
    /**
     * Returns a DHCP Option as Short format.
     * 
     * <p>This method is only allowed for the following option codes:
     * <pre>
	 * DHO_BOOT_SIZE(13)
	 * DHO_MAX_DGRAM_REASSEMBLY(22)
	 * DHO_INTERFACE_MTU(26)
	 * DHO_DHCP_MAX_MESSAGE_SIZE(57)
     * </pre>
     * 
     * @param code the option code.
     * @return the option value, <tt>null</tt> if option is not present.
     * @throws IllegalArgumentException the option code is not in the list above.
     * @throws DHCPBadPacketException the option value in packet is of wrong size.
     */
    public Short getOptionAsShort(byte code) throws IllegalArgumentException {
    	DHCPOption opt = getOption(code);
    	return (opt != null) ? opt.getValueAsShort() : null;
    }
    /**
     * Returns a DHCP Option as Integer format.
     * 
     * <p>This method is only allowed for the following option codes:
     * <pre>
	 * DHO_TIME_OFFSET(2)
	 * DHO_PATH_MTU_AGING_TIMEOUT(24)
	 * DHO_ARP_CACHE_TIMEOUT(35)
	 * DHO_TCP_KEEPALIVE_INTERVAL(38)
	 * DHO_DHCP_LEASE_TIME(51)
	 * DHO_DHCP_RENEWAL_TIME(58)
	 * DHO_DHCP_REBINDING_TIME(59)
     * </pre>
     * 
     * @param code the option code.
     * @return the option value, <tt>null</tt> if option is not present.
     * @throws IllegalArgumentException the option code is not in the list above.
     * @throws DHCPBadPacketException the option value in packet is of wrong size.
     */
    public Integer getOptionAsInteger(byte code) throws IllegalArgumentException {
    	DHCPOption opt = getOption(code);
    	return (opt != null) ? opt.getValueAsInt() : null;
    }
    /**
     * Returns a DHCP Option as InetAddress format.
     * 
     * <p>This method is only allowed for the following option codes:
     * <pre>
	 * DHO_SUBNET_MASK(1)
	 * DHO_SWAP_SERVER(16)
	 * DHO_BROADCAST_ADDRESS(28)
	 * DHO_ROUTER_SOLICITATION_ADDRESS(32)
	 * DHO_DHCP_REQUESTED_ADDRESS(50)
	 * DHO_DHCP_SERVER_IDENTIFIER(54)
	 * DHO_SUBNET_SELECTION(118)
     * </pre>
     * 
     * @param code the option code.
     * @return the option value, <tt>null</tt> if option is not present.
     * @throws IllegalArgumentException the option code is not in the list above.
     * @throws DHCPBadPacketException the option value in packet is of wrong size.
     */
    public InetAddress getOptionAsInetAddr(byte code) throws IllegalArgumentException {
    	DHCPOption opt = getOption(code);
    	return (opt != null) ? opt.getValueAsInetAddr() : null;
    }
    /**
     * Returns a DHCP Option as String format.
     * 
     * <p>This method is only allowed for the following option codes:
     * <pre>
	 * DHO_HOST_NAME(12)
	 * DHO_MERIT_DUMP(14)
	 * DHO_DOMAIN_NAME(15)
	 * DHO_ROOT_PATH(17)
	 * DHO_EXTENSIONS_PATH(18)
	 * DHO_NETBIOS_SCOPE(47)
	 * DHO_DHCP_MESSAGE(56)
	 * DHO_VENDOR_CLASS_IDENTIFIER(60)
	 * DHO_NWIP_DOMAIN_NAME(62)
	 * DHO_NIS_DOMAIN(64)
	 * DHO_NIS_SERVER(65)
	 * DHO_TFTP_SERVER(66)
	 * DHO_BOOTFILE(67)
	 * DHO_NDS_TREE_NAME(86)
	 * DHO_USER_AUTHENTICATION_PROTOCOL(98)
     * </pre>
     * 
     * @param code the option code.
     * @return the option value, <tt>null</tt> if option is not present.
     * @throws IllegalArgumentException the option code is not in the list above.
     */
    public String getOptionAsString(byte code) throws IllegalArgumentException {
    	DHCPOption opt = getOption(code);
    	return (opt != null) ? opt.getValueAsString() : null;
    }
    /**
     * Returns a DHCP Option as Short array format.
     * 
     * <p>This method is only allowed for the following option codes:
     * <pre>
	 * DHO_PATH_MTU_PLATEAU_TABLE(25)
	 * DHO_NAME_SERVICE_SEARCH(117)
     * </pre>
     * 
     * @param code the option code.
     * @return the option value array, <tt>null</tt> if option is not present.
     * @throws IllegalArgumentException the option code is not in the list above.
     * @throws DHCPBadPacketException the option value in packet is of wrong size.
     */
    public short[] getOptionAsShorts(byte code) throws IllegalArgumentException {
    	DHCPOption opt = getOption(code);
    	return (opt != null) ? opt.getValueAsShorts() : null;
    }
    /**
     * Returns a DHCP Option as InetAddress array format.
     * 
     * <p>This method is only allowed for the following option codes:
     * <pre>
	 * DHO_ROUTERS(3)
	 * DHO_TIME_SERVERS(4)
	 * DHO_NAME_SERVERS(5)
	 * DHO_DOMAIN_NAME_SERVERS(6)
	 * DHO_LOG_SERVERS(7)
	 * DHO_COOKIE_SERVERS(8)
	 * DHO_LPR_SERVERS(9)
	 * DHO_IMPRESS_SERVERS(10)
	 * DHO_RESOURCE_LOCATION_SERVERS(11)
	 * DHO_POLICY_FILTER(21)
	 * DHO_STATIC_ROUTES(33)
	 * DHO_NIS_SERVERS(41)
	 * DHO_NTP_SERVERS(42)
	 * DHO_NETBIOS_NAME_SERVERS(44)
	 * DHO_NETBIOS_DD_SERVER(45)
	 * DHO_FONT_SERVERS(48)
	 * DHO_X_DISPLAY_MANAGER(49)
	 * DHO_MOBILE_IP_HOME_AGENT(68)
	 * DHO_SMTP_SERVER(69)
	 * DHO_POP3_SERVER(70)
	 * DHO_NNTP_SERVER(71)
	 * DHO_WWW_SERVER(72)
	 * DHO_FINGER_SERVER(73)
	 * DHO_IRC_SERVER(74)
	 * DHO_STREETTALK_SERVER(75)
	 * DHO_STDA_SERVER(76)
	 * DHO_NDS_SERVERS(85)
     * </pre>
     * 
     * @param code the option code.
     * @return the option value array, <tt>null</tt> if option is not present.
     * @throws IllegalArgumentException the option code is not in the list above.
     * @throws DHCPBadPacketException the option value in packet is of wrong size.
     */
    public InetAddress[] getOptionAsInetAddrs(byte code) throws IllegalArgumentException {
    	DHCPOption opt = getOption(code);
    	return (opt != null) ? opt.getValueAsInetAddrs() : null;
    }
    /**
     * Returns a DHCP Option as Byte array format.
     * 
     * <p>This method is only allowed for the following option codes:
     * <pre>
     * DHO_DHCP_PARAMETER_REQUEST_LIST(55)
     * </pre>
     * 
     * <p>Note: this mehtod is similar to getOptionRaw, only with option type checking.
     * 
     * @param code the option code.
     * @return the option value array, <tt>null</tt> if option is not present.
     * @throws IllegalArgumentException the option code is not in the list above.
     */
    public byte[] getOptionAsBytes(byte code) throws IllegalArgumentException {
    	DHCPOption opt = getOption(code);
    	return (opt != null) ? opt.getValueAsBytes() : null;
    }


    /**
     * Sets a DHCP Option as Byte format.
     * 
     * <p>See <tt>DHCPOption</tt> for allowed option codes.
     * 
     * @param code the option code.
     * @param val the value
     * @throws IllegalArgumentException the option code is not in the list above.
     */
    public void setOptionAsByte(byte code, byte val) {
    	setOption(DHCPOption.newOptionAsByte(code, val));
    }
    /**
     * Sets a DHCP Option as Short format.
     * 
     * <p>See <tt>DHCPOption</tt> for allowed option codes.
     * 
     * @param code the option code.
     * @param val the value
     * @throws IllegalArgumentException the option code is not in the list above.
     */
    public void setOptionAsShort(byte code, short val) {
    	setOption(DHCPOption.newOptionAsShort(code, val));
    }
    /**
     * Sets a DHCP Option as Integer format.
     * 
     * <p>See <tt>DHCPOption</tt> for allowed option codes.
     * 
     * @param code the option code.
     * @param val the value
     * @throws IllegalArgumentException the option code is not in the list above.
     */
    public void setOptionAsInt(byte code, int val) {
    	setOption(DHCPOption.newOptionAsInt(code, val));
    }
    /**
     * Sets a DHCP Option as InetAddress format.
     * 
     * <p>See <tt>DHCPOption</tt> for allowed option codes.
     * 
     * @param code the option code.
     * @param val the value
     * @throws IllegalArgumentException the option code is not in the list above.
     */
    public void setOptionAsInetAddress(byte code, InetAddress val) {
    	setOption(DHCPOption.newOptionAsInetAddress(code, val));
    }
    /**
     * Sets a DHCP Option as InetAddress format.
     * 
     * <p>See <tt>DHCPOption</tt> for allowed option codes.
     * 
     * @param code the option code in String format.
     * @param val the value
     * @throws UnknownHostException cannot find the address
     * @throws IllegalArgumentException the option code is not in the list above.
     */
    public void setOptionAsInetAddress(byte code, String val) throws UnknownHostException {
    	setOption(DHCPOption.newOptionAsInetAddress(code, InetAddress.getByName(val)));
    }
    /**
     * Sets a DHCP Option as InetAddress array format.
     * 
     * <p>See <tt>DHCPOption</tt> for allowed option codes.
     * 
     * @param code the option code.
     * @param val the value array
     * @throws IllegalArgumentException the option code is not in the list above.
     */
    public void setOptionAsInetAddresses(byte code, InetAddress[] val) {
    	setOption(DHCPOption.newOptionAsInetAddresses(code, val));
    }
    /**
     * Sets a DHCP Option as String format.
     * 
     * <p>See <tt>DHCPOption</tt> for allowed option codes.
     * 
     * @param code the option code.
     * @param val the value
     * @throws IllegalArgumentException the option code is not in the list above.
     */
    public void setOptionAsString(byte code, String val) {
    	setOption(DHCPOption.newOptionAsString(code, val));
    }
    /**
     * Returns the option as raw byte[] buffer.
     * 
     * <p>This is the low-level maximum performance getter for options.
     * 
     * @param code option code
     * @return Returns the option as raw <tt>byte[]</tt>, or <tt>null</tt> if
     * the option is not present.
     */
    public byte[] getOptionRaw(byte code) {
        DHCPOption opt = getOption(code);
        if (opt != null)
            return opt.getValue();
        else
            return null;
    }
    /**
     * Returns the option as DHCPOption object.
     * 
     * <p>This is the low-level maximum performance getter for options.
     * This method is used by every option getter in this object.
     * 
     * @param code option code
     * @return Returns the option as <tt>DHCPOption</tt>, or <tt>null</tt> if
     * the option is not present.
     */
    public DHCPOption getOption(byte code) {
    	DHCPOption opt = options.get(code);
    	// Sanity checks
    	if (opt == null) return null;
    	assert(opt.getCode() == code);
    	assert(opt.getValueFast() != null);
        return opt;
    }
    /**
     * Tests whether an option code is present in the packet.
     * 
     * @param code DHCP option code
     * @return true if option is present
     */
    public boolean containsOption(byte code) {
    	return options.containsKey(code);
    }
    /**
     * Return an ordered list of all options.
     * 
     * <p>The Collection is read-only.
     * 
     * @return collection of <tt>DHCPOption</tt>.
     */
    public Collection<DHCPOption> getAllOptions() {
    	return Collections.unmodifiableCollection(options.values());	// read only
    }
    /**
     * Sets the option specified for the option.
     * 
     * <p>If <tt>buf</tt> is <tt>null</tt>, the option is cleared.
     * 
     * <p>Options are sorted in creation order. Previous values are replaced.
     * 
     * <p>This is the low-level maximum performance setter for options.
     * 
     * @param	code opt	option code, use <tt>DHO_*</tt> for predefined values.
     * @param	buf	raw buffer value (cloned). If null, the option is removed.
     */
    public void setOptionRaw(byte code, byte buf[]) {
        if (buf == null)		// clear parameter
            removeOption(code);
        else {
            setOption(new DHCPOption(code, buf));	// exception here if code=0 or code=-1
        }
    }
    /**
     * Sets the option specified for the option.
     * 
     * <p>If <tt>buf</tt> is <tt>null</tt>, the option is cleared.
     * 
     * <p>Options are sorted in creation order. Previous values are replaced, but their
     * previous position is retained.
     * 
     * <p>This is the low-level maximum performance setter for options.
     * This method is called by all setter methods in this class.
     * 
     * @param	opt	option code, use <tt>DHO_*</tt> for predefined values.
     */
    public void setOption(DHCPOption opt) {
    	if (opt == null) return;
    	if (opt.getValueFast() != null) {
    		options.put(opt.getCode(), opt);
    	} else {
    		removeOption(opt.getCode());
    	}
    }
    /**
     * Sets an array of options. Calles repeatedly setOption on each element of the array.
     * 
     * @param	opts	array of options.
     */
    public void setOptions(DHCPOption[] opts) {
    	if (opts == null) return;
    	for (DHCPOption opt : opts)
    		setOption(opt);
    }
    /**
     * Sets a Collection of options. Calles repeatedly setOption on each element of the List.
     * 
     * @param	opts	List of options.
     */
    public void setOptions(Collection<DHCPOption> opts) {
    	if (opts == null) return;
    	for (DHCPOption opt : opts)
    		setOption(opt);
    }
    /**
     * Remove this option from the options list.
     * 
     * @param opt the option code to remove.
     */
    public void removeOption(byte opt) {
        options.remove(opt);
    }
    /**
     * Remove all options.
     */
    public void removeAllOptions() {
    	options.clear();
    }
    /**
     * Converts LinkedList of String to DHO_USER_CLASS (77) option.
     * 
     * @param list List of String
     * @return byte[] buffer to use with <tt>setOptionRaw</tt>, <tt>null</tt> if list is null
     * @throws IllegalArgumentException if List contains anything else than String
     */
    public static byte[] stringListToUserClass(List<String> list) {
    	if (list == null) return null;
        ByteArrayOutputStream buf = new ByteArrayOutputStream(32);
        DataOutputStream out = new DataOutputStream(buf);
        try {
	        for (String s : list) {
	        	byte[] bytes = stringToBytes(s);
	        	int size = bytes.length;
	        	if (size > 255) size = 255;
	        	out.writeByte(size);
	        	out.write(bytes, 0, size);
	        }
	        return buf.toByteArray();
	    } catch (IOException e) {
			logger.log(Level.SEVERE, "Unexpected IOException", e);
	        return buf.toByteArray();
	    }
    }
    /**
     * Returns the IP address of the machine to which this datagram is being sent 
     * or from which the datagram was received.
     * 
     * @return the IP address of the machine to which this datagram is being sent
     * or from which the datagram was received. <tt>null</tt> if no address.
     */
    public InetAddress getAddress() {
        return address;
    }
    /**
     * Sets the IP address of the machine to which this datagram is being sent.
     * 
     * @param address the <tt>InetAddress</tt>.
     * @throws IllegalArgumentException address is not of <tt>Inet4Address</tt> class.
     */
    public void setAddress(InetAddress address) {
        if ((address != null) && !(address instanceof Inet4Address))
            throw new IllegalArgumentException("only IPv4 addresses accepted");
        this.address = address;
    }
    /**
     * Returns the port number on the remote host to which this datagram is being sent 
     * or from which the datagram was received.
     * 
     * @return the port number on the remote host to which this datagram is being sent 
     * or from which the datagram was received.
     */
    public int getPort() {
        return port;
    }
    /**
     * Sets the port number on the remote host to which this datagram is being sent.
     * 
     * @param port the port number.
     */
    public void setPort(int port) {
        this.port = port;
    }

    // ========================================================================
    // utility functions
    
    /* (non javadoc)
     * 
     * Converts a null terminated byte[] string to a String object,
     * with a transparent conversion.
     * 
     * Faster version than String.getBytes()
     */
    static final String bytesToString(byte buf[]) {
    	if (buf == null) return "";
    	return bytesToString(buf, 0, buf.length);
    }
    static final String bytesToString(byte buf[], int src, int len) {
    	if (buf == null) return "";
    	if (src < 0) {
    		len += src;	// reduce length
    		src = 0;
    	}
    	if (len <= 0) return "";
    	if (src >= buf.length) return "";
    	if (src + len > buf.length) len = buf.length - src;
        // string should be null terminated or whole buffer
    	// first find the real lentgh
    	for (int i=src; i<src+len; i++) {
    		if (buf[i] == 0) {
    			len = i - src;
    			break;
    		}
    	}
    	
        char[] chars = new char[len];
        for (int i=src; i<src+len; i++)
        	chars[i-src] = (char) buf[i];
        return String.valueOf(chars);
    }
    
    /* (non-Javadoc)
     * Convert byte to hex string (2 chars) (uppercase)
     */
    static final private char[] hex = { '0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F' }; 
    static final private void appendHex(StringBuffer sbuf, byte b) {
        int i = (b & 0xFF);
        sbuf.append(hex[(i & 0xF0) >> 4]).append(hex[i & 0x0F]);
    }

    /* (non-Javadoc)
     * Convert a byte[] to hex string (uppercase), limited to <tt>len</tt> bytes
     */
    static final String toHex(final byte buf[], int src, int len) {
        if (buf==null) return "";
    	if (src < 0) {
    		len += src;	// reduce length
    		src = 0;
    	}
    	if (len <= 0) return "";
    	if (src >= buf.length) return "";
    	if (src + len > buf.length) len = buf.length - src;
        StringBuffer sbuf = new StringBuffer(len*2);
        for (int i=src; i<src+len; i++)
        	appendHex(sbuf, buf[i]);
        return sbuf.toString();
    }
    
    /* (non-Javadoc)
     * Convert plain byte[] to hex string (uppercase)
     */
    static final String toHex(final byte buf[]) {
        return toHex(buf, 0, buf.length);
    }

    /* (non-Javadoc)
     * Convert integer to hex string (uppercase)
     */
    static private final String toHex(int i) {
        StringBuffer s = new StringBuffer();
        appendHex(s, (byte)((i & 0xff000000) >>> 24));
        appendHex(s, (byte)((i & 0x00ff0000) >>> 16));
        appendHex(s, (byte)((i & 0x0000ff00) >>>  8));
        appendHex(s, (byte)((i & 0x000000ff)       ));
        return s.toString();
    }
    
    static final byte[] stringToBytes(String str) {
    	if (str == null) return null;
    	char[] chars = str.toCharArray();
    	int len = chars.length;
    	byte[] buf = new byte[len];
    	for (int i=0; i<len; i++)
    		buf[i] = (byte) chars[i];
    	return buf;
    }
    /**
     * Faster version than <tt>InetAddress.getHostAddres()</tt>.
     * 
     * @return String representation of address.
     */
    public static final String getHostAddress(InetAddress addr) {
    	if (addr == null)
    		throw new IllegalArgumentException("addr must not be null");
    	if (!(addr instanceof Inet4Address))
    		throw new IllegalArgumentException("addr must be an instance of Inet4Address");
    	byte[] src = addr.getAddress();
    	StringBuffer sbuf = new StringBuffer(15);
    	sbuf.append(src[0] & 0xFF).append(".").append(src[1] & 0xFF).append(".");
    	sbuf.append(src[2] & 0xFF).append(".").append(src[3] & 0xFF);
    	return sbuf.toString();
    }
    
}
