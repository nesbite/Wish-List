'use strict';

angular.module('wishlist.services', [])

    .factory('GreetingService', function ($resource) {
        console.log();
        return $resource('http://rest-service.guides.spring.io/greeting');
    });