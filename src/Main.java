import java.util.Scanner;

// Joshua Colicchio
// Program integrating skills learned in COP 2006
public class Main {

	public static void main(String[] args) {
		System.out.println("Welcome to my integration project!");
		PromptUser();
	}
	
	public static void PromptUser() {
		System.out.println("Type a data type to learn more about it.");
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
			default: 
				System.out.println("I have no idea what a \"" + response + "\" is..."); 
				PromptUser(); 
				break;
		}
	}
	
	private static void DisplayData(int TypeId) {
		switch (TypeId) {
			case 0: System.out.println("A String is a collection of chars."); break; // string
			case 1: System.out.println("An int is a whole number."); break; // int
			case 2: System.out.println("A double is a 64-bit number with a fractional element."); break; // double
			case 3: System.out.println("A float is a 32-bit number with a fractional element."); break; // float
			case 4: System.out.println("A boolean is a true / false statement."); break; // bool
		}
		PromptUser();
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