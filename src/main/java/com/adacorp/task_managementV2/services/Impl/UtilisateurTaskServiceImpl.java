package com.adacorp.task_managementV2.services.Impl;

import com.adacorp.task_managementV2.model.Task;
import com.adacorp.task_managementV2.model.Utilisateur;
import com.adacorp.task_managementV2.model.UtilisateurTask;
import com.adacorp.task_managementV2.repository.UtilisateurTaskRepository;
import com.adacorp.task_managementV2.services.UtilisateurTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UtilisateurTaskServiceImpl implements UtilisateurTaskService {

    private final UtilisateurTaskRepository utilisateurTaskRepository ;

    @Autowired
    public UtilisateurTaskServiceImpl(UtilisateurTaskRepository utilisateurTaskRepository) {
        this.utilisateurTaskRepository = utilisateurTaskRepository;
    }

    @Override
    public void save(UtilisateurTask utilisateurTask) {
        this.utilisateurTaskRepository.save(utilisateurTask);
    }

    @Override
    public void update(UtilisateurTask utilisateurTask) {
        this.utilisateurTaskRepository.save(utilisateurTask);
    }

    @Override
    public void delete(Long id) {
        this.utilisateurTaskRepository.deleteById(id);
    }

    @Override
    public void delete(UtilisateurTask utilisateurTask) {
        this.utilisateurTaskRepository.delete(utilisateurTask);
    }

    @Override
    public Optional<UtilisateurTask> findOne(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<UtilisateurTask> findByTask(Task task) {
        // return Optional.empty();
        return this.utilisateurTaskRepository.findByTask(task);
    }

    @Override
    public List<UtilisateurTask> findByUtilisateur(Utilisateur utilisateur) {
        // return List.of();
        return this.utilisateurTaskRepository.findAllByUtilisateur(utilisateur);
    }

    @Override
    public List<UtilisateurTask> findAll() {
        // return List.of();
        return this.utilisateurTaskRepository.findAll();
    }
}
