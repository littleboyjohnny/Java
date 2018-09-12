package ru.skillbench.tasks.basics.math;

import java.util.Arrays;

public class ArrayVectorImpl implements ArrayVector {
	
	private double[] arr;

	@Override
	public void set(double... elements) {
		arr = elements;
	}

	@Override
	public double[] get() {
		return arr;
	}
	
	@Override
	public ArrayVector clone() {
		ArrayVector a = new ArrayVectorImpl();
		a.set(arr.clone());
		return a;
	}

	@Override
	public int getSize() {
		return arr.length;
	}

	@Override
	public void set(int index, double value) {
		if (index >= 0) {
			if (index < arr.length) {
				arr[index] = value;
			} else {
				double[] cp = new double[index + 1];
				//System.arraycopy(arr, 0, cp, 0, arr.length);
				for (int i = 0; i < arr.length; i++) {
					cp[i] = arr[i];
				}
				cp[index] = value;
				arr = cp;
			}
		}
	}

	@Override
	public double get(int index) throws ArrayIndexOutOfBoundsException {
		if (index >= arr.length)
			throw new ArrayIndexOutOfBoundsException("Too many");
		else {
			return arr[index];
		}
	}

	@Override
	public double getMax() {
		double max = arr[0];
		for (int i = 1; i < arr.length; i++) {
			if (arr[i] > max)
				max = arr[i];
		}
		return max;
	}

	@Override
	public double getMin() {
		double min = arr[0];
		for (int i = 1; i < arr.length; i++) {
			if (arr[i] < min)
				min = arr[i];
		}
		return min;
	}

	@Override
	public void sortAscending() {
		Arrays.sort(arr);
	}

	@Override
	public void mult(double factor) {
		for (int i = 0; i < arr.length; i++) {
			arr[i] *= factor;
		}
	}

	@Override
	public ArrayVector sum(ArrayVector anotherVector) {
		int locmin = Math.min(arr.length, anotherVector.getSize());
		for (int i = 0; i < locmin; i++) {
			arr[i] += anotherVector.get(i); 
		}
		return this;
	}

	@Override
	public double scalarMult(ArrayVector anotherVector) {
		int locmin = Math.min(arr.length, anotherVector.getSize());
		double sum = 0;
		for (int i = 0; i < locmin; i++) {
			sum += arr[i] * anotherVector.get(i); 
		}
		return sum;
	}

	@Override
	public double getNorm() {
		double res = 0;
		for(int i = 0; i < arr.length; i++) {
			res += arr[i] * arr[i];
		}
		return Math.sqrt(res);
	}

}
