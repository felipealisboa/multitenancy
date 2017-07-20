package org.springframework.cloud.multitenancy.core.properties;

/**
 * Time the library waits to query the database list again
 * 
 * @author WRP
 * */
public class CatalogConfigProperties {
	
	private Integer refresh;

	public Integer getRefresh() {
		return refresh;
	}

	public void setRefresh(Integer refresh) {
		this.refresh = refresh;
	}	
}
