package com.tobias.recentlyviewedservice.repository;

import com.tobias.recentlyviewedservice.domain.RecentlyViewedItem;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data Cassandra repository for the RecentlyViewedItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RecentlyViewedItemRepository extends CassandraRepository<RecentlyViewedItem, UUID> {
    @AllowFiltering
    List<RecentlyViewedItem> findAllByUserid(Long userId);
}
