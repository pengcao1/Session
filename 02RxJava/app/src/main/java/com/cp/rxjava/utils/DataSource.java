package com.cp.rxjava.utils;

import com.cp.rxjava.models.Task;

import java.util.ArrayList;
import java.util.List;

public class DataSource {

    public static List<Task> createTasksList(){
        List<Task> tasks = new ArrayList<>();
        tasks.add( new Task("The first task",true,3));
        tasks.add( new Task("The second task",false,1));
        tasks.add( new Task("The third task",true,0));
        tasks.add( new Task("The four task",false,2));
        tasks.add( new Task("The fifth task",true,4));
        tasks.add( new Task("The six task",false,5));

        return tasks;
    }
}
