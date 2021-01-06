package com.example.study.repository;

import com.example.study.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    /*
    // select * from user where account = ?
    Optional<User> findByAccount(String account);
    //String account는 Account에 매칭된다. ac라고 써도 상관없다.
    //하지만 동일하게 맞춰주는것을 권장한다.

    Optional<User> findByEmail(String email);

    // select * from user where account = ? and email = ?
    Optional<User> findByAccountAndEmail(String account, String email);
    //String account는 Account, email은 Email에 매칭된다.
     */
    //query문을 아예 함수명으로 쓸 수 있다.
    // select user where phoneNumber = ? order by id desc
    Optional<User> findFirstByPhoneNumberOrderByIdDesc(String phoneNumber);
    //User findFirstByPhoneNumberOrderByIdDesc(String phoneNumber);
}
