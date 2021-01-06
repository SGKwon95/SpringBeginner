package com.example.study.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration//설정 파일이라는것을 알려주는 annotation
@EnableJpaAuditing//Jpa 감시 활성화
public class JpaConfig {

}
