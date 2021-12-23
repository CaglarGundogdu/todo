<template>
    <Nav title="Active ToDos" />
    <div class="main-div">
    
        <div class="title-w-button">
            <label class="page-title"> Active ToDos </label>
            <button class="create-group-button" @click="toggleFilter"> 
                        <font-awesome-icon icon="filter" /> {{filterMessage}} Filter
                </button>
        </div>
        
        <TodoFilter @changedFilterView="changedFilterView" v-if="filterActive" :completedTodos="false" :todoGroupList="groupList"> </TodoFilter>
        
        <div class="todo-group-grid" v-if="!filterView">
            <div v-for="group in groupList || []" :key="group.id">
    
                <TodoGroup :todo-group="group" :todo-group-list="groupList" :active="true"> </TodoGroup>
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
import Todo from "./Todo.vue";
import "vue3-date-time-picker/dist/main.css";
import TodoFilter from "./TodoFilter.vue";

export default {
    name: 'ActiveTodos',
    components: { Nav, TodoGroup, Todo ,TodoFilter },
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
.my-modal-content {
    display: flex;
    flex-direction: column;
}

.todo-group-grid {
    display: grid;
    grid-template-columns: repeat(3, minmax(240px, 1fr));
    align-items: baseline;
    column-gap: 10px;
    row-gap: 10px;
    padding-bottom:10px;
}

@media (max-width: 992px) {
    .todo-group-grid {
        grid-template-columns: repeat(1, minmax(240px, 1fr));
    }
}
</style>
