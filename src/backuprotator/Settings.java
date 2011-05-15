package backuprotator;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Settings {
	
	private String backupPath = "";
	private ArrayList<String> filenames = new ArrayList<String>();
	private int maxNo = 0;
	
	public Settings() throws FileNotFoundException {
		Scanner settingsFile = new Scanner(new File("settings.conf"));
		while (settingsFile.hasNextLine()) {
			String temp = settingsFile.nextLine();
			if (temp.startsWith("backupPath")) {
				backupPath = temp.substring(11);
			}
			if (temp.startsWith("filename")) {
				filenames.add(temp.substring(9));
			}
			if (temp.startsWith("maxNo")) {
				maxNo = Integer.parseInt(temp.substring(6));
			}
		}
	}

	public String getBackupPath() {
		return backupPath;
	}

	public String getFilename(int i) {
		return filenames.get(i);
	}
	
	public int getTotalFilenames() {
		return filenames.size();
	}

	public int getMaxNo() {
		return maxNo;
	}
}
