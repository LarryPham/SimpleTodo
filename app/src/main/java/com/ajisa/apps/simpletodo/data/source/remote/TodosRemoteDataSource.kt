package com.ajisa.apps.simpletodo.data.source.remote

import android.os.Handler
import com.ajisa.apps.simpletodo.data.Todo
import com.ajisa.apps.simpletodo.data.source.TodosDataSource
import com.google.common.collect.Lists

/**
 * Implementation of the data source that adds a latency simulating network.
 */
class TodosRemoteDataSource private constructor(): TodosDataSource {

    override fun getTodos(callback: TodosDataSource.LoadTodosCallback) {
        val handler = Handler()
        handler.postDelayed({ callback.onTodosLoaded(Lists.newArrayList(TODOS_SERVICE_DATA.values))}, SERVICE_LATENCY_IN_MILLIS.toLong())
    }

    override fun getTodo(todoId: String, callback: TodosDataSource.GetTodoCallback) {
        val todo = TODOS_SERVICE_DATA[todoId]
        val handler = Handler()
        handler.postDelayed({ callback.onTodoLoaded(todo!!)}, SERVICE_LATENCY_IN_MILLIS.toLong())
    }

    override fun saveTodo(todo: Todo) {
        TODOS_SERVICE_DATA.put(todo.id, todo)
    }

    override fun completeTodo(todo: Todo) {
        val completedTodo = Todo(todo.title, todo.description, todo.id, true)
        TODOS_SERVICE_DATA.put(todo.id, completedTodo)
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

    companion object {
        private var INSTANCE: TodosRemoteDataSource? = null
        private val SERVICE_LATENCY_IN_MILLIS = 5000
        private val TODOS_SERVICE_DATA: MutableMap<String, Todo>

        init {
            TODOS_SERVICE_DATA = LinkedHashMap<String, Todo>(2)
            addTodo("Build tower in Pisa", "Ground looks good, no foundation work required.")
            addTodo("Finish bridge in Tacoma", "Found awesome girders at half the cost.")
        }

        private fun addTodo(title: String, description: String) {
            val newTodo = Todo(title, description)
            TODOS_SERVICE_DATA.put(newTodo.id, newTodo)
        }
    }
}