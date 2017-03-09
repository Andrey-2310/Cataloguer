package Scene;

import ImplementationDAO.InfoImplDAO.AudioImplDAO;
import ImplementationDAO.InfoImplDAO.BookImplDAO;
import ImplementationDAO.InfoImplDAO.DocImplDAO;
import ImplementationDAO.InfoImplDAO.VideoImplDAO;
import ImplementationDAO.RolesImplDAO.AdminImplDAO;
import ImplementationDAO.SuperExtd;
import Instances.InfoSources.*;
import Instances.Roles.MainModel;
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
import java.lang.reflect.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by Андрей on 24.02.2017.
 */
class WorkWindow {
    private BorderPane workWindowLayout;
    private HBox VidgetsLayout;
    private VBox PagesLayout;
    private GridPane InfoTableLayout, UserTableLayout;
    private final Button okeyButton = new Button("Ok");
    private final Button cancelButton = new Button("Cancel");
    private ImageView deleteImage;
    private final TextField searchField = new TextField();
    private ScrollPane scrollPane=new ScrollPane();
    private BookImplDAO bookImplDAO = new BookImplDAO();
    private DocImplDAO docImplDAO = new DocImplDAO();
    private AudioImplDAO audioImplDAO = new AudioImplDAO();
    private VideoImplDAO videoImplDAO = new VideoImplDAO();
    private AdminImplDAO adminImplDAO = new AdminImplDAO();

    private void handleScrollBarAction(ActionEvent event) {

    }

    private void OkeyButtonAction() {
        try {
            SetTableLayout();
            switch (MainInfo.getInstNum()) {
            case 1:
                Vector<Doc> docs = docImplDAO.GetUserItem("*" + searchField.getText() + "*");
                ShowItems(docs);
                if (!docs.isEmpty()) deleteImage.setVisible(true);
                break;
            case 2:
                Vector<Book> books = bookImplDAO.GetUserItem("*" + searchField.getText() + "*");
                ShowItems(books);
                if (!books.isEmpty()) deleteImage.setVisible(true);
                break;
            case 3:
                Vector<Audio> audios = audioImplDAO.GetUserItem("*" + searchField.getText() + "*");
                ShowItems(audios);
                if (!audios.isEmpty()) deleteImage.setVisible(true);
                break;
            case 4:
                Vector<Video> videos = videoImplDAO.GetUserItem("*" + searchField.getText() + "*");
                ShowItems(videos);
                if (!videos.isEmpty()) deleteImage.setVisible(true);
            }
            deleteImage.setOnMousePressed(this::handleDeleteButtonAction);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    private void handleSearchingOkeyButtonAction(ActionEvent event) {
        OkeyButtonAction();
    }


    private void handleCancelButtonAction(ActionEvent event) {
        okeyButton.setVisible(false);
        cancelButton.setVisible(false);
        searchField.clear();
        searchField.setVisible(false);
        SetTableLayout();
        switch (MainInfo.getInstNum()) {
        case 1:
            DocImplDAO docImplDAO = new DocImplDAO();
            ShowItems(docImplDAO.ExtructItems());
            break;
        case 2:
            BookImplDAO bookImplDAO = new BookImplDAO();
            ShowItems(bookImplDAO.ExtructItems());
            break;
        case 3:
            AudioImplDAO audioImplDAO = new AudioImplDAO();
            ShowItems(audioImplDAO.ExtructItems());
            break;
        case 4:
            VideoImplDAO videoImplDAO = new VideoImplDAO();
            ShowItems(videoImplDAO.ExtructItems());
            break;
        }
        deleteImage.setVisible(false);
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
        try {
            switch (MainInfo.getInstNum()) {
            case 1:
                docImplDAO.DeleteItem("*" + searchField.getText() + "*");
                SetTableLayout();
                ShowItems(docImplDAO.ExtructItems());
                break;
            case 2:
                bookImplDAO.DeleteItem("*" + searchField.getText() + "*");
                SetTableLayout();
                ShowItems(bookImplDAO.ExtructItems());
                break;
            case 3:
                audioImplDAO.DeleteItem("*" + searchField.getText() + "*");
                SetTableLayout();
                ShowItems(audioImplDAO.ExtructItems());
                break;
            case 4:
                videoImplDAO.DeleteItem("*" + searchField.getText() + "*");
                SetTableLayout();
                ShowItems(videoImplDAO.ExtructItems());
                break;
            }
            searchField.clear();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void handleSearchButtonAction(MouseEvent event) {
        searchField.setVisible(true);
        searchField.setText("Type to Search");
        searchField.setOnMouseClicked((event1) -> searchField.clear());
        searchField.setOnKeyTyped((event1) -> {
            if (event1.getCharacter().equals("\r"))
                OkeyButtonAction();
            //  if(event1.getCharacter().equals(""))
            if (!cancelButton.isVisible() && !okeyButton.isVisible()) {
                cancelButton.setVisible(true);
                okeyButton.setVisible(true);
                okeyButton.setStyle(" -fx-background-color: #1e56ff; -fx-font-weight: bold;");
            }
            cancelButton.setOnAction(this::handleCancelButtonAction);
            okeyButton.setOnAction(this::handleSearchingOkeyButtonAction);

        });
    }

    private void SetWorkWindowLayout() {
        workWindowLayout.setCenter(scrollPane);
        workWindowLayout.setLeft(PagesLayout);
        workWindowLayout.setTop(VidgetsLayout);
    }

    private void SetTableLayout() {
        scrollPane.setContent(InfoTableLayout);
        InfoTableLayout.setGridLinesVisible(true);
        Text[] headText = new Text[]{new Text("№"), new Text("Name"), new Text("Size, Mb"), new Text("Date"), new Text("Blob")};

        Node node = InfoTableLayout.getChildren().get(0);
        InfoTableLayout.getChildren().clear();
        InfoTableLayout.getChildren().add(0, node);
        InfoTableLayout.addRow(0, headText);
        for (Text t : headText) {
            GridPane.setHalignment(t, HPos.CENTER);
        }
        InfoTableLayout.setStyle(" -fx-background-color:#c2dbad; -fx-border-color: #380c0c;");

    }

    private void SetVidgetsLayout() {
        VidgetsLayout.setMinHeight(40);
        VidgetsLayout.setAlignment(Pos.CENTER_LEFT);
        VidgetsLayout.setPadding(new Insets(15));
        VidgetsLayout.setStyle("-fx-background-color: #380c0c;");

        ImageView addImage = new ImageView(new Image("plus.png"));
        addImage.setOnMousePressed(this::handleAddButtonAction);

        deleteImage = new ImageView(new Image("basket.png"));
        deleteImage.setOnMousePressed(this::handleDeleteButtonAction);

        ImageView searchImage = new ImageView(new Image("search.png"));
        searchImage.setOnMousePressed(this::handleSearchButtonAction);

        ImageView playImage = new ImageView(new Image("play.png"));
        Label greetText = new Label("Hi there," + MainModel.getName());
        greetText.setWrapText(true);
        greetText.setMaxWidth(120);
        greetText.setStyle(" -fx-text-fill:#FFFFFF;");
        greetText.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
        VidgetsLayout.setSpacing(10);
        VidgetsLayout.getChildren().addAll(greetText, playImage);

        if (MainModel.getRole() != 3) {
            VidgetsLayout.getChildren().add(addImage);
        }
        VidgetsLayout.getChildren().add(searchImage);
        okeyButton.setVisible(false);
        cancelButton.setVisible(false);
        searchField.setVisible(false);
        VidgetsLayout.getChildren().addAll(searchField, okeyButton, cancelButton, deleteImage);
        deleteImage.setVisible(false);
    }

    private void SetPagesLayout(Stage workStage) {
        PagesLayout.setMinSize(100, 300);
        PagesLayout.setPadding(new Insets(10, 15, 0, 15));
        PagesLayout.setSpacing(10);
        Button booksButton = new Button("Books");
        Button docsButton = new Button("Docs");
        Button audioButton = new Button("Music");
        Button videoButton = new Button("Video");
        Button usersButton = new Button("Users");
        Button backButton = new Button("Back");
        backButton.setStyle(" -fx-background-color: #1e56ff; -fx-font-weight: bold;");
        booksButton.setMinSize(70, 30);
        docsButton.setMinSize(70, 30);
        audioButton.setMinSize(70, 30);
        videoButton.setMinSize(70, 30);
        backButton.setMinSize(70, 30);
        usersButton.setMinSize(70, 30);
        if (MainModel.getId() != 1) usersButton.setVisible(false);
        else usersButton.setVisible(true);
        VBox.setMargin(backButton, new Insets(20, 0, 0, 0));
        PagesLayout.getChildren().addAll(docsButton, booksButton, audioButton, videoButton, usersButton, backButton);

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
        usersButton.setOnAction(event -> {
           scrollPane.setContent(UserTableLayout);

        /*    AdminImplDAO adminImplDAO = new AdminImplDAO();
            try {
                ShowUsers(adminImplDAO.GetAllUsers());
            } catch (SQLException | IOException e) {
                e.printStackTrace();
            }*/
        });

        backButton.setOnAction((event) -> new EnterWindow(workStage));
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
                InfoTableLayout.addRow(i + 1, newText);

            }
    }

    private void ShowUsers(Vector<Text[]> users) {
        UserTableLayout.setGridLinesVisible(true);
        Text[] headText = new Text[]{new Text("№"), new Text("ID"), new Text("Name"),
                new Text("Password"), new Text("E-mail"), new Text("Role"), new Text("Place")};
        UserTableLayout.addRow(0, headText);
        for (Text t : headText) {
            GridPane.setHalignment(t, HPos.CENTER);
        }
        UserTableLayout.setStyle(" -fx-background-color:#c2dbad; -fx-border-color: #380c0c;");
        if (UserTableLayout.getColumnConstraints().isEmpty())
            UserTableLayout.getColumnConstraints().addAll(new ColumnConstraints(30), new ColumnConstraints(30),
                    new ColumnConstraints(100), new ColumnConstraints(100), new ColumnConstraints(150),
                    new ColumnConstraints(35), new ColumnConstraints(35));
        if (!users.isEmpty()) {
            for (int i = 0; i < users.size(); i++) {
                UserTableLayout.addRow(i + 1, users.get(i));
                for (Text user : users.get(i)) {
                    GridPane.setHalignment(user, HPos.CENTER);
                }
            }
        }

    }

    WorkWindow(Stage workStage) {
        /*DAO Implemenattions definition*/


        /*Layout definition*/
        workWindowLayout = new BorderPane();
        VidgetsLayout = new HBox();
        PagesLayout = new VBox();
        InfoTableLayout = new GridPane();
        UserTableLayout = new GridPane();
        /*Vidgets Layout and components*/
        SetVidgetsLayout();

         /*Table Layout and components*/
        SetTableLayout();
        /*Pages Layout and components*/
        SetPagesLayout(workStage);
        InfoTableLayout.getRowConstraints().add(new RowConstraints(20));
        InfoTableLayout.getColumnConstraints().addAll(new ColumnConstraints(30), new ColumnConstraints(200),
                new ColumnConstraints(50), new ColumnConstraints(100), new ColumnConstraints(100));



       /*WorkWindow Layout and components*/
        SetWorkWindowLayout();
        try {
            ShowUsers(adminImplDAO.GetAllUsers());
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        ShowItems(docImplDAO.ExtructItems());
        workStage.setScene(new Scene(workWindowLayout, 580, 350));
        workStage.setTitle("Home Cataloguer");
    }


}

