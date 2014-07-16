package dto;


public class UserDTO {
	
	private String username;
	
	private String hash;
	
	public UserDTO(String username, String hash){
		this.setUsername(username);
		this.setHash(hash);
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

}
