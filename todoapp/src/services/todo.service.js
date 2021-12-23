import axios from 'axios';
import authHeader from './auth-header';

const API_URL = 'http://localhost:8000/api/';

class TodoService {

    getTodoGroups() {
        return axios.get(API_URL + 'todo-groups', { headers: authHeader() });
    }

    createTodoGroup(todoGroup) {
        return axios.post(API_URL + 'todo-group', todoGroup, { headers: authHeader() });
    }

    updateTodoGroup(todoGroup) {
        return axios.put(API_URL + 'todo-group', todoGroup, { headers: authHeader() });
    }

    deleteTodoGroup(todoGroupId) {
        return axios.delete(API_URL + 'todo-group/' + todoGroupId, { headers: authHeader() });
    }

    createTodo(todo) {
        return axios.post(API_URL + 'todo', todo, { headers: authHeader() });
    }

    updateTodo(todo) {
        return axios.put(API_URL + 'todo', todo, { headers: authHeader() });
    }

    deleteTodo(todoId) {
        return axios.delete(API_URL + 'todo/' + todoId, { headers: authHeader() });
    }

    getAllTodoList(todoGroupId) {
        const authObj = authHeader();
        axios.defaults.headers.common['Authorization'] = authObj.Authorization;
        return axios.get(API_URL + 'todos', { params: { todoGroupId } });
    }

    getActiveTodoList(todoGroupId) {
        const authObj = authHeader();
        axios.defaults.headers.common['Authorization'] = authObj.Authorization;
        return axios.get(API_URL + 'active-todos', { params: { todoGroupId } });
    }

    getCompletedTodoList(todoGroupId) {
        const authObj = authHeader();
        axios.defaults.headers.common['Authorization'] = authObj.Authorization;
        return axios.get(API_URL + 'completed-todos', { params: { todoGroupId } });
    }

    getTodoPriorities() {
        return axios.get(API_URL + 'priorities', { headers: authHeader() });
    }

    searchTodos(todoGroupId, priorityId, dueDate, isCompleted) {
        var req_params = {}
        if (todoGroupId != null) {
            req_params.todoGroupId = todoGroupId;
        }
        if (priorityId != null) {
            req_params.priorityId = priorityId;
        }

        if (dueDate != null) {
            req_params.dueDate = dueDate;
        }

        req_params.isCompleted = isCompleted;

        return axios.get(API_URL + "search", { params: req_params })
    }
}

export default new TodoService();