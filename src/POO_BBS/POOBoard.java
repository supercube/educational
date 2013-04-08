package POO_BBS;

public class POOBoard extends Entry {
	private String Name;
	private POOArticle[] Articles;
	private int Article_count;
	public static final int MAXART = 1024;
	
	
	public POOBoard(String name){
		super(Entry.TYPE.BOARD);
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
	
	public Entry get(int pos){
		if(pos >= Article_count || pos < 0)
			return null;
		
		return Articles[pos];
	}
	
	public boolean del(int pos){
		if(pos >= Article_count || pos < 0)
			return false;
		
		for(int i = pos + 1; i < Article_count; i++){
			Articles[i-1] = Articles[i]; 
		}
		Article_count--;
		return true;
	}
		
	public boolean move(int src, int dest){
		if(src >= MAXART || dest >= MAXART || src < 0 || dest < 0)
			return false;
		
		int i;
		POOArticle tmp = Articles[src];
		if(src <= dest){
			for(i = src + 1; i <= dest; i++){
				Articles[i-1] = Articles[i];
				if(Articles[i] == null)
					break;
			}
			Articles[i-1] = tmp;
		}else{
			for(i = src - 1; i >= dest; i--){
				Articles[i+1] = Articles[i];
				if(Articles[i] == null)
					break;
			}
			Articles[i+1] = tmp;
		}
		return true;
	}
	
	public int length(){
		return Article_count;
	}
	
	public void show(){
		System.out.println();
		System.out.println("****************************************************************************************");
		System.out.println("Board " + Name + ", " + Article_count + " items");
		System.out.println("----------------------------------------------------------------------------------------");
		System.out.println("Eval\tID\tTitle\tAuthor");
		String pos;
		for(int i = 0; i < Article_count; i++){
			pos = String.format("%03d ", i);
			System.out.print(pos);
			Articles[i].list();
		}
		System.out.println("****************************************************************************************");
		System.out.println();
	}
	
	public String getTitle(){
		return Name;
	}
}
