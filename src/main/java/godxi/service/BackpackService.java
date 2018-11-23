package godxi.service;

import godxi.domain.Backpack;
import godxi.repository.BackpackRepository;
import godxi.service.dto.BackpackDTO;
import godxi.service.mapper.BackpackMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing Backpack.
 */
@Service
@Transactional
public class BackpackService {

    private final Logger log = LoggerFactory.getLogger(BackpackService.class);

    private final BackpackRepository backpackRepository;

    private final BackpackMapper backpackMapper;

    public BackpackService(BackpackRepository backpackRepository, BackpackMapper backpackMapper) {
        this.backpackRepository = backpackRepository;
        this.backpackMapper = backpackMapper;
    }

    /**
     * Save a backpack.
     *
     * @param backpackDTO the entity to save
     * @return the persisted entity
     */
    public BackpackDTO save(BackpackDTO backpackDTO) {
        log.debug("Request to save Backpack : {}", backpackDTO);
        Backpack backpack = backpackMapper.toEntity(backpackDTO);
        backpack = backpackRepository.save(backpack);
        return backpackMapper.toDto(backpack);
    }

    /**
     * Get all the backpacks.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<BackpackDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Backpacks");
        return backpackRepository.findAll(pageable)
            .map(backpackMapper::toDto);
    }


    /**
     * Get one backpack by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<BackpackDTO> findOne(Long id) {
        log.debug("Request to get Backpack : {}", id);
        return backpackRepository.findById(id)
            .map(backpackMapper::toDto);
    }

    /**
     * Delete the backpack by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Backpack : {}", id);
        backpackRepository.deleteById(id);
    }
}
