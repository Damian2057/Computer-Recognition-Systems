package p.lodz.pl.dao;

import p.lodz.pl.model.Vector;

import java.io.*;
import java.util.List;

import static p.lodz.pl.constants.Const.SERIALIZE_PATH;
import static p.lodz.pl.constants.Const.SERIALIZE_VECTORS;

public class SerializeLoaderImpl implements SerializeLoader<List<Vector>> {

    @Override
    public List<Vector> read() {
        try {
            FileInputStream fi = new FileInputStream(SERIALIZE_VECTORS.getName());
            ObjectInputStream oi = new ObjectInputStream(fi);

            return (List<Vector>) oi.readObject();

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void write(List<Vector> obj) {
        try {
            createDirIfNotExist();
            FileOutputStream f = new FileOutputStream(SERIALIZE_VECTORS.getName());
            ObjectOutputStream o = new ObjectOutputStream(f);
            o.writeObject(obj);

            o.close();
            f.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isFileExist() {
        File f = new File(SERIALIZE_VECTORS.getName());
        return f.exists() && !f.isDirectory();
    }

    private void createDirIfNotExist() {
        String directoryName = SERIALIZE_PATH.getName();
        File directory = new File(directoryName);
        if (! directory.exists()){
            directory.mkdir();
        }
    }
}
