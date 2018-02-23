var book4allApp = angular.module('books4all',[]);


book4allApp.controller('mainController', ['$scope', function($scope){
	$scope.view = 'view/home.html';
	
	$scope.setView = function(view){
		$scope.view = view;
	}
	$scope.getView = function(){
		if($scope.isUserLoggedIn == true){
			$scope.view = 'view/home.html';			
		}
	}
}]);


book4allApp.controller('loginController', ['$rootScope', '$scope', '$http', function($rootScope, $scope, $http) {
	$http.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded; charset=utf-8";
	
	$scope.pwdPlaceHolder = "Password (Hint: Passw0rd)";
	$rootScope.loginWarning = null;
	$rootScope.isUserLoggedIn = false;

	// on login button click
	$scope.login = function() {
        $http({
            url : 'login',
            method : "POST",
            data : {
                'username' : $scope.loginUsername,
                'password' : $scope.loginPassword
            }
        }).then(function(response) {
            console.log(response.data);
            if(response.data.status == "LoginSuccess"){
            	$rootScope.user = response.data.object;
            	$rootScope.isUserLoggedIn = true;
                $scope.setView('view/home.html');
                $rootScope.loginWarning = null;
            }
            else if(response.data.status == "LoginFailure"){
            	$rootScope.loginWarning = response.data.details;
            }
        });
    }
    
    // on logout button click
    $scope.logout = function(){
    	$rootScope.user = null;
    	$rootScope.isUserLoggedIn = false;
        $scope.setView('view/home.html');
        $rootScope.loginWarning = null;
    }
}]);
//=================================== EO loginController ====================================================

book4allApp.controller('registrationController', ['$rootScope', '$scope', '$http',  function($rootScope, $scope, $http) {
	$scope.regWarningMessage = null;
	
	validUserData = function(){
		//TODO: implement validation
		return true;
	}	
	
	//on registration submit
	$scope.register = function(){
		
		if(validUserData){
			$http({
		        url : 'registration',
		        method : "POST",
		        data : {
		        	'Username': $scope.regUsername,
		    		'Email': $scope.regEmail,
		    		'Phone': $scope.regPhone,
		    		'Nickname': $scope.regNickname,
		    		'Password': $scope.regPassword,
		    		'Description': $scope.regDescription,
		    		'PhotoURL': $scope.regPhotoUrl,
		    		'AddrStreet': $scope.regAddrStreet,
		    		'AddrNumber': $scope.regAddrNumber,
		    		'AddrCity': $scope.regAddrCity,
		    		'AddrZip': $scope.regAddrZip
		        }
		    }).then(function(response) {
		        console.log(response.data);
		        if(response.data.status == "RegistrationSuccess"){
	                $scope.setView('view/home.html');
	                $rootScope.registrationSuccess = response.data.details;
	            }
	            else{
	            	$rootScope.registrationWarnings = response.data;
	            }
		    });
		}
		else{
			//$scope.regWarningMessage = "Please try again with valid data";
		}
	}
}]);
//=================================== EO registrationController ====================================================

book4allApp.controller('booksController', ['$scope', '$http', function($scope, $http) {
	$scope.books = null;
	
	$http({
        url : 'books',
        method : "POST",
        data : {}
    }).then(function(response) {
        console.log(response.data);
        $scope.books = response.data.object;
    });
}]);
//=================================== EO booksController ====================================================

book4allApp.controller('myBooksController', ['$rootScope', '$scope', '$http',  function($rootScope, $scope, $http) {
	$scope.books = null;
	$scope.noBooksWarning = null;
	$scope.bookToRead = null; //Path
	$scope.currentBook = null; //Object BookOfUser
	$scope.bookInfo = null;
	
	$scope.read = function(book){
		$(".mask").show();
		$scope.currentBook = book;
		$scope.bookToRead = book.Path;
		$(".mybooks-reading-window").show("slow", function(){
			$(".mybooks-reading-window").scrollTop(book.ScrollLocation);
		});
	}
	
	$scope.closeBook = function(){
		$(".mask").hide();
		$scope.currentBook.ScrollLocation = $(".mybooks-reading-window").scrollTop();
		$(".mybooks-reading-window").hide();
		
		$scope.scrollLocation = $(".mybooks-reading-window").scrollTop(); 
		$scope.currentBook.isOpen = true;
		
		$http({
	        url : 'books/closeBook',
	        method : "POST",
	        data : {
	        	'BookId': $scope.currentBook.BookId,
	        	'UserId': $scope.user.Id,
	        	'ScrollLocation': $scope.currentBook.ScrollLocation
	        	}
	    }).then(function(response) {
//	    	if(response.data.status == "Failure"){
//	    		console.log(response.data);
//	    	}
	    });
		
		$scope.currentBook = null;
	}
	
	$scope.like = function(book){
		book.Likes += 1;
		
		$http({
	        url : 'books/likeBook',
	        method : "POST",
	        data : {
	        	'BookId': book.BookId,
	        	'UserId': $scope.user.Id
	        	}
	    }).then(function(response) {
	    	console.log(response.data);
	    	if(response.data.status == "Success"){
	    		book.IsLiked = true;
	    		$(".book-id-" + book.BookId + " .store-book-like-button").hide();
	    		$(".book-id-" + book.BookId + " .store-book-unlike-button").show();
	    	}
	    	else{
	    		$scope.noBooksWarning = response.data.details;
	    		book.Likes -= 1;
	    	}
	    });
	}
	
	$scope.unlike = function(book){
		book.Likes -= 1;
		
		$http({
	        url : 'books/unlikeBook',
	        method : "POST",
	        data : {
	        	'BookId': book.BookId,
	        	'UserId': $scope.user.Id,
	        	}
	    }).then(function(response) {
	    	console.log(response.data);
	    	if(response.data.status == "Success"){
	    		book.IsLiked = false;
	    		$(".book-id-" + book.BookId + " .store-book-unlike-button").hide();
	    		$(".book-id-" + book.BookId + " .store-book-like-button").show();
	    	}
	    	else{
	    		$scope.noBooksWarning = response.data.details;
	    		book.Likes += 1;
	    	}
	    });
		
	}
	
	$scope.showBookInfo = function(book){
		$(".mask").show();
		$(".mybooks-book-info").show();
		$scope.bookInfo = book;
	}
	$scope.closeBookInfo = function(){
		$(".mask").hide();
		$(".mybooks-book-info").hide();
		$scope.bookInfo = null;
	}
	
	
	$http({
        url : 'books/myBooks',
        method : "POST",
        data : {'value': $scope.user.Id}
    }).then(function(response) {
    	console.log(response.data);
    	if(response.data.status == "BooksFound"){
	        $scope.books = response.data.object;
    	}
    	else{
    		$scope.noBooksWarning = response.data.details;
    	}
    });
}]);
//=================================== EO myBooksController ====================================================

book4allApp.controller('adminController', ['$scope', '$http',  function($scope, $http) {
	
	$scope.browseUsers = function(){
		$http({
	        url : 'admin/allUsers',
	        method : "POST",
	        data : {}
	    }).then(function(response) {
	        console.log(response.data);
	    });
	}
}]);
//=================================== EO adminController ====================================================
