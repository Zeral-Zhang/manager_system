package com.zeral.repository;

import com.zeral.domain.LineProcess;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@SuppressWarnings("unused")
@Repository
public interface LineProcessRepository extends JpaRepository<LineProcess, Long> {
    Page<LineProcess> findAllByDeleteStatueIsTrueAndLineId(Long lineId, Pageable pageable);
    LineProcess findByLineIdAndProcessId(Long lineId, Long processId);
    List<LineProcess> findAllByDeleteStatueIsTrueAndLineId(Long lineId);
}
