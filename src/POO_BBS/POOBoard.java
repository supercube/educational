package POO_BBS;

public class POOBoard {
	private String Name;
	private POOArticle[] Articles;
	private int Article_count;
	public static final int MAXART = 1024;
	
	
	public POOBoard(String name){
		this.Name = name;
		Articles = new POOArticle[MAXART];
		for(int i = 0; i < MAXART; i++)
			Articles[i] = null;
		Article_count = 0;
	}
	
	public boolean add(POOArticle article){
		if(Article_count > MAXART)
			return false;
		
		Articles[Article_count] = article;
		Article_count++;
		return true;
	}
	
	public boolean del(int pos){
		if(pos >= MAXART || Articles[pos] == null)
			return false;
		
		Articles[pos] = null;
		Article_count--;
		return true;
	}
		
	public boolean move(int src, int dest){
		if(src >= MAXART || dest >= MAXART)
			return false;
		
		POOArticle tmp = Articles[dest];
		Articles[dest] = Articles[src];
		Articles[src] = tmp;
		
		if(Articles[src] == null){
			for(int i = src + 1; i <= dest; i++){
				Articles[i-1] = Articles[i];
				if(Articles[i] == null)
					break;
			}
		}
		return true;
	}
	
	public int length(){
		return Article_count;
	}
	
	public void show(){
		System.out.println("Eval\tID\tTitle\tAuthor");
		for(int i = 0; i < Article_count; i++)
			Articles[i].list();
	}
}
