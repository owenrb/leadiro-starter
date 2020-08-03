package com.leadiro.starter.service.starter;

import com.leadiro.starter.service.StarterService;
import com.leadiro.starter.service.starter.dao.StarterMapper;
import com.leadiro.starter.service.starter.dto.Starter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * An example concrete implementation of the StarterService.
 */
@Service
@Slf4j
public class ConcreteStarterService implements StarterService {
    @Autowired private StarterMapper mapper;

    @Override
    public Starter getStarter(int id) {
        log.debug("Getting Starter {}", id);
        return mapper.getStarter(id);
    }

}
