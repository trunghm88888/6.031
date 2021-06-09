package flashcards;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class CenteredViewTextPane extends JTextPane {
	
	private static final SimpleAttributeSet attrs=new SimpleAttributeSet();
	private StyledDocument text;

	public CenteredViewTextPane() {
		super();
		setEditorKit(new MyEditorKit());
		StyleConstants.setAlignment(attrs,StyleConstants.ALIGN_CENTER);
		text = (StyledDocument) getDocument();
	}

	public CenteredViewTextPane(StyledDocument doc) {
		super(doc);
	}
	
	public void setText(String s) {
		try {
			text.remove(0, text.getLength());
			text.insertString(0, s, attrs);
			text.setParagraphAttributes(0, text.getLength(), attrs, false);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}
}
