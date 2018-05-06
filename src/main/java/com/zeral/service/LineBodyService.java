package com.zeral.service;

import com.zeral.domain.LineBody;
import com.zeral.domain.LineProcess;
import com.zeral.domain.ProjectType;
import com.zeral.repository.LineBodyRepository;
import com.zeral.repository.LineProcessRepository;
import com.zeral.web.rest.vm.LineBodyAndProcessVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class LineBodyService {
    private final Logger log = LoggerFactory.getLogger(WorkPlanService.class);
    private final Boolean IS_DELETE = false;
    private final LineBodyRepository lineBodyRepository;
    private final LineProcessRepository lineProcessRepository;
    public LineBodyService(LineBodyRepository lineBodyRepository, LineProcessRepository lineProcessRepository) {
        this.lineBodyRepository = lineBodyRepository;
        this.lineProcessRepository = lineProcessRepository;
    }
    @Transactional(readOnly = true)
    public Page<LineBody> findAllLineBody(String query, Pageable pageable) {
        return lineBodyRepository.findAllByDeleteStatueIsTrueAndNameLike(query, pageable);
    }
    public LineBody save(LineBody lineBody) {
        log.debug("Request to save LineBody : {}", lineBody);
        return lineBodyRepository.save(lineBody);
    }
    public Optional<LineBody> updateLineBody(LineBody LineBody) {
//       List<LineProcess> lineProcess = lineBodyAndProcessVM.getLineProcesses();
//       if(lineProcess.size() > 0) {
//          List<LineProcess> lineProcessList = lineProcessRepository.findAllByDeleteStatueIsTrueAndLineId(lineBodyAndProcessVM.getLineBody().getId());
//          for(LineProcess oldLp: lineProcessList) {
//              LineProcess lineProcess1 = lineProcessRepository.findByLineIdAndProcessId(oldLp.getLineId(), oldLp.getProcessId());
//              lineProcessRepository.delete(lineProcess1.getId());
//          }
//           for(LineProcess newLp: lineBodyAndProcessVM.getLineProcesses()) {
//               newLp.setDeleteStatue(true);
//               lineProcessRepository.save(newLp);
//           }
//       }
        return Optional.of(lineBodyRepository.findOne(LineBody.getId())).map(lineBodyMain -> {
            lineBodyMain.setName(LineBody.getName());
            return lineBodyMain;
        });
    }
    /**
     * 逻辑删除
     * */
    public Optional<LineBody> deleteLineBody(Long id) {
        return Optional.of(lineBodyRepository.findById(id)).map(lineBodyMain -> {
            lineBodyMain.setDeleteStatue(IS_DELETE);
            return lineBodyMain;
        });
    }
}
