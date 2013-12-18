package org.progressive.entities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.progressive.exceptions.AlreadyEatsWithGuest;
import org.progressive.exceptions.CantEatWithSpouse;
import org.progressive.exceptions.TooManyGuestsException;

public class Meal {
	private CourseType courseType;
	private House host;
	private List<Person> guests;
	private Integer maxGuests;
	
	public Meal(CourseType type, Integer maxGuests) {
		super();
		this.courseType = type;
		this.maxGuests = maxGuests;
		this.host = null;
		this.guests = new ArrayList<Person>();
	}
	
	public House getHost() {
		return host;
	}
	
	public void setHost(House host) {
		this.host = host;
		host.setAllocatedCourse(courseType);
		for (Person person: host.getOccupants()) {
			this.guests.add(person);
		}
	}
	
	public void calculateAttendingMealWith() {
		for(Person guest: guests) {
			for (Person person: guests) {
				if (!guest.equals(person)) {
					guest.attendingMeal(person);
				}
			}
		}
	}	
	
	public List<Person> getGuests() {
		return guests;
	}
	
	public void setGuests(List<Person> guests) {
		this.guests = guests;
	}
	
	public Integer getMaxGuests() {
		return maxGuests;
	}
	
	public CourseType getCourseType() {
		return courseType;
	}

	public void addGuest(Person person) throws TooManyGuestsException, AlreadyEatsWithGuest, CantEatWithSpouse {
		if(this.guests.contains(person.getSpouse())) {
			throw new CantEatWithSpouse();
		}
		if(this.maxGuests.equals(this.guests.size())) {
			throw new TooManyGuestsException();
		}	
		this.guests.add(person);
	}
	
	public void clear() {
		Iterator<Person> i = guests.iterator();
		while(i.hasNext()) {
			if(!(host.getOccupants().contains(i.next()))) {
				i.remove();
			}
		}
		this.host.clear();
	}
	
	@Override
	public String toString() {
		return courseType + " meal hosted by " + host + " with guests " + guests;
	}
}