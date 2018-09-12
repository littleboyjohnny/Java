package ru.skillbench.tasks.javaapi.collections;

import java.util.Iterator;
import java.util.ArrayList;

public class TreeNodeImpl implements TreeNode {
	
	private TreeNode parent = null;
	private ArrayList<TreeNode> children = null;
	private boolean expanded = false;
	private Object data = null;

	@Override
	public TreeNode getParent() {
		return parent;
	}

	@Override
	public void setParent(TreeNode parent) {
		this.parent = parent;
	}

	@Override
	public TreeNode getRoot() {
		if (parent == null) {
			return null;
		} else {
			TreeNode obj = parent;
			while (obj.getParent() != null) {
				obj = obj.getParent();
			}
			return obj;
		}
	}

	@Override
	public boolean isLeaf() {
		return children == null || children.size() == 0;
	}

	@Override
	public int getChildCount() {
		if (children == null)
			return 0;
		return children.size();
	}

	@Override
	public Iterator<TreeNode> getChildrenIterator() {
		if (children == null)
			return null;
		return children.iterator();
	}

	@Override
	public void addChild(TreeNode child) {
		if (children == null)
			children = new ArrayList<TreeNode>();
		child.setParent(this);
		this.children.add(child);
	}

	@Override
	public boolean removeChild(TreeNode child) {
		if (children == null)
			return false;
		int index = children.indexOf(child);
		if (index == -1) {
			return false;
		}
		children.remove(index).setParent(null);
		return true;
	}

	@Override
	public boolean isExpanded() {
		return expanded;
	}

	@Override
	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
		if (children != null) {
			for (Iterator<TreeNode> i = children.iterator(); i.hasNext();) {
				i.next().setExpanded(expanded);
			}
		}
	}

	@Override
	public Object getData() {
		return data;
	}

	@Override
	public void setData(Object data) {
		this.data = data;

	}

	@Override
	public String getTreePath() {
		if (parent == null)
			return data.toString();
		if (data == null)
			return parent.getTreePath() + "->" + "empty";
		else
			return parent.getTreePath() + "->" + data.toString();
	}

	@Override
	public TreeNode findParent(Object data) {
		TreeNode obj = this;
		if (data == null) {
			while (obj != null && obj.getData() != null) {
				obj = obj.getParent();
			}
		} else {
			while (obj != null) {
				obj = obj.getParent();
				if (obj.getData() != null && obj.getData().equals(data)) {
					break;
				}
			}
		}
		return obj;
	}

	@Override
	public TreeNode findChild(Object data) {
		if (children == null)
			return null;
		TreeNode result = null;
		if (data == null) {
			for (Iterator<TreeNode> i = children.iterator(); i.hasNext();) {
				TreeNode obj = i.next();
				if (obj.getData() == null)
					return obj;
				else {
					result = obj.findChild(data);
					if (obj.getData() == null)
						return result; 
				}
			}
		} else {
			for (Iterator<TreeNode> i = children.iterator(); i.hasNext();) {
				TreeNode obj = i.next();
				if (obj.getData() != null && obj.getData().equals(data))
					return obj;
				else {
					result = obj.findChild(data);
					if (obj.getData() != null && obj.getData().equals(data))
						return result; 
				}
			}
		}
		return result;
	}

}
