package es.boostreator.domain.model.enums;


import java.util.Arrays;
import java.util.List;

public enum Brand {

    DELONGHI(new Site[]{Site.FNAC, Site.ELCORTEINGLES}),
    KRUPS(new Site[]{Site.FNAC, Site.ELCORTEINGLES}),
    PHILIPS(new Site[]{Site.FNAC, Site.ELCORTEINGLES}),
    BOSCH(new Site[]{Site.FNAC, Site.ELCORTEINGLES}),
    SAECO(new Site[]{Site.FNAC, Site.ELCORTEINGLES}),
    SEVERIN(new Site[]{Site.FNAC, Site.ELCORTEINGLES}),
    TECNHOGAR(new Site[]{Site.FNAC, Site.ELCORTEINGLES}),
    BODUM(new Site[]{Site.FNAC, Site.ELCORTEINGLES}),
    ELCORTEINGLES(new Site[]{Site.ELCORTEINGLES}),
    MELITTA(new Site[]{Site.FNAC}),
    BIALETTI(new Site[]{Site.FNAC}),
    ORBEGOZO(new Site[]{Site.FNAC}),
    NESCAFE(new Site[]{Site.FNAC}),
    TASSIMO(new Site[]{Site.FNAC}),
    SIEMENS(new Site[]{Site.FNAC}),
    CLATRONIC(new Site[]{Site.FNAC}),
    SENSEO(new Site[]{Site.FNAC}),
    JURA(new Site[]{Site.FNAC}),
    PROLINE(new Site[]{Site.FNAC}),
    SAIVOD(new Site[]{Site.ELCORTEINGLES}),
    UFESA(new Site[]{Site.ELCORTEINGLES}),
    BRA(new Site[]{Site.ELCORTEINGLES}),
    TAURUS(new Site[]{Site.ELCORTEINGLES});

    private List<Site> sites;

    Brand() {
        this.sites = Arrays.asList(Site.values());
    }

    Brand(Site[] sites) {
        this.sites = Arrays.asList(sites);
    }

    public boolean isAvailableIn(Site site) {
        return this.sites.contains(site);
    }

    public boolean isEqualTo(String other) {
        return other != null &&
                this.name()
                        .toUpperCase()
                        .equals(other
                                .trim()
                                .toUpperCase()
                                .replace("'", "")
                                .replace(" ", "")
                        );
    }

    public static Brand getByName(String brandName) {
        try {
            return Brand.valueOf(brandName.toUpperCase().trim());
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }

    public static Brand getByProductTitle(String text) {
        Brand[] brands = Brand.values();
        text = text
                .trim()
                .toUpperCase()
                .replace("'", "")
                .replace(" ", "");
        try {
            for (Brand brand : brands) if (text.contains(brand.name())) return brand;
        } catch (IllegalArgumentException ignored) {
        }

        return null;
    }

}
