<template>
    <div class="todo" :class="borderClassName()">
        <div class="todo-header">
        <div class="todo-header-flex">
            <label class="checkbox">
    <input type="checkbox" v-on:change="toggleCheckbox(checked)" v-model="checked"/>
    <svg viewBox="0 0 21 18">
    <symbol :id="tickPath()" viewBox="0 0 21 18" xmlns="http://www.w3.org/2000/svg">
    <path d="M5.22003 7.26C5.72003 7.76 7.57 9.7 8.67 11.45C12.2 6.05 15.65 3.5 19.19 1.69" fill="none" stroke-width="2.25" stroke-linecap="round" stroke-linejoin="round" />
    </symbol>
    <defs>
    <mask :id="tick()">
    <use class="tick mask" :href="href()" />
    </mask>
    </defs>
    <use class="tick" :href="href()" stroke="currentColor" />
    <path fill="white" :mask="mask()" d="M18 9C18 10.4464 17.9036 11.8929 17.7589 13.1464C17.5179 15.6054 15.6054 17.5179 13.1625 17.7589C11.8929 17.9036 10.4464 18 9 18C7.55357 18 6.10714 17.9036 4.85357 17.7589C2.39464 17.5179 0.498214 15.6054 0.241071 13.1464C0.0964286 11.8929 0 10.4464 0 9C0 7.55357 0.0964286 6.10714 0.241071 4.8375C0.498214 2.39464 2.39464 0.482143 4.85357 0.241071C6.10714 0.0964286 7.55357 0 9 0C10.4464 0 11.8929 0.0964286 13.1625 0.241071C15.6054 0.482143 17.5179 2.39464 17.7589 4.8375C17.9036 6.10714 18 7.55357 18 9Z" />
    </svg>
    <svg class="lines" viewBox="0 0 11 11">
        <path d="M5.88086 5.89441L9.53504 4.26746" />
        <path d="M5.5274 8.78838L9.45391 9.55161" />
        <path d="M3.49371 4.22065L5.55387 0.79198" />
    </svg>
    </label>
            <label class="todo-title" :class="completedClass()">
            {{todo.title}}
            </label>

            </div>
            <div v-if="editEnable" class="todo-action-div">
                <button class="todo-action-button" @click="editClicked()" v-if="editEnable && !disableEdit"> 
                        <font-awesome-icon icon="edit" />
                </button>
                <button class="todo-action-button" @click="deleteClicked()" v-if="editEnable && !showDeleteLoading"> 
                        <font-awesome-icon icon="trash" />
                </button>
                <span
                  v-show="showDeleteLoading"
                  class="spinner-border spinner-border-sm"
                  style="color:#c3d9e9;height:24px;width:24px;"
                ></span>
            </div>
        </div>
        <div class="group-header">
    
            <label class="todo-desc">
            {{todo.description}}
            </label>
        </div>
        <div class="priority-dude-date">
            <div>
                <label class="todo-due-date">
            Priority:
        </label>
                <label :class="priorityColor()">
            {{todo.priority.description}}
        </label>
            </div>
    
            <label class="todo-due-date">
        {{dateTime(todo.dueDate)}}
        </label>
        </div>
    </div>
</template>

<script>
import moment from "moment";

export default {
    name: "Todo",
    components: {},
    props: {
        todo: {},
        editEnable:Boolean,
        disableEdit:Boolean
    },
    data() {
        return {
            checked: this.todo.completed,
            showDeleteLoading:false,
        };
    },
    methods: {
        dateTime(value) {
            return moment(value).format('DD/MM/YYYY HH:mm');
        },
        borderClassName() {
            return "priority-border" + this.todo.priority.id;
        },
        priorityColor() {
            return "priority-color" + this.todo.priority.id;
        },
        toggleCheckbox(val) {
            this.$emit("update-todo-completed", { todo: this.todo, completed: val });
        },
        mask() {
            return "url(#tick" + this.todo.id + ")";
        },
        tick() {
            return "tick" + this.todo.id;
        },
        tickPath() {
            return "tick-path" + this.todo.id;
        },
        href() {
            return "#tick-path" + this.todo.id;
        },
        completedClass() {
            return this.todo.completed ? "todo-title-completed" : "todo-title-active";
        },
        editClicked() {
            this.$emit("edit", this.todo);
        },
        deleteClicked() {
            this.showDeleteLoading = true;
            this.$emit("delete", this.todo);
        }
    }

}
</script>

<style>
.todo {
    display: flex;
    flex-direction: column;
    background: #fcfeff;
    padding: 10px;
    text-align: left;
    border-radius: 4px;
    box-shadow: 0px 1px 4px 0px #39526244;
}

.todo-title {
    font-weight: 500;
    font-size: 14px;
    margin-bottom: 4px;
}

.todo-desc {
    font-weight: 400;
    font-size: 14px;
    margin-bottom: 0;
}

.add-todo-button {
    background: #82b1d169;
    border: none;
    height: 40px;
    width: 40px;
    border-radius: 8px;
    color: #466070;
    font-size: 22px;
}

.group-header {
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    align-items: center;
}

.todo-header {
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    align-items: center;
    gap: 10px;
}

.todo-due-date {
    font-size: 13px;
    color: #4f6674;
    font-weight: 300;
    padding-right: 4px;
}

.todo-action-button {
    background: transparent;
    border: none;
    color: #8298a9;
    height:24px;
    width:24px;
}

.todo-header-flex {
    display: flex;
    flex-direction: row;
    gap: 10px;
    align-items: center;
}

.todo-action-div {
    display: flex;
    flex-direction: row;
    gap: 10px;
}

.priority-dude-date {
    display: flex;
    justify-content: space-between;
    height: 32px;
    line-height: 32px;
}

.priority-border1 {
    border-left: 3px solid #dd0008;
}

.priority-border2 {
    border-left: 3px solid rgb(50 73 89);
}

.priority-border3 {
    border-left: 3px solid #BBC7CA;
}

.priority-color1 {
    color: #dd0008;
    font-weight: 400;
    font-size: 14px;
}

.priority-color2 {
    color: rgb(50 73 89);
    ;
    font-weight: 400;
    font-size: 14px;
}

.priority-color3 {
    color: #BBC7CA;
    font-weight: 400;
    font-size: 14px;
}
.todo-title-completed {
    text-decoration: line-through;
} 
.todo-title-active {
    text-decoration: none;
}

.checkbox {
    --border-default: #bbbbc1;
    --border-hover: #9898a3;
    --active: #19B34E;
    --active-tick: #ffffff;
    display: block;
    width: 18px;
    height: 18px;
    cursor: pointer;
    position: relative;
    -webkit-tap-highlight-color: transparent;
    margin-top:4px;
}

.checkbox svg {
    display: block;
    position: absolute;
}

.checkbox input {
    display: block;
    outline: none;
    border: none;
    padding: 0;
    margin: 0;
    -webkit-appearance: none;
    width: 18px;
    height: 18px;
    border-radius: 36%/36%;
    box-shadow: inset 0 0 0 1.5px var(--border, var(--border-default));
    background: var(--background, transparent);
    transition: background 0.25s linear, box-shadow 0.25s linear;
}

.checkbox input+svg {
    width: 21px;
    height: 18px;
    left: 0;
    top: 0;
    color: var(--active);
}

.checkbox input+svg .tick {
    stroke-dasharray: 20;
    stroke-dashoffset: var(--stroke-dashoffset, 20);
    transition: stroke-dashoffset 0.2s;
}

.checkbox input+svg .tick.mask {
    stroke: var(--active-tick);
}

.checkbox input+svg+svg {
    width: 11px;
    height: 11px;
    fill: none;
    stroke: var(--active);
    stroke-width: 1.25;
    stroke-linecap: round;
    top: -6px;
    right: -10px;
    stroke-dasharray: 4.5px;
    stroke-dashoffset: 13.5px;
    pointer-events: none;
    -webkit-animation: var(--animation, none) 0.2s ease 0.175s;
    animation: var(--animation, none) 0.2s ease 0.175s;
}

.checkbox input:checked {
    --background: var(--active);
    --border: var(--active);
}

.checkbox input:checked+svg {
    --stroke-dashoffset: 0;
}

.checkbox input:checked+svg+svg {
    --animation: check;
}

.checkbox:hover input:not(:checked) {
    --border: var(--border-hover);
}

@-webkit-keyframes check {
    100% {
        stroke-dashoffset: 4.5px;
    }
}

@keyframes check {
    100% {
        stroke-dashoffset: 4.5px;
    }
}
</style>