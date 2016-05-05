'use strict';

app.controller('UserController', 'UsersService', function ($scope, $http, UsersService) {
    $scope.users = {};

    $scope.getUsers = function(){
        UsersService.getUsers().then(function (users) {
            console.log(users);
            $scope.users = users;
        }, function (err) {
            console.log("getUsers ERROR ", err);
        });
    }

});