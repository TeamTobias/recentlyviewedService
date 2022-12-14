package com.tobias.recentlyviewedservice.domain;

import java.io.Serializable;
import java.util.UUID;
import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.mapping.Table;

/**
 * A RecentlyViewedItem.
 */
// Since spring-data-cassandra 3.4.2, table names needs to be in lowercase
// See https://github.com/spring-projects/spring-data-cassandra/issues/1293#issuecomment-1192555467
@Table("recentlyvieweditem")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RecentlyViewedItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private UUID id;

    private Long userid;

    private Long catalogid;

    private String brandname;

    private String catalogname;

    private Integer catalogunitprice;

    private String catalogimg;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public RecentlyViewedItem id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Long getUserid() {
        return this.userid;
    }

    public RecentlyViewedItem userid(Long userid) {
        this.setUserid(userid);
        return this;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public Long getCatalogid() {
        return this.catalogid;
    }

    public RecentlyViewedItem catalogid(Long catalogid) {
        this.setCatalogid(catalogid);
        return this;
    }

    public void setCatalogid(Long catalogid) {
        this.catalogid = catalogid;
    }

    public String getBrandname() {
        return this.brandname;
    }

    public RecentlyViewedItem brandname(String brandname) {
        this.setBrandname(brandname);
        return this;
    }

    public void setBrandname(String brandname) {
        this.brandname = brandname;
    }

    public String getCatalogname() {
        return this.catalogname;
    }

    public RecentlyViewedItem catalogname(String catalogname) {
        this.setCatalogname(catalogname);
        return this;
    }

    public void setCatalogname(String catalogname) {
        this.catalogname = catalogname;
    }

    public Integer getCatalogunitprice() {
        return this.catalogunitprice;
    }

    public RecentlyViewedItem catalogunitprice(Integer catalogunitprice) {
        this.setCatalogunitprice(catalogunitprice);
        return this;
    }

    public void setCatalogunitprice(Integer catalogunitprice) {
        this.catalogunitprice = catalogunitprice;
    }

    public String getCatalogimg() {
        return this.catalogimg;
    }

    public RecentlyViewedItem catalogimg(String catalogimg) {
        this.setCatalogimg(catalogimg);
        return this;
    }

    public void setCatalogimg(String catalogimg) {
        this.catalogimg = catalogimg;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RecentlyViewedItem)) {
            return false;
        }
        return id != null && id.equals(((RecentlyViewedItem) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RecentlyViewedItem{" +
            "id=" + getId() +
            ", userid=" + getUserid() +
            ", catalogid=" + getCatalogid() +
            ", brandname='" + getBrandname() + "'" +
            ", catalogname='" + getCatalogname() + "'" +
            ", catalogunitprice=" + getCatalogunitprice() +
            ", catalogimg='" + getCatalogimg() + "'" +
            "}";
    }
}
