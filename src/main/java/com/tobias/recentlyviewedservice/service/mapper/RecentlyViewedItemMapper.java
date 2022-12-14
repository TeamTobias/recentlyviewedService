package com.tobias.recentlyviewedservice.service.mapper;

import com.tobias.recentlyviewedservice.domain.RecentlyViewedItem;
import com.tobias.recentlyviewedservice.service.dto.RecentlyViewedItemDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link RecentlyViewedItem} and its DTO {@link RecentlyViewedItemDTO}.
 */
@Mapper(componentModel = "spring")
public interface RecentlyViewedItemMapper extends EntityMapper<RecentlyViewedItemDTO, RecentlyViewedItem> {}
