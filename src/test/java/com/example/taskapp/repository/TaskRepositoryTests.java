package com.example.taskapp.repository;

import com.example.taskapp.model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class TaskRepositoryTests {

    @Autowired
    private TaskRepository taskRepository;

    @BeforeEach
    void setUp() {
        taskRepository.deleteAll(); 
    }

    @Test
    void testSaveAndFindTask() {

        Task task = new Task();
        task.setTitle("Test Task");
        task.setDescription("This is a test task.");
        task.setAssignee("John Doe");
        task.setDueDate(LocalDate.now().plusDays(7));
        task.setPriority("High");


        Task savedTask = taskRepository.save(task);


        Task foundTask = taskRepository.findById(savedTask.getId()).orElse(null);


        assertThat(foundTask).isNotNull();
        assertThat(foundTask.getTitle()).isEqualTo("Test Task");
        assertThat(foundTask.getDescription()).isEqualTo("This is a test task.");
        assertThat(foundTask.getAssignee()).isEqualTo("John Doe");
        assertThat(foundTask.getDueDate()).isEqualTo(task.getDueDate());
        assertThat(foundTask.getPriority()).isEqualTo("High");
    }

    @Test
    void testFindAllTasks() {

        Task task1 = new Task();
        task1.setTitle("Task 1");
        task1.setDescription("First task.");
        task1.setAssignee("Alice");
        task1.setDueDate(LocalDate.now().plusDays(5));
        task1.setPriority("Medium");

        Task task2 = new Task();
        task2.setTitle("Task 2");
        task2.setDescription("Second task.");
        task2.setAssignee("Bob");
        task2.setDueDate(LocalDate.now().plusDays(10));
        task2.setPriority("Low");

        taskRepository.save(task1);
        taskRepository.save(task2);


        Iterable<Task> tasks = taskRepository.findAll();

        assertThat(tasks).hasSize(2);
    }

    @Test
    void testSaveTask() {
        Task task = new Task();
        task.setTitle("Test Title");
        task.setDescription("Test Description");
        task.setAssignee("Test Assignee");
        task.setDueDate(LocalDate.now());
        task.setPriority("HIGH");
        Task savedTask = taskRepository.save(task);
        assertNotNull(savedTask.getId()); 
    }
}
