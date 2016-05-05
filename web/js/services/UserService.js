'use strict';

services.service('UserService', ['RestService', function (RestService) {

    var service = {};

    service.getUsers = RestService.getFunctionFactory('/users');
    service.getUser = RestService.getFunctionFactory('/{username}')

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