package godxi.service;

import godxi.domain.Work;
import godxi.repository.WorkRepository;
import godxi.service.dto.WorkDTO;
import godxi.service.mapper.WorkMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing Work.
 */
@Service
@Transactional
public class WorkService {

    private final Logger log = LoggerFactory.getLogger(WorkService.class);

    private final WorkRepository workRepository;

    private final WorkMapper workMapper;

    public WorkService(WorkRepository workRepository, WorkMapper workMapper) {
        this.workRepository = workRepository;
        this.workMapper = workMapper;
    }

    /**
     * Save a work.
     *
     * @param workDTO the entity to save
     * @return the persisted entity
     */
    public WorkDTO save(WorkDTO workDTO) {
        log.debug("Request to save Work : {}", workDTO);
        Work work = workMapper.toEntity(workDTO);
        work = workRepository.save(work);
        return workMapper.toDto(work);
    }

    /**
     * Get all the works.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<WorkDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Works");
        return workRepository.findAll(pageable)
            .map(workMapper::toDto);
    }


    /**
     * Get one work by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<WorkDTO> findOne(Long id) {
        log.debug("Request to get Work : {}", id);
        return workRepository.findById(id)
            .map(workMapper::toDto);
    }

    /**
     * Delete the work by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Work : {}", id);
        workRepository.deleteById(id);
    }
}
