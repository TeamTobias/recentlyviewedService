package com.tobias.recentlyviewedservice.service.dto;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.tobias.recentlyviewedservice.domain.RecentlyViewedItem} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RecentlyViewedItemDTO implements Serializable {

    private UUID id;

    private Long userid;

    private Long catalogid;

    private String brandname;

    private String catalogname;

    private Integer catalogunitprice;

    private String catalogimg;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public Long getCatalogid() {
        return catalogid;
    }

    public void setCatalogid(Long catalogid) {
        this.catalogid = catalogid;
    }

    public String getBrandname() {
        return brandname;
    }

    public void setBrandname(String brandname) {
        this.brandname = brandname;
    }

    public String getCatalogname() {
        return catalogname;
    }

    public void setCatalogname(String catalogname) {
        this.catalogname = catalogname;
    }

    public Integer getCatalogunitprice() {
        return catalogunitprice;
    }

    public void setCatalogunitprice(Integer catalogunitprice) {
        this.catalogunitprice = catalogunitprice;
    }

    public String getCatalogimg() {
        return catalogimg;
    }

    public void setCatalogimg(String catalogimg) {
        this.catalogimg = catalogimg;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RecentlyViewedItemDTO)) {
            return false;
        }

        RecentlyViewedItemDTO recentlyViewedItemDTO = (RecentlyViewedItemDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, recentlyViewedItemDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RecentlyViewedItemDTO{" +
            "id='" + getId() + "'" +
            ", userid=" + getUserid() +
            ", catalogid=" + getCatalogid() +
            ", brandname='" + getBrandname() + "'" +
            ", catalogname='" + getCatalogname() + "'" +
            ", catalogunitprice=" + getCatalogunitprice() +
            ", catalogimg='" + getCatalogimg() + "'" +
            "}";
    }
}
