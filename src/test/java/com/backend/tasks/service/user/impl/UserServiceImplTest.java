package com.backend.tasks.service.user.impl;

import com.backend.tasks.Application;
import com.backend.tasks.crud.OrganizationRepository;
import com.backend.tasks.crud.UserRepository;
import com.backend.tasks.repository.Organization;
import com.backend.tasks.repository.User;
import com.backend.tasks.service.user.UserService;
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
public class UserServiceImplTest {
    @Autowired
    UserService userService;

    @MockBean
    UserRepository userRepository;

    @MockBean
    OrganizationRepository organizationRepository;

    private final List<User> users = new ArrayList<>();

    @Before
    public void setUp() {
        createUsers();
        createRepositoryMocks();
    }

    private void createUsers() {
        Organization org = new Organization();
        org.setId(5L);
        org.setName("Samsung");

        User user1 = new User();
        user1.setId(1L);
        user1.setUsername("John Week");
        user1.setOrganization(org);

        User user2 = new User();
        user2.setId(2L);
        user2.setUsername("Bob Martin");
        user2.setOrganization(org);

        users.add(user1);
        users.add(user2);
    }

    private void createRepositoryMocks() {
        User user = users.get(0);
        Organization org = user.getOrganization();

        Mockito.when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        Mockito.when(userRepository.findById(3L)).thenReturn(Optional.empty());

        Mockito.when(userRepository.save(user)).thenReturn(user);
        Mockito.when(userRepository.findAllByOrganizationId(5L))
                .thenReturn(new UserList(users));

        Mockito.when(organizationRepository.findById(5L)).thenReturn(Optional.of(org));
    }

    @Test
    public void testFindById() {
        User org = userService.findById(1L);
        assertThat(org.getUsername())
                .isEqualTo("John Week");
    }

    @Test
    public void testFindByIdNonExisting() {
        User org = userService.findById(3L);
        assertThat(org)
                .isNull();
    }

    @Test
    public void testExists() {
        assertThat(userService.exists(1L, 5L))
                .isTrue();
    }

    @Test
    public void testNotExists() {
        assertThat(userService.exists(3L, 5L))
                .isFalse();
    }

    @Test
    public void testNotExistsWrongOrgId() {
        assertThat(userService.exists(1L, 3L))
                .isFalse();
    }

    @Test
    public void testFindAll() {
        assertThat(userService.findAll(5L))
                .hasSameElementsAs(users);
    }

    @Test
    public void save() {
        User user1 = new User();
        user1.setId(1L);
        user1.setUsername("John Week");

        User actual = userService.save(5L, user1);
        assertThat(actual).isNotNull();
        assertThat(actual.getUsername()).isEqualTo("John Week");
    }

    class UserList implements Iterable<User> {
        private final List<User> list = new ArrayList<>();

        public UserList(List<User> orgs) {
            for (User org : orgs) {
                this.list.add(org);
            }
        }

        @Override
        public Iterator<User> iterator() {
            return list.iterator();
        }
    }
}