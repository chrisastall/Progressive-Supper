package org.progressive.exceptions;

import org.progressive.entities.Person;
import org.progressive.entities.CourseType;

public class CantAllocatePersonToMeal extends Exception {
	private static final long serialVersionUID = 7901864654103950314L;

	private Person person;
	private CourseType course;
	
	public CantAllocatePersonToMeal(Person guest, CourseType course) {
		this.person = guest;
		this.course = course;
	}

	public Person getPerson() {
		return person;
	}

	public CourseType getCourse() {
		return course;
	}

	@Override
	public String toString() {
		return "Cant allocate " + person + " to meal for course " + course;
	}
}
