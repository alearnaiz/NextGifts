package dto;


public class GiftDTO {
	
	private String name;
	
	private String description;

	private String urls;
	
	private String shop;

	private Double price;
	
	private String publicHashtags;
	
	private boolean anonymous;
	
	private String username;
	
	private boolean yours;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrls() {
		return urls;
	}

	public void setUrls(String urls) {
		this.urls = urls;
	}

	public String getShop() {
		return shop;
	}

	public void setShop(String shop) {
		this.shop = shop;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
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

	public boolean isYours() {
		return yours;
	}

	public void setYours(boolean yours) {
		this.yours = yours;
	}
	
}
