package es.boostreator.view;


import es.boostreator.domain.model.Product;
import es.boostreator.domain.model.enums.Brand;
import es.boostreator.domain.model.enums.Site;
import es.boostreator.domain.service.CoffeeMachineService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import com.jfoenix.controls.*;
import javafx.scene.text.Text;
import java.util.ArrayList;
import java.util.List;


public class SearchViewController{

    @FXML
    private JFXSpinner loadCircle;

    @FXML
    private Text loadText;

    @FXML
    private JFXTextField searchBox;

    @FXML
    private JFXComboBox<String> categoriesDrawer;

    @FXML
    private JFXButton applyCategores;

    @FXML
    private JFXTextField searchBarInput;

    @FXML
    private JFXComboBox<String> brandDrawer;

    @FXML
    private JFXToggleButton fnacSwitch;

    @FXML
    private JFXToggleButton ElCorteInglesSwitch;


    @FXML
    private JFXListView<Product> resultsListView;

    @FXML
    public void applyBrandButton(ActionEvent actionEvent) {
        //APPLY BRAND CHOICE
    }

    @FXML
    public void applyCategoriesButton(ActionEvent actionEvent){
        //APPLY CATEGORIES CHOICE
    }

    @FXML
    public void searchBarInput(ActionEvent actionEvent){
        //ON TEXT CHANGE
        searchParameter = "cafetera " + searchBarInput.getText();
    }

    @FXML
    public void initializeCategoriesDrawer(ActionEvent actionEvent){
        ArrayList<String> categories = new ArrayList<>();
        categories.add("Drop");
        categories.add("Multi-function");
        categories.add("Other");
        categoriesDrawer.getItems().addAll();
    }

    @FXML
    public void initializeBrandsDrawer(ActionEvent actionEvent){
        ArrayList<String> brands = new ArrayList<>();
        brands.add("Phillips");
        brands.add("Nespresso");
        brandDrawer.getItems().addAll(brands);
    }


    //FNAC SWITCH
    public boolean fnacSwitchState = false;
    //ELCORTEINGLES SWITCH
    public boolean elCorteInglesSwitchState = false;

    public List<Site> sites = new ArrayList<>();
    public List<Brand> brands = new ArrayList<>();

    //search parameter
    public String searchParameter = "cafetera";

    //Instantiate coffeeMachineService
    CoffeeMachineService service = new CoffeeMachineService();

    @FXML
    public void findProducts(ActionEvent actionEvent){
        System.out.println("heyyy");
        findProducts();
    }


    public void findProducts(){
        //configureParameters();
        sites.add(Site.FNAC);

        System.out.println("passed configure parameters");
        List<Product> res = service.getProductList(searchParameter, brands, sites, 20);
        resultsListView.getItems().addAll(res);
        System.out.println(res);
        loadCircle.setVisible(false);
        loadText.setText("");
    }

    @FXML
    public void changeFnacSwitch(ActionEvent actionEvent){ fnacSwitchState = !fnacSwitchState; }

    @FXML
    public void changeElCorteInglesSwitch(ActionEvent actionEvent){ elCorteInglesSwitchState = !elCorteInglesSwitchState; }


    public void configureParameters(){
        if(!fnacSwitchState && !elCorteInglesSwitchState) sites.add(Site.FNAC); sites.add(Site.ELCORTEINGLES);
        if(elCorteInglesSwitchState) sites.add(Site.ELCORTEINGLES);
        if(fnacSwitchState) sites.add(Site.FNAC);
    }
}



