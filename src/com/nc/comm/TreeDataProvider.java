/**
 * Tree数据源提供操口
 */
package com.nc.comm;

public interface TreeDataProvider {
	
	public Object[] getChildren(Object userData)throws Exception ;

	public String getHref(Object userData)throws Exception;

	public String getHrefTarget(Object userData)throws Exception ;

	public String getIcon(Object userData) throws Exception;
	
	

	public String getText(Object userData) throws Exception;
	
	public boolean isLeaf(Object userData)throws Exception;

	public boolean isCascade(Object userData) throws Exception;

	public Boolean isChecked(Object userData) throws Exception;

	public boolean isExpanded(Object userData)throws Exception;

}
