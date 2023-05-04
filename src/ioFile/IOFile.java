package ioFile;

import java.util.ArrayList;
import java.util.List;

public interface IOFile<E> {
    void write(List<E> e, String path);

    List<E> read(String path);
}
