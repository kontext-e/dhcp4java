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
package org.dhcpcluster.struct;

import java.io.Serializable;
import org.dhcp4java.DHCPOption;
import org.dhcp4java.DHCPPacket;
import org.dhcpcluster.filter.AlwaysTrueFilter;
import org.dhcpcluster.filter.RequestFilter;

/**
 * 
 * @author Stephan Hadinger
 * @version 0.71
 */
public class NodeRoot implements Serializable {

    private static final long serialVersionUID = 2L;
    /** freely usable comment */
    protected String comment = null;
    
    /** filter applicable to Subnet */
    protected RequestFilter				requestFilter = ALWAYS_TRUE_FILTER;
    
    /** array of dhcp options */
    protected DHCPOption[]					dhcpOptions = DHCPOPTION_0;
    
    private int							defaultLease = 86400;
    private int							maxLease = 86400;

    public NodeRoot() {
    }
    
    public void applyOptions(DHCPPacket request, DHCPPacket response) {
    	for (DHCPOption opt : dhcpOptions) {
    		response.setOption(opt.applyOption(request));
    	}
    }
    
    
    
    protected static final DHCPOption[] DHCPOPTION_0 = new DHCPOption[0];
    protected static final RequestFilter ALWAYS_TRUE_FILTER = new AlwaysTrueFilter();

	/**
	 * @return Returns the comment.
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @param comment The comment to set.
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * @return Returns the dhcpOptions.
	 */
	public DHCPOption[] getDhcpOptions() {
		return dhcpOptions;
	}

	/**
	 * @param dhcpOptions The dhcpOptions to set.
	 */
	public void setDhcpOptions(DHCPOption[] dhcpOptions) {
		this.dhcpOptions = dhcpOptions;
	}

	/**
	 * @return Returns the requestFilter.
	 */
	public RequestFilter getRequestFilter() {
		return requestFilter;
	}

	/**
	 * @param requestFilter The requestFilter to set.
	 */
	public void setRequestFilter(RequestFilter requestFilter) {
		this.requestFilter = requestFilter;
	}

	/**
	 * @return Returns the defaultLease.
	 */
	public int getDefaultLease() {
		return defaultLease;
	}

	/**
	 * @param defaultLease The defaultLease to set.
	 */
	public void setDefaultLease(int defaultLease) {
		this.defaultLease = defaultLease;
	}

	/**
	 * @return Returns the maxLease.
	 */
	public int getMaxLease() {
		return maxLease;
	}

	/**
	 * @param maxLease The maxLease to set.
	 */
	public void setMaxLease(int maxLease) {
		this.maxLease = maxLease;
	}
	
}
