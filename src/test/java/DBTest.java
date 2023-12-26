import com.database.ConnectDatabase;
import com.manager.Inventory;
import com.manager.Item;
import com.manager.RetailStrategy;
import org.junit.Test;

import java.util.List;


public class DBTest {
    @Test
    public void dbTest() {
        ConnectDatabase test = new ConnectDatabase();
        test.getConnection();
    }
}