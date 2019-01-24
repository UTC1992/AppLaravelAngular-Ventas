package com.jmc.backend.ventas.apirest.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jmc.backend.ventas.apirest.models.dao.IRolesDao;
import com.jmc.backend.ventas.apirest.models.entity.Role;

@Service
public class RoleServiceImplement implements IRoleService{

	@Autowired 
	private IRolesDao roleDao;
	@Override
	public List<Role> findAll() {
		// TODO Auto-generated method stub
		
		return roleDao.findAll();
	}

	@Override
	public Role findById(Long id) {
		// TODO Auto-generated method stub
		return roleDao.findById(id).orElse(null);
	}

}
