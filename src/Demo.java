import POO_BBS.*;
public class Demo {
	public static void main(String[] args){
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
		dir1.add(dir2);
		dir1.show();
	}
}
