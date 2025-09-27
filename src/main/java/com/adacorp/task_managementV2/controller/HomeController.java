package com.adacorp.task_managementV2.controller;

import com.adacorp.task_managementV2.constantes.RoleConstante;
import com.adacorp.task_managementV2.constantes.StateConstante;
import com.adacorp.task_managementV2.model.HostName;
import com.adacorp.task_managementV2.model.Task;
import com.adacorp.task_managementV2.model.Utilisateur;
import com.adacorp.task_managementV2.model.UtilisateurTask;
import com.adacorp.task_managementV2.security.CustomUserDetailsService;
import com.adacorp.task_managementV2.services.StateService;
import com.adacorp.task_managementV2.services.TaskService;
import com.adacorp.task_managementV2.services.UtilisateurService;
import com.adacorp.task_managementV2.services.UtilisateurTaskService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.ArrayList;
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
        // **************** Ceci est hors Spring-security ***********************
        // return "redirect:/home-list-users";
        // **************** Ceci est hors Spring-security ***********************
        return INDEX_LOGIN ;
    }

    @GetMapping(value = "/index-login")
    public String indexLogin(Model model){
        model.addAttribute(SUCCESS,"Task Management Application !!!") ;
        // **************** Ceci est hors Spring-security ***********************
        // return "redirect:/home-list-users";
        // **************** Ceci est hors Spring-security ***********************
        return INDEX_LOGIN ;
    }

    @GetMapping("/dashboard")
    public String dashboard(Principal p, HttpServletRequest request, Model model) {

        /* Utilisateur de la session de Connexion */
        Utilisateur connectedUserDB = userService.findByEmail( p.getName() ).get() ;

        // Gestion de l'Authentification en Mode ADMIN
        if (connectedUserDB.getRole().getCode().equals(RoleConstante.ROLE_ADMIN)) {
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
        }

        // Gestion de l'Authentification en Mode !ADMIN
        if (!connectedUserDB.getRole().getCode().equals(RoleConstante.ROLE_ADMIN)) {

            // Méthode Reference - Pour la liste des tâches en NEW de l'Utilisateur connecté
            List<UtilisateurTask> taskNewList = this.userTaskService.findByUtilisateur_EmailAndTask_State_CodeOrderByAssignmentDateDesc(connectedUserDB.getEmail(), StateConstante.NEW) ;
            // Méthode Reference - Pour la liste des tâches en TO.DO de l'Utilisateur connecté
            List<UtilisateurTask> taskTodoList = this.userTaskService.findByUtilisateur_EmailAndTask_State_CodeOrderByAssignmentDateDesc(connectedUserDB.getEmail(), StateConstante.TODO) ;
            // Méthode Reference - Pour la liste des tâches en DONE de l'Utilisateur connecté
            List<UtilisateurTask> taskDoneList = this.userTaskService.findByUtilisateur_EmailAndTask_State_CodeOrderByAssignmentDateDesc(connectedUserDB.getEmail(), StateConstante.DONE) ;


            model.addAttribute("newState", taskNewList.size() ) ;
            model.addAttribute("todoState", taskTodoList.size() ) ;
            model.addAttribute("doneState", taskDoneList.size() ) ;
            model.addAttribute("allState", taskNewList.size()+taskTodoList.size()+taskDoneList.size() ) ;


            model.addAttribute("taskNewList", taskNewList ) ;
            model.addAttribute("taskTodoList", taskTodoList ) ;
            model.addAttribute("taskDoneList", taskDoneList ) ;
        }

        model.addAttribute("Titre", "commun.label.dashboard") ;

        HostName H = new HostName();

        //Affecter au Poste client la valeur de la méthode getPosteName
        String posteClient =   H.getPosteClient(request);
        //Modification de la valeur du poste client après affectation
        H.setHostname(posteClient);

        // Session Active juste pour 24H
        request.getSession().setMaxInactiveInterval(3600*24);

        // On stocke l'objet session dans le String ${session.connectedUserDB} ${session.connectedUserDB.email}
        request.getSession().setAttribute( "userPrincipal", p ) ;
        request.getSession().setAttribute( "connectedUserDB", connectedUserDB ) ;
        request.getSession().setAttribute( "connectedUserHostName", posteClient ) ;

        return "views/welcome-dashboard";
    }

    @GetMapping(value = "/index-logout")
    public String logOut(HttpServletRequest request, Model model){
        model.addAttribute(SUCCESS,"Task Management Application - Logout User !!!") ;
        // On détruit la session
        request.getSession().invalidate();
        return INDEX_LOGIN ;
    }
}
