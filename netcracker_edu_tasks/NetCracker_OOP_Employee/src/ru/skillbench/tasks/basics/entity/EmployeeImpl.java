package ru.skillbench.tasks.basics.entity;

public class EmployeeImpl implements Employee {
	
	private int salary = 1000;
	private String firstName;
	private String lastName;
	private Employee manager;

	public int getSalary() {
		return salary;
	}

	public void increaseSalary(int value) {
		salary += value;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFullName() {
		return firstName + " " + lastName;
	}

	public void setManager(Employee manager) {
		this.manager = manager;
	}

	public String getManagerName() {
		if (manager != null) {
			return manager.getFirstName() + " " + manager.getLastName();
		} else {
			return "No manager";
		}
	}

	public Employee getTopManager() {
		if (manager == null) {
			return this;
		} else {
			return manager.getTopManager();
		}
	}

}
