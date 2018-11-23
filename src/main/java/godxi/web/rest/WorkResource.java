package godxi.web.rest;

import com.codahale.metrics.annotation.Timed;
import godxi.service.WorkService;
import godxi.web.rest.errors.BadRequestAlertException;
import godxi.web.rest.util.HeaderUtil;
import godxi.web.rest.util.PaginationUtil;
import godxi.service.dto.WorkDTO;
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

/**
 * REST controller for managing Work.
 */
@RestController
@RequestMapping("/api")
public class WorkResource {

    private final Logger log = LoggerFactory.getLogger(WorkResource.class);

    private static final String ENTITY_NAME = "work";

    private final WorkService workService;

    public WorkResource(WorkService workService) {
        this.workService = workService;
    }

    /**
     * POST  /works : Create a new work.
     *
     * @param workDTO the workDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new workDTO, or with status 400 (Bad Request) if the work has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/works")
    @Timed
    public ResponseEntity<WorkDTO> createWork(@Valid @RequestBody WorkDTO workDTO) throws URISyntaxException {
        log.debug("REST request to save Work : {}", workDTO);
        if (workDTO.getId() != null) {
            throw new BadRequestAlertException("A new work cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WorkDTO result = workService.save(workDTO);
        return ResponseEntity.created(new URI("/api/works/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /works : Updates an existing work.
     *
     * @param workDTO the workDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated workDTO,
     * or with status 400 (Bad Request) if the workDTO is not valid,
     * or with status 500 (Internal Server Error) if the workDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/works")
    @Timed
    public ResponseEntity<WorkDTO> updateWork(@Valid @RequestBody WorkDTO workDTO) throws URISyntaxException {
        log.debug("REST request to update Work : {}", workDTO);
        if (workDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        WorkDTO result = workService.save(workDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, workDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /works : get all the works.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of works in body
     */
    @GetMapping("/works")
    @Timed
    public ResponseEntity<List<WorkDTO>> getAllWorks(Pageable pageable) {
        log.debug("REST request to get a page of Works");
        Page<WorkDTO> page = workService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/works");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /works/:id : get the "id" work.
     *
     * @param id the id of the workDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the workDTO, or with status 404 (Not Found)
     */
    @GetMapping("/works/{id}")
    @Timed
    public ResponseEntity<WorkDTO> getWork(@PathVariable Long id) {
        log.debug("REST request to get Work : {}", id);
        Optional<WorkDTO> workDTO = workService.findOne(id);
        return ResponseUtil.wrapOrNotFound(workDTO);
    }

    /**
     * DELETE  /works/:id : delete the "id" work.
     *
     * @param id the id of the workDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/works/{id}")
    @Timed
    public ResponseEntity<Void> deleteWork(@PathVariable Long id) {
        log.debug("REST request to delete Work : {}", id);
        workService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
