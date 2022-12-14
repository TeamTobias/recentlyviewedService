package com.tobias.recentlyviewedservice.web.rest;

import com.tobias.recentlyviewedservice.repository.RecentlyViewedItemRepository;
import com.tobias.recentlyviewedservice.service.RecentlyViewedItemService;
import com.tobias.recentlyviewedservice.service.dto.RecentlyViewedItemDTO;
import com.tobias.recentlyviewedservice.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.tobias.recentlyviewedservice.domain.RecentlyViewedItem}.
 */
@RestController
@RequestMapping("/api")
public class RecentlyViewedItemResource {

    private final Logger log = LoggerFactory.getLogger(RecentlyViewedItemResource.class);

    private static final String ENTITY_NAME = "recentlyviewedServiceRecentlyViewedItem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RecentlyViewedItemService recentlyViewedItemService;

    private final RecentlyViewedItemRepository recentlyViewedItemRepository;

    public RecentlyViewedItemResource(
        RecentlyViewedItemService recentlyViewedItemService,
        RecentlyViewedItemRepository recentlyViewedItemRepository
    ) {
        this.recentlyViewedItemService = recentlyViewedItemService;
        this.recentlyViewedItemRepository = recentlyViewedItemRepository;
    }

    /**
     * {@code POST  /recently-viewed-items} : Create a new recentlyViewedItem.
     *
     * @param recentlyViewedItemDTO the recentlyViewedItemDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new recentlyViewedItemDTO, or with status {@code 400 (Bad Request)} if the recentlyViewedItem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/recently-viewed-items")
    public ResponseEntity<RecentlyViewedItemDTO> createRecentlyViewedItem(@RequestBody RecentlyViewedItemDTO recentlyViewedItemDTO)
        throws URISyntaxException {
        log.debug("REST request to save RecentlyViewedItem : {}", recentlyViewedItemDTO);
        if (recentlyViewedItemDTO.getId() != null) {
            throw new BadRequestAlertException("A new recentlyViewedItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        recentlyViewedItemDTO.setId(UUID.randomUUID());
        RecentlyViewedItemDTO result = recentlyViewedItemService.save(recentlyViewedItemDTO);
        return ResponseEntity
            .created(new URI("/api/recently-viewed-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /recently-viewed-items/:id} : Updates an existing recentlyViewedItem.
     *
     * @param id the id of the recentlyViewedItemDTO to save.
     * @param recentlyViewedItemDTO the recentlyViewedItemDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated recentlyViewedItemDTO,
     * or with status {@code 400 (Bad Request)} if the recentlyViewedItemDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the recentlyViewedItemDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/recently-viewed-items/{id}")
    public ResponseEntity<RecentlyViewedItemDTO> updateRecentlyViewedItem(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody RecentlyViewedItemDTO recentlyViewedItemDTO
    ) throws URISyntaxException {
        log.debug("REST request to update RecentlyViewedItem : {}, {}", id, recentlyViewedItemDTO);
        if (recentlyViewedItemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, recentlyViewedItemDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!recentlyViewedItemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RecentlyViewedItemDTO result = recentlyViewedItemService.update(recentlyViewedItemDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, recentlyViewedItemDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /recently-viewed-items/:id} : Partial updates given fields of an existing recentlyViewedItem, field will ignore if it is null
     *
     * @param id the id of the recentlyViewedItemDTO to save.
     * @param recentlyViewedItemDTO the recentlyViewedItemDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated recentlyViewedItemDTO,
     * or with status {@code 400 (Bad Request)} if the recentlyViewedItemDTO is not valid,
     * or with status {@code 404 (Not Found)} if the recentlyViewedItemDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the recentlyViewedItemDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/recently-viewed-items/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RecentlyViewedItemDTO> partialUpdateRecentlyViewedItem(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody RecentlyViewedItemDTO recentlyViewedItemDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update RecentlyViewedItem partially : {}, {}", id, recentlyViewedItemDTO);
        if (recentlyViewedItemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, recentlyViewedItemDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!recentlyViewedItemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RecentlyViewedItemDTO> result = recentlyViewedItemService.partialUpdate(recentlyViewedItemDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, recentlyViewedItemDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /recently-viewed-items} : get all the recentlyViewedItems.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of recentlyViewedItems in body.
     */
    @GetMapping("/recently-viewed-items")
    public List<RecentlyViewedItemDTO> getAllRecentlyViewedItems() {
        log.debug("REST request to get all RecentlyViewedItems");
        return recentlyViewedItemService.findAll();
    }

    /**
     * {@code GET  /recently-viewed-items/:id} : get the "id" recentlyViewedItem.
     *
     * @param id the id of the recentlyViewedItemDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the recentlyViewedItemDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/recently-viewed-items/{userid}")
    public ResponseEntity<List<RecentlyViewedItemDTO>> getRecentlyViewedItem(@PathVariable Long userid) {
        log.debug("REST request to get RecentlyViewedItem : {}", userid);
        List<RecentlyViewedItemDTO> recentlyViewedItemDTO = recentlyViewedItemService.findAllByUserid(userid);
        return ResponseEntity.ok().body(recentlyViewedItemDTO);
    }

    /**
     * {@code DELETE  /recently-viewed-items/:id} : delete the "id" recentlyViewedItem.
     *
     * @param id the id of the recentlyViewedItemDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/recently-viewed-items/{id}")
    public ResponseEntity<Void> deleteRecentlyViewedItem(@PathVariable UUID id) {
        log.debug("REST request to delete RecentlyViewedItem : {}", id);
        recentlyViewedItemService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
