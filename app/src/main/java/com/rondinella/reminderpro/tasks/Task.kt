package com.rondinella.reminderpro.tasks

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import java.time.LocalDate
import java.time.LocalTime

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val title: String,

    val description: String,

    @TypeConverters(DateConverter::class)
    val dueDate: LocalDate? = null,

    @TypeConverters(TimeConverter::class)
    val dueTime: LocalTime? = null,

    val weekNumber: Int? = null,

    val startTimeSlot: Int? = null,

    val durationSlots: Int? = null,

    val isComplete: Boolean = false
)

// Converters for LocalDate and LocalTime since Room does not natively support these types.
class DateConverter {
    @TypeConverter
    fun fromTimestamp(value: Long?): LocalDate? {
        return value?.let { LocalDate.ofEpochDay(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: LocalDate?): Long? {
        return date?.toEpochDay()
    }
}

class TimeConverter {
    @TypeConverter
    fun fromTimestamp(value: Long?): LocalTime? {
        return value?.let { LocalTime.ofSecondOfDay(it) }
    }

    @TypeConverter
    fun dateToTimestamp(time: LocalTime?): Long? {
        return time?.toSecondOfDay()?.toLong()
    }
}
