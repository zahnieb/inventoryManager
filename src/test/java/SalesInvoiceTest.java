import com.manager.Inventory;
import com.manager.Invoice;
import com.manager.InvoiceFactory;
import com.manager.SalesInvoice;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class SalesInvoiceTest {
    @Test
    public void newSalesInvoiceTest(){
        Inventory test = new Inventory();
        test.newSalesInvoice();
    }

    @Test
    public void getSalesInvoiceList(){
        Inventory test = new Inventory();
        List<SalesInvoice> invoiceList = new ArrayList<>();

        System.out.println(test.getSalesInvoiceList());
        for(SalesInvoice invoice: invoiceList){
            System.out.println("Invoice Number: "+invoice.getSalesInvoiceNumber());
            System.out.println("Total: "+invoice.getTotal());
            System.out.println("Cx ID: "+invoice.getCustomerID());
        }
    }

    @Test
    public void deleteSalesInvoiceTest(){
        Inventory test = new Inventory();
        //List<SalesInvoice> invoiceList = new ArrayList<>();
        //TODO: Remove invoice test automation & assert with prev/after invoiceList size
        int newInvoiceNumber = test.newSalesInvoice();
        System.out.println(test.getSalesInvoiceList());

        test.deleteInvoice(newInvoiceNumber);
    }

    @Test
    public void insertToSalesInvoiceTest(){
        Inventory test = new Inventory();
        //test.insertToSalesInvoice(6, 10,  10, 1);
        test.insertToSalesInvoice(2,1,10,1);
        //test.insertToSalesInvoice(6,3, 9, 1);

        test.getSalesInvoiceList();
    }

    @Test
    public void getSalesInvoiceTest(){
        Inventory test = new Inventory();
        InvoiceFactory invoiceFactory = new InvoiceFactory();
        Invoice invoice = invoiceFactory.create(InvoiceFactory.Type.SALES);

        invoice = test.getSalesInvoice(2);
        System.out.println(invoice.getTotal());

        System.out.println(invoice.getCustomerID());
        assert(invoice.getCustomerID()==1);
    }

    @Test
    public void getInvoiceItemsTest(){
        Inventory test = new Inventory();
        System.out.println(test.getSalesInvoiceItems(2));
    }

    //TODO: finalize sale & update total purchased customer in DB
    @Test
    public void finalizeSaleTest(){
        Inventory test = new Inventory();
        test.finalizeSale(6);

        assert(test.getCustomersList().get(0).getTotalPurchases() > 0);
    }
}
