package com.example.store_analysis.naverapi;

public class Keyword
{
    public String device = null;
    public String gender = null;
    public String ages = null;
    public String request;

    public Keyword(String startDate, String endDate, String timeUnit, String category, String keyword_name, String keyword_param)
    {
        request = String.format("{\"startDate\":\"%s\",\"endDate\":\"%s\",\"timeUnit\":\"%s\", \"category\":\"%s\", \"keyword\":[{\"name\":\"%s\",\"param\":[\"%s\"]}]",
                startDate, endDate, timeUnit, category, keyword_name, keyword_param);

        if(device != null) request = request + String.format(", \"device\":\"%s\"", device);
        if(gender != null) request = request + String.format(", \"gender\":\"%s\"", gender);
        if(ages != null) request = request + String.format(", \"ages\":[\"%s\"]", ages);
        request = request + "}";
    }
}
