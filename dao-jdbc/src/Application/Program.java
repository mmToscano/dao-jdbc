package Application;

import model.dao.DepartmentDao;
import model.dao.SellerDao;
import model.dao.daoFactory;
import model.dao.impl.SellerDaoJDBC;
import model.entities.Department;
import model.entities.Seller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Program {

    public static void main(String[] args) {
        SellerDao sellerDao = daoFactory.createSellerDao(); //aqui ocorre, acredito eu, um downcasting. Aí com ele dá para retornar o seller.
        DepartmentDao departmentDao = daoFactory.createDepartmentDao();

        System.out.println("=== TEST === findById-Seller");
        Seller seller = sellerDao.findById(3);

        System.out.println(seller);

        System.out.println("\n=== TEST === findByDepartment-Seller");
        Department dep = new Department(2, null);
        List<Seller> sellerList = sellerDao.findByDepartment(dep);

        System.out.println(sellerList);

        System.out.println("\n=== TEST === findByDepartment-Seller");
        List<Seller> allSellers = sellerDao.findAll();

        System.out.println(allSellers);

        System.out.println("=== TEST === insert-Seller");
        Seller newSeller = new Seller(null, "Greg", "Greg@tau", new Date(), 4000.00, dep);
        sellerDao.insert(newSeller);

        System.out.println(allSellers);


    }
}
