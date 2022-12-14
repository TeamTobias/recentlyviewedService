package com.tobias.recentlyviewedservice.service;

import com.tobias.recentlyviewedservice.service.dto.RecentlyViewedItemDTO;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service Interface for managing {@link com.tobias.recentlyviewedservice.domain.RecentlyViewedItem}.
 */
public interface RecentlyViewedItemService {
    /**
     * Save a recentlyViewedItem.
     *
     * @param recentlyViewedItemDTO the entity to save.
     * @return the persisted entity.
     */
    RecentlyViewedItemDTO save(RecentlyViewedItemDTO recentlyViewedItemDTO);

    /**
     * Updates a recentlyViewedItem.
     *
     * @param recentlyViewedItemDTO the entity to update.
     * @return the persisted entity.
     */
    RecentlyViewedItemDTO update(RecentlyViewedItemDTO recentlyViewedItemDTO);

    /**
     * Partially updates a recentlyViewedItem.
     *
     * @param recentlyViewedItemDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<RecentlyViewedItemDTO> partialUpdate(RecentlyViewedItemDTO recentlyViewedItemDTO);

    /**
     * Get all the recentlyViewedItems.
     *
     * @return the list of entities.
     */
    List<RecentlyViewedItemDTO> findAll();

    /**
     * Get the "id" recentlyViewedItem.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RecentlyViewedItemDTO> findOne(UUID id);

    List<RecentlyViewedItemDTO> findAllByUserid(Long userId);
    /**
     * Delete the "id" recentlyViewedItem.
     *
     * @param id the id of the entity.
     */
    void delete(UUID id);
}
