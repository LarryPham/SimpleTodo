package com.ajisa.apps.simpletodo.data.source

import com.ajisa.apps.simpletodo.data.Todo
import com.ajisa.apps.simpletodo.data.source.local.TodosLocalDataSource
import com.ajisa.apps.simpletodo.data.source.remote.TodosRemoteDataSource

class TodosRepository private constructor(todosRemoteDataSource: TodosRemoteDataSource, todosLocalDataSource: TodosLocalDataSource): TodosDataSource {
    override fun getTodos(callback: TodosDataSource.LoadTodosCallback) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getTodo(todoId: String, callback: TodosDataSource.GetTodoCallback) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun saveTodo(todo: Todo) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun completeTodo(todo: Todo) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun completeTodo(todoId: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun activateTodo(todo: Todo) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun activateTodo(todoId: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun clearCompletedTodos() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun refreshTodos() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun removeAllTodos() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun removeTodo(todoId: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}