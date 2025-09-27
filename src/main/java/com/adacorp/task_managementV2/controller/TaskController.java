package com.adacorp.task_managementV2.controller;

import com.adacorp.task_managementV2.services.StateService;
import com.adacorp.task_managementV2.services.TaskService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class TaskController {

    private final StateService stateService ;
    private final TaskService taskService ;

    @Autowired
    public  TaskController(StateService stateService, TaskService taskService) {
        this.stateService = stateService ;
        this.taskService = taskService ;
    }

    @GetMapping(value = "/home-list-tasks")
    public String listTasks(Model model, Principal p, HttpServletRequest request){

        model.addAttribute("dataTable", "page.titre.taskList") ;
        model.addAttribute("titreDataTable", "Gestion des Taches") ;
        model.addAttribute("Titre", "page.task.title.label") ;
        return "views/task-list";
    }

    @GetMapping(value = "/home-add-task")
    public String addTask(Model model, Principal p, HttpServletRequest request){
        model.addAttribute("dataTable", "page.titre.taskList") ;
        model.addAttribute("titreDataTable", "Gestion Ajout & Mise à Jour des Taches") ;
        model.addAttribute("Titre", "page.task.title.label.add") ;
        model.addAttribute("states", stateService.findAll()) ;
        return "views/task-add";
    }

}
