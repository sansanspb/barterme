package ru.iqdevelop.barterme.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import ru.iqdevelop.barterme.entities.RoleEntity;
import ru.iqdevelop.barterme.entities.UserEntity;
import ru.iqdevelop.barterme.models.auth.RegistrationModel;
import ru.iqdevelop.barterme.repositories.NotificationRepository;
import ru.iqdevelop.barterme.repositories.RoleRepository;
import ru.iqdevelop.barterme.repositories.UserRepository;
import ru.iqdevelop.barterme.utils.Helpers;
import ru.iqdevelop.barterme.utils.RandomString;

import java.util.List;
import java.util.stream.Collectors;

@Service
@EnableTransactionManagement
public class AuthService {

    private static final int TOKEN_LENGTH = 7;
    private static final String BASE_USER_ROLE = "ROLE_USER";
    private static final String CONFIRMED_USER_ROLE = "ROLE_CONFIRMED_USER";

    @Autowired
    UserRepository userDao;

    @Autowired
    RoleRepository roleDao;

    @Autowired
    NotificationRepository notificationDao;

    @Transactional
    public void register(RegistrationModel registrationModel) {
        validateEmail(registrationModel);

        UserEntity newUser = new UserEntity();
        newUser.setEmail(registrationModel.getEmail());
        newUser.setPassword(registrationModel.getPassword());
        newUser.setTitle(registrationModel.getTitle());
        newUser.setPhone(registrationModel.getPhone());
        newUser.setConfirmed(false);
        newUser.setConfirmedDate(null);
        RandomString rnd = new RandomString(TOKEN_LENGTH);
        newUser.setConfirmToken(rnd.nextString());
        newUser.setEnabled(true);

        userDao.insert(newUser);

        RoleEntity userRole = roleDao.getByTitle(BASE_USER_ROLE);
        RoleEntity confirmedUserRole = roleDao.getByTitle(CONFIRMED_USER_ROLE);
        assert (userRole != null);
        newUser.getRoles().add(userRole);
        newUser.getRoles().add(confirmedUserRole);

        //NotificationEntity newNot = NotificationBuilder.buildConfirmCodeNotification(newUser.getConfirmToken(), newUser);
        //notificationDao.insert(newNot);
    }

    @Transactional
    public void confirmUser(String inputToken, String userName) {
        validateToken(inputToken);

        UserEntity user = userDao.findByEmail(userName);
        Helpers.assertNotNull(user);

        if (user.getConfirmToken().equals(inputToken)) {
            RoleEntity userRole = roleDao.getByTitle(CONFIRMED_USER_ROLE);
            Helpers.assertNotNull(userRole);

            user.getRoles().add(userRole);
            user.setConfirmToken("");
        } else {
            throw new RuntimeException("Код подтверждения не верный");
        }
    }

    private void validateToken(String inputToken) {
        if (Helpers.isNullOrWhitespace(inputToken)) {
            throw new RuntimeException("Код подтверждения не корректен");
        }
    }

    @Transactional
    public List<String> getCurrentRoles(String userName) {
        UserEntity user = userDao.findByEmail(userName);
        return user.getRoles().stream().map(RoleEntity::getTitle).collect(Collectors.toList());
    }

    @Transactional
    public void changePassword(String email, String password, String newPassword) {
        UserEntity user = userDao.findByEmail(email);
        String oldPassword = user.getPassword();
        if (oldPassword.equals(password)) {
            user.setPassword(newPassword);
        } else {
            throw new RuntimeException("Неверный старый пароль");
        }
    }

    private void validateEmail(RegistrationModel registrationModel) {
        List<UserEntity> users = userDao.getAll();

        if (Helpers.isNullOrWhitespace(registrationModel.getEmail()) || !Helpers.isValidEmailAddress(registrationModel.getEmail())) {
            throw new RuntimeException("Почтовый адрес заполнен не корректно");
        }

        users.forEach(t -> {
            if (t.getEmail().equals(registrationModel.getEmail())) {
                throw new RuntimeException("Почтовый адрес уже зарегистрирован на сайте");
            }
        });
    }
}
