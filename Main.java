import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Scanner;

public class Main {

	public static void main(String args[]) throws IOException {
		try {
			//Imports and Reads the drivers-info.txt file
        	File driversIn = new File("drivers-info.txt");
        	Scanner driversInSc = new Scanner(driversIn);

        	Customer user = new Customer();
        	Restaurant restaurant = new Restaurant();
        	Scanner input = new Scanner(System.in);     
        	        	
        	//Customer details
        	//Requests the users name and stores it the the customer object
        	String userName = "";
        	System.out.println("Enter your name:");
        	while ( userName.isEmpty() ) {
        		userName = input.nextLine();
        		if ( userName.isEmpty() ) {
        			System.err.println("Field must be entered!");
        			System.out.println("Enter your name:");
        		}
        	}
        	user.setName(userName);
        	        	
        	//Requests the users number and stores it the the customer object
        	String userContact = "";
        	System.out.println("Enter your contact number:");
        	while (userContact.isEmpty()) {
        		userContact = input.nextLine();
        		if (userContact.isEmpty() || !userContact.matches("[0-9 ]+")) {
        			System.err.println("Field not entered or formatted correctly!");
        			System.out.println("Enter your contact number:");
        			userContact = "";
        		}
        	}
        	user.setContact(userContact);
        	
        	//Requests the users address and stores it the the customer object
        	String userStreetAddress = "";
        	System.out.println("Enter your street address:");
        	while ( userStreetAddress.isEmpty() ) {
        		userStreetAddress = input.nextLine();
        		//makes sure the user inputs their address
        		if ( userStreetAddress.isEmpty() ) {
        			System.err.println("Field must be entered!");
        			System.out.println("Enter your street address:");
        		}
        	}
        	user.setStreetAddress(userStreetAddress);
        	
        	//Requests the users address and stores it the the customer object
        	String userArea = "";
        	System.out.println("Enter your area:");
        	while ( userArea.isEmpty() ) {
        		userArea = input.nextLine();
        		//makes sure the user inputs their area
        		if ( userArea.isEmpty() ) {
        			System.err.println("Field must be entered!");
        			System.out.println("Enter your area:");
        		}
        	}
        	user.setArea(userArea);
        	
        	//Requests the users location and stores it the the customer object
        	String userLocation = "";
        	System.out.println("Enter your location:");
        	while ( userLocation.isEmpty() ) {
        		userLocation = input.nextLine();
        		//makes sure the user inputs their location
        		if ( userLocation.isEmpty() ) {
        			System.err.println("Field must be entered!");
        			System.out.println("Enter your location:");
        		}
        	}
        	user.setLocation(userLocation);

        	//Requests the users email and stores it the the customer object
        	String userEmail = "";
        	System.out.println("Enter your email:");
        	while ( userEmail.isEmpty() ) {
        		userEmail = input.nextLine();
        		//makes sure the user inputs their email
        		if (userEmail.isEmpty()) {
        			System.err.println("Field must be entered!");
        			System.out.println("Enter your email:");
        		}
        	}
        	user.setEmail(userEmail);
        	
        	//Restaurant inputs
			//Shows all available restaurants
			System.out.println();
        	System.out.println("We cater for these restaurants:");
        	for (String i : showAllRestaurants()) {
        		System.out.println(i);
        	}
        	System.out.println();
        	
        	//Requests the name of the desired restaurant and stores it the the restaurant object
        	String restName = "";
        	boolean accepted;
        	System.out.println("Enter the Restuarant name:");
        	while ( restName.isEmpty() ) {
        		restName = input.nextLine();
        		//takes the users input and checks if if the requested restaurant is catered for
        		accepted = readRestaurantList(restName);
        		if ( restName.isEmpty() ) {
        			System.err.println("Field must be entered!");
        			System.out.println("Enter your location:");
        		}
        		if ( accepted == false ){
        			System.err.println("We dont cater for " + restName + ".");
        			System.out.println("Enter the Restuarant name:");
        			restName = "";
        		}
        	}
        	//once a restaurant name is accepted it is stored to the restaurant object
        	restaurant.setName(restName);
        	
        	/*the restaurant objects name is used to get the text file with its name, 
        	* the contact info and location info from the file is then stored to the restaurant object*/
        	restaurant.setContact((readRestaurant(restaurant.getName()).get("Contact")));
        	restaurant.setLocation((readRestaurant(restaurant.getName()).get("Location")));
        	
        	//Displays all items for sale from the selected restaurant
        	showMenu(readRestaurant(restaurant.getName()));
        	
        	/* this is the food ordering loop. The user gets prompted to enter a food item and how 
        	 * many of that item they want until they are done*/
        	boolean done = false;
        	//loops until 'done' is true
        	do {
        		String item;
        		System.out.println("Enter the food items name:");
        		item = input.nextLine();
        		//checks if the food item exists in the restaurants text file
        		if ( (readRestaurant(restaurant.getName())).containsKey(item) ) {
        			restaurant.setItem(item);
        			//gets the price of the item from the restaurants text file
    				restaurant.setItemPrice( (readRestaurant(restaurant.getName()).get(item)) );
        		} else {
        			System.err.println("That food item does not exist. Please enter the name as you see it.");
        			continue;
        		}
        		
        		//requests the user to input how many of the food item they want
        		String itemCount = "";
        		System.out.println("Enter how many you want:");
        		while (itemCount.isEmpty()) {
        			itemCount = input.nextLine();
        			//only numbers are valid
        			if (itemCount.isEmpty() || !itemCount.matches("[0-9]+")) {
        				System.err.println("This field is required!");
        				System.out.println("Enter how many you want:");
        				itemCount = "";
        			}
        		}
        		restaurant.setAmountOfItem(itemCount);
        		
        		//if the user is done, the loop is broken
        		System.out.println("Do you want to order anything else? y/n");
        		if (input.nextLine().equalsIgnoreCase("n")) {
        			done = true;
        		}
        	} while(done == false) ;
        	
        	System.out.println("Enter any special instructions:");
        	restaurant.setPrep(input.nextLine());
        	
        	System.out.println("\nYour receipt will now be printed!");
        	
        	//gets all the drivers in the correct location
         	List<String> drivers = new ArrayList<>();
        	while(driversInSc.hasNextLine()) {
        		String line = driversInSc.nextLine();
        		String[] arrOfStr = line.split(", ");
        		//Skips any driver might be missing a value
        		if (arrOfStr.length == 3) {
        			//if the driver location,the user location and the restaurant are the same, the matched drivers are picked.
        			if (arrOfStr[1].equalsIgnoreCase(restaurant.getLocation()) && arrOfStr[1].equalsIgnoreCase(user.getLocation())) {
            			drivers.add(line);
            		}
    			}
        	}        	
        	driversInSc.close();
        	input.close();
        	
        	int listLen = drivers.toArray().length;
        	
        	updateOrderNumber();
        	writeNameAndOrder(user.getName(), fetchOrderNumber());
        	//writeCustomerLocations(user.getName() ,user.getLocation());
        	//Holds all the information for the receipt, except for the string arrays
        	String[] toPrint = {user.getName(),user.getEmail(), user.getContact(), user.getLocation(), readRestaurant(restaurant.getName()).get("Name") 
        			,restaurant.getLocation() ,restaurant.getPrep() ,restaurant.getPrice(), checkDriver(drivers, listLen), user.getStreetAddress(), user.getArea(), restaurant.getContact(), fetchOrderNumber()};
        	
        	
        	//prints the final receipt
        	printReceipt(toPrint, restaurant.getAmountOfItem(), restaurant.getAllItems(), restaurant.getItemPrice());
       	
		} catch (FileNotFoundException e) {
			System.err.println(e);
		}
		
	}
	
	private static String checkDriver(List<String> drivers, int len) {
		String out = "";
		//if the drivers list is not empty, the loads are sorted the the drivers name with the lowest load is returned
		if (!drivers.isEmpty()) {
			
			//Stores the drivers list in a 2d array
			String[][] holder = new String[len][];		
			
			/*goes through the incoming drivers array, spits each one and stores 
			 * each driver with their location and load as an array, and stores that in the holder array*/
			for (int i = 0; i < drivers.toArray().length; i++) {
				holder[i] = ((String) drivers.toArray()[i]).split(", ");
			}
			
			//this holds the lowest load. Initially it holds the first drivers load from the array
			String[] lowestLoad = holder[0];
			
			for (int i = 0; i < holder.length; i++) {
				//if the current drivers load is less than the previous drivers load the new load is stored in last low and the array is stored in out.
				if (Integer.parseInt(holder[i][2]) < Integer.parseInt(lowestLoad[2])) {
					lowestLoad = holder[i];
				}
				
			}
			out = lowestLoad[0];
		}
		return out;
	}

	public static void printReceipt( String[] content, ArrayList<String> amountOfItem, ArrayList<String> restItem, ArrayList<String> restPriceList) 
	{	
			try {
				FileWriter receiptWriter = new FileWriter("receipt.txt");
				//if no drivers are available, print the error receipt else print the full receipt
				if (content[8].isEmpty()){
					receiptWriter.write("Sorry! Our drivers are too far away from you to be able to deliver to your location.");
				}else {
					//each item is used from the content array
					receiptWriter.write("Order Number " + content[12]);
					receiptWriter.write("\r\nCustomer: " + content[0]);
					receiptWriter.write("\r\n" + "Email: " + content[1]);
					receiptWriter.write("\r\n" + "Phone Number: " + content[2]);
					receiptWriter.write("\r\n" + "Location: " + content[3]);
					receiptWriter.write("\r\n");
					receiptWriter.write("\r\n" + "You have ordered the following from " + content[4] + " in " + content[5] + ":");
					receiptWriter.write("\r\n");
					for (int i = 0; i < restItem.size(); i++) {
					receiptWriter.write("\r\n" + amountOfItem.get(i) + " x " + restItem.get(i) + " (R" + restPriceList.get(i) + ")");
					}
					receiptWriter.write("\r\n");
					receiptWriter.write("\r\n" + "Special instructions: " + content[6]);
					receiptWriter.write("\r\n");
					receiptWriter.write("\r\n" + "Total: R" + content[7]);
					receiptWriter.write("\r\n");
					receiptWriter.write("\r\n" + content[8] + " is nearest to the restaurant and so they will be delivering your order to you at: ");
					receiptWriter.write("\r\n");
					receiptWriter.write("\r\n" + content[9]);
					receiptWriter.write("\r\n" + content[10]);
					receiptWriter.write("\r\n");
					receiptWriter.write("\r\n" + "If you need to contact the restaurant, their number is " + content[11] + ".");
					
					//the customers location is only added if the order was successfull, as to not fill the file up with fake locations
					writeCustomerLocations(content[0] ,content[3]);
					//adds one more to the load of which ever driver takes the delivery
					updateDriverList(content[8]);
				}
				
				receiptWriter.close();
			} catch (IOException e) {
				System.out.println("An error occurred.");
				e.printStackTrace();
			}
	}
	
	public static LinkedHashMap<String, String> readRestaurant(String restName) throws IOException {
		//reads the file with the inputed name
		String restaurantIn = restName.toLowerCase() + ".txt";
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();

	    String line;
	    BufferedReader reader = new BufferedReader(new FileReader(restaurantIn));
	    //reads through the file and creates a map
	    while ((line = reader.readLine()) != null)
	    {
	        String[] parts = line.split(":", 2);
	        if (parts.length >= 2)
	        {
	            String key = parts[0];
	            String value = parts[1];
	            map.put(key, value);
	        } 
	    }

	    reader.close();
		return map;
	}
	
	public static ArrayList<String> showAllRestaurants() throws IOException {
		//reads the restaurant file and returns the contents
		String filePath = "restaurantList.txt";
		ArrayList<String> restaurantList = new ArrayList<String>();

	    String line;
	    BufferedReader reader = new BufferedReader(new FileReader(filePath));
	    //writes each line in the file to restaurantList
	    while ((line = reader.readLine()) != null)
	    {
	    	restaurantList.add(line);
	    }

	    reader.close();
		return restaurantList;
	}
	
	public static boolean readRestaurantList(String restName) throws IOException {
		//this method is used to check that a restaurant that the user has requested is catered for
		boolean available = false;
		File myObj = new File("restaurantList.txt");
		ArrayList<String> restaurants = new ArrayList<String>();
		Scanner myReader = new Scanner(myObj);
		
	    while (myReader.hasNextLine())
	    {
	    	String rest = myReader.nextLine();
	    	restaurants.add(rest);
	    }
	    //if it does exist available is changed to true
	    for (String index : restaurants) {
	    	if (index.equalsIgnoreCase(restName) ) {
	    		available = true;
	    	}
	    }

	    myReader.close();
		return available;
	}
	
	public static void showMenu(LinkedHashMap<String, String> map) {
		//the info buffer is to skip the 3 pieces of information in each restaurant file
		int infoBuffer = 0;
		System.out.println("Here is the menu for " + map.get("Name") + ":\n");
		for ( String key : map.keySet() ) {
			infoBuffer++;
			//once the information has been passed the rest of the information is printed
			if (infoBuffer > 3) {
				System.out.println(key + " : R" + map.get(key));
			}
	        
	    }
		System.out.println();
	}
	
	public static void updateOrderNumber() {
		try {
			File file = new File("order_number.txt");
			Writer output;
			 
			// If the file doesn't exist, it creates a new one initiates it with 1 as it would be the first order number.
			if (file.createNewFile()) {
				output = new BufferedWriter(new FileWriter(file, true));

				output.append("1");
				output.close();
			} 
			// If the file already exists, the 1 added to the existing number
			else {
				BufferedReader nameFile = new BufferedReader(new FileReader("order_number.txt"));
		        StringBuffer inputBuffer = new StringBuffer();
		        String line;
		        
		        //stores the read number to input buffer
		        while ((line = nameFile.readLine()) != null) {
		            inputBuffer.append(line);
		        }
		        nameFile.close();
		        
		        int inputStr;
		        //if for some reason the file is there but is empty, a 0 is added to avoid errors
		        if ((inputBuffer.toString()).isEmpty()) {
		        	inputStr = 0;
		        }else {
		        	inputStr = Integer.parseInt(inputBuffer.toString());
		        }
		        
		        FileWriter myWriter = new FileWriter("order_number.txt");
		        //adds 1 to the old number and replaces the old one
		        myWriter.write(Integer.toString(inputStr  + 1));
		        myWriter.close();
			}

		} 
		catch (IOException e) {
			System.err.println("An error occurred.");
			e.printStackTrace();
		}
	}
	
	public static String fetchOrderNumber() {
		String currentOrderNumber = "";
		try {
			BufferedReader nameFile = new BufferedReader(new FileReader("order_number.txt"));
	        StringBuffer inputBuffer = new StringBuffer();
	        String line;
	        
	        //reads the first line (the order number)
	        while ((line = nameFile.readLine()) != null) {
	            inputBuffer.append(line);
	        }
	        nameFile.close();
	        //stores it as a string and returned
	        currentOrderNumber = inputBuffer.toString();
		} 
		catch (IOException e) {
		     	System.err.println("An error occurred.");
		    	e.printStackTrace();
		}
		return currentOrderNumber;
	}
	
	public static void writeNameAndOrder(String name, String number) {
		try {
			 File file = new File("name_&_order_number.txt");
			 Writer output;
			 
			// If the file doesn't exist, it creates a new one and appends the users name and order number.
			 if (file.createNewFile()) {
				output = new BufferedWriter(new FileWriter(file, true));
				//output.append("User name and Order Number:");
				output.append(name + " - " + number);
				output.close();
			} 
			// If the file already exists, each line is read, stored, sorted and printed back to the file
			else {
				BufferedReader nameFile = new BufferedReader(new FileReader("name_&_order_number.txt"));
				List<String> namesAndNums = new ArrayList<String>();
				
				String inputLine;
				//each line is added to the list array
				while ((inputLine = nameFile.readLine()) != null) {
					namesAndNums.add(inputLine);
				}
				//this adds the new name and order number
				namesAndNums.add(name + " - " + number);
				nameFile.close();
				//the list is then sorted
				Collections.sort(namesAndNums);
				
				FileWriter fileWriter = new FileWriter("name_&_order_number.txt");
				//each line if the sorted array is written to the text file
				for (String outputLine : namesAndNums) {
					fileWriter.write(outputLine + "\n");
				}
				fileWriter.close();
			}

		} 
		catch (IOException e) {
		      	System.err.println("An error occurred.");
		      	e.printStackTrace();
		}
	}
	
	public static void writeCustomerLocations(String name, String userLocation) {
		try {
			// This chunk corrects the cases so the program wont make cape town and Cape Town two different locations
			// makes the location lower case
			String location = userLocation.toLowerCase();
			// each letter including the spaces are stored to charArray
			char[] charArray = location.toCharArray();
			boolean foundSpace = true;
			
			for(int i = 0; i < charArray.length; i++) {
				// if the character is a letter
				if(Character.isLetter(charArray[i])) {
					// satisfied if the previous character was a space
					if(foundSpace) {
						// changes the current letter into upper case and sets foundSpace to false
						charArray[i] = Character.toUpperCase(charArray[i]);
						foundSpace = false;
					}
				} else {
					// when this is true, the next character will be upper cased
					foundSpace = true;
				}
			}
			//this is used as the new location variable
			location = String.valueOf(charArray);
			
			File file = new File("customer_locations.txt");
			Writer output;
			 
			/* If the file doesn't exist, this creates a new one and initiates it with the users location and name..*/
			if (file.createNewFile()) {
				output = new BufferedWriter(new FileWriter(file, true));
				
				output.append(location + ":");
				output.append("\n" + name);
				output.close();
			} 
			// If the file already exists, each line is read, stored, sorted and printed back to the file
			else {
				BufferedReader readFile = new BufferedReader(new FileReader("customer_locations.txt"));
				
				String inputLine;
				List<String> namesAndLoc = new ArrayList<String>();
				//reads through the file
				while ((inputLine = readFile.readLine()) != null) {
					//if the current line contains the users location
					if ((inputLine.toLowerCase()).contains(location.toLowerCase())) {
						//the current line is replaced with the Location and the name is written to the next line
						namesAndLoc.add(location + ":");
						namesAndLoc.add(name);
					}
					else {
						namesAndLoc.add(inputLine);
					}
				}
				readFile.close();
				
				/*after the file has read, namesAndLoc is checked to see if it doesn't contains the users location
				if it doesn't, the new location title is added and the users name added */
				if(!namesAndLoc.contains(location + ":")) {
					//a space is added for clarity in the text file
					namesAndLoc.add("");
					namesAndLoc.add(location + ":");
					namesAndLoc.add(name);
				}

				FileWriter fileWriter = new FileWriter("customer_locations.txt");
				//the contents are then written back to the file
				for (String outputLine : namesAndLoc) {
					fileWriter.write(outputLine + "\n");
				}
				fileWriter.close();
			}
			
		} 
		catch (IOException e) {
		      	System.err.println("An error occurred.");
		    	e.printStackTrace();
		}
	}
	
	public static void updateDriverList(String driverName) {
		try {
			BufferedReader readFile = new BufferedReader(new FileReader("drivers-info.txt"));
			
			String inputLine;
			List<String> drivers = new ArrayList<String>();
			//reads through the file
			while ((inputLine = readFile.readLine()) != null) {
				//if the current line contains the drivers name
				if (inputLine.contains(driverName)) {
					//the current line is split
					String[] splitLine = inputLine.split(", ");
					//converts the driver load to a int, adds one the converts it back to a string
					splitLine[2] = Integer.toString(Integer.parseInt(splitLine[2]) + 1);
					drivers.add(String.join(", ", splitLine));
				}
				else {
					drivers.add(inputLine);
				}
			}
			readFile.close();
			
			FileWriter fileWriter = new FileWriter("drivers-info.txt");
			//the contents are then written back to the file
			for (String outputLine : drivers) {
				fileWriter.write(outputLine + "\n");
			}
			fileWriter.close();
			
		} 
		catch (IOException e) {
		      	System.err.println("An error occurred.");
		      	e.printStackTrace();
		}
	}
}
