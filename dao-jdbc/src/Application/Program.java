package Application;

import model.entities.Department;
import model.entities.Seller;

public class Program {

    public static void main(String[] args) {
        Department obj = new Department(1, "books");
        Seller seller = new Seller();

        System.out.println(obj);
        System.out.println(seller);
    }
}
