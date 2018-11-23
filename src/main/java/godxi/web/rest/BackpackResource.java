package godxi.web.rest;

import com.codahale.metrics.annotation.Timed;
import godxi.service.BackpackService;
import godxi.web.rest.errors.BadRequestAlertException;
import godxi.web.rest.util.HeaderUtil;
import godxi.web.rest.util.PaginationUtil;
import godxi.service.dto.BackpackDTO;
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
 * REST controller for managing Backpack.
 */
@RestController
@RequestMapping("/api")
public class BackpackResource {

    private final Logger log = LoggerFactory.getLogger(BackpackResource.class);

    private static final String ENTITY_NAME = "backpack";

    private final BackpackService backpackService;

    public BackpackResource(BackpackService backpackService) {
        this.backpackService = backpackService;
    }

    /**
     * POST  /backpacks : Create a new backpack.
     *
     * @param backpackDTO the backpackDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new backpackDTO, or with status 400 (Bad Request) if the backpack has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/backpacks")
    @Timed
    public ResponseEntity<BackpackDTO> createBackpack(@Valid @RequestBody BackpackDTO backpackDTO) throws URISyntaxException {
        log.debug("REST request to save Backpack : {}", backpackDTO);
        if (backpackDTO.getId() != null) {
            throw new BadRequestAlertException("A new backpack cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BackpackDTO result = backpackService.save(backpackDTO);
        return ResponseEntity.created(new URI("/api/backpacks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /backpacks : Updates an existing backpack.
     *
     * @param backpackDTO the backpackDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated backpackDTO,
     * or with status 400 (Bad Request) if the backpackDTO is not valid,
     * or with status 500 (Internal Server Error) if the backpackDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/backpacks")
    @Timed
    public ResponseEntity<BackpackDTO> updateBackpack(@Valid @RequestBody BackpackDTO backpackDTO) throws URISyntaxException {
        log.debug("REST request to update Backpack : {}", backpackDTO);
        if (backpackDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BackpackDTO result = backpackService.save(backpackDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, backpackDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /backpacks : get all the backpacks.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of backpacks in body
     */
    @GetMapping("/backpacks")
    @Timed
    public ResponseEntity<List<BackpackDTO>> getAllBackpacks(Pageable pageable) {
        log.debug("REST request to get a page of Backpacks");
        Page<BackpackDTO> page = backpackService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/backpacks");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /backpacks/:id : get the "id" backpack.
     *
     * @param id the id of the backpackDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the backpackDTO, or with status 404 (Not Found)
     */
    @GetMapping("/backpacks/{id}")
    @Timed
    public ResponseEntity<BackpackDTO> getBackpack(@PathVariable Long id) {
        log.debug("REST request to get Backpack : {}", id);
        Optional<BackpackDTO> backpackDTO = backpackService.findOne(id);
        return ResponseUtil.wrapOrNotFound(backpackDTO);
    }

    /**
     * DELETE  /backpacks/:id : delete the "id" backpack.
     *
     * @param id the id of the backpackDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/backpacks/{id}")
    @Timed
    public ResponseEntity<Void> deleteBackpack(@PathVariable Long id) {
        log.debug("REST request to delete Backpack : {}", id);
        backpackService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
