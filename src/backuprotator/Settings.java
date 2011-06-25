/*
    Copyright (C) 2011 Ross Lagerwall <rosslagerwall@gmail.com>

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

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
