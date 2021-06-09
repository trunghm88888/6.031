package flashcards;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class App {
	
	private static final String profilePath = "C:\\Users\\trung\\Documents\\code\\6.031\\git\\flashCardAppProfiles\\";
	
	public static int saveProfile(Profile p) {
		try {
			String path = profilePath + p.getName();
			FileOutputStream fileOut = new FileOutputStream(path);
			ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
			objOut.writeObject(p);
			objOut.close();
			return 1;
		} catch (Exception ex) {
            ex.printStackTrace();
        }
		
		return 0;
	}
	
	public static List<String> getProfileNames() {
		File profiles = new File(profilePath);
		File[] profileNames = profiles.listFiles();
		List<String> names = new ArrayList<>();
		
		if (profileNames != null) {
			for (File profile : profileNames) {
				names.add(profile.getName());
			}
		}
		
		return names;

	}
	
	public static Profile loadProfile(String name) throws Exception {
		
			FileInputStream fileIn = new FileInputStream(profilePath + name);
			ObjectInputStream objIn = new ObjectInputStream(fileIn);
			Profile p = (Profile) objIn.readObject();
			objIn.close();
			return p;
	}
	
	public static void main(String[] args) throws Exception {
		Profile p = loadProfile("Trung");
		System.out.print(p.countFlashCards(p.getListOfSubjects()));
	}
}
