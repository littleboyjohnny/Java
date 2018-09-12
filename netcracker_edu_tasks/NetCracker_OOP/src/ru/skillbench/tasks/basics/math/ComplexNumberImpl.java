package ru.skillbench.tasks.basics.math;

import java.util.Arrays;

public class ComplexNumberImpl implements ComplexNumber {
	
	private double re;
	private double im;
	
	@Override
	public double getRe() {
		return re;
	}

	@Override
	public double getIm() {
		return im;
	}

	@Override
	public boolean isReal() {
		return im == 0;
	}

	@Override
	public void set(double re, double im) {
		this.re = re;
		this.im = im;
	}

	@Override
	public void set(String value) throws NumberFormatException {
		if (value.indexOf('i') == -1) {
			// im == 0
			this.im = 0;
			this.re = Double.parseDouble(value);
		} else {
			// im != 0
			int lastplus = value.lastIndexOf('+');
			int lastminus = value.lastIndexOf('-');
			if (lastplus > 0 || lastminus > 0) {
				if (lastplus > 0) {
					String sub = value.substring(lastplus, value.length() - 1);
					if (sub.length() == 1)
						this.im = 1;
					else
						this.im = Double.parseDouble(sub);
					this.re = Double.parseDouble(value.substring(0, lastplus));
				}
				if (lastminus > 0) {
					String sub = value.substring(lastminus, value.length() - 1);
					if (sub.length() == 1)
						this.im = 1;
					else
						this.im = Double.parseDouble(sub);
					this.re = Double.parseDouble(value.substring(0, lastminus));
				}
			} else {
				this.re = 0;
				String sub = value.substring(0, value.length() - 1);
				if (sub.length() == 1)
					this.im = 1;
				else
					this.im = Double.parseDouble(sub);
			}
		}
	}

	@Override
	public ComplexNumber copy() {
		ComplexNumber cmp = new ComplexNumberImpl();
		cmp.set(re, im);
		return cmp;
	}

	@Override
	public ComplexNumber clone() throws CloneNotSupportedException {
		ComplexNumber cmp = new ComplexNumberImpl();
		cmp.set(re, im);
		return cmp;
	}
	
	@Override
	public String toString() {
		if (this.re == 0 || this.im == 0) {
			if (this.re == 0 && this.im == 0) {
				return "0";
			} else if (this.re == 0) {
				return Double.toString(this.im) + "i";
			} else {
				return Double.toString(this.re);
			}
		}
		else {
			if (this.im > 0)
				return Double.toString(this.re) + "+" + Double.toString(this.im) + "i";
			else
				return Double.toString(this.re) + Double.toString(this.im) + "i";
		}
	}
	
	@Override
	public boolean equals(Object other) {
		return this.re == ((ComplexNumber)other).getRe() && this.im == ((ComplexNumber)other).getIm();
	}
	
	@Override
	public int compareTo(ComplexNumber other) {
		return (int)(Math.pow(this.re, 2) + Math.pow(this.im, 2) - Math.pow(other.getRe(), 2) - Math.pow(other.getIm(), 2));
	}

	@Override
	public void sort(ComplexNumber[] array) {
		Arrays.sort(array);
	}

	@Override
	public ComplexNumber negate() {
		this.im = -this.im;
		this.re = -this.re;
		return this;
	}

	@Override
	public ComplexNumber add(ComplexNumber arg2) {
		this.re += arg2.getRe();
		this.im += arg2.getIm();
		return this;
	}

	@Override
	public ComplexNumber multiply(ComplexNumber arg2) {
		double tempRe = this.re * arg2.getRe() - this.im * arg2.getIm();
		double tempIm = this.re * arg2.getIm() + this.im * arg2.getRe();
		this.re = tempRe;
		this.im = tempIm;
		return this;
	}

}
