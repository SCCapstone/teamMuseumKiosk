package controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;

public class DeleteAdvertisementsController extends AdminController{

    private final TableView<Advertisement> tableView = new TableView<Advertisement>();

    @FXML
    private Group advertisementGroup;

    private ObservableList<Advertisement> dataList = FXCollections.observableArrayList();

    public void initialize() throws FileNotFoundException {

        String adName = "";
        Image adImage;

        dataList = populateDataList();

        TableColumn columnAdImage = new TableColumn("Ad Image");
        columnAdImage.setCellValueFactory(new PropertyValueFactory<Advertisement, Image>("adImage"));
        columnAdImage.setMinWidth(200);

        TableColumn columnAdName = new TableColumn("Ad File Name");
        columnAdName.setCellValueFactory(new PropertyValueFactory<Advertisement, String>("adName"));

        tableView.setItems(dataList);
        tableView.getColumns().addAll(columnAdImage, columnAdName);

        // setting table size
        tableView.setMinWidth(550);
        tableView.setMinHeight(400);

        // sets column number to 2
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        VBox vBox = new VBox();
        vBox.setSpacing(50);
        vBox.getChildren().add(tableView);

        advertisementGroup.getChildren().add(vBox);

        // grabbing user's selected advertisement from table
        tableView.getSelectionModel().setCellSelectionEnabled(true);
        ObservableList selectedCells = tableView.getSelectionModel().getSelectedCells();

        selectedCells.addListener(new ListChangeListener() {
            @Override
            public void onChanged(Change c) {
                TablePosition tablePosition = (TablePosition) selectedCells.get(0);
                int advertisementRow = tablePosition.getRow();

                try {
                    deleteFile(advertisementRow);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public ObservableList populateDataList() throws FileNotFoundException {

        String adName = "";
        Image adImage;

        // get list of current advertisements
        String directoryPath = "./Advertisements";
        ArrayList<File> files = new ArrayList<File>();

        File path = new File(directoryPath);

        File[] fileList = path.listFiles();
        for (int i = 0; i < fileList.length; i++) {

            // adding name and image to advertisement object
            adName = fileList[i].toString();

            //Creating an image
            adImage = new Image(new FileInputStream(fileList[i]));

            //Setting the image view
            ImageView imageView = new ImageView(adImage);

            Advertisement advertisement = new Advertisement(imageView, adName);
            // add to dataList
            dataList.add(advertisement);
        }

        return dataList;
    }

    //  for displaying list of advertisements
    public class Advertisement {
        // each advertisement has name and image
        private SimpleStringProperty adName;
        private ImageView adImage;

        public String getAdName() { return adName.get(); }
        public ImageView getAdImage() {
            adImage.setPreserveRatio(true);
            adImage.setFitHeight(100);
            return adImage;
        }

        Advertisement(ImageView adImage, String adName) {
            this.adImage = adImage;
            this.adName = new SimpleStringProperty(adName);
        }
    }

    public void deleteFile (int advertisementRow) throws IOException {

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setOpacity(0.75);
        URL url = new URL(getClass().getResource("/design/popup.fxml").toExternalForm());
        FXMLLoader loader = new FXMLLoader(url);
        Parent root = loader.load();

        PopupController controller = loader.getController();
        loader.setController(controller);
        controller.setText("Are you sure you want to delete this advertisement? " +
                "This will delete the file from your system.");
        controller.setButtonText("No");
        controller.setDeleteAdButton("Yes");
        controller.setStage(stage);

        Scene scene = new Scene(root, 600, 485);
        stage.setScene(scene);
        stage.showAndWait();

        if (controller.isDeleteAdFile()) {

            // deleting image file requested by user
            String fileName = dataList.get(advertisementRow).adName.getValue();

            File path = new File(fileName);
            //path.delete();
            Files.delete(path.toPath());
        }
    }

    private void showPopupWindow(Stage stage, String text) throws IOException { loadPopupScene(stage, text); }

    public void closePage(ActionEvent actionEvent) throws IOException {
        advertisementGroup.getScene().getWindow().hide();
    }
}
