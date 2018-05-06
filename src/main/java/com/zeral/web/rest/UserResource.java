package com.zeral.web.rest;

import com.zeral.constants.Constants;
import com.zeral.domain.File;
import com.zeral.domain.User;
import com.zeral.repository.UserRepository;
import com.zeral.constants.AuthoritiesConstants;
import com.zeral.security.SecurityUtils;
import com.zeral.service.FileService;
import com.zeral.service.MailService;
import com.zeral.service.UserService;
import com.zeral.web.rest.errors.BadRequestAlertException;
import com.zeral.web.rest.errors.EmailAlreadyUsedException;
import com.zeral.web.rest.errors.LoginAlreadyUsedException;
import com.zeral.web.rest.util.HeaderUtil;
import com.zeral.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing users.
 * <p>
 * This class accesses the User entity, and needs to fetch its collection of authorities.
 * <p>
 */
@RestController
@RequestMapping("/api")
public class UserResource {

    private final Logger log = LoggerFactory.getLogger(UserResource.class);

    private final UserRepository userRepository;

    private final UserService userService;

    private final MailService mailService;

    private final FileService fileService;

    public UserResource(UserRepository userRepository, UserService userService, MailService mailService, FileService fileService) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.mailService = mailService;
        this.fileService = fileService;
    }

    /**
     *  添加图片文件接口
     *
     * */
    @PostMapping("/addPicture")
    public ResponseEntity addPicture(@RequestParam("file") MultipartFile file, HttpServletResponse response){
        try {
            System.out.println("=========开始上传图片======================================");
            User user = userRepository.findByDeletedIsFalseAndLogin(SecurityUtils.getCurrentUser());
            File fileEntity = fileService.saveAndUploadFileToFTP(user.getId(), file);
            System.out.println("==========图片上传完毕======================================");
            // 使用了上传文件的输出流和response的返回json会出错，重置response
            response.reset();
            return ResponseEntity.ok(fileEntity.getId());
        } catch (Exception e) {
            response.reset();
            return ResponseEntity.badRequest().body("图片上传失败!");
        }
    }

    /**
     * POST  /users  : Creates a new user.
     * <p>
     * Creates a new user if the login and email are not already used, and sends an
     * mail with an activation link.
     * The user needs to be activated on creation.
     *
     * @param user the user to create
     * @return the ResponseEntity with status 201 (Created) and with body the new user, or with status 400 (Bad Request) if the login or email is already in use
     * @throws URISyntaxException if the Location URI syntax is incorrect
     * @throws BadRequestAlertException 400 (Bad Request) if the login or email is already in use
     */
    @PostMapping("/users")
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) throws URISyntaxException {
        log.debug("REST request to save User : {}", user);

        if (user.getId() != null) {
            throw new BadRequestAlertException("A new user cannot already have an ID", "userManagement", "idexists");
            // Lowercase the user login before comparing with database
        } else if (userRepository.findOneByDeletedIsFalseAndLogin(user.getLogin().toLowerCase()).isPresent()) {
            throw new LoginAlreadyUsedException();
        } else if (userRepository.findOneByDeletedIsFalseAndEmailIgnoreCase(user.getEmail()).isPresent()) {
            throw new EmailAlreadyUsedException();
        } else {
            User newUser = userService.createUser(user);
            mailService.sendCreationEmail(newUser);
            return ResponseEntity.created(new URI("/api/users/" + newUser.getLogin()))
                .headers(HeaderUtil.createAlert( "新建用户成功", newUser.getLogin()))
                .body(newUser);
        }
    }

    /**
     * PUT /users : Updates an existing User.
     *
     * @param user the user to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated user
     * @throws EmailAlreadyUsedException 400 (Bad Request) if the email is already in use
     * @throws LoginAlreadyUsedException 400 (Bad Request) if the login is already in use
     */
    @PutMapping("/users")
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<User> updateUser(@Valid @RequestBody User user) {
        log.debug("REST request to update User : {}", user);
        Optional<User> existingUser = userRepository.findOneByDeletedIsFalseAndEmailIgnoreCase(user.getEmail());
        if (existingUser.isPresent() && (!existingUser.get().getId().equals(user.getId()))) {
            throw new EmailAlreadyUsedException();
        }
        existingUser = userRepository.findOneByDeletedIsFalseAndLogin(user.getLogin().toLowerCase());
        if (existingUser.isPresent() && (!existingUser.get().getId().equals(user.getId()))) {
            throw new LoginAlreadyUsedException();
        }
        Optional<User> updatedUser = userService.updateUser(user);

        return ResponseUtil.wrapOrNotFound(updatedUser,
            HeaderUtil.createAlert("用户更新成功", user.getLogin()));
    }

    /***
     * 更新部门，不包括角色及部门
     * @param user
     * @return
     */
    @PutMapping("/updateUserNotRole")
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<User> updateUserNotRole(@Valid @RequestBody User user) {
        log.debug("REST request to update User : {}", user);
        Optional<User> existingUser = userRepository.findOneByDeletedIsFalseAndEmailIgnoreCase(user.getEmail());
        if (existingUser.isPresent() && (!existingUser.get().getId().equals(user.getId()))) {
            throw new EmailAlreadyUsedException();
        }
        existingUser = userRepository.findOneByDeletedIsFalseAndLogin(user.getLogin().toLowerCase());
        if (existingUser.isPresent() && (!existingUser.get().getId().equals(user.getId()))) {
            throw new LoginAlreadyUsedException();
        }
        Optional<User>  updatedUser = userService.updateUser(user.getId(),user);

        return ResponseUtil.wrapOrNotFound(updatedUser,
            HeaderUtil.createAlert("用户更新成功", user.getLogin()));
    }


    /***
     * 转换部门
     * @param user
     * @return
     */
    @PutMapping("/updateUserDepartment")
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<User> updateUserDepartment(@Valid @RequestBody User user) {
        log.debug("REST request to update User : {}", user);
        Optional<User>  updatedUser = userService.updateUserDepartment(user.getId(),user);
        return ResponseUtil.wrapOrNotFound(updatedUser,
            HeaderUtil.createAlert("用户部门更换成功", user.getLogin()));
    }

      /**
     * GET /users : get all users.
     *
     * @param  query 过滤查询数据
     * @param pageable 分页数据
     * @return the ResponseEntity with status 200 (OK) and with body all users
     */
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers(@RequestParam String query, Pageable pageable) {
        String name = '%' + query + '%';
        final Page<User> page = userService.getAllManagedUsers(name, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/users");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


    /**
     * GET /users : getAllUsersByDepartmentId
     *
     * @param  query 过滤查询数据
     * @param pageable 分页数据
     * @return the ResponseEntity with status 200 (OK) and with body all users
     */
    @GetMapping("/departmentUsers")
    public ResponseEntity<List<User>> getAllUsersByDepartmentId(@RequestParam String query, Pageable pageable) {
        final Page<User> page = userService.getAllUsersByDepartmentId(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/users");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * 获取部门下的所有员工
     *
     * @return the ResponseEntity with status 200 (OK) and with body all users
     */
    @GetMapping("/departmentUsers/{departmentId}")
    public ResponseEntity<List<User>> getAllUsersByDepartmentId(@PathVariable String departmentId) {
        final List<User> users = userService.getAllByDepartmentId(departmentId);
        return ResponseEntity.ok(users);
    }


    /**
     * @return a string list of the all of the roles
     */
    @GetMapping("/users/authorities")
    @Secured(AuthoritiesConstants.ADMIN)
    public List<String> getAuthorities() {
        return userService.getAuthorities();
    }

    /**
     * GET /users/:login : get the "login" user.
     *
     * @param login the login of the user to find
     * @return the ResponseEntity with status 200 (OK) and with body the "login" user, or with status 404 (Not Found)
     */
    @GetMapping("/users/{login:" + Constants.LOGIN_REGEX + "}")
    public ResponseEntity<User> getUser(@PathVariable String login) {
        log.debug("REST request to get User : {}", login);
        return ResponseUtil.wrapOrNotFound(
            userService.getUserWithRolesByLogin(login));
    }

    /**
     * DELETE /users/:login : delete the "login" User.
     *
     * @param login the login of the user to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/users/{login:" + Constants.LOGIN_REGEX + "}")
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<Void> deleteUser(@PathVariable String login) {
        log.debug("REST request to delete User: {}", login);
        userService.deleteUser(login);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert( "删除用户成功", login)).build();
    }
}
