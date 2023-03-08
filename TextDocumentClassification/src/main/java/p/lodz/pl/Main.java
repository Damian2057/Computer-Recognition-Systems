package p.lodz.pl;

import p.lodz.pl.dao.ArticleLoader;

import java.io.FileNotFoundException;
import java.io.IOException;

import static p.lodz.pl.constants.Const.ARTICLES;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Hello world!");
        ArticleLoader<?> articleLoader = new ArticleLoader<>(ARTICLES.getName());
        articleLoader.read();
    }
}