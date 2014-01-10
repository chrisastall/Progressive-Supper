package org.progressive.entities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections4.Bag;
import org.apache.commons.collections4.bag.HashBag;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.progressive.exceptions.ReallyAllFourCourses;

public class Person implements Comparable<Person> {
	private String name;
	private Gender gender;
	private House house;
	private String specialRequest;
	private Person spouse;
	private Bag<Person> hadCourseWith;

	
	public Person(String name, Gender gender) {
		super();
		this.name = name;
		this.gender = gender;
		this.specialRequest = null;
		this.hadCourseWith = new HashBag<Person>();
		this.spouse = null;
	}
	
	public Person(String name, Gender gender, String specialRequest) {
		super();
		this.name = name;
		this.gender = gender;
		this.specialRequest = specialRequest;
		this.hadCourseWith = new HashBag<Person>();
		this.spouse = null;
	}

	public String getName() {
		return name;
	}
	
	public House getHouse() {
		return house;
	}

	public void setHouse(House house) {
		this.house = house;
	}

	public String getSpecialRequest() {
		return specialRequest;
	}
	
	public Bag<Person> getHadCourseWith() {
		return hadCourseWith;
	}
	
	public List<Pair<Person,Integer>> getHadCourseWithOrdered() {
		Integer maxCount = 0;
		for(Person person: this.hadCourseWith.uniqueSet()) {
			if(this.hadCourseWith.getCount(person) > maxCount) {
				maxCount = this.hadCourseWith.getCount(person);
			}
		}

		List<Pair<Person, Integer>> output = new ArrayList<Pair<Person, Integer>>();
		for(Integer i = maxCount; i != 0; i--) {
			for(Person person: this.hadCourseWith.uniqueSet()) {
				if (this.hadCourseWith.getCount(person) == i) {
					output.add(new MutablePair<Person, Integer>(person, i));
				}
			}
		}
		return output;
	}
	
	public Boolean hadCourseWith(Set<Person> guests) {
		for(Person person: hadCourseWith.uniqueSet()) {
			for (Person guest: guests) {
				if (guest.equals(person)) {
					return true;
				}
			}
		}
		return false;
	}

	public void attendingMeal(Person person) {
		this.hadCourseWith.add(person);
	}
	
	public Integer getScore() throws ReallyAllFourCourses {
		Integer score = 0;
		for(Person person: this.hadCourseWith.uniqueSet()) {
			Integer i = this.hadCourseWith.getCount(person);
			switch (i) {
				case 2 : score += 1; break;
				case 3 : score += 100; break;
				case 4 : throw new ReallyAllFourCourses();
			}
		}
		return score;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Person) {
			return ((Person) obj).getName().equals(name);
		} 
		return false;
	}
	
	@Override
	public int hashCode()
	{
	    return name.hashCode();
	}

	@Override
	public int compareTo(Person person)
	{
	    return this.name.compareTo(person.name);
	}
	
	public Person getSpouse() {
		if(spouse == null) {
			Set<Person> spouseSet = new HashSet<Person>(this.getHouse().getOccupants());
			spouseSet.remove(this);
			if (!spouseSet.isEmpty()) {
				spouse = spouseSet.iterator().next(); 
			}
		} 
		return spouse;
	}
	
	public void setSpouse(Person spouse) {
		this.spouse = spouse;
	}
	
	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}
	
	public Boolean isMale() {
		return gender == Gender.MALE;
	}

	public Boolean isFemale() {
		return gender == Gender.FEMALE;
	}

	public void clear() {
		this.hadCourseWith = new HashBag<Person>();
	}
	
	@Override
	public String toString() {
		return name + "(" + gender + ")";
	}
}
