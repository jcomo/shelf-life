package me.jcomo.stilltasty.parser;

import me.jcomo.stilltasty.core.StorageGuide;
import me.jcomo.stilltasty.core.StorageMethod;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StorageGuideParser implements HtmlParser<Optional<StorageGuide>> {
    public Optional<StorageGuide> parse(String html) {
        Document doc = Jsoup.parse(html);

        String foodName = parseFoodName(doc);
        if ("".equals(foodName)) {
            return Optional.empty();
        } else {
            return Optional.of(parseRemainingGuide(doc, foodName));
        }
    }

    private String parseFoodName(Document doc) {
        Element nameElement = doc.getElementsByClass("bigBlackHeading").first();
        if (nameElement != null) {
            return nameElement.text();
        } else {
            return "";
        }
    }

    private StorageGuide parseRemainingGuide(Document doc, String foodName) {
        List<StorageMethod> storageMethods = parseStorageMethods(doc);
        List<String> tips = parseTips(doc);

        return new StorageGuide(foodName, storageMethods, tips);
    }

    private List<StorageMethod> parseStorageMethods(Document doc) {
        Elements locations = doc.getElementsByClass("slicedHead");
        Elements expirations = doc.getElementsByClass("days");

        // Zip locations and expirations
        return IntStream.range(0, Math.min(locations.size(), expirations.size()))
                .mapToObj(i -> new StorageMethod(locations.get(i).text(), expirations.get(i).text()))
                .collect(Collectors.toList());
    }

    private List<String> parseTips(Document doc) {
        Element tipSection = doc.getElementsByClass("tips").first();
        if (null == tipSection) {
            return new ArrayList<>();
        } else {
            return tipsFromSection(tipSection);
        }
    }

    private List<String> tipsFromSection(Element tipSection) {
        return tipSection.getElementsByTag("li")
                .stream()
                .map(Element::text)
                .filter(tip -> !"".equals(tip))
                .collect(Collectors.toList());
    }
}
