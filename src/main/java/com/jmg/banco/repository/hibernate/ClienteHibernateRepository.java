package com.jmg.banco.repository.hibernate;

import com.jmg.banco.domain.Cliente;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteHibernateRepository extends CrudRepository<Cliente, Long> {

    Optional<Cliente> findByNroDocumento(Long nroDocumento);

}
