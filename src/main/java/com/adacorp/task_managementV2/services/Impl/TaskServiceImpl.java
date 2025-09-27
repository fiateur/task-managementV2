package com.adacorp.task_managementV2.services.Impl;

import com.adacorp.task_managementV2.model.State;
import com.adacorp.task_managementV2.model.Task;
import com.adacorp.task_managementV2.repository.TaskRepository;
import com.adacorp.task_managementV2.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository ;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public void save(Task task) {
        this.taskRepository.save(task);
    }

    @Override
    public void update(Task task) {
        this.taskRepository.save(task);
    }

    @Override
    public void delete(Long id) {
        this.taskRepository.deleteById(id);
    }

    @Override
    public void delete(Task task) {
        this.taskRepository.delete(task);
    }

    @Override
    public Optional<Task> findByName(String name) {
        // return Optional.empty();
        return this.taskRepository.findByName(name);
    }

    @Override
    public Optional<Task> findOne(Long id) {
        // return Optional.empty();
        return this.taskRepository.findById(id);
    }

    @Override
    public List<Task> findAll() {
        // return List.of();
        return this.taskRepository.findAll();
    }

    @Override
    public long count() {
        return this.taskRepository.count();
    }

    @Override
    public int countByStateCode(String code) {
        return this.taskRepository.countByState_Code(code);
    }

    @Override
    public int countAllByStateCode(String code) {
        return this.taskRepository.countAllByState_Code(code);
    }

    @Override
    public long countByState(State state) {
        return this.taskRepository.countByState(state);
    }

    @Override
    public List<Task> findAllStateCode(String code) {
        // return List.of();
        return  this.taskRepository.findALlByState_CodeOrderByDateCreationDesc(code);
    }

    @Override
    public List<Task> findAllByStateIdAndDateCreationBetweenNativeQuery(String dateDebut, String dateFin, Long state_id) {
        return this.taskRepository.findAllByStateIdAndDateCreationBetweenNativeQuery( dateDebut, dateFin, state_id);
    }

    @Override
    public List<Task> findAllByDateCreationBetweenJoinUserTaskNativeQuery(String dateDebut, String dateFin, Long utilisateur_id) {
        return this.taskRepository.findAllByDateCreationBetweenJoinUtilisateurTaskNativeQuery(dateDebut, dateFin, utilisateur_id);
    }

    @Override
    public List<Task> findAllByStateIdAndDateCreationBetweenJoinUserTaskNativeQuery(String dateDebut, String dateFin, Long state_id, Long user_id) {
        return this.taskRepository.findAllByStateIdAndDateCreationBetweenJoinUtilisateurTaskNativeQuery(dateDebut, dateFin, state_id, user_id);
    }

    @Override
    public List<Task> findAllByDateCreationBetweenQuery(Date dateDebut, Date dateFin) {
        return this.taskRepository.findAllByDateCreationBetweenQuery(dateDebut, dateFin);
    }

    @Override
    public List<Task> findAllByDateCreationBetweenNativeQuery(String dateDebut, String dateFin) {
        return this.taskRepository.findAllByDateCreationBetweenNativeQuery(dateDebut, dateFin);
    }
}
