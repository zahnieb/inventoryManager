import com.manager.Customer;
import com.manager.Inventory;
import com.manager.Item;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class CustomerTest {
    @Test
    public void newCustomerTest(){
        Inventory test = new Inventory();
        Customer customerTest = new Customer();

        customerTest.setCustomerName("Custard Test");
        customerTest.setPhone("712872733");

        test.addCustomer(customerTest);
    }

    @Test
    public void getCustomersListTest(){
        Inventory test = new Inventory();
        List<Customer> result = test.getCustomersList();

        //prints name of first on list
        System.out.println(result.get(0).getCustomerName());

        //size varies with tests done prev/added/removed
        System.out.println(result.size());
    }

    @Test
    public void removeCustomerByIDTest(){
        Inventory test = new Inventory();
        Customer testCustomer = new Customer();
        testCustomer.setCustomerName("Remove this Customer");
        testCustomer.setPhone("7263651289");

        test.addCustomer(testCustomer);
        int testCustomerID = testCustomer.getCustomerID();

        test.removeCustomerByID(testCustomerID);
    }

    @Test
    public void getCustomerNameTest(){
        Inventory test = new Inventory();
        List<Customer> customerList = test.getCustomersList();
        List<String> nameList = new ArrayList<>();

        for (Customer customer: customerList){
            nameList.add(test.getCustomerName(customer.getCustomerID()));
        }

        for(String name: nameList){
            System.out.println(name);
        }

        assert(nameList.get(0).equals("Custard Test"));
    }
}
