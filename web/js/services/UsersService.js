'use strict';

services.service('UsersService', ['RestService', function (RestService) {

    var service = {};

    service.getUsers = RestService.getFunctionFactory('/users');

    return service;
}]);