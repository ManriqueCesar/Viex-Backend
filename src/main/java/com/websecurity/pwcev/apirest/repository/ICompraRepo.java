package com.websecurity.pwcev.apirest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.websecurity.pwcev.apirest.model.NumeroCompra;

@Repository
public interface ICompraRepo extends JpaRepository<NumeroCompra, Integer> {

}
