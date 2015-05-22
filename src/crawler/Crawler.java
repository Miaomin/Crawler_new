package crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Crawler {
	static void sleep (){
        long waittime;
        Random time = new Random();
        waittime = time.nextInt(10)+5;
        try{
            Thread.sleep(waittime*200);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
	}
	
	public static void main(String[] args) {
		try {
			WebDriver driver = new ChromeDriver();
			String channel = args[0];
			File dir = new File(args[1]);
			int pages = Integer.valueOf(args[2]);
			dir.mkdirs();
			DateFormat dateFormat = new SimpleDateFormat("yyMMddHHmmss");
			int file = 1;
			int page = 1;
			String url = "http://techcrunch.com/" + channel +"/";
			driver.get(url);
			while (page <= pages){
				if(page>1){
					String suburl = url+ "page/"+ page+"/";
					driver.get(suburl);
				}
				String source = driver.getPageSource();
				Document doc = Jsoup.parse(source);
				Elements links = doc.select("a[href].read-more");
				for(int i=0; i<links.size(); i++){
					String link = links.get(i).attr("href");
					sleep();
					driver.get(link);
					source = driver.getPageSource();
					Date date = new Date();
					File output = new File (dir + File.separator + dateFormat.format(date) + "_"+page+"_"+file);
					Writer writer = new OutputStreamWriter(
							new FileOutputStream(output));
					writer.write(source);
					writer.close();
					sleep();
					file++;
				}
				page++;
			}
			driver.quit();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
