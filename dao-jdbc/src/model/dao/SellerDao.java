package model.dao;

import model.entities.Department;
import model.entities.Seller;

import java.util.List;

/*
O sellerDae o o DepartmentDao são as interfaces que definem os métodos de manipulação do banco. O sellerDaoJDBC e o Department são as
classes concretas que irão modificar as classes department e seller

 */

public interface SellerDao {

    void insert(Seller obj);
    void update(Seller obj);
    void deleteById(Seller obj);
    Seller findById(Integer id);
    List<Seller> findByDepartment(Department department);
    List<Seller> findAll();
}
