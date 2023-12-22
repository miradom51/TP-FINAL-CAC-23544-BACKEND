package com.cac.repository;


import java.util.List;

import com.cac.entity.Personal;


public interface PersonalRepository {
	public void save(Personal personal);

	public Personal getById(Long id);

	public void update(Personal personal);

	public void delete(Long id);

	public List<Personal> findAll();
}