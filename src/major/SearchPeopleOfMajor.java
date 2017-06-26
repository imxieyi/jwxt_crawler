package major;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import login.Session;

public class SearchPeopleOfMajor {
	
	public static ArrayList<PersonMajor> search(Session sess, String majorid) throws IOException {
		ArrayList<PersonMajor> result = new ArrayList<>();
		for(int i=1;;i++) {
			Connection.Response resp = Jsoup.connect(
					"http://jwxt.sustc.edu.cn/jsxsd/xsxj/yxxsList.do")
					.userAgent(Session.useragent)
					.cookie("JSESSIONID", sess.getJSESSIONID())
					.method(Connection.Method.POST)
					.data("zy", "")
					.data("zyid", majorid)
					.data("pageIndex", new Integer(i).toString())
					.execute();
			Document doc = resp.parse();
			Element table = doc.select("table").get(1);
			Elements rows = table.select("tr");
			if(rows.size()<2)break;
			for(int r=1;r<rows.size();r++) {
				Elements cols = rows.get(r).select("td");
				PersonMajor pm = new PersonMajor();
				pm.stuid = cols.get(0).text();
				pm.name = cols.get(1).text();
				pm.sex = cols.get(2).text();
				pm.cla = cols.get(4).text();
				pm.major = cols.get(5).text();
				pm.pref = cols.get(6).text();
				result.add(pm);
			}
		}
		return result;
	}

}
