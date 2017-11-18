package es.boostreator.controller;

import com.jfoenix.controls.*;
import es.boostreator.domain.model.Product;
import es.boostreator.domain.model.enums.Brand;
import es.boostreator.domain.model.enums.Site;
import es.boostreator.domain.service.CoffeeMachineService;
import javafx.beans.property.SimpleStringProperty;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;


public class SearchViewController implements Initializable {

    // Instantiate coffeeMachineService
    private CoffeeMachineService service = new CoffeeMachineService();

    // Switch
    @FXML
    private boolean fnacSwitch = true;

    private boolean eciSwitch = true;

    private List<Site> sites = new ArrayList<>();
    private List<Brand> brands = new ArrayList<>();

    @FXML
    private JFXSpinner loadingSpinner;

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
    private TableColumn<Product, String> brandColumn;

    @FXML
    private TableColumn<Product, String> modelColumn;

    @FXML
    private TableColumn<Product, String> typeColumn;

    @FXML
    private TableColumn<Product, String> priceECIColumn;

    @FXML
    private TableColumn<Product, String> priceFNACColumn;

    @FXML
    private JFXSlider sliderLimit;

    @FXML
    private JFXButton searchButton;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.initializeBrandsDrawer();
        this.initializeCategoriesDrawer();
        this.initializeColumns();
        this.initializeListeners();
        this.hideSpinner();
    }


    @FXML
    public void search(ActionEvent actionEvent) {
        this.setParameters();

        searchButton.setDisable(true);
        resultsListView.getItems().clear();

        this.launchThread();
    }

    @FXML
    public void changeFnacSwitch(ActionEvent actionEvent) {
        fnacSwitch = !fnacSwitch;
    }

    @FXML
    public void changeEciSwitch(ActionEvent actionEvent) {
        eciSwitch = !eciSwitch;
    }

    private void launchThread() {
        showSpinner();
        Thread t = new Thread(new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                String selected = categoriesDrawer.getSelectionModel().getSelectedItem();
                List<Product> products = service.getProductList(
                        (selected == null ? "Cafetera" : "Cafetera " + selected),
                        brands,
                        sites,
                        (int) sliderLimit.getValue()
                );
                resultsListView.getItems().addAll(products);
                return null;
            }

            @Override
            protected void succeeded() {
                searchButton.setDisable(false);
                hideSpinner();

            }
        });
        t.start();
        Runtime.getRuntime().addShutdownHook(new Thread(t::stop));
    }

    private void showSpinner() {
        loadingSpinner.setVisible(true);
    }

    private void hideSpinner() {
        loadingSpinner.setVisible(false);
    }

    private void initializeBrandsDrawer() {
        brandsDrawer.getItems().addAll(Arrays.asList(Brand.values()));
    }

    private void initializeCategoriesDrawer() {
        categoriesDrawer.getItems().addAll(
                "Automática",
                "Manual",
                "Espresso",
                "Italiana",
                "Goteo"
        );
    }

    private void initializeColumns() {
        brandColumn.setCellValueFactory(c ->
                new SimpleStringProperty(
                        (c.getValue().getBrand() == null) ? "" : c.getValue().getBrand().name()));

        modelColumn.setCellValueFactory(c ->
                new SimpleStringProperty(
                        (c.getValue().getModel() == null) ? "" : c.getValue().getModel()));

        typeColumn.setCellValueFactory(c -> new SimpleStringProperty(
                c.getValue().toStringTypes().toLowerCase()));

        priceECIColumn.setCellValueFactory(c ->
                new SimpleStringProperty(
                        (c.getValue().getPrice().get(Site.ELCORTEINGLES) == null) ? "x" :
                                c.getValue().toStringPrice(Site.ELCORTEINGLES)));

        priceFNACColumn.setCellValueFactory(c -> new SimpleStringProperty(
                c.getValue().getPrice().get(Site.FNAC) == null ? "x" :
                        c.getValue().toStringPrice(Site.FNAC)));
    }

    private void initializeListeners() {
        resultsListView.setRowFactory(tv -> {
            TableRow<Product> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                    Product product = row.getItem();
                    showDialog(product);
                }
            });
            return row;
        });
    }

    private void setParameters() {
        sites = new ArrayList<>();
        if (!fnacSwitch && !eciSwitch) {
            sites.add(Site.FNAC);
            sites.add(Site.ELCORTEINGLES);
        }
        if (eciSwitch) sites.add(Site.ELCORTEINGLES);
        if (fnacSwitch) sites.add(Site.FNAC);

        brands = new ArrayList<>();
        brands.add(brandsDrawer.getSelectionModel().getSelectedItem());
    }

    private void showDialog(Product p) {
        Stage dialog = new Stage();
        dialog.initStyle(StageStyle.UTILITY);

        Text title = new Text("PRODUCT VIEW");
        title.setY(30.0);
        title.setX(20.0);
        title.setFont(Font.font(16));
        title.setStroke(Paint.valueOf("#754d2d"));

        Text brand = new Text("Brand:  " + ((p.getBrand() == null) ? "" : p.getBrand().name()));
        brand.setY(60.0);
        brand.setX(20.0);

        Text model = new Text("Model: " + p.getModel());
        model.setY(90.0);
        model.setX(20.0);

        Text types = new Text("Types: " + p.toStringTypes());
        types.setY(120.0);
        types.setX(20.0);

        Text priceECI = new Text("Price EL CORTE INGLES: " +
                (p.getPrice().get(Site.ELCORTEINGLES) == null
                        ? "Not Found"
                        : p.getPrice().get(Site.ELCORTEINGLES) + " €"));
        priceECI.setY(150.0);
        priceECI.setX(20.0);

        Text priceFNAC = new Text("Price FNAC: " +
                (p.getPrice().get(Site.FNAC) == null
                        ? "Not Found"
                        : p.getPrice().get(Site.FNAC) + " €"));
        priceFNAC.setY(180.0);
        priceFNAC.setX(20.0);

        Scene scene = new Scene(new Group(title, brand, model, types, priceECI, priceFNAC), 650, 200);
        dialog.setScene(scene);
        dialog.show();
    }

}



