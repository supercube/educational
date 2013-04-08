package POO_BBS;

public class POOBoard extends Entry {
	private String Name;
	private POOArticle[] Articles;
	private int Article_count;
	private POODirectory Essence;
	public static final int MAXART = 1024;
	public static final int ESSENCE = -1024;
	
	public POOBoard(String name){
		super(Entry.TYPE.BOARD);
		this.Name = name;
		Articles = new POOArticle[MAXART];
		for(int i = 0; i < MAXART; i++)
			Articles[i] = null;
		Article_count = 0;
		Essence = new POODirectory("Essence");
	}
	
	public boolean add(POOArticle article){
		if(Article_count >= MAXART)
			return false;
		
		Articles[Article_count] = article;
		Article_count++;
		return true;
	}
	
	public boolean addEssence(int pos, int pos_in_ess){
		if(pos >= MAXART || pos < 0 || pos_in_ess >= Essence.length() || pos_in_ess < 0){
			return false;
		}else if(Essence.get(pos_in_ess) == null || Essence.get(pos_in_ess).getType() != Entry.TYPE.BOARD){
			return false;
		}
			
		POOBoard brd = (POOBoard)Essence.get(pos_in_ess);
		int id = brd.length();
		POOArticle art = Articles[pos].clone(id);
		brd.add(art);
		Articles[pos].setFocused();
		return true;
	}
	
	public boolean focus(int pos){
		if(pos >= Article_count || pos < 0)
			return false;
		
		Articles[pos].setFocused();
		return true;
	}
	
	public Entry get(int pos){
		if(pos == ESSENCE)
			return (Entry)Essence;
		
		if(pos >= Article_count || pos < 0)
			return null;
		
		return Articles[pos];
	}
	
	public int get(String title){

		for(int i = 0; i < Article_count; i++){
			if(title.equals(Articles[i].getTitle())){
				return i;
			}
		}
		return -1;
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
		
	public boolean transcript(int id, POOArticle art){
		POOArticle art2 = art.clone(id);
		if(art2 == null)
			return false;
		return this.add(art2);
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
		String pos;
		System.out.println();
		System.out.println("************************************************************************************************************");
		System.out.println("Board " + Name + ", " + Article_count + " items");
		System.out.println("------------------------------------------------------------------------------------------------------------");
		System.out.println("Pos\tEval\tID\tTitle\tAuthor");
		for(int i = 0; i < Article_count; i++){
			pos = String.format("%03d\t", i);
			System.out.print(pos);
			Articles[i].list();
		}
		System.out.println("************************************************************************************************************");
		System.out.println();
	}
	
	public String getTitle(){
		return Name;
	}
}
