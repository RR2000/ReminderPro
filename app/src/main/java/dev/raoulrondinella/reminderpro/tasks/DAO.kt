package dev.raoulrondinella.reminderpro.tasks

import androidx.room.*

@Dao
interface TaskDao {
    @Query("SELECT * FROM tasks")
    fun getAllTasks(): List<Task>

    @Query("SELECT * FROM tasks WHERE id = :taskId")
    fun getTask(taskId: Int): Task

    @Insert
    fun insertTask(task: Task): Long

    @Update
    fun updateTask(task: Task)

    @Delete
    fun deleteTask(task: Task)
}

@Dao
interface SubtaskDao {
    @Query("SELECT * FROM subtasks WHERE taskId = :taskId")
    fun getSubtasksForTask(taskId: Int): List<Subtask>

    @Insert
    fun insertSubtask(subtask: Subtask): Long

    @Update
    fun updateSubtask(subtask: Subtask)

    @Delete
    fun deleteSubtask(subtask: Subtask)
}
