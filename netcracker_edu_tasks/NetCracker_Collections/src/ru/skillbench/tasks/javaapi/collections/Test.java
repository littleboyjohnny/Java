package ru.skillbench.tasks.javaapi.collections;

import java.util.Iterator;

public class Test {

	public static void main(String[] args) {
		/*
		TreeNode root = new TreeNodeImpl();
		root.setData("root");
		TreeNode ch1 = new TreeNodeImpl();
		ch1.setData("ch1");
		TreeNode ch2 = new TreeNodeImpl();
		ch2.setData("ch2");
		TreeNode leaf11 = new TreeNodeImpl();
		leaf11.setData("leaf11");
		TreeNode leaf12 = new TreeNodeImpl();
		leaf12.setData("leaf12");
		TreeNode leaf21 = new TreeNodeImpl();
		leaf21.setData("leaf21");
		ch1.addChild(leaf11);
		ch1.addChild(leaf12);
		ch2.addChild(leaf21);
		root.addChild(ch1);
		root.addChild(ch2);
		
		System.out.println(leaf11.getTreePath());
		//System.out.println(root.getParent());
		System.out.println(root.getRoot());
		*/
		String n = null;
		StringFilter sf = new StringFilterImpl();
		sf.add("abcdefg");
		sf.add("aaaaaa");
		sf.add("bbbbb");
		sf.add(n);
		sf.add("1 000");
		sf.add("-9.56");
		Iterator<String> i = sf.getStringsStartingWith(null);
		for (;i.hasNext(); ) {
			System.out.println(i.next());
		}
	}

}
