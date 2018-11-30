package xoGame_Pack;

import javax.management.ReflectionException;

import javafx.application.Application;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.text.TextAlignment;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Reflection;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.Scene;


public class XoDoClass extends Application implements IXO{

	private char currPlayer = 'X';
	private CellMaker[][] getcell = new CellMaker[3][3];
	private Label getPlayerMSG = new Label ("Player : "+currPlayer+" Must Play Now ");
	private Button resetBTN = new Button ("Click Me To -Reset - Game : ");
	Color xPColor = new Color(.1,.1,.1,.1);
	Color oPColor = new Color(.1,.1,.1,.1);
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		GridPane gamepane = new GridPane();
		for (int i = 0 ; i < 3 ; i++) {
			for (int j = 0 ; j<3 ; j++) {
				getcell[i][j]=new CellMaker();
				
				//Grid  cell  May Diff in Place I & J
				gamepane.add(getcell[i][j],j,i);
				
			}
		}
		
		BorderPane outerPane = new BorderPane ();
		outerPane.setCenter(gamepane);
		ColorPicker cPick = new ColorPicker(); 
		ColorPicker XplayerCPick = new ColorPicker();
		ColorPicker OplayerCPick = new ColorPicker();
		// Top
		HBox onTopHBox = new HBox(40);
		Label BoardColor = new Label("PicK A Color For The Game Board");
		BoardColor.setGraphic(cPick);
		onTopHBox.getChildren().add(BoardColor);
		
		Label XPColor = new Label("PicK A Color For X Player ");
		XPColor.setGraphic(XplayerCPick);
		onTopHBox.getChildren().add(XPColor);
		
		Label OPlayer = new Label("PicK A Color For O Player");
		OPlayer.setGraphic(OplayerCPick);
		onTopHBox.getChildren().add(OPlayer);
		
		//
		outerPane.setTop(onTopHBox);
		resetBTN.setStyle("-fx- Border-color:yellow;-fx- Strock:RED;");
		resetBTN.setStyle("-fx-Color:Yellow;-fx-setStrock:Black;-fx-Border-color:GREEN; -fx-BackGround:BLACK;");
		getPlayerMSG.setGraphic(resetBTN);   
		resetBTN.setStyle("-fx-Strok:black");
		outerPane.setBottom(getPlayerMSG);
		outerPane.setStyle("-fx-Color:Yellow;-fx-setStrock:Black;-fx-Border-color:GREEN; -fx-BackGround:LIGHTPINK;");
		cPick.setOnAction(e->{String colorV ="#"+ cPick.getValue().toString().substring(2, cPick.getValue().toString().length()-2);outerPane.setStyle("-fx-BackGround:"+colorV+";");});
		
		XplayerCPick.setOnAction(e->{xPColor= XplayerCPick.getValue();});
		OplayerCPick.setOnAction(e->{oPColor= OplayerCPick.getValue();});
		
		outerPane.setEffect( new Reflection(.1,.1,.2,.2) );
		resetBTN.setOnMouseClicked(e->{primaryStage.close();XoDoClass newC = new XoDoClass();
		Stage primaryStag= new Stage();
		try {newC.start(primaryStag);}  catch (Exception e1) { e1.toString();} });
		Scene scene = new Scene(outerPane,900,450);
		primaryStage.setTitle("AhmedS.-XO- Game V. 0.01");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	

	@Override
	public boolean isTheGBoardFull() {
		
		for(int i =0 ; i<3;i++) {
		for (int j = 0 ; j<3;j++) {
			if (getcell[i][j].getPlayer()==' ') {
				return false;
			}
		}	
		}
		
		return true;
	}

	@Override
	public boolean Hasawinner(char currP) {
		for (int i = 0 ; i< 3 ; i ++ ) { 
	    if (getcell[i][0].getPlayer()==currP && getcell[i][1].getPlayer()==currP && getcell[i][2].getPlayer()==currP   ) {
	    	//System.out.println("1"+currP+"  pr");
	    	
			return true;
		}
		}
		for (int i = 0 ; i< 3 ; i ++ ) { 
	    if (getcell[0][i].getPlayer()==currP && getcell[1][i].getPlayer()==currP && getcell[2][i].getPlayer()==currP   ) {
	    	System.out.println("2"+currP);
			return true;
		}
		}
	    if (getcell[0][0].getPlayer()==currP && getcell[1][1].getPlayer()==currP && getcell[2][2].getPlayer()==currP   ) {
	    	//System.out.println("3"+currP);
			return true;
		}
	    if (getcell[0][2].getPlayer()==currP && getcell[1][1].getPlayer()==currP && getcell[2][0].getPlayer()==currP   ) {
	    	System.out.println("4"+currP);
			return true;
		}
	   
		
		return false;
	}
	
	public static void main(String[] args) {
		
        launch(args);
	}
	public class CellMaker extends Pane implements ICell {
		private char player = ' ';
		DropShadow eff = new DropShadow(.2,.1,.1,Color.BLACK);
		// cell constructor 
		CellMaker (){		
			this.setStyle("-fx-Border-Color: Black;");
			this.setEffect(eff);
			this.setPrefSize(300,300);
			this.setOnMouseClicked(  e->HandelClick()  );
		}
		private void HandelClick() {
			if (player ==' ' && currPlayer !=' ') {
				//System.out.println("hand "+currPlayer);
				setPlayerTo(currPlayer);
				//currPlayer=' ';
				if (Hasawinner(currPlayer)) {
				//	System.out.println("hand 2"+currPlayer);
					getPlayerMSG.setText(currPlayer+"Has Won >!");
					Alert alert = new Alert(AlertType.INFORMATION,"Congrates Player : "+currPlayer+" Has Won !",ButtonType.CLOSE);
					alert.setTitle("Congrates !"+currPlayer);
					alert.show();
					//System.out.println("hand 2-1 "+currPlayer);
				currPlayer=' ';
				}
				else if (isTheGBoardFull()) {
					getPlayerMSG.setText("Eq.Game TheBoard Is Full With No Winner  >!");
					Alert alert = new Alert(AlertType.INFORMATION,"The Board Is Full With No Winner !",ButtonType.CLOSE);
					alert.setTitle("Board Is Full! ");
					alert.show();
					currPlayer=' ';
					}
				else {
					currPlayer=(currPlayer=='X')?'O':'X';
				getPlayerMSG.setText(currPlayer+"Must Play Now >>>!");
				}
			}
		}
		
		@Override
		public void setPlayerTo(char c) {
			player = c;
			if (player =='X') {
				Line lineX1 = new Line (10,10,this.getHeight()-10,this.getWidth()-10);
				lineX1.endXProperty().bind(this.widthProperty().subtract(10));
				lineX1.endYProperty().bind(this.heightProperty().subtract(10));
				lineX1.setStroke(xPColor);
				Line lineX2 = new Line (10,this.getHeight()-10,this.getWidth()-10,10);
				lineX2.endXProperty().bind(this.widthProperty().subtract(10));
				lineX2.startYProperty().bind(this.heightProperty().subtract(10));
				lineX2.setStroke(xPColor);
				this.getChildren().addAll(lineX1,lineX2);	
				
			} 
			else if (player =='O') { 
				Ellipse playerOShape = new Ellipse (this.getWidth()/2,this.getHeight()/2,
						this.getWidth()/2-10,this.getHeight()/2-10);
				playerOShape.centerXProperty().bind(this.widthProperty().divide(2));
				playerOShape.centerYProperty().bind(this.heightProperty().divide(2));
				playerOShape.radiusXProperty().bind(this.widthProperty().divide(2).subtract(10));
				playerOShape.radiusYProperty().bind(this.heightProperty().divide(2).subtract(10));
				playerOShape.setStroke(Color.BLACK);playerOShape.setFill(oPColor);
				this.getChildren().addAll(playerOShape);
			}	
		}

		@Override
		public char getPlayer() {
			// TODO Auto-generated method stub
			return player;
		}
	}
}
