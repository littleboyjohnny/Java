package ru.skillbench.tasks.javaapi.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Stream;

public class ReflectorImpl implements Reflector {
	
	private Class<?> clazz = null;

	@Override
	public void setClass(Class<?> clazz) {
		this.clazz = clazz;
	}

	@Override
	public Stream<String> getMethodNames(Class<?>... paramTypes) throws NullPointerException {
		ArrayList<String> result = new ArrayList<String> ();
		HashMap<String, Class<?>[]> localMapParamTypes = new HashMap<>();
		HashMap<String, Class<?>> localMapReturn = new HashMap<>();
		
		Method[] methods = clazz.getMethods();
		for (Method met: methods) {
			Class<?>[] parameterTypes = met.getParameterTypes();
			if (Arrays.equals(parameterTypes, paramTypes)) {
				String name = met.getName();
				localMapParamTypes.put(name, parameterTypes);
				localMapReturn.put(name, met.getReturnType());
				result.add(name);
			}
		}
		
		Class<?> superClazz = clazz.getSuperclass();
		while (superClazz != null) {
			methods = superClazz.getMethods();
			for (Method met: methods) {
				Class<?>[] parameterTypes = met.getParameterTypes();
				if (Arrays.equals(parameterTypes, paramTypes) && (!localMapReturn.containsKey(met.getName())
						|| !met.getReturnType().equals(localMapReturn.get(met.getName())))) {
					String name = met.getName();
					localMapParamTypes.put(name, parameterTypes);
					localMapReturn.put(name, met.getReturnType());
					result.add(name);
				}
			}
			superClazz = superClazz.getSuperclass();
		}
		return result.stream();
	}

	@Override
	public Stream<Field> getAllDeclaredFields() throws NullPointerException {
		ArrayList<Field> result = new ArrayList<>();
		Field[] fields;
		fields = clazz.getFields();
		for (int i = 0; i < fields.length; i++) {
			result.add(fields[i]);
		}
		
		Class<?> superClazz = clazz;
		while (superClazz != null) {
			fields = superClazz.getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				result.add(fields[i]);
			}
			superClazz = superClazz.getSuperclass();
		}
		return result.stream().filter(field->!Modifier.isStatic(field.getModifiers()));
	}

	@Override
	public Object getFieldValue(Object target, String fieldName)
			throws NoSuchFieldException, IllegalAccessException {
		Class<?> locClass = (clazz != null)? clazz : target.getClass();
		Field field = null;
		while (locClass != null && field == null) {
			try {
				field = locClass.getField(fieldName);
			}
			catch (NoSuchFieldException e) {
				try {
					field = locClass.getDeclaredField(fieldName);
					field.setAccessible(true);
				}
				catch (NoSuchFieldException e1) {
					field = null;
					locClass = locClass.getSuperclass();
				}
				
			}
		}
		if (locClass == null)
			throw new NoSuchFieldException();
		return field.get(target);
	}

	@Override
	public Object getMethodResult(Object constructorParam, String methodName,
			Object... methodParams) throws IllegalAccessException,
			InstantiationException, NoSuchMethodException,
			InvocationTargetException {
		Constructor<?> con ;
		Object instance;
		if (constructorParam != null) {
			con = clazz.getConstructor(new Class<?>[]{constructorParam.getClass()});
			instance = con.newInstance(constructorParam);
		} else { 
			con = clazz.getConstructor(new Class<?>[0]);
			instance = con.newInstance(new Object[0]);
		}
		
		Class<?>[] paramsTypes = new Class<?>[methodParams.length];
		for (int i = 0; i<methodParams.length; i++) {
			paramsTypes[i] = methodParams[i].getClass();
		}
		
		Method met = null;
		try {
			met = clazz.getMethod(methodName, paramsTypes);
		}
		catch (NoSuchMethodException e) {
			met = clazz.getDeclaredMethod(methodName, paramsTypes);
			met.setAccessible(true);
		}
		return met.invoke(instance, methodParams);
	}

}
