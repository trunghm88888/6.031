package flashcards;

import java.awt.EventQueue;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class TestFrame {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestFrame window = new TestFrame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws Exception 
	 */
	public TestFrame() throws Exception {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws Exception 
	 */
	private void initialize() throws Exception {
		frame = new JFrame();
		frame.setBounds(0, 0, 1000, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Profile p = App.loadProfile("Trung");
		List<FlashCard> fs = new ArrayList<>();
		fs.addAll(p.getSubject("Math"));
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 1000, 600);
		GridLayout grid = new GridLayout(0, 1);
		grid.setVgap(10);
		panel.setLayout(grid);
		
		for (FlashCard f : fs) {
			FlashCardInfo info = new FlashCardInfo(f, 1000, p, new LinkedList(), new LinkedList(), panel, new JScrollPane());
			panel.add(info);
		}
		
		frame.add(new JScrollPane(panel));
	}

}
