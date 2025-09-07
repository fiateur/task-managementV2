package com.adacorp.task_managementV2.controller;

import com.adacorp.task_managementV2.model.Role;
import com.adacorp.task_managementV2.model.Utilisateur;
import com.adacorp.task_managementV2.services.Impl.UtilisateurServiceImpl;
import com.adacorp.task_managementV2.services.RoleService;
import com.adacorp.task_managementV2.services.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class UserController {

    private final UtilisateurService utilisateurService;
    private final RoleService roleService;
    private static final String USER = "user" ;
    private static  final String USERS = "users";
    private static final String ROLES =  "roles";
    private static  final String SUCCESS = "success" ;
    private static  final String ERROR = "error" ;
    private static  final String PAGE_USER_UPDATE_PWD = "views/user-update-password" ;
    private static  final String PAGE_USER_ADD = "views/user-add" ;
    private static  final String PAGE_USER_LIST = "views/user-list" ;
    private static  final String PAGE_USER_DETAIL = "views/user-detail" ;
    private static  final String PAGE_USER_PROFILE = "views/user-profile" ;
    private static  final String USER_ADMIN_ACCOUNT = "admin@admin.com" ;

    @Autowired
    public UserController(UtilisateurService utilisateurService, RoleService roleService) {
        this.utilisateurService = utilisateurService;
        this.roleService = roleService;
    }

    @GetMapping(value = "/create-user-test")
    public String addAdminUser(Model model){
        Optional<Role> role = roleService.findByCode("ADMIN");
        Utilisateur utilisateur = new Utilisateur();

        if (this.utilisateurService.findByEmail(USER_ADMIN_ACCOUNT).isEmpty()) {
            utilisateur = new Utilisateur() ;
            utilisateur.setAccountNonLocked(true);
            utilisateur.setFirstName("Root");
            utilisateur.setLastName("Administrator");
            utilisateur.setTelephone("696237913");
            utilisateur.setEmail(USER_ADMIN_ACCOUNT);
            utilisateur.setSexe('M');
            // utilisateur.setPassword( this.passwordEncoder.encode(FIRST_PWD) );
            utilisateur.setPassword( "1234566789" ) ;
            utilisateur.setRole(role.orElseThrow(() -> new RuntimeException("No role found")));
            this.utilisateurService.save(utilisateur);
        } else {
            utilisateur = this.utilisateurService.findByEmail(USER_ADMIN_ACCOUNT).get() ;
            utilisateur.setAccountNonLocked(true);
            utilisateur.setFirstName("Root");
            utilisateur.setLastName("Administrator");
            utilisateur.setTelephone("696237913");
            utilisateur.setEmail(USER_ADMIN_ACCOUNT);
            utilisateur.setSexe('M');
            // utilisateur.setPassword( this.passwordEncoder.encode(FIRST_PWD) );
            utilisateur.setPassword( "1234566789" ) ;
            utilisateur.setRole(role.orElseThrow(() -> new RuntimeException("No role found")));
            this.utilisateurService.save(utilisateur);

        }

        model.addAttribute(SUCCESS,"Successful creation of test user") ;
        return "redirect:/" ;
    }

    @GetMapping(value = "/home-list-users")
    public String listUsers(Model model){

        List<Utilisateur> listUsers;
        listUsers = this.utilisateurService.findAll() ;
        // ----------------------------------------------------------------------------
        model.addAttribute(USERS, listUsers);
        model.addAttribute(SUCCESS,"Successful Redirection users page") ;
        model.addAttribute("Titre", "commun.label.usersManagement") ;
        // ----------------------------------------------------------------------------
        return PAGE_USER_LIST ;
    }


}