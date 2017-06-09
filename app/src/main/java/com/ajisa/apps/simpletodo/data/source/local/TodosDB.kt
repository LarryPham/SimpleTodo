package com.ajisa.apps.simpletodo.data.source.local

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import android.util.Log

class TodosDB(context: Context): SQLiteOpenHelper(context, TodosDB.DATABASE_NAME, null, TodosDB.DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        Log.d(TAG, "Executing the sql query statement.")
        db!!.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // Not required now.
    }

    override fun onDowngrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        super.onDowngrade(db, oldVersion, newVersion)
    }

    companion object {
        val DATABASE_VERSION = 1
        val DATABASE_NAME = "Todos.db"

        private val TAG = TodosDB.javaClass.simpleName
        private val TEXT_TYPE = " TEXT"
        private val BOOLEAN_TYPE = " INTEGER"
        private val COMMA_SEPARATOR = ","
        private val OPEN_PARENTHESIS = "("
        private val CLOSE_PARENTHESIS= ")"

        private val SQL_CREATE_ENTRIES = "CREATE TABLE" + PersistenceContract.Entry.TABLE_NAME + OPEN_PARENTHESIS +
                BaseColumns._ID + TEXT_TYPE + " PRIMARY KEY" + COMMA_SEPARATOR +
                PersistenceContract.Entry.COLUMN_NAME_ENTRY_ID + TEXT_TYPE + COMMA_SEPARATOR +
                PersistenceContract.Entry.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEPARATOR +
                PersistenceContract.Entry.COLUMN_NAME_DESCRIPTION + TEXT_TYPE + COMMA_SEPARATOR +
                PersistenceContract.Entry.COLUMN_NAME_COMPLETED + BOOLEAN_TYPE + CLOSE_PARENTHESIS
    }
}
