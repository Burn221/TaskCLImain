package org.example.CLI;

import org.example.manager.TaskManager;
import org.example.model.Task;


import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Arrays;

import java.util.Scanner;

public class TaskCLI {



    Scanner scanner= new Scanner(System.in);
    TaskManager manager= new TaskManager();
    //Создаем конструктор для TaskCli, который принимает в себя manager, чтобы он мог корректно работать и использовать его методы
    public TaskCLI(TaskManager manager){
        this.manager= manager;

    }


    //главный метод программы- в нем происходят основная работа с пользователем
    public void run() {

        //Указатель внешнего цикла while, при прерывании вся программа завершается
        outer:
        while (true) {
            System.out.println("TaskTrackerCli, введите \"Help\" для получения списка доступных команд");
            inner:
            while (true) {
                System.out.println("Введите команду...");
                String line = scanner.nextLine().trim().toLowerCase();
                switch (line) {
                    case ("add"):
                        System.out.println();
                        System.out.println("Создаем задачу...");
                        System.out.println();
                        Task task = addConstructedTask();
                        if (task != null) {
                            manager.addTask(task);
                            System.out.println();
                            System.out.println("Задача успешно добавлена");
                            System.out.println();
                            break;
                        } else {
                            System.out.println();
                            System.out.println("Повторите ввод");
                            break;
                        }


                    case ("delete"):
                        if(manager.tasks.isEmpty()) {
                            System.out.println("Похоже, список задач пуст...");
                            System.out.println();
                            break;
                        }
                        System.out.println();

                        printAvailableTasks();
                        while (true) {
                            try {
                                System.out.println("Введите id задачи для удаления");
                                String checkBack= scanner.nextLine().trim().toLowerCase();
                                if(checkBack.equals("back") || checkBack.equals("exit")) break ;
                                int deleteId = Integer.parseInt(checkBack);

                                if (manager.tasks.containsKey(deleteId)) {
                                    manager.deleteTask(deleteId);
                                    System.out.println();
                                    System.out.println("Задача успешно удалена!");
                                    System.out.println();
                                    break;
                                }
                            }
                            catch (NumberFormatException e){

                                System.out.println();
                            }

                            System.out.println("Введено неправильно значение id, попробуйте еще раз");
                            System.out.println();
                        }
                        break ;


                    case("delete all"):
                        System.out.println();
                        System.out.println("Вы уверены что хотите удалить все имеющиеся задачи? Напишите \"Да\" для подтверждения");
                        String respond= scanner.nextLine().trim().toLowerCase();

                        if(respond.equals("да")){
                            manager.deleteAllTask();
                            manager.setNextId(1);
                            System.out.println("Все задачи успешно удалены!");
                            break;
                        }
                        else break ;

                    case ("all"):

                        if(manager.tasks.isEmpty()) {
                            System.out.println();
                            System.out.println("Похоже, задач пока что нет");
                            System.out.println();
                            break;
                        }
                        System.out.println();
                        printAvailableTasks();

                        break;

                    case ("update"):

                        printAvailableTasks();

                        while (true) {
                            try {
                                if(manager.tasks.isEmpty()){
                                    System.out.println("Похоже, список задач пуст...");
                                    System.out.println();
                                    break;
                                }
                                System.out.println("Введите айди задачи которую вы хотели бы изменить");
                                int id = Integer.parseInt(scanner.nextLine());
                                Task replaceable = manager.getUpdatingTask(id);
                                replaceable.setLastUpdated(LocalDate.now());


                                System.out.println();
                                System.out.println("Чтобы вы хотели поменять? Доступные поля: everything, name, status, description, deadline. Также можете ввести exit для возврата");

                                String choice = scanner.nextLine().trim().toLowerCase();
                                System.out.println();

                                switch (choice) {
                                    case ("name"):
                                        System.out.println("Введите имя на замену");
                                        String replaceName = scanner.nextLine();
                                        replaceable.setName(replaceName);
                                        manager.tasks.replace(id, replaceable);
                                        System.out.println();
                                        System.out.println("Задача успешно обновлена");
                                        System.out.println();
                                        break inner;


                                    case ("status"):
                                        System.out.println("Введите статус задачи " + Arrays.toString(TaskManager.Status.values()) + ": ");
                                        TaskManager.Status replaceStatus;

                                        while (true) {
                                            try {
                                                replaceStatus = TaskManager.Status.valueOf(scanner.nextLine().trim().toUpperCase());
                                                replaceable.setStatus(replaceStatus);
                                                manager.tasks.replace(id, replaceable);
                                                System.out.println();
                                                System.out.println("Задача успешно обновлена");
                                                System.out.println();
                                                break;
                                            } catch (IllegalArgumentException e) {
                                                System.out.println();
                                                System.out.println("Введено неправильно значение, доступны" + Arrays.toString(TaskManager.Status.values()) + ": ");
                                                System.out.println();
                                            }

                                        }
                                        break;

                                    case ("description"):
                                        System.out.println("Введите описание на замену");
                                        String replaceDescription = scanner.nextLine();
                                        replaceable.setDescription(replaceDescription);
                                        manager.tasks.replace(id, replaceable);
                                        System.out.println();
                                        System.out.println("Задача успешно обновлена");
                                        System.out.println();
                                        break;

                                    case ("deadline"):
                                        LocalDate replaceDeadline;
                                        while (true) {
                                            try {
                                                System.out.println("Введите дедлайн на замену, формата YYYY-MM-DD");
                                                replaceDeadline = LocalDate.parse(scanner.nextLine().trim());
                                                replaceable.setDeadline(replaceDeadline);
                                                manager.tasks.replace(id, replaceable);
                                                System.out.println();
                                                System.out.println("Задача успешно обновлена");
                                                System.out.println();
                                                break;
                                            } catch (DateTimeParseException e) {
                                                System.out.println();
                                                System.out.println("Недопустимый формат даты, необходим формат YYYY-MM-DD");
                                                System.out.println();
                                            }
                                        }
                                        break;

                                    case ("everything"):
                                        replaceable = addConstructedTask();
                                        if (replaceable != null) {
                                            manager.tasks.replace(id, replaceable);
                                            replaceable.setId(id);
                                            System.out.println();
                                            System.out.println("Задача успешно обновлена");
                                            System.out.println();
                                            break;
                                        } else {
                                            System.out.println();
                                            System.out.println();
                                            System.out.println("Повторите ввод");
                                            System.out.println();
                                            break;
                                        }

                                    case ("exit"):

                                        break;

                                    default:
                                        System.out.println();
                                        System.out.println("Введен неизвестный атрибут, попробуйте еще раз");
                                        System.out.println();


                                }
                                break;
                            }

                            catch (NullPointerException  | NumberFormatException e){
                                System.out.println("Введен неправильный id, попробуйте еще раз");
                                System.out.println();

                            }

                        }
                        break;

                    case ("help"):
                        System.out.println("add- позволяет добавить новую задачу");
                        System.out.println();
                        System.out.println("delete- позволяет удалить имеющуюся задачу");
                        System.out.println();
                        System.out.println("delete all- позволяет удалить все задачи");
                        System.out.println();
                        System.out.println("update- позволяет изменить 1 или все элементы задачи");
                        System.out.println();
                        System.out.println("all- показывает все имеющиеся задачи");
                        System.out.println();
                        System.out.println("help- список команд");
                        System.out.println();
                        System.out.println("todo tasks- список задач, предстоящие к выполнению");
                        System.out.println();
                        System.out.println("in progress tasks- список задач в процессе");
                        System.out.println();
                        System.out.println("done tasks- список сделанных задач");
                        System.out.println();
                        break;

                    case ("todo tasks"):
                        System.out.println();
                        manager.todoTasks();
                        System.out.println();
                        break;

                    case ("in progress tasks"):
                        System.out.println();
                        manager.inProgressTasks();
                        System.out.println();
                        break;


                    case ("done tasks"):
                        System.out.println();
                        manager.doneTasks();
                        System.out.println();
                        break;

                    case ("exit"):

                        break outer;

                    case("back"):
                        break inner;


                    default:
                        System.out.println();
                        System.out.println("Неизвестная команда, введите help Для получения списка команд");
                        System.out.println();

                }
            }
        }
    }
    //Метод создания задачи через параметры, которйы затем передаются в конструктор
    public Task addConstructedTask() {

            System.out.println("Введите название для задачи");
            String name = scanner.nextLine().trim();
            System.out.println();
            System.out.println("Введите описание для задачи");
            String description = scanner.nextLine().trim();
            TaskManager.Status status;
            while (true) {
                try {
                    System.out.println();
                    System.out.println("Введите статус задачи " + Arrays.toString(TaskManager.Status.values()) + ": ");
                    String normalized= scanner.nextLine()
                            .trim()
                            .replace(" ","_")
                            .replace("-","_")
                            .toUpperCase();
                    status = TaskManager.Status.valueOf(normalized);
                    break;

                } catch (IllegalArgumentException e) {
                    System.out.println();
                    System.out.println("Неверный ввод, доступные поля" + Arrays.toString(TaskManager.Status.values()) + ": ");


                }
            }

            LocalDate createdAt = LocalDate.now();
            LocalDate deadline;
            while(true) {
                try {
                    System.out.println();
                    System.out.println("Введите дедлайн для задачи формата YYYY-MM-DD");
                    deadline = LocalDate.parse(scanner.nextLine().trim());
                    break;
                }
                catch (DateTimeParseException e){
                    System.out.println();
                    System.out.println("Введен неправильный формат даты, требуется формат YYYY-MM-DD");
                }
            }

            LocalDate lastUpdated= LocalDate.now();

            int id= manager.getNextId();


            return new Task(id, name, description, status, createdAt, deadline,lastUpdated);

        }
        public void printAvailableTasks(){
            System.out.println("Выводим список всех имеющихся задач...");
            System.out.println();
            manager.allTasks();
            System.out.println();

        }



    }








