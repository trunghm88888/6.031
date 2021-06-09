import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.SoftBevelBorder;

import flashcards.App;
import flashcards.FlashCard;
import flashcards.Profile;
import javax.swing.border.MatteBorder;

public class RecordPanel {

	private JFrame frame;
	private JTextField txtHe;
	private JTextArea txtA;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RecordPanel window = new RecordPanel();
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
	public RecordPanel() throws Exception {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws Exception 
	 */
	private void initialize() throws Exception {
		
		LinkedList<FlashCard> addCache = new LinkedList<FlashCard>();
		LinkedList<FlashCard> deleteCache = new LinkedList<FlashCard>();
		Profile p = App.loadProfile("Trung");
		LinkedList<FlashCard> fs = new LinkedList<FlashCard>();
		fs.addAll(p.getSubject("Math"));
		
		frame = new JFrame();
		frame.setBounds(0, 0, 1280, 720);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new MatteBorder(1, 1, 0, 1, (Color) new Color(0, 0, 0)));
		panel.setBounds(0, 0, 1265, 78);
		frame.getContentPane().add(panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] {165, 1100, 0};
		gbl_panel.rowHeights = new int[]{78, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JLabel searchLabel = new JLabel("Search");
		searchLabel.setHorizontalAlignment(SwingConstants.CENTER);
		searchLabel.setFont(new Font("Tahoma", Font.PLAIN, 30));
		GridBagConstraints gbc_searchLabel = new GridBagConstraints();
		gbc_searchLabel.fill = GridBagConstraints.BOTH;
		gbc_searchLabel.insets = new Insets(0, 0, 0, 5);
		gbc_searchLabel.gridx = 0;
		gbc_searchLabel.gridy = 0;
		panel.add(searchLabel, gbc_searchLabel);
		
		txtA = new JTextArea();
		txtA.setFont(new Font("Tahoma", Font.PLAIN, 24));
		GridBagConstraints gbc_txtA = new GridBagConstraints();
		gbc_txtA.fill = GridBagConstraints.BOTH;
		gbc_txtA.gridx = 1;
		gbc_txtA.gridy = 0;
		panel.add(txtA, gbc_txtA);
		txtA.setColumns(10);
		
		JPanel buttonPane = new JPanel();
		buttonPane.setBounds(1139, 77, 126, 608);
		frame.getContentPane().add(buttonPane);
		buttonPane.setLayout(new GridLayout(0, 1, 0, 0));
		
		JButton saveButton = new JButton("Save");
		saveButton.setFont(new Font("Tahoma", Font.PLAIN, 30));
		buttonPane.add(saveButton);
		
		JButton undoButton = new JButton("Undo");
		undoButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					if (addCache.getLast() != null) {
						FlashCard newF = addCache.pop();
						fs.remove(newF);
					} else addCache.removeLast();
					
					fs.add(deleteCache.pop());
					frame.getContentPane().remove(frame.getContentPane().getComponents().length - 1);
					loadScrollPane(fs, addCache, deleteCache);
				}
			}
		});
		undoButton.setFont(new Font("Tahoma", Font.PLAIN, 30));
		undoButton.setVerticalTextPosition(SwingConstants.TOP);
		buttonPane.add(undoButton);
		
		JButton backButton = new JButton("Back");
		backButton.setFont(new Font("Tahoma", Font.PLAIN, 30));
		buttonPane.add(backButton);
		
		loadScrollPane(fs, addCache, deleteCache);
		
	}
	
	private void loadScrollPane(List<FlashCard> fs, List<FlashCard> addCache, List<FlashCard> deleteCache) {
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel_2.setBounds(0, 77, 1140, 608);
		panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.Y_AXIS));
		
		for (FlashCard f : fs) {
			JTextArea q = new JTextArea(f.getFront());
			q.setFont(new Font("Tahoma", Font.PLAIN, 25));
			q.setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));
			q.setEditable(false);
			
			JTextArea a = new JTextArea(f.getBack());
			a.setFont(new Font("Tahoma", Font.PLAIN, 25));
			a.setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));
			a.setEditable(false);
			
			JPanel fPanel = new JPanel();
			fPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
			fPanel.setSize(1138, 200);
			GridLayout grid = new GridLayout(0, 1);
			grid.setVgap(5);
			fPanel.setLayout(grid);
			fPanel.add(q);
			fPanel.add(a);
			
			JPopupMenu menu = new JPopupMenu();
			JMenuItem delete = new JMenuItem("Delete");
			delete.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					int confirm = JOptionPane.showConfirmDialog(null, "Do you want to delete this flashcard?", "Confirmation",  JOptionPane.OK_CANCEL_OPTION);
					if (confirm == JOptionPane.OK_OPTION) {
						addCache.add(null);
						deleteCache.add(f);
						fs.remove(f);
						frame.getContentPane().remove(frame.getContentPane().getComponents().length - 1);
						loadScrollPane(fs, addCache, deleteCache);
					}
				}
			});
			menu.add(delete);
			menu.add(new JMenuItem("Edit"));
			
			fPanel.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					fPanel.setFocusable(true);
					menu.show(fPanel, e.getX(), e.getY());
				}
			});
			
			for (Component c : fPanel.getComponents()) {
				c.addMouseListener(new MouseAdapter() {
					@Override
					public void mousePressed(MouseEvent e) {
						fPanel.setFocusable(true);
						menu.show(fPanel, e.getX(), e.getY());
					}
				});
			}
			
			panel_2.add(fPanel);
		}
		
		JScrollPane scrollPane = new JScrollPane(panel_2);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(0, 77, 1140, 578);
		frame.getContentPane().add(scrollPane);
		frame.invalidate();
		frame.validate();
		frame.repaint();
	
	}
}
