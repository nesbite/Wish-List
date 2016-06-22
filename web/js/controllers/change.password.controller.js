'use strict';

angular.module('wishlist')
    .controller('ChangePasswordController', ChangePasswordController);

function ChangePasswordController($scope, $state, $location, Restangular) {
        var token = $location.search()['token'];
        var id = $location.search()['id'];
        $scope.changePassword = function(){

            Restangular.one('user').one('changePassword')
                .customPOST('id='+id+'&token='+token+'&oldPassword='+$scope.credentials.oldPassword+'&password='+ $scope.credentials.password, undefined, undefined, {})
                .then(function(resp){
                        console.log(resp);
                        $location.path("/login");
                    },
                    function(resp){
                        console.log(resp);

                    });
        };
    };