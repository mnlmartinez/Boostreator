package es.boostreator.domain.model.enums;

public enum Site {

    FNAC("https://www.fnac.es"),
    ELCORTEINGLES("https://www.elcorteingles.es");

    private final String url;

    Site(String url){
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

}
