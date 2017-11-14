package es.boostreator.domain.model;

import es.boostreator.domain.model.enums.Site;
import es.boostreator.domain.model.enums.Type;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ProductMapper {

    public static List<Product> siteProductMap2ProductList(Map<Site, List<SiteProduct>> siteProductsMap) {
        List<Product> products = new ArrayList<>();
        List<SiteProduct> fnac = siteProductsMap.get(Site.FNAC);
        List<SiteProduct> eci = siteProductsMap.get(Site.ELCORTEINGLES);

        for (; !fnac.isEmpty(); ) {
            SiteProduct p1 = fnac.get(0);
            SiteProduct found = null;
            int max = 0;

            for (SiteProduct p2 : eci) {
                if (compareBrands(p1, p2) && compareTypes(p1, p2) && comparePrices(p1, p2)) {
                    int points = compareProducts(p1, p2);
                    if (max < points) {
                        max = points;
                        found = p2;
                    }
                }
            }

            if (found != null && max >= getMinToJoin(p1, found)) {
                List<SiteProduct> siteProducts = new ArrayList<>();
                siteProducts.add(p1);
                siteProducts.add(found);
                eci.remove(found);
                products.add(siteProductList2Product(siteProducts));
            } else {
                products.add(siteProduct2Product(p1));
            }

            fnac.remove(p1);
        }

        while (!eci.isEmpty()) products.add(siteProduct2Product(eci.remove(0)));

        return products;
    }

    private static Product siteProductList2Product(List<SiteProduct> siteProducts) {
        Product product =
                new Product(
                        getBestDescription(siteProducts),
                        siteProducts.get(0).getBrand(),
                        getAllTypes(siteProducts));

        for (SiteProduct siteProduct : siteProducts)
            product.getPrice().put(siteProduct.getSite(), siteProduct.getPrice());

        return product;
    }

    private static Product siteProduct2Product(SiteProduct siteProduct) {
        Product product =
                new Product(
                        siteProduct.getModel(),
                        siteProduct.getBrand(),
                        siteProduct.getTypes());
        product.getPrice().put(siteProduct.getSite(), siteProduct.getPrice());

        return product;
    }

    private static int compareProducts(SiteProduct p1, SiteProduct p2) {
        int count = 0;
        List<String> words1 = Arrays.asList(cleanAndSplitDescription(p1.getModel()));
        List<String> words2 = Arrays.asList(cleanAndSplitDescription(p2.getModel()));

        for (String w1 : words1)
            if (words2.contains(w1)) count++;

        return count;
    }

    private static boolean compareBrands(SiteProduct p1, SiteProduct p2) {
        return p1.getBrand() == p2.getBrand();
    }

    private static boolean comparePrices(SiteProduct p1, SiteProduct p2) {
        double max = Math.max(p1.getPrice(), p2.getPrice());
        return Math.abs(p1.getPrice() - p2.getPrice()) < max * 0.2;
    }

    private static boolean compareTypes(SiteProduct p1, SiteProduct p2) {
        if (p1.getTypes().size() < p2.getTypes().size()) return compareTypes(p2, p1);

        int nTypes = p2.getTypes().size();
        int count = 0;

        for (Type t2 : p2.getTypes())
            if (p2.getTypes().contains(t2)) count++;

        return count >= (nTypes - 2);
    }

    private static int getMinToJoin(SiteProduct p1, SiteProduct p2) {
        int nWords1 = cleanAndSplitDescription(p1.getModel()).length;
        int nWords2 = cleanAndSplitDescription(p2.getModel()).length;
        int min = Math.min(nWords1, nWords2);

        return min - min / 2;
    }

    private static String getBestDescription(List<SiteProduct> siteProducts) {
        String desc = "";
        int nMax = 0;

        for (SiteProduct siteProduct : siteProducts) {
            int nWords = cleanAndSplitDescription(siteProduct.getModel()).length;
            if (nWords > nMax) {
                nMax = nWords;
                desc = siteProduct.getModel();
            }
        }

        return desc;
    }

    private static List<Type> getAllTypes(List<SiteProduct> siteProducts) {
        List<Type> types = siteProducts.get(0).getTypes();
        for (SiteProduct siteProduct : siteProducts) {
            siteProduct.getTypes().forEach(type -> {
                if (!types.contains(type)) types.add(type);
            });
        }
        return types;
    }

    private static String[] cleanAndSplitDescription(String desc) {
        desc = Normalizer
                .normalize(desc, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "")
                .toUpperCase()
                .trim()
                .replace("'", "")
                .replace("PARA", "")
                .replace("CON", "")
                .replace("DE", "")
                .replace("UN", "")
                .replace("EL", "")
                .replace("A", "")
                .replace("/","")
                .replaceAll(" {2}", " ");
        return desc.split(" ");
    }

}
