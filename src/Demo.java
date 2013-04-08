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
			
			System.out.print("Please enter your username: ");
			user.setUserName(input.nextLine());
			exit = false;
			current = 0;
			boolean help = false;
			while(!exit){
				/* print path and current */
				history[current].show();
				for(int i = 0; i <= current; i++){
					String left, right;
					switch(history[i].getType()){
						case ARTICLE:
							left = "<";
							right = ">";
							break;
						case BOARD:
							left = "[";
							right = "]";
							break;
						case DIRECTORY:
							left = "{";
							right = "}";
							break;
						default:
							left = "(";
							right = ")";
					}
					System.out.print(left + history[i].getTitle() + right + "/");
				}
				System.out.print("#> ");
				/* doCommand */
				Entry.TYPE type = history[current].getType();
				boolean correct = false;
				String title;
				String content;
				int src, dest;
				while(!correct){
					/* printInfo */
					if(help){
						switch(type){
							case ARTICLE:
								System.out.print("(Arrow) (Boo) (Push) (r)Return (Exit) :");
								break;
							case BOARD:
								System.out.print("Add a (a)Article (e)Essence (f)Focus or (Delete) (r)Return (g)Goto (m)Move (t)Transcript (z)EssenceRegion (Exit):");
								break;
							case DIRECTORY:
								System.out.print("Add a (b)Board (d)Directory (s)Splitting line or (Delete) (g)Goto (m)Move (r)Return (Exit):");
								break;
							default:
								System.out.println("Error Type");
								break;
						}
						help = false;
					}
					/* parse input */
					String cmd = input.nextLine();
					String[] cmds = cmd.split(" ");
					correct = true;
					String[] path;
					int search;
					boolean back;
					switch(cmds[0]){
						case "h":
						case "H":
						case "help":
						case "Help":
							correct = false;
							break;
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
							if(cmds.length != 3){
								System.out.print("Title\t: ");
								title = input.nextLine();
								System.out.print("Content\t: ");
								content = input.nextLine();
							}else{
								title = new String(cmds[1]);
								content = new String(cmds[2]);
							}
							POOArticle art = new POOArticle(history[current].length(), title, user.getUserName(), content);
							correct = ((POOBoard)history[current]).add(art);
							break;
						case "b":
						case "B":
							if(type != Entry.TYPE.DIRECTORY){
								correct = false;
								break;
							}
							
							if(cmds.length != 2){
								System.out.print("Board Name\t: ");
								title = input.nextLine();
							}else{
								title = new String(cmds[1]);
							}
							POOBoard brd = new POOBoard(title);
							correct = ((POODirectory)history[current]).add(brd);
							break;
						case "d":
						case "D":
							if(type != Entry.TYPE.DIRECTORY){
								correct = false;
								break;
							}
								
							if(cmds.length != 2){
								System.out.print("Directory Name\t: ");
								title = input.nextLine();
							}else{
								title = new String(cmds[1]);
							}
							POODirectory dir = new POODirectory(title);
							correct = ((POODirectory)history[current]).add(dir);
							break;
						case "e":
						case "E":
							if(type != Entry.TYPE.BOARD){
								correct = false;
								break;
							}
							
							if(cmds.length != 3){
								System.out.print("source\t\t: ");
								src = input.nextInt();
								System.out.print("destination\t: ");
								dest = input.nextInt();
								input.nextLine();
							}else{
								src = Integer.parseInt(cmds[1]);
								dest = Integer.parseInt(cmds[2]);
							}
							correct = ((POOBoard)history[current]).addEssence(src, dest);
							break;
						case "f":
						case "F":
							if(type != Entry.TYPE.BOARD){
								correct = false;
								break;
							}
							
							if(cmds.length != 2){
								System.out.print("destination: ");
								dest = input.nextInt();
								input.nextLine();
							}else{
								dest = Integer.parseInt(cmds[1]);
							}
							correct = ((POOBoard)history[current]).focus(dest);
							break;
						case "s":
						case "S":
							if(type != Entry.TYPE.DIRECTORY){
								correct = false;
								break;
							}
							correct = ((POODirectory)history[current]).add_split();
							break;	
						case "delete":
						case "Delete":
							if(type != Entry.TYPE.DIRECTORY && type != Entry.TYPE.BOARD){
								correct = false;
								break;
							}
							
							if(cmds.length != 2){
								System.out.print("destination\t: ");
								dest = input.nextInt();
								input.nextLine();
							}else{
								dest = Integer.parseInt(cmds[1]);
							}
							if(type == Entry.TYPE.DIRECTORY){
								correct = ((POODirectory)history[current]).del(dest);
							}else{
								correct = ((POOBoard)history[current]).del(dest);
							}
							break;
							
						case "cd":
							if(cmds.length != 2){
								System.out.print("path: ");
								content = input.nextLine();
							}else{
								content = new String(cmds[1]);
							}
							path = content.split("/");
							search = current;
							Entry[] newpath = new Entry[1024];
							back = false;
							int backpoint = search;
							for(int i = 0; i < path.length; i++){
								switch(path[i]){
									case ".":
										break;
									case "..":
										if(search <= 0 || back){
											correct = false;
											break;
										}
										search--;
										backpoint--;
										break;
									default:
										if(!back){
											if(search >= 1024 || history[search] == null){
												correct = false;
												break;
											}else if(history[search].getType() == Entry.TYPE.BOARD){
												newpath[search+1] = ((POOBoard)history[search]).get(path[i]);
												if(newpath[search+1] == null){
													correct = false;
													break;
												}
												search++;
											}else if(history[search].getType() == Entry.TYPE.DIRECTORY){
												back = true;
												backpoint = search;
												newpath[search+1] = ((POODirectory)history[search]).get(path[i]);
												if(newpath[search+1] == null){
													correct = false;
													break;
												}
												search++;
											}else{
												correct = false;
												break;
											}
										}else{
											if(search >= 1024 || newpath[search] == null){
												correct = false;
												break;
											}else if(newpath[search].getType() == Entry.TYPE.BOARD){
												if((newpath[search+1] =(Entry)((POOBoard)newpath[search]).get(path[i])) == null){
													correct = false;
												}
												search++;
											}else if(newpath[search].getType() == Entry.TYPE.DIRECTORY){
												newpath[search+1] = ((POODirectory)newpath[search]).get(path[i]);
												search++;
											}else{
												correct = false;
												break;
											}
										}
										break;
								}
								if(!correct)
									break;
							}
							if(!correct)
								break;
							
							for(int i = backpoint + 1; i <= search; i++){
								history[i] = newpath[i];
							}
							current = search;
							break;
							
						case "m":
						case "M":
							if(type != Entry.TYPE.BOARD && type != Entry.TYPE.DIRECTORY){
								correct = false;
								break;
							}
							if(cmds.length != 3){
								System.out.print("source(pos)\t: ");
								src = input.nextInt();
								System.out.print("destination\t: ");
								dest = input.nextInt();
								input.nextLine();
							}else{
								src = Integer.parseInt(cmds[1]);
								dest = Integer.parseInt(cmds[2]);
							}
							if(type == Entry.TYPE.BOARD){
								correct = ((POOBoard)history[current]).move(src, dest);
							}else{
								correct = ((POODirectory)history[current]).move(src, dest);
							}
							break;
						case "t":
						case "T":
							if(type != Entry.TYPE.BOARD && type != Entry.TYPE.DIRECTORY){
								correct = false;
								break;
							}
							
							if(cmds.length != 3){
								System.out.print("source(pos number)\t: ");
								src = input.nextInt();
								input.nextLine();
								System.out.print("destination(path)\t: ");
								content = input.nextLine();
							}else{
								src = Integer.parseInt(cmds[1]);
								content = new String(cmds[2]);
							}
							path = content.split("/");
							search = current;
							back = false;
							Entry unknown_entry = null;
							for(int i = 0; i < path.length; i++){
								switch(path[i]){
									case ".":
										break;
									case "..":
										if(search <= 0 || back){
											correct = false;
											break;
										}
										search--;
										break;
									default:
										if(!back){
											if(history[search].getType() == Entry.TYPE.BOARD){
												correct = false;
											}else if(history[search].getType() == Entry.TYPE.DIRECTORY){
												back = true;
												unknown_entry = ((POODirectory)history[search]).get(path[i]);
											}else{
												correct = false;
												break;
											}
										}else{
											if(unknown_entry == null){
												break;
											}else if(unknown_entry.getType() == Entry.TYPE.BOARD){
													correct = false;
											}else if(unknown_entry.getType() == Entry.TYPE.DIRECTORY){
												unknown_entry = ((POODirectory)unknown_entry).get(path[i]);
											}else{
												correct = false;
												break;
											}
										}
										break;
								}
								if(!correct)
									break;
							}
							if(!correct)
								break;
							int id = ((POOBoard)unknown_entry).length();
							POOArticle art2 = (POOArticle)((POOBoard)history[current]).get(src);
							((POOBoard)unknown_entry).transcript(id, art2);
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
							if(cmds.length != 2){
								System.out.print("Content\t: ");
								content = input.nextLine();
							}else{
								content = new String(cmds[1]);
							}
							correct = ((POOArticle)history[current]).arrow(content);
							break;
						case "boo":
						case "Boo":
							if(type != Entry.TYPE.ARTICLE){
								correct = false;
								break;
							}
							if(cmds.length != 2){
								System.out.print("Content\t: ");
								content = input.nextLine();
							}else{
								content = new String(cmds[1]);
							}
							correct = ((POOArticle)history[current]).boo(content);
							break;
						case "push":
						case "Push":
							if(type != Entry.TYPE.ARTICLE){
								correct = false;
								break;
							}
							if(cmds.length != 2){
								System.out.print("Content\t: ");
								content = input.nextLine();
							}else{
								content = new String(cmds[1]);
							}
							correct = ((POOArticle)history[current]).push(content);
							break;
						default:
							System.out.println("No Such Command");
							correct = false;
					}
					if(!correct){
						help = true;
					}
				}
			}
		}
	}
}
