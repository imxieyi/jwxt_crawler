package major;

public class PersonMajor {

	public String stuid;
	public String name;
	public String sex;
	public String cla;
	public String major;
	public String pref;
	
	@Override
	public String toString() {
		return stuid+"\t"+name+"\t"+sex+"\t"+cla+"\t"+major+"\t"+pref;
	}
	
}
