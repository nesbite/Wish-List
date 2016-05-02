
// definicja głównego modułu aplikacji
var app = angular.module('wishlist-app', [
    'ui.router'
]);

app.config(function ($stateProvider, $urlRouterProvider) {

    $urlRouterProvider.otherwise('/users');

    $stateProvider
        .state('users', {
            url: '/users',
            abstract: true,
            template: '<ui-view></ui-view>'
        })
        .state('users.list', {
            url: '',
            templateUrl: 'index.html',
            controller: 'UserController',
            service: 'UsersService'
        });

});

app.run(function(){

});