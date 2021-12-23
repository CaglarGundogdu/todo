<template>
  <Nav title="Completed ToDos"/>
  
  <div class="main-div">
        <div class="title-w-button">
            <label class="page-title"> Completed ToDos </label>
            <button class="create-group-button" @click="toggleFilter"> 
                        <font-awesome-icon icon="filter" /> {{filterMessage}} Filter
                </button>
        </div>
        
        <TodoFilter @changedFilterView="changedFilterView" v-if="filterActive" :completedTodos="true" :todoGroupList="groupList"> </TodoFilter>
        
        <div class="todo-group-grid" v-if="!filterView">
            <div v-for="group in groupList || []" :key="group.id">
    
                <TodoGroup :todo-group="group" :todo-group-list="groupList" :active="false" > </TodoGroup>
            </div>
        </div>
        <label class="page-title filter-title" v-if="filterView"> Filtered ToDos </label>
        <div class="todo-group-grid" v-if="filterView">
            <div v-for="todo in todoList || []" :key="todo.id">
                <Todo :todo="todo" @update-todo-completed="completed" :editEnable="false" :disableEdit="false"> </Todo>
            </div>
        </div>
    </div>
</template>

<script>
import Nav from "./Nav.vue";
import TodoService from "../services/todo.service";
import TodoGroup from "./TodoGroup.vue";
import TodoFilter from "./TodoFilter.vue";
import Todo from "./Todo.vue";

export default {
    name: 'CompletedToDos',
    components: {
      Nav, TodoGroup, TodoFilter, Todo
    },
    data() {

        return {

            description: "",
            groupList: {},
            filterActive:false,
            filterView: false,
            filterMessage:"Open",
            todoList: {},
        };
    },
    methods: {
        toggleFilter() {
            this.filterActive = !this.filterActive;
            
            this.filterMessage = this.filterActive ? "Close":"Open";
        },
        changedFilterView(filter) {
            this.filterView = filter.filterView;
            this.todoList = filter.todoList;
        },
        completed(data) {
            data.todo.completed = data.completed;
            TodoService.updateTodo(data.todo).then(
                (response) => {

                    if (response.data.success) {
                        this.todoList.splice(this.todoList.indexOf(data.todo), 1);
                    }
                },
                (error) => {
                    console.log(
                        (error.response &&
                            error.response.data &&
                            error.response.data.message) ||
                        error.message ||
                        error.toString());
                }
            );
        },
    },
    mounted() {
        TodoService.getTodoGroups().then(
            (response) => {
                console.log(response.data.data);
                this.groupList = response.data.data;
            },
            (error) => {
                console.log(
                    (error.response &&
                        error.response.data &&
                        error.response.data.message) ||
                    error.message ||
                    error.toString());
            }
        );
        
    },
};

</script>

<style>

</style>
