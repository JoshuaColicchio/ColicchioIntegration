import java.util.Scanner;

// Joshua Colicchio
// Program integrating skills learned in COP 2006
public class Main {

	public static void main(String[] args) {
		System.out.println("Welcome to my integration project!");
		PromptUser();
	}
	
	public static void PromptUser() {
		// The "final" keyword makes it so the program cannot change the value of the variable.
		// IE: If I typed PromptMessage = "some new message", the compiler would throw an error.
		final String PROMPTMSG = "Type a data type to learn more about it. Type \"Options\" for available data types";
		System.out.println(PROMPTMSG);
		Scanner iostream = new Scanner(System.in);
		String UserInput = iostream.nextLine();
		DisplayMenu(UserInput);
		iostream.close();
	}
	
	private static void DisplayMenu(String response) {
		switch(response.toLowerCase()) {
			case "string": DisplayData(0); break;
			case "int": DisplayData(1); break;
			case "double": DisplayData(2); break;
			case "float": DisplayData(3); break;
			case "boolean": DisplayData(4); break;
			case "options": DisplayData(999); break;
			default: 
				System.out.println("I have no idea what a \"" + response + "\" is..."); 
				PromptUser(); 
				break;
		}
	}
	
	private static void DisplayData(int i) {
		switch (i) {
			case 0:  // string
				System.out.println("A String is a collection of chars."); 
				DisplayAdditionalInfo(0);
				break;
			case 1: // int
				System.out.println("An int is a whole number."); 
				break; 
			case 2: // double
				System.out.println("A double is a 64-bit number with a fractional element."); 
				break; 
			case 3: // float
				System.out.println("A float is a 32-bit number with a fractional element."); 
				break; 
			case 4: // bool
				System.out.println("A boolean is a true / false statement."); 
				break; 
			case 999: // options
				System.out.println("Available options:\n\tString\n\tInt\n\tDouble\n\tFloat\n\tBoolean\n\tOptions"); 
				break; 
		}
	}

	private static void DisplayAdditionalInfo(int i) {
		Scanner input = new Scanner(System.in);
		switch (i) {
			case 0: // String
				System.out.println("The String class has methods built in that can be useful.\n"
						+ "Three such methods are:\n\tJoin\n\tLength\n\ttoLowerCase\n"
						+ "Please type the name of one of the choices.");
				String response = input.nextLine();
				//if (response.toLowerCase().equals("test")) { System.out.println("yep"); }
				break;
			case 1: 
				break;
			case 2: 
				break;
			default:
				break;
		}
	}

}

/*
 * A list and description of Java built-in data types within the user interface or in comments
	Use the four main types of variables (boolean, int, double, String) with appropriate names and define (in English) variable and scope
	Use final and describe what it means in a comment
	
 * When using a Scanner with strings
 * you need to use .nextLine() to 
 * move to the new line / clear the buffer
 * when going from numbers to strings
 */