'use strict';

angular.module('wishlist.controllers', [])

    .controller('GreetingController', function ($scope, $state, GreetingService) {
        $scope.greeting = GreetingService.get();
    })
    .controller('LoginController', function ($scope, $state, Restangular) {
        
        $scope.login = function(){
            // alert('username=' + $scope.credentials.username + '&password=' + $scope.credentials.password);
            Restangular.all('login').post('username=' + $scope.credentials.username + '&password=' + $scope.credentials.password);
        };
    })
    .controller('FriendController', function ($scope, $state, UserService) {
        var users = UserService.getUsers();

        $scope.friends = users;
    });

