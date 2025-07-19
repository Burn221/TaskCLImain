package org.example.Storage;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.example.manager.TaskManager;
import org.example.model.Task;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.Map;

public class TaskStoraging {

    TaskManager manager= new TaskManager();

    public TaskStoraging(TaskManager manager){
        this.manager=manager;
    }
    //Запись в файл формата json
    Gson gson= new GsonBuilder()
            .setPrettyPrinting() //Красивый вывод в файле
            .registerTypeAdapter(LocalDate.class,
                    (JsonDeserializer<LocalDate>)(json, type, ctx)->LocalDate.parse(json.getAsString())) //Создаем десериализатор для типа данных LocalDate который парсит строки из файла в формат Localdate
            .registerTypeAdapter(LocalDate.class,
                    (JsonSerializer<LocalDate>) (date, type, ctx) -> new JsonPrimitive(date.toString())) //сериализатор который перевеодит формат LocalDate в понятный для Json String
            .create();

    public void saveToFile(String path) {
        try ( Writer writer = new FileWriter(path)){

            Type mapType= new TypeToken<Map<Integer, Task>>(){}.getType(); //Передаем тип мапы в значение mapType, т.к java забывает тип Map<Integer,Task> во время выполнения
            gson.toJson(manager.tasks,mapType,writer);
            //при неравильном вводе или синтаксической ошибке Json обработается исключение
        } catch (IOException | JsonSyntaxException e) {
            System.out.println("Неправильно введена задача"+ e.getMessage());
        }
    }

    public void loadFromFile(String path) {

        //если файла не существует- не выполняем ничего, т.к значит задачи не были еще добавлены
        File f = new File(path);
        if (!f.exists()) {
            return;
        }
        try (Reader reader = new FileReader(path);) {
            Type mapType = new TypeToken<Map<Integer, Task>>() {}.getType();
            Map<Integer, Task> loaded = gson.fromJson(reader, mapType);
            manager.tasks.putAll(loaded);
            manager.setNextId(loaded.keySet().stream().max(Integer::compare).orElse(0) + 1);   //используем StreamApi, находим максимальное значение id и присваиваем nextid это значение, иначе устанавливаем его на 1
        } catch (IOException | NullPointerException e) {
            System.out.println("Ошибка ввода" + e.getMessage());
        }
    }
}
