package com.jmg.banco.repository.hibernate;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jmg.banco.domain.CuentaBancaria;

/*
Hay tres formas en su caso:

Anotación @Query (el mejor candidato para usted) http://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.at-query =
Consultas con nombre http://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.named-queries
Especificaciones http://docs.spring.io/spring-data/jpa/docs/current/reference/html/#specifications

*/
@Repository
public interface CuentaBancariaHibernateRepository extends CrudRepository<CuentaBancaria, Long> {

	/*
	 * @Query is for defining custom query and @Modifying is for telling
	 * spring-data-jpa that this query is an update operation and it requires
	 * executeUpdate() not executeQuery().
	 */
	@Modifying(flushAutomatically = true, clearAutomatically = true)
	@Query("update CuentaBancaria u set u.saldoCuenta = ?1, u.fechaActualizacionSaldo = ?2 where u.id = ?3")
	void setSaldoAndFechaSaldoById(BigDecimal saldoCuenta, Date fechaActualizacionSaldo, Long idCuenta);

	boolean existsByAliasAndEstadoCuenta(String alias, String name);

	boolean existsByIdAndEstadoCuenta(Long idCuentaBancaria, String name);

	@Modifying(flushAutomatically = true, clearAutomatically = true)
	@Query("update CuentaBancaria u set u.alias = ?1 where u.id = ?2")
	void setAliasById(String aliasNuevo, Long idCuentaBancaria);

	@Query("select u from CuentaBancaria u inner join u.cliente as clie where clie.id = ?1 and u.estadoCuenta = ?2 order by u.alias asc")
	List<CuentaBancaria> findByIdClienteByEstado(Long idCliente, String name);

	@Query("select u from CuentaBancaria u inner join u.cliente as clie where clie.tipoDocumento = ?1 and clie.nroDocumento = ?2 and u.estadoCuenta = ?3  order by u.alias asc")
	List<CuentaBancaria> findAllByDocumentoClienteByEstado(String tipoDocumento, String numeroDocumento, String name);

	@Query("select u.saldoCuenta from CuentaBancaria u where u.id = ?1 and u.estadoCuenta = ?2")
	BigDecimal findSaldoCuentaByIdAndEstado(Long idCuentaBancaria, String name);

}
