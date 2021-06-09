package flashcards;

import java.io.Serializable;

public class FlashCard implements Serializable {
	
	/**
	 * 
	 */
	private String question, answer, subject;
	
	/**
	 * Make a immutable FlashCard object with question and answer
	 * @param front the question, must not be empty string
	 * @param back the answer, must not be empty string
	 * @param tier the bucket tier of the flashcard
	 * @throw NullPointerException if any of the parameters is null
	 */
	public FlashCard(String front, String back, String subjectName) {
		
		if (front == null || back == null) {
			throw new NullPointerException("Question and answer cannot be null");
		}
		
		this.question = front;
		this.answer = back;
		this.subject = subjectName;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FlashCard other = (FlashCard) obj;
		if (answer == null) {
			if (other.answer != null)
				return false;
		} else if (!answer.equals(other.answer))
			return false;
		if (question == null) {
			if (other.question != null)
				return false;
		} else if (!question.equals(other.question))
			return false;
		if (subject == null) {
			if (other.subject != null)
				return false;
		} else if (!subject.equals(other.subject))
			return false;
		return true;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((answer == null) ? 0 : answer.hashCode());
		result = prime * result + ((question == null) ? 0 : question.hashCode());
		result = prime * result + ((subject == null) ? 0 : subject.hashCode());
		return result;
	}
	
	public String getFront() {
		return this.question;
	}
	
	public String getBack() {
		return this.answer;
	}
	
	public String getSubject() {
		return this.subject;
	}
	
}