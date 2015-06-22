/**
 * 
 */
package skortekaas.PGPDetachedFileSign;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Sven Kortekaas - https://github.com/SvenKortekaas
 *
 */
public class main {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		File directory = new File(".");
		List<String> fileList = new ArrayList<String>();
		
		fileList = makeFileList(directory, fileList);
		
		signer(directory, fileList);
		
	}

	//Get all the files from a directory
	//and put it in the List<String> fileList and returns it
	private static List<String> makeFileList(File directory, List<String> fileList) {
		
		File[] fList = getFileList(directory);
		
		for (File file : fList) {
			if (file.isFile()) {
				System.out.println(file.getName());
				fileList.add(file.getName());
			}
		}
		
		return fileList;
	}

	private static void signer(File directory, List<String> fileList) throws IOException {
		
		int fileListSize = getListStringSize(fileList);
		
		for (int i = 0; i < fileListSize; i++) {
		
			ProcessBuilder builder = new ProcessBuilder(
		            "cmd.exe", "/c", "cd "+"\""+directory.getCanonicalPath()+"\""+" && gpg --detach-sign --armor "+"\""+fileList.get(i)+"\"");
		        builder.redirectErrorStream(true);
		        Process p = builder.start();
		        BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
		        String line;
		        while (true) {
		            line = r.readLine();
		            if (line == null) { break; }
		            System.out.println(line);
		        }
	        
		}
	}

	private static File[] getFileList(File directory) {
		File [] fList = directory.listFiles();
		return fList;
	}

	private static int getListStringSize(List<String> fileList) {
		int fileListSize = fileList.size();
		return fileListSize;
	}

}
