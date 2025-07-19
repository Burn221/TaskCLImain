package org.example;

import org.example.CLI.TaskCLI;
import org.example.Storage.TaskStoraging;
import org.example.manager.TaskManager;
import org.example.model.Task;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        TaskManager manager= new TaskManager();
        TaskCLI cli= new TaskCLI(manager);
        TaskStoraging storager= new TaskStoraging(manager);
        //путь к файлу в который будет записываться и из которого будем записывать Json
        String path= "C:\\Users\\HP\\Desktop\\test\\taskCLI.txt";
        storager.loadFromFile(path);

        cli.run();

        System.out.println("Программа завершила свою работу!");
        storager.saveToFile(path);

        //тестовые комментарии для гита

    }
}