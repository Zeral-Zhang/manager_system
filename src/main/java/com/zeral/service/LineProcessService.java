package com.zeral.service;

import com.zeral.domain.LineBody;
import com.zeral.domain.LineProcess;
import com.zeral.repository.LineBodyRepository;
import com.zeral.repository.LineProcessRepository;
import com.zeral.web.rest.vm.LineBodyAndProcessVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class LineProcessService {
    private final Logger log = LoggerFactory.getLogger(WorkPlanService.class);
    private final LineProcessRepository lineProcessRepository;
    private final LineBodyRepository lineBodyRepository;
    public LineProcessService(LineProcessRepository lineProcessRepository, LineBodyRepository lineBodyRepository) {
        this.lineProcessRepository = lineProcessRepository;
        this.lineBodyRepository = lineBodyRepository;
    }
    @Transactional(readOnly = true)
    public Page<LineProcess> findAllLineProcess(String query, Pageable pageable) {
        Pageable p = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(),
            new Sort(Sort.DEFAULT_DIRECTION, "sort"));
        Long lineId = Long.parseLong(query);
        return lineProcessRepository.findAllByDeleteStatueIsTrueAndLineId(lineId,p);
    }
    public LineProcess save(LineProcess lineProcess) {
        log.debug("Request to save LineProcess : {}", lineProcess);
        return lineProcessRepository.save(lineProcess);
    }
    public Optional<LineBody> updateLineProcess(LineBodyAndProcessVM lineBodyAndProcessVM) {
        Optional<LineBody> lineBody = lineBodyRepository.findOneById(lineBodyAndProcessVM.getLineBody().getId());
        List<LineProcess> oldLineProcess = lineProcessRepository.findAllByDeleteStatueIsTrueAndLineId(lineBodyAndProcessVM.getLineBody().getId());
        for (LineProcess oldLp : oldLineProcess) {
            LineProcess lineProcess1 = lineProcessRepository.findByLineIdAndProcessId(oldLp.getLineId(), oldLp.getProcessId());
            lineProcessRepository.delete(lineProcess1.getId());
        }
//        for (LineProcess newLp : lineBodyAndProcessVM.getLineProcesses()) {
//            newLp.setDeleteStatue(true);
//            lineProcessRepository.save(newLp);
//        }
        for (int i = 0; i < lineBodyAndProcessVM.getLineProcesses().size(); i++) {
            LineProcess newlp = lineBodyAndProcessVM.getLineProcesses().get(i);
            newlp.setDeleteStatue(true);
            newlp.setSort(i + 1);
            lineProcessRepository.save(newlp);
        }
        return lineBody;
    }
}
