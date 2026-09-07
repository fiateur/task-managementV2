package com.adacorp.task_managementV2.controller;

import com.adacorp.task_managementV2.exception.EntityNotFoundException;
import com.adacorp.task_managementV2.model.Role;
import com.adacorp.task_managementV2.model.State;
import com.adacorp.task_managementV2.services.RoleService;
import com.adacorp.task_managementV2.services.StateService;
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
public class StateController {

    private final StateService stateService ;

    @Autowired
    public StateController(StateService stateService) {
        this.stateService = stateService;
    }

    @GetMapping(value = "/home-list-states")
    public String listStates(Model model, Principal p, HttpServletRequest request){

        List<State> listStates;
        listStates = this.stateService.findAll();
        model.addAttribute("states", listStates);
        model.addAttribute("dataTable", "page.titre.stateList") ;
        model.addAttribute("titreDataTable", "page.state.listState") ;
        model.addAttribute("Titre", "page.state.label.stateManagment") ;
        return "views/state-list";
    }

    @GetMapping(value = "/home-add-state")
    public String addState(Model model, Principal p, HttpServletRequest request) throws ParseException {

        State state = new State() ;
        model.addAttribute("state", state);
        setAttributCommun(model);

        return "views/state-add";
    }

    @PostMapping(value = "/home-add-state")
    public String addStatePost(@ModelAttribute("state") State state, Model model , RedirectAttributes redirectAttributes, Principal p, HttpServletRequest request){

        // ----------------------------------------------------------------------------
        model.addAttribute("SUCCESS","Successful Redirection add users page") ;
        model.addAttribute("state", new State());
        setAttributCommun (model);
        String page = null ;
        page = "redirect:/home-list-states" ;
        // ----------------------------------------------------------------------------

            // si le state qui vient du form n'a pas d'id alors c'est un nouvel enrégistrement
            if (state.getId() == null ) {
                this.stateService.save(state);
                log.info("state.getId()AFTER SAVE "+state.getId());
                redirectAttributes.addFlashAttribute("success", "Successful Insert Operation...");
            }
            // si le state qui vient du form a un id alors c'est une mise à jour des infos
            else if (state.getId() >= 1)
            {
                this.stateService.save(state);
                log.info("state.getId()AFTER UPDATE "+state.getId());
                model.addAttribute("succes", "Successful Update operation...");
            }

            return page ;
    }
    @GetMapping(value = "/home-edit-state")
    public String updateStateGet(@RequestParam("id") Long id, Model model) throws ParseException {

        State state = this.stateService.findOne(id).orElseThrow(() -> new EntityNotFoundException("Objet entité non Trouvé en Base...")) ;

        // ----------------------------------------------------------------------------
        model.addAttribute("SUCCESS","Successful Redirection add task page") ;
        model.addAttribute("state", state);
        setAttributCommun (model);
        // ----------------------------------------------------------------------------

        return "views/state-add" ;
    }

    public void setAttributCommun (Model model){
        model.addAttribute("labelAddState", "page.state.addState") ;
        model.addAttribute("labelModState", "page.state.modState") ;
        model.addAttribute("titreDataTable", "Ajout & Mise à Jour des States") ;
        model.addAttribute("Titre", "page.state.label.stateManagment") ;
        model.addAttribute("LabelCode", "commun.label.code") ;
        model.addAttribute("LabeDescription", "commun.label.description") ;
        model.addAttribute("LabelSave", "commun.label.save") ;
    }
}
