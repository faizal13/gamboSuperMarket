package com.gamboSupermarket.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gamboSupermarket.application.dto.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

}
