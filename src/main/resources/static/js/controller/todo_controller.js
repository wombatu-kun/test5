'use strict';

angular.module('myApp').controller('TodoController', ['$scope', 'TodoService', function($scope, TodoService) {
    var self = this;
    self.todo = {id:null, created:null, what:'', when:null};
    self.todos = [];
    self.now = null;

    self.submit = submit;
    self.edit = edit;
    self.remove = remove;
    self.reset = reset;


    fetchAllRecords();

    function fetchAllRecords(){
        TodoService.fetchAllRecords()
            .then(
            function(d) {
                var t = new Date();
                self.now = t.getFullYear()+'-'+('0'+(t.getMonth()+1)).slice(-2)+'-'+('0'+t.getDate()).slice(-2);
                self.todos = d;
            },
            function(errResponse){
                console.error('Error while fetching Records');
            }
        );
    }

    function createRecord(todo){
        TodoService.createRecord(todo)
            .then(
            fetchAllRecords,
            function(errResponse){
                console.error('Error while creating Record');
            }
        );
    }

    function updateRecord(todo, id){
        TodoService.updateRecord(todo, id)
            .then(
            fetchAllRecords,
            function(errResponse){
                console.error('Error while updating Record');
            }
        );
    }

    function deleteRecord(id){
        TodoService.deleteRecord(id)
            .then(
            fetchAllRecords,
            function(errResponse){
                console.error('Error while deleting Record');
            }
        );
    }

    function submit() {
        if(self.todo.id===null){
            console.log('Saving New Record', self.todo);
            createRecord(self.todo);
        }else{
            updateRecord(self.todo, self.todo.id);
            console.log('Record updated with id ', self.todo.id);
        }
        reset();
    }

    function edit(id){
        console.log('id to be edited', id);
        for(var i = 0; i < self.todos.length; i++){
            if(self.todos[i].id === id) {
                self.todo = angular.copy(self.todos[i]);
                if(self.todos[i].when!=null) {
                  self.todo.when = new Date(self.todos[i].when);
                }
                break;
            }
        }
    }

    function remove(id){
        console.log('id to be deleted', id);
        if(self.todo.id === id) {
            reset();
        }
        deleteRecord(id);
    }


    function reset(){
        self.todo={id:null, created:null, what:'', when:null};
        $scope.myForm.$setPristine();
    }

}]);
