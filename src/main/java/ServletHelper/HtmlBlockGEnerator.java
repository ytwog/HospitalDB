package ServletHelper;

import java.util.HashMap;

public class HtmlBlockGEnerator implements blockGenerator{
    @Override
    public String generateBlock(int DesignType, String ... Info) {
        String res = "";
        if(DesignType == 0) {
            res += "<div class=\"col-12 p-1 p-md-2 col-md-6 col-xl-4 text-light bg-secondary\">" +
                    "<div class=\"bg-dark h-100\">";
            res += "<div class=\"img-wrapper mb-md-2\">";
            res += "<img src=\"resource/images/man.png\" alt=\"Picture\" class=\"item-boxover\">";
            res += "</div>";
        } else if(DesignType == 1) {
            res += "<div class=\"col-12 p-1 p-md-2 col-md-12 col-xl-8 text-light bg-secondary\">" +
                    "<div class=\"bg-dark h-100\">";
        } else if(DesignType == 2) {
            res += "<div class=\"p-1 p-md-2 col-12 text-light bg-secondary\">" +
                    "<div class=\"bg-dark h-100\">";
        }
        res += "<div class=\"py-1 bg-black\"><p class=\" p-1 m-0\">";
        res += Info[0];
        res += "</p></div>";

        for (int i = 1; i < Info.length; i++) {
            res += "<div class=\"pl-1\">\n";
            res += "<div class=\"inlined item-info\">";
            res += Info[i];
            res += "</div></div>";
        }
        if(DesignType == 2) {
            res += "<div class=\"pl-1\">\n";
            res += "<div class=\"inlined item-info\">";
            res += ("<form action=\"client\" method=\"POST\">");
            res += ("<input type=\"submit\" name=\"discard\" value=\"Отклонить\">");
            res += ("<input type=\"submit\" name=\"aprove\" value=\"Принять\">");
            res += ("</form>");
            res += "</div></div>";
        }
        res += "</div></div>";
        return res;
    }

}
