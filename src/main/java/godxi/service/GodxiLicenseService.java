package godxi.service;

import godxi.domain.GodxiLicense;
import godxi.repository.GodxiLicenseRepository;
import godxi.service.dto.GodxiLicenseDTO;
import godxi.service.mapper.GodxiLicenseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing GodxiLicense.
 */
@Service
@Transactional
public class GodxiLicenseService {

    private final Logger log = LoggerFactory.getLogger(GodxiLicenseService.class);

    private final GodxiLicenseRepository godxiLicenseRepository;

    private final GodxiLicenseMapper godxiLicenseMapper;

    public GodxiLicenseService(GodxiLicenseRepository godxiLicenseRepository, GodxiLicenseMapper godxiLicenseMapper) {
        this.godxiLicenseRepository = godxiLicenseRepository;
        this.godxiLicenseMapper = godxiLicenseMapper;
    }

    /**
     * Save a godxiLicense.
     *
     * @param godxiLicenseDTO the entity to save
     * @return the persisted entity
     */
    public GodxiLicenseDTO save(GodxiLicenseDTO godxiLicenseDTO) {
        log.debug("Request to save GodxiLicense : {}", godxiLicenseDTO);
        GodxiLicense godxiLicense = godxiLicenseMapper.toEntity(godxiLicenseDTO);
        godxiLicense = godxiLicenseRepository.save(godxiLicense);
        return godxiLicenseMapper.toDto(godxiLicense);
    }

    /**
     * Get all the godxiLicenses.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<GodxiLicenseDTO> findAll(Pageable pageable) {
        log.debug("Request to get all GodxiLicenses");
        return godxiLicenseRepository.findAll(pageable)
            .map(godxiLicenseMapper::toDto);
    }


    /**
     * Get one godxiLicense by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<GodxiLicenseDTO> findOne(Long id) {
        log.debug("Request to get GodxiLicense : {}", id);
        return godxiLicenseRepository.findById(id)
            .map(godxiLicenseMapper::toDto);
    }

    /**
     * Delete the godxiLicense by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete GodxiLicense : {}", id);
        godxiLicenseRepository.deleteById(id);
    }
}
