package library_pack;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class BookController implements Initializable {
	Connection conn;
	@FXML
	TableView<Book> tableView;
	@FXML
	TableColumn<Book, String> title, writer, content, state;
	@FXML
	TableColumn<Book, Integer> id, status;
	@FXML
	Button btnRental, btnReturn, btnCancle, btnBack, btnNext;
	Book book;


	
	
	//확인용확인확인
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "hr", "hr");
		} catch (Exception e) {
			e.printStackTrace();
		}	
		ObservableList<Book> bookList = getBookList();
		tableView.setItems(bookList);

		id.setCellValueFactory(new PropertyValueFactory<Book, Integer>("id"));
		title.setCellValueFactory(new PropertyValueFactory<Book, String>("title"));
		writer.setCellValueFactory(new PropertyValueFactory<Book, String>("writer"));
		state.setCellValueFactory(new PropertyValueFactory<Book, String>("state"));
		status.setCellValueFactory(new PropertyValueFactory<Book, Integer>("status"));
		content.setCellValueFactory(new PropertyValueFactory<Book, String>("content"));

		tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Book>() {

			@Override
			public void changed(ObservableValue<? extends Book> observable, Book oldValue, Book newValue) {
				book = newValue;
			}
		});

		tableView.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if (event.getClickCount() == 2) {
					Stage addStage = new Stage(StageStyle.UTILITY);
					addStage.initModality(Modality.WINDOW_MODAL);
					addStage.initOwner(btnRental.getScene().getWindow());

					try {
						Parent parent = FXMLLoader.load(getClass().getResource("list.fxml"));
						Scene scene = new Scene(parent);
						addStage.setScene(scene);
						addStage.setResizable(false);
						addStage.show();
						addStage.setTitle("도서상세내용");

						Label listTitle = (Label) parent.lookup("#listTitle");
						listTitle.setText(book.getTitle());

						TextArea listContent = (TextArea) parent.lookup("#listContent1");
						listContent.setText(book.getContent());
						ImageView imageView = (ImageView) parent.lookup("#imageView");
						imageView.setImage(new Image("/images/" + book.getImage() + ".jpg"));

						Button btnOk = (Button) parent.lookup("#btnOk");
						btnOk.setOnAction(new EventHandler<ActionEvent>() {

							@Override
							public void handle(ActionEvent event) {
								addStage.close();

							}
						});
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
	}

	public ObservableList<Book> getBookList() {
		ObservableList<Book> bookList = FXCollections.observableArrayList();
		String sql = "select id, title, writer, status, state, content, image from book";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Book book = new Book(rs.getInt("id"), rs.getString("title"), rs.getString("writer"),
						rs.getInt("status"), rs.getString("state"), rs.getString("content"), rs.getString("image"));
				bookList.add(book);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bookList;
	}

	public void handleBtnRentalAction(ActionEvent e) {	
			String sql = " UPDATE book set status = status-1, state = decode(status,1,'대출불가','대출가능') "  + 
				"WHERE id = "+ tableView.getSelectionModel().selectedItemProperty().get().getId();
				PreparedStatement pstmt;
				try {
					pstmt = conn.prepareStatement(sql);
						pstmt.executeUpdate();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			
			tableView.setItems(getBookList());
	}
	public void handleBtnReturnlAction(ActionEvent e) {
		String sql = "UPDATE book set status = status+1, state = decode(status,0,'대출가능','대출가능') " +
				"WHERE id = "+ tableView.getSelectionModel().selectedItemProperty().get().getId();
		PreparedStatement pstmt;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.executeUpdate();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		tableView.setItems(getBookList());

	}
			

	public void handleBtnBackAction(ActionEvent ae) {
		int rowIndex = tableView.getSelectionModel().getSelectedIndex();
		if (rowIndex > 0) {
			tableView.getSelectionModel().select(rowIndex - 1);
		}
	}

	
	public void handleBtnNextAction(ActionEvent ae) {
		tableView.getSelectionModel().selectNext();
	}

	
	public void handleBtnCancleAction(ActionEvent e) {
		Platform.exit();
//수정
	}

}
