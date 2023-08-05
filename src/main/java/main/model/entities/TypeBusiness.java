package main.model.entities;

import main.model.StatusCode;
import org.hibernate.annotations.GeneratorType;

import javax.persistence.*;
import java.util.List;

@Entity
public class TypeBusiness {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private int code;

    public TypeBusiness(){}

    public TypeBusiness(String name, int code){
        this.name = name;
        this.code = code;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
