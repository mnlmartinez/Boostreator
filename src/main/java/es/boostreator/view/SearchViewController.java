package es.boostreator.view;


import es.boostreator.domain.model.Product;
import es.boostreator.domain.model.enums.Brand;
import es.boostreator.domain.model.enums.Site;
import es.boostreator.domain.service.CoffeeMachineService;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import com.jfoenix.controls.*;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.util.Callback;

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
    private JFXTextField searchBarInput;

    @FXML
    private JFXComboBox categoriesDrawer;

    @FXML
    private JFXComboBox brandDrawer;


    @FXML
    private TableView<Product> resultsListView;

    @FXML
    private TableColumn brandColumn;

    @FXML
    private TableColumn modelColumn;

    @FXML
    private TableColumn<Product, String> priceECIColumn;

    @FXML
    private TableColumn<Product, String> priceFNACColumn;


    @FXML
    public void applyBrandButton(ActionEvent actionEvent) {
        //APPLY BRAND CHOICE
        //searchParameter+= " " +
        //System.out.println(brandDrawer.getSelectionModel().selectedItemProperty().toString());

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


    public void initializeBrandsDrawer(){
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
        findProducts();
        brandColumn.setCellValueFactory(new PropertyValueFactory("brand"));
        modelColumn.setCellValueFactory(new PropertyValueFactory("model"));
        priceECIColumn.setCellValueFactory((c -> new SimpleStringProperty(

                c.getValue().getPrice().toString()
                        .substring(c.getValue().getPrice().toString().indexOf(',')+15,c.getValue().getPrice().toString().length()-1))));

        priceFNACColumn.setCellValueFactory((c -> new SimpleStringProperty(

                c.getValue().getPrice().toString().substring(c.getValue().getPrice().toString().indexOf('F')+5,c.getValue().getPrice().toString().indexOf(',')-1))));

    }


    public void findProducts(){
        configureParameters();
        resultsListView.getItems().removeAll();

        System.out.println("passed configure parameters");
        List<Product> res = service.getProductList(searchParameter, brands, sites, 20);
        resultsListView.getItems().addAll(res);
        System.out.println(res);
        loadCircle.setVisible(false);
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



