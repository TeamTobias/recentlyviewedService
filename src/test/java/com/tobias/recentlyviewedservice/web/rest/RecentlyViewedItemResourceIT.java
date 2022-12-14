package com.tobias.recentlyviewedservice.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.tobias.recentlyviewedservice.IntegrationTest;
import com.tobias.recentlyviewedservice.domain.RecentlyViewedItem;
import com.tobias.recentlyviewedservice.repository.RecentlyViewedItemRepository;
import com.tobias.recentlyviewedservice.service.dto.RecentlyViewedItemDTO;
import com.tobias.recentlyviewedservice.service.mapper.RecentlyViewedItemMapper;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for the {@link RecentlyViewedItemResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RecentlyViewedItemResourceIT {

    private static final Long DEFAULT_USERID = 1L;
    private static final Long UPDATED_USERID = 2L;

    private static final Long DEFAULT_CATALOGID = 1L;
    private static final Long UPDATED_CATALOGID = 2L;

    private static final String DEFAULT_BRANDNAME = "AAAAAAAAAA";
    private static final String UPDATED_BRANDNAME = "BBBBBBBBBB";

    private static final String DEFAULT_CATALOGNAME = "AAAAAAAAAA";
    private static final String UPDATED_CATALOGNAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_CATALOGUNITPRICE = 1;
    private static final Integer UPDATED_CATALOGUNITPRICE = 2;

    private static final String DEFAULT_CATALOGIMG = "AAAAAAAAAA";
    private static final String UPDATED_CATALOGIMG = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/recently-viewed-items";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private RecentlyViewedItemRepository recentlyViewedItemRepository;

    @Autowired
    private RecentlyViewedItemMapper recentlyViewedItemMapper;

    @Autowired
    private MockMvc restRecentlyViewedItemMockMvc;

    private RecentlyViewedItem recentlyViewedItem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RecentlyViewedItem createEntity() {
        RecentlyViewedItem recentlyViewedItem = new RecentlyViewedItem()
            .userid(DEFAULT_USERID)
            .catalogid(DEFAULT_CATALOGID)
            .brandname(DEFAULT_BRANDNAME)
            .catalogname(DEFAULT_CATALOGNAME)
            .catalogunitprice(DEFAULT_CATALOGUNITPRICE)
            .catalogimg(DEFAULT_CATALOGIMG);
        return recentlyViewedItem;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RecentlyViewedItem createUpdatedEntity() {
        RecentlyViewedItem recentlyViewedItem = new RecentlyViewedItem()
            .userid(UPDATED_USERID)
            .catalogid(UPDATED_CATALOGID)
            .brandname(UPDATED_BRANDNAME)
            .catalogname(UPDATED_CATALOGNAME)
            .catalogunitprice(UPDATED_CATALOGUNITPRICE)
            .catalogimg(UPDATED_CATALOGIMG);
        return recentlyViewedItem;
    }

    @BeforeEach
    public void initTest() {
        recentlyViewedItemRepository.deleteAll();
        recentlyViewedItem = createEntity();
    }

    @Test
    void createRecentlyViewedItem() throws Exception {
        int databaseSizeBeforeCreate = recentlyViewedItemRepository.findAll().size();
        // Create the RecentlyViewedItem
        RecentlyViewedItemDTO recentlyViewedItemDTO = recentlyViewedItemMapper.toDto(recentlyViewedItem);
        restRecentlyViewedItemMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(recentlyViewedItemDTO))
            )
            .andExpect(status().isCreated());

        // Validate the RecentlyViewedItem in the database
        List<RecentlyViewedItem> recentlyViewedItemList = recentlyViewedItemRepository.findAll();
        assertThat(recentlyViewedItemList).hasSize(databaseSizeBeforeCreate + 1);
        RecentlyViewedItem testRecentlyViewedItem = recentlyViewedItemList.get(recentlyViewedItemList.size() - 1);
        assertThat(testRecentlyViewedItem.getUserid()).isEqualTo(DEFAULT_USERID);
        assertThat(testRecentlyViewedItem.getCatalogid()).isEqualTo(DEFAULT_CATALOGID);
        assertThat(testRecentlyViewedItem.getBrandname()).isEqualTo(DEFAULT_BRANDNAME);
        assertThat(testRecentlyViewedItem.getCatalogname()).isEqualTo(DEFAULT_CATALOGNAME);
        assertThat(testRecentlyViewedItem.getCatalogunitprice()).isEqualTo(DEFAULT_CATALOGUNITPRICE);
        assertThat(testRecentlyViewedItem.getCatalogimg()).isEqualTo(DEFAULT_CATALOGIMG);
    }

    @Test
    void createRecentlyViewedItemWithExistingId() throws Exception {
        // Create the RecentlyViewedItem with an existing ID
        recentlyViewedItem.setId(UUID.randomUUID());
        RecentlyViewedItemDTO recentlyViewedItemDTO = recentlyViewedItemMapper.toDto(recentlyViewedItem);

        int databaseSizeBeforeCreate = recentlyViewedItemRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRecentlyViewedItemMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(recentlyViewedItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RecentlyViewedItem in the database
        List<RecentlyViewedItem> recentlyViewedItemList = recentlyViewedItemRepository.findAll();
        assertThat(recentlyViewedItemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllRecentlyViewedItems() throws Exception {
        // Initialize the database
        recentlyViewedItem.setId(UUID.randomUUID());
        recentlyViewedItemRepository.save(recentlyViewedItem);

        // Get all the recentlyViewedItemList
        restRecentlyViewedItemMockMvc
            .perform(get(ENTITY_API_URL))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(recentlyViewedItem.getId().toString())))
            .andExpect(jsonPath("$.[*].userid").value(hasItem(DEFAULT_USERID.intValue())))
            .andExpect(jsonPath("$.[*].catalogid").value(hasItem(DEFAULT_CATALOGID.intValue())))
            .andExpect(jsonPath("$.[*].brandname").value(hasItem(DEFAULT_BRANDNAME)))
            .andExpect(jsonPath("$.[*].catalogname").value(hasItem(DEFAULT_CATALOGNAME)))
            .andExpect(jsonPath("$.[*].catalogunitprice").value(hasItem(DEFAULT_CATALOGUNITPRICE)))
            .andExpect(jsonPath("$.[*].catalogimg").value(hasItem(DEFAULT_CATALOGIMG)));
    }

    @Test
    void getRecentlyViewedItem() throws Exception {
        // Initialize the database
        recentlyViewedItem.setId(UUID.randomUUID());
        recentlyViewedItemRepository.save(recentlyViewedItem);

        // Get the recentlyViewedItem
        restRecentlyViewedItemMockMvc
            .perform(get(ENTITY_API_URL_ID, recentlyViewedItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(recentlyViewedItem.getId().toString()))
            .andExpect(jsonPath("$.userid").value(DEFAULT_USERID.intValue()))
            .andExpect(jsonPath("$.catalogid").value(DEFAULT_CATALOGID.intValue()))
            .andExpect(jsonPath("$.brandname").value(DEFAULT_BRANDNAME))
            .andExpect(jsonPath("$.catalogname").value(DEFAULT_CATALOGNAME))
            .andExpect(jsonPath("$.catalogunitprice").value(DEFAULT_CATALOGUNITPRICE))
            .andExpect(jsonPath("$.catalogimg").value(DEFAULT_CATALOGIMG));
    }

    @Test
    void getNonExistingRecentlyViewedItem() throws Exception {
        // Get the recentlyViewedItem
        restRecentlyViewedItemMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    void putExistingRecentlyViewedItem() throws Exception {
        // Initialize the database
        recentlyViewedItem.setId(UUID.randomUUID());
        recentlyViewedItemRepository.save(recentlyViewedItem);

        int databaseSizeBeforeUpdate = recentlyViewedItemRepository.findAll().size();

        // Update the recentlyViewedItem
        RecentlyViewedItem updatedRecentlyViewedItem = recentlyViewedItemRepository.findById(recentlyViewedItem.getId()).get();
        updatedRecentlyViewedItem
            .userid(UPDATED_USERID)
            .catalogid(UPDATED_CATALOGID)
            .brandname(UPDATED_BRANDNAME)
            .catalogname(UPDATED_CATALOGNAME)
            .catalogunitprice(UPDATED_CATALOGUNITPRICE)
            .catalogimg(UPDATED_CATALOGIMG);
        RecentlyViewedItemDTO recentlyViewedItemDTO = recentlyViewedItemMapper.toDto(updatedRecentlyViewedItem);

        restRecentlyViewedItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, recentlyViewedItemDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(recentlyViewedItemDTO))
            )
            .andExpect(status().isOk());

        // Validate the RecentlyViewedItem in the database
        List<RecentlyViewedItem> recentlyViewedItemList = recentlyViewedItemRepository.findAll();
        assertThat(recentlyViewedItemList).hasSize(databaseSizeBeforeUpdate);
        RecentlyViewedItem testRecentlyViewedItem = recentlyViewedItemList.get(recentlyViewedItemList.size() - 1);
        assertThat(testRecentlyViewedItem.getUserid()).isEqualTo(UPDATED_USERID);
        assertThat(testRecentlyViewedItem.getCatalogid()).isEqualTo(UPDATED_CATALOGID);
        assertThat(testRecentlyViewedItem.getBrandname()).isEqualTo(UPDATED_BRANDNAME);
        assertThat(testRecentlyViewedItem.getCatalogname()).isEqualTo(UPDATED_CATALOGNAME);
        assertThat(testRecentlyViewedItem.getCatalogunitprice()).isEqualTo(UPDATED_CATALOGUNITPRICE);
        assertThat(testRecentlyViewedItem.getCatalogimg()).isEqualTo(UPDATED_CATALOGIMG);
    }

    @Test
    void putNonExistingRecentlyViewedItem() throws Exception {
        int databaseSizeBeforeUpdate = recentlyViewedItemRepository.findAll().size();
        recentlyViewedItem.setId(UUID.randomUUID());

        // Create the RecentlyViewedItem
        RecentlyViewedItemDTO recentlyViewedItemDTO = recentlyViewedItemMapper.toDto(recentlyViewedItem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRecentlyViewedItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, recentlyViewedItemDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(recentlyViewedItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RecentlyViewedItem in the database
        List<RecentlyViewedItem> recentlyViewedItemList = recentlyViewedItemRepository.findAll();
        assertThat(recentlyViewedItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchRecentlyViewedItem() throws Exception {
        int databaseSizeBeforeUpdate = recentlyViewedItemRepository.findAll().size();
        recentlyViewedItem.setId(UUID.randomUUID());

        // Create the RecentlyViewedItem
        RecentlyViewedItemDTO recentlyViewedItemDTO = recentlyViewedItemMapper.toDto(recentlyViewedItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRecentlyViewedItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(recentlyViewedItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RecentlyViewedItem in the database
        List<RecentlyViewedItem> recentlyViewedItemList = recentlyViewedItemRepository.findAll();
        assertThat(recentlyViewedItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamRecentlyViewedItem() throws Exception {
        int databaseSizeBeforeUpdate = recentlyViewedItemRepository.findAll().size();
        recentlyViewedItem.setId(UUID.randomUUID());

        // Create the RecentlyViewedItem
        RecentlyViewedItemDTO recentlyViewedItemDTO = recentlyViewedItemMapper.toDto(recentlyViewedItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRecentlyViewedItemMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(recentlyViewedItemDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RecentlyViewedItem in the database
        List<RecentlyViewedItem> recentlyViewedItemList = recentlyViewedItemRepository.findAll();
        assertThat(recentlyViewedItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateRecentlyViewedItemWithPatch() throws Exception {
        // Initialize the database
        recentlyViewedItem.setId(UUID.randomUUID());
        recentlyViewedItemRepository.save(recentlyViewedItem);

        int databaseSizeBeforeUpdate = recentlyViewedItemRepository.findAll().size();

        // Update the recentlyViewedItem using partial update
        RecentlyViewedItem partialUpdatedRecentlyViewedItem = new RecentlyViewedItem();
        partialUpdatedRecentlyViewedItem.setId(recentlyViewedItem.getId());

        partialUpdatedRecentlyViewedItem
            .userid(UPDATED_USERID)
            .catalogid(UPDATED_CATALOGID)
            .brandname(UPDATED_BRANDNAME)
            .catalogname(UPDATED_CATALOGNAME)
            .catalogunitprice(UPDATED_CATALOGUNITPRICE)
            .catalogimg(UPDATED_CATALOGIMG);

        restRecentlyViewedItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRecentlyViewedItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRecentlyViewedItem))
            )
            .andExpect(status().isOk());

        // Validate the RecentlyViewedItem in the database
        List<RecentlyViewedItem> recentlyViewedItemList = recentlyViewedItemRepository.findAll();
        assertThat(recentlyViewedItemList).hasSize(databaseSizeBeforeUpdate);
        RecentlyViewedItem testRecentlyViewedItem = recentlyViewedItemList.get(recentlyViewedItemList.size() - 1);
        assertThat(testRecentlyViewedItem.getUserid()).isEqualTo(UPDATED_USERID);
        assertThat(testRecentlyViewedItem.getCatalogid()).isEqualTo(UPDATED_CATALOGID);
        assertThat(testRecentlyViewedItem.getBrandname()).isEqualTo(UPDATED_BRANDNAME);
        assertThat(testRecentlyViewedItem.getCatalogname()).isEqualTo(UPDATED_CATALOGNAME);
        assertThat(testRecentlyViewedItem.getCatalogunitprice()).isEqualTo(UPDATED_CATALOGUNITPRICE);
        assertThat(testRecentlyViewedItem.getCatalogimg()).isEqualTo(UPDATED_CATALOGIMG);
    }

    @Test
    void fullUpdateRecentlyViewedItemWithPatch() throws Exception {
        // Initialize the database
        recentlyViewedItem.setId(UUID.randomUUID());
        recentlyViewedItemRepository.save(recentlyViewedItem);

        int databaseSizeBeforeUpdate = recentlyViewedItemRepository.findAll().size();

        // Update the recentlyViewedItem using partial update
        RecentlyViewedItem partialUpdatedRecentlyViewedItem = new RecentlyViewedItem();
        partialUpdatedRecentlyViewedItem.setId(recentlyViewedItem.getId());

        partialUpdatedRecentlyViewedItem
            .userid(UPDATED_USERID)
            .catalogid(UPDATED_CATALOGID)
            .brandname(UPDATED_BRANDNAME)
            .catalogname(UPDATED_CATALOGNAME)
            .catalogunitprice(UPDATED_CATALOGUNITPRICE)
            .catalogimg(UPDATED_CATALOGIMG);

        restRecentlyViewedItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRecentlyViewedItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRecentlyViewedItem))
            )
            .andExpect(status().isOk());

        // Validate the RecentlyViewedItem in the database
        List<RecentlyViewedItem> recentlyViewedItemList = recentlyViewedItemRepository.findAll();
        assertThat(recentlyViewedItemList).hasSize(databaseSizeBeforeUpdate);
        RecentlyViewedItem testRecentlyViewedItem = recentlyViewedItemList.get(recentlyViewedItemList.size() - 1);
        assertThat(testRecentlyViewedItem.getUserid()).isEqualTo(UPDATED_USERID);
        assertThat(testRecentlyViewedItem.getCatalogid()).isEqualTo(UPDATED_CATALOGID);
        assertThat(testRecentlyViewedItem.getBrandname()).isEqualTo(UPDATED_BRANDNAME);
        assertThat(testRecentlyViewedItem.getCatalogname()).isEqualTo(UPDATED_CATALOGNAME);
        assertThat(testRecentlyViewedItem.getCatalogunitprice()).isEqualTo(UPDATED_CATALOGUNITPRICE);
        assertThat(testRecentlyViewedItem.getCatalogimg()).isEqualTo(UPDATED_CATALOGIMG);
    }

    @Test
    void patchNonExistingRecentlyViewedItem() throws Exception {
        int databaseSizeBeforeUpdate = recentlyViewedItemRepository.findAll().size();
        recentlyViewedItem.setId(UUID.randomUUID());

        // Create the RecentlyViewedItem
        RecentlyViewedItemDTO recentlyViewedItemDTO = recentlyViewedItemMapper.toDto(recentlyViewedItem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRecentlyViewedItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, recentlyViewedItemDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(recentlyViewedItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RecentlyViewedItem in the database
        List<RecentlyViewedItem> recentlyViewedItemList = recentlyViewedItemRepository.findAll();
        assertThat(recentlyViewedItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchRecentlyViewedItem() throws Exception {
        int databaseSizeBeforeUpdate = recentlyViewedItemRepository.findAll().size();
        recentlyViewedItem.setId(UUID.randomUUID());

        // Create the RecentlyViewedItem
        RecentlyViewedItemDTO recentlyViewedItemDTO = recentlyViewedItemMapper.toDto(recentlyViewedItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRecentlyViewedItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(recentlyViewedItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RecentlyViewedItem in the database
        List<RecentlyViewedItem> recentlyViewedItemList = recentlyViewedItemRepository.findAll();
        assertThat(recentlyViewedItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamRecentlyViewedItem() throws Exception {
        int databaseSizeBeforeUpdate = recentlyViewedItemRepository.findAll().size();
        recentlyViewedItem.setId(UUID.randomUUID());

        // Create the RecentlyViewedItem
        RecentlyViewedItemDTO recentlyViewedItemDTO = recentlyViewedItemMapper.toDto(recentlyViewedItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRecentlyViewedItemMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(recentlyViewedItemDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RecentlyViewedItem in the database
        List<RecentlyViewedItem> recentlyViewedItemList = recentlyViewedItemRepository.findAll();
        assertThat(recentlyViewedItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteRecentlyViewedItem() throws Exception {
        // Initialize the database
        recentlyViewedItem.setId(UUID.randomUUID());
        recentlyViewedItemRepository.save(recentlyViewedItem);

        int databaseSizeBeforeDelete = recentlyViewedItemRepository.findAll().size();

        // Delete the recentlyViewedItem
        restRecentlyViewedItemMockMvc
            .perform(delete(ENTITY_API_URL_ID, recentlyViewedItem.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RecentlyViewedItem> recentlyViewedItemList = recentlyViewedItemRepository.findAll();
        assertThat(recentlyViewedItemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
