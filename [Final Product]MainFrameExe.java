import javax.swing.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.*;
import java.io.*;

public class MainFrameExe {
	
	public static final BlockingQueue<String> queue = new PriorityBlockingQueue<String>();
	public static String name = "";
	public static final JFrame frame = new JFrame("Gym Planner");
	
	public static void main(String[] args) throws FileNotFoundException {
		
		// Initialize the Main Frame
		System.out.println("Initializing the Main Frame");
		frame.setSize(400, 200);
		
		// Set up components used for getting the name for the file
		System.out.println("Setting up the intro panel");
		frame.add(intro());
		frame.setVisible(true);
		try {
			name = queue.take();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Retrieved input : " + name);
		File saveTo = new File(name + ".txt");
		
		System.out.println("Loading next panel...");
		MusclePanel muscle = new MusclePanel(queue);
		frame.add(muscle);
		frame.repaint();
		frame.pack();
		frame.setVisible(true);
		String difficulty = "";
		try {
			difficulty = queue.take();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		frame.remove(muscle);
		frame.repaint();
		frame.setVisible(false);
		System.out.println("Desired Difficulty : " + difficulty);
		
		System.out.println("Loading next panel...");
		ExercisePanel exercises = new ExercisePanel(queue);
		frame.add(exercises);
		frame.pack();
		frame.repaint();
		frame.setVisible(true);
		
		String chestSelected = "";
		String legSelected = "";
		String armSelected = "";
		try {
			chestSelected = queue.take();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		try {
			legSelected = queue.take();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		try {
			armSelected = queue.take();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		frame.setVisible(false);
		frame.remove(exercises);
		
		// now that i have all the data i need, time to write
		PrintStream write = new PrintStream(saveTo);
		write.println("Difficulty : " + difficulty); 
		saveData("Chest", write, chestSelected);
		saveData("Legs", write, legSelected);
		saveData("Arms", write, armSelected);
		System.out.println("Done");
	}
	
	public static JPanel intro() {
		// Initializing the Panel
		JPanel intro = new JPanel(new GridBagLayout());
		GridBagConstraints con = new GridBagConstraints();
		con.insets = new Insets(10, 10, 10, 10);
		
		// Initializing the Components
		JLabel introText = new JLabel("Welcome to your customizable Gym Planner");
		JLabel nameThisFile = new JLabel("Please enter a name for this file.");
		JTextField response = new JTextField(6);
		response.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String look = response.getText();
				if(look.length() > 0) {
					// After the TextField gets a proper String, give it to the queue 
					// and close this window
					queue.offer(look);
					frame.remove(intro);
					frame.repaint();
					frame.setVisible(false);
				}
			}
		});
		
		// Adding Components to the Panel
		con.gridx = 0;
		con.gridy = 0;
		con.gridwidth = 2;
		intro.add(introText, con);
		con.gridx = 0;
		con.gridy = 1;
		con.gridwidth = 1;
		intro.add(nameThisFile, con);
		con.gridx = 1;
		con.gridy = 1;
		intro.add(response, con);
		return intro;
	}
	
	public static void saveData(String muscle, PrintStream write, String exercises) {
		write.println(muscle + " : ");
		write.println("Exercises : " + exercises);
		write.println();
	}
}
