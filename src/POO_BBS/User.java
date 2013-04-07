package POO_BBS;

public class User {
	private String UserName;
	
	public User(){
		this(null);
	}
	
	public User(String username){
		this.UserName = username;
	}
	
	public void setUserName(String username){
		this.UserName = username;
	}
	
	public String getUserName(){
		return UserName;
	}
}
