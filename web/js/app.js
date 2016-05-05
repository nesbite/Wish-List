'use strict';

// definicja głównego modułu aplikacji
var app = angular.module('wishlist-app', [
    'ui.router',
    'wishlist-app.services',
    'wishlist-app.directives',
    'wishlist-app.controllers'
]);

app.config(function ($stateProvider, $urlRouterProvider) {

    $urlRouterProvider.otherwise('/users');

    $stateProvider
        .state('users.list', {
            url: '/users',
            templateUrl: 'partials/views/users.html',
            controller: 'UserController',
            service: 'UsersService'
        });

});

app.run(function(){

});