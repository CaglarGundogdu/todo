<template>
    <Nav title="Groups" />
    <div class="main-div">
        <div class="title-w-button">
            <label class="page-title"> Groups </label>
            <button class="create-group-button" @click="showTodoGroupCreateUpdateModel()"> 
                              <font-awesome-icon icon="plus" /> Create Group
                      </button>
            
        </div>
        <div class="todo-group-grid">
            <div v-for="group in groupList || []" :key="group.id">
    
                <TodoGroup :todo-group="group" :todo-group-list="groupList" @deleteTodoGroup="deleteTodoGroup" @updateTodoGroup="updateTodoGroup" :all="true" :deleteGroupEnable="true"> </TodoGroup>
            </div>
        </div>
        <Modal @close="showTodoGroupCreateUpdateModel" :modalActive="modalActive">
            <div class="create-todo-div">
                <label class="modal-label">Description</label>
                <input class="todo-input" v-model="todoGroup.description" type="text-area" @keyup.enter="createOrUpdateTodoGroup()" />
    
                <div style="text-align:center">
                    <button class="btn login-button btn-block" @click="createOrUpdateTodoGroup()">
                            <span>{{modalCreateOrUpdateButtonTitle}} Group</span>
                        </button>
                </div>
            </div>
        </Modal>
    </div>
</template>

<script>
import Nav from "./Nav.vue";
import TodoService from "../services/todo.service";
import TodoGroup from "./TodoGroup.vue";
import Modal from "./Modal.vue";

export default {
    name: 'Groups',
    components: { Nav, TodoGroup, Modal },
    data() {

        return {
            modalActive: false,
            groupList: {},
            todoGroup: { description: null },
            modalCreateOrUpdateButtonTitle: "Create",
        };
    },
    methods: {
        showTodoGroupCreateUpdateModel() {

            this.modalActive = !this.modalActive;

            if (this.todoGroup.id) {
                this.modalCreateOrUpdateButtonTitle = "Update";
            } else {
                this.modalCreateOrUpdateButtonTitle = "Create";
            }
            if (!this.modalActive) {
                this.todoGroup = { description: null };
            }
        },
        createOrUpdateTodoGroup() {
            if (this.todoGroup.id) {
                TodoService.updateTodoGroup(this.todoGroup).then(
                    (response) => {
                        const updatedTodoGroup = response.data.data;

                        if (response.data.success) {
                            this.modalActive = false;

                            for (var i = 0; i < this.groupList.length; i++) {
                                if (this.groupList[i].id === updatedTodoGroup.id) {
                                    this.groupList[i].description = updatedTodoGroup.description;
                                    break;
                                }
                            }

                            this.todoGroup = { description: null };
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
                TodoService.createTodoGroup(this.todoGroup).then(
                    (response) => {

                        if (response.data.success) {
                            this.groupList.push(response.data.data);
                            this.modalActive = false;
                            this.todoGroup = { description: null };
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

        deleteTodoGroup(todoGroup) {

            TodoService.deleteTodoGroup(todoGroup.id).then(
                (response) => {

                    if (response.data.success) {
                        this.groupList.splice(this.groupList.indexOf(todoGroup), 1);
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
        updateTodoGroup(todoGroup) {
            this.todoGroup = {
                id: todoGroup.id,
                description: todoGroup.description,
                user:todoGroup.user,
                createDate:todoGroup.createDate
            };
            
            this.showTodoGroupCreateUpdateModel();
        }
    },
    mounted() {
        TodoService.getTodoGroups().then(
            (response) => {
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
.create-group-button {
    background: #c3d9e9;
    border-radius: 8px;
    color: #466070;
    font-weight: 500;
    border: 0px;
    height: 44px;
    font-size: 14px;
}
</style>
