package org.progressive.entities;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.progressive.exceptions.AlreadyEatsWithGuest;
import org.progressive.exceptions.CantAllocatePersonToMeal;
import org.progressive.exceptions.CantEatWithSpouse;
import org.progressive.exceptions.NoMoreMealsToHost;
import org.progressive.exceptions.TooManyGuestsException;

public class Course {
	static Logger logger = Logger.getLogger(Course.class);
	
	private CourseType type;
	private Set<Meal> meals;
	private Integer numHosts;
	
	public Course(CourseType type, Integer numHosts) {
		this.type = type;
		this.numHosts = numHosts;
		this.meals = new HashSet<Meal>();
	}
	
	public Course(CourseType type, Integer numHosts, Integer numGuests) {
		this.type = type;
		this.numHosts = numHosts;
		setUpMeals(numHosts, numGuests);
	}

	private void setUpMeals(Integer numHosts, Integer numGuests) {
		this.meals = new HashSet<Meal>();
		Integer div = numGuests / numHosts;
		Integer remainder = numGuests % numHosts;
		for(int i = 0; i < numHosts; i++) {
			Integer mealGuests = div;
			if (remainder > 0) {
				mealGuests++;
				remainder--;
			}
			Meal meal = new Meal(type, mealGuests);
			meals.add(meal);
		}
	}
	
	public CourseType getType() {
		return type;
	}

	public Integer getNumHosts() {
		return numHosts;
	}

	public Set<Meal> getMeals() {
		return meals;
	}
	
	public void addHost(House host) throws NoMoreMealsToHost {
		Boolean allMealsHosted = true;
		for(Meal meal: meals) {
			if (meal.getHost() == null) {
				meal.setHost(host);
				allMealsHosted = false;
				break;
			}
		}
		if (allMealsHosted) {
			throw new NoMoreMealsToHost();
		}
	}
	
	private Boolean alreadyHostingMeal(Person person) {
		for(Meal meal: meals) {
			if(meal.getHost().getOccupants().contains(person)) {
				return true;
			}
		}
		return false;
	}
	
	public Set<Person> getHosts() {
		Set<Person> hosts = new HashSet<Person>();
		for (Meal meal: meals) {
			hosts.addAll(meal.getHost().getOccupants());
		}
		return hosts;
	}
	
	public void clear() {
		for(Meal meal: meals) {
			meal.clear();
		}
	}
	
	public void addGuests(List<Person> guests) throws CantAllocatePersonToMeal {
		//Randomise the list of people
		long seed = System.nanoTime();
		Collections.shuffle(guests, new Random(seed));
		
		for(Person guest: guests) {
			Boolean allocatedToMeal = false;
			logger.debug("Trying " + guest + " " + guest.getHadCourseWith());
			for(Meal meal: meals) {
				logger.debug("  For " + meal.getCourseType() + " at " + meal.getHost() + " with " + meal.getGuests() );
				try {
					if(!alreadyHostingMeal(guest)) {
						meal.addGuest(guest);
						logger.debug("    Allocated " + guest + " to " + meal.getHost() + " with " + meal.getGuests());
					} else {
						logger.debug("    Is the host at " + meal.getHost());
					}
					allocatedToMeal = true;
					break;
				} catch (TooManyGuestsException e) {
					logger.debug("    " + meal.getHost() + " already full");
				} catch (AlreadyEatsWithGuest e) {
					logger.debug("    " + guest + " already eats with another guest");
				} catch (CantEatWithSpouse e) {
					logger.debug("    " + guest + " can't eat with their spouse");
				}
			}
			if(!allocatedToMeal) {
				logger.info("    Failed to allocate " + guest + " to course " + type);
				throw new CantAllocatePersonToMeal(guest, type);
			}
		}
		calculateAttendingMealWith();
	}
	
	private void calculateAttendingMealWith() {
		for(Meal meal: meals) {
			meal.calculateAttendingMealWith();
		}
	}
	
	public Boolean courseFullyHosted() {
		for(Meal meal: meals) {
			if (meal.getHost() == null) {
				return false;
			}
		}
		return true;
	}
	
	public SortedSet<Person> getAllGuests() {
		SortedSet<Person> sorted = new TreeSet<Person>();
		for(Meal meal: meals) {
			sorted.addAll(meal.getGuests());
		}
		return sorted;
	}

	@Override
	public String toString() {
		StringBuffer output = new StringBuffer();
		for(Meal meal: meals) {
			output.append("\n  ");
			output.append(meal);
		}
		
		return output.toString();
	}
	
}
