package com.example.taskapp.controller;

import com.example.taskapp.model.Task;
import com.example.taskapp.repository.TaskRepository;

import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;


    @GetMapping("/tasks/form")
    public String showTaskForm(Model model) {
        model.addAttribute("task", new Task());
        return "task-form";  
        
    }


    @PostMapping("/tasks")
    public String submitTask(@ModelAttribute Task task) {
        taskRepository.save(task);
        return "redirect:/home";  
        
    }


    @GetMapping("/home")
    public String home() {
        return "task-home";  
        
    }


    @GetMapping("/tasks/list")
    public String listTasks(Model model) {
        model.addAttribute("tasks", taskRepository.findAll());
        return "task-list";  
        
    }

    // Optional: Handle GET requests to `/tasks` by redirecting or providing content
    @GetMapping("/tasks")
    public String tasksHome() {
        return "redirect:/home";  
        
    }


    @GetMapping("/tasks/details/{id}")
    public String showTaskDetails(@PathVariable("id") Long id, Model model) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid task ID: " + id));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDueDate = task.getDueDate().format(formatter);
        model.addAttribute("task", task);
        model.addAttribute("formattedDueDate", formattedDueDate);
        return "task-details";  
        
    }


    @GetMapping("/tasks/update/{id}")
    public String updateTaskForm(@PathVariable("id") Long id, Model model) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid task ID: " + id));
        model.addAttribute("task", task);
        return "update-task";  
    }

    //update
    @PostMapping("/tasks/update/{id}")
    public String updateTask(@PathVariable("id") Long id, @ModelAttribute Task task) {

        Task existingTask = taskRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid task ID: " + id));
        
        // Update fields
        existingTask.setTitle(task.getTitle());
        existingTask.setDescription(task.getDescription());
        existingTask.setAssignee(task.getAssignee());
        existingTask.setDueDate(task.getDueDate());
        existingTask.setPriority(task.getPriority());

        taskRepository.save(existingTask);

        return "redirect:/tasks/list";  
    }

    //delete
    @PostMapping("/tasks/delete/{id}")
    public String deleteTask(@PathVariable("id") Long id) {
        taskRepository.deleteById(id);
        return "redirect:/tasks/list";  
}




}
