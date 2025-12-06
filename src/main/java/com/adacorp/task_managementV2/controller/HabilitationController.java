package com.adacorp.task_managementV2.controller;

import com.adacorp.task_managementV2.exception.EntityNotFoundException;
import com.adacorp.task_managementV2.model.Habilitation;
import com.adacorp.task_managementV2.services.HabilitationService;
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
import java.util.List;

@Slf4j
@Controller
public class HabilitationController {

    private final HabilitationService habilitationService ;

    @Autowired
    public  HabilitationController(HabilitationService habilitationService) {
        this.habilitationService = habilitationService ;
    }

    @GetMapping(value = "/home-list-habilitations")
    public String listHabilitations(Model model, Principal p, HttpServletRequest request){

        List<Habilitation> listHabilitations;
        listHabilitations = this.habilitationService.findAll();
        model.addAttribute("habilitations", listHabilitations);
        model.addAttribute("dataTable", "page.titre.habilitationList") ;
        model.addAttribute("titreDataTable", "page.listHabilitation.listRole") ;
        model.addAttribute("Titre", "page.role.label.roleManagment") ;
        return "views/habilitation-list";
    }

    @GetMapping(value = "/home-add-habilitation")
    public String addRole(Model model, Principal p, HttpServletRequest request) throws ParseException {

        Habilitation habilitation = new Habilitation() ;
        model.addAttribute("habilitation", habilitation);
        setAttributCommun(model);

        return "views/habilitation-add";
    }

    @PostMapping(value = "/home-add-habilitation")
    public String addHabilitationPost(@ModelAttribute("habilitation") Habilitation habilitation, Model model , RedirectAttributes redirectAttributes, Principal p, HttpServletRequest request){

        // ----------------------------------------------------------------------------
        model.addAttribute("SUCCESS","Successful Redirection add users page") ;
        model.addAttribute("habilitation", new Habilitation());
        setAttributCommun (model);
        String page = null ;
        page = "redirect:/home-list-habilitations" ;
        // ----------------------------------------------------------------------------

            // si l'habilitation qui vient du form n'a pas d'id alors c'est un nouvel enrégistrement
            if (habilitation.getId() == null ) {
                this.habilitationService.save(habilitation);
                log.info("habilitation.getId()AFTER SAVE " + habilitation.getId());
                redirectAttributes.addFlashAttribute("success", "Successful Insert Operation...");
            }
            // si le habilitation qui vient du form a un id alors c'est une mise à jour des infos
            else if (habilitation.getId() >= 1)
            {
                this.habilitationService.save(habilitation);
                log.info("habilitation.getId()AFTER UPDATE "+ habilitation.getId());
                model.addAttribute("succes", "Successful Update operation...");
            }

            return page ;
    }
    @GetMapping(value = "/home-edit-habilitation")
    public String updateHabilitationGet(@RequestParam("id") Long id, Model model) throws ParseException {

        Habilitation habilitation = this.habilitationService.findOne(id).orElseThrow(() -> new EntityNotFoundException("Objet entité non Trouvé en Base...")) ;

        // ----------------------------------------------------------------------------
        model.addAttribute("SUCCESS","Successful Redirection add task page") ;
        model.addAttribute("habilitation", habilitation);
        setAttributCommun (model);
        // ----------------------------------------------------------------------------

        return "views/habilitation-add" ;
    }

    public void setAttributCommun (Model model){
        model.addAttribute("labelAddHabilitation", "page.habilitation.addHabilitation") ;
        model.addAttribute("labelModHabilitation", "page.Habilitation.modHabilitation") ;
        model.addAttribute("titreDataTable", "Ajout & Mise à Jour des Habilitation") ;
        model.addAttribute("Titre", "page.habilitation.label.habilitationManagment") ;
        model.addAttribute("LabelCode", "commun.label.code") ;
        model.addAttribute("LabelLibelle", "commun.label.libelle") ;
        model.addAttribute("LabelSave", "commun.label.save") ;
    }
}
