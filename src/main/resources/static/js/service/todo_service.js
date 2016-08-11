'use strict';

angular.module('myApp').factory('TodoService', ['$http', '$q', function($http, $q){

    var REST_SERVICE_URI = 'http://localhost:8080/rest/';

    var factory = {
        fetchAllRecords: fetchAllRecords,
        createRecord: createRecord,
        updateRecord: updateRecord,
        deleteRecord: deleteRecord
    };

    return factory;

    function fetchAllRecords() {
        var deferred = $q.defer();
        $http.get(REST_SERVICE_URI)
            .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function(errResponse){
                console.error('Error while fetching Records');
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    }

    function createRecord(todo) {
        var deferred = $q.defer();
        $http.post(REST_SERVICE_URI, todo)
            .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function(errResponse){
                console.error('Error while creating Record');
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    }


    function updateRecord(todo, id) {
        var deferred = $q.defer();
        $http.put(REST_SERVICE_URI+id, todo)
            .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function(errResponse){
                console.error('Error while updating Record');
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    }

    function deleteRecord(id) {
        var deferred = $q.defer();
        $http.delete(REST_SERVICE_URI+id)
            .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function(errResponse){
                console.error('Error while deleting Record');
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    }

}]);
