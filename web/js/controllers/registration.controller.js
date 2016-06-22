'use strict';

angular.module('wishlist')
    .controller('RegistrationController', RegistrationController);


function RegistrationController($scope, $state, $location, Restangular) {
        $scope.register = function(){
            Restangular.one('user').one('registration').customPOST($scope.user, undefined, undefined, {'Content-Type': 'application/json'}).then(function(resp){
                console.log(resp);
                $location.path("/login");
            },
                function(resp){
                console.log(resp);
            });
        };
}

