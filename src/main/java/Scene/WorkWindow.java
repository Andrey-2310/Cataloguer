package Scene;

import ImplementationDAO.InfoImplDAO.*;

import ImplementationDAO.RolesImplDAO.AdminImplDAO;
import ImplementationDAO.SuperExtd;
import Instances.InfoSources.*;
import Instances.Roles.MainModel;
import Instances.Roles.User;
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

/**
 * This class represents the main window for working in app
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
    private ScrollPane scrollPane = new ScrollPane();
    private BookImplDAO bookImplDAO = new BookImplDAO();
    private DocImplDAO docImplDAO = new DocImplDAO();
    private AudioImplDAO audioImplDAO = new AudioImplDAO();
    private VideoImplDAO videoImplDAO = new VideoImplDAO();
    private AdminImplDAO adminImplDAO = new AdminImplDAO();

    /**
     * this function handler Okey Button in  Searching mode.
     * It reads data from tetxlabel and then call GetUserItem method of item which is turn on right now
     * @param event - event which occured and it's the cause of action
     */
    private void handleSearchingOkeyButtonAction(ActionEvent event) {
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
                break;
            case 5:
                Vector<Text[]> users = adminImplDAO.SearchUsers("*" + searchField.getText() + "*");
                ShowUsers(users);
                if (!users.isEmpty()) deleteImage.setVisible(true);
                scrollPane.setContent(UserTableLayout);
            }
            deleteImage.setOnMousePressed(this::handleDeleteButtonAction);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * this function handlers Okey Button in Playing mode
     * It calls OpenFiles method of item which is turn on right now, search item with entered index
     * @param event  event which occured and it's the cause of action
     */
    private void handlePlayingOkeyButtonAction(ActionEvent event) {
        if (searchField.getText().matches("[+]?\\d+"))
            try {
                switch (MainInfo.getInstNum()) {
                case 1:
                    docImplDAO.OpenItem(Integer.valueOf(searchField.getText()));
                    break;
                case 2:
                    bookImplDAO.OpenItem(Integer.valueOf(searchField.getText()));
                    break;
                case 3:
                    audioImplDAO.OpenItem(Integer.valueOf(searchField.getText()));
                    break;
                case 4:
                    videoImplDAO.OpenItem(Integer.valueOf(searchField.getText()));
                    break;
                }
            } catch (SQLException | IOException | InterruptedException e) {
                e.printStackTrace();
            }
        else {
            System.out.println("Enter correct number");
            searchField.clear();
        }
    }


    /**
     * this function handlers Play Button, creates handler for searchField, okeyButton and cancelButton
     * @param event  event which occured and it's the cause of action
     */
    private void handlePlayButtonAction(MouseEvent event) {
        searchField.setVisible(true);
        searchField.setText("Type № to Open");
        searchField.setOnMouseClicked((event1) -> searchField.clear());
        searchField.setOnKeyTyped((event1) -> {
            if (!cancelButton.isVisible() && !okeyButton.isVisible()) {
                cancelButton.setVisible(true);
                okeyButton.setVisible(true);
                okeyButton.setStyle(" -fx-background-color: #1e56ff; -fx-font-weight: bold;");
            }
            cancelButton.setOnAction(this::handleCancelButtonAction);
            okeyButton.setOnAction(this::handlePlayingOkeyButtonAction);
        });
    }

    /**
     * this function handlers Cancel Button. It clears searchfield, and goes back to
     * table of user cataloguer
     * @param event - event which occured and it's the cause of action
     */

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
        case 5:
            try {
                ShowUsers(adminImplDAO.GetAllUsers());
                scrollPane.setContent(UserTableLayout);
            } catch (SQLException | IOException e) {
                e.printStackTrace();
            }
            break;
        }
        deleteImage.setVisible(false);
    }

    /**
     * this function handlers Add Button. It chooses the way to add file
     * depending on item which is turned on
     * @param event - event which occured and it's the cause of action
     */

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
            case 5:
                new NewUserWindow(new Stage());
                break;
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * this function handlers Delete Button. It chooses the way to add file
     * depending on item which is turned on
     * @param event - event which occured and it's the cause of action
     */
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
            case 5:
                adminImplDAO.DeleteUser("*" + searchField.getText() + "*");
                ShowUsers(adminImplDAO.GetAllUsers());
                scrollPane.setContent(UserTableLayout);
            }
            searchField.clear();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     *  this function handlers Search Button, creates handler for searchField, okeyButton and cancelButton
     * @param event  event which occured and it's the cause of action
     */
    private void handleSearchButtonAction(MouseEvent event) {
        searchField.setVisible(true);
        searchField.setText("Type to Search");
        searchField.setOnMouseClicked((event1) -> searchField.clear());
        searchField.setOnKeyTyped((event1) -> {
            if (!cancelButton.isVisible() && !okeyButton.isVisible()) {
                cancelButton.setVisible(true);
                okeyButton.setVisible(true);
                okeyButton.setStyle(" -fx-background-color: #1e56ff; -fx-font-weight: bold;");
            }
            cancelButton.setOnAction(this::handleCancelButtonAction);
            okeyButton.setOnAction(this::handleSearchingOkeyButtonAction);

        });
    }

    /**
     * this method is used to build in layouts in main borderLayout
     */
    private void SetWorkWindowLayout() {
        workWindowLayout.setCenter(scrollPane);
        workWindowLayout.setLeft(PagesLayout);
        workWindowLayout.setTop(VidgetsLayout);
    }

    /**
     *  this method is used to build TableLayout
     */
    private void SetTableLayout() {
        scrollPane.setContent(InfoTableLayout);
        InfoTableLayout.setGridLinesVisible(true);
        Text[] headText = new Text[]{new Text("№"), new Text("Name"), new Text("Size"), new Text("Date"), new Text("Blob")};

        Node node = InfoTableLayout.getChildren().get(0);
        InfoTableLayout.getChildren().clear();
        InfoTableLayout.getChildren().add(0, node);
        InfoTableLayout.addRow(0, headText);
        for (Text t : headText) {
            GridPane.setHalignment(t, HPos.CENTER);
        }
        InfoTableLayout.setStyle(" -fx-background-color:#c2dbad; -fx-border-color: #380c0c;");

    }

    /**
     * this method is used to build VidgetsLayout, set vidgets and their handlers
     */
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
        playImage.setOnMousePressed(this::handlePlayButtonAction);

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

    /**
     * this method is used to build PageLayout, set buttons to change page, handlers
     * @param workStage - event which occured and it's the cause of action
     */

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
            MainInfo.setInstNum(5);
        });

        backButton.setOnAction((event) -> new EnterWindow(workStage));
    }


    /**
     * this methods shows all items thst are needed
     * @param itemsToShow- Vector of user which app needs to show
     * @param <Info> - it can be Book, Doc, Video, Audio
     */
    private <Info extends MainInfo> void ShowItems(Vector<Info> itemsToShow) {

        if (!itemsToShow.isEmpty())
            for (int i = 0; i < itemsToShow.size(); i++) {
                Text[] newText = new Text[]{new Text(String.valueOf(i + 1)),
                        new Text(itemsToShow.get(i).getInstName()), new Text(String.valueOf(itemsToShow.get(i).getInstSize())),
                        new Text(String.valueOf(itemsToShow.get(i).getInstDate())), new Text(String.valueOf(itemsToShow.get(i).getInstBLOB()))};
                for (Text t : newText) {
                    GridPane.setHalignment(t, HPos.CENTER);
                }
                newText[1].setWrappingWidth(140);
                newText[4].setWrappingWidth(140);
                InfoTableLayout.addRow(i + 1, newText);

            }
    }


    /**
     * This method helps to Show all Users
     * @param users Vector of Text[] in which of them info about one user.
     */
    private void ShowUsers(Vector<Text[]> users) {
        UserTableLayout.setGridLinesVisible(true);
        Text[] headText = new Text[]{new Text("№"), new Text("ID"), new Text("Name"),
                new Text("Password"), new Text("E-mail"), new Text("Role"), new Text("Place")};
        Node node = UserTableLayout.getChildren().get(0);
        UserTableLayout.getChildren().clear();
        UserTableLayout.getChildren().add(0, node);
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

    /**
     * This method is main in this class. it creates main layout and others issues
     * @param workStage  - event which occured and it's the cause of action
     */
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
        InfoTableLayout.getColumnConstraints().addAll(new ColumnConstraints(30), new ColumnConstraints(140),
                new ColumnConstraints(30), new ColumnConstraints(140), new ColumnConstraints(140));



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

