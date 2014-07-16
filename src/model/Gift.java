package model;

import java.sql.Timestamp;

public class Gift {
	
	private int id;
	
	private String name;
	
	private String image;
	
	private String description;

	private String urls;
	
	private String shop;

	private Double price;
	
	private String publicHashtags;
	
	private String privateNotes;
	
	private String privateHashtags;
	
	private boolean gotIt;
	
	private boolean delivered;
	
	private boolean spread;
	
	private boolean anonymous;
	
	private Timestamp startDate;
	
	private Timestamp endDate;

	private Timestamp spreadDate;
	
	private Integer giftId;

	private String username;

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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
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

	public String getPrivateNotes() {
		return privateNotes;
	}

	public void setPrivateNotes(String privateNotes) {
		this.privateNotes = privateNotes;
	}

	public String getPrivateHashtags() {
		return privateHashtags;
	}

	public void setPrivateHashtags(String privateHashtags) {
		this.privateHashtags = privateHashtags;
	}

	public boolean isGotIt() {
		return gotIt;
	}

	public void setGotIt(boolean gotIt) {
		this.gotIt = gotIt;
	}

	public boolean isDelivered() {
		return delivered;
	}

	public void setDelivered(boolean delivered) {
		this.delivered = delivered;
	}

	public boolean isSpread() {
		return spread;
	}

	public void setSpread(boolean spread) {
		this.spread = spread;
	}

	public boolean isAnonymous() {
		return anonymous;
	}

	public void setAnonymous(boolean anonymous) {
		this.anonymous = anonymous;
	}

	public Timestamp getStartDate() {
		return startDate;
	}

	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}

	public Timestamp getEndDate() {
		return endDate;
	}

	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}

	public Timestamp getSpreadDate() {
		return spreadDate;
	}

	public void setSpreadDate(Timestamp spreadDate) {
		this.spreadDate = spreadDate;
	}

	public Integer getGiftId() {
		return giftId;
	}

	public void setGiftId(Integer giftId) {
		this.giftId = giftId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
}
