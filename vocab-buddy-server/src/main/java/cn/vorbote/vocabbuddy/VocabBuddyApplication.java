package cn.vorbote.vocabbuddy;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@Slf4j
@EnableScheduling
@MapperScan("cn.vorbote.vocabbuddy.rep")
@SpringBootApplication
public class VocabBuddyApplication {

    public static void main(String[] args) {
        SpringApplication.run(VocabBuddyApplication.class, args);
    }

}
