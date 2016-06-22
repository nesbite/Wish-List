'use strict';

angular.module('wishlist')
    .controller('FriendRequestsController', FriendRequestsController);

function FriendRequestsController($scope, $state, Restangular) {
        $scope.getFriendRequests = function() {
            Restangular.all('friends/requests').getList().then(function (resp) {
                $scope.friendRequests = resp;
                console.log($scope.friendRequests);
            });
        };
        $scope.getFriendRequests();

        $scope.acceptRequest = function (friendId) {
            Restangular.all('friends').one('add').one(friendId).customPUT().then(function(){
                $scope.getFriendRequests();
            }, function () {
                $scope.getFriendRequests();
            });
        };

        $scope.rejectRequest = function (friendId) {
            Restangular.all('friends').one('requests').one('reject').one(friendId).customPUT().then(function(){
                $scope.getFriendRequests();
            }, function () {
                $scope.getFriendRequests();
            });
        }

}