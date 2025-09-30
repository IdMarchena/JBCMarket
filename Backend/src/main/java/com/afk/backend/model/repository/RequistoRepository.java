package com.afk.backend.model.repository;

import com.afk.backend.model.entity.Requisito;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RequistoRepository extends JpaRepository<Requisito, Long> {
    List<Requisito> findByVacanteId(Long idVacante);
}
