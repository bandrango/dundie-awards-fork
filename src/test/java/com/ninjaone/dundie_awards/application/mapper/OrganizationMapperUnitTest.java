package com.ninjaone.dundie_awards.application.mapper;

import com.ninjaone.dundie_awards.application.dto.OrganizationDTO;
import com.ninjaone.dundie_awards.domain.entity.Organization;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class OrganizationMapperUnitTest {

    private final OrganizationMapper mapper = new OrganizationMapper();

    @Test
    void toDTO_WithValidOrganization_ShouldConvertToDTO() {
        Organization org = new Organization(1L, "TechCorp");
        
        OrganizationDTO dto = mapper.toDTO(org);

        assertThat(dto)
            .isNotNull()
            .hasFieldOrPropertyWithValue("id", 1L)
            .hasFieldOrPropertyWithValue("name", "TechCorp");
    }

    @Test
    void toDTO_WithNullOrganization_ShouldReturnNull() {
        OrganizationDTO dto = mapper.toDTO(null);
        assertThat(dto).isNull();
    }

    @Test
    void toDTO_WithOrganizationWithoutId_ShouldConvertToDTO() {
        Organization org = new Organization("StartupCorp");
        
        OrganizationDTO dto = mapper.toDTO(org);

        assertThat(dto)
            .isNotNull()
            .hasFieldOrPropertyWithValue("id", null)
            .hasFieldOrPropertyWithValue("name", "StartupCorp");
    }

    @Test
    void toDomain_WithValidDTO_ShouldConvertToDomain() {
        OrganizationDTO dto = new OrganizationDTO(2L, "NewOrg");
        
        Organization org = mapper.toDomain(dto);

        assertThat(org)
            .isNotNull()
            .hasFieldOrPropertyWithValue("id", 2L)
            .hasFieldOrPropertyWithValue("name", "NewOrg");
    }

    @Test
    void toDomain_WithNullDTO_ShouldReturnNull() {
        Organization org = mapper.toDomain(null);
        assertThat(org).isNull();
    }

    @Test
    void toDomain_WithDTOWithoutId_ShouldConvertToDomain() {
        OrganizationDTO dto = new OrganizationDTO(null, "NewStartup");
        
        Organization org = mapper.toDomain(dto);

        assertThat(org)
            .isNotNull()
            .hasFieldOrPropertyWithValue("id", null)
            .hasFieldOrPropertyWithValue("name", "NewStartup");
    }

    @Test
    void roundTrip_DTOToDomainToDTO_ShouldPreserveData() {
        OrganizationDTO original = new OrganizationDTO(5L, "TestOrg");
        
        Organization domain = mapper.toDomain(original);
        OrganizationDTO roundTrip = mapper.toDTO(domain);

        assertThat(roundTrip).isEqualTo(original);
    }
}
