
app.controller('UserController', function ($scope, $http, UsersService) {
    $scope.user = {};

    $scope.getUsers = function(){
        UsersService.getUsers().then(function (users) {
            console.log(users);
            $scope.users = users;
        }, function (err) {
            console.log("getUsers ERROR ", err);
        });
    }

});