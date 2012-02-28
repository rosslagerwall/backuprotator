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
import java.util.Iterator;

/**
* BackupRotator is a program that allows files to be "rotated" with an upper
* limit on the number of files that exist.
* Basically, it is given a filename(s) and a number n in a configuration file
* and it renames the filename(s) such that there it forms an increasing
* list filename1, filename2, up to the smaller of n or as many as are
* available. If there are more than n files when the program is run, the
* oldest files get deleted.
* Eg: If there are files called log1, log2, log3 and maxNo=3, running the
* program will delete log3, rename log2 to log3 and rename log1 to log2.
* Eg: If there are files called log1, log2, log3 and maxNo=0, running the
* program will rename log2 to log3 and rename log1 to log2.
* Eg: If there is a file called log1 and maxNo=3, running the program will
* delete log3, rename log1 to log2.
* Eg: If there are files called log1, log2 and maxNo=3, running the
* program will rename log2 to log3 and rename log1 to log2.
**/
public class BackupRotator {

	public static void main(String[] args) throws FileNotFoundException {
		if (args.length > 0 && (args[0].equals("-h") ||
				args[0].equals("--help")) || args.length > 1) {
			System.err.println("BackupRotator [-h] [--help] [configfile]\n\n" +
				"*configfile* specifies the path to the configuration file" +
				" and defaults to \"settings.conf\".\n"+
				"*-h* or *--help* displays this message.");
			System.exit(64);
		}

		Settings set = new Settings(args.length > 0 ?
								args[0] : "settings.conf");

		Iterator<String> filenameIterator = set.iterator();
		while (filenameIterator.hasNext()) {
			String current = filenameIterator.next();
			File oldest = new File(set.getBackupPath() + "/"
					+ current.replace("#", "" + set.getMaxNo()));
			if (oldest.exists() && set.getMaxNo() != 0) {
				oldest.delete();
			}
			if (set.getMaxNo() != 0) {
				for (int i = set.getMaxNo(); i >= 1; i--) {
					String oldname = set.getBackupPath() + "/"
							+ current.replace("#", "" + i);
					String newname = set.getBackupPath() + "/"
							+ current.replace("#", "" + (i + 1));
					File currentFile = new File(oldname);
					if (currentFile.exists()) {
						currentFile.renameTo(new File(newname));
					}
				}
			} else {
				int total = 1;
				while (new File(set.getBackupPath() + "/"
						+ current.replace("#", "" + total)).exists()) {
					String oldname = set.getBackupPath() + "/"
							+ current.replace("#", "" + total);
					String newname = set.getBackupPath()
							+ "/"
							+ current.replace("#",
									"temp" + (total + 1));
					File currentFile = new File(oldname);
					currentFile.renameTo(new File(newname));
					total++;
				}

				for (int i = 2; i <= total; i++) {
					String oldname = set.getBackupPath() + "/"
							+ current.replace("#", "temp" + i);
					String newname = set.getBackupPath() + "/"
							+ current.replace("#", "" + i);
					File currentFile = new File(oldname);
					currentFile.renameTo(new File(newname));
				}
			}
		}
	}

}
