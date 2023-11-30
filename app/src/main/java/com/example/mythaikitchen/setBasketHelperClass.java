package com.example.mythaikitchen;

public class setBasketHelperClass {
    String totalCostString , food1, food2, food3, food4, food5, food6, quantity1str, quantity2str, quantity3str, quantity4str, quantity5str, quantity6str, meat1, meat2, meat3, timeFormat, deliveryStatusStr, payment;
    public setBasketHelperClass(String totalCostString, String food1, String food2, String food3, String food4, String food5, String food6, String quantity1str, String quantity2str, String quantity3str, String quantity4str, String quantity5str, String quantity6str, String meat1, String meat2, String meat3, String timeFormat, String deliveryStatusStr, String payment){
        this.totalCostString = totalCostString;
        this.food1 = food1;
        this.food2 = food2;
        this.food3 = food3;
        this.food4 = food4;
        this.food5 = food5;
        this.food6 = food6;
        this.quantity1str = quantity1str;
        this.quantity2str = quantity2str;
        this.quantity3str = quantity3str;
        this.quantity4str = quantity4str;
        this.quantity5str = quantity5str;
        this.quantity6str = quantity6str;
        this.meat1 = meat1;
        this.meat2 = meat2;
        this.meat3 = meat3;
        this.timeFormat = timeFormat;
        this.deliveryStatusStr = deliveryStatusStr;
        this.payment = payment;
    }


    public String getTotalCostString() {
        return totalCostString;
    }

    public String getFood1() {
        if (!quantity1str.equals("0")) {
            return food1;
        }
        return null;
    }

    public String getFood2() {
        if (!quantity2str.equals("0")) {
            return food2;
        }
        return null;
    }

    public String getFood3() {
        if (!quantity3str.equals("0")) {
            return food3;
        }
        return null;
    }

    public String getFood4() {
        if (!quantity4str.equals("0")) {
            return food4;
        }
        return null;
    }

    public String getFood5() {
        if (!quantity5str.equals("0")) {
            return food5;
        }
        return null;
    }

    public String getFood6() {
        if (!quantity6str.equals("0")) {
            return food6;
        }
        return null;
    }

    public String getQuantity1str() {
        if (!quantity1str.equals("0")) {
            return quantity1str;
        }
        return null;
    }

    public String getQuantity2str() {
        if (!quantity2str.equals("0")) {
            return quantity2str;
        }
        return null;
    }

    public String getQuantity3str() {
        if (!quantity3str.equals("0")) {
            return quantity3str;
        }
        return null;
    }

    public String getQuantity4str() {
        if (!quantity4str.equals("0")) {
            return quantity4str;
        }
        return null;
    }

    public String getQuantity5str() {
        if (!quantity5str.equals("0")) {
            return quantity5str;
        }
        return null;
    }

    public String getQuantity6str() {
        if (!quantity6str.equals("0")) {
            return quantity6str;
        }
        return null;
    }

    public String getMeat1() {
        return meat1;
    }

    public String getMeat2() {
        return meat2;
    }

    public String getMeat3() {
        return meat3;
    }

    public String getTimeFormat() {return timeFormat;}
    public String getDeliveryStatus(){return deliveryStatusStr;}
    public String getPayment(){return payment;}
}
