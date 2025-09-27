package com.adacorp.task_managementV2.controller;

import com.adacorp.task_managementV2.exception.EntityNotFoundException;
import com.adacorp.task_managementV2.model.Role;
import com.adacorp.task_managementV2.model.Utilisateur;
import com.adacorp.task_managementV2.services.RoleService;
import com.adacorp.task_managementV2.services.UtilisateurService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
import java.util.Optional;

@Slf4j
@Controller
public class UserController {

    private static final String USER_SESSION = "connectedUserDB";
    private final UtilisateurService utilisateurService;
    private final RoleService roleService;
    private static final String UTILISATEUR = "utilisateur" ;
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
    private static  final String MSG_CONFIRMATION_NOT_SAME_PWD = "Confirmation password is not the same as User password" ;
    private static  final String MSG_NO_SUCH_USER_FOUND = "No such user found" ;
    private static  final String FIRST_PWD = "123456789" ;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UtilisateurService utilisateurService, RoleService roleService, PasswordEncoder passwordEncoder ) {
        this.utilisateurService = utilisateurService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
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
            utilisateur.setPassword( this.passwordEncoder.encode(FIRST_PWD) );
            // utilisateur.setPassword( "123456789" ) ;
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
            utilisateur.setPassword( this.passwordEncoder.encode(FIRST_PWD) );
            // utilisateur.setPassword( "123456789" ) ;
            utilisateur.setRole(role.orElseThrow(() -> new RuntimeException("No role found")));
            this.utilisateurService.save(utilisateur);

        }

        model.addAttribute(SUCCESS,"Successful creation of test user") ;
        return "redirect:/" ;
    }

    @GetMapping(value = "/home-list-users")
    public String listUsers(Model model, Principal p){

        List<Utilisateur> listUsers;
        listUsers = this.utilisateurService.findAll() ;
        // ----------------------------------------------------------------------------
        model.addAttribute(USERS, listUsers);
        model.addAttribute(SUCCESS,"Successful Redirection users page") ;
        model.addAttribute("Titre", "commun.label.usersManagement") ;
        model.addAttribute("userNameSession", p.getName()) ;
        model.addAttribute("userSession", utilisateurService.findByEmail(p.getName()).get()) ;
        // ----------------------------------------------------------------------------
        return PAGE_USER_LIST ;
    }

    @GetMapping(value = "/home-add-user")
    public String addUserGet(Model model, Principal p, HttpServletRequest request) throws ParseException {

        List<Role> roleList = this.roleService.findAll() ;
        Utilisateur utilisateur = new Utilisateur() ;
        utilisateur.setAccountNonLocked(true);



        // NOTES :--> Différents type de Formatage de Date Java HTML thymeleaf
        Date dateDerniereConnexion = utilisateur.getDateDerniereConnexion() ;
        // Outils de Formatage de la Date en format Database
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
        String dateHeureConnexionString = sdf.format(dateDerniereConnexion != null ? dateDerniereConnexion : new Date());
        Date dateHeureConnexionDateDate = sdf.parse(dateHeureConnexionString) ;

        /*
            Explanation of Pattern:
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
                    yyyy → 4-digit year
                    MM → 2-digit month
                    dd → 2-digit day
                    HH → 2-digit hour in 24-hour format (use hh for 12-hour)
                    mm → 2-digit minute
                    ss → 2-digit seconds
                    SSS → 3-digit milliseconds (not mmm)
        */
        utilisateur.setDateDerniereConnexion(dateHeureConnexionDateDate);
        // NOTES :--> Différents type de Formatage de Date Java HTML thymeleaf

        // ----------------------------------------------------------------------------
        model.addAttribute(SUCCESS,"Successful Redirection add users page") ;
        model.addAttribute("utilisateur", utilisateur);
        model.addAttribute("dateHeureConnexionString", dateHeureConnexionString);
        model.addAttribute("dateHeureConnexionDateEntity", utilisateur.getDateDerniereConnexion());
        model.addAttribute("dateDerniereConnexionDateModel", dateHeureConnexionDateDate);
        setAttributCommun (model);
        // ----------------------------------------------------------------------------

        return PAGE_USER_ADD ;
    }

    @PostMapping(value = "/home-add-user")
    public String addUserPost(@ModelAttribute(UTILISATEUR) Utilisateur utilisateur, Model model , RedirectAttributes redirectAttributes, Principal p, HttpServletRequest request){

        // ----------------------------------------------------------------------------
        model.addAttribute(SUCCESS,"Successful Redirection add users page") ;
        model.addAttribute("utilisateur", new Utilisateur());
        setAttributCommun (model);
        // ----------------------------------------------------------------------------


        List<Role> listRole = this.roleService.findAll() ;
        model.addAttribute(ROLES,listRole) ;
        log.info("user.getId() {}", utilisateur.getId());

        if (utilisateur.getConfirmPassword().equals( utilisateur.getPassword() )){

            // String password = this.passwordEncoder.encode   ( utilisateur.getPassword() ) ;
            String password = utilisateur.getPassword() ;
            String page = null ;
            // si le User qui vient du form n'a pas d'id alors c'est un nouvel enrégistrement
            if ( utilisateur.getId() == null ) {
                utilisateur.setPassword(password);
                this.utilisateurService.save(utilisateur);
                model.addAttribute(UTILISATEUR,utilisateur) ;
                log.info("user.getId()AFTER SAVE "+utilisateur.getId());
                redirectAttributes.addFlashAttribute(SUCCESS, "Successful Insert Operation...");
                page = "redirect:/home-list-users" ;
            }
            // si le User qui vient du form a un id alors c'est une mise à jour des infos
            else if (utilisateur.getId() >= 1)
            {
                utilisateur.setPassword(password);
                this.utilisateurService.save(utilisateur);
                model.addAttribute(UTILISATEUR,utilisateur) ;
                log.info("user.getId()AFTER UPDATE "+utilisateur.getId());
                model.addAttribute(SUCCESS, "Successful Update operation...");

                // On modifie les infos d'affichage de l'Utilisateur Connecté
                /*
                Optional<Utilisateur>  connectedUserDB = utilisateurService.findByEmail( p.getName() ) ;
                if ( connectedUserDB.isPresent() && Objects.equals( utilisateur.getId(), connectedUserDB.get().getId()) ){
                    connectedUserDB.get().setId(utilisateur.getId());
                    connectedUserDB.get().setPassword(password);
                    connectedUserDB.get().setLastName(utilisateur.getLastName());
                    connectedUserDB.get().setFirstName(utilisateur.getFirstName());
                    connectedUserDB.get().setRole(utilisateur.getRole());
                    connectedUserDB.get().setEmail(utilisateur.getEmail());
                    connectedUserDB.get().setTelephone(utilisateur.getTelephone());
                    connectedUserDB.get().setAccountNonLocked(true);
                    request.getSession().setAttribute( USER_SESSION, connectedUserDB.get() ) ;
                }
                */
                page = PAGE_USER_ADD ;
            }

            return page ;

        } else {

            String page = null ;

            if ( utilisateur.getId() == null ){
                model.addAttribute(SUCCESS,null) ;
                model.addAttribute(UTILISATEUR,utilisateur) ;
                redirectAttributes.addFlashAttribute(ERROR,MSG_CONFIRMATION_NOT_SAME_PWD) ;
                page =  "redirect:/home-add-user" ;
            } else if (utilisateur.getId() >= 1) {
                model.addAttribute(SUCCESS,null) ;
                model.addAttribute(UTILISATEUR,utilisateur) ;
                model.addAttribute(ERROR,MSG_CONFIRMATION_NOT_SAME_PWD) ;
                page = PAGE_USER_ADD ;
            }

            return page ;
        }

    }

    @GetMapping(value = "/home-edit-user")
    public String updateUserGet(@RequestParam("id") Long id, Model model) throws ParseException {

        List<Role> roleList = this.roleService.findAll() ;
        Utilisateur utilisateur = this.utilisateurService.findOne(id).orElseThrow(() -> new EntityNotFoundException("Objet entité non Trouvé en Base...")) ;

        Date dateDerniereConnexion = utilisateur.getDateDerniereConnexion() ;
        // Outils de Formatage de la Date en format Database
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
        String dateHeureConnexionString = sdf.format(dateDerniereConnexion != null ? dateDerniereConnexion : new Date());
        Date dateHeureConnexionDateDate = sdf.parse(dateHeureConnexionString) ;

        /*
            Explanation of Pattern:
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
                    yyyy → 4-digit year
                    MM → 2-digit month
                    dd → 2-digit day
                    HH → 2-digit hour in 24-hour format (use hh for 12-hour)
                    mm → 2-digit minute
                    ss → 2-digit seconds
                    SSS → 3-digit milliseconds (not mmm)
        */

        // ----------------------------------------------------------------------------
        model.addAttribute(SUCCESS,"Successful Redirection add users page") ;
        model.addAttribute("utilisateur", utilisateur);
        model.addAttribute("dateHeureConnexionString", dateHeureConnexionString);
        model.addAttribute("dateHeureConnexionDateEntity", utilisateur.getDateDerniereConnexion());
        model.addAttribute("dateDerniereConnexionDateModel", dateHeureConnexionDateDate);
        model.addAttribute(ROLES,roleList);
        setAttributCommun (model);
        // ----------------------------------------------------------------------------

        return PAGE_USER_ADD ;
    }

    @GetMapping(value = "/home-disable-OR-enable-user")
    public String deleteUser(@RequestParam("id") Long id, RedirectAttributes redirectAttributes ) {
        Optional<Utilisateur> user = this.utilisateurService.findOne(id);

        if (user.isPresent()) {
            // user.get().setAccountNonLocked( !user.get().isAccountNonLocked() ) ;
            redirectAttributes.addFlashAttribute("successOperation", "Opération effectuée avec Succès...") ;
        } else  {
            redirectAttributes.addFlashAttribute("errorOperation", "Echec de l'Opération") ;
        }

        // Interface Fonctionnelle : Lambda Expression
        user.ifPresent(utilisateur -> utilisateur.setAccountNonLocked(!utilisateur.isAccountNonLocked()));

        utilisateurService.save(user.get()) ;
        // utilisateurService.delete(user.orElseThrow(() -> new RuntimeException(MSG_NO_SUCH_USER_FOUND)));
        return "redirect:/home-list-users" ;
    }

    public void setAttributCommun (Model model){
        List<Role> roleList = this.roleService.findAll() ;
        model.addAttribute("Titre", "page.user.addUser") ;
        model.addAttribute("LabelFistName", "commun.label.firstName") ;
        model.addAttribute("LabelLastName", "commun.label.lastName") ;
        model.addAttribute("LabelSexe", "commun.label.sexe") ;
        model.addAttribute("LabelSexeF", "commun.label.sexeF") ;
        model.addAttribute("LabelSexeM", "commun.label.sexeM") ;
        model.addAttribute("LabelMasculin", "commun.label.masculin") ;
        model.addAttribute("LabelFeminin", "commun.label.feminin") ;
        model.addAttribute("LabelTelephone", "commun.label.telephone") ;
        model.addAttribute("LabelEmail", "commun.label.email") ;
        model.addAttribute("LabelPassword", "commun.label.password") ;
        model.addAttribute("LabelConfirmPassword", "commun.label.confirmPassword") ;
        model.addAttribute("LabelRole", "commun.label.role") ;
        model.addAttribute("LabelSave", "commun.label.save") ;
        model.addAttribute("LabelHaveAccount", "Have an account? Go to login") ;
        model.addAttribute("listRole", roleList) ;
    }
}