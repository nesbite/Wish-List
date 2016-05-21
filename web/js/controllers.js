'use strict';

angular.module('wishlist.controllers', [])

    .controller('GreetingController', function ($scope, $state, GreetingService, UserService) {
        $scope.greeting = GreetingService.get();
        
        $scope.users = UserService.query(); // because it is an array

    });