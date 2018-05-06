package com.zeral.repository;

import com.zeral.domain.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.time.Instant;

/**
 * Spring Data JPA repository for the User entity.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAllByActivatedIsFalseAndCreatedDateBefore(Instant dateTime);

    List<User> findAllByDeletedIsFalseAndDepartmentId(String departmentId);

    Optional<User> findOneByDeletedIsFalseAndResetKey(String resetKey);

    Optional<User> findOneByDeletedIsFalseAndEmailIgnoreCase(String email);

    Optional<User> findOneByDeletedIsFalseAndLogin(String login);

    Page<User> findAllByDeletedIsFalseAndDepartmentId(String query, Pageable pageable);

    @EntityGraph(attributePaths = "roles")
    Optional<User> findOneWithRolesByDeletedIsFalseAndId(Long id);

    @EntityGraph(attributePaths = "roles")
    User findByDeletedIsFalseAndLogin(String login);

    @EntityGraph(attributePaths = "roles")
    @Cacheable(cacheNames = "users")
    Optional<User> findOneWithRolesByDeletedIsFalseAndLogin(String login);

    @EntityGraph(attributePaths = "roles", type = EntityGraph.EntityGraphType.FETCH)
    Page<User> findAllByDeletedIsFalseAndNameLikeAndLoginNot(String query, String login, Pageable pageable);
}
