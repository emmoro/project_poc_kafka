package com.br.elton.consumer.adapters.repository;

import com.br.elton.consumer.adapters.entity.ConsoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsoleRepository extends JpaRepository<ConsoleEntity, Long> {

    public ConsoleEntity findByName(String name);

    public List<ConsoleEntity> findAllByOrderById();

}
