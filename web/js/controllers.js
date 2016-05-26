'use strict';

angular.module('wishlist.controllers', [])

    .controller('GreetingController', function ($scope, $state, GreetingService) {
        $scope.greeting = GreetingService.get();
    })
    .controller('LoginController', function ($scope, $state, $location, Restangular) {
        
        $scope.login = function(){
            // alert('username=' + $scope.credentials.username + '&password=' + $scope.credentials.password);

            Restangular.all('login').post('username=' + $scope.credentials.username
                + '&password=' + $scope.credentials.password).then(function(response){
                var resp = response;
                console.log(resp);
                $location.path("/gifts");

            }, function(resp){
                console.log(resp);
            });
            // Restangular.all('users').getList().then(function(response){
            //     $scope.users = response;
            //     console.log($scope.users);
            // })

        };
        $scope.logout = function(){
            // alert('username=' + $scope.credentials.username + '&password=' + $scope.credentials.password);

            Restangular.all('logout').post().then(function(response){
                var resp = response;
                console.log(resp);
            }, function(resp){
                console.log(resp);
            });
            // Restangular.all('users').getList().then(function(response){
            //     $scope.users = response;
            //     console.log($scope.users);
            // })



        };

        $scope.usersM = function(){
            // alert('username=' + $scope.credentials.username + '&password=' + $scope.credentials.password);

            Restangular.all('users').getList().then(function(response){
                $scope.users = response;
                console.log($scope.users);
            }, function(resp){
                console.log(resp);
            })



        };


    })
    .controller('FriendController', function ($scope, $state, Restangular) {
        var users = Restangular.all('friends').getList().then(function(resp){
            $scope.friends = resp;
            console.log($scope.friends);
        });

        $scope.deleteFriend = function (friend) {
            Restangular.all('friends').one('delete').one(friend.username).remove();
        }

        $scope.addFriend = function (friend) {
            Restangular.all('friends').one('add').one(friend.username).save();
        }

    })
    .controller('GiftController', function ($scope, $state, Restangular) {
    var gifts = Restangular.all('gifts').getList().then(function(resp){
        $scope.gifts = resp;
        console.log($scope.gifts);
    });
        $scope.deleteGift = function (gift) {
            Restangular.all('gifts').one('remove').one(gift.id).remove();
        }

        $scope.addGift = function (gift) {
            Restangular.all('gifts').one('add', gift).post();
        }

});

