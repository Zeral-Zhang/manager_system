package com.zeral.web.rest;

import com.zeral.domain.LineBody;
import com.zeral.domain.ProjectType;
import com.zeral.repository.LineBodyRepository;
import com.zeral.repository.LineProcessRepository;
import com.zeral.service.LineBodyService;
import com.zeral.web.rest.errors.BadRequestAlertException;
import com.zeral.web.rest.util.HeaderUtil;
import com.zeral.web.rest.util.PaginationUtil;
import com.zeral.web.rest.vm.LineBodyAndProcessVM;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class LineBodyResource {
    private final Logger log = LoggerFactory.getLogger(WorkplanResource.class);
    public final String ENTITY_NAME = "linebody";
    private final Boolean NO_DELETE = true;
    private final LineBodyService lineBodyService;
    private final LineProcessRepository lineProcessRepository;
    private final LineBodyRepository lineBodyRepository;
    public LineBodyResource(LineBodyService lineBodyService,
                            LineProcessRepository lineProcessRepository,
                            LineBodyRepository lineBodyRepository) {
        this.lineBodyService = lineBodyService;
        this.lineProcessRepository = lineProcessRepository;
        this.lineBodyRepository = lineBodyRepository;
    }
    @GetMapping("/lines")
    public ResponseEntity<List<LineBody>> findAll(@RequestParam String query, Pageable pageable) {
         log.debug("REST request to get a page of LineBody");
        String name = '%' + query + '%';
        final Page<LineBody> page = lineBodyService.findAllLineBody(name, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/lines");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
    @PutMapping("line")
    public ResponseEntity<LineBody> updateLineBody(@Valid @RequestBody LineBody lineBody) throws URISyntaxException {
        log.debug("REST request to update LineBody : {}", lineBody);
        Optional<LineBody> existingLineBody =lineBodyRepository.findByNameIgnoreCase(lineBody.getName());
        if(existingLineBody.isPresent() && existingLineBody.get().getName().equals(lineBody.getName())) {
            throw new BadRequestAlertException("线体名称已被使用", ENTITY_NAME, "nameExists");
        }
//        Optional<LineBody> existingLineBody1 =lineBodyRepository.findByCodeIgnoreCase(lineBodyAndProcessVM.getLineBody().getCode());
//        if(existingLineBody1.isPresent() && existingLineBody1.get().getCode().equals(lineBodyAndProcessVM.getLineBody().getCode())) {
//            throw new BadRequestAlertException("线体编号已被使用", ENTITY_NAME, "codeExists");
//        }
        Optional<LineBody> newLineBody = lineBodyService.updateLineBody(lineBody);
        return ResponseUtil.wrapOrNotFound(newLineBody,
            HeaderUtil.createAlert("线体更新成功", newLineBody.get().getName()));
    }
    @PostMapping("line")
    public ResponseEntity<LineBody> addLineBody(@Valid @RequestBody LineBody lineBody) throws URISyntaxException {
        log.debug("REST request to save LineBody : {}", lineBody);
//        if(lineBodyRepository.findByCodeIgnoreCase(lineBody.getCode()).isPresent()) {
//            throw new BadRequestAlertException("线体编号已被使用！", ENTITY_NAME, "codeExists");
//        }
        if(lineBodyRepository.findByNameIgnoreCase(lineBody.getName()).isPresent()) {
            throw new BadRequestAlertException("线体名称已被使用", ENTITY_NAME, "codeExists");
        }
        lineBody.setDeleteStatue(NO_DELETE);
        LineBody result = lineBodyService.save(lineBody);
        return ResponseEntity.created(new URI("/api/line/" + result.getName()))
            .headers(HeaderUtil.createAlert("新增线体成功！", result.getName()))
            .body(result);
    }

    @DeleteMapping("/line/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable String id) {
        log.debug("REST request to delete LineBody : {}", id);
        Optional<LineBody> result = lineBodyService.deleteLineBody(Long.valueOf(id));
        return ResponseEntity.ok().headers(HeaderUtil.createAlert( "删除用户成功", result.get().getName())).build();
    }
}
