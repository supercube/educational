import POO_BBS.*;
import java.util.Scanner;
public class Demo {
	
	public static void main(String[] args){
		Scanner input = new Scanner(System.in);
		POODirectory home = new POODirectory("HomePage");
		User user = new User();
		Entry[] history = new Entry[1024];
		history[0] = home;
		int current = 0;
		boolean exit = false;
		while(true){
			System.out.print("Please enter your username: ");
			user.setUserName(input.nextLine());
			
			while(!exit){
				history[current].show();
				
				/* doCommand */
				Entry.TYPE type = history[current].getType();
				boolean correct = false;
				while(!correct){
					
					/* printInfo */
					switch(type){
						case ARTICLE:
							System.out.print("r)Return e)Exit :");
							break;
						case BOARD:
							System.out.print("a)Add a article r)Return e)Exit:");
							break;
						case DIRECTORY:
							System.out.print("a)Add a article r)Return e)Exit:");
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
						case "e":
						case "E":
							exit = true;
							break;
						case "a":
						case "A":
							if(type != Entry.TYPE.BOARD && type != Entry.TYPE.DIRECTORY)
								correct = false;
							String title;
							String content;
							System.out.print("Title\t: ");
							title = input.nextLine();
							System.out.print("Content\t: ");
							content = input.nextLine();
							POOArticle art = new POOArticle(history[current].length(), title, user.getUserName(), content);
							if(type == Entry.TYPE.BOARD){
								((POOBoard)history[current]).add(art);
							}else{
								((POODirectory)history[current]).add(art);
							}
							break;
						default:
							System.out.println("Error Command");
							correct = false;
					}
				}
			}
			
			/*while(!exit){
				history[current].show();
				doCommand(user, history, current, exit, input);
			}*/
		}/*
		POOBoard board1 = new POOBoard("CF");
		POOArticle art1 = new POOArticle(1, "Test", "LPJ", "This is a simple test.\nOnly test for Article and Board.\nThe third line.");
		POOArticle art2 = new POOArticle(2, "Test2", "LPJ", "content is content");
		POOArticle art3 = new POOArticle(3, "Test3", "LPJ", "this is empty");
		POODirectory dir1 = new POODirectory("Favorite");
		POODirectory dir2 = new POODirectory("SC2");
		board1.add(art1);
		board1.add(art2);
		board1.add(art3);
		board1.move(0,4);
		art1.push("good");
		art1.boo("bad");
		art1.arrow("normal");
		art1.boo("SFSB");
		board1.show();
		art1.show();
		dir1.add(board1);
		dir1.add(art1);
		dir1.add_split();
		dir1.add(dir2);
		dir1.show();*/
	}
}
