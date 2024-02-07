package lk.ijse.gdse.back_end.bo;

import lk.ijse.gdse.back_end.bo.custom.impl.*;

public class BOFactory {
    private static BOFactory boFactory;

    private BOFactory(){

    }

    public static BOFactory getBoFactory(){
        return (boFactory == null) ? boFactory = new BOFactory() : boFactory;
    }

    public enum BOTypes{
        CUSTOMER_BO, ITEM_BO, PLACE_ORDER_BO, ORDER_DETAIL_BO, LOG_IN_BO, SIGN_UP_BO
    }

    public <T extends SuperBO>T getBO(BOTypes res){
        switch (res){
            case CUSTOMER_BO:
                return (T) new CustomerBOImpl();
            case ITEM_BO:
                return (T) new ItemBOImpl();
            case PLACE_ORDER_BO:
                return (T) new PlaceOrderBOImpl();
            case ORDER_DETAIL_BO:
                return (T) new OrderDetailBOImpl();
            default:
                return null;
        }
    }
}
