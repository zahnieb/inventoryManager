import com.manager.Inventory;
import com.manager.Item;
import com.manager.Customer;


import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class InventoryTest {
    @Test
    public void getItemsTest(){
        Inventory test = new Inventory();
        List<Item> result = test.getInventory();
        System.out.println(result.get(0).getName());

        //size varies with tests done prev/added/removed
        System.out.println(result.size());
    }

    @Test
    public void getTotalInventoryValue(){
        Inventory test = new Inventory();
        Double testResult = test.getTotalValue();

        //sum with getting Items by Item list
        double secondSum = 0;
        List<Item> result = test.getInventory();
        for(Item item: result){
            secondSum += item.getSalesPrice();
        }

        assert(testResult==secondSum);
    }
}
