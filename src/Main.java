import java.util.Scanner;

// Joshua Colicchio
// Program integrating skills learned in COP 2006
public class Main {

	public static void main(String[] args) {
		System.out.println("Welcome to my integration project!\nType a data type to learn more about it.");
		DisplayMenu();	
	}
	
	public static String PromptUser() {
		
		return "uh";
	}
	
	public static void DisplayMenu() {
		String response = "";
		switch(choice.toLowerCase()) {
		case "string": response = "you typed string";
		case "int": response = "you typed int";
		case "double": response = "you typed double";
		case "float": response = "you typed float";
		case "boolean": response = "you typed boolean";
		default: response = "I have no idea what a " + choice + " is...";
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