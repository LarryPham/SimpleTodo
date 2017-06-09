package com.ajisa.apps.simpletodo.data.source.local

import android.content.ContentValues
import android.content.Context
import com.ajisa.apps.simpletodo.data.Todo
import com.ajisa.apps.simpletodo.data.source.TodosDataSource
import com.google.common.base.Preconditions

/**
 * Concrete implementation of a data source as a db.
 */
class TodosLocalDataSource
private constructor(context: Context): TodosDataSource {
    private val todosDB: TodosDB

    init {
        Preconditions.checkNotNull(context)
        todosDB = TodosDB(context)
    }

    override fun getTodos(callback: TodosDataSource.LoadTodosCallback) {
        val todos = ArrayList<Todo>()
        val db = todosDB.writableDatabase

        val projection = arrayOf(PersistenceContract.Entry.COLUMN_NAME_ENTRY_ID, PersistenceContract.Entry.COLUMN_NAME_TITLE,
                PersistenceContract.Entry.COLUMN_NAME_DESCRIPTION, PersistenceContract.Entry.COLUMN_NAME_COMPLETED)
        val cursor = db.query(PersistenceContract.Entry.TABLE_NAME, projection, null, null, null, null, null)
        if (cursor != null && cursor.count > 0) {
            while (cursor.moveToNext()) {
                val itemId = cursor.getString(cursor.getColumnIndexOrThrow(PersistenceContract.Entry.COLUMN_NAME_ENTRY_ID))
                val title = cursor.getString(cursor.getColumnIndex(PersistenceContract.Entry.COLUMN_NAME_TITLE))
                val description = cursor.getString(cursor.getColumnIndex(PersistenceContract.Entry.COLUMN_NAME_DESCRIPTION))
                val completed = cursor.getInt(cursor.getColumnIndex(PersistenceContract.Entry.COLUMN_NAME_COMPLETED)) == 1

                val todo = Todo(title, description, itemId, completed)
                todos.add(todo)
            }
        }
        cursor?.close()
        db.close()

        if (todos.isEmpty()) {
            callback.onDataNotAvailable()
        } else {
            callback.onTodosLoaded(todos)
        }
    }

    override fun getTodo(todoId: String, callback: TodosDataSource.GetTodoCallback) {
        val db = todosDB.readableDatabase
        val projection = arrayOf(PersistenceContract.Entry.COLUMN_NAME_ENTRY_ID, PersistenceContract.Entry.COLUMN_NAME_TITLE,
                PersistenceContract.Entry.COLUMN_NAME_DESCRIPTION, PersistenceContract.Entry.COLUMN_NAME_COMPLETED)
        val selection = PersistenceContract.Entry.COLUMN_NAME_ENTRY_ID + " LIKE ?"
        val selectionArgs = arrayOf(todoId)

        val cursor = db.query(PersistenceContract.Entry.TABLE_NAME, projection, selection, selectionArgs, null, null, null)
        var todo: Todo? = null

        if (cursor != null && cursor.count > 0) {
            cursor.moveToFirst()

            val itemId = cursor.getString(cursor.getColumnIndexOrThrow(PersistenceContract.Entry.COLUMN_NAME_ENTRY_ID))
            val title = cursor.getString(cursor.getColumnIndex(PersistenceContract.Entry.COLUMN_NAME_TITLE))
            val description = cursor.getString(cursor.getColumnIndex(PersistenceContract.Entry.COLUMN_NAME_DESCRIPTION))
            val completed = cursor.getInt(cursor.getColumnIndex(PersistenceContract.Entry.COLUMN_NAME_COMPLETED)) == 1
            todo = Todo(title, description, itemId, completed)
        }
        cursor?.close()
        db.close()

        if (todo != null) {
            callback.onDataNotAvailable()
        } else {
            callback.onTodoLoaded(todo!!)
        }
    }

    override fun saveTodo(todo: Todo) {
        Preconditions.checkNotNull(todo)
        val db = todosDB.writableDatabase

        val values = ContentValues()
        values.put(PersistenceContract.Entry.COLUMN_NAME_ENTRY_ID, todo.id)
        values.put(PersistenceContract.Entry.COLUMN_NAME_TITLE, todo.title)
        values.put(PersistenceContract.Entry.COLUMN_NAME_DESCRIPTION, todo.description)

        db.insert(PersistenceContract.Entry.TABLE_NAME, null, values)
        db.close()
    }

    override fun completeTodo(todo: Todo) {
        val db = todosDB.writableDatabase

        val values = ContentValues()
        values.put(PersistenceContract.Entry.COLUMN_NAME_COMPLETED, true)

        val selection = PersistenceContract.Entry.COLUMN_NAME_ENTRY_ID + " LIKE ?"
        val selectionArgs = arrayOf(todo.id)

        db.update(PersistenceContract.Entry.TABLE_NAME, values, selection, selectionArgs)
        db.close()
    }

    override fun completeTodo(todoId: String) {
        // Not required for the local data source because the TodosRepository handles.
    }

    override fun activateTodo(todo: Todo) {
        val db = todosDB.writableDatabase
        val values = ContentValues()
        values.put(PersistenceContract.Entry.COLUMN_NAME_COMPLETED, false)

        val selection = PersistenceContract.Entry.COLUMN_NAME_ENTRY_ID + " LIKE ?"
        val selectionArgs = arrayOf(todo.id)

        db.update(PersistenceContract.Entry.TABLE_NAME, values, selection, selectionArgs)
        db.close()
    }

    override fun activateTodo(todoId: String) {
        // Not required now.
    }

    override fun clearCompletedTodos() {
        val db = todosDB.writableDatabase
        val selection = PersistenceContract.Entry.COLUMN_NAME_COMPLETED + " LIKE ?"
        val selectionArgs= arrayOf("1")

        db.delete(PersistenceContract.Entry.TABLE_NAME, selection, selectionArgs)
        db.close()
    }

    override fun refreshTodos() {
        // Not required now.
    }

    override fun removeAllTodos() {
        val db = todosDB.writableDatabase
        db.delete(PersistenceContract.Entry.TABLE_NAME, null, null)
        db.close()
    }

    override fun removeTodo(todoId: String) {
        val db = todosDB.writableDatabase
        val selection = PersistenceContract.Entry.COLUMN_NAME_ENTRY_ID + " LIKE ?"
        val selectionArgs = arrayOf(todoId)

        db.delete(PersistenceContract.Entry.TABLE_NAME, selection, selectionArgs)
        db.close()
    }

    companion object {
        private var INSTANCE: TodosLocalDataSource? = null

        fun getInstance(context: Context): TodosLocalDataSource {
            if (INSTANCE == null) {
                INSTANCE = TodosLocalDataSource(context)
            }
            return INSTANCE as TodosLocalDataSource
        }
    }
}