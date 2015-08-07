import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.*;

import javax.swing.*;

import java.io.*;


public class MusclePanel extends JPanel {
	public class DifficultyNodes {
		private String name;
		private String repSet;
		
		public DifficultyNodes(String name, String repSet) {
			this.name = name;
			this.repSet = repSet;
		}
		
		public String toString() {
			return this.name;
		}
		
		public String getRep() {
			return this.repSet;
		}
	}
	public MusclePanel(BlockingQueue<String> queue) {
		this.setLayout(new GridBagLayout());
		this.setSize(300,  200);
		this.setBackground(Color.CYAN);
		GridBagConstraints con = new GridBagConstraints();
		con.insets = new Insets(10, 10, 10, 10);
		
		
		JLabel difficulty = new JLabel("Type of Training");
		DifficultyNodes[] choices = {new DifficultyNodes("Endurance", "12 x 10 x 8 with 20 second rest"), 
															  new DifficultyNodes("Strength", "5 x 5 x 5 with 2 minute rest"),
															  new DifficultyNodes("Hybrid", "8 x 8 x 8 with 40 second rest")};
		JComboBox<DifficultyNodes> list = new JComboBox<DifficultyNodes>(choices);
		JButton next = new JButton("Next");
		next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)  {
				String difficulty = ((DifficultyNodes)list.getSelectedItem()).getRep();
				queue.offer(difficulty);
				
			}
		});
		
		con.gridx = 0;
		con.gridy = 1;
		this.add(difficulty, con);
		con.gridx = 1;
		con.gridy = 1;
		this.add(list, con);
		con.gridx = 0;
		con.gridy = 2;
		con.gridwidth = 2;
		this.add(next, con);
	}
	
	public void create() {
		JFrame frame = new JFrame("Page 2");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(this);
		frame.pack();
		frame.setVisible(true);
	}
}
