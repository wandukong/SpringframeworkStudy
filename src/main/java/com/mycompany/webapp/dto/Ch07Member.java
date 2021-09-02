package com.mycompany.webapp.dto;

public class Ch07Member {
	private String name;
	private int age;
	private String job;
	private Ch07City city;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public Ch07City getCity() {
		return city;
	}
	public void setCity(Ch07City city) {
		this.city = city;
	}
}
