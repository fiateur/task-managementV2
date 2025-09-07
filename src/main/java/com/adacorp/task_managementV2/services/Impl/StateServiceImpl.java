package com.adacorp.task_managementV2.services.Impl;

import com.adacorp.task_managementV2.model.State;
import com.adacorp.task_managementV2.repository.StateRepository;
import com.adacorp.task_managementV2.services.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StateServiceImpl implements StateService {

    private final StateRepository stateRepository ;

    @Autowired
    public StateServiceImpl(StateRepository stateRepository) {
        this.stateRepository = stateRepository;
    }

    @Override
    public void save(State state) {
        this.stateRepository.save(state);
    }

    @Override
    public void update(State state) {
        this.stateRepository.save(state);
    }

    @Override
    public void delete(Long id) {
        this.stateRepository.deleteById(id);
    }

    @Override
    public void delete(State state) {
        this.stateRepository.delete(state);
    }

    @Override
    public Optional<State> findByCode(String code) {
        // return Optional.empty();
        return this.stateRepository.findByCode(code);
    }

    @Override
    public Optional<State> findOne(Long id) {
        // return Optional.empty();
        return this.stateRepository.findById(id) ;
    }

    @Override
    public List<State> findAll() {
        // return List.of();
        return this.stateRepository.findAll() ;
    }
}
