package POO_BBS;

public class POODirectory extends Entry{
	private String Name;
	private Entry[] Entries;
	private int Entry_count;
	public static final int MAXENTRY = 1024;
	
	
	public POODirectory(String name){
		super(Entry.TYPE.DIRECTORY);
		this.Name = name;
		Entry_count = 0;
		Entries = new Entry[MAXENTRY];
		for(int i = 0; i < MAXENTRY;i++)
			Entries[i] = null;
	}
	
	public boolean add(POOBoard board){
		if(Entry_count >= MAXENTRY)
			return false;
		
		Entries[Entry_count] = board;
		Entry_count++;
		return true;
	}
	
	public boolean add(POOArticle article){
		if(Entry_count >= MAXENTRY)
			return false;
		
		Entries[Entry_count] = article;
		Entry_count++;
		return true;
	}
	
	public boolean add(POODirectory dir){
		if(Entry_count >= MAXENTRY)
			return false;
		
		Entries[Entry_count] = dir;
		Entry_count++;
		return true;
	}
	
	public boolean add_split(){
		if(Entry_count >= MAXENTRY)
			return false;
		
		SplittingLine spline = new SplittingLine(); 
		Entries[Entry_count] = spline;
		Entry_count++;
		return true;
	}
	
	public boolean del(int pos){
		if(pos >= Entry_count)
			return false;
		
		for(int i = pos + 1; i < Entry_count; i++){
			Entries[i-1] = Entries[i];
		}
		Entry_count--;
		return true;
	}
	
	public boolean move(int src, int dest){
		if(src >= MAXENTRY || dest >= MAXENTRY)
			return false;
		
		int i;
		Entry tmp = Entries[src];
		for(i = src + 1; i <= dest; i++){
			Entries[i-1] = Entries[i];
			if(Entries[i] == null)
				break;
		}
		Entries[i-1] = tmp;
		return true;
	}
	
	public int length(){
		return Entry_count;
	}
	
	public void show(){
		System.out.println();
		System.out.println("****************************************************************************************");
		System.out.println("Article " + Name + ", " + Entry_count + " items");
		System.out.println("----------------------------------------------------------------------------------------");
		for(int i = 0; i < Entry_count; i++){
			System.out.println(Entries[i].getTitle());
		}
		System.out.println("****************************************************************************************");
		System.out.println();
	}
	
	public String getTitle(){
		return Name;
	}
}