package com.zeral.service;

import com.zeral.constants.AuthoritiesConstants;
import com.zeral.constants.Constants;
import com.zeral.domain.Role;
import com.zeral.domain.User;
import com.zeral.repository.AuthorityRepository;
import com.zeral.repository.UserRepository;
import com.zeral.security.SecurityUtils;
import com.zeral.service.util.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service class for managing users.
 */
@Service
@Transactional
public class UserService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    private static final String USERS_CACHE = "users";

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthorityRepository authorityRepository;

    private final CacheManager cacheManager;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthorityRepository authorityRepository, CacheManager cacheManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authorityRepository = authorityRepository;
        this.cacheManager = cacheManager;
    }

    public Optional<User> completePasswordReset(String newPassword, String key) {
       log.debug("Reset user password for reset key {}", key);

       return userRepository.findOneByDeletedIsFalseAndResetKey(key)
           .filter(user -> user.getResetDate().isAfter(Instant.now().minusSeconds(86400)))
           .map(user -> {
                user.setPassword(passwordEncoder.encode(newPassword));
                user.setResetKey(null);
                user.setResetDate(null);
                cacheManager.getCache(USERS_CACHE).evict(user.getLogin());
                return user;
           });
    }

    public Optional<User> requestPasswordReset(String mail) {
        return userRepository.findOneByDeletedIsFalseAndEmailIgnoreCase(mail)
            .filter(User::getActivated)
            .map(user -> {
                user.setResetKey(RandomUtil.generateResetKey());
                user.setResetDate(Instant.now());
                cacheManager.getCache(USERS_CACHE).evict(user.getLogin());
                return user;
            });
    }

    public User registerUser(User user, String password) {

        User newUser = new User();
        Role role = authorityRepository.findOne(AuthoritiesConstants.USER);
        Set<Role> roles = new HashSet<>();
        String encryptedPassword = passwordEncoder.encode(password);
        newUser.setLogin(user.getLogin());
        // new user gets initially a generated password
        newUser.setPassword(encryptedPassword);
        newUser.setName(user.getName());
        newUser.setEmail(user.getEmail());
        newUser.setImageUrl(user.getImageUrl());
        newUser.setLangKey(user.getLangKey());
        newUser.setIdCard(user.getIdCard());
        newUser.setPhone(user.getPhone());
        newUser.setGender(user.getGender());
        newUser.setBirthday(user.getBirthday());
        // new user is not active
        newUser.setActivated(false);
        roles.add(role);
        newUser.setRoles(roles);
        userRepository.save(newUser);
        log.debug("Created Information for User: {}", newUser);
        return newUser;
    }

    public User createUser(User user) {
        if (user.getLangKey() == null) {
            user.setLangKey(Constants.DEFAULT_LANGUAGE); // default language
        }
        Role role = authorityRepository.findOne(AuthoritiesConstants.USER);
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);
        String encryptedPassword = passwordEncoder.encode(RandomUtil.generatePassword());
        user.setPassword(encryptedPassword);
        user.setResetKey(RandomUtil.generateResetKey());
        user.setResetDate(Instant.now());
        user.setActivated(true);
        userRepository.save(user);
        log.debug("Created Information for User: {}", user);
        return user;
    }

    /**
     * Update basic information (first name, last name, email, language) for the current user.
     *
     * @param name first name of user
     * @param email email id of user
     * @param langKey language key
     * @param imageUrl image URL of user
     */
    public void updateUser(String name, String email, String langKey, String imageUrl) {
        SecurityUtils.getCurrentUserLogin()
            .flatMap(userRepository::findOneByDeletedIsFalseAndLogin)
            .ifPresent(user -> {
                user.setName(name);
                user.setEmail(email);
                user.setLangKey(langKey);
                user.setImageUrl(imageUrl);
                cacheManager.getCache(USERS_CACHE).evict(user.getLogin());
                log.debug("Changed Information for User: {}", user);
            });
    }

    /**
     * 修改用户信息部分字段，返回修改后的用户
     *
     * @param user 修改的用户
     * @return 更新的用户
     */
    public Optional<User> updateUser(User user) {
        return Optional.of(userRepository
            .findOne(user.getId()))
            .map(userDomain -> {
                userDomain.setLogin(user.getLogin());
                userDomain.setName(user.getName());
                userDomain.setEmail(user.getEmail());
                userDomain.setGender(user.getGender());
                userDomain.setPhone(user.getPhone());
                userDomain.setIdCard(user.getIdCard());
                userDomain.setJobTitle(user.getJobTitle());
                userDomain.setPosition(user.getPosition());
                Set<Role> managedRoles = userDomain.getRoles();
                managedRoles.clear();
                user.getRoles().stream()
                    .map(role -> authorityRepository.findOne(role.getCode()))
                    .forEach(managedRoles::add);
                cacheManager.getCache(USERS_CACHE).evict(userDomain.getLogin());
                log.debug("修改用户信息: {}", userDomain);
                return userDomain;
            });
    }


    /***
     * 更新员工，不包括角色及部门
     * @param id
     * @return
     */
    public Optional<User> updateUser(Long id, User user) {
        log.debug("REST request to update User : {}",user);
        User user1 = userRepository.findOne(id);
        user1.setLogin(user.getLogin());
        user1.setName(user.getName());
        user1.setEmail(user.getEmail());
        user1.setGender(user.getGender());
        user1.setPhone(user.getPhone());
        user1.setPosition(user.getPosition());
        user1.setJobTitle(user.getJobTitle());
        return Optional.of(user1);
    }
    /***
     * 更新员工，不包括角色
     * @param id
     * @return
     */
    public Optional<User> updateUserDepartment(Long id, User user) {
        log.debug("REST request to update User : {}",user);
        User user1 = userRepository.findOne(id);
        user1.setDepartmentId(user.getDepartmentId());
        return Optional.of(user1);
    }


    /**
     * 逻辑删
     *
     * @param login 账户
     */
    public void deleteUser(String login) {
        userRepository.findOneByDeletedIsFalseAndLogin(login).ifPresent(user -> {
            user.setDeleted(true);
            cacheManager.getCache(USERS_CACHE).evict(login);
            log.debug("Deleted User: {}", user);
        });
    }

    public void changePassword(String password) {
        SecurityUtils.getCurrentUserLogin()
            .flatMap(userRepository::findOneByDeletedIsFalseAndLogin)
            .ifPresent(user -> {
                String encryptedPassword = passwordEncoder.encode(password);
                user.setPassword(encryptedPassword);
                cacheManager.getCache(USERS_CACHE).evict(user.getLogin());
                log.debug("Changed password for User: {}", user);
            });
    }

    @Transactional(readOnly = true)
    public Page<User> getAllManagedUsers(String query, Pageable pageable) {
        return userRepository.findAllByDeletedIsFalseAndNameLikeAndLoginNot(query, SecurityUtils.getCurrentUser(), pageable);
    }


    @Transactional(readOnly = true)
    public Page<User> getAllUsersByDepartmentId(String query, Pageable pageable) {
        return userRepository.findAllByDeletedIsFalseAndDepartmentId(query, pageable);
    }


    @Transactional(readOnly = true)
    public List<User> getAllByDepartmentId(String departmentId) {
        return userRepository.findAllByDeletedIsFalseAndDepartmentId(departmentId);
    }


    @Transactional(readOnly = true)
    public Optional<User> getUserWithRolesByLogin(String login) {
        return userRepository.findOneWithRolesByDeletedIsFalseAndLogin(login);
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserWithRoles(Long id) {
        return userRepository.findOneWithRolesByDeletedIsFalseAndId(id);
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserWithRoles() {
        return SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneWithRolesByDeletedIsFalseAndLogin);
    }

//    /**
//     * Not activated users should be automatically deleted after 3 days.
//     * <p>
//     * This is scheduled to get fired everyday, at 01:00 (am).
//     */
//    @Scheduled(cron = "0 0 1 * * ?")
//    public void removeNotActivatedUsers() {
//        List<User> users = userRepository.findAllByActivatedIsFalseAndCreatedDateBefore(Instant.now().minus(3, ChronoUnit.DAYS));
//        for (User user : users) {
//            log.debug("Deleting not activated user {}", user.getLogin());
//            userRepository.delete(user);
//            cacheManager.getCache(USERS_CACHE).evict(user.getLogin());
//        }
//    }

    /**
     * @return a list of all the authorities
     */
    public List<String> getAuthorities() {
        return authorityRepository.findAll().stream().map(Role::getCode).collect(Collectors.toList());
    }

}
