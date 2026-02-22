package com.example.boradra.taskmanager.domain.model;

import lombok.Value;

@Value
public final class TaskTitle {

    private final String value;
    
    public TaskTitle(String value)
    {
        if (value == null || value.trim().isEmpty())
        {
            throw new IllegalArgumentException("Title can not be empty");
        }
      /* */
        if(value.length()> 100) {
            throw new IllegalArgumentException("Title can not be more than 100 characters");
        }
        this.value = value;
    }

    /* 
    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskTitle that = (TaskTitle) o;
        return Objects.equals(value,that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    */

}
