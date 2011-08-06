package com.dragansah.gsoc2011.demoapp.pages;

import org.apache.tapestry5.annotations.Cached;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.beaneditor.NonVisual;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Index
{
    private int index;

    public int getIndex()
    {
        return ++index;
    }

    @Property
    private String title;

    @SuppressWarnings("unused")
    @Property
    private TodoItem todoItem;

    @Cached
    public Collection<String> getTitles()
    {
        return getTitlesToItems().keySet();
    }

    @Cached
    public Map<String, List<TodoItem>> getTitlesToItems()
    {
        Map<String, List<TodoItem>> map = new LinkedHashMap<String, List<TodoItem>>();

        map.put("Part 1: Right Click Menu component", Arrays.asList(
                TodoItem.newTodo("Right Click Menu component", Status.IN_PROGRESS),
                TodoItem.newTodo("Integration with t5 grid", Status.IN_PROGRESS),
                TodoItem.newTodo("Ajax behavior for the menu's options", Status.IN_PROGRESS),
                TodoItem.newTodo("Standalone t5 component", Status.NOT_STARTED),
                TodoItem.newTodo("Documentation", Status.NOT_STARTED)));

        map.put(
                "Part 2: Grid component enhancements",
                Arrays.asList(
                        TodoItem.newTodo("Grid Sorting", Status.IN_PROGRESS),
                        TodoItem.newTodo("Pagination", Status.IN_PROGRESS)));

        map.put(
                "3.1. (Optional) Changing column order and visibility and saving this in a db.",
                Arrays.asList(TodoItem.newTodo(
                        "Changing column order and visibility and saving this in a db",
                        Status.NOT_STARTED)));

        map.put(
                "3.2 (Optional) Basic support for generalizing the SPI like functionality for ajax updates",
                Arrays.asList(TodoItem
                        .newTodo(
                                "Basic support for generalizing the SPI like functionality for ajax updates",
                                Status.NOT_STARTED)));
        return map;
    }

    public List<TodoItem> getTodoItems()
    {
        return getTitlesToItems().get(title);
    }

    static enum Status
    {
        NOT_STARTED
        {
            String getLabel()
            {
                return "not started";
            }

            String getColor()
            {
                return "red";
            }
        },
        IN_PROGRESS
        {
            String getLabel()
            {
                return "in progress";
            }

            String getColor()
            {
                return "blue";
            }
        },
        DONE
        {
            String getLabel()
            {
                return "done";
            }

            String getColor()
            {
                return "green";
            }
        };

        abstract String getLabel();

        abstract String getColor();
    }

    public static class TodoItem
    {
        public TodoItem(String name, Status status)
        {
            super();
            this.setName(name);
            this.setStatus(status);
        }

        private String name;

        private Status status;

        static TodoItem newTodo(String name, Status status)
        {
            return new TodoItem(name, status);
        }

        public void setName(String name)
        {
            this.name = name;
        }

        public String getName()
        {
            return name;
        }

        public void setStatus(Status status)
        {
            this.status = status;
        }

        public Status getStatus()
        {
            return status;
        }

        @NonVisual
        public String getStatusLabel()
        {
            return status.getLabel();
        }

        @NonVisual
        public String getStatusColor()
        {
            return status.getColor();
        }
    }
}
