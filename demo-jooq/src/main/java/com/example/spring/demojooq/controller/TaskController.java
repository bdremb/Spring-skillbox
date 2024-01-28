package com.example.spring.demojooq.controller;


import com.example.spring.demojooq.model.Task;
import com.example.spring.demojooq.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import static java.util.Objects.nonNull;

@Controller
@RequiredArgsConstructor
public class TaskController {

    private final TaskService service;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("tasks", service.findAll());
        return "index"; //index.html
    }

    @GetMapping("/task/create")
    public String showCreateForm(Model model) {
        model.addAttribute("task", new Task());
        return "create"; //create.html
    }

    @PostMapping("/task/create")
    public String createTask(@ModelAttribute Task task) {
        task.setId(System.currentTimeMillis());
        service.save(task);

        return "redirect:/";  //public String index(Model model)
    }

    @GetMapping("/task/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Task task = service.getById(id);
        if (nonNull(task)) {
            model.addAttribute("task", task);
            return "edit"; //edit.html
        }
        return "redirect:/"; //public String index(Model model)
    }

    @PostMapping("/task/edit")
    public String editTask(@ModelAttribute Task task) {
        service.update(task);
        return "redirect:/"; //public String index(Model model)
    }

    @GetMapping("/task/delete/{id}")
    public String deleteTask(@PathVariable Long id) {
        service.deleteById(id);
        return "redirect:/"; //public String index(Model model)
    }

}
