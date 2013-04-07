package POO_BBS;

public class Entry {
	private TYPE Type; 
	public static enum TYPE{ARTICLE, BOARD, DIRECTORY};
	public Entry(TYPE type){
		this.Type = type;
	}
	
	public TYPE getType(){
		return Type;
	}
	
	public String getTitle(){
		return null;
	}
}
