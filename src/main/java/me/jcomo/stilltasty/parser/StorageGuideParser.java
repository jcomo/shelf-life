package me.jcomo.stilltasty.parser;

import me.jcomo.stilltasty.core.StorageGuide;
import me.jcomo.stilltasty.core.StorageMethod;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        Element storageElement = doc.getElementsByClass("food-storage-container").first();
        if (storageElement != null) {
            return storageElement.getElementsByTag("h2").first().text();
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
        return doc.getElementsByClass("food-inside")
                .stream()
                .map(this::parseStorageMethod)
                .collect(Collectors.toList());
    }

    private StorageMethod parseStorageMethod(Element methodElement) {
        String location = methodElement.getElementsByClass("food-storage-left").first()
                .getElementsByTag("span").first().text();
        String expiration = methodElement.getElementsByClass("food-storage-right").first()
                .getElementsByTag("span").first().text();
        return new StorageMethod(location, expiration);
    }

    private List<String> parseTips(Document doc) {
        Element tipSection = doc.getElementsByClass("food-tips").first();
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
