/*
 * Allen Tran
 * GymScheduler Project
 * Version 1 : The Very Basic
 * 
 * Main Idea : This class will construct a work out 
 * routine and save it to a file.
 */

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

public class SchedulerMain {
	
	public static void main(String[] args) throws FileNotFoundException {
		
		Scanner user = new Scanner(System.in);
		intro();
		System.out.print("How many muscle groups would you like to work out today?    ");
		int num = user.nextInt();
		List<String> categories = new ArrayList<String>();
		for(int i = 0; i < num; i++) {
			System.out.println("Name this muscle group    ");
			String group = user.next();
			categories.add(group);
		}
		construct(categories, user);
	}
	
	/*
	 * Gives the user a short description of this application.
	 */
	public static void intro() {
		System.out.println("Welcome to my workout scheduler. To get a specific routine please");
		System.out.println("answer the following questions to suit your needs.");
	}
	
	public static void construct(List<String> groups, Scanner user) throws FileNotFoundException {
		System.out.print("What would you like to name this work out?    ");
		String name = user.next() + ".txt";
		File newDoc = new File(name);
		PrintStream output = new PrintStream(newDoc);
		for(int i = 0; i < groups.size(); i++) {
			String muscleGroup = groups.get(i);
			if(verify(muscleGroup)) {
				chooseExercises(muscleGroup, output, user);
			}else {
				System.out.println("Sorry your input \"" + muscleGroup + "\""
						+ " cannot be recognized, please try again with more a more"
						+ " common phrase.    ");
				String reattempt = user.nextLine();
				groups.add(reattempt);
			}
		}
		System.out.println("Done! Checkout your workout in your " + name + " text file.");
	}
	
	/*
	 * Returns true if the user's input is recognizable in the database, else return false;
	 */
	public static boolean verify(String muscle) throws FileNotFoundException {
		File muscles = new File("MuscleGroups.txt");
		muscle = muscle.toUpperCase();
		if(muscles.exists()) {
			Scanner file = new Scanner(muscles);
			String copy = "";
			while(file.hasNextLine()) {
				copy += file.nextLine() + " ";
			}
			return copy.contains(muscle);
		}else {
			System.out.println("You are missing the \"MuscleGroups\" file. Please put that file"
					+ " in the same directory as the application");
			throw new FileNotFoundException();
		}
	}
	
	/*
	 * Given a String representing the muscle group, an int num to represent the 
	 * number of exercises for that muscle group and a PrintStream output, writes 
	 * the desired amount of exercises ( if exists in current database) to output.
	 */
	public static void chooseExercises(String group, PrintStream output, Scanner user)
																						throws FileNotFoundException {
		group = group.toUpperCase();
		output.println(group);
		Exercise muscle = new Exercise(group);
		muscle.choose(output, user);
	}
}
