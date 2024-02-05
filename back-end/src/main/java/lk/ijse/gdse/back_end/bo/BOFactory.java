package lk.ijse.gdse.back_end.bo;

import lk.ijse.gdse.back_end.bo.custom.impl.CustomerBOImpl;
import lk.ijse.gdse.back_end.bo.custom.impl.ItemBOImpl;

public class BOFactory {
    private static BOFactory boFactory;

    private BOFactory(){

    }

    public static BOFactory getBoFactory(){
        return (boFactory == null) ? boFactory = new BOFactory() : boFactory;
    }

    public enum BOTypes{
        CUSTOMER_BO, ITEM_BO
    }

    public <T extends SuperBO>T getBO(BOTypes res){
        switch (res){
            case CUSTOMER_BO:
                return (T) new CustomerBOImpl();
            case ITEM_BO:
                return (T) new ItemBOImpl();
            default:
                return null;
        }
    }
}
