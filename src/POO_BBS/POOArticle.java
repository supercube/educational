package POO_BBS;

public class POOArticle {
	private int ID;
	private String title;
	private String author;
	private String content;
	private int evaluation_count;
	private String[] evalutaion_messages;
	public static final int MAXEVAL = 1024;
	
	
	public POOArticle(int id){
		this.ID = id;
	}
}
