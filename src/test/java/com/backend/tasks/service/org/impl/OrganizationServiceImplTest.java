package com.backend.tasks.service.org.impl;

import com.backend.tasks.Application;
import com.backend.tasks.crud.OrganizationRepository;
import com.backend.tasks.repository.Organization;
import com.backend.tasks.service.org.OrganizationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

/**
 * Implement tests for UserServiceImpl
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class OrganizationServiceImplTest {

    @Autowired
    OrganizationService organizationService;

    @MockBean
    OrganizationRepository organizationRepository;

    private final List<Organization> organizations = new ArrayList<>();

    @Before
    public void setUp() {
        createOrganization();
        createRepositoryMocks();
    }

    private void createOrganization() {
        Organization org1 = new Organization();
        org1.setId(1L);
        org1.setName("Valve");

        Organization org2 = new Organization();
        org2.setId(2L);
        org2.setName("Microsoft");

        organizations.add(org1);
        organizations.add(org2);
    }

    private void createRepositoryMocks() {
        Organization org = organizations.get(0);
        Mockito.when(organizationRepository.findAll())
                .thenReturn(new OrganizationList(organizations));

        Mockito.when(organizationRepository.findById(org.getId())).thenReturn(Optional.of(org));
        Mockito.when(organizationRepository.findById(3L)).thenReturn(Optional.empty());

        Mockito.when(organizationRepository.save(org)).thenReturn(org);
    }

    @Test
    public void testFindById() {
        Organization org = organizationService.findById(1L);
        assertThat(org.getName())
                .isEqualTo("Valve");
    }

    @Test
    public void testFindByIdNonExisting() {
        Organization org = organizationService.findById(3L);
        assertThat(org)
                .isNull();
    }

    @Test
    public void testExists() {
        assertThat(organizationService.exists(1L))
                .isTrue();
    }

    @Test
    public void testNotExists() {
        assertThat(organizationService.exists(3L))
                .isFalse();
    }

    @Test
    public void testFindAll() {
        assertThat(organizationService.findAll())
                .hasSameElementsAs(organizations);
    }

    @Test
    public void save() {
        Organization org1 = new Organization();
        org1.setId(1L);
        org1.setName("Valve");

        Organization actual = organizationService.save(org1);
        assertThat(actual).isNotNull();
        assertThat(actual.getName()).isEqualTo("Valve");
    }

    class OrganizationList implements Iterable<Organization> {
        private final List<Organization> list = new ArrayList<>();

        public OrganizationList(List<Organization> orgs) {
            for (Organization org : orgs) {
                this.list.add(org);
            }
        }

        @Override
        public Iterator<Organization> iterator() {
            return list.iterator();
        }
    }
}