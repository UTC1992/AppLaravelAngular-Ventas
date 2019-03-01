package com.jmc.backend.ventas.apirest.models.services.Interfaces;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.jmc.backend.ventas.apirest.models.entity.Cliente;
import com.jmc.backend.ventas.apirest.models.entity.Venta;

public interface IClienteService {
	
	public List<Cliente> findAll(Long idEmpresa);
	public Page<Cliente> findAll(Pageable pageable);
	public Cliente findById(Long Id);
	public Cliente save(Cliente cliente);
	public void delete(Long id);
	public Cliente findByEmail(String email, Long idEmpresa);
	public Cliente findByCedula(String cedula, Long idEmpresa);
	public Cliente findByRuc(String ruc, Long idEmpresa);
	
	/**	
	 * metodos de facturas clientes
	 */
	
	public Venta findVentaById(Long id);
	public Venta saveVenta(Venta venta);
	public void deleteVentaById(Long id);
	
}
