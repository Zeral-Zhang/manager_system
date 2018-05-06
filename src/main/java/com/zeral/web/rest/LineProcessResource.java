package com.zeral.web.rest;
import com.zeral.domain.LineBody;
import com.zeral.domain.LineProcess;
import com.zeral.repository.LineProcessRepository;
import com.zeral.service.LineProcessService;
import com.zeral.web.rest.errors.BadRequestAlertException;
import com.zeral.web.rest.util.HeaderUtil;
import com.zeral.web.rest.util.PaginationUtil;
import com.zeral.web.rest.vm.LineBodyAndProcessVM;
import com.zeral.web.rest.vm.RoleMenuVM;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class LineProcessResource {
    private final Logger log = LoggerFactory.getLogger(WorkplanResource.class);
    private static final String ENTITY_NAME = "LineProcess";
    private final Boolean NO_DELETE = true;
    private final LineProcessService lineProcessService;
    private final LineProcessRepository lineProcessRepository;
    public LineProcessResource(LineProcessService lineProcessService, LineProcessRepository lineProcessRepository) {
        this.lineProcessService = lineProcessService;
        this.lineProcessRepository = lineProcessRepository;
    }
    @GetMapping("/lineProcess")
    public ResponseEntity<List<LineProcess>> findAll(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to get a page of LineProcess");
        final Page<LineProcess> page = lineProcessService.findAllLineProcess(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/lineProcess");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
    @PostMapping("lineProcess")
    public ResponseEntity<LineProcess> addLineProcess(@Valid @RequestBody List<LineProcess> lineProcesses)
        throws URISyntaxException {
        log.debug("REST request to save LineProcess : {}", lineProcesses);
        for(int i = 0; i < lineProcesses.size(); i++) {
            LineProcess tempLineProcess = lineProcesses.get(i);
            tempLineProcess.setDeleteStatue(NO_DELETE);
            tempLineProcess.setSort(i+1);
            lineProcessService.save(tempLineProcess);
        }
        LineProcess result = lineProcesses.get(0);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, result.getLineId().toString()))
            .body(result);
    }
    @PutMapping("lineProcess")
    public ResponseEntity<LineBody> updateLineBody(@Valid @RequestBody LineBodyAndProcessVM lineBodyAndProcessVM) throws URISyntaxException {
        log.debug("REST request to update LineBody : {}", lineBodyAndProcessVM);
        Optional<LineBody> newLineBody = lineProcessService.updateLineProcess(lineBodyAndProcessVM);
        return ResponseUtil.wrapOrNotFound(newLineBody,
            HeaderUtil.createAlert("线体更新成功", newLineBody.get().getName()));
    }
}
