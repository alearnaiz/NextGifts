var localHostUrl = "/";

var URL = {
	login: localHostUrl+"NextGifts/rest/login",
	checkHash: localHostUrl+"NextGifts/rest/checkhash",
	register: localHostUrl+"NextGifts/rest/users",
	publicGifts: localHostUrl+"NextGifts/rest/home",
	createGift: localHostUrl+"NextGifts/rest/gifts/create",
	myGifts: localHostUrl+"NextGifts/rest/gifts/all",
	getGift: function(id){
		return localHostUrl+"NextGifts/rest/gifts/"+id;
	},
	copyGift: function(id){
		return localHostUrl+"NextGifts/rest/gifts_copy/create/"+id;
	},
	editGift: function(id){
		return localHostUrl+"NextGifts/rest/gifts/edit/"+id;
	},
	editCopiedGift: function(id){
		return localHostUrl+"NextGifts/rest/gifts_copy/edit/"+id;
	},
	deleteGift: function(id){
		return localHostUrl+"NextGifts/rest/gifts/delete/"+id;
	}
};