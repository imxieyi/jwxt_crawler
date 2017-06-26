package major;

import static major.SearchPeopleOfMajor.search;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import login.Session;

public class SearchAllMajors {
	
	private static ArrayList<Major> getMajors(Session sess) throws IOException {
		ArrayList<Major> result = new ArrayList<>();
		for(int p=1;;p++){
			Document doc = Jsoup.connect(
					"http://jwxt.sustc.edu.cn/jsxsd/xsxj/toQueryZyfl.do")
					.userAgent(Session.useragent)
					.cookie("JSESSIONID", sess.getJSESSIONID())
					.data("pageIndex", new Integer(p).toString())
					.method(Connection.Method.POST)
					.execute().parse();
			Element table = doc.select("table").get(1);
			Elements rows = table.select("tr");
			if(rows.size()<2)break;
			if(rows.get(1).select("td").size()<2)break;
			for(int r=1;r<rows.size();r++) {
				Elements cols = rows.get(r).select("td");
				String name = cols.get(1).text();
				String id = cols.get(1).select("a[href]").first().attr("href").trim();
				id = id.split("=")[1];
				Major maj = new Major();
				maj.name = name;
				maj.id = id;
				result.add(maj);
			}
		}
		return result;
	}

	public static void main(String[] args) throws IOException {
		Session sess = Session.loadfromconf();
		ArrayList<Major> majors = getMajors(sess);
		for(Major maj:majors) {
			ArrayList<PersonMajor> r = search(sess, maj.id);
			for(PersonMajor pm:r) {
				System.out.println(pm);
			}
		}
	}
	
}
