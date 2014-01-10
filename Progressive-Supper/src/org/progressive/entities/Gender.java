package org.progressive.entities;

public enum Gender {
	MALE, FEMALE;
	
	@Override
	public String toString() {
		switch(this) {
		case MALE: return "M";
		case FEMALE: return "F";
		default: throw new IllegalArgumentException();
		}
	}
}
