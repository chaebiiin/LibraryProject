package library_pack;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Book {
	private SimpleIntegerProperty id;
	private SimpleStringProperty title;
	private SimpleStringProperty writer;
	private SimpleIntegerProperty status;
	private SimpleStringProperty state;
	private SimpleStringProperty content;
	private SimpleStringProperty image;
	
	public Book(int id, String title, String writer, int status, String state, String content, String image) {
		super();
		this.id = new SimpleIntegerProperty(id);
		this.title = new SimpleStringProperty(title);
		this.writer = new SimpleStringProperty(writer);
		this.status = new SimpleIntegerProperty(status);
		this.state = new SimpleStringProperty(state);
		this.content = new SimpleStringProperty(content);
		this.image = new SimpleStringProperty(image);
	}

//id
	public void setId(int id) {
		this.id.set(id);
	}

	public int getId() {
		return this.id.get();
	}

	public SimpleIntegerProperty idPriProperty() {
		return this.id;
	}

//title
	public void setTitle(String title) {
		this.title.set(title);
	}

	public String getTitle() {
		return title.get();
	}

	public SimpleStringProperty titleProprtProperty() {
		return this.title;
	}

//writer
	public void setWriter(String writer) {
		this.writer.set(writer);
	}

	public String getWriter() {
		return writer.get();
	}

	public SimpleStringProperty writerProperty() {
		return this.writer;
	}

//status

	public void setStatus(int status) {
		this.status.set(status);
	}

	public int getStatus() {
		return status.get();
	}

	public SimpleIntegerProperty statusProperty() {
		return this.status;
	}

//state	
	public void setState(String state) {
		this.state.set(state);
	}
public String getState() {
		return state.get();
	}
public SimpleStringProperty stateProperty() {
	return this.state;
}


	//content
	public void setContent(String content) {
		this.content.set(content);
	}

	public String getContent() {
		return content.get();
	}

	public SimpleStringProperty contentProperty() {
		return this.content;
	}
	
	//image
	public void setImage(String image) {
		this.image.set(image);
	}
	public String getImage() {
		return image.get();
	}
	public SimpleStringProperty imageProperty() {
		return this.image;
	}
	
	
}

//ID        NUMBER(20)      
//TITLE     VARCHAR2(500)  
//WRITER    VARCHAR2(500)  
//STATUS    NUMBER(30)     
//CONTENT   VARCHAR2(2000) 
// image VARCHAR2(2000)