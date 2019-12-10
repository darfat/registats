package com.darfat.registats.service;

import com.darfat.registats.domain.Position;
import com.darfat.registats.repository.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PositionService {
    @Autowired
    PositionRepository positionRepository;


    public Position findPosition(String name){
        return positionRepository.findOneByName(name);
    }
}
