package com.ajisa.apps.simpletodo.data

import com.google.common.base.Objects
import com.google.common.base.Strings
import java.util.*

/**
 * Immutable model class for a Task.
 */
class Todo
/**
 * Use this constructor to specify a completed Task if the Task already has an id(copy of another Task).
 *
 * @param title title of the task.
 * @param description description of the task.
 * @param id  id of the task.
 * @param completed true if the task is completed, false if it's active.
 */
@JvmOverloads constructor(val title: String, val description: String, val id: String = UUID.randomUUID().toString(), val isCompleted: Boolean = false) {

    /**
     * Use this constructor to create a new completed Task.
     * @param title title of the task.
     * @param description description of the task.
     * @param completed true if the task is completed, false if it's active.
     */
    constructor(title: String, description: String, completed: Boolean): this (title, description, UUID.randomUUID().toString(), completed) { }

    val titleForList: String?
        get() {
            if (!Strings.isNullOrEmpty(title)) {
                return title
            } else {
                return description
            }
        }

    val isActive: Boolean
        get() = !isCompleted

    val isEmpty: Boolean
        get() = Strings.isNullOrEmpty(title) && Strings.isNullOrEmpty(description)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false

        val todo = other as Todo?
        return Objects.equal(id, todo!!.id) && Objects.equal(title, todo.title) && Objects.equal(description, todo.description)
    }

    override fun hashCode(): Int = Objects.hashCode(id, title, description)
    override fun toString(): String = "Task with title: $title!!"
}