package dto;

public class HomeGiftDTO {
	
	private int id;
	
	private String name;
	
	private String publicHashtags;
	
	private boolean anonymous;
	
	private String username;
	
	private boolean copied;
	
	private boolean mine;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPublicHashtags() {
		return publicHashtags;
	}

	public void setPublicHashtags(String publicHashtags) {
		this.publicHashtags = publicHashtags;
	}

	public boolean isAnonymous() {
		return anonymous;
	}

	public void setAnonymous(boolean anonymous) {
		this.anonymous = anonymous;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean isCopied() {
		return copied;
	}

	public void setCopied(boolean copied) {
		this.copied = copied;
	}

	public boolean isMine() {
		return mine;
	}

	public void setMine(boolean mine) {
		this.mine = mine;
	}

}
