package Scene;

import ImplementationDAO.InfoImplDAO.AudioImplDAO;
import ImplementationDAO.InfoImplDAO.BookImplDAO;
import ImplementationDAO.InfoImplDAO.DocImplDAO;
import ImplementationDAO.InfoImplDAO.VideoImplDAO;
import ImplementationDAO.SuperExtd;
import Instances.InfoSources.*;
import Instances.Roles.MainModel;
import InterfacesDAO.CatInstDAO.InfoDAO;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Vector;

/**
 * Created by Андрей on 24.02.2017.
 */
class WorkWindow {
    private BorderPane workWindowLayout;
    private HBox VidgetsLayout;
    private VBox PagesLayout;
    private GridPane TableLayout;
    private Button booksButton, docsButton, audioButton, videoButton, backButton, okeyButton, cancelButton;
    private ImageView addImage, deleteImage, searchImage;
    private TextField searchField;
    private ScrollBar scrollBar;

    private  void handleScrollBarAction(ActionEvent event){

    }
    private void handleOkeyButtonAction(ActionEvent event) {
        try {
            SetTableLayout();
            switch (MainInfo.getInstNum()) {
            case 1:
                DocImplDAO docImplDAO = new DocImplDAO();
                ShowItems(docImplDAO.GetUserItem("*" + searchField.getText() + "*"));

            break;
            case 2:
                BookImplDAO bookImplDAO = new BookImplDAO();
                ShowItems(bookImplDAO.GetUserItem("*" + searchField.getText() + "*"));
                break;
            case 3:
                AudioImplDAO audioImplDAO = new AudioImplDAO();
                ShowItems(audioImplDAO.GetUserItem("*" + searchField.getText() + "*"));
                break;
            case 4:
                VideoImplDAO videoImplDAO = new VideoImplDAO();
                ShowItems(videoImplDAO.GetUserItem("*" + searchField.getText() + "*"));
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    private void handleCancelButtonAction(ActionEvent event) {
        okeyButton.setVisible(false);
        cancelButton.setVisible(false);
        searchField.clear();
        searchField.setVisible(false);
        SetTableLayout();
        switch (MainInfo.getInstNum()) {
        case 1:
            DocImplDAO docImplDAO=new DocImplDAO();
            ShowItems(docImplDAO.ExtructItems());
            break;
        case 2:
            BookImplDAO bookImplDAO=new BookImplDAO();
            ShowItems(bookImplDAO.ExtructItems());
            break;
        case 3:
            AudioImplDAO audioImplDAO=new AudioImplDAO();
            ShowItems(audioImplDAO.ExtructItems());
            break;
        case 4:
            VideoImplDAO videoImplDAO=new VideoImplDAO();
            ShowItems(videoImplDAO.ExtructItems());
            break;

        }
    }

    private void handleAddButtonAction(MouseEvent event) {
        try {
            switch (MainInfo.getInstNum()) {
            case 1:
                DocImplDAO docImplDAO = new DocImplDAO();
                docImplDAO.SetItem(SuperExtd.ChooseFile(new Stage()));
                ShowItems(docImplDAO.ExtructItems());
                break;
            case 2:
                BookImplDAO bookImplDAO = new BookImplDAO();
                bookImplDAO.SetItem(SuperExtd.ChooseFile(new Stage()));
                ShowItems(bookImplDAO.ExtructItems());
                break;
            case 3:
                AudioImplDAO audioImplDAO = new AudioImplDAO();
                audioImplDAO.SetItem(SuperExtd.ChooseFile(new Stage()));
                ShowItems(audioImplDAO.GetUserItem(null));
                break;
            case 4:
                VideoImplDAO videoImplDAO = new VideoImplDAO();
                videoImplDAO.SetItem(SuperExtd.ChooseFile(new Stage()));
                ShowItems(videoImplDAO.GetUserItem(null));
                break;
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    private void handleDeleteButtonAction(MouseEvent event) {

        searchField = new TextField("Type to search");
        cancelButton = null;
        okeyButton = null;
        searchField.setOnMouseClicked((event1) -> searchField.clear());
        searchField.setOnKeyPressed((event1) -> {
            if (cancelButton == null && okeyButton == null) {
                cancelButton = new Button("Cancel");
                okeyButton = new Button("Okey");
                okeyButton.setStyle(" -fx-background-color: #1e56ff; -fx-font-weight: bold;");
                VidgetsLayout.getChildren().addAll(okeyButton, cancelButton);
                cancelButton.setOnAction(this::handleCancelButtonAction);
                okeyButton.setOnAction(this::handleOkeyButtonAction);
            }
        });
        VidgetsLayout.getChildren().add(searchField);
        /*InfoDAO inst = null;
        switch (MainInfo.getInstNum()) {
        case 1:
            inst = new BookImplDAO();
            inst.DeleteItem();
            ShowItems(new Book());
            break;
        case 2:
            inst = new DocImplDAO();
            inst.DeleteItem();
            ShowItems(new Doc());
            break;
        case 3:
            inst = new AudioImplDAO();
            inst.DeleteItem();
            ShowItems(new Audio());
            break;
        case 4:
            inst = new VideoImplDAO();
            inst.DeleteItem();
            ShowItems(new Video());
            break;
        }*/
    }

    private void handleSearchButtonAction(MouseEvent event){

    }




    private void SetWorkWindowLayout() {
        workWindowLayout.setCenter(TableLayout);
        workWindowLayout.setLeft(PagesLayout);
        workWindowLayout.setTop(VidgetsLayout);
        workWindowLayout.setRight(scrollBar);
    }

    private void SetTableLayout() {

        //TableLayout.setStyle("-fx-background-color: #00FF00;");
        TableLayout.setGridLinesVisible(true);

        Node node = TableLayout.getChildren().get(0);
        TableLayout.getChildren().clear();
        TableLayout.getChildren().add(0, node);
        Text[] headText = new Text[]{new Text("№"), new Text("Name"), new Text("Size, Mb"), new Text("Date"), new Text("Blob")};
        TableLayout.addRow(0, headText);
        for (Text t : headText) {
            GridPane.setHalignment(t, HPos.CENTER);
        }
        TableLayout.getColumnConstraints().addAll(new ColumnConstraints(30), new ColumnConstraints(200),
                new ColumnConstraints(50), new ColumnConstraints(100), new ColumnConstraints(100));

    }

    private void SetVidgetsLayout() {
        VidgetsLayout.setMinHeight(40);
        VidgetsLayout.setAlignment(Pos.CENTER_LEFT);
        VidgetsLayout.setPadding(new Insets(15));
        VidgetsLayout.setStyle("-fx-background-color: #380c0c;");
        addImage = new ImageView(new Image("plus.png"));
       // VidgetsLayout.getChildren().add(addImage);
        addImage.setOnMousePressed(this::handleAddButtonAction);

        deleteImage = new ImageView(new Image("minus.png"));
       // VidgetsLayout.getChildren().add(deleteImage);
        deleteImage.setOnMousePressed(this::handleDeleteButtonAction);

        searchImage=new ImageView(new Image("search.png"));

        Label greetText=new Label("Hi there,"+ MainModel.getName());
        greetText.setWrapText(true);
        greetText.setMaxWidth(150);
        greetText.setStyle(" -fx-text-fill:#FFFFFF;");
        greetText.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
VidgetsLayout.setSpacing(10);
        VidgetsLayout.getChildren().add(greetText);

        if (MainModel.getRole() !=3) {
            VidgetsLayout.getChildren().add(addImage);
            VidgetsLayout.getChildren().add(deleteImage);
        }
        VidgetsLayout.getChildren().add(searchImage);
    }

    private void SetPagesLayout(Stage workStage) {
        PagesLayout.setMinSize(100, 300);
        PagesLayout.setPadding(new Insets(10, 15, 0, 15));
        PagesLayout.setSpacing(10);
        //GridPane.setVgrow();
        // PagesLayout.set
        // VidgetsLayout.setPadding(new Insets(10, 10, 10, 10));
      /*  Button q=new Button("qw");
        LeftPanel.getChildren().add(q);*/
        booksButton = new Button("Books");
        docsButton = new Button("Docs");
        audioButton = new Button("Music");
        videoButton = new Button("Video");
        backButton = new Button("Back");
        backButton.setStyle(" -fx-background-color: #1e56ff; -fx-font-weight: bold;");
        backButton.setOnAction((event) -> new EnterWindow(workStage));
        booksButton.setMinSize(70, 30);
        docsButton.setMinSize(70, 30);
        audioButton.setMinSize(70, 30);
        videoButton.setMinSize(70, 30);
        backButton.setMinSize(70, 30);
        VBox.setMargin(backButton, new Insets(50, 0, 0, 0));
        PagesLayout.getChildren().addAll(booksButton, docsButton, audioButton, videoButton, backButton);
    }

    private <Info extends MainInfo> void ShowItems(Vector<Info> itemsToShow) {
        if (!itemsToShow.isEmpty())
            for (int i = 0; i < itemsToShow.size(); i++) {
                Text[] newText = new Text[]{new Text(String.valueOf(i + 1)),
                        new Text(itemsToShow.get(i).getInstName()), new Text(String.valueOf(itemsToShow.get(i).getInstSize())),
                        new Text(String.valueOf(itemsToShow.get(i).getInstDate())), new Text(String.valueOf(itemsToShow.get(i).getInstBLOB()))};
                for (Text t : newText) {
                    GridPane.setHalignment(t, HPos.CENTER);
                }
                newText[1].setWrappingWidth(200);
                TableLayout.addRow(i + 1, newText);

            }
    }

    WorkWindow(Stage workStage) {
        /*DAO Implemenattions definition*/
        BookImplDAO bookImplDAO = new BookImplDAO();
        DocImplDAO docImplDAO = new DocImplDAO();
        AudioImplDAO audioImplDAO = new AudioImplDAO();
        VideoImplDAO videoImplDAO = new VideoImplDAO();

        /*Layout definition*/
        workWindowLayout = new BorderPane();
        VidgetsLayout = new HBox();
        PagesLayout = new VBox();
        TableLayout = new GridPane();

        /*Vidgets Layout and components*/
        SetVidgetsLayout();

         /*Table Layout and components*/
        SetTableLayout();
        TableLayout.getRowConstraints().add(new RowConstraints(20));

        /*Pages Layout and components*/
        SetPagesLayout(workStage);

        docsButton.setOnAction(e -> {
            SetTableLayout();
            ShowItems(docImplDAO.ExtructItems());
        });
        booksButton.setOnAction(event -> {
            SetTableLayout();
            ShowItems(bookImplDAO.ExtructItems());
        });
        audioButton.setOnAction(event -> {
            SetTableLayout();
            ShowItems(audioImplDAO.ExtructItems());

        });
        videoButton.setOnAction(event -> {
            SetTableLayout();
            ShowItems(videoImplDAO.ExtructItems());
        });


       /*WorkWindow Layout and components*/
        SetWorkWindowLayout();
        workStage.setScene(new Scene(workWindowLayout, 580, 350));
    }


}

