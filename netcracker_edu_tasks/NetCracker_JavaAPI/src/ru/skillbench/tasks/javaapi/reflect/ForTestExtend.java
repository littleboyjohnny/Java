package ru.skillbench.tasks.javaapi.reflect;

public class ForTestExtend extends ForTest {
	
	private String field3 = null;
	protected String p2 = null;
	
	public String hidden = null;
	
	public String getF3() {
		return field3;
	}
	
	private void privateMethod() {
		return;
	}
	
	public Integer sameMethod() {
		return null;
	}

}
