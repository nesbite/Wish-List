'use strict';

controllers.controller('UserController', 'UsersService', function ($scope, $http, UsersService) {
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

//controllers.controller('UserController', ['$scope', '$dialogs', 'UsersService',
//    function ($scope, $dialogs, UsersService) {
//
//        $scope.data = {};
//
//        getUsers();
//
//        var getUsers = function () {
//
//            UsersService.getUsers(function (successResult) {
//                $scope.users = successResult;
//            },
//            function (errorResult) {
//                $dialogs.error("Error occurred!", errorResult.data.error);
//            }
//        );
//    };
//}]);