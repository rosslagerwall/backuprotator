package backuprotator;

import java.io.*;

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

		for (int filenameIterator = 0; filenameIterator < set
				.getTotalFilenames(); filenameIterator++) {
			File oldest = new File(set.getBackupPath() + "/"
					+ set.getFilename(filenameIterator).replace("#", "" + set.getMaxNo()));
			if (oldest.exists() && set.getMaxNo() != 0) {
				oldest.delete();
			}
			if (set.getMaxNo() != 0) {
				for (int i = set.getMaxNo(); i >= 1; i--) {
					String oldname = set.getBackupPath() + "/"
							+ set.getFilename(filenameIterator).replace("#", "" + i);
					String newname = set.getBackupPath() + "/"
							+ set.getFilename(filenameIterator).replace("#", "" + (i + 1));
					File currentFile = new File(oldname);
					if (currentFile.exists()) {
						currentFile.renameTo(new File(newname));
					}
				}
			} else {
				int total = 1;
				while (new File(set.getBackupPath() + "/"
						+ set.getFilename(filenameIterator).replace("#", "" + total)).exists()) {
					String oldname = set.getBackupPath() + "/"
							+ set.getFilename(filenameIterator).replace("#", "" + total);
					String newname = set.getBackupPath()
							+ "/"
							+ set.getFilename(filenameIterator).replace("#",
									"temp" + (total + 1));
					File currentFile = new File(oldname);
					currentFile.renameTo(new File(newname));
					total++;
				}

				for (int i = 2; i <= total; i++) {
					String oldname = set.getBackupPath() + "/"
							+ set.getFilename(filenameIterator).replace("#", "temp" + i);
					String newname = set.getBackupPath() + "/"
							+ set.getFilename(filenameIterator).replace("#", "" + i);
					File currentFile = new File(oldname);
					currentFile.renameTo(new File(newname));
				}
			}
		}
	}

}
