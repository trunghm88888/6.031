package flashcards;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class AppGUI {

	
	protected static Profile p;
	protected List<String> choosenSubjects;
	protected static JFrame frame;
	static final SimpleAttributeSet attrs=new SimpleAttributeSet();
	protected static JPanel StartPanel, ProfilePanel, AddFlashCardPanel, PracticePanel, DatabasePanel;
	private static final int FRAME_WIDTH = 1280;
	private static final int FRAME_LENGTH = 720;
	private static final int PANEL_WIDTH = 1260;
	private static final int PANEL_LENGTH = 700;
	private static final int PROFILE_PANEL_GREETING_LABEL_X = 230;
	private static final int PROFILE_PANEL_GREETING_LABEL_Y = 100;
	private static final int ADDFLASHCARD_PANEL_SUBJECTBOX_X = 268;
	private static final int ADDFLASHCARD_PANEL_SUBJECTBOX_Y = 137;
	private static final int ADDFLASHCARD_PANEL_ADDSUBJECTPANEL_X = 468;
	private static final int ADDFLASHCARD_PANEL_ADDSUBJECTPANEL_Y = 101;
	static final int PRACTICE_PANEL_PANE_X = 34;
	static final int PRACTICE_PANEL_PANE_Y = 90;
	static final int PRACTICE_PANEL_PREVIOUS_X = 123;
	static final int PRACTICE_PANEL_PREVIOUS_Y = 25;
	static final int PRACTICE_PANEL_NEXT_X = 934;
	static final int PRACTICE_PANEL_NEXT_Y = 545;
	static final int PRACTICE_PANEL_RESPONSE_X = 863;
	static final int PRACTICE_PANEL_RESPONSE_Y = 90;
	static final int PRACTICE_PANEL_SUBMITBUTTON_X = 95;
	static final int PRACTICE_PANEL_SUBMITBUTTON_Y = 351;
	static final ButtonGroup responseButton = new ButtonGroup();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StartPanel = buildStartPanel();
					AppGUI window = new AppGUI();
					ProfilePanel = window.buildProfilePanel();
					AddFlashCardPanel = window.buildAddFlashCardPanel();
					PracticePanel = window.buildPracticePanel();
					DatabasePanel = window.buildDatabasePanel();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AppGUI() {
		frame = new JFrame();
		frame.setBounds(0, 0, FRAME_WIDTH, FRAME_LENGTH);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setContentPane(StartPanel);
		frame.addWindowListener(new WindowAdapter() {
		    public void windowClosing(WindowEvent e) {
		    	if (p != null) App.saveProfile(p);
		    }
		});
	    StyleConstants.setAlignment(attrs,StyleConstants.ALIGN_CENTER);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private static JPanel buildStartPanel() {
		
		JPanel StartPanel = new JPanel();
		StartPanel.setBounds(0, 0, PANEL_WIDTH, PANEL_LENGTH);
		GridBagLayout gbl_StartPanel = new GridBagLayout();
		gbl_StartPanel.columnWidths = new int[] {300, 660, 300};
		gbl_StartPanel.rowHeights = new int[] {30, 180, 50, 180, 50, 180, 30};
		gbl_StartPanel.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_StartPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		StartPanel.setLayout(gbl_StartPanel);
		
		JLabel lblNewLabel = new JLabel("FlashCardApp 1.0");
		lblNewLabel.setBackground(Color.WHITE);
		lblNewLabel.setForeground(Color.BLACK);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 36));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.fill = GridBagConstraints.BOTH;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 1;
		StartPanel.add(lblNewLabel, gbc_lblNewLabel);
		
		JButton btnNewButton = new JButton("New Student");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					String name = JOptionPane.showInputDialog(null, "What's your name?", "Add new profile", JOptionPane.QUESTION_MESSAGE);
					if (name != null) {
						if (name.length() == 0) {
							JOptionPane.showMessageDialog(null, "Invalid name! At least 1 character!", "Error", JOptionPane.ERROR_MESSAGE);
						} else if (App.getProfileNames().contains(name)) {
							JOptionPane.showMessageDialog(null, "This name is existed!", "Error", JOptionPane.ERROR_MESSAGE);
						} else {
							p = new Profile(name);
							loadProfileData();
							frame.setContentPane(ProfilePanel);
						}
					}
				}
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 30));
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.fill = GridBagConstraints.BOTH;
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewButton.gridx = 1;
		gbc_btnNewButton.gridy = 3;
		StartPanel.add(btnNewButton, gbc_btnNewButton);
		
		JButton btnExistedStudent = new JButton("Existed Student");
		btnExistedStudent.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					DefaultComboBoxModel<String> existedNamesBox = new DefaultComboBoxModel<>();
					List<String> existedNames = App.getProfileNames();
					existedNamesBox.addAll(existedNames);
					
					JComboBox<String> namesBox = new JComboBox<>(existedNamesBox);
					
					int hasProfile = JOptionPane.showConfirmDialog(null, namesBox, "Choose your account", JOptionPane.OK_CANCEL_OPTION);
					if (hasProfile == JOptionPane.OK_OPTION) {
					
						String name = (String) namesBox.getSelectedItem();
		
						try {
							p = App.loadProfile(name);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
						
						loadProfileData();
						frame.setContentPane(ProfilePanel);
					}
				}
			}
		});
		btnExistedStudent.setFont(new Font("Tahoma", Font.PLAIN, 30));
		GridBagConstraints gbc_btnExistedStudent = new GridBagConstraints();
		gbc_btnExistedStudent.fill = GridBagConstraints.BOTH;
		gbc_btnExistedStudent.gridx = 1;
		gbc_btnExistedStudent.gridy = 5;
		StartPanel.add(btnExistedStudent, gbc_btnExistedStudent);
		
		return StartPanel;
		
	}
	
	private JPanel buildProfilePanel() {
		JPanel ProfilePanel = new JPanel();
		ProfilePanel.setBackground(SystemColor.info);
		ProfilePanel.setBounds(0, 0, PANEL_WIDTH, PANEL_LENGTH);
		ProfilePanel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("FlashCardApp 1.0");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setForeground(Color.BLACK);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 36));
		lblNewLabel_1.setBackground(Color.WHITE);
		lblNewLabel_1.setBounds(487, 30, 286, 44);
		ProfilePanel.add(lblNewLabel_1);
		
		JButton btnNewButton = new JButton("Add FlashCard");
		btnNewButton.setForeground(Color.BLACK);
		btnNewButton.setOpaque(true);
		btnNewButton.setBorderPainted(false);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton.setBackground(Color.GREEN);
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					loadSubjectData();
					frame.setContentPane(AddFlashCardPanel);
				}
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 30));
		btnNewButton.setBounds(480, 215, 300, 118);
		ProfilePanel.add(btnNewButton);
		
		JButton btnPractice = new JButton("Practice");
		btnPractice.setBackground(Color.ORANGE);
		btnPractice.setOpaque(true);
		btnPractice.setBorderPainted(false);
		btnPractice.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					ArrayList<String> choosenSubjects = getChoosenSubjects();
					
					Practice pr = new Practice(p);
					try {
						pr.StartPractice(choosenSubjects, pr);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btnPractice.setFont(new Font("Tahoma", Font.PLAIN, 30));
		btnPractice.setBounds(480, 363, 300, 118);
		ProfilePanel.add(btnPractice);
		
		JButton btnSeeRecord = new JButton("<html><body>See flashcards<br>database</body></html>");
		btnSeeRecord.setBackground(Color.CYAN);
		btnSeeRecord.setOpaque(true);
		btnSeeRecord.setBorderPainted(false);
		btnSeeRecord.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				ArrayList<FlashCard> displayList = new ArrayList<FlashCard>();
				LinkedList<FlashCard> deleteCache = new LinkedList<FlashCard>();
				LinkedList<JPanel> deletePanelCache = new LinkedList<JPanel>();
				
				for (String subject : getChoosenSubjects()) {
					displayList.addAll(p.getSubject(subject));
				}
				
				try {
					prepareDatabase(deleteCache, deletePanelCache, displayList);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				frame.setContentPane(DatabasePanel);
			}
		});
		btnSeeRecord.setFont(new Font("Tahoma", Font.PLAIN, 30));
		btnSeeRecord.setBounds(480, 520, 300, 118);
		ProfilePanel.add(btnSeeRecord);
		
		JButton btnNewButton_2 = new JButton("BACK");
		btnNewButton_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) frame.setContentPane(StartPanel);
			}
		});
		btnNewButton_2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnNewButton_2.setToolTipText("");
		btnNewButton_2.setBounds(25, 25, 93, 39);
		ProfilePanel.add(btnNewButton_2);
		
		JLabel txtHe = new JLabel();
		txtHe.setFont(new Font("Tahoma", Font.PLAIN, 36));
		txtHe.setHorizontalAlignment(SwingConstants.CENTER);
		txtHe.setBounds(230, 100, 800, 65);
		ProfilePanel.add(txtHe);
		
		return ProfilePanel;
	}
	
	private JPanel buildAddFlashCardPanel() {
		JPanel AddFlashCardPanel = new JPanel();
		AddFlashCardPanel.setBounds(0, 0, PANEL_WIDTH, PANEL_LENGTH);
		AddFlashCardPanel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("FlashCardApp 1.0");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setForeground(Color.BLACK);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 36));
		lblNewLabel_1.setBackground(Color.WHITE);
		lblNewLabel_1.setBounds(468, 30, 286, 44);
		AddFlashCardPanel.add(lblNewLabel_1);
		
		JLabel lblNewLabel = new JLabel("Subject");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(121, 137, 99, 37);
		AddFlashCardPanel.add(lblNewLabel);
		
		JPanel addSubjectPanel = new JPanel();
		addSubjectPanel.setBounds(ADDFLASHCARD_PANEL_ADDSUBJECTPANEL_X, ADDFLASHCARD_PANEL_ADDSUBJECTPANEL_Y, 754, 73);
		addSubjectPanel.setLayout(null);
		AddFlashCardPanel.add(addSubjectPanel);
		addSubjectPanel.setVisible(false); addSubjectPanel.setEnabled(false);
		
		JTextField newSubject = new JTextField();
		newSubject.setBounds(0, 36, 754, 37);
		addSubjectPanel.add(newSubject);
		newSubject.setFont(new Font("Tahoma", Font.PLAIN, 20));
		newSubject.setColumns(10);
		
		JLabel addSubjectNotice = new JLabel("Type your subject here!");
		addSubjectNotice.setBounds(0, 0, 211, 25);
		addSubjectPanel.add(addSubjectNotice);
		addSubjectNotice.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		JLabel lblNewLabel_2 = new JLabel("Question");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblNewLabel_2.setBounds(121, 258, 117, 37);
		AddFlashCardPanel.add(lblNewLabel_2);
		
		JTextArea question = new JTextArea();
		question.setFont(new Font("Monospaced", Font.PLAIN, 25));
		question.setBounds(268, 185, 954, 180);
		AddFlashCardPanel.add(question);
		
		JLabel lblNewLabel_3 = new JLabel("Answer");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblNewLabel_3.setBounds(121, 446, 97, 37);
		AddFlashCardPanel.add(lblNewLabel_3);
		
		JTextArea answer = new JTextArea();
		answer.setFont(new Font("Monospaced", Font.PLAIN, 25));
		answer.setBounds(268, 376, 954, 180);
		AddFlashCardPanel.add(answer);
		
		JLabel lblNewLabel_4 = new JLabel("Tier");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblNewLabel_4.setBounds(121, 571, 51, 37);
		AddFlashCardPanel.add(lblNewLabel_4);
		
		JComboBox<Integer> tier = new JComboBox<>();
		tier.setModel(new DefaultComboBoxModel<Integer>(new Integer[] {0, 1, 2, 3, 4}));
		tier.setMaximumRowCount(5);
		tier.setFont(new Font("Tahoma", Font.PLAIN, 30));
		tier.setBounds(268, 567, 77, 45);
		AddFlashCardPanel.add(tier);
		
		JButton btnNewButton_2 = new JButton("BACK");
		btnNewButton_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) frame.setContentPane(ProfilePanel);
			}
		});
		btnNewButton_2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnNewButton_2.setToolTipText("");
		btnNewButton_2.setBounds(25, 25, 93, 39);
		AddFlashCardPanel.add(btnNewButton_2);
		
		JComboBox<String> subjectBox = new JComboBox<>();
		subjectBox.addItemListener(new ItemListener() {
			public void itemStateChanged (ItemEvent e) {
				if ((String) subjectBox.getSelectedItem() == "New subject") {
					addSubjectPanel.setVisible(true); addSubjectPanel.setEnabled(true);
				} else {
					addSubjectPanel.setVisible(false); 
					addSubjectPanel.setEnabled(false);
				}
			}
		});
		subjectBox.setBounds(268, 137, 158, 37);
		subjectBox.setFont(new Font("Tahoma", Font.PLAIN, 24));
		AddFlashCardPanel.add(subjectBox);
		
		JButton btnNewButton_3 = new JButton("Add");
		btnNewButton_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					String subjectName = (String) subjectBox.getSelectedItem();
					String front = question.getText();
					String back = answer.getText();
					int bucketTier = (int) tier.getSelectedItem();
					
					if (subjectName == null) {
						JOptionPane.showMessageDialog(null, "Please include the subject!");
					} else if (front.trim().length() == 0) {
						JOptionPane.showMessageDialog(null, "Please insert the question!");
					} else if (back.trim().length() == 0) {
						JOptionPane.showMessageDialog(null, "Please insert the answer!");
					} else {
						
						if (subjectName == "New subject") {
							subjectName = newSubject.getText(); 
							p.addSubject(subjectName);
						}
						
						int add = p.addFlashCard(subjectName, new FlashCard(front, back, subjectName), bucketTier);
						if (add == 0) JOptionPane.showMessageDialog(null, "Duplicated flashcard! Not added.");
						else JOptionPane.showMessageDialog(null, "Added!");
						
						question.setText(""); answer.setText(""); loadSubjectData(); addSubjectPanel.setVisible(false); addSubjectPanel.setEnabled(false);
						subjectBox.setSelectedItem(subjectName);
					}
				}
			}
		});
		btnNewButton_3.setFont(new Font("Tahoma", Font.PLAIN, 30));
		btnNewButton_3.setBounds(584, 582, 200, 65);
		AddFlashCardPanel.add(btnNewButton_3);
		
		return AddFlashCardPanel;
	}
	
	private JPanel buildPracticePanel() {
		JPanel PracticePanel = new JPanel();
		PracticePanel.setBounds(0, 0, PANEL_WIDTH, PANEL_LENGTH);
		PracticePanel.setLayout(null);
		
		JButton btnNewButton_2 = new JButton("BACK");
		btnNewButton_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) frame.setContentPane(ProfilePanel);
			}
		});
		btnNewButton_2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnNewButton_2.setToolTipText("");
		btnNewButton_2.setBounds(25, 25, 93, 39);
		PracticePanel.add(btnNewButton_2);
		
		JProgressBar progressBar = new JProgressBar();
		progressBar.setBounds(333, 25, 898, 39);
		PracticePanel.add(progressBar);
		
		JTextPane textArea = new JTextPane();
		textArea.setBounds(PRACTICE_PANEL_PANE_X, PRACTICE_PANEL_PANE_Y, 819, 572);
		textArea.setEditable(false);
		textArea.setFont(new Font("Times New Roman", Font.PLAIN, 48));
		PracticePanel.add(textArea);
		
		JPanel responsePanel = new JPanel();
		responsePanel.setBounds(863, 90, 387, 444);
		responsePanel.setVisible(false); responsePanel.setEnabled(false);
		PracticePanel.add(responsePanel);
		responsePanel.setLayout(null);
		
		JTextArea txtrDidYouAnswer = new JTextArea();
		txtrDidYouAnswer.setBounds(0, 0, 387, 109);
		responsePanel.add(txtrDidYouAnswer);
		txtrDidYouAnswer.setForeground(SystemColor.desktop);
		txtrDidYouAnswer.setBackground(SystemColor.control);
		txtrDidYouAnswer.setFont(new Font("Times New Roman", Font.PLAIN, 30));
		txtrDidYouAnswer.setText("Did you answer correctly?\r\nDo you find it hard or \r\neasy to answer?");
		
		JRadioButton WRONG = new JRadioButton("No, I got it wrong");
		WRONG.setBounds(38, 116, 303, 74);
		WRONG.setName("WRONG");
		responsePanel.add(WRONG);
		responseButton.add(WRONG);
		WRONG.setFont(new Font("Tahoma", Font.PLAIN, 25));
		
		JRadioButton EASY = new JRadioButton("It is easy :)");
		EASY.setBounds(38, 193, 303, 74);
		EASY.setName("EASY");
		responsePanel.add(EASY);
		responseButton.add(EASY);
		EASY.setFont(new Font("Tahoma", Font.PLAIN, 25));
		
		JRadioButton HARD = new JRadioButton("It is hard :(");
		HARD.setBounds(38, 270, 303, 74);
		HARD.setName("HARD");
		responsePanel.add(HARD);
		responseButton.add(HARD);
		HARD.setFont(new Font("Tahoma", Font.PLAIN, 25));
		
		JButton btnNewButton_5 = new JButton("Submit");
		btnNewButton_5.setBounds(95, 351, 187, 86);
		responsePanel.add(btnNewButton_5);
		btnNewButton_5.setFont(new Font("Tahoma", Font.PLAIN, 25));
		
		JButton previousQuestion = new JButton("Previous question");
		previousQuestion.setFont(new Font("Tahoma", Font.PLAIN, 20));
		previousQuestion.setBounds(123, 25, 205, 39);
		PracticePanel.add(previousQuestion);
		
		JButton nextQuestion = new JButton("Next question");
		nextQuestion.setFont(new Font("Tahoma", Font.PLAIN, 30));
		nextQuestion.setBounds(PRACTICE_PANEL_NEXT_X, PRACTICE_PANEL_NEXT_Y, 251, 53);
		nextQuestion.setVisible(false); nextQuestion.setEnabled(false);
		PracticePanel.add(nextQuestion);
		
		return PracticePanel;
	}
	
	
	/**
	 * Load greeting label at ProfilePanel
	 */
	private static void loadProfileData() {
		JLabel label = (JLabel) ProfilePanel.getComponentAt(PROFILE_PANEL_GREETING_LABEL_X, PROFILE_PANEL_GREETING_LABEL_Y);
		label.setText("Hello " + p.getName() + ", what do you want to do today?");
	}
	
	private static ArrayList<String> getChoosenSubjects() {
		List<String> subjectsAvailable = p.getListOfSubjects();
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 1));
		
		for (String subject : subjectsAvailable) {
			panel.add(new JCheckBox(subject));
		}
		
		ArrayList<String> choosenSubjects = new ArrayList<String>();
		
		int isPractice = JOptionPane.showConfirmDialog(null, panel, "Choose subjects to practice", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
		
		if (isPractice == JOptionPane.OK_OPTION) {
		
			for (Component sBox : panel.getComponents()) {
				if (sBox instanceof JCheckBox) {
					if (((JCheckBox) sBox).isSelected()) {
						choosenSubjects.add(((JCheckBox) sBox).getText());
					}
				}
			}
		}
		return choosenSubjects;
	}
	
	/**
	 * Load subjects data for  AddFlashCardPanel
	 */
	private static void loadSubjectData() {
		JComboBox<String> subjectBox = (JComboBox<String>) AddFlashCardPanel.getComponentAt(ADDFLASHCARD_PANEL_SUBJECTBOX_X, ADDFLASHCARD_PANEL_SUBJECTBOX_Y);
		DefaultComboBoxModel<String> subjects = new DefaultComboBoxModel<>();
		subjects.addAll(p.getListOfSubjects());
		subjects.addElement("New subject");
		subjectBox.setModel(subjects);
		
		JPanel addSubjectPanel = (JPanel) AddFlashCardPanel.getComponentAt(ADDFLASHCARD_PANEL_ADDSUBJECTPANEL_X, ADDFLASHCARD_PANEL_ADDSUBJECTPANEL_Y);
		if ((String) subjectBox.getSelectedItem() == "New subject") addSubjectPanel.setVisible(true); addSubjectPanel.setEnabled(true);
	}
	
	static void displayBasicPractice(FlashCard f, int i, Practice pr, JTextPane pane, JButton previousQuestion, JButton nextQuestion) throws BadLocationException, IOException {
		
		String question = f.getFront();
		String answer = f.getBack();
		
		StyledDocument text = (StyledDocument) pane.getDocument();
		text.remove(0, text.getLength());
		text.insertString(0, question, attrs);
		text.setParagraphAttributes(0, text.getLength()-1, attrs, false);
		
		if (i == 0) {
			previousQuestion.setVisible(false); previousQuestion.setEnabled(false);
		} else {
			previousQuestion.setVisible(true); previousQuestion.setEnabled(true);
		}
		
        if (i == pr.toPractice.size() - 1) {
        	nextQuestion.setText("Finish");
        } else {
        	nextQuestion.setText("Next question");
        }
	}
	
	static JPanel buildDatabasePanel() {
		JPanel DatabasePanel = new JPanel(null);
		DatabasePanel.setSize(PANEL_WIDTH, PANEL_LENGTH);

		JPanel searchPane = new JPanel();
		searchPane.setBorder(new MatteBorder(1, 1, 0, 1, (Color) new Color(0, 0, 0)));
		searchPane.setBounds(0, 0, 1265, 78);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] {165, 1100, 0};
		gbl_panel.rowHeights = new int[]{78, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		searchPane.setLayout(gbl_panel);
		
		JLabel searchLabel = new JLabel("Search");
		searchLabel.setHorizontalAlignment(SwingConstants.CENTER);
		searchLabel.setFont(new Font("Tahoma", Font.PLAIN, 30));
		GridBagConstraints gbc_searchLabel = new GridBagConstraints();
		gbc_searchLabel.fill = GridBagConstraints.BOTH;
		gbc_searchLabel.insets = new Insets(0, 0, 0, 5);
		gbc_searchLabel.gridx = 0;
		gbc_searchLabel.gridy = 0;
		searchPane.add(searchLabel, gbc_searchLabel);
		
		JTextArea searchString = new JTextArea();
		searchString.setFont(new Font("Tahoma", Font.PLAIN, 20));
		searchString.setLineWrap(true);
		GridBagConstraints gbc_txtA = new GridBagConstraints();
		gbc_txtA.fill = GridBagConstraints.BOTH;
		gbc_txtA.gridx = 1;
		gbc_txtA.gridy = 0;
		searchString.setColumns(10);
		searchPane.add(searchString, gbc_txtA);
		
		DatabasePanel.add(searchPane);
		// DatabasePanel_searchPane_INDEX = 0
		
		JPanel buttonPane = new JPanel();
		buttonPane.setBounds(1139, 77, 126, 608);
		buttonPane.setLayout(new GridLayout(0, 1, 0, 10));
		
		JButton saveButton = new JButton("Save");
		saveButton.setFont(new Font("Tahoma", Font.PLAIN, 30));
		buttonPane.add(saveButton);
		
		
		JButton undoButton = new JButton("Undo");
		undoButton.setFont(new Font("Tahoma", Font.PLAIN, 30));
		undoButton.setVerticalTextPosition(SwingConstants.TOP);
		buttonPane.add(undoButton);
		
		JButton backButton = new JButton("Back");
		backButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					frame.setContentPane(ProfilePanel);
				}
			}
		});
		backButton.setFont(new Font("Tahoma", Font.PLAIN, 30));
		buttonPane.add(backButton);
		
		DatabasePanel.add(buttonPane);
		// DatabasePanel_buttonPane_INDEX = 1
		
		JPanel infoPane = new JPanel(new GridLayout(0, 1, 0, 10));
		
		DatabasePanel.add(infoPane);
		// DatabasePanel_infoPane_INDEX = 2
		
		return DatabasePanel;
	}
	
	private void prepareDatabase(LinkedList<FlashCard> deleteCache, LinkedList<JPanel> deletePanelCache, ArrayList<FlashCard> displayList) throws Exception {
		
		JPanel searchPane = (JPanel) DatabasePanel.getComponent(0);
		JPanel buttonPane = (JPanel) DatabasePanel.getComponent(1);
		JPanel infoPane = (JPanel) DatabasePanel.getComponent(2);
		
		JScrollPane scroll = new JScrollPane();
		
		for (FlashCard f : displayList) {
			FlashCardInfo fPane = new FlashCardInfo(f, infoPane.getWidth(), p, deleteCache, deletePanelCache, infoPane, scroll);
			infoPane.add(fPane);
		}
		
		scroll.setViewportView(infoPane);
		scroll.setBounds(0, 77, 1139, 578);
		scroll.getVerticalScrollBar().setUnitIncrement(20);
		DatabasePanel.add(scroll);
	}
}
