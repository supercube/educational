package POO_BBS;

public class POOBoard {
	private String Name;
	private POOArticle[] Articles;
	private int Article_count;
	public static final int MAXART = 1024;
	
	
	public POOBoard(String name){
		this.Name = name;
		Articles = new POOArticle[MAXART];
		Article_count = 0;
	}
	
	public void add(POOArticle article){
		if(Article_count <= MAXART){
			Articles[Article_count] = article;
			Article_count++;
		}
	}
}
