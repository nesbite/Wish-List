'use strict';

angular.module('wishlist.controllers', [])

    .controller('GreetingController', function ($scope, $state, GreetingService) {
        $scope.greeting = GreetingService.get();
    })
    .controller('RegistrationController', function ($scope, $state, $location, Restangular) {
        $scope.register = function(){

            Restangular.one('user').one('registration').customPOST($scope.user, undefined, undefined, {'Content-Type': 'application/json'}).then(function(resp){
                console.log(resp);
                $location.path("/login");

            },
                function(resp){
                console.log(resp);
            });
        };
    })
    .controller('ChangePasswordController', function ($scope, $state, $location, Restangular) {
        var token = $location.search()['token'];
        var id = $location.search()['id'];
        $scope.changePassword = function(){

            Restangular.one('user').one('changePassword')
                .customPOST('id='+id+'&token='+token+'&oldPassword='+$scope.credentials.oldPassword+'&password='+ $scope.credentials.password, undefined, undefined, {})
                .then(function(resp){
                console.log(resp);
                $location.path("/login");
            },
                function(resp){
                console.log(resp);

            });
        };
    })
    .controller('RegistrationConfirmController', function ($scope, $state, $location, Restangular) {
        var token = $location.search()['token'];
        console.log(token);
        $scope.confirmRegistration = function(){

            Restangular.one('registrationConfirm?token='+token).get().then(function(resp){
                console.log(resp);
                $scope.message = "Registration confirmed";
            },
                function(resp){
                    console.log(resp);
                    $scope.message = "Registration confirmed";

            });
        };
        $scope.confirmRegistration();
        $scope.goBack = function(){
            $location.path("/login");
        }
    })
    .controller('LoginController', function ($scope, $rootScope, $state, $location, Restangular) {

        $scope.login = function(){
            Restangular.all('login').post('username=' + $scope.credentials.username
                + '&password=' + $scope.credentials.password).then(function(resp){
                console.log(resp);
                $location.path("/gifts");

            }, function(resp){
                console.log(resp);
            });
        };

        $scope.logout = function(){
            Restangular.all('logout').post().then(function(resp){
                console.log(resp);
            }, function(resp){
                console.log(resp);
            });
            $location.path('login');

        };

        $scope.recoverEmail = function(email){
            Restangular.one('user').post('resetPassword?email='+email).then(function(resp){
                console.log(resp);
            }, function(resp){
                console.log(resp);
            });
        };
        

    })
    .controller('FriendController', function ($scope, $state, Restangular) {
        $scope.getFriends = function() {
            Restangular.all('friends').getList().then(function (resp) {
                $scope.friends = resp;
                for (var i = 0; i < $scope.friends.size; i++) {
                    Restangular.all('gifts').one($scope.friends[i].username).getList().then(function (response, i) {
                        $scope.friends[i].gifts = response;
                    })
                }
                console.log($scope.friends);
            });
        };
        $scope.getFriends();

        $scope.showDetail = function (item) {
            if ($scope.active != item.id) {
                $scope.active = item.id;
            }
            else {
                $scope.active = null;
            }
        };
        $scope.deleteFriend = function (friend) {
            Restangular.all('friends').one('delete', friend.username).remove().then(function () {
                $scope.getFriends();
            });
        };

        $scope.addFriend = function (friendId) {
            Restangular.all('friends').one('add').one(friendId).customPUT().then(function(){
                $scope.getFriends();
            }, function () {
                $scope.getFriends();
            });
        }

    })
    .controller('GiftController', function ($scope, $state, Restangular) {
        $scope.getGifts = function() {
            Restangular.all('gifts').getList().then(function (resp) {
                $scope.gifts = resp;
                console.log($scope.gifts);
            });
        };
        
        $scope.getGifts();

        $scope.deleteGift = function (gift) {
            Restangular.all('gifts').one('remove').one(gift.id).remove().then(function () {
                $scope.getGifts();
            });
        };

        $scope.addGift = function (gift) {
            Restangular.all('gifts').customPOST(gift, 'add', undefined, {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }).then(function () {
                gift.name = "";
                gift.description = "";
                $scope.getGifts();
            });
        };
        $scope.setCurrentGift = function(gift) {
            $scope.currentGift = gift;
        };
        $scope.editGift = function(gift){
            var id = gift.id;
            Restangular.all('gifts').one('update', id).customPUT(gift, undefined, undefined, {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }).then(function () {
                $scope.getGifts();
            });
        };

});

