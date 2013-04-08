import POO_BBS.*;
import java.util.Scanner;
public class Demo {
	
	public static void main(String[] args){
		
		Scanner input = new Scanner(System.in);
		POODirectory home = new POODirectory("HomePage");
		User user = new User();
		Entry[] history = new Entry[1024];
		history[0] = home;
		int current;
		boolean exit;
		
		while(true){
			exit = false;
			current = 0;
			System.out.print("Please enter your username: ");
			user.setUserName(input.nextLine());
			
			while(!exit){
				history[current].show();
				//System.out.println(Integer.toString(current));
				/* doCommand */
				Entry.TYPE type = history[current].getType();
				boolean correct = false;
				String title;
				String content;
				int src, dest;
				while(!correct){
					
					/* printInfo */
					switch(type){
						case ARTICLE:
							System.out.print("(Arrow) (Boo) (Push) (r)Return (Exit) :");
							break;
						case BOARD:
							System.out.print("Add a (a)Article (e)Essence or (Delete) (r)Return (g)Goto (m)Move (z)EssenceRegion (Exit):");
							break;
						case DIRECTORY:
							System.out.print("Add a (b)Board (d)Directory (s)Splitting line or (Delete) (g)Goto (m)Move (r)Return (Exit):");
							break;
						default:
							System.out.println("Error Type");
							break;
					}
					
					/* parse input */
					correct = true;
					String tmp = input.nextLine();
					switch(tmp){
						case "r":
						case "R":
						if(current != 0)
								current--;
							break;
						case "exit":
						case "Exit":
							exit = true;
							break;
						case "a":
						case "A":
							if(type != Entry.TYPE.BOARD){
								correct = false;
								break;
							}
							System.out.print("Title\t: ");
							title = input.nextLine();
							System.out.print("Content\t: ");
							content = input.nextLine();
							POOArticle art = new POOArticle(history[current].length(), title, user.getUserName(), content);
							((POOBoard)history[current]).add(art);
							break;
						case "b":
						case "B":
							if(type != Entry.TYPE.DIRECTORY){
								correct = false;
								break;
							}
							System.out.print("Board Name\t: ");
							title = input.nextLine();
							POOBoard brd = new POOBoard(title);
							((POODirectory)history[current]).add(brd);
							break;
						case "d":
						case "D":
							if(type != Entry.TYPE.DIRECTORY){
								correct = false;
								break;
							}
								
							System.out.print("Directory Name\t: ");
							title = input.nextLine();
							POODirectory dir = new POODirectory(title);
							((POODirectory)history[current]).add(dir);
							break;
						case "e":
						case "E":
							if(type != Entry.TYPE.BOARD){
								correct = false;
								break;
							}
							
							System.out.print("source\t\t: ");
							src = input.nextInt();
							System.out.print("destination\t: ");
							dest = input.nextInt();
							input.nextLine();
							((POOBoard)history[current]).addEssence(src, dest);
							break;
						case "s":
						case "S":
							if(type != Entry.TYPE.DIRECTORY){
								correct = false;
								break;
							}
							((POODirectory)history[current]).add_split();
							break;	
						case "delete":
						case "Delete":
							if(type != Entry.TYPE.DIRECTORY && type != Entry.TYPE.BOARD){
								correct = false;
								break;
							}
							System.out.print("destination\t: ");
							dest = input.nextInt();
							input.nextLine();
							if(type == Entry.TYPE.DIRECTORY){
								((POODirectory)history[current]).del(dest);
							}else{
								((POOBoard)history[current]).del(dest);
							}
							break;
						case "g":
						case "G":
							if((type != Entry.TYPE.DIRECTORY && type != Entry.TYPE.BOARD) || current + 1 >= 1024){
								correct = false;
								break;
							}
							System.out.print("destination\t: ");
							dest = input.nextInt();
							input.nextLine();
							if(type == Entry.TYPE.DIRECTORY){
								history[current+1] = ((POODirectory)history[current]).get(dest);
							}else{
								history[current+1] = ((POOBoard)history[current]).get(dest);
							}
							if(history[current+1] == null){
								correct = false;
								break;
							}
							if(history[current+1].getType() != Entry.TYPE.DIRECTORY && history[current+1].getType() != Entry.TYPE.BOARD && history[current+1].getType() != Entry.TYPE.ARTICLE){
								correct = false;
								break;
							}
							current++;
							break;
						case "m":
						case "M":
							if(type != Entry.TYPE.BOARD && type != Entry.TYPE.DIRECTORY){
								correct = false;
								break;
							}
							System.out.print("source\t\t: ");
							src = input.nextInt();
							System.out.print("destination\t: ");
							dest = input.nextInt();
							input.nextLine();
							if(type == Entry.TYPE.BOARD){
								((POOBoard)history[current]).move(src, dest);
							}else{
								((POODirectory)history[current]).move(src, dest);
							}
							break;
						case "z":
						case "Z":
							if(type != Entry.TYPE.BOARD || current + 1 >= 1024){
								correct = false;
								break;
							}
							history[current+1] = ((POOBoard)history[current]).get(POOBoard.ESSENCE);
							if(history[current+1] == null || history[current+1].getType() != Entry.TYPE.DIRECTORY){
								correct = false;
								break;
							}
							current++;
							break;
						case "arrow":
						case "Arrow":
							if(type != Entry.TYPE.ARTICLE){
								correct = false;
								break;
							}
							System.out.print("Content\t: ");
							content = input.nextLine();
							((POOArticle)history[current]).arrow(content);
							break;
						case "boo":
						case "Boo":
							if(type != Entry.TYPE.ARTICLE){
								correct = false;
								break;
							}
							System.out.print("Content\t: ");
							content = input.nextLine();
							((POOArticle)history[current]).boo(content);
							break;
						case "push":
						case "Push":
							if(type != Entry.TYPE.ARTICLE){
								correct = false;
								break;
							}
							System.out.print("Content\t: ");
							content = input.nextLine();
							((POOArticle)history[current]).push(content);
							break;
						default:
							System.out.println("No Such Command");
							correct = false;
					}
				}
			}
		}
	}
}
