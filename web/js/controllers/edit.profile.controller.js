'use strict';

angular.module('wishlist')
    .controller('EditProfileController', EditProfileController);

function EditProfileController($scope, $state, $location, Restangular) {
        $scope.showProfile= function(){
            Restangular.one('users').get().then(function(resp){
                    console.log(resp);
                    $scope.user = resp;
                },
                function(resp){
                    console.log(resp);
                });
        };
        $scope.showProfile();

        $scope.editProfile = function(user){
            Restangular.one('users').one('update').customPUT(user, undefined, undefined, {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }).then(function(resp){
                console.log(resp);
                $scope.showProfile();
            });
        };
}
