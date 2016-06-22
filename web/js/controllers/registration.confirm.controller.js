'use strict';

angular.module('wishlist')
    .controller('RegistrationConfirmController', RegistrationConfirmController);


function RegistrationConfirmController($scope, $state, $location, Restangular) {
        var token = $location.search()['token'];
        console.log(token);
        $scope.confirmRegistration = function(){

            Restangular.one('registrationConfirm?token='+token).get().then(function(resp){
                    console.log(resp);
                    $scope.message = "Registration confirmed";
                },
                function(resp){
                    console.log(resp);
                    $scope.message = "Registration confirmed";

                });
        };
        $scope.confirmRegistration();
        $scope.goBack = function(){
            $location.path("/login");
        }
}