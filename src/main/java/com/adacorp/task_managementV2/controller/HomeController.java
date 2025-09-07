package com.adacorp.task_managementV2.controller;

import com.adacorp.task_managementV2.constantes.StateConstante;
import com.adacorp.task_managementV2.model.Task;
import com.adacorp.task_managementV2.model.Utilisateur;
import com.adacorp.task_managementV2.model.UtilisateurTask;
import com.adacorp.task_managementV2.services.StateService;
import com.adacorp.task_managementV2.services.TaskService;
import com.adacorp.task_managementV2.services.UtilisateurService;
import com.adacorp.task_managementV2.services.UtilisateurTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
public class HomeController {

    private UtilisateurService userService ;
    private StateService stateService ;
    private TaskService taskService ;
    private UtilisateurTaskService userTaskService ;
    private Task task ;
    private UtilisateurTask userTask ;
    private static final  String INDEX_LOGIN = "index-login" ;
    private static final  String INDEX_FORGOT_PASSWPORD = "index-forgot-password" ;
    private static final String SUCCESS = "success" ;
    private static final String ERROR = "error" ;
    private static final String USER = "user" ;
    private static final String MSG = "msg" ;

    @Autowired
    public HomeController(UtilisateurService userService, StateService stateService, TaskService taskService, UtilisateurTaskService userTaskService) {
        this.userService = userService;
        this.stateService = stateService;
        this.taskService = taskService;
        this.userTaskService = userTaskService;
    }

    @GetMapping(value = "/")
    public String indexApp(Model model){
        model.addAttribute(SUCCESS,"Task Management Application !!!") ;
        // return INDEX_LOGIN ;
        return "redirect:/home-list-users";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {

        /* Utilisateur de la session de Connexion */
        // Utilisateur connectedUserDB = userService.findByEmail( p.getName() ).get() ;

        model.addAttribute("newState", this.taskService.countByStateCode(StateConstante.NEW) ) ;
        model.addAttribute("todoState", this.taskService.countByStateCode(StateConstante.TODO) ) ;
        model.addAttribute("doneState", this.taskService.countByStateCode(StateConstante.DONE) ) ;
        model.addAttribute("allState", this.taskService.count() ) ;

        List<Task> taskNewList = this.taskService.findAllStateCode(StateConstante.NEW) ;
        List<Task> taskTodoList = this.taskService.findAllStateCode(StateConstante.TODO) ;
        List<Task> taskDoneList = this.taskService.findAllStateCode(StateConstante.DONE) ;

        model.addAttribute("taskNewList", taskNewList ) ;
        model.addAttribute("taskTodoList", taskTodoList ) ;
        model.addAttribute("taskDoneList", taskDoneList ) ;
        model.addAttribute("Titre", "commun.label.dashboard") ;

        return "views/welcome-dashboard";
    }
}
