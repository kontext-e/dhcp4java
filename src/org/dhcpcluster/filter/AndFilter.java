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
package org.dhcpcluster.filter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.dhcp4java.DHCPPacket;

public final class AndFilter implements RequestFilter {
	
	private final List<RequestFilter> filters;
	
	public AndFilter() {
		this.filters = new ArrayList<RequestFilter>();
	}
	
	public AndFilter(RequestFilter[] filters) {
		if (filters == null) {
			throw new NullPointerException("filters must not be null");
		}
		this.filters = new ArrayList<RequestFilter>(filters.length);
		for (RequestFilter element : filters) {
			this.filters.add(element);
		}
	}
	
	public AndFilter(List<RequestFilter> filters) {
		if (filters == null) {
			throw new NullPointerException("filters must not be null");
		}
		this.filters = new ArrayList<RequestFilter>(filters.size());
		for (RequestFilter element : filters) {
			this.filters.add(element);
		}
	}

	/* (non-Javadoc)
	 * @see org.dhcpcluster.filter.RequestFilter#filter(org.dhcp4java.DHCPPacket)
	 */
	public boolean isRequestAccepted(DHCPPacket request) {
		for (RequestFilter filter : this.filters) {
			if ((filter != null) && (!filter.isRequestAccepted(request))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * @return Returns the filters.
	 */
	public List<RequestFilter> getFilters() {
		return filters;
	}

}
