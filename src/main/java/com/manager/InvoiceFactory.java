package com.manager;

//Factory, will add purchase Invoice if time allows, setup factory for one type but
//***open for extension ****//
public class InvoiceFactory {
    public enum Type {SALES};

    public Invoice create(Type type){
        if (type == Type.SALES){
            return new SalesInvoice();
        }

        return null;
    }
}
