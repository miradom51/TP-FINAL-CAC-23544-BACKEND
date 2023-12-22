package com.cac.repository;


import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.cac.entity.Personal;
import com.cac.utils.DateUtils;



public class MySqlPersonalRepository implements PersonalRepository {

	public void save(Personal personal) {
	    String sql = "INSERT INTO personal (nombre, apellido, domicilio, email, telefono, dni, fecha_alta) VALUES (?,?,?,?,?,?,?)";

	    try (Connection con = AdministradorDeConexiones.getConnection()) {
	        PreparedStatement statement = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
	        statement.setString(1, personal.getNombre());
	        statement.setString(2, personal.getApellido());
	        statement.setString(3, personal.getDomicilio());
	        statement.setString(4, personal.getEmail());
	        statement.setString(5, personal.getTelefono());
	        statement.setString(6, personal.getDni());
	        statement.setDate(7, java.sql.Date.valueOf(personal.getFechaAlta()));

	        statement.executeUpdate();

	        ResultSet res = statement.getGeneratedKeys();
	        if (res.next()) {
	            Long id = res.getLong(1);
	            personal.setId(id);
	        }
	    } catch (Exception e) {
	        throw new IllegalArgumentException("No se pudo crear el personal:", e);
	    }
	}


	public Personal getById(Long id) {
	    String sql = "select id, nombre, apellido, domicilio, telefono, dni, email, fecha_alta from personal where id = ?";

	    Personal personal = null;
	    try(Connection con = AdministradorDeConexiones.getConnection()) {
	        PreparedStatement statement = con.prepareStatement(sql);
	        statement.setLong(1, id);

	        ResultSet res = statement.executeQuery();// SELECT

	        if (res.next()) {
	            Long dbId = res.getLong(1);  
	            String nombre = res.getString(2);  
	            String apellido = res.getString(3);  
	            String email = res.getString(4);  
	            String domicilio = res.getString(5); 
	            String telefono = res.getString(6); 
	            String dni = res.getString(7); 
	            Date fechaAlta = res.getDate(8);  

	            personal = new Personal(dbId, nombre, apellido, email, domicilio, telefono, dni, DateUtils.asLocalDate(fechaAlta));
	        }

	    } catch (Exception e) {
	        throw new IllegalArgumentException("No se pudo crear el personal:", e);
	    }
	    return personal;
	}

	@Override
    public void update(Personal personal) {
        String sql = "update personal "
                + "set nombre=?, apellido=?, email=?, domicilio=?, telefono=?, dni=? "
                + "where id = ?";

        // try with resources
        try (Connection con = AdministradorDeConexiones.getConnection();
             PreparedStatement statement = con.prepareStatement(sql)) {

            statement.setString(1, personal.getNombre());
            statement.setString(2, personal.getApellido());
            statement.setString(3, personal.getEmail());
            statement.setString(4, personal.getDomicilio());
            statement.setString(5, personal.getTelefono());
            statement.setString(6, personal.getDni());
            statement.setLong(7, personal.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositorioException("No se pudo actualizar el personal", e);
        }
    }


	@Override
	public void delete(Long id) {
		
		String sql = "delete from personal where id = ?";
		
		//try with resources
		try(Connection con = AdministradorDeConexiones.getConnection()) {
			
			PreparedStatement statement = con.prepareStatement(sql);
			
			statement.setLong(1, id);
			
			statement.executeUpdate();
		}catch (Exception e) {
			throw new IllegalArgumentException("No se pudo eliminar el personal:", e);
		}
	}

	public List<Personal> findAll() {
	    List<Personal> personalList = new ArrayList<>();

	    try (Connection con = AdministradorDeConexiones.getConnection()) {
	        String sql = "SELECT * FROM personal";
	        try (PreparedStatement statement = con.prepareStatement(sql)) {
	            try (ResultSet resultSet = statement.executeQuery()) {
	            	while (resultSet.next()) {
	            	    Personal personal = new Personal(
	            	        resultSet.getLong("id"),
	            	        resultSet.getString("nombre"),
	            	        resultSet.getString("apellido"),
	            	        resultSet.getString("email"),
	            	        resultSet.getString("domicilio"),
	            	        resultSet.getString("telefono"),
	            	        resultSet.getString("dni"),
	            	        resultSet.getDate("fecha_alta").toLocalDate()
	            	    );

	            	    personalList.add(personal);
	            	}

	            }
	        }
	    } catch (SQLException e) {
	        throw new RepositorioException("No se pudo obtener la lista de personal", e);
	    }

	    return personalList;
	}


}