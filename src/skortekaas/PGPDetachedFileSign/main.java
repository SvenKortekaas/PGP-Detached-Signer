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
 * @author Sven Kortekaas
 *
 */
public class main {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		File directory = new File(".");
		List<String> fileList = new ArrayList<String>();
		
		//Get all the files from a directory
		//and put it in the List<String> fileList
		File [] fList = directory.listFiles();
		
		for (File file : fList) {
			if (file.isFile()) {
				System.out.println(file.getName());
				fileList.add(file.getName());
			}
		}
		
		int fileListSize = fileList.size();
		
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

}
