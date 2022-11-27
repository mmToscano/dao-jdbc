package model.dao;

import model.entities.Department;

import java.util.List;

public interface DepartmentDao {

    void insert(Department obj);
    void update(Department obj);
    void deleteById(Department obj);
    Department findById(Department obj);
    List<Department> findAll();
}
