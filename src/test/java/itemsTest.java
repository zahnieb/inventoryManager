import com.manager.Inventory;
import com.manager.Item;
import com.manager.RetailStrategy;
import org.junit.Test;

import java.util.Scanner;

public class itemsTest {
    @Test
    public void itemNameTest(){
        Inventory test = new Inventory();
        String result = test.getItemName(1);

        System.out.println(result);

        assert(result.equals("Testing Item"));
    }

    @Test
    public void removeItemTest(){
        Inventory test = new Inventory();

        int prevNumber = test.getInventory().size();

        //will always work due to number of removed prev.
        int num = test.getInventory().size()-1;

        test.removeItemFromInventory(num);

        assert(test.getInventory().size()==prevNumber-1);
    }

    @Test
    public void dbAddItemTest(){
        Inventory test = new Inventory();
        Item testItem = new Item();

        testItem.setDescription("Testing description for Item");
        testItem.setName("Testing Item");
        testItem.setPurchasePrice(3.01);
        testItem.setSalesStrategy(new RetailStrategy());
        testItem.setSalesPrice(5.15);

        test.addItem(testItem);
        System.out.println(testItem.getSalesPrice());
    }
}