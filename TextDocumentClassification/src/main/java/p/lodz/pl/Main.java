package p.lodz.pl;

import p.lodz.pl.logic.start.Starter;
import p.lodz.pl.logic.start.StarterImpl;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Starter starter = new StarterImpl();
        starter.start();
    }
}