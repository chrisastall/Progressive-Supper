package org.progressive.entities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.progressive.exceptions.CantAllocatePersonToMeal;
import org.progressive.exceptions.NoMoreMealsToHost;
import org.progressive.exceptions.ReallyAllFourCourses;

public class ProgressiveSupper {
	private Map<CourseType, Course> courses;
	private List<Person> guests;

	public Map<CourseType, Course> getCourses() {
		return courses;
	}
	
	public ProgressiveSupper(Integer numHouses, Integer numPeople) {
		setUpCourses(numHouses, numPeople);
	}
	
	private void setUpCourses(Integer numHouses, Integer numPeople) {
		this.guests = null;
		this.courses = new HashMap<CourseType, Course>();
		
		Integer div = numHouses / CourseType.values().length;
		Integer remainder = numHouses % CourseType.values().length;
		for(CourseType type: CourseType.values()) {
			Integer numHosts = div;
			if (remainder > 0) {
				numHosts++;
				remainder--;
			}
			Course course = null;
			if (numPeople != null) {
				course = new Course (type, numHosts, numPeople);
			} else {
				course = new Course (type, numHosts);
			}
			courses.put(type, course);
		}
	}
	
	public void allocateHosts(Set<House> hosts) {
		for (int i = 1; i <= CourseType.values().length; i++) {
			for (House host: hosts) {
				if (host.getAllocatedCourse() == null) {
					if (host.getCourses().size() == i) {
						for(CourseType type: host.getCourses()) {
							try {
								courses.get(type).addHost(host);
								break;
							} catch (NoMoreMealsToHost e) {
								// Do Nothing
							}
						}
					}
				}
			}
		}
	}
	
	public Boolean allCoursesFullyHosted() {
		for(CourseType type: CourseType.values()) {
			if (courses.get(type).courseFullyHosted() == false) {
				return false;
			}
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuffer output = new StringBuffer();
		output.append("ProgressiveSupper");
		for(CourseType type: CourseType.values()) {
			output.append("\n");
			output.append(courses.get(type));
		}
		
		return output.toString();
	}

	public void clear() {
		for(CourseType type: CourseType.values()) {
			courses.get(type).clear();
		}
		for(Person person: guests) {
			person.clear();
		}
	}
	
	public List<Person> getGuests() {
		return guests;
	}

	public void setGuests(List<Person> guests) {
		this.guests = guests;
	}

	public Integer getScore() throws ReallyAllFourCourses {
		Integer score = 0;
		for(Person person: guests) {
			score += person.getScore();
		}
		return score;
	}
	
	public void allocateGuests() throws CantAllocatePersonToMeal {
		for(CourseType type: CourseType.values()) {
			courses.get(type).addGuests(guests);
		}
		
	}

	
}
