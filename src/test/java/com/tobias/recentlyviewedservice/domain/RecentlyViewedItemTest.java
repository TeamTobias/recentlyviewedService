package com.tobias.recentlyviewedservice.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.tobias.recentlyviewedservice.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class RecentlyViewedItemTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RecentlyViewedItem.class);
        RecentlyViewedItem recentlyViewedItem1 = new RecentlyViewedItem();
        recentlyViewedItem1.setId(UUID.randomUUID());
        RecentlyViewedItem recentlyViewedItem2 = new RecentlyViewedItem();
        recentlyViewedItem2.setId(recentlyViewedItem1.getId());
        assertThat(recentlyViewedItem1).isEqualTo(recentlyViewedItem2);
        recentlyViewedItem2.setId(UUID.randomUUID());
        assertThat(recentlyViewedItem1).isNotEqualTo(recentlyViewedItem2);
        recentlyViewedItem1.setId(null);
        assertThat(recentlyViewedItem1).isNotEqualTo(recentlyViewedItem2);
    }
}
