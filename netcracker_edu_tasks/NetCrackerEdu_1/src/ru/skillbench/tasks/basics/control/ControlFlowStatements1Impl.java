package ru.skillbench.tasks.basics.control;

public class ControlFlowStatements1Impl implements ControlFlowStatements1 {

	public float getFunctionValue(float x) {
		return (float)((x > 0)? 2 * Math.sin(x) : 6 -x);
	}

	public String decodeWeekday(int weekday) {
		switch (weekday) {
			case 1:
				return "Monday";
			case 2:
				return "Tuesday";
			case 3:
				return "Wednesday";
			case 4:
				return "Thursday";
			case 5:
				return "Friday";
			case 6:
				return "Saturday";
			case 7:
				return "Sunday";
			default:
				return null;
		}
	}

	public int[][] initArray() {
		int[][] array = new int[8][5];
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 5; j++) {
				array[i][j] = i * j;
			}
		}
		return array;
	}

	public int getMinValue(int[][] array) {
		int min = array[0][0];
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[i].length; j++) {
				if (min > array[i][j]) {
					min = array[i][j];
				}
			}
		}
		return min;
	}

	public BankDeposit calculateBankDeposit(double P) {
		double amount = 1000;
		int years = 0;
		while (amount < 5000) {
			years++;
			amount += amount * P / 100;
		}
		BankDeposit bd = new BankDeposit();
		bd.amount = amount;
		bd.years = years;
		return bd;
	}

}
