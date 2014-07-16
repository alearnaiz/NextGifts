var nextGiftsApp = angular.module('nextGiftsApp' , []);

// Parameters for sharing between Controllers
nextGiftsApp.factory('Share', function() {
	var share = {
		id: null
	};
	return share;
});

// Login Controller
nextGiftsApp.controller('loginCtrl', function($scope, $http){
	
	$scope.username = "";
	$scope.password = "";
	
	$scope.signIn = function(){
		var username = $scope.username;
		var password = $scope.password;
		if(username != "" && password != ""){
			$("#signInL").button("disable");
			$http.get(URL.login, {params: {username: username, password: password}})
				.success(function(data, status) {
					if (data){
						window.localStorage.setItem("username", data.username);
						window.localStorage.setItem("hash", data.hash);
						window.location.href = "#home";
					}
					else{
						window.alert("Username or password not valid");
						$("#usernameL").parent().css("border-color", "red");
						$("#passwordL").parent().css("border-color", "");
						$scope.password = "";
						$("#signInL").button("enable");
					}
				}).error(function(data, status) {
					window.alert("Something is wrong");
					$("#signInL").button("enable");
			});
		}
		else{
			if (username == ""){
				$("#usernameL").parent().css("border-color", "red");
			}
			else{
				$("#usernameL").parent().css("border-color", "");
			}
			if (password == ""){
				$("#passwordL").parent().css("border-color", "red");
			}
			else{
				$("#passwordL").parent().css("border-color", "");
			}
		}
	};
	
	$scope.signUp = function(){
		window.location.href = "#register";
	};
	
	var checkUserHash = function(){
		if (localStorage.getItem("hash")){
			$http.get(URL.checkHash, {params: {hash: localStorage.getItem("hash")}})
				.success(function(data, status){
					if (data){
						window.localStorage.setItem("username", data);
						window.location.href = "#home";
					}
					else{
						window.localStorage.removeItem("username");
						window.localStorage.removeItem("hash");
					}
				}).error(function(data, status){
					window.alert("Something is wrong");
					window.localStorage.removeItem("username");
					window.localStorage.removeItem("hash");
			});
		}
	};
	
	// Checks if the user is already logged (page without hash)
	(function(){
		if (!window.location.hash){
			checkUserHash();
		}
	}());
	
	// Checks if the user is already logged (page with login hash)
	$("#login").on("pagebeforeshow", function(){
		checkUserHash();
	});
	
	// Sets default data and styles
	$("#login").on("pagebeforehide", function(){
		$scope.username = "";
		$scope.password = "";
		$("#usernameL").parent().css("border-color", "");
		$("#passwordL").parent().css("border-color", "");
		$("#signInL").button("enable");
	});
	
});


// Register Controller
nextGiftsApp.controller('registerCtrl', function($scope, $http){
	
	$scope.firstName = "";	
	$scope.lastNames = "";
	$scope.username = "";
	$scope.password = "";
	
	$scope.signUp = function(){
		var firstName = $scope.firstName;
		var lastNames = $scope.lastNames;
		var username = $scope.username;
		var password = $scope.password;
		
		if (firstName != "" && lastNames != "" && username != "" && password != ""){
			$("#signUpR").button("disable");
			$http.post(URL.register, null, {params: {firstName: firstName, lastNames: lastNames, username: username, password: password}})
				.success(function(data, status) {
					if (data){
						window.localStorage.setItem("username", data.username);
						window.localStorage.setItem("hash", data.hash);
						window.location.href = "#home";
	  				}
	  				else{
	  					window.alert("Sorry, username already registered. Choose a different one");
	  					clearStyles();
	  					$("#usernameR").parent().css("border-color", "red");
	  					$("#signUpR").button("enable");
	  				}
				}).error(function(data, status) {
					window.alert("Something is wrong");
					$("#signUpR").button("enable");
			});
		}
		else{
			if (firstName == ""){
				$("#firstNameR").parent().css("border-color", "red");
			}
			else{
				$("#firstNameR").parent().css("border-color", "");
			}
			if (lastNames == ""){
				$("#lastNamesR").parent().css("border-color", "red");
			}
			else{
				$("#lastNamesR").parent().css("border-color", "");
			}
			if (username == ""){
				$("#usernameR").parent().css("border-color", "red");
			}
			else{
				$("#usernameR").parent().css("border-color", "");
			}
			if (password == ""){
				$("#passwordR").parent().css("border-color", "red");
			}
			else{
				$("#passwordR").parent().css("border-color", "");
			}
		}
	};
	
	$scope.goLogin = function(){
		window.location.href = "#login";
	};
	
	var clearStyles = function(){
		$("#firstNameR").parent().css("border-color", "");
		$("#lastNamesR").parent().css("border-color", "");
		$("#usernameR").parent().css("border-color", "");
		$("#passwordR").parent().css("border-color", "");
		$("#signUpR").button("enable");
	};
	
	// Sets default data and styles
	$("#register").on("pagebeforehide", function(){
		$scope.firstName = "";
		$scope.lastNames = "";
		$scope.username = "";
		$scope.password = "";
		clearStyles();
	});
	
});


// Home Controller
nextGiftsApp.controller('homeCtrl', function($scope, $http, Share){
	
	$scope.gifts = null;
	
	$scope.seeGift = function(id){
		Share.id = id;
		window.location.href = "#seeGift";
	};
	
	$scope.copyGift = function(id){
		Share.id = id;
		window.location.href = "#copyGift";
	};
	
	$scope.goMyGifts = function(){
		window.location.href = "#myGifts";
	};

	$scope.goAddGift = function(){
		window.location.href = "#addGift";
	};
	
	$scope.closeSession = function(){
		window.localStorage.removeItem("username");
		window.localStorage.removeItem("hash");
		window.location.href = "#login";
	};
	
	$scope.refresh = function(){
		window.location.reload();
	};
	
	var pageShowHome = false;
	
	var showList = function(){
		$("#listH").listview("refresh");
		$("#listH").show();
	};
	
	$("#home").on("pagebeforeshow", function(){
		$http.get(URL.publicGifts, {params: {hash: window.localStorage.getItem("hash")}})
			.success(function(data, status) {
				$scope.gifts = data;
				if (pageShowHome){
					// FIXME (Workaround)
					setTimeout(function(){
						showList();
					}, 10);
				}
			}).error(function(data, status) {
				window.alert("Something is wrong");
		});
	});
	
	$("#home").on("pageshow", function(){
		pageShowHome = true;
		if ($scope.gifts){
			showList();
		}
	});
	
	// Sets default data and styles
	$("#home").on("pagebeforehide", function(){
		$scope.gifts = null;
		pageShowHome = false;
		$("#listH").hide();
	});
	
});


// Add Gift Controller
nextGiftsApp.controller('addGiftCtrl', function($scope, $http){
	
	$scope.gift = {
		name: "",
		description: "",
		urls: "",
		shop: "",
		price: null,
		publicHashtags: "",
		privateNotes: "",
		privateHashtags: "",
		gotIt: false,
		delivered: false,
		spread: false,
		anonymous: false
	};
	
	$scope.addGift = function(){
		var name = $scope.gift.name;
		var description = $scope.gift.description;
		var urls = $scope.gift.urls;
		var shop = $scope.gift.shop;
		var price = $scope.gift.price;
		var publicHashtags = $scope.gift.publicHashtags;
		var privateNotes = $scope.gift.privateNotes;
		var privateHashtags = $scope.gift.privateHashtags;
		var gotIt = $scope.gift.gotIt;
		var delivered = $scope.gift.delivered;
		var spread = $scope.gift.spread;
		var anonymous = $scope.gift.anonymous;
		if (name != ""){
			$("#addGiftAG").button("disable");
			$http.post(URL.createGift, null, {params: {name: name, description: description, urls: urls, shop: shop, price: price, 
				publicHashtags: publicHashtags, privateNotes: privateNotes, privateHashtags: privateHashtags, gotIt: gotIt, 
				delivered: delivered, spread: spread, anonymous: anonymous, hash: window.localStorage.getItem("hash")}})
				.success(function(data, status) {
					window.location.href = "#myGifts";
				}).error(function(data, status) {
					window.alert("Something is wrong");
					$("#addGiftAG").button("enable");
			});
		}
		else{
			$("#nameAG").parent().css("border-color", "red");
		}
	};
	
	$scope.goHome = function(){
		window.location.href = "#home";
	};
	
	$scope.goMyGifts = function(){
		window.location.href = "#myGifts";
	};
	
	$scope.closeSession = function(){
		window.localStorage.removeItem("username");
		window.localStorage.removeItem("hash");
		window.location.href = "#login";
	};
	
	$scope.changeGotIt = function(){
		if (!$scope.gift.gotIt && $scope.gift.delivered){
			$scope.gift.delivered = false;
			$("#deliveredAG").prop("checked", false).checkboxradio("refresh");
		}
	};
	
	$scope.changeDelivered = function(){
		if ($scope.gift.delivered && !$scope.gift.gotIt){
			$scope.gift.gotIt = true;
			$("#gotItAG").prop("checked", true).checkboxradio("refresh");
		}
	};
	
	$scope.changeSpread = function(){
		if ($scope.gift.spread){
			$("#anonymousAG").checkboxradio("option", "disabled", false);
		}
		else{
			if ($scope.gift.anonymous){
				$scope.gift.anonymous = false;
				$("#anonymousAG").prop("checked", false).checkboxradio("refresh");
			}
			$("#anonymousAG").checkboxradio("option", "disabled", true);
		}
	};
	
	// Sets default data and styles
	$("#addGift").on("pagebeforehide", function(){
		$scope.gift.name = "";
		$scope.gift.description = "";
		$scope.gift.urls = "";
		$scope.gift.shop = "";
		$scope.gift.price = null;
		$scope.gift.publicHashtags = "";
		$scope.gift.privateNotes = "";
		$scope.gift.privateHashtags = "";
		$scope.gift.gotIt = false;
		$scope.gift.delivered = false;
		$scope.gift.spread = false;
		$scope.gift.anonymous = false;
		$("#gotItAG").prop("checked", false).checkboxradio("refresh");
		$("#deliveredAG").prop("checked", false).checkboxradio("refresh");
		$("#spreadAG").prop("checked", false).checkboxradio("refresh");
		$("#anonymousAG").prop("checked", false).checkboxradio("refresh");
		$("#anonymousAG").checkboxradio("option", "disabled", true);
		$("#nameAG").parent().css("border-color", "");
		$("#addGiftAG").button("enable");
	});
	
});
	
// My Gifts Controller
nextGiftsApp.controller('myGiftsCtrl', function($scope, $http, Share){
	
	$scope.gifts = null;
	
	$scope.seeGift = function(id){
		Share.id = id;
		window.location.href = "#seeGift";
	};
	
	$scope.editGift = function(id){
		Share.id = id;
		window.location.href = "#editGift";
	};
	
	$scope.goHome = function(){
		window.location.href = "#home";
	};

	$scope.goAddGift = function(){
		window.location.href = "#addGift";
	};
	
	$scope.closeSession = function(){
		window.localStorage.removeItem("username");
		window.localStorage.removeItem("hash");
		window.location.href = "#login";
	};
	
	var pageShowMyGifts = false;
	
	var showList = function(){
		$("#listMG").listview("refresh");
		$("#listMG").show();
	};
	
	$("#myGifts").on("pagebeforeshow", function(){
		$http.get(URL.myGifts, {params: {hash: window.localStorage.getItem("hash")}})
			.success(function(data, status) {
				$scope.gifts = data;
				if (pageShowMyGifts){
					// FIXME (Workaround)
					setTimeout(function(){
						showList();
					}, 10);
				}
			}).error(function(data, status) {
				window.alert("Something is wrong");
		});
	});
	
	$("#myGifts").on("pageshow", function(){
		pageShowMyGifts = true;
		if ($scope.gifts){
			showList();
		}
	});
	
	// Sets default data and styles
	$("#myGifts").on("pagebeforehide", function(){
		$scope.gifts = null;
		pageShowMyGifts = false;
		$("#listMG").hide();
	});
	
});

//See Gift Controller
nextGiftsApp.controller('seeGiftCtrl', function($scope, $http, Share){
	
	$scope.name = "";
	$scope.description = "";
	$scope.urls = "";
	$scope.shop = "";
	$scope.price = null;
	$scope.publicHashtags = "";
	$scope.anonymous = false;
	$scope.username = "";
	$scope.privateNotes = "";
	$scope.privateHashtags = "";
	$scope.yours = false;
	$scope.gotIt = false;
	$scope.delivered = false;
	$scope.spread = false;
	$scope.copied = false;
	
	$scope.copyGift = function(){
		window.location.href = "#copyGift";
	};
	
	$scope.goHome = function(){
		window.location.href = "#home";
	};
	
	$scope.goMyGifts = function(){
		window.location.href = "#myGifts";
	};

	$scope.goAddGift = function(){
		window.location.href = "#addGift";
	};
	
	$scope.closeSession = function(){
		window.localStorage.removeItem("username");
		window.localStorage.removeItem("hash");
		window.location.href = "#login";
	};
	
	$("#seeGift").on("pagebeforeshow", function(){
		$http.get(URL.getGift(Share.id), {params: {hash: window.localStorage.getItem("hash")}})
			.success(function(data, status) {
				$scope.name = data.name;
				$scope.description = data.description;
				$scope.urls = data.urls;
				$scope.shop = data.shop;
				$scope.price = data.price;
				$scope.publicHashtags = data.publicHashtags;
				$scope.anonymous = data.anonymous;
				$scope.username = data.username;
				$scope.yours = data.yours;
				if (data.privateNotes != undefined){
					$scope.privateNotes = data.privateNotes;
				}
				if (data.privateHashtags != undefined){
					$scope.privateHashtags = data.privateHashtags;
				}
				if (data.gotIt != undefined){
					$scope.gotIt = data.gotIt;
				}
				if (data.delivered != undefined){
					$scope.delivered = data.delivered;
				}
				if (data.spread != undefined){
					$scope.spread = data.spread;
				}
				if (data.copied != undefined){
					$scope.copied = data.copied;
				}
			}).error(function(data, status) {
				window.alert("Something is wrong");
		});
	});
	
	// Sets default data
	$("#seeGift").on("pagebeforehide", function(){
		$scope.name = "";
		$scope.description = "";
		$scope.urls = "";
		$scope.shop = "";
		$scope.price = null;
		$scope.publicHashtags = "";
		$scope.privateNotes = "";
		$scope.privateHashtags = "";
		$scope.anonymous = false;
		$scope.username = "";
		$scope.yours = false;
		$scope.gotIt = false;
		$scope.delivered = false;
		$scope.spread = false;
		$scope.copied = false;	
	});
	
});


// Copy Gift Controller
nextGiftsApp.controller('copyGiftCtrl', function($scope, $http, Share){
	
	$scope.privateNotes = "";
	$scope.privateHashtags = "";
	$scope.gotIt = false;
	$scope.delivered = false;
	
	$scope.copyGift = function(){
		var privateNotes = $scope.privateNotes;
		var privateHashtags = $scope.privateHashtags;
		var gotIt = $scope.gotIt;
		var delivered = $scope.delivered;
		$("#copyGiftCG").button("disable");
		$http.post(URL.copyGift(Share.id), null, {params: {privateNotes: privateNotes, privateHashtags: privateHashtags, gotIt: gotIt, delivered: delivered, hash: window.localStorage.getItem("hash")}})
			.success(function(data, status) {
				Share.id = data;
				window.location.href = "#seeGift";
			}).error(function(data, status) {
				window.alert("Something is wrong");
				$("#copyGiftCG").button("enable");
		});
	};
	
	$scope.goBack = function(){
		window.history.back();
	};
	
	$scope.goHome = function(){
		window.location.href = "#home";
	};
	
	$scope.goMyGifts = function(){
		window.location.href = "#myGifts";
	};

	$scope.goAddGift = function(){
		window.location.href = "#addGift";
	};
	
	$scope.closeSession = function(){
		window.localStorage.removeItem("username");
		window.localStorage.removeItem("hash");
		window.location.href = "#login";
	};
	
	$scope.changeGotIt = function(){
		if (!$scope.gotIt && $scope.delivered){
			$scope.delivered = false;
			$("#deliveredCG").prop("checked", false).checkboxradio("refresh");
		}
	};
	
	$scope.changeDelivered = function(){
		if ($scope.delivered && !$scope.gotIt){
			$scope.gotIt = true;
			$("#gotItCG").prop("checked", true).checkboxradio("refresh");
		}
	};
	
	// Sets default data and styles
	$("#copyGift").on("pagebeforehide", function(){
		$scope.privateNotes = "";
		$scope.privateHashtags = "";
		$scope.gotIt = false;
		$scope.delivered = false;
		$("#gotItCG").prop("checked", false).checkboxradio("refresh");
		$("#deliveredCG").prop("checked", false).checkboxradio("refresh");
		$("#copyGiftCG").button("enable");
	});
	
});


// Edit Gift Controller
nextGiftsApp.controller('editGiftCtrl', function($scope, $http, Share){
	
	$scope.privateNotes = "";
	$scope.privateHashtags = "";
	$scope.gotIt = false;
	$scope.delivered = false;
	$scope.spread = false;
	$scope.anonymous = false;
	$scope.copied = true;

	$scope.editGift = function(){
		$("#editGiftEG").button("disable");
		$("#deleteGiftEG").button("disable");
		var privateNotes = $scope.privateNotes;
		var privateHashtags = $scope.privateHashtags;
		var gotIt = $scope.gotIt;
		var delivered = $scope.delivered;
		if (!$scope.copied){
			var spread = $scope.spread;
			var anonymous = $scope.anonymous;
			$http.post(URL.editGift(Share.id), null, {params: {privateNotes: privateNotes, privateHashtags: privateHashtags, gotIt: gotIt, delivered: delivered, spread: spread, anonymous: anonymous, hash: window.localStorage.getItem("hash")}})
				.success(function(data, status) {
					window.location.href = "#seeGift";
				}).error(function(data, status) {
					window.alert("Something is wrong");
					$("#editGiftEG").button("enable");
					$("#deleteGiftEG").button("enable");
			});
		}
		else{
			$http.post(URL.editCopiedGift(Share.id), null, {params: {privateNotes: privateNotes, privateHashtags: privateHashtags, gotIt: gotIt, delivered: delivered, hash: window.localStorage.getItem("hash")}})
				.success(function(data, status) {
					window.location.href = "#seeGift";
				}).error(function(data, status) {
					window.alert("Something is wrong");
					$("#editGiftEG").button("enable");
					$("#deleteGiftEG").button("enable");
			});
		}
	};
	
	$scope.deleteGift = function(){
		var deleteGift = window.confirm("Do you really want to delete this gift?");
		if (deleteGift){
			$("#editGiftEG").button("disable");
			$("#deleteGiftEG").button("disable");
			$http.post(URL.deleteGift(Share.id), null, {params: {hash: window.localStorage.getItem("hash")}})
				.success(function(data, status) {
					window.location.href = "#myGifts";
				}).error(function(data, status) {
					window.alert("Something is wrong");
					$("#editGiftEG").button("enable");
					$("#deleteGiftEG").button("enable");
			});
		}
	};
	
	$scope.goBack = function(){
		window.history.back();
	};
	
	$scope.goHome = function(){
		window.location.href = "#home";
	};
	
	$scope.goMyGifts = function(){
		window.location.href = "#myGifts";
	};

	$scope.goAddGift = function(){
		window.location.href = "#addGift";
	};
	
	$scope.closeSession = function(){
		window.localStorage.removeItem("username");
		window.localStorage.removeItem("hash");
		window.location.href = "#login";
	};
	
	$scope.changeGotIt = function(){
		if (!$scope.gotIt && $scope.delivered){
			$scope.delivered = false;
			$("#deliveredEG").prop("checked", false).checkboxradio("refresh");
		}
	};
	
	$scope.changeDelivered = function(){
		if ($scope.delivered && !$scope.gotIt){
			$scope.gotIt = true;
			$("#gotItEG").prop("checked", true).checkboxradio("refresh");
		}
	};
	
	// FIXME Workaround
	$scope.changeSpread = function(){
		$scope.spread = !$scope.spread;
	};
	
	// FIXME Workaround
	$scope.changeAnonymous = function(){
		$scope.anonymous = !$scope.anonymous;
	};
	
	$("#editGift").on("pagebeforeshow", function(){
		$http.get(URL.getGift(Share.id), {params: {hash: window.localStorage.getItem("hash")}})
			.success(function(data, status) {
				$scope.privateNotes = data.privateNotes;
				$scope.privateHashtags = data.privateHashtags;
				$scope.gotIt = data.gotIt;
				if (data.gotIt){
					$("#gotItEG").prop("checked", true).checkboxradio("refresh");
				}
				$scope.delivered = data.delivered;
				if(data.delivered){
					$("#deliveredEG").prop("checked", true).checkboxradio("refresh");
				}
				if (!data.copied){
					$scope.copied = false;
					$scope.spread = data.spread;
					$scope.anonymous = data.anonymous;
				}
			}).error(function(data, status) {
				window.alert("Something is wrong");
		});
	});
	
	// Sets default data and styles
	$("#editGift").on("pagebeforehide", function(){
		$scope.privateNotes = "";
		$scope.privateHashtags = "";
		$scope.gotIt = false;
		$scope.delivered = false;
		$scope.spread = false;
		$scope.anonymous = false;
		$scope.copied = true;
		$("#editGiftEG").button("enable");
		$("#deleteGiftEG").button("enable");
		$("#gotItEG").prop("checked", false).checkboxradio("refresh");
		$("#deliveredEG").prop("checked", false).checkboxradio("refresh");
	});
	
});