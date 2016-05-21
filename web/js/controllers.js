'use strict';

angular.module('wishlist.controllers', [])

    .controller('GreetingController', function ($scope, $state, GreetingService) {
        $scope.greeting = GreetingService.get();
    })
    .controller('FriendController', function ($scope, $state, UserService) {
        var users = UserService.getUsers();

        $scope.friends = users;
    });

