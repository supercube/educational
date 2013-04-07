import POO_BBS.*;
import java.util.Scanner;
public class Demo {
	private static String[] cmds = {"r","a"};
	
	public static void main(String[] args){
		Scanner input = new Scanner(System.in);
		POODirectory home = new POODirectory("HomePage");
		User user = new User();
		Entry[] history = new Entry[1024];
		history[0] = home;
		Integer current = 0;
		Boolean exit = false;
		while(true){
			System.out.print("Please enter your username: ");
			user.setUserName(input.nextLine());
			while(!exit){
				history[current].show();
				doCommand(user, history, current, exit, input);
				System.out.println(exit);
			}
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
	
	public static void doCommand(User user, Entry[] history, Integer current, Boolean exit, Scanner input){
		while(true){
			printInfo(history[current].getType());
			if(parseInput(user, history, current, exit, input) == true){
				break;
			}
		}
	}
	
	public static void printInfo(Entry.TYPE type){
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
	}
	
	public static boolean parseInput(User user, Entry[] history, Integer current, Boolean exit, Scanner input){
		Entry.TYPE type = history[current].getType();
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
					return false;
				String title;
				String content;
				System.out.print("Title\t: ");
				title = input.nextLine();
				System.out.print("Content\t: ");
				content = input.nextLine();
				POOArticle art = new POOArticle(history[current].length(), title, user.getUserName(), content);
				break;
			default:
				System.out.println("Error Command");
				return false;
		}
		return true;
	}
}

class data {
	
	
}
