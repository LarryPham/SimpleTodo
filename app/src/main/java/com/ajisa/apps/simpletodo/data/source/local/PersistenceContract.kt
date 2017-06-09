package com.ajisa.apps.simpletodo.data.source.local

import android.provider.BaseColumns

object PersistenceContract {
    abstract class Entry: BaseColumns {
        companion object {
            val TABLE_NAME = "Todo"
            val COLUMN_NAME_ENTRY_ID = "ID"
            val COLUMN_NAME_TITLE = "Title"

            val COLUMN_NAME_DESCRIPTION = "Description"
            val COLUMN_NAME_COMPLETED = "Completed"
        }
    }
}