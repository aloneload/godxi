package godxi.service;

import godxi.domain.Pavilion;
import godxi.repository.PavilionRepository;
import godxi.service.dto.PavilionDTO;
import godxi.service.mapper.PavilionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing Pavilion.
 */
@Service
@Transactional
public class PavilionService {

    private final Logger log = LoggerFactory.getLogger(PavilionService.class);

    private final PavilionRepository pavilionRepository;

    private final PavilionMapper pavilionMapper;

    public PavilionService(PavilionRepository pavilionRepository, PavilionMapper pavilionMapper) {
        this.pavilionRepository = pavilionRepository;
        this.pavilionMapper = pavilionMapper;
    }

    /**
     * Save a pavilion.
     *
     * @param pavilionDTO the entity to save
     * @return the persisted entity
     */
    public PavilionDTO save(PavilionDTO pavilionDTO) {
        log.debug("Request to save Pavilion : {}", pavilionDTO);
        Pavilion pavilion = pavilionMapper.toEntity(pavilionDTO);
        pavilion = pavilionRepository.save(pavilion);
        return pavilionMapper.toDto(pavilion);
    }

    /**
     * Get all the pavilions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<PavilionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Pavilions");
        return pavilionRepository.findAll(pageable)
            .map(pavilionMapper::toDto);
    }


    /**
     * Get one pavilion by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<PavilionDTO> findOne(Long id) {
        log.debug("Request to get Pavilion : {}", id);
        return pavilionRepository.findById(id)
            .map(pavilionMapper::toDto);
    }

    /**
     * Delete the pavilion by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Pavilion : {}", id);
        pavilionRepository.deleteById(id);
    }
}
