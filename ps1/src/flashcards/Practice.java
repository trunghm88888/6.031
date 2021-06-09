package flashcards;

import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class Practice {
	
	public List<FlashCard> toPractice;
	public int wrongAns = 0;
	public int upTier = 0;
	public int downTier = 0;
	public List<Difficulty> answerResults = new ArrayList<>();;
	public int i;
	public Profile profile;
	static final SimpleAttributeSet attrs=new SimpleAttributeSet();
	public String question, answer;
	
	public Practice(Profile p) {
		this.profile = p;
		StyleConstants.setAlignment(attrs,StyleConstants.ALIGN_CENTER);
	}
	
	public void StartPractice(List<String> subjects, Practice pr) throws Exception {
		
        JTextPane QApane = (JTextPane) AppGUI.PracticePanel.getComponentAt(AppGUI.PRACTICE_PANEL_PANE_X, AppGUI.PRACTICE_PANEL_PANE_Y);
		QApane.setEditorKit(new MyEditorKit());
		
		StyledDocument text = (StyledDocument) QApane.getDocument();
			
		toPractice = this.profile.getDueFlashCards(subjects);
		
		if (toPractice.size() == 0) {
			JOptionPane.showMessageDialog(null, "No due flashcards, please add more!");
			return;
		}
		
		AppGUI.frame.setContentPane(AppGUI.PracticePanel);
		JPanel responsePanel = (JPanel) AppGUI.PracticePanel.getComponentAt(AppGUI.PRACTICE_PANEL_RESPONSE_X, AppGUI.PRACTICE_PANEL_RESPONSE_Y);
		responsePanel.setEnabled(false); responsePanel.setVisible(false);
		JButton responseSubmit = (JButton) responsePanel.getComponentAt(AppGUI.PRACTICE_PANEL_SUBMITBUTTON_X, AppGUI.PRACTICE_PANEL_SUBMITBUTTON_Y);
		
		JButton previousQuestion = (JButton) AppGUI.PracticePanel.getComponentAt(AppGUI.PRACTICE_PANEL_PREVIOUS_X, AppGUI.PRACTICE_PANEL_PREVIOUS_Y);
		JButton nextQuestion = (JButton) AppGUI.PracticePanel.getComponentAt(AppGUI.PRACTICE_PANEL_NEXT_X, AppGUI.PRACTICE_PANEL_NEXT_Y);
		nextQuestion.setEnabled(false); nextQuestion.setVisible(false);
		
		i = 0;
		question = toPractice.get(i).getFront();
		answer = toPractice.get(i).getBack();
		
		QApane.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mousePressed(MouseEvent e) {
				try {
					if (QApane.getText().compareTo(question) == 0) {
						text.remove(0, text.getLength());
						text.insertString(0, answer, attrs);
						text.setParagraphAttributes(0, text.getLength()-1, attrs, false);
					} else {
						text.remove(0, text.getLength());
						text.insertString(0, question, attrs);
						text.setParagraphAttributes(0, text.getLength()-1, attrs, false);
					}
				} catch (BadLocationException e1) {
					e1.printStackTrace();
				}
				if (i == pr.answerResults.size()) {
					responsePanel.setVisible(true); responsePanel.setEnabled(true);
					nextQuestion.setEnabled(false); nextQuestion.setVisible(false);
				} else {
					responsePanel.setVisible(false); responsePanel.setEnabled(false);
					nextQuestion.setVisible(true); nextQuestion.setEnabled(true);
				}
			}
		});
		
		responseSubmit.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				for (Enumeration<AbstractButton> buttons = AppGUI.responseButton.getElements(); buttons.hasMoreElements();) {
		            AbstractButton button = buttons.nextElement();
		            
		            FlashCard f = pr.toPractice.get(i);
		            
		            if (button.isSelected()) {
		                if (button.getName() == "EASY") {
		                	pr.answerResults.add(Difficulty.EASY);
		                	pr.upTier += 1;
		                	int newTier = profile.getTier(f) + 1;
		                	profile.changeTier(f, newTier);
		                	profile.changeDayToPractice(f, (int) Math.pow(2, newTier));
		                } else if (button.getName() == "HARD") {
		                	pr.answerResults.add(Difficulty.HARD);
		                	pr.downTier += 1;
		                	int newTier = profile.getTier(f) - 1;
		                	profile.changeTier(f, newTier);
		                	profile.changeDayToPractice(f, (int) Math.pow(2, newTier));
		                } else {
		                	pr.answerResults.add(Difficulty.WRONG);
		                	pr.wrongAns += 1;
		                	int newTier = 0;
		                	profile.changeTier(f, newTier);
		                	profile.changeDayToPractice(f, 1);
		                }
		                
		                JOptionPane.showMessageDialog(null, "Successfully record the result");
		                responsePanel.setVisible(false); responsePanel.setEnabled(false);
		                AppGUI.responseButton.clearSelection();
		                
		                if (pr.answerResults.size() == pr.toPractice.size()) {
		    				App.saveProfile(profile);
		    				nextQuestion.setText("Finish");
		                } else {
		                	nextQuestion.setText("Next question");
		                }
		                nextQuestion.setVisible(true); nextQuestion.setEnabled(true);
		                return;
		            }
				}
				if (pr.answerResults.size() < i + 1) {
					JOptionPane.showMessageDialog(null, "Please choose a response!");
				}
			}
		});
		
		previousQuestion.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				i -= 1;
				FlashCard prevF = toPractice.get(i);
				question = prevF.getFront();
				answer = prevF.getBack();
				try {
					responsePanel.setEnabled(false); responsePanel.setVisible(false);
					AppGUI.displayBasicPractice(prevF, i, pr, QApane, previousQuestion, nextQuestion);
				} catch (BadLocationException | IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		nextQuestion.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (i < toPractice.size() - 1) {
					i += 1;
					FlashCard nextF = toPractice.get(i);
					question = nextF.getFront();
					answer = nextF.getBack();
					
					if (i < pr.answerResults.size()) {
						nextQuestion.setVisible(true); nextQuestion.setEnabled(true);
					} else {
						nextQuestion.setVisible(false); nextQuestion.setEnabled(false);
					}
					try {
						AppGUI.displayBasicPractice(nextF, i, pr, QApane, previousQuestion, nextQuestion);
					} catch (BadLocationException | IOException e1) {
						e1.printStackTrace();
					}
				} else {
					
					JPanel resultsPanel = new JPanel(new GridLayout(0, 1));
					
					Object[][] results = {
							{"Easy answer", "Hard answer", "Wrong answer"},
							{pr.upTier, pr.downTier, pr.wrongAns}
					};
					String[] colNames = {"Easy answer", "Hard answer", "Wrong ans"};
					JTable resultsTable = new JTable(results, colNames);
					
					JLabel goBackConfirm = new JLabel("Going back to profile?");
					
					resultsPanel.add(resultsTable); resultsPanel.add(goBackConfirm);
					
					int goBack = JOptionPane.showConfirmDialog(null, resultsPanel, "Result!", JOptionPane.OK_OPTION);
					
					if (goBack == JOptionPane.OK_OPTION) {
						AppGUI.frame.setContentPane(AppGUI.ProfilePanel);
					}
				}
			}
		});
		
		AppGUI.displayBasicPractice(toPractice.get(i), i, pr, QApane, previousQuestion, nextQuestion);
	}
}
