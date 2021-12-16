angular.module('app').controller('editTaskController', function ($scope, $http, $localStorage) {

    $scope.baseURL_tasksAPI = baseURL_tasksAPI;

    $scope.users = [];

    var nowDate = new Date();


    $scope.task = {
        taskExecutors:[],
        //taskFiles:[],
        id:0,
        shortDescribe:"",
        fullDescribe:"",
        initionDate: nowDate.getFullYear() + '-' + nowDate.getMonth() + '-' + nowDate.getDate(),
        initionTime: nowDate.getHours() + ':' + nowDate.getMinutes(),
        repeatable: false,
        repeatPeriod: '',
        durationOfExecute: 'PT1h',
        authorId: 0,
        common: false,
        active: false
        }

    $scope.loadUsers = function(){
        $http({
            url: baseURL_usersAPI + '/userlist',
            method: 'GET'
        }).then(function(response){
console.log(response.data);
            $scope.users = response.data.content;
        });
    };


    $scope.addNewExecutor = function() {
        $scope.task.taskExecutors.push('');
    };
    $scope.removeExecutor = function(z) {
        $scope.task.taskExecutors.splice(z, 1);
    };

    $scope.addNewFile = function() {
        $scope.task.taskFiles.push('');
    };
    $scope.removeFile = function(z) {
        $scope.task.taskFiles.splice(z, 1);
    };

    $scope.createTask = function(){

        angular.element(document.querySelector("#message_block")).text("").removeClass("text-danger");
        angular.element(document.querySelector("#shortDescribe")).removeClass("border-danger");
        angular.element(document.querySelector("#fullDescribe")).removeClass("border-danger");
        angular.element(document.querySelector("#initionDate")).removeClass("border-danger");
        angular.element(document.querySelector("#initionTime")).removeClass("border-danger");
        angular.element(document.querySelector("#repeatPeriod")).removeClass("border-danger");
        angular.element(document.querySelector("#durationOfExecute")).removeClass("border-danger");
        angular.element(document.querySelector("#authorId")).removeClass("border-danger");


        var elements = document.querySelectorAll('.errmessage');
        for(let elem of elements){
            elem.remove();
        }

console.log($scope.task);
        $http.post(baseURL_tasksAPI + "/whole/", $scope.task ? $scope.task : '{}')
        .then(function successCallback(response){
            angular.element(document.querySelector("#message_block")).text("Задача сохранена").addClass("text-success");
        }, function errorCallback(response) {
                if(response.data){
console.log(response);
                    if(Array.isArray(response.data)){
                        var fieldsArr = response.data;
                        var message_block = document.querySelector("#message_block");
                        angular.element(message_block)
                            .html("<p>Заполните обязательные поля</p>")
                            .addClass("text-danger");
                        fieldsArr.forEach(function(errField, i){
                            if(errField.fieldName=="globalerror"){
                                message_block.innerHTML = message_block.innerHTML + "<p>" + errField.message + "</p>";
                            } else {
                                let curField = document.querySelector("#" + errField.fieldName);
                                angular.element(curField)
                                        .addClass("border-danger");
                                curField.insertAdjacentHTML("beforebegin", '<span class = "errmessage text-danger">' + errField.message + '</span>');
                            }
                        });
                    } else {
                        angular.element(document.querySelector("#message_block"))
                            .text("Ошибка: " + response.data)
                            .addClass("text-danger");
                    }
                }
           }
        );
    }

    $scope.loadUsers();


});