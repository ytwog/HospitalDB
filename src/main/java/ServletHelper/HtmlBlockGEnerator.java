package ServletHelper;

public class HtmlBlockGEnerator {
    public static String generateBlock(String definition, String firstSentance, String secondSentance) {
        String res = "<div class=\"col-12 p-1 p-md-2 col-md-6 col-xl-4 text-light bg-secondary\">";
        res += "<div class=\"item-wrap bg-dark h-100\"><div class=\"img-wrapper mb-md-2\">";
        res += "<img src=\"resource/images/man.png\" alt=\"Picture\" class=\"item-boxover\"></div>";
        res += "<div class=\"d-none d-sm-block d-lg-block py-1 bg-black d-xl-block\"><p class=\" p-1 m-0\">";
        res += definition;
        res += "</p></div>";
        res += "<div class=\"d-none d-sm-block py-1 pl-1 bg-black d-md-block d-lg-block d-xl-block\">\n";
        res += "<div class=\"inlined item-info\">";
        res += firstSentance;
        res += "</div>\n</div>\n";
        res += "<div class=\"p-1 inlined item-info\">";
        res += secondSentance;
        res += "</div>\n</div>\n";
        res += "</div></div></div>";
        return res;
    }
}
