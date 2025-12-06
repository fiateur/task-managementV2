package com.adacorp.task_managementV2.services.Impl;

import com.adacorp.task_managementV2.model.Habilitation;
import com.adacorp.task_managementV2.repository.HabilitationRepository;
import com.adacorp.task_managementV2.services.HabilitationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HabilitationServiceImpl implements HabilitationService {

    // Dependency Injection By Variables
    @Autowired
    private final HabilitationRepository  habilitationRepository;

    // Dependency Injection Constructor
    @Autowired
    public HabilitationServiceImpl(HabilitationRepository habilitationRepository) {
        this.habilitationRepository = habilitationRepository;
    }

    @Override
    public void save(Habilitation habilitation) {
        this.habilitationRepository.save(habilitation) ;
    }

    @Override
    public void update(Habilitation habilitation) {
        this.habilitationRepository.save(habilitation) ;
    }

    @Override
    public void delete(Long id) {
        this.habilitationRepository.deleteById(id);
    }

    @Override
    public void delete(Habilitation habilitation) {
        this.habilitationRepository.delete(habilitation);
    }

    @Override
    public Optional<Habilitation> findByCode(String code) {
        return this.habilitationRepository.findByCode(code) ;
        // return Optional.empty();
    }

    @Override
    public Optional<Habilitation> findOne(Long id) {
        return this.habilitationRepository.findById(id) ;
        // return Optional.empty();
    }

    @Override
    public List<Habilitation> findAll() {
        return this.habilitationRepository.findAll() ;
        // return List.of();
    }
}
