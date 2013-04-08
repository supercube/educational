package POO_BBS;

public class SplittingLine extends Entry{
	private String Content;
	
	
	public SplittingLine(){
		super(Entry.TYPE.SPLITTINGLINE);
		Content = "--------------------------------------------------------------------------------------------------------";
	}
	
	public String getTitle(){
		return Content;
	}
}


