package org.progressive.entities;

import java.util.HashSet;
import java.util.Set;

public class House {
	private String name;
	private Set<CourseType> courses;
	private Set<Person> occupants;
	private CourseType allocatedCourse;
	private Boolean preDinner;
	private Boolean postDinner;
	
	public House(String name) {
		super();
		this.name = name;
		this.courses = new HashSet<CourseType>();
		this.occupants = new HashSet<Person>();
		this.preDinner = false;
		this.postDinner = false;
		this.allocatedCourse = null;
	}
	
	public House(String name, Set<CourseType> courses) {
		super();
		this.name = name;
		this.courses = courses;
		this.occupants = new HashSet<Person>();
		this.preDinner = false;
		this.postDinner = false;
		this.allocatedCourse = null;
	}
	
	public House(String name, Set<CourseType> courses, Set<Person> occupants) {
		super();
		this.name = name;
		this.courses = courses;
		this.occupants = occupants;
		this.preDinner = false;
		this.postDinner = false;
		this.allocatedCourse = null;
	}
	
	public String getName() {
		return name;
	}
	
	public Set<CourseType> getCourses() {
		return courses;
	}
	
	public void setCourses(Set<CourseType> courses) {
		this.courses = courses;
	}
	
	public Set<Person> getOccupants() {
		return occupants;
	}
	
	public void setOccupants(Set<Person> occupants) {
		this.occupants = occupants;
		for(Person occupant: occupants) {
			for(Person occupant2: occupants) {
				if(!occupant.equals(occupant2)) {
					occupant.setSpouse(occupant2);
				}
			}
		}
	}
	
	public Boolean isPreDinner() {
		return preDinner;
	}

	public void setPreDinner(Boolean preDinner) {
		this.preDinner = preDinner;
	}

	public Boolean isPostDinner() {
		return postDinner;
	}

	public void setPostDinner(Boolean postDinner) {
		this.postDinner = postDinner;
	}

	public CourseType getAllocatedCourse() {
		return allocatedCourse;
	}

	public void setAllocatedCourse(CourseType allocatedCourse) {
		this.allocatedCourse = allocatedCourse;
	}

	public void addCourse(CourseType course) {
		this.courses.add(course);
	}
	
	public void addOccupant(Person person) {
		this.occupants.add(person);
		person.setHouse(this);
	}

	public void clear() {
		this.allocatedCourse = null;
		for(Person occupant: occupants) {
			occupant.clear();
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof House) {
			return ((House) obj).getName().equals(name);
		} 
		return false;
	}
	
	@Override
	public int hashCode()
	{
	    return name.hashCode();
	}
	
	@Override
	public String toString() {
		return name;
	}
	
}
