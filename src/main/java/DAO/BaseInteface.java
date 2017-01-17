package DAO;

import java.util.ArrayList;

public interface BaseInteface<T> {
    T getById(int id);
    ArrayList<T> getList(String query);
    void remove(int id);
    T getLast();
}
