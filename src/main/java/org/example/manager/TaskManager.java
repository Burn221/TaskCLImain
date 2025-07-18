package org.example.manager;
import com.google.gson.*;
import org.example.model.Task;
import java.time.LocalDate;
import java.util.*;

public class TaskManager{

    //метод позволяющий генерировать id +1 для следующей задачи
    public int generateId(){
        return nextId++;
    }

    public int getNextId(){
        return nextId;
    }

    public void setNextId(int nextId){
        this.nextId=nextId;
    }

    private int nextId=1;



    //создание связанных мапов с задачами, для сохранения порядка
    public LinkedHashMap<Integer,Task> tasks= new LinkedHashMap<>();


    //Создание gson билдера для преобразования в Json и из Json
    Gson gson= new GsonBuilder()
            .setPrettyPrinting() //Красивый вывод в файле
            .registerTypeAdapter(LocalDate.class,
                    (JsonDeserializer<LocalDate>)(json,type,ctx)->LocalDate.parse(json.getAsString())) //Создаем десериализатор для типа данных LocalDate который парсит строки из файла в формат Localdate
            .registerTypeAdapter(LocalDate.class,
                    (JsonSerializer<LocalDate>) (date, type, ctx) -> new JsonPrimitive(date.toString())) //сериализатор который перевеодит формат LocalDate в понятный для Json String
            .create();




    public HashMap<Integer,Task> getTasks(){
        return tasks;
    }


    public void addTask(Task task){

        tasks.put(nextId,task);
        generateId();

    }

    public void deleteTask(int id){
        tasks.remove(id);
    }

    public void deleteAllTask(){

        tasks.clear();
    }

    public void allTasks(){
        for(Map.Entry<Integer,Task> entry: tasks.entrySet()){
            System.out.println(entry.getValue());

        }
    }

    public void todoTasks(){
        System.out.println("Задачи которые предстоит выполнить: ");
        for(Map.Entry<Integer,Task> entry: tasks.entrySet()){
            if(entry.getValue().getStatus()==Status.TODO) {
                System.out.println(entry.getValue());
                System.out.println();
            }
        }
    }

    public void inProgressTasks(){
        System.out.println("Задачи которые в процессе выполнения: ");
        for(Map.Entry<Integer,Task> entry: tasks.entrySet()){
            if(entry.getValue().getStatus()==Status.IN_PROGRESS) {
                System.out.println(entry.getValue());
                System.out.println();
            }
        }
    }

    public void doneTasks(){
        System.out.println("Задачи которые уже выполнены: ");
        for(Map.Entry<Integer,Task> entry: tasks.entrySet()){
            if(entry.getValue().getStatus()==Status.DONE) {
                System.out.println(entry.getValue());
                System.out.println();
            }
        }
    }

    public Task getUpdatingTask(int id){

        Task replacingTask;
        return replacingTask=tasks.get(id);

    }


    //Перечисление которое содержит 3 типа для статуса
    public enum Status{
        TODO,
        IN_PROGRESS,
        DONE;


    }








}


