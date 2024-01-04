package com.example.store_analysis.naverapi;

public class Category
{
    public String device = null;
    public String gender = null;
    public String ages = null;
    public String request;

    public Category(String startDate, String endDate, String timeUnit, String category_name, String category_param)
    {
        request = String.format("{\"startDate\":\"%s\",\"endDate\":\"%s\",\"timeUnit\":\"%s\",\"category\":[{\"name\":\"%s\",\"param\":[\"%s\"]}]",
                startDate, endDate, timeUnit, category_name, category_param);

        if(device != null) request = request + String.format(", \"device\":\"%s\"", device);
        if(gender != null) request = request + String.format(", \"gender\":\"%s\"", gender);
        if(ages != null) request = request + String.format(", \"ages\":[\"%s\"]", ages);
        request = request + "}";
    }
}
