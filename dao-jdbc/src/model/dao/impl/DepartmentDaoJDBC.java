package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.DepartmentDao;
import model.entities.Department;
import model.entities.Seller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DepartmentDaoJDBC implements DepartmentDao {

    private Connection conn;

    public DepartmentDaoJDBC(Connection conn){
        this.conn = conn;
    }

    @Override
    public void insert(Department obj) {

    }

    @Override
    public void update(Department obj) {

    }

    @Override
    public void deleteById(Department obj) {

    }

    @Override
    public Department findById(Integer id) {

        return null;
    }

    @Override
    public List<Department> findAll() {
        return null;
    }

    private Seller instantiateSeller(Department dep, ResultSet rs) throws SQLException {
        Seller sell = new Seller();
        sell.setId(rs.getInt("Id"));
        sell.setName(rs.getString("Name"));
        sell.setEmail(rs.getString("Email"));
        sell.setBaseSalary(rs.getDouble("BaseSalary"));
        sell.setBirthDate(rs.getDate("birthDate"));
        sell.setDepartment(dep);
        return sell;
    }

    private Department instantiateDepartment(ResultSet rs) throws SQLException { //O tratamento está sendo propagado, pois caso uma exceção seja lançada, ela será tratada nos catches nos métodos acima (como, por exemplo, na página 67)
        Department dep = new Department();
        dep.setId(rs.getInt("Id"));
        dep.setName(rs.getString("Name"));
        return dep;
    }
}
