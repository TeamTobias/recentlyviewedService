package com.tobias.recentlyviewedservice.service.impl;

import com.tobias.recentlyviewedservice.domain.RecentlyViewedItem;
import com.tobias.recentlyviewedservice.repository.RecentlyViewedItemRepository;
import com.tobias.recentlyviewedservice.service.RecentlyViewedItemService;
import com.tobias.recentlyviewedservice.service.dto.RecentlyViewedItemDTO;
import com.tobias.recentlyviewedservice.service.mapper.RecentlyViewedItemMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link RecentlyViewedItem}.
 */
@Service
public class RecentlyViewedItemServiceImpl implements RecentlyViewedItemService {

    private final Logger log = LoggerFactory.getLogger(RecentlyViewedItemServiceImpl.class);

    private final RecentlyViewedItemRepository recentlyViewedItemRepository;

    private final RecentlyViewedItemMapper recentlyViewedItemMapper;

    public RecentlyViewedItemServiceImpl(
        RecentlyViewedItemRepository recentlyViewedItemRepository,
        RecentlyViewedItemMapper recentlyViewedItemMapper
    ) {
        this.recentlyViewedItemRepository = recentlyViewedItemRepository;
        this.recentlyViewedItemMapper = recentlyViewedItemMapper;
    }

    @Override
    public RecentlyViewedItemDTO save(RecentlyViewedItemDTO recentlyViewedItemDTO) {
        log.debug("Request to save RecentlyViewedItem : {}", recentlyViewedItemDTO);
        RecentlyViewedItem recentlyViewedItem = recentlyViewedItemMapper.toEntity(recentlyViewedItemDTO);
        recentlyViewedItem = recentlyViewedItemRepository.save(recentlyViewedItem);
        return recentlyViewedItemMapper.toDto(recentlyViewedItem);
    }

    @Override
    public RecentlyViewedItemDTO update(RecentlyViewedItemDTO recentlyViewedItemDTO) {
        log.debug("Request to update RecentlyViewedItem : {}", recentlyViewedItemDTO);
        RecentlyViewedItem recentlyViewedItem = recentlyViewedItemMapper.toEntity(recentlyViewedItemDTO);
        recentlyViewedItem = recentlyViewedItemRepository.save(recentlyViewedItem);
        return recentlyViewedItemMapper.toDto(recentlyViewedItem);
    }

    @Override
    public Optional<RecentlyViewedItemDTO> partialUpdate(RecentlyViewedItemDTO recentlyViewedItemDTO) {
        log.debug("Request to partially update RecentlyViewedItem : {}", recentlyViewedItemDTO);

        return recentlyViewedItemRepository
            .findById(recentlyViewedItemDTO.getId())
            .map(existingRecentlyViewedItem -> {
                recentlyViewedItemMapper.partialUpdate(existingRecentlyViewedItem, recentlyViewedItemDTO);

                return existingRecentlyViewedItem;
            })
            .map(recentlyViewedItemRepository::save)
            .map(recentlyViewedItemMapper::toDto);
    }

    @Override
    public List<RecentlyViewedItemDTO> findAll() {
        log.debug("Request to get all RecentlyViewedItems");
        return recentlyViewedItemRepository
            .findAll()
            .stream()
            .map(recentlyViewedItemMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Optional<RecentlyViewedItemDTO> findOne(UUID id) {
        log.debug("Request to get RecentlyViewedItem : {}", id);
        return recentlyViewedItemRepository.findById(id).map(recentlyViewedItemMapper::toDto);
    }

    @Override
    public void delete(UUID id) {
        log.debug("Request to delete RecentlyViewedItem : {}", id);
        recentlyViewedItemRepository.deleteById(id);
    }
}
