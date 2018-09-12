package ru.skillbench.tasks.javaapi.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.ParseException;

import ru.skillbench.tasks.javaapi.reflect.Reflector.SampleNumber;

public class Checker {

	/**
	 * @param args
	 * @throws ParseException 
	 */
	public static void main(String[] args) throws ParseException {
		/*
		Reflector r = new ReflectorImpl();
		//Number sn = 6;
		//sn.setValue("255");
		r.setClass(ForTestExtend.class);
		//r.getMethodNames().forEach(s->System.out.println(s));
		r.getAllDeclaredFields().forEach(s->System.out.println(s));
		*/
		ForTest f = new ForTest();
		Class<?> clazz = f.getClass();
		Field[] fields = clazz.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			System.out.println(fields[i].getName());
		}
		//Class<? extends Object[]> mas1 = mas.getClass();
		//for (int i = 0; i < mas.length; i++) {
			//System.out.println(mas[i].getName());
		//}
	}

}
