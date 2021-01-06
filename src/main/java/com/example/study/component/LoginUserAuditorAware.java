package com.example.study.component;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class LoginUserAuditorAware implements AuditorAware<String> {

    @Override//감시자 설정
    public Optional<String> getCurrentAuditor() {
        return Optional.of("AdminServer");
    }
    /*
    이렇게 하면 entity에 @CreatedBy, @LastModifiedBy,
    @CreatedDate, @LastModifiedDate로 지정된 변수에
    자동으로 AdminServer라고 할당해 준다.
     */
}
