package es.boostreator.domain.model.enums;


import java.util.Arrays;
import java.util.List;

public enum Brand {

    DELONGHI(new Site[]{Site.FNAC}),
    KRUPS,
    SAECO,
    SEVERING,
    FILTERLOGIC,
    PHILIPS;

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
            System.out.println("Fallo en " + text);
            ignored.printStackTrace();
        }

        return null;
    }

}
