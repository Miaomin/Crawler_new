package crawler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class TextExtractor {
	public static void main(String[] args) throws IOException{
		File inputFolder = new File(args[0]);
		if (!inputFolder.exists()){
			System.out.println("folder not exist");
			System.exit(-1);
		}
		File[] allFiles = inputFolder.listFiles();
		File outFolder = new File(args[1]);
		outFolder.mkdirs();
		for (File f:allFiles){
			//String inputFile = folder+File.separator+f;
			//BufferedReader inputReader = new BufferedReader(new FileReader(inputFile));
			if(!f.isHidden()){
				System.out.println(f);
				Document doc = Jsoup.parse(f, null, "http://techcrunch.com/");
				Elements para = doc.select("div.article-entry");
				String title = doc.getElementsByTag("title").text().split("  |  ")[0];
				String[] fileNames = f.toString().split(File.separator);
				String fileName = fileNames[fileNames.length-1];
				PrintWriter textWriter = new PrintWriter(new FileWriter(outFolder+File.separator+fileName));
				textWriter.println(title);				
				//System.out.println(title);
				for(int i = 0; i<para.size(); i++){
					textWriter.println(para.get(i).text());
					//System.out.println(para.get(i).text());
				}
				textWriter.close();
			}
			
			
		}
		
	}

}
