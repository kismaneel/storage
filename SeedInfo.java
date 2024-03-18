package pjtUtils.webCrawling;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SeedInfo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String urlFmt = "https://www.seed.go.kr/interwork/seed/7/list.do?csSignature=m2YGHrpWqxa8H5RKskBsuA==&layout=UP4P6DW%2Buy6KAOCpjdrTDA%3D%3D&page2=1&page=";
		int maxPageNo = 1328;	// 1328
		int cnt = 1;
		String filePath = "C:/PjtUtils/Output.txt";
		
		try (OutputStream output = new FileOutputStream(filePath)){
			for( int i=1; i<maxPageNo; i++ ) {
				Document doc = Jsoup.connect(urlFmt+i).get();
				Elements tbody = doc.select("#_institute > div > div:nth-child(5) > table > tbody");
				Elements trList = tbody.select("tr");
				for( Element tr : trList ) {
					String td0 = tr.select("td").get(0).text();
					String td1 = tr.select("td").get(1).text();
					String td1_href = tr.select("td").get(1).select("a").attr("href");
					String td2 = tr.select("td").get(2).text();
					String td3 = tr.select("td").get(3).text();
					String td4 = tr.select("td").get(4).text();
					String td5 = tr.select("td").get(5).text();
					String td6 = tr.select("td").get(6).text();
					String td7 = tr.select("td").get(7).text();
					String dtlUrl = String.format("https://www.seed.go.kr/interwork/%s/%s/%s/protectionFarmView.do", td1_href.substring("javascript:jf_farm_view(".length(), td1_href.length()-2).replaceAll("'", "").split(","));
					
					
					Document docDtl = Jsoup.connect(dtlUrl).get();
					Elements aplcDt = docDtl.select("#_institute > div > div.table-scroll > table:nth-child(2) > tbody > tr:nth-child(2) > td:nth-child(4)");
					
					String line = (cnt++) + "\t" + td0 + "\t" + td1 + "\t" + td2 + "\t" + td3 + "\t" + td4 + "\t" + td5 + "\t" + td6 + "\t" + td7 + "\t" + dtlUrl + "\t" + aplcDt.text();
					System.out.println(line);
					line = (cnt == 1 ? "" : "\n") + line;
					output.write(line.getBytes());
				}
			}
		
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}

}
