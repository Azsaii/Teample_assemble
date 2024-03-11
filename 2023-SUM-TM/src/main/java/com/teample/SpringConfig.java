package com.teample;

import com.teample.packages.member.repository.*;
import com.teample.packages.profile.repository.MemoryProfileRepository;
import com.teample.packages.profile.repository.ProfileRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    @Bean
    public MemberRepository memberRepository() { //memberRepository 빈 등록
        return new MemoryMemberRepository();
    }

    @Bean
    public ProfileRepository profileRepository() { //profileRepository 빈 등록
        return new MemoryProfileRepository();
    }
}