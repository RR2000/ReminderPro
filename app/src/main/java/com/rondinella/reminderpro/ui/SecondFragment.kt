package com.rondinella.reminderpro.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.room.Room
import com.rondinella.reminderpro.R
import com.rondinella.reminderpro.databinding.FragmentSecondBinding
import com.rondinella.reminderpro.tasks.Task
import com.rondinella.reminderpro.tasks.TaskDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.LocalTime

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSecond.setOnClickListener {
            // Create a new task
            val task = Task(
                title = "Example Task",
                description = "This is an example task",
                dueDate = LocalDate.now(),
                dueTime = LocalTime.now()
            )

            // Launch a new coroutine in the IO dispatcher (good for database operations)
            viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
                // Get an instance of the database and add the task
                val db = Room.databaseBuilder(
                    requireContext(),
                    TaskDatabase::class.java, "task-database"
                ).build()

                db.taskDao().insertTask(task)

                // Navigate back to the first fragment on the main dispatcher
                withContext(Dispatchers.Main) {
                    findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}