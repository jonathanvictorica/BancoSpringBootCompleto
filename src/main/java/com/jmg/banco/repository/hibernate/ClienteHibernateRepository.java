package com.jmg.banco.repository.hibernate;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jmg.banco.domain.Cliente;

@Repository
public interface ClienteHibernateRepository extends CrudRepository<Cliente, Long> {

	Optional<Cliente> findByNroDocumento(Long nroDocumento);

}
