package com.adacorp.task_managementV2.controller;

import com.adacorp.task_managementV2.exception.EntityNotFoundException;
import com.adacorp.task_managementV2.model.Role;
import com.adacorp.task_managementV2.model.Task;
import com.adacorp.task_managementV2.model.Utilisateur;
import com.adacorp.task_managementV2.services.StateService;
import com.adacorp.task_managementV2.services.TaskService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
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

        List<Task> listTasks;
        listTasks = this.taskService.findAll();
        model.addAttribute("tasks", listTasks);
        model.addAttribute("dataTable", "page.titre.taskList") ;
        model.addAttribute("titreDataTable", "Gestion des Taches") ;
        model.addAttribute("Titre", "page.task.title.label.gestionDesTaches") ;
        return "views/task-list";
    }

    @GetMapping(value = "/home-add-task")
    public String addTask(Model model, Principal p, HttpServletRequest request) throws ParseException {

        Task task = new Task() ;
        model.addAttribute("task", task);
        setAttributCommun(model);

        return "views/task-add";
    }

    @PostMapping(value = "/home-add-task")
    public String addUserPost(@ModelAttribute("task") Task task, Model model , RedirectAttributes redirectAttributes, Principal p, HttpServletRequest request){

        // ----------------------------------------------------------------------------
        model.addAttribute("SUCCESS","Successful Redirection add users page") ;
        model.addAttribute("task", new Task());
        setAttributCommun (model);
        String page = null ;
        page = "redirect:/home-list-tasks" ;
        // ----------------------------------------------------------------------------

            // si le User qui vient du form n'a pas d'id alors c'est un nouvel enrégistrement
            if ( task.getId() == null ) {
                this.taskService.save(task);
                model.addAttribute("task",task) ;
                log.info("task.getId()AFTER SAVE "+task.getId());
                redirectAttributes.addFlashAttribute("success", "Successful Insert Operation...");
            }
            // si le User qui vient du form a un id alors c'est une mise à jour des infos
            else if (task.getId() >= 1)
            {
                this.taskService.save(task);
                model.addAttribute("task",task) ;
                log.info("task.getId()AFTER UPDATE "+task.getId());
                model.addAttribute("succes", "Successful Update operation...");
            }

            return page ;
    }
    @GetMapping(value = "/home-edit-task")
    public String updateTaskGet(@RequestParam("id") Long id, Model model) throws ParseException {

        Task task = this.taskService.findOne(id).orElseThrow(() -> new EntityNotFoundException("Objet entité non Trouvé en Base...")) ;

        // ----------------------------------------------------------------------------
        model.addAttribute("SUCCESS","Successful Redirection add task page") ;
        model.addAttribute("task", task);
        setAttributCommun (model);
        // ----------------------------------------------------------------------------

        return "views/task-add" ;
    }

    public void setAttributCommun (Model model){
        model.addAttribute("labelAddTask", "page.task.title.label.addTask") ;
        model.addAttribute("labelModTask", "page.task.title.label.modTask") ;
        model.addAttribute("titreDataTable", "Gestion Ajout & Mise à Jour des Taches") ;
        model.addAttribute("Titre", "page.task.title.label.gestionDesTaches") ;
        model.addAttribute("LabelName", "commun.label.name") ;
        model.addAttribute("LabelDescription", "commun.label.description") ;
        model.addAttribute("LabelDateCreation", "commun.label.dateCreation") ;
        model.addAttribute("LabelState", "commun.label.state") ;
        model.addAttribute("LabelSave", "commun.label.save") ;
        model.addAttribute("listState", stateService.findAll()) ;
    }
}
