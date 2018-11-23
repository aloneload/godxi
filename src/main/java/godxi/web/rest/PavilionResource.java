package godxi.web.rest;

import com.codahale.metrics.annotation.Timed;
import godxi.service.PavilionService;
import godxi.web.rest.errors.BadRequestAlertException;
import godxi.web.rest.util.HeaderUtil;
import godxi.web.rest.util.PaginationUtil;
import godxi.service.dto.PavilionDTO;
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
 * REST controller for managing Pavilion.
 */
@RestController
@RequestMapping("/api")
public class PavilionResource {

    private final Logger log = LoggerFactory.getLogger(PavilionResource.class);

    private static final String ENTITY_NAME = "pavilion";

    private final PavilionService pavilionService;

    public PavilionResource(PavilionService pavilionService) {
        this.pavilionService = pavilionService;
    }

    /**
     * POST  /pavilions : Create a new pavilion.
     *
     * @param pavilionDTO the pavilionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pavilionDTO, or with status 400 (Bad Request) if the pavilion has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/pavilions")
    @Timed
    public ResponseEntity<PavilionDTO> createPavilion(@Valid @RequestBody PavilionDTO pavilionDTO) throws URISyntaxException {
        log.debug("REST request to save Pavilion : {}", pavilionDTO);
        if (pavilionDTO.getId() != null) {
            throw new BadRequestAlertException("A new pavilion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PavilionDTO result = pavilionService.save(pavilionDTO);
        return ResponseEntity.created(new URI("/api/pavilions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pavilions : Updates an existing pavilion.
     *
     * @param pavilionDTO the pavilionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pavilionDTO,
     * or with status 400 (Bad Request) if the pavilionDTO is not valid,
     * or with status 500 (Internal Server Error) if the pavilionDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/pavilions")
    @Timed
    public ResponseEntity<PavilionDTO> updatePavilion(@Valid @RequestBody PavilionDTO pavilionDTO) throws URISyntaxException {
        log.debug("REST request to update Pavilion : {}", pavilionDTO);
        if (pavilionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PavilionDTO result = pavilionService.save(pavilionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, pavilionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pavilions : get all the pavilions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of pavilions in body
     */
    @GetMapping("/pavilions")
    @Timed
    public ResponseEntity<List<PavilionDTO>> getAllPavilions(Pageable pageable) {
        log.debug("REST request to get a page of Pavilions");
        Page<PavilionDTO> page = pavilionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/pavilions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /pavilions/:id : get the "id" pavilion.
     *
     * @param id the id of the pavilionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pavilionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/pavilions/{id}")
    @Timed
    public ResponseEntity<PavilionDTO> getPavilion(@PathVariable Long id) {
        log.debug("REST request to get Pavilion : {}", id);
        Optional<PavilionDTO> pavilionDTO = pavilionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pavilionDTO);
    }

    /**
     * DELETE  /pavilions/:id : delete the "id" pavilion.
     *
     * @param id the id of the pavilionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/pavilions/{id}")
    @Timed
    public ResponseEntity<Void> deletePavilion(@PathVariable Long id) {
        log.debug("REST request to delete Pavilion : {}", id);
        pavilionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
