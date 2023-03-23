package p.lodz.pl;

import p.lodz.pl.logic.start.Starter;
import p.lodz.pl.logic.start.StarterImpl;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Starter starter = new StarterImpl();
        starter.start();
    }
}