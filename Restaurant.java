import java.util.ArrayList;

public class Restaurant {
	
	//Attributes
	private String name;
	private String location;
	private String contactNum;
	private ArrayList<String> item = new ArrayList<String>();
	private ArrayList<String> itemPrice = new ArrayList<String>();
	private ArrayList<String> amountOfItem = new ArrayList<String>();
	private String specialPrep;	
	private double amountToPay;

	
	//Methods
		
	// SETTERS	
		public void setName(String name) {
			this.name = name;
		}
		public void setLocation(String location) {
			this.location = location;
		}
		public void setContact(String contact) {
			this.contactNum = contact;
		}
		public void setItem(String item) {
			(this.item).add(item);
		}
		public void setItemPrice(String itemPrice) {
			(this.itemPrice).add(itemPrice);
		}
		public void setAmountOfItem(String amountOfItem) {
			(this.amountOfItem).add(amountOfItem);
		}
		public void setPrep(String specialPrep) {
			this.specialPrep = specialPrep;
		}

		// GETTERS
		public String getName() {
			return name;
		}
		public String getLocation() {
			return location;
		}
		public String getContact() {
			return contactNum;
		}
		public ArrayList<String> getAmountOfItem() {
			return amountOfItem;
		}
		public String getItem(int n) {
			return item.get(n);
		}
		public ArrayList<String> getAllItems() {
			return item;
		}
		public ArrayList<String> getItemPrice() {
			return itemPrice;
		}
		public String getPrep() {
			return specialPrep;
		}
		public String getPrice() {
			//multiplies each items cost by the number of that item the user has selected
			for (int i = 0; i < item.size(); i++) {
				amountToPay += Double.valueOf(itemPrice.get(i)) * Double.valueOf(amountOfItem.get(i));
			}
			//formats the final total to a string with 2 decimal places.
			return String.format( "%.2f", amountToPay );
		}
}

