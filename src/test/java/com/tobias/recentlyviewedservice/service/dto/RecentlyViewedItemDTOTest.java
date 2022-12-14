package com.tobias.recentlyviewedservice.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.tobias.recentlyviewedservice.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class RecentlyViewedItemDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RecentlyViewedItemDTO.class);
        RecentlyViewedItemDTO recentlyViewedItemDTO1 = new RecentlyViewedItemDTO();
        recentlyViewedItemDTO1.setId(UUID.randomUUID());
        RecentlyViewedItemDTO recentlyViewedItemDTO2 = new RecentlyViewedItemDTO();
        assertThat(recentlyViewedItemDTO1).isNotEqualTo(recentlyViewedItemDTO2);
        recentlyViewedItemDTO2.setId(recentlyViewedItemDTO1.getId());
        assertThat(recentlyViewedItemDTO1).isEqualTo(recentlyViewedItemDTO2);
        recentlyViewedItemDTO2.setId(UUID.randomUUID());
        assertThat(recentlyViewedItemDTO1).isNotEqualTo(recentlyViewedItemDTO2);
        recentlyViewedItemDTO1.setId(null);
        assertThat(recentlyViewedItemDTO1).isNotEqualTo(recentlyViewedItemDTO2);
    }
}
