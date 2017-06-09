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
        // Not required for the remote data source.
    }

    override fun activateTodo(todo: Todo) {
        val activeTodo = Todo(todo.title, todo.description, todo.id, false)
        TODOS_SERVICE_DATA.put(activeTodo.id, activeTodo)
    }

    override fun activateTodo(todoId: String) {
        // Not required for the remote data source.
    }

    override fun clearCompletedTodos() {
        val it = TODOS_SERVICE_DATA.entries.iterator()
        while (it.hasNext()) {
            val entry = it.next()
            if (entry.value.isCompleted) {
                it.remove()
            }
        }
    }

    override fun refreshTodos() {
        // Not required at now.
    }

    override fun removeAllTodos() {
        TODOS_SERVICE_DATA.clear()
    }

    override fun removeTodo(todoId: String) {
        TODOS_SERVICE_DATA.remove(todoId)
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

        val instance: TodosRemoteDataSource
            get() {
                if (INSTANCE == null) {
                    INSTANCE = TodosRemoteDataSource()
                }
                return INSTANCE as TodosRemoteDataSource
            }

        private fun addTodo(title: String, description: String) {
            val newTodo = Todo(title, description)
            TODOS_SERVICE_DATA.put(newTodo.id, newTodo)
        }
    }
}