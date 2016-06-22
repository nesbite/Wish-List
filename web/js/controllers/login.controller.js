'use strict';

angular.module('wishlist')
    .controller('LoginController', LoginController);

function LoginController($scope, $rootScope, $state, $location, Restangular) {

    $scope.isActive = function(route) {
        return route === $location.path();
    };

    $scope.login = function(){
        Restangular.all('login').post('username=' + $scope.credentials.username
            + '&password=' + $scope.credentials.password).then(function(resp){
            console.log(resp);
            $location.path("/gifts");

        }, function(resp){
            alert("Login failed!");
            console.log(resp);
        });
    };

    $scope.logout = function(){
        Restangular.all('logout').post().then(function(resp){
            console.log(resp);
        }, function(resp){
            console.log(resp);
        });
        $location.path('login');

    };

    $scope.recoverEmail = function(email){
        Restangular.one('user').post('resetPassword?email='+email).then(function(resp){
            console.log(resp);
        }, function(resp){
            console.log(resp);
        });
    };
}