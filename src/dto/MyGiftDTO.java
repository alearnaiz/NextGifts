package dto;


public class MyGiftDTO extends GiftDTO{
	
	private String privateNotes;
	
	private String privateHashtags;
	
	private boolean gotIt;
	
	private boolean delivered;
	
	private boolean spread;
	
	private boolean copied;

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
	
	public boolean isCopied() {
		return copied;
	}

	public void setCopied(boolean copied) {
		this.copied = copied;
	}

}
