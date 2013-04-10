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
						System.out.println("Help");
						System.out.println("------------------------------------------------------------------------------------------------------------");
						switch(type){
							case ARTICLE:
								System.out.println("(Arrow) (Boo) (Push)");
								break;
								
							case BOARD:
							case DIRECTORY:
								System.out.println("Add a (mkart)Article (mkbrd)Board (mkdir)Directory (split)Splitting Line");
								System.out.println("Move [source pos] [destination pos]: move source entry to the destination");
								System.out.println("rm [pos] : remove indicated entry");
								if(type == Entry.TYPE.BOARD){
									System.out.println("Essence [source pos] [destination pos]: copy the source Article to destination Board in Essence Region");
									System.out.println("transcript [source pos] [destination path]: transcript source Article to destination Baord");
									System.out.println("focus [pos]: mark indicated Article");
									System.out.println("z       : visit the Essence Region");
								}
								break;
							default:
								break;
						}
						System.out.println("cd [path]: move to indicated (relative) path; ");
						System.out.println("ls       : list");
						System.out.println("return   : move to the previos page");
						System.out.println("exit     : logout and change user");
						
						help = false;
						correct = true;
						break;
					}
					/* parse input */
					String cmd = input.nextLine();
					String[] cmds = cmd.split(" ");
					correct = true;
					String[] path;
					int search;
					boolean back;
					String tmp1, tmp2;
					switch(cmds[0].toLowerCase()){
						case "h":
						case "help":
							correct = false;
							help = true;
							break;
						
						case "return":
							if(current != 0)
								current--;
							break;
						
						case "exit":
							exit = true;
							break;
						
						case "mkart":
							if(type != Entry.TYPE.BOARD){
								correct = false;
								System.out.println("Not in a Board");
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
						
						case "mkbrd":
							if(type != Entry.TYPE.DIRECTORY){
								correct = false;
								System.out.println("Not in a Directory");
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
						
						case "mkdir":
							if(type != Entry.TYPE.DIRECTORY){
								correct = false;
								System.out.println("Not in a Directory");
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
						
						case "read":
							if(type != Entry.TYPE.BOARD || current + 1 >= 1024){
								correct = false;
								System.out.println("Not in a Board or Stack Overflow");
								break;
							}
							
							if(cmds.length != 2){
								System.out.print("destination: ");
								tmp1 = input.nextLine();
								if(!tmp1.matches("-?[0-9]+?")){
									System.out.println("destination is not an Integer");
									correct = false;
									break;
								}
								dest = Integer.parseInt(tmp1);
							}else{
								if(!cmds[1].matches("-?[0-9]+?")){
									correct = false;
									System.out.println("destination is not an Integer");
									break;
								}
								dest = Integer.parseInt(cmds[1]);
							}
							history[current+1] = ((POOBoard)history[current]).get(dest);
							if(history[current+1] == null){
								correct = false;
								System.out.println("No such article");
								break;
							}
							current++;
							break;
						
						case "essence":
							if(type != Entry.TYPE.BOARD){
								correct = false;
								System.out.println("Not in a Board");
								break;
							}
							
							if(cmds.length != 3){
								System.out.print("source\t\t: ");
								tmp1 = input.nextLine();
								System.out.print("destination\t: ");
								tmp2 = input.nextLine();
								if(!tmp1.matches("-?[0-9]+?") || !tmp2.matches("-?[0-9]+?")){
									System.out.println("source or destination is not an Integer");
									correct = false;
									break;
								}
								src = Integer.parseInt(tmp1);
								dest = Integer.parseInt(tmp2);
							}else{
								if(!cmds[1].matches("-?[0-9]+?") || !cmds[2].matches("-?[0-9]+?")){
									correct = false;
									System.out.println("source or destination is not an Integer");
									break;
								}
								src = Integer.parseInt(cmds[1]);
								dest = Integer.parseInt(cmds[2]);
							}
							correct = ((POOBoard)history[current]).addEssence(src, dest);
							break;
						
						case "focus":
							if(type != Entry.TYPE.BOARD){
								correct = false;
								System.out.println("Not in a Board");
								break;
							}
							
							if(cmds.length != 2){
								System.out.print("destination: ");
								tmp1 = input.nextLine();
								if(!tmp1.matches("-?[0-9]+?")){
									System.out.println("destination is not an Integer");
									correct = false;
									break;
								}
								dest = Integer.parseInt(tmp1);
							}else{
								if(!cmds[1].matches("-?[0-9]+?")){
									correct = false;
									System.out.println("destination is not an Integer");
									break;
								}
								dest = Integer.parseInt(cmds[1]);
							}
							correct = ((POOBoard)history[current]).focus(dest);
							break;
							
						case "split":
							if(type != Entry.TYPE.DIRECTORY){
								System.out.println("Not in a Directory");
								correct = false;
								break;
							}
							correct = ((POODirectory)history[current]).add_split();
							break;	
						case "rm":
							if(type != Entry.TYPE.DIRECTORY && type != Entry.TYPE.BOARD){
								correct = false;
								break;
							}
							
							if(cmds.length != 2){
								System.out.print("destination: ");
								tmp1 = input.nextLine();
								if(!tmp1.matches("-?[0-9]+?")){
									System.out.println("destination is not an Integer");
									correct = false;
									break;
								}
								dest = Integer.parseInt(tmp1);
							}else{
								if(!cmds[1].matches("-?[0-9]+?")){
									correct = false;
									System.out.println("destination is not an Integer");
									break;
								}
								dest = Integer.parseInt(cmds[1]);
							}
							if(type == Entry.TYPE.DIRECTORY){
								correct = ((POODirectory)history[current]).del(dest);
							}else{
								correct = ((POOBoard)history[current]).del(dest);
							}
							break;
						case "ls":
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
							if(!correct){
								System.out.println("Path not found");
								break;
							}
							for(int i = backpoint + 1; i <= search; i++){
								history[i] = newpath[i];
							}
							current = search;
							break;
							
						case "move":
							if(type != Entry.TYPE.BOARD && type != Entry.TYPE.DIRECTORY){
								System.out.println("Not in a Board or Directory");
								correct = false;
								break;
							}
							if(cmds.length != 3){
								System.out.print("source\t\t: ");
								tmp1 = input.nextLine();
								System.out.print("destination\t: ");
								tmp2 = input.nextLine();
								if(!tmp1.matches("-?[0-9]+?") || !tmp2.matches("-?[0-9]+?")){
									correct = false;
									System.out.println("source or destination is not an Integer");
									break;
								}
								src = Integer.parseInt(tmp1);
								dest = Integer.parseInt(tmp2);
							}else{
								if(!cmds[1].matches("-?[0-9]+?") || !cmds[2].matches("-?[0-9]+?")){
									correct = false;
									System.out.println("source or destination is not an Integer");
									break;
								}
								src = Integer.parseInt(cmds[1]);
								dest = Integer.parseInt(cmds[2]);
							}
							if(type == Entry.TYPE.BOARD){
								correct = ((POOBoard)history[current]).move(src, dest);
							}else{
								correct = ((POODirectory)history[current]).move(src, dest);
							}
							break;
						case "transcript":
							if(type != Entry.TYPE.BOARD && type != Entry.TYPE.DIRECTORY){
								correct = false;
								System.out.println("Not in a Board or Directory");
								break;
							}
							
							if(cmds.length != 3){
								System.out.print("source(pos number)\t: ");
								tmp1 = input.nextLine();
								if(!tmp1.matches("-?[0-9]+?")){
									correct = false;
									System.out.println("source is not an Integer");
									break;
								}
								src = Integer.parseInt(tmp1);
								System.out.print("destination(path)\t: ");
								content = input.nextLine();
							}else{
								if(!cmds[1].matches("-?[0-9]+?")){
									correct = false;
									System.out.println("source is not an Integer");
									break;
								}
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
							if(!correct){
								System.out.println("Path not found");
								break;
							}
							if(unknown_entry instanceof POOBoard == false){
								correct = false;
								System.out.println("destination is not a Board");
								break;
							}
							int id = ((POOBoard)unknown_entry).length();
							POOArticle art2 = (POOArticle)((POOBoard)history[current]).get(src);
							((POOBoard)unknown_entry).transcript(id, art2);
							break;
						case "z":
							if(type != Entry.TYPE.BOARD || current + 1 >= 1024){
								System.out.println("Not in a Board or Stack Overflow");
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
							if(type != Entry.TYPE.ARTICLE){
								System.out.println("Not in an Article");
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
							if(type != Entry.TYPE.ARTICLE){
								System.out.println("Not in an Article");
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
							if(type != Entry.TYPE.ARTICLE){
								System.out.println("Not in an Article");
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
							help = true;
					}
				}
			}
		}
	}
}
