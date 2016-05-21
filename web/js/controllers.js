'use strict';

angular.module('wishlist.controllers', [])

.controller('GreetingController',function($scope,$state, GreetingService) {

    $scope.greeting = GreetingService.get();
    
});