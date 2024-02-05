package lk.ijse.gdse.back_end.bo;

import lk.ijse.gdse.back_end.bo.custom.impl.CustomerBOImpl;

public class BOFactory {
    private static BOFactory boFactory;

    private BOFactory(){

    }

    public static BOFactory getBoFactory(){
        return (boFactory == null) ? boFactory = new BOFactory() : boFactory;
    }

    public enum BOTypes{
        CUSTOMER_BO
    }

    public <T extends SuperBO>T getBO(BOTypes res){
        switch (res){
            case CUSTOMER_BO:
                return (T) new CustomerBOImpl();
            default:
                return null;
        }
    }
}
