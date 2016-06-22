'use strict';


angular.module('wishlist')
    .controller('GiftController', GiftController);

function GiftController($scope, $state, Restangular) {
    $scope.getGifts = function () {
        Restangular.all('gifts').getList().then(function (resp) {
            $scope.gifts = resp;
            console.log($scope.gifts);
        });
    };

    $scope.isActive = function (route) {
        return route === $location.path();
    };

    $scope.getGifts();

    $scope.deleteGift = function (gift) {
        Restangular.all('gifts').one('remove').one(gift.id).remove().then(function () {
            $scope.getGifts();
        });
    };

    $scope.addGift = function (gift) {
        Restangular.all('gifts').customPOST(gift, 'add', undefined, {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }).then(function () {
            gift.name = "";
            gift.description = "";
            $scope.getGifts();
        });
    };
    $scope.setCurrentGift = function (gift) {
        $scope.currentGift = gift;
    };
    $scope.editGift = function (gift) {
        var id = gift.id;
        Restangular.all('gifts').one('update', id).customPUT(gift, undefined, undefined, {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }).then(function () {
            $scope.getGifts();
        });
    };
    $scope.publish = function (friendId) {
        Restangular.all('friends').one('publish').one(friendId).customPUT().then(function () {
            $scope.getGifts();
        }, function () {
            $scope.getGifts();
        });
    };
}