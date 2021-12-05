var baseURL = window.location.protocol + '//' + window.location.host;
var baseUrL_application = '/';
if(baseURL.indexOf('localhost')>-1){
    var baseUrL_application = '/';
}

const baseURL_usersAPI = baseUrL_application + 'api/v21/users/';
const baseURL_filesAPI = baseUrL_application + 'api/v21/files/';

(function ($localStorage) {
    'use strict';

    angular
        .module('app', ['ngRoute', 'ngStorage'])
        .config(config)
        .run(run);

    function config($routeProvider) {
        $routeProvider
//            .when('/', {
//                templateUrl: '/index.html',
//                controller: 'indexController'
//            })
            .when('/users', {
                  templateUrl: 'users/users.html',
                  controller: 'usersController'
            })
            .when('/tasks', {
                  templateUrl: 'tasks/tasks.html',
                  controller: 'tasksController'
            })
            .otherwise({
                redirectTo: '/'
            })
            ;
    }

    function run($rootScope, $http, $localStorage) {

        if ($localStorage.yphUser) {
            $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.yphUser.token;
        }

    }

})();

angular.module('app').controller('indexController', function ($rootScope, $location, $scope, $http, $localStorage) {

    $scope.loginError = false;
    $scope.username = '';

    $scope.tryToAuth = function () {
        $http.post(baseURL_auth, $scope.user)
            .then(function successCallback(response) {
                if (response.data.token) {
                    $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                    $localStorage.yphUser = {username: $scope.user.username, token: response.data.token};
                    $scope.username = $scope.user.username;
                    $scope.loginError=false;
                    if ($scope.user.username) {
                        $scope.user.username = null;
                    }
                    if ($scope.user.password) {
                        $scope.user.password = null;
                    }
                }else{
                    $scope.loginError=true;
                }
            }, function errorCallback(response) {
                $scope.loginError=true;
                $scope.username='';
            });
    };

    $scope.clearUser = function () {
        delete $localStorage.yphUser;
        $http.defaults.headers.common.Authorization = '';
    };

    $scope.tryToLogout = function () {
        $scope.clearUser();
        if ($scope.user.username) {
            $scope.user.username = null;
        }
        if ($scope.user.password) {
            $scope.user.password = null;
        }
        $scope.username ='';
    };

    $scope.isUserLoggedIn = function () {
        if ($localStorage.yphUser) {
            return true;
        } else {
            return false;
        }
    };

    $scope.initCart = function(){
        if(!$localStorage.cartUuid){
            $http({
                url: baseURL_cart + 'generate',
                method: 'GET',
                params: {}
            }).then(function(response){
            console.log(response);
                $localStorage.cartUuid = response.data.value;
            });
          }
    }

    if ($localStorage.yphUser) {
        $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.marketUser.token;
        $scope.username =$localStorage.marketUser.username;
    }

})