package POO_BBS;

public class POOArticle extends Entry{
	private int ID;
	private String title;
	private String author;
	private String content;
	private int no_evaluation;
	private int evaluation_count;
	private String[] evaluation_messages;
	private byte[] evaluation_type; /* 1:push, -1:boo, 0:arrow */
	public static final int MAXEVAL = 1024;
	
	
	
	public POOArticle(int id, String title, String author, String content){
		super(Entry.TYPE.ARTICLE);
		this.ID = id;
		this.title = title;
		this.author = author;
		this.content = content;
		no_evaluation = 0;
		evaluation_count = 0;
		evaluation_messages = new String[MAXEVAL];
		evaluation_type = new byte[MAXEVAL];
	}
	
	public boolean push(String message){
		if(no_evaluation >= MAXEVAL)
			return false;
		
		evaluation_messages[no_evaluation] = message;
		evaluation_type[no_evaluation] = 1;
		evaluation_count++;
		no_evaluation++;
		return true;
	}
	
	public boolean boo(String message){
		if(no_evaluation >= MAXEVAL)
			return false;
		
		evaluation_messages[no_evaluation] = message;
		evaluation_type[no_evaluation] = -1;
		evaluation_count--;
		no_evaluation++;
		return true;
	}
	
	public boolean arrow(String message){
		if(no_evaluation >= MAXEVAL)
			return false;
		
		evaluation_messages[no_evaluation] = message;
		evaluation_type[no_evaluation] = 0;
		no_evaluation++;
		return true;
	}
	
	public void show(){
		String ID = String.format("%03d", this.ID);
		System.out.println();
		System.out.println("****************************************************************************************");
		System.out.println("Author\t: " + author);
		System.out.println("Title\t: " + title);
		System.out.println("Info\t: " + "ID " + ID + ", Evaluation " + evaluation_count);
		System.out.println("----------------------------------------------------------------------------------------");
		System.out.println(content);
		System.out.println("----------------------------------------------------------------------------------------");
		for(int i = 0; i < no_evaluation; i++){
			String eval;
			switch(evaluation_type[i]){
				case 1:
					eval = "Push";
					break;
				case -1:
					eval = "Boo";
					break;
				case 0:
					eval = "Arrow";
					break;
				default:
					eval = "Error";
					break;
			}
			System.out.println(eval + "\t: " + evaluation_messages[i]);
		}
		System.out.println("****************************************************************************************");
		System.out.println();
	}
	
	public void list(){
		String ID = String.format("%03d", this.ID);
		System.out.println(evaluation_count + "\t" + ID + "\t" + title + "\t" + author);
	}
	
	public String getTitle(){
		return title;
	}
}
