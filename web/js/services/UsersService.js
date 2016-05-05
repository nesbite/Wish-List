'use strict';

services.service('UsersService', ['RestService', function (RestService) {

    var service = {};

    service.getUsers = RestService.getFunctionFactory('/users');

    return service;
}]);

//services.factory('UsersService', function ($resource) {
//    return $resource('/users', {}, {
//        getUsers: {
//            method: 'GET',
//            isArray: true
//        }
//    });
//});