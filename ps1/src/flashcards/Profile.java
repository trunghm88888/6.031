package flashcards;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Profile implements Serializable {
	
	/**
	 * 
	 */
	private String name;
	private Map<String, Set<FlashCard>> FlashCards = new HashMap<String, Set<FlashCard>>();
	private Map<FlashCard, Integer> Tiers = new HashMap<FlashCard, Integer>();
	private Map<FlashCard, Integer> DayToPractice = new HashMap<FlashCard, Integer>();
	
	public Profile(String studentName) {
		this.name = studentName;
	}
	
	public void addSubject(String subjectName) {
		this.FlashCards.put(subjectName, new HashSet<FlashCard>());
	}
	
	public int addFlashCard(String subjectName, FlashCard f, int tier) {
		if (this.FlashCards.get(subjectName).contains(f)) {
			return 0;
		}
		
		this.FlashCards.get(subjectName).add(f);
		this.Tiers.put(f, tier);
		this.DayToPractice.put(f, ((int) Math.pow(2, tier)) - 1);
		return 1;
	}
	
	public int addWholeFlashCard(FlashCard f, int tier) {
		if (this.FlashCards.get(f.getSubject()).contains(f)) {
			return 0;
		}
		
		this.FlashCards.get(f.getSubject()).add(f);
		this.Tiers.put(f, tier);
		return 1;
	}
	
	public void delFlashCard(FlashCard f) {
		this.FlashCards.get(f.getSubject()).remove(f);
		this.Tiers.remove(f);
		this.DayToPractice.remove(f);
	}
	
	public Map<String, Set<FlashCard>> getAllFlashCards() {
		return this.FlashCards;
	}
	
	public void changeTier(FlashCard f, int newTier) {
		this.Tiers.put(f, newTier);
	}
	
	public int getTier(FlashCard f) {
		return this.Tiers.get(f);
	}
	
	public Map<FlashCard, Integer> getAllTiers() {
		return this.Tiers;
	}
	
	public int getDayToPractice(FlashCard f) {
		return this.DayToPractice.get(f);
	}
	
	public void changeDayToPractice(FlashCard f, int newDay) {
		this.DayToPractice.put(f, newDay);
	}
	
	public Map<FlashCard, Integer> getAllDayToPractice() {
		return this.DayToPractice;
	}
	
	public Set<FlashCard> getSubject(String subjectName) {
		return this.FlashCards.get(subjectName);
	}
	
	public String getName() {
		return this.name;
	}
	
	public void updateName(String newName) {
		this.name = newName;
	}
	
	public List<String> getListOfSubjects() {
		List<String> subjects = new ArrayList<String>();
		
		for (String subject : this.FlashCards.keySet()) {
			subjects.add(subject);
		}
		
		return subjects;
	}
	
	public int countFlashCards(List<String> subjectNames) {
		int totalFlashCards = 0;
		
		for (String subject : subjectNames) {
			totalFlashCards += this.FlashCards.get(subject).size();
		}
		
		return totalFlashCards;
	}
	
	public void delSubject(String subjectName) {
		for (FlashCard f : this.FlashCards.get(subjectName)) {
			this.Tiers.remove(f); this.DayToPractice.remove(f);
		}
		this.FlashCards.remove(subjectName);
	}
		
	public List<FlashCard> getDueFlashCards(List<String> subjects) {
		
		List<FlashCard> toPractice = new ArrayList<FlashCard>();
		
		List<Iterator<FlashCard>> iterators = new ArrayList<Iterator<FlashCard>>();
		
		for (String subject : subjects) {
			
			List<FlashCard> dueFlashCards = new ArrayList<FlashCard>();
			
			for (FlashCard f : this.FlashCards.get(subject)) {
				if (this.DayToPractice.get(f) == 0) {
					dueFlashCards.add(f);
				}
			}
			
			iterators.add(dueFlashCards.iterator());
		}
		
		int i = 0;
		int size = iterators.size();		
		
		while (size > 0) {
			if (iterators.get(i).hasNext()) {
				toPractice.add(iterators.get(i).next());
				i++;
			} else {
				iterators.remove(i);
				size -= 1;
				if (size == 0) break;
			}
			i = i % size;
		}
		
		return toPractice;
	}
	
	public static void main(String[] args) throws Exception {
		Profile q = App.loadProfile("Trung");
		System.out.println(q.DayToPractice.values());
	}
}
