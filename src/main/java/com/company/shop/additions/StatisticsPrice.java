package com.company.shop.additions;

public class StatisticsPrice implements Comparable<StatisticsPrice>{

    private Integer id;
    private Integer profit;
    private String title;

    public StatisticsPrice(Integer id, Integer profit, String title) {
        this.id = id;
        this.title = title;
        this.profit = profit;
    }

    public String getTitle() {
        return title;
    }

    public Integer getProfit() {
        return profit;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public int compareTo(StatisticsPrice o) {
        if (getProfit() == null || o.getProfit() == null) {
            return 0;
        }
        return getProfit().compareTo(o.getProfit());
    }
}
