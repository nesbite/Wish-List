'use strict';

angular.module('wishlist.services',[])

.factory('GreetingService',function($resource) {
    return $resource('http://rest-service.guides.spring.io/greeting');
});