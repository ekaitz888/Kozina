package eus.kozina.model.daoimpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import eus.kozina.model.Conector;
import eus.kozina.model.bean.Alimento;
import eus.kozina.model.bean.Ingrediente;
import eus.kozina.model.dao.IngredienteModelo;

public class IngredienteModeloImp extends Conector implements IngredienteModelo {

	@Override
	public ArrayList<Ingrediente> ingredientes(int id_receta) {
		ArrayList<Ingrediente> ingredientes = new ArrayList<Ingrediente>();

		try {
			PreparedStatement pst = this.conexion.prepareStatement(
					"SELECT alimentos.*, ingredientes.cantidad as cantidad FROM ingredientes INNER JOIN alimentos on ingredientes.id_alimento= alimentos.id WHERE ingredientes.id_receta=?");
			pst.setInt(1, id_receta);

			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				
				Alimento alimento = new Alimento(rs.getString("nombre"));
				alimento.setId(rs.getInt("id"));
				alimento.setDescripcion(rs.getString("descripcion"));
				
				Ingrediente ingrediente = new Ingrediente();
				ingrediente.setAlimento(alimento);
				ingrediente.setCantidad(rs.getInt("cantidad"));
			
				ingredientes.add(ingrediente);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return ingredientes;
	}

}
