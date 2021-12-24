<template>
    <div class="todo-group">
        <div class="group-header">
    
            <label class="group-title">
                        {{todoGroup.description}}
                        </label>
            <div class="group-action-buttons-div">
                <button class="add-todo-button" @click="showTodoCreateUpdateModel()" v-if="active"> 
                                    <font-awesome-icon icon="plus" />
                            </button>
                <button class="edit-button-w-title" @click="toggleEdit()"> 
                                    <font-awesome-icon icon="edit" /> {{editTodosButtonTitle}} Todos
                    </button>
                <button v-if="deleteGroupEnable" class="delete-group-button" @click="deleteGroupClicked()" :disabled="showDeleteLoading">
                        <font-awesome-icon icon="trash"/> Delete Group
                        
                </button>
                <button v-if="deleteGroupEnable" class="delete-group-button" @click="updateGroupClicked()" :disabled="showDeleteLoading">
                        <font-awesome-icon icon="edit"/> Update Group
                        
                </button>
                <span
                v-show="showDeleteLoading"
                class="spinner-border spinner-border-sm"
                style="color:#c3d9e9;height:24px;width:24px;"
                ></span>
            </div>
        </div>
        <div v-for="todo in todoList || []" :key="todo.id">
    
            <Todo :todo="todo" @update-todo-completed="completed" @edit="editTodo" @delete="deleteTodo" :editEnable="editEnable" :disableEdit="!active"> </Todo>
        </div>
        <div v-if="!todoList || todoList.length == 0" style="text-align:center;height:40px;">
                <label class="no-records"> No Todos Found.</label>
        </div>
        <Modal @close="showTodoCreateUpdateModel" :modalActive="modalActive">
            <div class="create-todo-div">
                <label class="modal-label">Title</label>
                <input class="todo-input" v-model="todo.title" @keyup.enter="createOrUpdateTodo()"/>
                <label class="modal-label">Description</label>
                <textarea class="todo-input" v-model="todo.description" />
                <label class="modal-label">Group</label>
                <select v-model="todo.group" :disabled="true">
                            <option v-for="tg in todoGroupList || []" :value="tg" :key="tg.id">
                            {{ tg.description }}
                            </option>
                        </select>
                <label class="modal-label">Priority</label>
                <select v-model="todo.priority">
                            <option v-for="priority in priorities || []" :value="priority" :key="priority.id">
                            {{ priority.description }}
                            </option>
                        </select>
                <label class="modal-label">Due Date</label>
                <Datepicker v-model="todo.dueDate" />
                <div style="text-align:center">
                    <button class="btn login-button btn-block" @click="createOrUpdateTodo()">
                                        <span>{{modalCreateOrUpdateButtonTitle}} Todo</span>
                                    </button>
                </div>
            </div>
        </Modal>
    </div>
</template>

<script>
import Todo from "./Todo.vue";
import TodoService from "../services/todo.service";
import Modal from "./Modal.vue";
import Datepicker from "vue3-date-time-picker";
import "vue3-date-time-picker/dist/main.css";

export default {
    name: "TodoGroup",
    components: { Todo, Modal, Datepicker,},
    props: {
        todoGroup: {
            type: Object,
            default: () => ({})
        },
        todoGroupList: {},
        active: Boolean,
        all: Boolean,
        deleteGroupEnable: Boolean,
    },
    data() {

        return {
            modalActive: false,
            todo: { title: null, description: null, priority: null, group: this.todoGroup, dueDate: new Date() },
            todoList: [],
            priorities: [],
            editEnable: false,
            modalCreateOrUpdateButtonTitle: "Create",
            showDeleteLoading: false,
            editTodosButtonTitle:"Enable Editing",
        };
    },
    methods: {
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
        toggleEdit() {
            this.editEnable = !this.editEnable;
            
            this.editTodosButtonTitle =this.editEnable ? "Disable Editing" : "Enable Editing"; 
        },
        initTodo() {
             this.todo = {title:null, description: null, priority: "", group: this.todoGroup, dueDate: new Date() };
        },
        editTodo(todo) {
            this.todo = {
                id: todo.id,
                title: todo.title,
                description: todo.description,
                priority: {
                    id: todo.priority.id,
                    description: todo.priority.description,
                    level: todo.priority.level
                },
                group: this.todoGroup,
                dueDate: todo.dueDate
            };
            this.showTodoCreateUpdateModel();
        },
        deleteTodo(todo) {
            TodoService.deleteTodo(todo.id).then(
                (response) => {

                    if (response.data.success) {
                        this.todoList.splice(this.todoList.indexOf(todo), 1);
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
        showTodoCreateUpdateModel() {

            this.modalActive = !this.modalActive;

            if (this.todo.id) {
                this.modalCreateOrUpdateButtonTitle = "Update";
            } else {
                this.modalCreateOrUpdateButtonTitle = "Create";
            }

            if (this.modalActive) {
                TodoService.getTodoPriorities().then(
                    (response) => {
                        if (response.data.success) {
                            this.priorities = response.data.data;

                            if (!this.todo.priority) {
                                this.todo.priority = this.priorities[0];
                            }
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
            } else {
                this.initTodo();
            }

        },
        createOrUpdateTodo() {
            if (this.todo.id) {
                TodoService.updateTodo(this.todo).then(
                    (response) => {
                        const updatedTodo = response.data.data;

                        if (response.data.success) {
                            this.modalActive = false;

                            for (var i = 0; i < this.todoList.length; i++) {
                                if (this.todoList[i].id === updatedTodo.id) {
                                    this.todoList[i] = updatedTodo;
                                    break;
                                }
                            }

                            this.initTodo();
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
            } else {
                TodoService.createTodo(this.todo).then(
                    (response) => {
                        console.log(response.data.data);
                        if (response.data.success) {
                            this.todoList.push(response.data.data);
                            this.modalActive = false;
                            this.initTodo();
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
            }

        },
        deleteGroupClicked() {
            this.showDeleteLoading = true;
            this.$emit("deleteTodoGroup", this.todoGroup);
        },
        updateGroupClicked() {
            this.$emit("updateTodoGroup", this.todoGroup);
        }
    },
    mounted() {
        if (this.all) {

            TodoService.getAllTodoList(this.todoGroup.id).then(
                (response) => {
                    this.todoList = response.data.data;
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
        } else if (this.active) {
            TodoService.getActiveTodoList(this.todoGroup.id).then(
                (response) => {
                    this.todoList = response.data.data;
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
        } else {
            TodoService.getCompletedTodoList(this.todoGroup.id).then(
                (response) => {
                    this.todoList = response.data.data;
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
        }


    },
}
</script>

<style>
.todo-group {
    min-width: 320px;
    display: flex;
    flex-direction: column;
    background: #e2edf57a;
    border: 1px solid #b5c7d37a;
    min-height: 140px;
    padding: 12px;
    text-align: left;
    border-radius: 8px;
    gap: 10px;
}

.group-title {
    font-weight: 500;
    margin-bottom: 0;
}

.add-todo-button {
    background: #82b1d169;
    border: none;
    height: 40px;
    width: 40px;
    border-radius: 8px;
    color: #466070;
    font-size: 18px;
}

.group-header {
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    align-items: center;
}

.create-todo-div {
    display: flex;
    flex-direction: column;
}

.modal-label {
    color: #828d99;
    width: 100%;
    text-align: left;
    margin-bottom: -2px;
    font-size: 14px;
    font-family: 'Prompt', sans-serif;
    font-weight: 500;
}

.todo-input {
    border: 1px solid #7a80864a;
    height: 40px;
    border-radius: 4px;
    max-height:100px;
    color: #3f5a6b;
    font-size:14px;
}

.edit-button-w-title {
    background: #c3d9e9;
    border-radius: 8px;
    color: #466070;
    font-size:12px;
    font-weight: 400;
    border: 0px;
}

.edit-button-w-title:hover{
    background: #acc1d1;
}

.group-action-buttons-div {
    display: flex;
    flex-direction: row;
    gap: 10px;
    min-height: 40px;
}

.delete-group-button {
    background: #c3d9e9;
    border-radius: 8px;
    color: #466070;
    font-size:12px;
    font-weight: 400;
    border: 0px;
}
.delete-group-button:hover{
    background: #acc1d1;
}

.no-records {
    font-weight: 400;
    font-size: 14px;
    color: #a0b1bf;
    padding-top: 20px;
}

.dp__input {
    font-family: 'Prompt' !important;
    color: #3f5a6b !important;
    font-size: 14px;
}

.dp__calendar {
    position: relative;
    font-family: 'Prompt';
    font-weight: 400;
}

.dp__action_buttons {
    font-weight: 500;
    font-family: 'Prompt';
    font-size: 14px;
}

.dp__select {
    color: #20303d;
}

.db__calendar_header {
    font-weight: 600;
    font-family: 'Prompt';
    color: #1a2b36;
}

.dp__clear_icon {
    display: none;
}
</style>