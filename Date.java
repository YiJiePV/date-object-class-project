/*Karena Qian 
 * CS 210 
 * 6/12/2020 
 * This class creates Date objects with values for month, day, and year
 * and performs various tasks with them, including increasing them (time-wise), 
 * calculating the time interval between two Dates,
 * confirming if a Date's year is a leap year, 
 * and converting Dates into readable Strings*/
public class Date {
	//Fields (private):
	private int month;
	private int day;
	private int year;
	private int days[] = new int[13]; //stores # days in each calendar month
	//Constructor methods:
	//default constructor that initializes Date() to January 1, 1970
	public Date() {
		month = 1;
		day = 1;
		year = 1970;
	}
	/*Constructs new Date object to given date (year, month, and day)
	 * throws exception if date isn't within numerical & time boundaries*/
	public Date(int iyear, int imonth, int iday) {
		setYear(iyear);
		setMonth(imonth);
		setDay(iday);
		if(year == 1582 && ((month == 10 && day < 15) || month < 10)) {
			throw new IllegalArgumentException();
		}
	}
	//Accessor methods:
	//Returns the day value of date
	public int getDay() {	
		return day;
	}
	//Returns the month value of date
	public int getMonth() {
		return month;
	}
	//Returns the year value of date
	public int getYear() {
		return year;
	}
	//Mutator methods
	/*Sets new value for year if valid
	 * else throw the IllegalArgumentException*/
	public void setYear(int year) {
		if(year > 2999 || year < 1582) {
			throw new IllegalArgumentException();
		}
		else {
			this.year = year;
		}
	}
	/*Sets new value for month if valid
	 * else throw the IllegalArgumentException*/
	public void setMonth(int month) {
		if(month > 12 || month < 1) {
			throw new IllegalArgumentException();
		}
		else {
			this.month = month;
		}
	}
	/*Sets new value for day if valid
	 * else throw the IllegalArgumentException*/
	public void setDay(int day) {
		daysInMonth();
		if(month == 2) {
			if(isLeapYear() == true) { //checking for leap year
				if(day > days[month]+1 || day < 1) {
					throw new IllegalArgumentException();
				}
				else {
					this.day = day;			
				}
			}
			else {
				if(day > days[month] || day < 1) {
					throw new IllegalArgumentException();
				}
				else {
					this.day = day;			
				}
			}
		}
		else {
			if(day > days[month] || day < 1) {
				throw new IllegalArgumentException();
			}
			else {
				this.day = day;			
			}
		}
	}
	//Adds # days to Date (time-wise) 
	public void addDays(int days) {  
		daysInMonth();
		day += days;
		correctDate();
	}
	//Adds weeks * days to Date (time-wise) 
	public void addWeeks(int weeks) {
		daysInMonth();
		day += weeks * 7;
		correctDate();
	}
	//Static version of daysTo
	//Returns # days between 2 Dates (presume d1 < d2)
	 public static int daysTo(Date d1, Date d2) {
		int days = 0;
		boolean d2Smaller = false;
		//checking if d1 is bigger than d2 (and is not the same), if so, then swap 
		if(d1.year > d2.year || (d1.year== d2.year && d1.month > d2.month) || 
				(d1.year== d2.year && d1.month == d2.month && d1.day > d2.day)) {
			Date t = d2;
			d2 = d1;
			d1 = t;
			d2Smaller = true;
		}
	    days = Date.countDays(d1, d2, d2Smaller);
    	return days;
	}
	//Non-static version of daysTo
	// Return # days between 2 Dates
	public int daysTo(Date d){
		int days = Date.daysTo(this, d); //calls static daysTo
		return days;
	}
	//Tests if year of Date is leap year
	public boolean isLeapYear() {
		if(year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
			return true;
		}
		return false;
	}
	//Formats Date like "January 1, 1970"
	public String longDate() {
		switch (month){
		case 1:
			return "January " + day + ", " + year;
		case 2:
			return "February " + day + ", " + year;
		case 3:
			return "March " + day + ", " + year;
		case 4:
			return "April " + day + ", " + year;
		case 5:
			return "May " + day + ", " + year;
		case 6:
			return "June " + day + ", " + year;
		case 7:
			return "July " + day + ", " + year;
		case 8:
			return "August " + day + ", " + year;
		case 9:
			return "September " + day + ", " + year;
		case 10:
			return "October " + day + ", " + year;
		case 11:
			return "November " + day + ", " + year;
		case 12:
			return "December " + day + ", " + year;
		}
		return "";
	}
	//Formats Date like "2003/02/06"
	public String toString() {
		String newString = year + "/";
		if(month < 10 || day < 10) {
			if(month < 10) {
				newString += "0" + month + "/";
			}
			else {
				newString += month + "/";
			}
			if(day < 10) {
				newString += "0" + day;
			}
			else {
				newString += day;
			}
			return newString;
		}
		else {
			return year + "/" + month + "/" + day;
		}
	}
	//Helper methods:
	//Sets array for number of days per month
	public void daysInMonth() {
		for(int i = 1; i <= 12; i++) {
			if(i <= 7 && i % 2 != 0) {
				days[i] = 31;
			}
			else if(i >= 8 && i % 2 == 0) {
				days[i] = 31;
			}
			else if(i == 2) {
				days[i] = 28;
			}
			else {
				days[i] = 30;
			}
		}
	}
	//Time-wisely corrects Date based on current # days 
	public int correctDate() {
		int daysInMonth = 0;
		while(day >= days[month]+1) { //for # days bigger than given month
			daysInMonth = days[month];
			if(month == 2) { //checking for leap year if month = February
				if(isLeapYear() == true) {
					daysInMonth++;
				} 
			}
			month++;
			if(month > 12) {
				year++;
				month = month - 12;			
			}
			day = day - daysInMonth; 
		}
		while(day < 1) { //for # days smaller than 1
			month--;
			if(month < 1) {
				year--;
				month += 12;
			}
			daysInMonth = days[month];
			if(month == 2) {
				if(isLeapYear() == true) { //check for leap year if month = February
					daysInMonth++;
				} 
			}
			day += daysInMonth;
		}
		return day;
	}
	//Counts days in time interval between Date 1 and Date 2 (where d1 -> d < d2)
	public static int countDays(Date d1, Date d2, boolean d2isSmaller) {
		int countDays = 0;
		Date d = new Date(d1.year, d1.month, d1.day);
		d.daysInMonth();
		while (d.year != d2.year || d.month != d2.month || d.day != d2.day) {
				d.day++;
				d.correctDate();
				if(d2isSmaller == false) {
					countDays++; //count up
				}
				else {
					countDays--; //count down
				}
		}
		return countDays;
	}
}
