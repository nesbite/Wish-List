'use strict';

angular.module('wishlist')
    .factory('UserService', UserService);
function UserService($resource, serverUrl) {
    var service = {};

    service.getUsers = function() {
        return $resource(serverUrl + "/users").query();
    };

    service.getUser = function(username) {
        return $resource(serverUrl + "/users/" + username).get();
    };

    return service;
}