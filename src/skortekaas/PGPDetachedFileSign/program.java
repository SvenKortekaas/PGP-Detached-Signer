/*
 * PGP-Detached-Signer - PGP sign files in the same directory
 * Copyright (C) 2015  Sven Kortekaas
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package skortekaas.PGPDetachedFileSign;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * This application creates an PGP armor detached sign for every file that is in
 * the same directory as the .jar file
 * 
 * @author Sven Kortekaas - https://github.com/SvenKortekaas
 * @since 22-06-2015
 *
 */
public class program {

	/**
	 * @param args
	 *            Do not use them
	 * @throws IOException
	 *             IOException for signer(directory, fileList)
	 */
	public static void main(String[] args) throws IOException {
		File directory = new File(".");
		List<String> fileList = new ArrayList<String>();

		fileList = makeFileList(directory);

		signer(directory, fileList);

	}

	/**
	 * Gets all the files from a directory and puts it in a List and returns it
	 * 
	 * @param directory
	 *            the directory for the file list
	 * @return returns the list with files
	 */
	private static List<String> makeFileList(File directory) {
		List<String> fileList = new ArrayList<String>();
		File[] fList = getFileList(directory);

		for (File file : fList) {
			if (file.isFile()) {
				System.out.println(file.getName());
				fileList.add(file.getName());
			}
		}

		return fileList;
	}

	/**
	 * This method opens CMD.exe sets the path to the location of the .JAR and
	 * uses 'gpg --detach-sign --armor [file]' for every file there is in the
	 * directory
	 * 
	 * @param directory
	 *            the directory where the .JAR is in
	 * @param fileList
	 *            the files that need to get signed
	 * @throws IOException
	 *             IOException for ProcessBuilder, BufferedReader and
	 *             InputStreamReader
	 */
	private static void signer(File directory, List<String> fileList)
			throws IOException {

		int fileListSize = getListStringSize(fileList);

		for (int i = 0; i < fileListSize; i++) {

			ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", "cd "
					+ "\"" + directory.getCanonicalPath() + "\""
					+ " && gpg --detach-sign --armor " + "\"" + fileList.get(i)
					+ "\"");
			builder.redirectErrorStream(true);
			Process p = builder.start();
			BufferedReader r = new BufferedReader(new InputStreamReader(
					p.getInputStream()));
			String line;
			while (true) {
				line = r.readLine();
				if (line == null) {
					break;
				}
				System.out.println(line);
			}

		}
	}

	/**
	 * The method returns the file list of the given directory in a File arry
	 * 
	 * @param directory
	 *            give the directory for the file list
	 * @return the list of files as a File arry
	 */
	private static File[] getFileList(File directory) {
		File[] fList = directory.listFiles();
		return fList;
	}

	/**
	 * This method gives you the number as an integer of items in a List
	 * 
	 * @param fileList
	 *            the list in List
	 * @return the size of the given List as an integer
	 */
	private static int getListStringSize(List<String> fileList) {
		int fileListSize = fileList.size();
		return fileListSize;
	}

}
