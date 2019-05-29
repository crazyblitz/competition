package com.ley.innovation.contest.configuration;

import com.ley.innovation.contest.utils.BeanMapper;
import lombok.extern.slf4j.Slf4j;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
@ConditionalOnClass(value = {Mapper.class, DozerBeanMapper.class})
@Slf4j
public class DozerConfiguration {

    @Value("${dozer.mappingLocation}")
    private String mappingLocations;


    @Bean
    @ConditionalOnMissingBean
    public DozerBeanMapper dozerMapper() {
        String[] mappingLocationList = mappingLocations.split(",");
        List<String> mappingFiles = Arrays.asList(mappingLocationList);
        DozerBeanMapper dozerBeanMapper = new DozerBeanMapper();
        dozerBeanMapper.setMappingFiles(mappingFiles);
        log.info("dozer mapping locations: {}", mappingFiles);
        return dozerBeanMapper;
    }


    @Bean
    @ConditionalOnMissingBean
    public BeanMapper beanMapper() {
        return new BeanMapper();
    }
}
