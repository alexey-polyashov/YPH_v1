angular.module('app').controller('usersController', function ($scope, $http, $localStorage) {

    $scope.baseURL_usersAPI = baseURL_usersAPI;
    $scope.baseURL_userdata = '#!/users/userdata';
    $scope.baseURL_newuser = '#!/users/newuser';

    $scope.pageCounter = 0;
    $scope.totalPage = 0;
    $scope.pagesInView = 3;
    $scope.showPages = true;

    $scope.loadUsers = function(){
        var recordsOnPage = 20;
        $http({
            url: baseURL_usersAPI + 'userlist',
            method: 'GET',
            params: {'page': $scope.pageCounter,
                     'recordsOnPage': recordsOnPage}
         })
        .then(function successCallback(response){
            showUsers(response.data);
        }, function errorCallback(response) {
            console.log("Error: " + response);
        });
    };

    function showUsers(response_data){
        $scope.users = response_data.content;
        $scope.totalPage = response_data.totalPages;
        $scope.users_count = response_data.totalElements;
        let minPageNumber = Math.floor($scope.pageCounter / $scope.pagesInView) * $scope.pagesInView;
        $scope.viewedPageNumbers = [];
        $scope.pageNumbers = [];
        for(let i=0; i<$scope.pagesInView && i+minPageNumber < $scope.totalPage; i++){
            $scope.viewedPageNumbers[i] = (minPageNumber+i)+1;
            $scope.pageNumbers[i] = (minPageNumber+i);
        };
        for(let i=0; i<response_data.numberOfElements; i++){
            $scope.users[i].position = $scope.users[i].position.name;
            $scope.users[i].division = $scope.users[i].division.name;
        }
    }

    $scope.clicPrevPage = function () {
        if($scope.pageCounter>0){
            $scope.pageCounter -= 1;
            $scope.loadUsers();
        }
        return false;
    };

    $scope.clicNextPage = function () {
        if($scope.pageCounter<($scope.totalPage-1)){
            $scope.pageCounter += 1;
            $scope.loadUsers();
        }
        return false;
    };

    $scope.goToPage = function (page) {
        if(page>=0 && page<$scope.totalPage){
            $scope.pageCounter = page;
            $scope.loadUsers();
        }
        return false;
    };

    $scope.loadUsers();

});