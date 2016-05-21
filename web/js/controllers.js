'use strict';

angular.module('wishlist.controllers', [])

    .controller('GreetingController', function ($scope, $state, config, GreetingService) {
        console.log(config.serverUrl);
        $scope.greeting = GreetingService.get();

    });