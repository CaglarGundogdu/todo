<template>
    <div class="filter-container">
            <div class="filter-field-group">
                <label class="modal-label">Group</label>
                <select v-model="selectedFilterGroupId" style="width: 100%;font-size: 14px;">
                    <option :value="null">Select Group</option>
                        <option v-for="tg in todoGroupList || []" :value="tg.id" :key="tg.id">
                        {{ tg.description }}
                        </option>
                    </select>
            </div>
            <div class="filter-field-group">
                <label class="modal-label">Priority</label>
            <select v-model="selectedFilterPriorityId" style="width: 100%;font-size: 14px;">
                    <option :value="null">Select Priority</option>
                    <option v-for="priority in priorities || []" :value="priority.id" :key="priority.id">
                    {{ priority.description }}
                    </option>
                </select>
            </div>
            <div class="filter-field-group">
                <label class="modal-label">Due Date</label>
                <Datepicker v-model="selectedFilterDueDate" />
            </div>
            <div class="filter-button-group">
                <button class="filter-search-button" @click="searchTodos"><font-awesome-icon icon="search" /> Search </button>
                <button class="filter-clear-button" @click="clearFilter"> Clear </button>
            </div>
        </div>
</template>

<script>
import TodoService from "../services/todo.service";

import Datepicker from '@vuepic/vue-datepicker';
import '@vuepic/vue-datepicker/dist/main.css';

export default {
    name:"TodoFilter",
    components:{ Datepicker},
    props: {
        todoGroupList: {},
        completedTodos:Boolean,
    },
    setup() {
        
    },
    data() {

        return {
            todoList: {},
            priorities: {},
            selectedFilterGroupId: null,
            selectedFilterPriorityId: null,
            selectedFilterDueDate: null,
        };
    },
    methods:{
        clearFilter() {
            this.changedFilterView(false);
            this.selectedFilterGroupId = null;
            this.selectedFilterPriorityId = null;
            this.selectedFilterDueDate = null;
        },
        searchTodos() {
            TodoService.searchTodos(this.selectedFilterGroupId, this.selectedFilterPriorityId, this.selectedFilterDueDate, this.completedTodos).then(
                (response) => {
                    this.todoList = response.data.data;
                    this.changedFilterView(true);
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
        changedFilterView(filterView) {
            this.filterView = filterView;
            this.$emit("changedFilterView", {filterView:this.filterView, todoList:this.todoList});
        },
    },
    mounted() {
       
        TodoService.getTodoPriorities().then(
            (response) => {
                if (response.data.success) {
                    this.priorities = response.data.data;
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
}
</script>

<style>
.filter-title{
  padding-top:20px;
}

.filter-field-group {
  min-width: 200px;  
}

.filter-container {
  display:flex;
  justify-content: center;
  gap:12px;
  background:#eff5fa8c;
  padding:20px;
  border-radius: 8px;
  margin-bottom: 12px;
}

.filter-button-group {
  display: flex;
    justify-content: center;
    align-items: center;
    gap:12px;
}

.filter-search-button {
  background: #c3d9e9;
    border-radius: 38px;
    color: #466070;
    font-weight: 500;
    border: 0px;
    height: 44px;
    width: 120px;
    box-shadow: 0px 1px 2px 0px #3249584d;
}

.filter-clear-button {
  background: #eff5fa;
    border-radius: 38px;
    color: #8696a3;
    font-weight: 500;
    border: 0px;
    height: 44px;
    width: 120px;
    box-shadow: 0px 1px 2px 0px #32495817;
}

@media (max-width: 992px) {
  .filter-container {
    display:flex;
    flex-direction: column;
    justify-content: flex-start;
    gap:12px;
  }
}
</style>