package com.ajisa.apps.simpletodo.data.source

import com.ajisa.apps.simpletodo.data.Todo

interface TodosDataSource {

    interface LoadTodosCallback {
        fun onTodosLoaded(todos: List<Todo>)
        fun onDataNotAvailable()
    }

    interface GetTodoCallback {
        fun onTodoLoaded(todo: Todo)
        fun onDataNotAvailable()
    }

    fun getTodos(callback: LoadTodosCallback)
    fun getTodo(todoId: String, callback: GetTodoCallback)

    fun saveTodo(todo: Todo)

    fun completeTodo(todo: Todo)
    fun completeTodo(todoId: String)

    fun activateTodo(todo: Todo)
    fun activateTodo(todoId: String)

    fun clearCompletedTodos()
    fun refreshTodos()

    fun removeAllTodos()
    fun removeTodo(todoId: String)
}