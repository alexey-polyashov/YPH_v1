angular.module('app').controller('tasksController', function ($scope, $http, $localStorage) {

    $scope.baseURL_tasksAPI = baseURL_tasksAPI;
    $scope.baseURL_taskdata = '#!/tasks/taskdata';
    $scope.baseURL_newtask = '#!/tasks/newtask';

    $scope.pageCounter = 0;
    $scope.totalPage = 0;
    $scope.pagesInView = 3;
    $scope.showPages = true;

    $scope.users = [];

    $scope.taskFilter = [];


    $scope.loadUsers = function(){
        $http({
            url: baseURL_usersAPI + '/userlist',
            method: 'GET'
        }).then(function(response){
console.log(response.data);
            $scope.users = response.data.content;
        });
    };


    $scope.addNewFilter = function() {
        $scope.taskFilter.push('');
    };
    $scope.removeFilter = function(z) {
        $scope.taskFilter.splice(z, 1);
    };

    $scope.loadTasks = function(){
        var recordsOnPage = 20;
        $http({
            url: baseURL_tasksAPI + 'tasklist',
            method: 'GET',
            params: {'page': $scope.pageCounter,
                     'recordsOnPage': recordsOnPage}
         })
        .then(function successCallback(response){
            showTasks(response.data);
        }, function errorCallback(response) {
            console.log("Error: " + response);
        });
    };

    function showTasks(response_data){
        $scope.tasks = response_data.content;
        $scope.totalPage = response_data.totalPages;
        $scope.tasks_count = response_data.totalElements;
        let minPageNumber = Math.floor($scope.pageCounter / $scope.pagesInView) * $scope.pagesInView;
        $scope.viewedPageNumbers = [];
        $scope.pageNumbers = [];
        for(let i=0; i<$scope.pagesInView && i+minPageNumber < $scope.totalPage; i++){
            $scope.viewedPageNumbers[i] = (minPageNumber+i)+1;
            $scope.pageNumbers[i] = (minPageNumber+i);
        };
console.log($scope.tasks);
        for(let i=0; i<response_data.numberOfElements; i++){
            $scope.tasks[i].repeatable = $scope.tasks[i].repeatable==true ? "X" : "";
            $scope.tasks[i].active = $scope.tasks[i].active==true ? "X" : "";
            $scope.tasks[i].done = $scope.tasks[i].done==true ? "X" : "";
            $scope.tasks[i].common = $scope.tasks[i].common==true ? "X" : "";
            let e_present = "";
            for(let j=0; j<$scope.tasks[i].taskExecutors.length; j++){
                e_present = e_present + $scope.tasks[i].taskExecutors[j].userName + ", ";
            }
            e_present = e_present.substr(0, e_present.length - 2);
            $scope.tasks[i].taskExecutors = e_present;
        }
    }

    $scope.clicPrevPage = function () {
        if($scope.pageCounter>0){
            $scope.pageCounter -= 1;
            $scope.loadTasks();
        }
        return false;
    };

    $scope.clicNextPage = function () {
        if($scope.pageCounter<($scope.totalPage-1)){
            $scope.pageCounter += 1;
            $scope.loadTasks();
        }
        return false;
    };

    $scope.goToPage = function (page) {
        if(page>=0 && page<$scope.totalPage){
            $scope.pageCounter = page;
            $scope.loadTasks();
        }
        return false;
    };

    $scope.loadUsers();

    $scope.loadTasks();

});