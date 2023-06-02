package p.lodz.pl.backend.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Serialize<R> implements Serializable {

    private final List<R> list;


    public Serialize(List<R> list) {
        this.list = list;
    }
}
