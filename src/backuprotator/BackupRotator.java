package backuprotator;

import java.io.*;

public class BackupRotator {

	public static void main(String[] args) throws FileNotFoundException {
		Settings set = new Settings();

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
