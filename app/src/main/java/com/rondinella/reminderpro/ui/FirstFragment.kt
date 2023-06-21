package com.rondinella.reminderpro.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.rondinella.reminderpro.R
import com.rondinella.reminderpro.databinding.FragmentFirstBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.rondinella.reminderpro.tasks.TaskDatabase

class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    // Define the RecyclerView and the Adapter
    private lateinit var taskRecyclerView: RecyclerView
    private lateinit var taskAdapter: TaskAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)

        // Get the RecyclerView from the binding
        taskRecyclerView = binding.taskRecyclerView

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize the adapter and attach it to the RecyclerView
        taskAdapter = TaskAdapter(listOf()) { task ->
            // launch a new coroutine in IO dispatcher for deleting the task
            viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
                // Get an instance of the database
                val db = Room.databaseBuilder(
                    requireContext(),
                    TaskDatabase::class.java, "task-database"
                ).build()

                db.taskDao().deleteTask(task)

                // update tasks and adapter on the main dispatcher
                withContext(Dispatchers.Main) {
                    val tasks = db.taskDao().getAllTasks()
                    taskAdapter.tasks = tasks
                    taskAdapter.notifyDataSetChanged()
                }
            }
        }
        taskRecyclerView.layoutManager = LinearLayoutManager(context)
        taskRecyclerView.adapter = taskAdapter

        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            // Get an instance of the database
            val db = Room.databaseBuilder(
                requireContext(),
                TaskDatabase::class.java, "task-database"
            ).build()

            // Retrieve the tasks
            val tasks = db.taskDao().getAllTasks()

            // Update the RecyclerView on the main thread
            withContext(Dispatchers.Main) {
                taskAdapter.tasks = tasks
                taskAdapter.notifyDataSetChanged()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
