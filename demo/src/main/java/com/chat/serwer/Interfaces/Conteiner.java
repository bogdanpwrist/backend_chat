package com.chat.serwer.Interfaces;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chat.serwer.DBO.ContainerRepository;

@Repository
public interface Conteiner extends JpaRepository<ContainerRepository, String> {
    ArrayList<ContainerRepository> findAll();
}
