package es.boostreator.view;


import com.jfoenix.controls.*;
import es.boostreator.domain.model.Product;
import es.boostreator.domain.model.enums.Brand;
import es.boostreator.domain.model.enums.Site;
import es.boostreator.domain.service.CoffeeMachineService;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;


public class SearchViewController implements Initializable {

    @FXML
    private JFXSpinner loadCircle;

    @FXML
    private Text loadText;

    @FXML
    private JFXTextField searchBox;

    @FXML
    private JFXTextField searchBarInput;

    @FXML
    private JFXComboBox<String> categoriesDrawer;

    @FXML
    private JFXComboBox<Brand> brandsDrawer;

    @FXML
    private TableView<Product> resultsListView;

    @FXML
    private TableColumn brandColumn;

    @FXML
    private TableColumn modelColumn;

    @FXML
    private TableColumn<Product, String> typeColumn;

    @FXML
    private TableColumn<Product, String> priceECIColumn;

    @FXML
    private TableColumn<Product, String> priceFNACColumn;

    @FXML
    private JFXSlider sliderLimit;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.initializeBrandsDrawer();
        this.initializeCategoriesDrawer();

        resultsListView.setRowFactory(tv -> {
            TableRow<Product> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY
                        && event.getClickCount() == 2) {

                    Product clickedRow = row.getItem();
                    System.out.println(clickedRow.toString());
                    showDialog(clickedRow);
                }
            });
            return row;
        });
    }


    public void initializeBrandsDrawer() {
        brandsDrawer.getItems().addAll(Arrays.asList(Brand.values()));
    }

    public void initializeCategoriesDrawer() {
        categoriesDrawer.getItems().addAll(
                "Automática",
                "Manual",
                "Espresso",
                "Italiana",
                "Goteo",
                "Capsulas"
        );
    }


    //FNAC SWITCH
    public boolean fnacSwitchState = false;

    //ELCORTEINGLES SWITCH
    public boolean elCorteInglesSwitchState = false;

    public List<Site> sites = new ArrayList<>();
    public List<Brand> brands = new ArrayList<>();


    //Instantiate coffeeMachineService
    CoffeeMachineService service = new CoffeeMachineService();

    @FXML
    public void findProducts(ActionEvent actionEvent) {
        findProducts();
        brandColumn.setCellValueFactory(new PropertyValueFactory("brand"));
        modelColumn.setCellValueFactory(new PropertyValueFactory("model"));


        try {
            typeColumn.setCellValueFactory((c -> new SimpleStringProperty(
                    c.getValue().toStringTypes().toLowerCase())));

            priceECIColumn.setCellValueFactory((c -> new SimpleStringProperty(
                    !c.getValue().getPrice().toString().contains("ELCORTEINGLES") ? "None" : c.getValue().toStringPrice(Site.ELCORTEINGLES))));

            priceFNACColumn.setCellValueFactory((c -> new SimpleStringProperty(
                    !c.getValue().getPrice().toString().contains("FNAC") ? "None" : c.getValue().toStringPrice(Site.FNAC))));
        } catch (Exception e) {

        }

    }


    public void findProducts() {
        configureParameters();
        resultsListView.getItems().clear();
        String selected = categoriesDrawer.getSelectionModel().getSelectedItem();
        List<Product> res = service.getProductList(selected == null ? "cafetera" : "cafetera " + selected, brands, sites, (int) sliderLimit.getValue());
        resultsListView.getItems().addAll(res);
    }

    @FXML
    public void changeFnacSwitch(ActionEvent actionEvent) {
        fnacSwitchState = !fnacSwitchState;
    }

    @FXML
    public void changeElCorteInglesSwitch(ActionEvent actionEvent) {
        elCorteInglesSwitchState = !elCorteInglesSwitchState;
    }


    public void configureParameters() {
        sites = new ArrayList<>();
        if (!fnacSwitchState && !elCorteInglesSwitchState) {
            sites.add(Site.FNAC);
            sites.add(Site.ELCORTEINGLES);
        }
        if (elCorteInglesSwitchState) sites.add(Site.ELCORTEINGLES);
        if (fnacSwitchState) sites.add(Site.FNAC);
    }

    public void showDialog(Product p) {


        Stage dialog1 = new Stage();
        dialog1.initStyle(StageStyle.UTILITY);
        Text title = new Text("Product View");
        title.setY(20.0);
        title.setX(20.0);
        Text brand = new Text("Brand: " + p.getBrand().name());
        brand.setY(60.0);
        brand.setX(20.0);
        Text model = new Text("Model: " + p.getModel().toString());
        model.setY(80.0);
        model.setX(20.0);
        Text types = new Text("Types: " + p.toStringTypes());
        types.setY(100.0);
        types.setX(20.0);
        Text priceECI = new Text("Price EL CORTE INGLES: " + p.getPrice().get(Site.ELCORTEINGLES) == null ? "Not Found" : ("Price 'El Corte Ingles': " + p.getPrice().get(Site.ELCORTEINGLES)));
        priceECI.setY(120.0);
        priceECI.setX(20.0);
        Text priceFNAC = new Text("Price FNAC: " + p.getPrice().get(Site.FNAC) == null ? "Not Found" : ("Price FNAC: " + p.getPrice().get(Site.FNAC)));
        priceFNAC.setY(140.0);
        priceFNAC.setX(20.0);
        Scene scene = new Scene(new Group(title, brand, model, types, priceECI, priceFNAC), 550, 150);
        dialog1.setScene(scene);
        dialog1.show();
    }


}



