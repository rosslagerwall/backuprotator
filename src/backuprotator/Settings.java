package backuprotator;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
* Loads the configuration settings for the backup rotation.
* The config file format is as follows:
*   A line starting with "backupPath=" defines the path to the directory
*   containing the files to be rotated.
*   A line starting with "maxNo=" defines the maximum number of files that
*   may exist at any one time.
*   A line starting with "filename=" defines the generic name of the file to be
*   rotated relative to backupPath. A # indicates the position where the file
*   will be numbered. Eg: filename=myfile# will result files being called
*   myfile1, myfile2, etc. There may be more than one "filename=" line. The
*   rotation will be applied to all filenames.
**/
public class Settings {
	
	private String backupPath = "";
	private ArrayList<String> filenames = new ArrayList<String>();
	private int maxNo = 0;
	
	public Settings(String name) throws FileNotFoundException {
		Scanner settingsFile = new Scanner(new File(name));
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
