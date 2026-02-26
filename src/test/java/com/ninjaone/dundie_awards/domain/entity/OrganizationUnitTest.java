package com.ninjaone.dundie_awards.domain.entity;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class OrganizationUnitTest {

    @Test
    void constructorWithIdAndName_ShouldCreateOrganization() {
        Organization org = new Organization(1L, "TechCorp");
        assertThat(org)
            .hasFieldOrPropertyWithValue("id", 1L)
            .hasFieldOrPropertyWithValue("name", "TechCorp");
    }

    @Test
    void constructorWithNameOnly_ShouldCreateOrganization() {
        Organization org = new Organization("TechCorp");
        assertThat(org)
            .hasFieldOrPropertyWithValue("id", null)
            .hasFieldOrPropertyWithValue("name", "TechCorp");
    }

    @Test
    void nullName_ShouldThrowException() {
        assertThatThrownBy(() -> new Organization(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Organization name cannot be null or empty");
    }

    @Test
    void blankName_ShouldThrowException() {
        assertThatThrownBy(() -> new Organization("   "))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Organization name cannot be null or empty");
    }

    @Test
    void blankNameWithId_ShouldThrowException() {
        assertThatThrownBy(() -> new Organization(1L, "   "))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Organization name cannot be null or empty");
    }

    @Test
    void equalsAndHashCode() {
        Organization org1 = new Organization(1L, "TechCorp");
        Organization org2 = new Organization(1L, "TechCorp");
        Organization org3 = new Organization(2L, "OtherCorp");

        assertThat(org1).isEqualTo(org2);
        assertThat(org1).isNotEqualTo(org3);
        assertThat(org1.hashCode()).isEqualTo(org2.hashCode());
    }

    @Test
    void toString_ShouldContainNameAndId() {
        Organization org = new Organization(1L, "TechCorp");
        assertThat(org.toString()).contains("TechCorp");
    }
}
