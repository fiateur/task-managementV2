package com.adacorp.task_managementV2.controller;

import com.adacorp.task_managementV2.exception.EntityNotFoundException;
import com.adacorp.task_managementV2.model.Role;
import com.adacorp.task_managementV2.services.RoleService;
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
import java.util.List;

@Slf4j
@Controller
public class RoleController {

    private final RoleService roleService ;


    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService ;
    }

    @GetMapping(value = "/home-list-roles")
    public String listRoles(Model model, Principal p, HttpServletRequest request){

        List<Role> listRoles;
        listRoles = this.roleService.findAll();
        model.addAttribute("roles", listRoles);
        model.addAttribute("dataTable", "page.titre.roleList") ;
        model.addAttribute("titreDataTable", "page.role.listRole") ;
        model.addAttribute("Titre", "page.role.label.roleManagment") ;
        return "views/role-list";
    }

    @GetMapping(value = "/home-add-role")
    public String addRole(Model model, Principal p, HttpServletRequest request) throws ParseException {

        Role role = new Role() ;
        model.addAttribute("role", role);
        setAttributCommun(model);

        return "views/role-add";
    }

    @PostMapping(value = "/home-add-role")
    public String addUserPost(@ModelAttribute("role") Role role, Model model , RedirectAttributes redirectAttributes, Principal p, HttpServletRequest request){

        // ----------------------------------------------------------------------------
        model.addAttribute("SUCCESS","Successful Redirection add users page") ;
        model.addAttribute("role", new Role());
        setAttributCommun (model);
        String page = null ;
        page = "redirect:/home-list-roles" ;
        // ----------------------------------------------------------------------------

            // si le role qui vient du form n'a pas d'id alors c'est un nouvel enrégistrement
            if (role.getId() == null ) {
                this.roleService.save(role);
                log.info("role.getId()AFTER SAVE "+role.getId());
                redirectAttributes.addFlashAttribute("success", "Successful Insert Operation...");
            }
            // si le role qui vient du form a un id alors c'est une mise à jour des infos
            else if (role.getId() >= 1)
            {
                this.roleService.save(role);
                log.info("role.getId()AFTER UPDATE "+role.getId());
                model.addAttribute("succes", "Successful Update operation...");
            }

            return page ;
    }
    @GetMapping(value = "/home-edit-role")
    public String updateTaskGet(@RequestParam("id") Long id, Model model) throws ParseException {

        Role role = this.roleService.findOne(id).orElseThrow(() -> new EntityNotFoundException("Objet entité non Trouvé en Base...")) ;

        // ----------------------------------------------------------------------------
        model.addAttribute("SUCCESS","Successful Redirection add task page") ;
        model.addAttribute("role", role);
        setAttributCommun (model);
        // ----------------------------------------------------------------------------

        return "views/role-add" ;
    }

    public void setAttributCommun (Model model){
        model.addAttribute("labelAddRole", "page.role.addRole") ;
        model.addAttribute("labelModRole", "page.role.modRole") ;
        model.addAttribute("titreDataTable", "Ajout & Mise à Jour des Roles") ;
        model.addAttribute("Titre", "page.role.label.roleManagment") ;
        model.addAttribute("LabelCode", "commun.label.code") ;
        model.addAttribute("LabelLibelle", "commun.label.libelle") ;
        model.addAttribute("LabelSave", "commun.label.save") ;
    }
}
