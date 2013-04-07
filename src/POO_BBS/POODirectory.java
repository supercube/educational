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
	
	public void show(){
		for(int i = 0; i < Entry_count; i++){
			System.out.println(Entries[i].getTitle());
		}
		
	}
	
	public String getTitle(){
		return Name;
	}
}