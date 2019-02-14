import java.util.Random;
import java.util.Scanner;

// Joshua Colicchio
// Program integrating skills learned in COP 2006

/*
 * A variable in Java is like a box that you can store things in. The type of thing you can store
 * in the box depends on the type of box that you create. Realistically, a variable is a name for a section of memory.
 *
 * Scope in Java is the level of access a variable has / where a variable 'exists'.
 * For example, if you declare a variable inside of a method, that variable is scoped only in that method, not outside of it.
 */
public class Main {

	// The "final" keyword makes it so the program cannot change the value of the variable.
	// IE: If I typed PromptMessage = "some new message", the compiler would throw an error.
	final static String PROMPT_MSG = "Type a data type to learn more about it. Type \"Options\" for available data types";
	
	public static void main(String[] args) {
		System.out.println("Welcome to my integration project!");
		promptUser();
	}
	
	public static void promptUser() {
		System.out.println(PROMPT_MSG);
		Scanner iostream = new Scanner(System.in);
		String userInput = iostream.nextLine();
		// The String.equals method is used instead of '==' because
		// Java looks at '==' by checking whether or not both objects
		// occupy the same spot in memory.
		if (userInput.equals("") || userInput.isEmpty()) {
			promptUser("Error: Please type a data type.");
			return;
		} else {
			// The String.trim method removes any whitespace from the beginning and
			// end of the string.
			displayMenu(userInput.trim());
		}
	}
	
	public static void promptUser(String customPromptMSG) {
		System.out.println(customPromptMSG);
		Scanner iostream = new Scanner(System.in);
		String userInput = iostream.nextLine();
		if (userInput.compareTo("") != 0 || userInput.isEmpty()) {
			promptUser("Error: Please type a data type.");
			return;
		} else {
			// The String.trim method removes any whitespace from the beginning and
			// end of the string.
			displayMenu(userInput.trim());
		}
	}
	
	// "private static void displayMenu" is a header,
	// and "(String response)" is a parameter
	private static void displayMenu(String response) {
		/*
		 *  The String.toLowerCase method converts a given string to
		 *  its lower case counterpart.
		 *  IE - "Hello World".toLowerCase() will return
		 *  "hello world"
	 	 */
		switch(response.toLowerCase()) {
			// displayData() below is an example of a method call
			case "string": displayData(0); break;
			case "int": displayData(1); break;
			case "double": displayData(2); break;
			case "float": displayData(3); break;
			case "boolean": displayData(4); break;
			case "random": Random rand = new Random();
						   displayData(rand.nextInt(5));
						   break;
			case "options": displayData(999); break;
			default: 
				// The String.toUpperCase method converts a given string into its upper case counterpart.
				// IE - "Hello World".toUpperCase() will return "HELLO WORLD"
				promptUser("I have no idea what a \"" + response.toUpperCase() + "\" is..."); 
				break;
		}
	}
	
	private static void displayData(long input) {
		// By casting the int i as a long, I can assign the value of i to the 
		// long L, and use L for further operations.
		int switchInput = (int)input;
		switch (switchInput) {
			case 0:  // string
				System.out.println("A String is a collection of chars."); 
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
		promptUser();
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
 * 
 * A CALL contains an ARGUMENT. A HEADER contains a Parameter
 * 
 * A Method is a group of statements 
 */
