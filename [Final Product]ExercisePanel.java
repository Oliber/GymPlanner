import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.*;
import java.util.List;
import java.util.concurrent.*;

public class ExercisePanel extends JPanel {
	
	public ExercisePanel(BlockingQueue<String> queue) throws FileNotFoundException {
		this.setBackground(Color.RED);
		this.setSize(500, 800);
		this.setVisible(true);
		this.setLayout(new GridBagLayout());
		GridBagConstraints con = new GridBagConstraints();
		con.insets = new Insets(10,10,10,10);
		
		// Loading data from files
		Set<String> chestSelected = new HashSet<String>();
		createComponents("CHEST", 0, con, chestSelected);
		Set<String> legSelected = new HashSet<String>();
		createComponents("LEG", 5, con, legSelected);
		Set<String> armSelected = new HashSet<String>();
		createComponents("ARM", 10, con, armSelected);
		
		JButton save = new JButton("Save");
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				queue.offer(chestSelected.toString());
				queue.offer(legSelected.toString());
				queue.offer(armSelected.toString());
			}
		});
		
		con.gridwidth = 2;
		con.gridx = 1;
		con.gridy = 15;
		this.add(save, con);
	}
	
	public void createComponents(String muscle, int spacing, GridBagConstraints con, 
															Set<String> selected) throws FileNotFoundException {
		List<String> listOfExercises = loadExercises(muscle + ".txt");
		String[] exercises = createStringArray(listOfExercises);
		
		JLabel label = new JLabel(muscle + " Exercises");
		JTextField view = new JTextField(30);
		view.setEditable(false);
		view.setText(" ");
		JComboBox<String> list = new JComboBox<String>(exercises);
		JButton add = new JButton("Add");
		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selected.add(list.getSelectedItem().toString());
				view.setText(selected.toString());
			}
		});
		
		con.gridwidth = 1;
		con.gridx = 0;
		con.gridy = 0 + spacing;
		this.add(label, con);
		con.gridx = 0;
		con.gridy = 1 + spacing;
		this.add(list, con);
		con.gridx = 1;
		con.gridy = 1 + spacing;
		this.add(add, con);
		con.gridx = 0;
		con.gridwidth = 2;
		con.gridy = 2 + spacing;
		this.add(view, con);
	}
	
	public List<String> loadExercises(String fileName) throws FileNotFoundException {
		File muscle = new File(fileName);
		Scanner read = new Scanner(muscle);
		List<String> exercises = new ArrayList<String>();
		while(read.hasNextLine()) {
			exercises.add(read.nextLine());
		}
		return exercises;
	}
	
	public void create() {
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(this);
		f.pack();
		f.setVisible(true);
	}
	
	public String[] createStringArray(List<String> list) {
		String[] exercises = new String[list.size()];
		for(int i = 0; i < list.size(); i++) {
			exercises[i] = list.get(i);
		}
		return exercises;
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		BlockingQueue<String> q = new PriorityBlockingQueue<String>();
		JFrame test = new JFrame();
		test.setSize(400, 900);
		test.add(new ExercisePanel(q));
		test.setVisible(true);
		test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
