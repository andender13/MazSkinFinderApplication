package andender13.mazskinfinderapplication.utility.skinParser;

import andender13.mazskinfinderapplication.entity.Skin;
import andender13.mazskinfinderapplication.seleniumSearch.CsMoneyImageParser;
import andender13.mazskinfinderapplication.service.SkinService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JSoupCsMoneyWikiParser {

    @Autowired
    private CsMoneyImageParser csMoneyImageParser;
    @Autowired
    private SkinService skinService;

    public void parseCsMoneyWiki() throws IOException {
        String baseUrl = "https://wiki.cs.money/weapons/";
        List<String> allGuns = List.of(
                "usp-s",
                "glock-18",
                "desert-eagle",
                "p250",
                "five-seven",
                "cz75-auto",
                "p2000",
                "tec-9",
                "r8-revolver",
                "dual-berettas",
                "ak-47",
                "m4a4",
                "m4a1-s",
                "aug",
                "sg-553",
                "galil-ar",
                "famas",
                "awp",
                "ssg-08",
                "scar-20",
                "g3sg1");

        for (String gun : allGuns) {
            String url = baseUrl + gun;
            Document document = Jsoup.connect(url).get();
            String gunType = document.select(".rdmwocwwwyeqwxiiwtdwuwgwkh").text();
            System.out.println(gunType);
            Elements guns = document.select(".kxmatkcipwonxvwweiqqdoumxg");
            List<String> skinsList = new ArrayList<>();
            for (Element element : guns) {
                String skinName = element.select(".zhqwubnajobxbgkzlnptmjmgwn").text();
                if (!skinName.isEmpty()) {
                    skinsList.add(skinName);
                }
            }
            List<String> imgUrls = csMoneyImageParser.startImageParse(url);
            for (int i = 0; i < skinsList.size(); i++) {
                Skin skin = Skin.builder()
                        .name(skinsList.get(i))
                        .imageUrl(imgUrls.get(i))
                        .gunType(gunType)
                        .build();
                skinService.saveSkin(skin);
            }
        }
    }

}
