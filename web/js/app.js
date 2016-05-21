'use strict';

// main application module definition
angular.module('sogo', [
    'ui.router',
    'ngResource',
    'sogo.services',
    'sogo.directives',
    'sogo.controllers'
])

.config(function ($stateProvider, $httpProvider) {

    $stateProvider
        .state('greeting', {
            url: '/greeting',
            templateUrl: 'partials/views/greeting.html',
            controller: 'GreetingController',
            service: 'GreetingService'
        })

        .state('login', {
            url: '/login',
            templateUrl: 'partials/views/login.html'
        });

}).run(function ($state) {
   $state.go('greeting');
});