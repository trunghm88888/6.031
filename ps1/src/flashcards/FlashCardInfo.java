package flashcards;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.MatteBorder;

import net.miginfocom.swing.MigLayout;
import javax.swing.JButton;

public class FlashCardInfo extends JPanel {
	private JTextField sTextField;
	private JTextField tTextField;
	private JTextField dTextField;

	/**
	 * Create the panel.
	 * @throws Exception 
	 */
	
	public FlashCardInfo(FlashCard f, int panelWidth, Profile p, LinkedList<FlashCard> deleteCache, LinkedList<JPanel> deletePanelCache, JPanel infoPane, JScrollPane scroll) throws Exception {
		super();
		setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		setLayout(new MigLayout("", "[1000]", "[30:n,grow,center][30:n,grow,center][20][20][70][]"));
		
		JPanel qPane = new JPanel();
		add(qPane, "cell 0 0,grow");
		qPane.setLayout(new MigLayout("", "[120,center][880,center]", "[20:n,grow]"));
		
		JLabel lblNewLabel = new JLabel("Question");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		qPane.add(lblNewLabel, "cell 0 0,aligny center");
		
		JTextArea qTextArea = new JTextArea();
		qTextArea.setWrapStyleWord(true);
		qTextArea.setFont(new Font("Times New Roman", Font.BOLD, 20));
		qTextArea.setLineWrap(true);
		qPane.add(qTextArea, "cell 1 0,grow");
		
		JPanel aPane = new JPanel();
		add(aPane, "cell 0 1,grow");
		aPane.setLayout(new MigLayout("", "[120,center][880,center]", "[20:n,grow]"));
		
		JLabel lblNewLabel_1 = new JLabel("Answer");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		aPane.add(lblNewLabel_1, "cell 0 0,aligny center");
		
		JTextArea aTextArea = new JTextArea();
		aTextArea.setWrapStyleWord(true);
		aTextArea.setFont(new Font("Times New Roman", Font.BOLD, 20));
		aTextArea.setEditable(false);
		aTextArea.setLineWrap(true);
		aPane.add(aTextArea, "cell 1 0,grow");
		
		JPanel sPane = new JPanel();
		add(sPane, "cell 0 2,grow");
		sPane.setLayout(new MigLayout("", "[100,center][890,center]", "[20,center]"));
		
		JLabel lblNewLabel_2 = new JLabel("Subject");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		sPane.add(lblNewLabel_2, "cell 0 0,alignx center,aligny center");
		
		sTextField = new JTextField();
		sTextField.setFont(new Font("Times New Roman", Font.BOLD, 20));
		sTextField.setBorder(BorderFactory.createEmptyBorder());
		sTextField.setEditable(false);
		sPane.add(sTextField, "cell 1 0,growx");
		sTextField.setColumns(10);
		
		JPanel tPane = new JPanel();
		add(tPane, "cell 0 3,grow");
		tPane.setLayout(new MigLayout("", "[100,left][880,grow,center]", "[20]"));
		
		JLabel lblNewLabel_3 = new JLabel("Tier");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 20));
		tPane.add(lblNewLabel_3, "cell 0 0,alignx center,aligny center");
		
		tTextField = new JTextField();
		tTextField.setFont(new Font("Times New Roman", Font.BOLD, 20));
		tTextField.setBorder(BorderFactory.createEmptyBorder());
		tTextField.setEditable(false);
		tPane.add(tTextField, "cell 1 0,growx");
		tTextField.setColumns(10);
		
		qTextArea.setText(f.getFront()); aTextArea.setText(f.getBack()); sTextField.setText(f.getSubject()); tTextField.setText(Integer.toString(p.getTier(f)));
 
		JPanel panel = new JPanel();
		add(panel, "cell 0 4,grow");
		panel.setLayout(new MigLayout("", "[110][100][800]", "[40:n]"));
		 
		JLabel lblNewLabel_4 = new JLabel();
		lblNewLabel_4.setText("<html><body>Day to<br>practice</body></html>");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 20));
		panel.add(lblNewLabel_4, "cell 0 0,alignx center,aligny center");
		 
		dTextField = new JTextField(Integer.toString(p.getDayToPractice(f)));
		dTextField.setFont(new Font("Times New Roman", Font.BOLD, 20));
		dTextField.setBorder(BorderFactory.createEmptyBorder());
		dTextField.setEditable(false);
		panel.add(dTextField, "cell 1 0,growx");
		dTextField.setColumns(10);
		
		JButton btnNewButton = new JButton("Save");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnNewButton.setVisible(false); btnNewButton.setEnabled(false);
		add(btnNewButton, "cell 0 5,alignx center,aligny center");
		
		JPopupMenu editMenu = new JPopupMenu();
		
		JMenuItem delete = new JMenuItem("Delete");
		delete.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					deleteCache.add(f);
					deletePanelCache.add(panel);
					infoPane.remove(panel);
					scroll.setViewportView(infoPane);
					scroll.invalidate();
					scroll.validate();
					scroll.repaint();
				}
			}
		});
		editMenu.add(delete);
		
		JMenuItem edit = new JMenuItem("Edit");
		edit.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					qTextArea.setEditable(true);
					aTextArea.setEditable(true);
					sTextField.setEditable(true);
					tTextField.setEditable(true);
					dTextField.setEditable(true);
					btnNewButton.setVisible(true); btnNewButton.setEnabled(true);				}
			}
		});
		editMenu.add(edit);
		
		traverse(this, editMenu);
		
	}
	
	private void traverse(Container aContainer, JPopupMenu aMenu) {
	    for (final Component comp : aContainer.getComponents()) {
	        if (comp instanceof JComponent) {
	            ((JComponent) comp).setComponentPopupMenu(aMenu);
	        }
	        if (comp instanceof Container) {
	            traverse((Container) comp, aMenu);
	        }
	    }
	}
	
	public static void main(String[] args) throws Exception {
	}
}
