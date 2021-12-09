angular.module('app').controller('newUsersController', function ($scope, $http, $localStorage) {

    $scope.baseURL_usersAPI = baseURL_usersAPI;

    $scope.loadPositions = function(){
        $http({
            url: baseURL_positionsAPI,
            method: 'GET'
        }).then(function(response){
            $scope.positions= response.data;
        });
    };

    $scope.loadDivisions = function(){
        $http({
            url: baseURL_divisionsAPI,
            method: 'GET'
        }).then(function(response){
            $scope.divisions = response.data;
            let f = true;
            let d = 0;
            let prefix = "";
            $scope.divisions.forEach(function(division, i){
                    if(f){
                        d = division.depthLevel;
                        f = false;
                    }else if(d < division.depthLevel){
                        prefix+="---";
                    }else if(d > division.depthLevel){
                        prefix = prefix.substr(prefix.length-3);
                    }
                    d = division.depthLevel;
                    $scope.divisions[i].name = prefix + division.name;
                });
        });
    };

    $scope.createUser = function(){

        angular.element(document.querySelector("#message_block")).text("").removeClass("text-danger");
        angular.element(document.querySelector("#login")).removeClass("border-danger");
        angular.element(document.querySelector("#email")).removeClass("border-danger");
        angular.element(document.querySelector("#password")).removeClass("border-danger");
        angular.element(document.querySelector("#passwordCheck")).removeClass("border-danger");
        angular.element(document.querySelector("#shortname")).removeClass("border-danger");

        var elements = document.querySelectorAll('.errmessage');
        for(let elem of elements){
            elem.remove();
        }

        $http.post(baseURL_usersAPI, $scope.newuser ? $scope.newuser : '{}')
        .then(function successCallback(response){
            angular.element(document.querySelector("#message_block")).text("Пользователь сохранен").addClass("text-success");
        }, function errorCallback(response) {
                if(response.data){
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
                        angular.element(document.querySelector("#cart_message_block"))
                            .text("Ошибка: " + response.data)
                            .addClass("text-danger");
                    }
                }
           }
        );
    }

    $scope.loadPositions();
    $scope.loadDivisions();


});