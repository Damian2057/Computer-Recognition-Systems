package p.lodz.pl;

import p.lodz.pl.dao.ArticleLoader;
import p.lodz.pl.model.Feature;
import p.lodz.pl.model.Vector;

import java.io.IOException;
import java.util.List;

import static p.lodz.pl.constants.Const.ARTICLES;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Hello world!");
        ArticleLoader articleLoader = new ArticleLoader(ARTICLES.getName());
        articleLoader.read();
    }
}