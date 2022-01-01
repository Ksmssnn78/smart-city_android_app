package com.example.smartcityapp2;

public class Bill_Records {
    String Name,mail,phone,date,BillNo,ConsumerNo;
    long Amount;

    public Bill_Records() {
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBillNo() {
        return BillNo;
    }

    public void setBillNo(String billno) {
        this.BillNo = billno;
    }

    public String getConsumerno() {
        return ConsumerNo;
    }

    public void setConsumerno(String consumerno) {
        this.ConsumerNo = consumerno;
    }

    public long getAmount() {
        return Amount;
    }

    public void setAmount(long amount) {
        this.Amount = amount;
    }
}
