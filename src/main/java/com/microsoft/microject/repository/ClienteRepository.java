package com.microsoft.microject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microsoft.microject.domain.Cliente;

public interface ClienteRepository extends JpaRepository <Cliente, Long> {

}
