package grade;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import login.Session;

public class SearchPeopleOfClass {

	private static final String classid = "";
	private static final int stuidfrom = 11510000;
	private static final int stuidto   = 11510999;

	public static void main(String[] args) throws IOException {
		Session sess = Session.loadfromconf();
		for (int stuid = stuidfrom; stuid <= stuidto; stuid++) {
			Document doc = Jsoup.connect(
					"http://jwxt.sustc.edu.cn/jsxsd/kscj/pscj_list.do?xs0101id=" + stuid + "&jx0404id=" + classid)
					.userAgent(Session.useragent).cookie("JSESSIONID", sess.getJSESSIONID()).get();
			Element table = doc.select("table").first();
			Elements rows = table.select("tr");
			if (rows.size() < 2)
				continue;
			Elements cols = rows.get(1).select("td");
			double ass = 0;
			double assRate = 0;
			String assStr = cols.get(1).text();
			if (!assStr.equals("")) {
				ass = Double.parseDouble(assStr);
				assRate = Double.parseDouble(cols.get(2).text().substring(0, cols.get(2).text().length() - 1));
			}
			double mid = 0;
			double midRate = 0;
			String midStr = cols.get(3).text();
			if (!midStr.equals("")) {
				mid = Double.parseDouble(midStr);
				midRate = Double.parseDouble(cols.get(4).text().substring(0, cols.get(4).text().length() - 1));
			}
			double fin = 0;
			double finRate = 0;
			String finStr = cols.get(5).text();
			if (!finStr.equals("")) {
				fin = Double.parseDouble(finStr);
				finRate = Double.parseDouble(cols.get(6).text().substring(0, cols.get(6).text().length() - 1));
			}
			double total = (ass * assRate + mid * midRate + fin * finRate) * 0.01 + (100 - assRate - midRate - finRate);
			System.out.printf("%d %.0f\n", stuid, total);
		}
	}

}
