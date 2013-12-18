package org.progressive;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.varia.NullAppender;
import org.progressive.entities.House;
import org.progressive.entities.Person;
import org.progressive.entities.ProgressiveSupper;
import org.progressive.exceptions.CantAllocatePersonToMeal;
import org.progressive.exceptions.ReallyAllFourCourses;

public class Main {
	
	public static void main(String [] args) {
		org.apache.log4j.BasicConfigurator.configure(new NullAppender());
		//org.apache.log4j.BasicConfigurator.configure();
		
		Set<House> houses = new DataSet2014().getHouses();
		
		// Calculate the number of Houses
		Integer numHouses = 0;
		for(House house: houses) {
			if(!(house.isPreDinner() || house.isPostDinner())) {
				numHouses++;
			}
		}
		System.out.println("Number of Houses: " + numHouses);
		
		// Get all People attending
		Set<Person> allPeople = new HashSet<Person>();
		for (House house: houses) {
			allPeople.addAll(house.getOccupants());
		}
		System.out.println("Number of People: " + allPeople.size());
		
		// Create a progressive supper
		ProgressiveSupper ps = new ProgressiveSupper(numHouses, allPeople.size());

		// Allocate Hosts to the meals
		ps.allocateHosts(houses);
			
		
		Integer lowestScore = 99999999;
		
		// If we are fully hosted then allocate guests
		if (ps.allCoursesFullyHosted()) {
			Integer tried = 0;
			List<Person> people = new ArrayList<Person>();
			people.addAll(allPeople);
			Boolean complete = false;
			while (complete == false) {
				try {
					tried++;
					ps.setGuests(people);
					ps.clear();
					ps.allocateGuests();
					if (ps.getScore().compareTo(lowestScore) < 0) {
						System.out.println("Score = " + ps.getScore());
						
						lowestScore = ps.getScore();
						System.out.println(ps);
						System.out.println();
						for(Person p: people) {
							System.out.println(p.getName() + " is having dinner with " + p.getHadCourseWithOrdered());
						}
					}
					
					if(lowestScore == 0) {
						complete = true;
					}
				} catch (CantAllocatePersonToMeal e) {

				} catch (ReallyAllFourCourses e) {

				}
				if ((tried % 1000000) == 0) {
					System.out.println("Tried " + tried / 1000000 + "m");
				}
			}
		} else {
			System.out.println("NOT Fully Hosted");
		}
	}
}