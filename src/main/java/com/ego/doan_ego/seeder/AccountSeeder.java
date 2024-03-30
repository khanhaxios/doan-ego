package com.ego.doan_ego.seeder;

import com.ego.doan_ego.constant.UserRole;
import com.ego.doan_ego.entities.AccountDao;
import com.ego.doan_ego.entities.UserDao;
import com.ego.doan_ego.repository.AccountRepository;
import com.ego.doan_ego.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class AccountSeeder implements CommandLineRunner {

    private final AccountRepository accountRepository;

    private final UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        AccountDao accountDao = accountRepository.getAccountByUsername("kandev");
        if (accountDao == null) {
            accountDao = new AccountDao();
            accountDao.setRole(UserRole.ROLE_TEACHER);
            accountDao.setPassword(new BCryptPasswordEncoder().encode("13122002"));
            accountDao.setUsername("kandev");
            UserDao userDao = new UserDao();
            userDao.setEmail("dangkdev@gmail.com");
            userDao.setPhone("0376658437");
            userDao.setFullName("dangduykhanh");
            userDao.setDateOfBirth(System.currentTimeMillis());
            userDao.setNickName("KhanhDaiCa");
            accountDao.setUserId(userRepository.save(userDao).getId());
            accountRepository.save(accountDao);
        }
    }
}
