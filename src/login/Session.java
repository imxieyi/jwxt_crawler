package login;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class Session {
	
	private static final String loginurl = "https://cas.sustc.edu.cn/cas/login?service=http%3A%2F%2Fjwxt.sustc.edu.cn%2Fjsxsd%2F";
	public static final String useragent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36";
	
	private Map<String, String> cookies;
	
	//Read parameters from login.properties
	public static Session loadfromconf() throws IOException {
		Properties prop = new Properties();
		File f = new File("login.properties");
		if(!f.exists()){
			System.err.println("login.properties not found!");
			System.exit(-1);
		}
		FileInputStream fis = new FileInputStream(f);
		prop.load(fis);
		return new Session(prop.getProperty("id"), prop.getProperty("password"));
	}
	
	public Session(String username, String password) throws IOException {
		//CAS Login Page
		Connection.Response loginForm = Jsoup.connect(loginurl)
				.userAgent(useragent)
				.method(Connection.Method.GET).execute();
		Document loginDoc = loginForm.parse();
		Element form = loginDoc.getElementById("fm1");
		Element lt = form.getElementsByAttributeValue("name", "lt").first();
		Element execution = form.getElementsByAttributeValue("name", "execution").first();
		Element _eventId = form.getElementsByAttributeValue("name", "_eventId").first();
		Element submit = form.getElementsByAttributeValue("name", "submit").first();
		//CAS Auth
		Connection.Response auth = Jsoup.connect(loginurl)
				.userAgent(useragent)
				.data("username", username)
				.data("password", password)
				.data("lt", lt.val())
				.data("execution", execution.val())
				.data("_eventId", _eventId.val())
				.data("submit", submit.val())
				.cookies(loginForm.cookies())
				.method(Connection.Method.POST)
				.execute();
		cookies = auth.cookies();
	}
	
	public Map<String, String> getCookies() {
		return cookies;
	}
	
	public String getJSESSIONID() {
		return cookies.get("JSESSIONID");
	}

}
