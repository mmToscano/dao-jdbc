package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellerDaoJDBC implements SellerDao {

    private Connection conn;

    public SellerDaoJDBC(Connection conn){
        this.conn = conn;
    }

    @Override
    public void insert(Seller obj) {

        PreparedStatement st = null;

        try {
            st = conn.prepareStatement("insert into seller " +
                    "(Name, Email, BirthDate, BaseSalary, DepartmentId) " +
                    "values (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            st.setString(1, obj.getName());
            st.setString(2, obj.getEmail());
            st.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
            st.setDouble(4, obj.getBaseSalary());
            st.setInt(5, obj.getDepartment().getId());

            int rowsAffected = st.executeUpdate();

            if(rowsAffected > 0){
                ResultSet rs = st.getGeneratedKeys();
                if(rs.next()){
                    int id = rs.getInt(1);
                    obj.setId(id);
                }
                DB.closeResultSet(rs);
            }else{
                throw new DbException("Erro inesperado. Linha não inserida");
            }
        }catch(SQLException e){
            throw new DbException(e.getMessage());
        }finally {
            DB.closeStatement(st);
        }

    }

    @Override
    public void update(Seller obj) {

    }

    @Override
    public void deleteById(Seller obj) {

    }

    @Override
    public Seller findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement("" +
                    "SELECT seller.*,department.Name as DepName " +
                    "FROM seller INNER JOIN department " +
                    "ON seller.DepartmentId = department.Id " +
                    "WHERE seller.Id = ?");

            st.setInt(1, id);
            rs = st.executeQuery();
            //No java, tudo é orientado à objeto. Por isso, quando o rs retornar com uma tabela das informações buscadas, é preciso transformar tais informações em objetos no java
            if (rs.next()){
                Department dep = instantiateDepartment(rs);
                Seller sell = instantiateSeller(dep, rs);

                return sell;
            }
            return null;
        }catch(SQLException e){
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Seller> findByDepartment(Department dep) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement("" +
                    "select seller.*, department.Name " +
                    "from seller inner join department " +
                    "on seller.DepartmentId = department.Id " +
                    "where DepartmentId = ? " +
                    "order by seller.Name;");

            st.setInt(1, dep.getId());//O comando foi construído
            rs = st.executeQuery();//O resultado do comando foi colocado no resultSet

            //cria a lista e o map
            List<Seller> sellers = new ArrayList<>();
            Map<Integer, Department> map = new HashMap<>();

            //O método next faz o resultSet passar para a tupla de baixo.
            while (rs.next()){

                //Vai procurar um objeto department no map. Se não encontrar (como vai ser no primeiro loop), é criado um department e colocado no map.
                Department department = map.get(rs.getInt("Id"));

                if(department == null){
                    department = instantiateDepartment(rs);
                    map.put(rs.getInt("DepartmentId"), dep);
                }

                Seller seller = instantiateSeller(department, rs);
                sellers.add(seller);
            }
            return sellers;
        }catch (SQLException e){
            throw new DbException(e.getMessage());
        }finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Seller> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement("" +
                    "select seller.*, department.Name " +
                    "from seller inner join department  " +
                    "on seller.DepartmentId = department.Id " +
                    "order by seller.Name");
            rs = st.executeQuery();

            List<Seller> sellers = new ArrayList<>();
            Map<Integer, Department> map = new HashMap<>();

            while (rs.next()){

                Department department = map.get(rs.getInt("Id"));

                if(department == null){
                    department = instantiateDepartment(rs);
                    map.put(rs.getInt("DepartmentId"), department);
                }

                Seller seller = instantiateSeller(department, rs);
                sellers.add(seller);

            }
            return sellers;
        }catch (SQLException e){
            throw new DbException(e.getMessage());
        }finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
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
        dep.setId(rs.getInt("DepartmentId"));
        dep.setName(rs.getString("Name"));
        return dep;
    }


}
