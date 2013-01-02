package com.nc.comm;

import java.util.LinkedList;
import java.util.List;



public class UITreeNode {
	protected String text;
	protected String icon;
	protected String id;
	protected Boolean checked;
	protected Boolean allowChildren;
	protected Boolean disabled;
	protected Boolean expandable;
	protected Boolean expanded;
	protected String href;
	protected String parentId;
	protected String hrefTarget;
	protected Boolean leaf;
	protected String qtip;
	protected Boolean singleClickExpand;
	protected String cls;
	protected String iconCls;
    private String moduleType;
	
	protected Boolean cascade;

	
	private UITreeNode parent;
	
	private List<UITreeNode> children;
	
	public List<UITreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<UITreeNode> children) {
		this.children = children;
	}
	
	public void add(UITreeNode node) {
		node.setParent(this);
		if (this.children == null)
			this.children = new LinkedList<UITreeNode>();
		this.children.add(node);
	}
	

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setExpandable(java.lang.Boolean value) {
		this.expandable = value;
	}

	public void setExpanded(java.lang.Boolean value) {
		this.expanded = value;
	}

	public java.lang.Boolean getExpandable() {
		if (this.expandable != null) {
			return this.expandable;
		}
		return null;
	}

	public java.lang.Boolean getExpanded() {
		if (this.expanded != null) {
			return this.expanded;
		}
		return null;
	}

	public java.lang.String getHref() {
		if (this.href != null) {
			return this.href;
		}

		return null;
	}

	public void setHref(java.lang.String value) {
		this.href = value;
	}

	public java.lang.String getHrefTarget() {
		if (this.hrefTarget != null) {
			return this.hrefTarget;
		}

		return null;
	}

	public void setHrefTarget(java.lang.String value) {
		this.hrefTarget = value;
	}

	public java.lang.Boolean getLeaf() {
		if (this.leaf != null) {
			return this.leaf;
		}
		return null;
	}

	public void setLeaf(java.lang.Boolean value) {
		this.leaf = value;
	}

	public java.lang.String getQtip() {
		if (this.qtip != null) {
			return this.qtip;
		}

		return null;
	}

	public void setQtip(java.lang.String value) {
		this.qtip = value;
	}

	public java.lang.String getCls() {
		if (this.cls != null) {
			return this.cls;
		}

		return null;
	}

	public void setCls(java.lang.String value) {
		this.cls = value;
	}

	public java.lang.String getIconCls() {
		if (this.iconCls != null) {
			return this.iconCls;
		}

		return null;
	}

	public void setIconCls(java.lang.String value) {
		this.iconCls = value;
	}

	public java.lang.Boolean getCascade() {
		if (this.cascade != null) {
			return this.cascade;
		}

		return null;
	}

	public void setCascade(java.lang.Boolean value) {
		this.cascade = value;
	}

	public java.lang.Boolean getChecked() {
		if (this.checked != null) {
			return this.checked;
		}

		return null;
	}

	public void setChecked(java.lang.Boolean value) {
		this.checked = value;
	}


	public UITreeNode getParent() {
		return parent;
	}


	public void setParent(UITreeNode parent) {
		this.parent = parent;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getModuleType() {
		return moduleType;
	}

	public void setModuleType(String moduleType) {
		this.moduleType = moduleType;
	}

}
