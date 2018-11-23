package godxi.web.rest;

import com.codahale.metrics.annotation.Timed;
import godxi.service.GodxiLicenseService;
import godxi.web.rest.errors.BadRequestAlertException;
import godxi.web.rest.util.HeaderUtil;
import godxi.web.rest.util.PaginationUtil;
import godxi.service.dto.GodxiLicenseDTO;
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
 * REST controller for managing GodxiLicense.
 */
@RestController
@RequestMapping("/api")
public class GodxiLicenseResource {

    private final Logger log = LoggerFactory.getLogger(GodxiLicenseResource.class);

    private static final String ENTITY_NAME = "godxiLicense";

    private final GodxiLicenseService godxiLicenseService;

    public GodxiLicenseResource(GodxiLicenseService godxiLicenseService) {
        this.godxiLicenseService = godxiLicenseService;
    }

    /**
     * POST  /godxi-licenses : Create a new godxiLicense.
     *
     * @param godxiLicenseDTO the godxiLicenseDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new godxiLicenseDTO, or with status 400 (Bad Request) if the godxiLicense has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/godxi-licenses")
    @Timed
    public ResponseEntity<GodxiLicenseDTO> createGodxiLicense(@Valid @RequestBody GodxiLicenseDTO godxiLicenseDTO) throws URISyntaxException {
        log.debug("REST request to save GodxiLicense : {}", godxiLicenseDTO);
        if (godxiLicenseDTO.getId() != null) {
            throw new BadRequestAlertException("A new godxiLicense cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GodxiLicenseDTO result = godxiLicenseService.save(godxiLicenseDTO);
        return ResponseEntity.created(new URI("/api/godxi-licenses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /godxi-licenses : Updates an existing godxiLicense.
     *
     * @param godxiLicenseDTO the godxiLicenseDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated godxiLicenseDTO,
     * or with status 400 (Bad Request) if the godxiLicenseDTO is not valid,
     * or with status 500 (Internal Server Error) if the godxiLicenseDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/godxi-licenses")
    @Timed
    public ResponseEntity<GodxiLicenseDTO> updateGodxiLicense(@Valid @RequestBody GodxiLicenseDTO godxiLicenseDTO) throws URISyntaxException {
        log.debug("REST request to update GodxiLicense : {}", godxiLicenseDTO);
        if (godxiLicenseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GodxiLicenseDTO result = godxiLicenseService.save(godxiLicenseDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, godxiLicenseDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /godxi-licenses : get all the godxiLicenses.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of godxiLicenses in body
     */
    @GetMapping("/godxi-licenses")
    @Timed
    public ResponseEntity<List<GodxiLicenseDTO>> getAllGodxiLicenses(Pageable pageable) {
        log.debug("REST request to get a page of GodxiLicenses");
        Page<GodxiLicenseDTO> page = godxiLicenseService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/godxi-licenses");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /godxi-licenses/:id : get the "id" godxiLicense.
     *
     * @param id the id of the godxiLicenseDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the godxiLicenseDTO, or with status 404 (Not Found)
     */
    @GetMapping("/godxi-licenses/{id}")
    @Timed
    public ResponseEntity<GodxiLicenseDTO> getGodxiLicense(@PathVariable Long id) {
        log.debug("REST request to get GodxiLicense : {}", id);
        Optional<GodxiLicenseDTO> godxiLicenseDTO = godxiLicenseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(godxiLicenseDTO);
    }

    /**
     * DELETE  /godxi-licenses/:id : delete the "id" godxiLicense.
     *
     * @param id the id of the godxiLicenseDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/godxi-licenses/{id}")
    @Timed
    public ResponseEntity<Void> deleteGodxiLicense(@PathVariable Long id) {
        log.debug("REST request to delete GodxiLicense : {}", id);
        godxiLicenseService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
