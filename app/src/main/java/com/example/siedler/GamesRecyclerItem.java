package com.example.siedler;

public class GamesRecyclerItem {
    private Integer id;
    private String numbers; // 1%2%1%2%1%2%1%2%1
    private String players; // patrick#1#bianca#2
    private String date; // 2021/03/13/18/30

    @Override
    public String toString() {
        return "GamesRecyclerItem{" +
                "id=" + id +
                ", numbers='" + numbers + '\'' +
                ", players='" + players + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumbers() {
        return numbers;
    }

    public void setNumbers(String numbers) {
        this.numbers = numbers;
    }

    public String getPlayers() {
        return players;
    }

    public void setPlayers(String players) {
        this.players = players;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public GamesRecyclerItem(Integer id, String numbers, String players, String date) {
        this.id = id;
        this.numbers = numbers;
        this.players = players;
        this.date = date;
    }
}
