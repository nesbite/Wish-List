'use strict';

angular.module('wishlist')
    .controller('FriendController', FriendController);

function FriendController($scope, $state, Restangular) {
        $scope.getFriends = function() {
            Restangular.all('friends').getList().then(function (resp) {
                $scope.friends = resp;
                for (var i = 0; i < $scope.friends.size; i++) {
                    Restangular.all('gifts').one($scope.friends[i].username).getList().then(function (response, i) {
                        $scope.friends[i].gifts = response;
                    })
                }
                console.log($scope.friends);
            });
        };
        $scope.getFriends();

        $scope.showDetail = function (item) {
            if ($scope.active != item.id) {
                $scope.active = item.id;
            }
            else {
                $scope.active = null;
            }
        };
        $scope.deleteFriend = function (friend) {
            Restangular.all('friends').one('delete', friend.username).remove().then(function () {
                $scope.getFriends();
            });
        };

        $scope.addFriend = function (friendId) {
            Restangular.all('friends').one('add').one(friendId).customPUT().then(function(){
                $scope.getFriends();
            }, function () {
                $scope.getFriends();
            });
        }

    }