package me.jcomo.stilltasty.parser;

public interface HtmlParser<T> {
    T parse(String html);
}
