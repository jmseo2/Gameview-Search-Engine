package dataindex;

import java.io.*;

public class Utility {
	// returns data of webpage text file
	public static String [] readWebpageTextData(String fileName) {
		String [] res = new String[4];
		try {
			BufferedReader in = new BufferedReader(new FileReader(fileName));
			String docid = in.readLine();
			String url = in.readLine();
			String title = in.readLine();
			String body = "";
			while (true) {
				String line = in.readLine();
				if (line == null)
					break;
				body += line;
			}
			res[0] = docid;
			res[1] = url;
			res[2] = title;
			res[3] = body;
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return res;
	}
}
