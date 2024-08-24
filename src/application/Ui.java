package application;

import java.awt.Color;
import java.net.URL;
import javax.sound.sampled.Clip;
import javafx.animation.TranslateTransition;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Ui {
	Etat currentState = new Etat_can_miss(3, 3, true);
	Operation operation = null;
	boolean leftSpot = true, rightSpot = true;
	int can1Spot=1, can2Spot=1, can3Spot=1, mis1Spot=1, mis2Spot=1, mis3Spot=1;
	int boatDist[] = new int[3];
	int steps=0;
	boolean solved =false;
	Clip clip;
	
	private MediaPlayer bgMediaPlayer;
	private MediaPlayer jumpMediaPlayer;
	private MediaPlayer waterMediaPlayer;
	private MediaPlayer goMediaPlayer;
	private MediaPlayer winMediaPlayer;
	private MediaPlayer loseMediaPlayer;
	
 
	public void launchInterface(Stage primaryStage) {
		playBgSound();
		boatDist[0]=0; boatDist[1]=0; boatDist[2]=0;
			
		// Création des images de fond pour chaque rectangle
        Image cielImage = new Image("/images/sky.jpg");
        Image premierRiveImage = new Image("/images/grass.png");
        Image deuxiemeRiveImage = new Image("/images/grass.png");
        Image riveImage = new Image("/images/river.png");
        Image boatImage = new Image("/images/boat.png");
        Image canImage = new Image("/images/cannibale.png");
        Image misImage = new Image("/images/missionaire.png");

        // Création des rectangles pour les parties du paysage
        Rectangle ciel = new Rectangle(1000, 600);
        Rectangle premierRive = new Rectangle(330, 300);
        Rectangle deuxiemeRive = new Rectangle(330, 300);
        Rectangle rive = new Rectangle(382, 515);
        Rectangle boat = new Rectangle(150, 180);
        Rectangle can1 = new Rectangle(70, 70);
        Rectangle can2 = new Rectangle(70, 70);
        Rectangle can3 = new Rectangle(70, 70);
        Rectangle mis1 = new Rectangle(90, 80);
        Rectangle mis2 = new Rectangle(90, 80);
        Rectangle mis3 = new Rectangle(90, 80);
        
        //creation des rectangle congratulations & echec
        Rectangle congrats = new Rectangle(600, 200);
        Rectangle echec = new Rectangle(600, 200);
        
        // Configuration des images de fond pour chaque rectangle
        ciel.setFill(new ImagePattern(cielImage));
        premierRive.setFill(new ImagePattern(premierRiveImage));
        deuxiemeRive.setFill(new ImagePattern(deuxiemeRiveImage));
        rive.setFill(new ImagePattern(riveImage));
        boat.setFill(new ImagePattern(boatImage));
        can1.setFill(new ImagePattern(canImage));
        can2.setFill(new ImagePattern(canImage));
        can3.setFill(new ImagePattern(canImage));
        mis1.setFill(new ImagePattern(misImage));
        mis2.setFill(new ImagePattern(misImage));
        mis3.setFill(new ImagePattern(misImage));
        
        // Positionnement des rectangles
        premierRive.setLayoutX(0);
        premierRive.setLayoutY(300);
        deuxiemeRive.setLayoutX(670);
        deuxiemeRive.setLayoutY(300);
        rive.setLayoutX(310);
        rive.setLayoutY(125);
        boat.setLayoutX(303);//boat.setLayoutX(549)
        boat.setLayoutY(210);//boat.setLayoutY(210)
        can1.setLayoutX(40);can2.setLayoutX(80);can3.setLayoutX(120);
        can1.setLayoutY(240);can2.setLayoutY(240);can3.setLayoutY(240);
        mis1.setLayoutX(160);mis2.setLayoutX(200);mis3.setLayoutX(240);
        mis1.setLayoutY(234);mis2.setLayoutY(234);mis3.setLayoutY(234);
        
        //positionnement des rectangles de fin de jeu
        congrats.setLayoutX(200);
        congrats.setLayoutY(200);
        congrats.getStyleClass().add("congrats");
        congrats.setVisible(false);
        echec.setLayoutX(200);
        echec.setLayoutY(200);
        echec.getStyleClass().add("echec");
        echec.setVisible(false);

        
        // Création du bouton
        Button button = new Button("GO");
        button.setStyle("-fx-background-color: orange;" +
                "-fx-background-radius: 30;" +
                "-fx-text-fill: #333333;" +
                "-fx-font-size: 24px;" +
                "-fx-font-weight: bold;" +
                "-fx-padding: 10 20 10 20;" +
                "-fx-effect: dropshadow( three-pass-box, rgba(0,0,0,0.3), 10, 0, 0, 5);");
    
        button.setPrefSize(80, 80); // Taille du bouton
        button.setLayoutX(460); // Position X du bouton
        button.setLayoutY(40); // Position Y du bouton
        
        //creation bouton Quit
        Button buttonQuit = new Button("Quit");
        buttonQuit.setStyle("-fx-background-color: white;" +
                "-fx-background-radius: 30;" +
                "-fx-text-fill: black;" +
                "-fx-font-size: 20px;" +
                "-fx-font-weight: bold;" +
                "-fx-padding: 10 20 10 20;" +
                "-fx-effect: dropshadow( three-pass-box, rgba(0,0,0,0.3), 10, 0, 0, 5);");
    
        buttonQuit.setPrefSize(100, 30); // Taille du bouton
        buttonQuit.setLayoutX(350); // Position X du bouton
        buttonQuit.setLayoutY(330); // Position Y du bouton
        buttonQuit.setVisible(false);
        
      //creation bouton Replay
        Button buttonReplay = new Button("Replay");
        buttonReplay.setStyle("-fx-background-color: white;" +
                "-fx-background-radius: 30;" +
                "-fx-text-fill: black;" +
                "-fx-font-size: 20px;" +
                "-fx-font-weight: bold;" +
                "-fx-padding: 10 20 10 20;" +
                "-fx-effect: dropshadow( three-pass-box, rgba(0,0,0,0.3), 10, 0, 0, 5);");
    
        buttonReplay.setPrefSize(120, 30); // Taille du bouton
        buttonReplay.setLayoutX(530); // Position X du bouton
        buttonReplay.setLayoutY(330); // Position Y du bouton
        buttonReplay.setVisible(false);
        
        // Création du label
        Label label = new Label("Press on a character to jump");
        label.setStyle("-fx-font-size: 16px; -fx-text-fill: white; -fx-font-weight: bold;");
        label.setPrefSize(300, 80); 
        label.setLayoutX(400);
        label.setLayoutY(100);
        
        // Création du label Congrats
        Label labelCongrats = new Label("CONGRATS YOU SOLVED THE PROBLEM !");
        labelCongrats.setStyle("-fx-font-size: 24px; -fx-text-fill: black; -fx-font-weight: bold;");
        labelCongrats.setPrefSize(500, 150); 
        labelCongrats.setLayoutX(260);
        labelCongrats.setLayoutY(200);
        labelCongrats.setVisible(false);
        
        // Création du label Echec
        Label labelEchec = new Label("AH ! YOU LOST, TRY AGAIN !");
        labelEchec.setStyle("-fx-font-size: 24px; -fx-text-fill: black; -fx-font-weight: bold;");
        labelEchec.setPrefSize(500, 150); 
        labelEchec.setLayoutX(335);
        labelEchec.setLayoutY(200);
        labelEchec.setVisible(false);
        
     // Ajout de l'effet hover au bouton
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: yellow;" +
                "-fx-background-radius: 30;" +
                "-fx-text-fill: #333333;" +
                "-fx-font-size: 24px;" +
                "-fx-font-weight: bold;" +
                "-fx-padding: 10 20 10 20;" +
                "-fx-effect: dropshadow( three-pass-box, rgba(0,0,0,0.3), 10, 0, 0, 5);")); 
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: orange;" +
                "-fx-background-radius: 30;" +
                "-fx-text-fill: #333333;" +
                "-fx-font-size: 24px;" +
                "-fx-font-weight: bold;" +
                "-fx-padding: 10 20 10 20;" +
                "-fx-effect: dropshadow( three-pass-box, rgba(0,0,0,0.3), 10, 0, 0, 5);")); 
        
     // Ajout de l'effet hover au bouton Quit
        buttonQuit.setOnMouseEntered(e -> buttonQuit.setStyle("-fx-background-color: black;" +
                "-fx-background-radius: 30;" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 20px;" +
                "-fx-font-weight: bold;" +
                "-fx-padding: 10 20 10 20;" +
                "-fx-effect: dropshadow( three-pass-box, rgba(0,0,0,0.3), 10, 0, 0, 5);")); 
        buttonQuit.setOnMouseExited(e -> buttonQuit.setStyle("-fx-background-color: white;" +
                "-fx-background-radius: 30;" +
                "-fx-text-fill: black;" +
                "-fx-font-size: 20px;" +
                "-fx-font-weight: bold;" +
                "-fx-padding: 10 20 10 20;" +
                "-fx-effect: dropshadow( three-pass-box, rgba(0,0,0,0.3), 10, 0, 0, 5);")); 
        
     // Ajout de l'effet hover au bouton Replay
        buttonReplay.setOnMouseEntered(e -> buttonReplay.setStyle("-fx-background-color: black;" +
                "-fx-background-radius: 30;" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 20px;" +
                "-fx-font-weight: bold;" +
                "-fx-padding: 10 20 10 20;" +
                "-fx-effect: dropshadow( three-pass-box, rgba(0,0,0,0.3), 10, 0, 0, 5);")); 
        buttonReplay.setOnMouseExited(e -> buttonReplay.setStyle("-fx-background-color: white;" +
                "-fx-background-radius: 30;" +
                "-fx-text-fill: black;" +
                "-fx-font-size: 20px;" +
                "-fx-font-weight: bold;" +
                "-fx-padding: 10 20 10 20;" +
                "-fx-effect: dropshadow( three-pass-box, rgba(0,0,0,0.3), 10, 0, 0, 5);")); 
              
     // Create a translate transition for cannibal 1
        TranslateTransition can1transitionToR = new TranslateTransition(Duration.seconds(1.5), can1);
        TranslateTransition can1transitionToL = new TranslateTransition(Duration.seconds(1.5), can1);
        TranslateTransition can1transitionLToLRiv = new TranslateTransition(Duration.seconds(1.5), can1);
        TranslateTransition can1transitionRToLRiv = new TranslateTransition(Duration.seconds(1.5), can1);
        TranslateTransition can1transitionLToLBoat = new TranslateTransition(Duration.seconds(2.5), can1);
        TranslateTransition can1transitionRToRBoat = new TranslateTransition(Duration.seconds(2.5), can1);
        TranslateTransition can1transitionLToRRiv = new TranslateTransition(Duration.seconds(1.5), can1);
        TranslateTransition can1transitionRToRRiv = new TranslateTransition(Duration.seconds(1.5), can1);
        TranslateTransition can1transitionToRre = new TranslateTransition(Duration.seconds(1.5), can1);
        TranslateTransition can1transitionToLre = new TranslateTransition(Duration.seconds(1.5), can1);
        TranslateTransition can1transitionLToLBoatre = new TranslateTransition(Duration.seconds(2.5), can1);
        TranslateTransition can1transitionRToRBoatre = new TranslateTransition(Duration.seconds(2.5), can1);
        
     // Create a translate transition for cannibal 2
        TranslateTransition can2transitionToR = new TranslateTransition(Duration.seconds(1.5), can2);
        TranslateTransition can2transitionToL = new TranslateTransition(Duration.seconds(1.5), can2);
        TranslateTransition can2transitionLToLRiv = new TranslateTransition(Duration.seconds(1.5), can2);
        TranslateTransition can2transitionRToLRiv = new TranslateTransition(Duration.seconds(1.5), can2);
        TranslateTransition can2transitionLToLBoat = new TranslateTransition(Duration.seconds(2.5), can2);
        TranslateTransition can2transitionRToRBoat = new TranslateTransition(Duration.seconds(2.5), can2);
        TranslateTransition can2transitionLToRRiv = new TranslateTransition(Duration.seconds(1.5), can2);
        TranslateTransition can2transitionRToRRiv = new TranslateTransition(Duration.seconds(1.5), can2);
        TranslateTransition can2transitionToRre = new TranslateTransition(Duration.seconds(1.5), can2);
        TranslateTransition can2transitionToLre = new TranslateTransition(Duration.seconds(1.5), can2);
        TranslateTransition can2transitionLToLBoatre = new TranslateTransition(Duration.seconds(2.5), can2);
        TranslateTransition can2transitionRToRBoatre = new TranslateTransition(Duration.seconds(2.5), can2);
        
     // Create a translate transition for cannibal 3
        TranslateTransition can3transitionToR = new TranslateTransition(Duration.seconds(1.5), can3);
        TranslateTransition can3transitionToL = new TranslateTransition(Duration.seconds(1.5), can3);
        TranslateTransition can3transitionLToLRiv = new TranslateTransition(Duration.seconds(1.5), can3);
        TranslateTransition can3transitionRToLRiv = new TranslateTransition(Duration.seconds(1.5), can3);
        TranslateTransition can3transitionLToLBoat = new TranslateTransition(Duration.seconds(2.5), can3);
        TranslateTransition can3transitionRToRBoat = new TranslateTransition(Duration.seconds(2.5), can3);
        TranslateTransition can3transitionLToRRiv = new TranslateTransition(Duration.seconds(1.5), can3);
        TranslateTransition can3transitionRToRRiv = new TranslateTransition(Duration.seconds(1.5), can3);
        TranslateTransition can3transitionToRre = new TranslateTransition(Duration.seconds(1.5), can3);
        TranslateTransition can3transitionToLre = new TranslateTransition(Duration.seconds(1.5), can3);
        TranslateTransition can3transitionLToLBoatre = new TranslateTransition(Duration.seconds(2.5), can3);
        TranslateTransition can3transitionRToRBoatre = new TranslateTransition(Duration.seconds(2.5), can3);
        
     // Create a translate transition for missionnaire 1
        TranslateTransition mis1transitionToR = new TranslateTransition(Duration.seconds(1.5), mis1);
        TranslateTransition mis1transitionToL = new TranslateTransition(Duration.seconds(1.5), mis1);
        TranslateTransition mis1transitionLToLRiv = new TranslateTransition(Duration.seconds(1.5), mis1);
        TranslateTransition mis1transitionRToLRiv = new TranslateTransition(Duration.seconds(1.5), mis1);
        TranslateTransition mis1transitionLToLBoat = new TranslateTransition(Duration.seconds(2.5), mis1);
        TranslateTransition mis1transitionRToRBoat = new TranslateTransition(Duration.seconds(2.5), mis1);
        TranslateTransition mis1transitionLToRRiv = new TranslateTransition(Duration.seconds(1.5), mis1);
        TranslateTransition mis1transitionRToRRiv = new TranslateTransition(Duration.seconds(1.5), mis1);
        TranslateTransition mis1transitionToRre = new TranslateTransition(Duration.seconds(1.5), mis1);
        TranslateTransition mis1transitionToLre = new TranslateTransition(Duration.seconds(1.5), mis1);
        TranslateTransition mis1transitionLToLBoatre = new TranslateTransition(Duration.seconds(2.5), mis1);
        TranslateTransition mis1transitionRToRBoatre = new TranslateTransition(Duration.seconds(2.5), mis1);
        
     // Create a translate transition for missionnaire 2
        TranslateTransition mis2transitionToR = new TranslateTransition(Duration.seconds(1.5), mis2);
        TranslateTransition mis2transitionToL = new TranslateTransition(Duration.seconds(1.5), mis2);
        TranslateTransition mis2transitionLToLRiv = new TranslateTransition(Duration.seconds(1.5), mis2);
        TranslateTransition mis2transitionRToLRiv = new TranslateTransition(Duration.seconds(1.5), mis2);
        TranslateTransition mis2transitionLToLBoat = new TranslateTransition(Duration.seconds(2.5), mis2);
        TranslateTransition mis2transitionRToRBoat = new TranslateTransition(Duration.seconds(2.5), mis2);
        TranslateTransition mis2transitionLToRRiv = new TranslateTransition(Duration.seconds(1.5), mis2);
        TranslateTransition mis2transitionRToRRiv = new TranslateTransition(Duration.seconds(1.5), mis2);
        TranslateTransition mis2transitionToRre = new TranslateTransition(Duration.seconds(1.5), mis2);
        TranslateTransition mis2transitionToLre = new TranslateTransition(Duration.seconds(1.5), mis2);
        TranslateTransition mis2transitionLToLBoatre = new TranslateTransition(Duration.seconds(2.5), mis2);
        TranslateTransition mis2transitionRToRBoatre = new TranslateTransition(Duration.seconds(2.5), mis2);
        
     // Create a translate transition for missionnaire 3
        TranslateTransition mis3transitionToR = new TranslateTransition(Duration.seconds(1.5), mis3);
        TranslateTransition mis3transitionToL = new TranslateTransition(Duration.seconds(1.5), mis3);
        TranslateTransition mis3transitionLToLRiv = new TranslateTransition(Duration.seconds(1.5), mis3);
        TranslateTransition mis3transitionRToLRiv = new TranslateTransition(Duration.seconds(1.5), mis3);
        TranslateTransition mis3transitionLToLBoat = new TranslateTransition(Duration.seconds(2.5), mis3);
        TranslateTransition mis3transitionRToRBoat = new TranslateTransition(Duration.seconds(2.5), mis3);
        TranslateTransition mis3transitionLToRRiv = new TranslateTransition(Duration.seconds(1.5), mis3);
        TranslateTransition mis3transitionRToRRiv = new TranslateTransition(Duration.seconds(1.5), mis3);
        TranslateTransition mis3transitionToRre = new TranslateTransition(Duration.seconds(1.5), mis3);
        TranslateTransition mis3transitionToLre = new TranslateTransition(Duration.seconds(1.5), mis3);
        TranslateTransition mis3transitionLToLBoatre = new TranslateTransition(Duration.seconds(2.5), mis3);
        TranslateTransition mis3transitionRToRBoatre = new TranslateTransition(Duration.seconds(2.5), mis3);
        
     // Create a translate transition for boat depart and retour
        TranslateTransition boattransitionLToR = new TranslateTransition(Duration.seconds(2.5), boat);
        TranslateTransition boattransitionRToL = new TranslateTransition(Duration.seconds(2.5), boat);
        
       
        // Set up the transition for cannibal 1
        can1transitionToR.setToX(320); can1transitionToL.setToX(280); can1transitionLToLRiv.setToX(0);
        can1transitionToR.setToY(-17); can1transitionToL.setToY(-17); can1transitionLToLRiv.setToY(0);
        can1transitionRToLRiv.setToX(0); can1transitionLToLBoat.setToX(520); can1transitionRToRBoat.setToX(560);
        can1transitionRToLRiv.setToY(0); can1transitionLToLBoat.setToY(-17); can1transitionRToRBoat.setToY(-17);
        can1transitionLToRRiv.setToX(630); can1transitionRToRRiv.setToX(630);
        can1transitionLToRRiv.setToY(0); can1transitionRToRRiv.setToY(0);
        can1transitionToRre.setToX(560); can1transitionToLre.setToX(520);
        can1transitionToRre.setToY(-17); can1transitionToLre.setToY(-17);
        can1transitionLToLBoatre.setToX(280); can1transitionRToRBoatre.setToX(320);
        can1transitionLToLBoatre.setToY(-17); can1transitionRToRBoatre.setToY(-17);
        
        // Set up the transition for cannibal 2
        can2transitionToR.setToX(280); can2transitionToL.setToX(240); can2transitionLToLRiv.setToX(0);
        can2transitionToR.setToY(-17); can2transitionToL.setToY(-17); can2transitionLToLRiv.setToY(0);
        can2transitionRToLRiv.setToX(0); can2transitionLToLBoat.setToX(480); can2transitionRToRBoat.setToX(520);
        can2transitionRToLRiv.setToY(0); can2transitionLToLBoat.setToY(-17); can2transitionRToRBoat.setToY(-17);
        can2transitionLToRRiv.setToX(630); can2transitionRToRRiv.setToX(630);
        can2transitionLToRRiv.setToY(0); can2transitionRToRRiv.setToY(0);
        can2transitionToRre.setToX(520); can2transitionToLre.setToX(480);
        can2transitionToRre.setToY(-17); can2transitionToLre.setToY(-17);
        can2transitionLToLBoatre.setToX(240); can2transitionRToRBoatre.setToX(280);
        can2transitionLToLBoatre.setToY(-17); can2transitionRToRBoatre.setToY(-17);
        
        // Set up the transition for cannibal 3
        can3transitionToR.setToX(240); can3transitionToL.setToX(200); can3transitionLToLRiv.setToX(0);
        can3transitionToR.setToY(-17); can3transitionToL.setToY(-17); can3transitionLToLRiv.setToY(0);
        can3transitionRToLRiv.setToX(0); can3transitionLToLBoat.setToX(440); can3transitionRToRBoat.setToX(480);
        can3transitionRToLRiv.setToY(0); can3transitionLToLBoat.setToY(-17); can3transitionRToRBoat.setToY(-17);
        can3transitionLToRRiv.setToX(630); can3transitionRToRRiv.setToX(630);
        can3transitionLToRRiv.setToY(0); can3transitionRToRRiv.setToY(0);
        can3transitionToRre.setToX(480); can3transitionToLre.setToX(440);
        can3transitionToRre.setToY(-17); can3transitionToLre.setToY(-17);
        can3transitionLToLBoatre.setToX(200); can3transitionRToRBoatre.setToX(240);
        can3transitionLToLBoatre.setToY(-17); can3transitionRToRBoatre.setToY(-17);
        
        // Set up the transition for missionnaire 1
        mis1transitionToR.setToX(200); mis1transitionToL.setToX(160); mis1transitionLToLRiv.setToX(0);
        mis1transitionToR.setToY(-17); mis1transitionToL.setToY(-17); mis1transitionLToLRiv.setToY(0);
        mis1transitionRToLRiv.setToX(0); mis1transitionLToLBoat.setToX(400); mis1transitionRToRBoat.setToX(440);
        mis1transitionRToLRiv.setToY(0); mis1transitionLToLBoat.setToY(-17); mis1transitionRToRBoat.setToY(-17);
        mis1transitionLToRRiv.setToX(630); mis1transitionRToRRiv.setToX(630);
        mis1transitionLToRRiv.setToY(0); mis1transitionRToRRiv.setToY(0);
        mis1transitionToRre.setToX(440); mis1transitionToLre.setToX(400);
        mis1transitionToRre.setToY(-17); mis1transitionToLre.setToY(-17);
        mis1transitionLToLBoatre.setToX(160); mis1transitionRToRBoatre.setToX(200);
        mis1transitionLToLBoatre.setToY(-17); mis1transitionRToRBoatre.setToY(-17);
        
        // Set up the transition for missionnaire 2
        mis2transitionToR.setToX(160); mis2transitionToL.setToX(120); mis2transitionLToLRiv.setToX(0);
        mis2transitionToR.setToY(-17); mis2transitionToL.setToY(-17); mis2transitionLToLRiv.setToY(0);
        mis2transitionRToLRiv.setToX(0);mis2transitionLToLBoat.setToX(360); mis2transitionRToRBoat.setToX(400);
        mis2transitionRToLRiv.setToY(0); mis2transitionLToLBoat.setToY(-17); mis2transitionRToRBoat.setToY(-17);
        mis2transitionLToRRiv.setToX(630); mis2transitionRToRRiv.setToX(630);
        mis2transitionLToRRiv.setToY(0); mis2transitionRToRRiv.setToY(0);
        mis2transitionToRre.setToX(400); mis2transitionToLre.setToX(360);
        mis2transitionToRre.setToY(-17); mis2transitionToLre.setToY(-17);
        mis2transitionLToLBoatre.setToX(120); mis2transitionRToRBoatre.setToX(160);
        mis2transitionLToLBoatre.setToY(-17); mis2transitionRToRBoatre.setToY(-17);
        
        
        // Set up the transition for missionnaire 3
        mis3transitionToR.setToX(120); mis3transitionToL.setToX(80); mis3transitionLToLRiv.setToX(0);
        mis3transitionToR.setToY(-17); mis3transitionToL.setToY(-17); mis3transitionLToLRiv.setToY(0);
        mis3transitionRToLRiv.setToX(0); mis3transitionLToLBoat.setToX(320); mis3transitionRToRBoat.setToX(360);
        mis3transitionRToLRiv.setToY(0); mis3transitionLToLBoat.setToY(-17); mis3transitionRToRBoat.setToY(-17);
        mis3transitionLToRRiv.setToX(630); mis3transitionRToRRiv.setToX(630);
        mis3transitionLToRRiv.setToY(0); mis3transitionRToRRiv.setToY(0);
        mis3transitionToRre.setToX(360); mis3transitionToLre.setToX(320);
        mis3transitionToRre.setToY(-17); mis3transitionToLre.setToY(-17);
        mis3transitionLToLBoatre.setToX(80); mis3transitionRToRBoatre.setToX(120);
        mis3transitionLToLBoatre.setToY(-17); mis3transitionRToRBoatre.setToY(-17);
        
        // Set up the transition for boat depart 2 cannibales
        boattransitionLToR.setToX(245); boattransitionRToL.setToX(0); 
        boattransitionLToR.setToY(0); boattransitionRToL.setToY(0);  
      
        //Ajout des effets click a Quit et Replay buttons
        buttonQuit.setOnMouseClicked(event -> {
        	System.exit(0);
        });
     
        buttonReplay.setOnMouseClicked(event -> {
        	currentState = new Etat_can_miss(3, 3, true);
            operation = null;
            leftSpot = true; rightSpot = true;
            can1Spot=1; can2Spot=1; can3Spot=1; mis1Spot=1; mis2Spot=1; mis3Spot=1;
            steps=0;
            solved =false;
            boatDist[0]=0; boatDist[1]=0; boatDist[2]=0;
            
            can1transitionLToLRiv.play();can2transitionLToLRiv.play();can3transitionLToLRiv.play();
            mis1transitionLToLRiv.play();mis2transitionLToLRiv.play();mis3transitionLToLRiv.play();
            boattransitionRToL.play();

            can1Spot=1; can2Spot=1; can3Spot=1;
            mis1Spot=1; mis2Spot=1; mis3Spot=1;
            
            // Activer les composants nécessaires ici
            can1.setDisable(false);can2.setDisable(false);can3.setDisable(false);
            mis1.setDisable(false); mis2.setDisable(false);mis3.setDisable(false);
            button.setDisable(false); buttonQuit.setVisible(false); buttonReplay.setVisible(false);
            congrats.setVisible(false); echec.setVisible(false); 
            labelCongrats.setVisible(false);labelEchec.setVisible(false); label.setVisible(true);
        });
        
     // Ajout de l'effet click aux personnages 
        can1.setOnMouseClicked(event -> {
        	
        	label.setVisible(false);
        	playJumpSound();
        	if(can1Spot == 1) {
        		if(boatDist[2]==0) {
        			if(leftSpot) {
            			can1transitionToL.play();
            			can1Spot = 2;
            			boatDist[0] ++;
            			leftSpot = false;
            		} else if(rightSpot) {
            			can1transitionToR.play();
            			can1Spot = 3;
            			boatDist[0] ++;
            			rightSpot = false;
            		}
        		} 		
        	}else if(can1Spot == 2){
        		can1transitionLToLRiv.play();
        		can1Spot = 1;
        		boatDist[0] --;
        		leftSpot = true;
        	} else if(can1Spot == 3) {
        		can1transitionRToLRiv.play();
        		can1Spot = 1;
        		boatDist[0] --;
        		rightSpot = true;
        	} else if(can1Spot == 4) {
        		can1transitionLToRRiv.play();
        		can1Spot = 6;
        		boatDist[0] --;
        		leftSpot = true;
        		if(solved &&  rightSpot) {
        			playWinSound();
        			congrats.setVisible(true);labelCongrats.setVisible(true);
        			buttonQuit.setVisible(true);
        			buttonReplay.setVisible(true);
        			can1.setDisable(true);can2.setDisable(true);can3.setDisable(true);
        			mis1.setDisable(true);mis2.setDisable(true);mis3.setDisable(true);
        			button.setDisable(true);
        			System.out.println("Congratulations! You've solved the problem!");
        		}
        	} else if(can1Spot == 5 ) {
        		can1transitionRToRRiv.play();
        		can1Spot = 6;
        		boatDist[0] --;
        		rightSpot = true;
        		if(solved && leftSpot) {
        			playWinSound();
        			congrats.setVisible(true);labelCongrats.setVisible(true);
        			buttonQuit.setVisible(true);
        			buttonReplay.setVisible(true);
        			can1.setDisable(true);can2.setDisable(true);can3.setDisable(true);
        			mis1.setDisable(true);mis2.setDisable(true);mis3.setDisable(true);
        			button.setDisable(true);
        			System.out.println("Congratulations! You've solved the problem!");
        		}
        	} else if(can1Spot == 6) {
        		if(boatDist[2]==1) {
        			if(leftSpot) {
            			can1transitionToLre.play();
            			can1Spot = 4;
            			boatDist[0] ++;
            			leftSpot = false;
            		} else if(rightSpot) {
            			can1transitionToRre.play();
            			can1Spot = 5;
            			boatDist[0] ++;
            			rightSpot = false;
            		}
        		}	
        	}
        });
        
        can2.setOnMouseClicked(event -> {
        	label.setVisible(false);
        	playJumpSound();
        	if(can2Spot == 1) {
        		if(boatDist[2]==0) {
        			if(leftSpot) {
            			can2transitionToL.play();
            			can2Spot = 2;
            			boatDist[0] ++;
            			leftSpot = false;
            		} else if(rightSpot){
            			can2transitionToR.play();
            			can2Spot = 3;
            			boatDist[0] ++;
            			rightSpot = false;
            		}
        		}       			
        	}else if(can2Spot == 2){
        		can2transitionLToLRiv.play();
        		can2Spot = 1;
        		boatDist[0] --;
        		leftSpot = true;
        	} else if(can2Spot == 3) {
        		can2transitionRToLRiv.play();
        		can2Spot = 1;
        		boatDist[0] --;
        		rightSpot = true;
        	} else if(can2Spot == 4) {
        		can2transitionLToRRiv.play();
        		can2Spot = 6;
        		boatDist[0] --;
        		leftSpot = true;
        		if(solved &&  rightSpot) {
        			playWinSound();
        			congrats.setVisible(true);labelCongrats.setVisible(true);
        			buttonQuit.setVisible(true);
        			buttonReplay.setVisible(true);
        			can1.setDisable(true);can2.setDisable(true);can3.setDisable(true);
        			mis1.setDisable(true);mis2.setDisable(true);mis3.setDisable(true);
        			button.setDisable(true);
        			System.out.println("Congratulations! You've solved the problem!");
        		}
        	} else if(can2Spot == 5 ) {
        		can2transitionRToRRiv.play();
        		can2Spot = 6;
        		boatDist[0] --;
        		rightSpot = true;
        		if(solved && leftSpot) {
        			playWinSound();
        			congrats.setVisible(true);labelCongrats.setVisible(true);
        			buttonQuit.setVisible(true);
        			buttonReplay.setVisible(true);
        			can1.setDisable(true);can2.setDisable(true);can3.setDisable(true);
        			mis1.setDisable(true);mis2.setDisable(true);mis3.setDisable(true);
        			button.setDisable(true);
        			System.out.println("Congratulations! You've solved the problem!");
        		}
        	} else if(can2Spot == 6) {
        		if(boatDist[2]==1) {
        			if(leftSpot) {
            			can2transitionToLre.play();
            			can2Spot = 4;
            			boatDist[0] ++;
            			leftSpot = false;
            		} else if(rightSpot) {
            			can2transitionToRre.play();
            			can2Spot = 5;
            			boatDist[0] ++;
            			rightSpot = false;
            		}
        		}       		
        	}
        });
        
        can3.setOnMouseClicked(event -> {
        	label.setVisible(false);
        	playJumpSound();
        	if(can3Spot == 1) {
        		if(boatDist[2]==0) {
        			if(leftSpot) {
            			can3transitionToL.play();
            			can3Spot = 2;
            			boatDist[0] ++;
            			leftSpot = false;
            		} else if(rightSpot){
            			can3transitionToR.play();
            			can3Spot = 3;
            			boatDist[0] ++;
            			rightSpot = false;
            		}
        		}       			
        	}else if(can3Spot == 2){
        		can3transitionLToLRiv.play();
        		can3Spot = 1;
        		boatDist[0] --;
        		leftSpot = true;
        	} else if(can3Spot == 3) {
        		can3transitionRToLRiv.play();
        		boatDist[0] --;
        		can3Spot = 1;
        		rightSpot = true;
        	} else if(can3Spot == 4) {
        		can3transitionLToRRiv.play();
        		can3Spot = 6;
        		boatDist[0] --;
        		leftSpot = true;
        		if(solved &&  rightSpot) {
        			playWinSound();
        			congrats.setVisible(true);labelCongrats.setVisible(true);
        			buttonQuit.setVisible(true);
        			buttonReplay.setVisible(true);
        			can1.setDisable(true);can2.setDisable(true);can3.setDisable(true);
        			mis1.setDisable(true);mis2.setDisable(true);mis3.setDisable(true);
        			button.setDisable(true);
        			System.out.println("Congratulations! You've solved the problem!");
        		}
        	} else if(can3Spot == 5 ) {
        		can3transitionRToRRiv.play();
        		can3Spot = 6;
        		boatDist[0] --;
        		rightSpot = true;
        		if(solved &&  leftSpot) {
        			playWinSound();
        			congrats.setVisible(true);labelCongrats.setVisible(true);
        			buttonQuit.setVisible(true);
        			buttonReplay.setVisible(true);
        			can1.setDisable(true);can2.setDisable(true);can3.setDisable(true);
        			mis1.setDisable(true);mis2.setDisable(true);mis3.setDisable(true);
        			button.setDisable(true);
        			System.out.println("Congratulations! You've solved the problem!");
        		}
        	} else if(can3Spot == 6) {
        		if(boatDist[2]==1) {
        			if(leftSpot) {
            			can3transitionToLre.play();
            			can3Spot = 4;
            			boatDist[0] ++;
            			leftSpot = false;
            		} else if(rightSpot) {
            			can3transitionToRre.play();
            			can3Spot = 5;
            			boatDist[0] ++;
            			rightSpot = false;
            		}
        		}       		
        	}
        });
        
        mis1.setOnMouseClicked(event -> {
        	label.setVisible(false);
        	playJumpSound();
        	if(mis1Spot == 1) {
        		if(boatDist[2]==0) {
        			if(leftSpot) {
            			mis1transitionToL.play();
            			mis1Spot = 2;
            			boatDist[1] ++;
            			leftSpot = false;
            		} else if(rightSpot){
            			mis1transitionToR.play();
            			mis1Spot = 3;
            			boatDist[1] ++;
            			rightSpot = false;
            		}
        		}      			
        	}else if(mis1Spot == 2){
        		mis1transitionLToLRiv.play();
        		mis1Spot = 1;
        		boatDist[1] --;
        		leftSpot = true;
        	} else if(mis1Spot == 3) {
        		mis1transitionRToLRiv.play();
        		mis1Spot = 1;
        		boatDist[1] --;
        		rightSpot = true;
        	} else if(mis1Spot == 4) {
        		mis1transitionLToRRiv.play();
        		mis1Spot = 6;
        		boatDist[1] --;
        		leftSpot = true;
        		if(solved &&  rightSpot) {
        			playWinSound();
        			congrats.setVisible(true);labelCongrats.setVisible(true);
        			buttonQuit.setVisible(true);
        			buttonReplay.setVisible(true);
        			can1.setDisable(true);can2.setDisable(true);can3.setDisable(true);
        			mis1.setDisable(true);mis2.setDisable(true);mis3.setDisable(true);
        			button.setDisable(true);
        			System.out.println("Congratulations! You've solved the problem!");
        		}
        	} else if(mis1Spot == 5 ) {
        		mis1transitionRToRRiv.play();
        		mis1Spot = 6;
        		boatDist[1] --;
        		rightSpot = true;
        		if(solved &&  leftSpot) {
        			playWinSound();
        			congrats.setVisible(true);labelCongrats.setVisible(true);
        			buttonQuit.setVisible(true);
        			buttonReplay.setVisible(true);
        			can1.setDisable(true);can2.setDisable(true);can3.setDisable(true);
        			mis1.setDisable(true);mis2.setDisable(true);mis3.setDisable(true);
        			button.setDisable(true);
        			System.out.println("Congratulations! You've solved the problem!");
        		}
        	} else if(mis1Spot == 6) {
        		if(boatDist[2]==1) {
        			if(leftSpot) {
            			mis1transitionToLre.play();
            			mis1Spot = 4;
            			boatDist[1] ++;
            			leftSpot = false;
            		} else if(rightSpot) {
            			mis1transitionToRre.play();
            			mis1Spot = 5;
            			boatDist[1] ++;
            			rightSpot = false;
            		}
        		}       		
        	}
        });
        
        mis2.setOnMouseClicked(event -> {
        	label.setVisible(false);
        	playJumpSound();
        	if(mis2Spot == 1) {
        		if(boatDist[2]==0) {
        			if(leftSpot) {
            			mis2transitionToL.play();
            			mis2Spot = 2;
            			boatDist[1] ++;
            			leftSpot = false;
            		} else if(rightSpot){
            			mis2transitionToR.play();
            			mis2Spot = 3;
            			boatDist[1] ++;
            			rightSpot = false;
            		}
        		}        			
        	}else if(mis2Spot == 2){
        		mis2transitionLToLRiv.play();
        		mis2Spot = 1;
        		boatDist[1] --;
        		leftSpot = true;
        	} else if(mis2Spot == 3) {
        		mis2transitionRToLRiv.play();
        		mis2Spot = 1;
        		boatDist[1] --;
        		rightSpot = true;
        	} else if(mis2Spot == 4) {
        		mis2transitionLToRRiv.play();
        		mis2Spot = 6;
        		boatDist[1] --;
        		leftSpot = true;
        		if(solved &&  rightSpot) {
        			playWinSound();
        			congrats.setVisible(true);labelCongrats.setVisible(true);
        			buttonQuit.setVisible(true);
        			buttonReplay.setVisible(true);
        			can1.setDisable(true);can2.setDisable(true);can3.setDisable(true);
        			mis1.setDisable(true);mis2.setDisable(true);mis3.setDisable(true);
        			button.setDisable(true);
        			System.out.println("Congratulations! You've solved the problem!");
        		}
        	} else if(mis2Spot == 5 ) {
        		mis2transitionRToRRiv.play();
        		mis2Spot = 6;
        		boatDist[1] --;
        		rightSpot = true;
        		if(solved &&  leftSpot) {
        			playWinSound();
        			congrats.setVisible(true);labelCongrats.setVisible(true);
        			buttonQuit.setVisible(true);
        			buttonReplay.setVisible(true);
        			can1.setDisable(true);can2.setDisable(true);can3.setDisable(true);
        			mis1.setDisable(true);mis2.setDisable(true);mis3.setDisable(true);
        			button.setDisable(true);
        			System.out.println("Congratulations! You've solved the problem!");
        		}
        	} else if(mis2Spot == 6) {
        		if(boatDist[2]==1) {
        			if(leftSpot) {
            			mis2transitionToLre.play();
            			mis2Spot = 4;
            			boatDist[1] ++;
            			leftSpot = false;
            		} else if(rightSpot) {
            			mis2transitionToRre.play();
            			mis2Spot = 5;
            			boatDist[1] ++;
            			rightSpot = false;
            		}
        		}        		
        	}
        });
        
        mis3.setOnMouseClicked(event -> {
        	label.setVisible(false);
        	playJumpSound();
        	if(mis3Spot == 1) {
        		if(boatDist[2]==0) {
        			if(leftSpot) {
            			mis3transitionToL.play();
            			mis3Spot = 2;
            			boatDist[1] ++;
            			leftSpot = false;
            		} else if(rightSpot){
            			mis3transitionToR.play();
            			mis3Spot = 3;
            			boatDist[1] ++;
            			rightSpot = false;
            		}
        		}   			
        	}else if(mis3Spot == 2){
        		mis3transitionLToLRiv.play();
        		mis3Spot = 1;
        		boatDist[1] --;
        		leftSpot = true;
        	} else if(mis3Spot == 3) {
        		mis3transitionRToLRiv.play();
        		mis3Spot = 1;
        		boatDist[1] --;
        		rightSpot = true;
        	} else if(mis3Spot == 4) {
        		mis3transitionLToRRiv.play();
        		mis3Spot = 6;
        		boatDist[1] --;
        		leftSpot = true;
        		if(solved &&  rightSpot) {
        			playWinSound();
        			congrats.setVisible(true);labelCongrats.setVisible(true);
        			buttonQuit.setVisible(true);
        			buttonReplay.setVisible(true);
        			can1.setDisable(true);can2.setDisable(true);can3.setDisable(true);
        			mis1.setDisable(true);mis2.setDisable(true);mis3.setDisable(true);
        			button.setDisable(true);
        			System.out.println("Congratulations! You've solved the problem!");
        		}
        	} else if(mis3Spot == 5 ) {
        		mis3transitionRToRRiv.play();
        		mis3Spot = 6;
        		boatDist[1] --;
        		rightSpot = true;
        		if(solved &&  leftSpot) {
        			playWinSound();
        			congrats.setVisible(true);labelCongrats.setVisible(true);
        			buttonQuit.setVisible(true);
        			buttonReplay.setVisible(true);
        			can1.setDisable(true);can2.setDisable(true);can3.setDisable(true);
        			mis1.setDisable(true);mis2.setDisable(true);mis3.setDisable(true);
        			button.setDisable(true);
        			System.out.println("Congratulations! You've solved the problem!");
        		}
        	} else if(mis3Spot == 6) {
        		if(boatDist[2]==1) {
        			if(leftSpot) {
            			mis3transitionToLre.play();
            			mis3Spot = 4;
            			boatDist[1] ++;
            			leftSpot = false;
            		} else if(rightSpot) {
            			mis3transitionToRre.play();
            			mis3Spot = 5;
            			boatDist[1] ++;
            			rightSpot = false;
            		}
        		}      		
        	}
        });
        
        button.setOnMouseClicked(event -> {
        	Etat nextState;
        	//***********************   Aller   *******************
        	if(!leftSpot && !rightSpot && boatDist[2]==0) {
        		playGoSound();
        		if(boatDist[0]==2) {
        			operation = new Depart_deux_can();
        			nextState = operation.action(currentState);
                    if (nextState != null) {
                    	steps++;
                        currentState = nextState;
                        boatDist[2]=1;
                        playWaterSound();
                        boattransitionLToR.play();
                        //test can1
                        if(can1Spot == 2) {
                        	can1transitionLToLBoat.play();
                        	can1Spot = 4;
                        } else if(can1Spot == 3) {
                        	can1transitionRToRBoat.play();
                        	can1Spot = 5;
                        }
                        //test can2
                        if(can2Spot == 2) {
                        	can2transitionLToLBoat.play();
                        	can2Spot = 4;
                        } else if(can2Spot == 3) {
                        	can2transitionRToRBoat.play();
                        	can2Spot = 5;
                        }
                      //test can3
                        if(can3Spot == 2) {
                        	can3transitionLToLBoat.play();
                        	can3Spot = 4;
                        } else if(can3Spot == 3) {
                        	can3transitionRToRBoat.play();
                        	can3Spot = 5;
                        }
                        System.out.println("valid operation! operation done");
                    } else {
                    	playLoseSound();
                    	echec.setVisible(true);labelEchec.setVisible(true);
            			buttonQuit.setVisible(true);
            			buttonReplay.setVisible(true);
            			can1.setDisable(true);can2.setDisable(true);can3.setDisable(true);
            			mis1.setDisable(true);mis2.setDisable(true);mis3.setDisable(true);
            			button.setDisable(true);
                        System.out.println("Invalid operation! Cannibals ate the missionaries. You lost!");
                    }
        		} else if(boatDist[1]==2) {
        			operation = new Depart_deux_miss();
        			nextState = operation.action(currentState);
                    if (nextState != null) {
                    	steps++;
                        currentState = nextState;
                        boatDist[2]=1;
                        playWaterSound();
                        boattransitionLToR.play();
                      //test mis1
                        if(mis1Spot == 2) {
                        	mis1transitionLToLBoat.play();
                        	mis1Spot = 4;
                        } else if(mis1Spot == 3) {
                        	mis1transitionRToRBoat.play();
                        	mis1Spot = 5;
                        }
                        //test mis2
                        if(mis2Spot == 2) {
                        	mis2transitionLToLBoat.play();
                        	mis2Spot = 4;
                        } else if(mis2Spot == 3) {
                        	mis2transitionRToRBoat.play();
                        	mis2Spot = 5;
                        }
                      //test mis3
                        if(mis3Spot == 2) {
                        	mis3transitionLToLBoat.play();
                        	mis3Spot = 4;
                        } else if(mis3Spot == 3) {
                        	mis3transitionRToRBoat.play();
                        	mis3Spot = 5;
                        }
                        System.out.println("valid operation! operation done");
                    } else {
                    	playLoseSound();
                    	echec.setVisible(true);labelEchec.setVisible(true);
            			buttonQuit.setVisible(true);
            			buttonReplay.setVisible(true);
            			can1.setDisable(true);can2.setDisable(true);can3.setDisable(true);
            			mis1.setDisable(true);mis2.setDisable(true);mis3.setDisable(true);
            			button.setDisable(true);
                        System.out.println("Invalid operation! Cannibals ate the missionaries. You lost!");
                    }
        		} else {
        			operation = new Depart_un_can_un_miss();
        			nextState = operation.action(currentState);
                    if (nextState != null) {
                    	steps++;
                        currentState = nextState;
                        boatDist[2]=1;
                        playWaterSound();
                        boattransitionLToR.play();
                      //test can1
                        if(can1Spot == 2) {
                        	can1transitionLToLBoat.play();
                        	can1Spot = 4;
                        } else if(can1Spot == 3) {
                        	can1transitionRToRBoat.play();
                        	can1Spot = 5;
                        }
                        //test can2
                        if(can2Spot == 2) {
                        	can2transitionLToLBoat.play();
                        	can2Spot = 4;
                        } else if(can2Spot == 3) {
                        	can2transitionRToRBoat.play();
                        	can2Spot = 5;
                        }
                      //test can3
                        if(can3Spot == 2) {
                        	can3transitionLToLBoat.play();
                        	can3Spot = 4;
                        } else if(can3Spot == 3) {
                        	can3transitionRToRBoat.play();
                        	can3Spot = 5;
                        }
                      //test mis1
                        if(mis1Spot == 2) {
                        	mis1transitionLToLBoat.play();
                        	mis1Spot = 4;
                        } else if(mis1Spot == 3) {
                        	mis1transitionRToRBoat.play();
                        	mis1Spot = 5;
                        }
                        //test mis2
                        if(mis2Spot == 2) {
                        	mis2transitionLToLBoat.play();
                        	mis2Spot = 4;
                        } else if(mis2Spot == 3) {
                        	mis2transitionRToRBoat.play();
                        	mis2Spot = 5;
                        }
                      //test mis3
                        if(mis3Spot == 2) {
                        	mis3transitionLToLBoat.play();
                        	mis3Spot = 4;
                        } else if(mis3Spot == 3) {
                        	mis3transitionRToRBoat.play();
                        	mis3Spot = 5;
                        }
                        System.out.println("valid operation! operation done");
                    } else {
                    	playLoseSound();
                    	echec.setVisible(true);labelEchec.setVisible(true);
            			buttonQuit.setVisible(true);
            			buttonReplay.setVisible(true);
            			can1.setDisable(true);can2.setDisable(true);can3.setDisable(true);
            			mis1.setDisable(true);mis2.setDisable(true);mis3.setDisable(true);
            			button.setDisable(true);
                        System.out.println("Invalid operation! Cannibals ate the missionaries. You lost!");
                    }
        		}
        	}else if((!leftSpot || !rightSpot) && boatDist[2]==0){
        		playGoSound();
        		if(boatDist[0]==1) {
        			operation = new Depart_un_can();
        			nextState = operation.action(currentState);
                    if (nextState != null) {
                    	steps++;
                        currentState = nextState;
                        boatDist[2]=1;
                        playWaterSound();
                        boattransitionLToR.play();
                      //test can1
                        if(can1Spot == 2) {
                        	can1transitionLToLBoat.play();
                        	can1Spot = 4;
                        } else if(can1Spot == 3) {
                        	can1transitionRToRBoat.play();
                        	can1Spot = 5;
                        }
                        //test can2
                        if(can2Spot == 2) {
                        	can2transitionLToLBoat.play();
                        	can2Spot = 4;
                        } else if(can2Spot == 3) {
                        	can2transitionRToRBoat.play();
                        	can2Spot = 5;
                        }
                      //test can3
                        if(can3Spot == 2) {
                        	can3transitionLToLBoat.play();
                        	can3Spot = 4;
                        } else if(can3Spot == 3) {
                        	can3transitionRToRBoat.play();
                        	can3Spot = 5;
                        }
                        System.out.println("valid operation! operation done");
                    } else {
                    	playLoseSound();
                    	echec.setVisible(true);labelEchec.setVisible(true);
            			buttonQuit.setVisible(true);
            			buttonReplay.setVisible(true);
            			can1.setDisable(true);can2.setDisable(true);can3.setDisable(true);
            			mis1.setDisable(true);mis2.setDisable(true);mis3.setDisable(true);
            			button.setDisable(true);
                        System.out.println("Invalid operation! Cannibals ate the missionaries. You lost!");
                    }
        		} else {
        			operation = new Depart_un_miss();
        			nextState = operation.action(currentState);
                    if (nextState != null) {
                    	steps++;
                        currentState = nextState;
                        boatDist[2]=1;
                        playWaterSound();
                        boattransitionLToR.play();
                      //test mis1
                        if(mis1Spot == 2) {
                        	mis1transitionLToLBoat.play();
                        	mis1Spot = 4;
                        } else if(mis1Spot == 3) {
                        	mis1transitionRToRBoat.play();
                        	mis1Spot = 5;
                        }
                        //test mis2
                        if(mis2Spot == 2) {
                        	mis2transitionLToLBoat.play();
                        	mis2Spot = 4;
                        } else if(mis2Spot == 3) {
                        	mis2transitionRToRBoat.play();
                        	mis2Spot = 5;
                        }
                      //test mis3
                        if(mis3Spot == 2) {
                        	mis3transitionLToLBoat.play();
                        	mis3Spot = 4;
                        } else if(mis3Spot == 3) {
                        	mis3transitionRToRBoat.play();
                        	mis3Spot = 5;
                        }
                        System.out.println("valid operation! operation done");
                    } else {
                    	playLoseSound();
                    	echec.setVisible(true);labelEchec.setVisible(true);
            			buttonQuit.setVisible(true);
            			buttonReplay.setVisible(true);
            			can1.setDisable(true);can2.setDisable(true);can3.setDisable(true);
            			mis1.setDisable(true);mis2.setDisable(true);mis3.setDisable(true);
            			button.setDisable(true);
                        System.out.println("Invalid operation! Cannibals ate the missionaries. You lost!");
                    }
        		}
        		// **********************   Retour   ********************** 
        	} else if(!leftSpot && !rightSpot && boatDist[2]==1) {
        		playGoSound();
        		if(boatDist[0]==2) {
        			operation = new Retour_deux_can();
        			nextState = operation.action(currentState);
                    if (nextState != null) {
                    	steps++;
                        currentState = nextState;
                        boatDist[2]=0;
                        playWaterSound();
                        boattransitionRToL.play();
                      //test can1
                        if(can1Spot == 4) {
                        	can1transitionLToLBoatre.play();
                        	can1Spot = 2;
                        } else if(can1Spot == 5) {
                        	can1transitionRToRBoatre.play();
                        	can1Spot = 3;
                        }
                        //test can2
                        if(can2Spot == 4) {
                        	can2transitionLToLBoatre.play();
                        	can2Spot = 2;
                        } else if(can2Spot == 5) {
                        	can2transitionRToRBoatre.play();
                        	can2Spot = 3;
                        }
                      //test can3
                        if(can3Spot == 4) {
                        	can3transitionLToLBoatre.play();
                        	can3Spot = 2;
                        } else if(can3Spot == 5) {
                        	can3transitionRToRBoatre.play();
                        	can3Spot = 3;
                        }
                        System.out.println("valid operation! operation done");
                    } else {
                    	playLoseSound();
                    	echec.setVisible(true);labelEchec.setVisible(true);
            			buttonQuit.setVisible(true);
            			buttonReplay.setVisible(true);
            			can1.setDisable(true);can2.setDisable(true);can3.setDisable(true);
            			mis1.setDisable(true);mis2.setDisable(true);mis3.setDisable(true);
            			button.setDisable(true);
                        System.out.println("Invalid operation! Cannibals ate the missionaries. You lost!");
                    }
        		} else if(boatDist[1]==2) {
        			operation = new Retour_deux_miss();
        			nextState = operation.action(currentState);
                    if (nextState != null) {
                    	steps++;
                        currentState = nextState;
                        boatDist[2]=0;
                        playWaterSound();
                        boattransitionRToL.play();
                      //test mis1
                        if(mis1Spot == 4) {
                        	mis1transitionLToLBoatre.play();
                        	mis1Spot = 2;
                        } else if(mis1Spot == 5) {
                        	mis1transitionRToRBoatre.play();
                        	mis1Spot = 3;
                        }
                        //test mis2
                        if(mis2Spot == 4) {
                        	mis2transitionLToLBoatre.play();
                        	mis2Spot = 2;
                        } else if(mis2Spot == 5) {
                        	mis2transitionRToRBoatre.play();
                        	mis2Spot = 3;
                        }
                      //test mis3
                        if(mis3Spot == 4) {
                        	mis3transitionLToLBoatre.play();
                        	mis3Spot = 2;
                        } else if(mis3Spot == 5) {
                        	mis3transitionRToRBoatre.play();
                        	mis3Spot = 3;
                        }
                        System.out.println("valid operation! operation done");
                    } else {
                    	playLoseSound();
                    	echec.setVisible(true);labelEchec.setVisible(true);
            			buttonQuit.setVisible(true);
            			buttonReplay.setVisible(true);
            			can1.setDisable(true);can2.setDisable(true);can3.setDisable(true);
            			mis1.setDisable(true);mis2.setDisable(true);mis3.setDisable(true);
            			button.setDisable(true);
                        System.out.println("Invalid operation! Cannibals ate the missionaries. You lost!");
                    }
        		} else {
        			operation = new Retour_un_can_un_miss();
        			nextState = operation.action(currentState);
                    if (nextState != null) {
                    	steps++;
                        currentState = nextState;
                        boatDist[2]=0;
                        playWaterSound();
                        boattransitionRToL.play();
                      //test can1
                        if(can1Spot == 4) {
                        	can1transitionLToLBoatre.play();
                        	can1Spot = 2;
                        } else if(can1Spot == 5) {
                        	can1transitionRToRBoatre.play();
                        	can1Spot = 3;
                        }
                        //test can2
                        if(can2Spot == 4) {
                        	can2transitionLToLBoatre.play();
                        	can2Spot = 2;
                        } else if(can2Spot == 5) {
                        	can2transitionRToRBoatre.play();
                        	can2Spot = 3;
                        }
                      //test can3
                        if(can3Spot == 4) {
                        	can3transitionLToLBoatre.play();
                        	can3Spot = 2;
                        } else if(can3Spot == 5) {
                        	can3transitionRToRBoatre.play();
                        	can3Spot = 3;
                        }
                      //test mis1
                        if(mis1Spot == 4) {
                        	mis1transitionLToLBoatre.play();
                        	mis1Spot = 2;
                        } else if(mis1Spot == 5) {
                        	mis1transitionRToRBoatre.play();
                        	mis1Spot = 3;
                        }
                        //test mis2
                        if(mis2Spot == 4) {
                        	mis2transitionLToLBoatre.play();
                        	mis2Spot = 2;
                        } else if(mis2Spot == 5) {
                        	mis2transitionRToRBoatre.play();
                        	mis2Spot = 3;
                        }
                      //test mis3
                        if(mis3Spot == 4) {
                        	mis3transitionLToLBoatre.play();
                        	mis3Spot = 2;
                        } else if(mis3Spot == 5) {
                        	mis3transitionRToRBoatre.play();
                        	mis3Spot = 3;
                        }
                        System.out.println("valid operation! operation done");
                    } else {
                    	playLoseSound();
                    	echec.setVisible(true);labelEchec.setVisible(true);
            			buttonQuit.setVisible(true);
            			buttonReplay.setVisible(true);
            			can1.setDisable(true);can2.setDisable(true);can3.setDisable(true);
            			mis1.setDisable(true);mis2.setDisable(true);mis3.setDisable(true);
            			button.setDisable(true);
                        System.out.println("Invalid operation! Cannibals ate the missionaries. You lost!");
                    }
        		}
        	} else if((!leftSpot || !rightSpot) && boatDist[2]==1){
        		playGoSound();
        		if(boatDist[0]==1) {
        			operation = new Retour_un_can();
        			nextState = operation.action(currentState);
                    if (nextState != null) {
                    	steps++;
                        currentState = nextState;
                        boatDist[2]=0;
                        playWaterSound();
                        boattransitionRToL.play();
                      //test can1
                        if(can1Spot == 4) {
                        	can1transitionLToLBoatre.play();
                        	can1Spot = 2;
                        } else if(can1Spot == 5) {
                        	can1transitionRToRBoatre.play();
                        	can1Spot = 3;
                        }
                        //test can2
                        if(can2Spot == 4) {
                        	can2transitionLToLBoatre.play();
                        	can2Spot = 2;
                        } else if(can2Spot == 5) {
                        	can2transitionRToRBoatre.play();
                        	can2Spot = 3;
                        }
                      //test can3
                        if(can3Spot == 4) {
                        	can3transitionLToLBoatre.play();
                        	can3Spot = 2;
                        } else if(can3Spot == 5) {
                        	can3transitionRToRBoatre.play();
                        	can3Spot = 3;
                        }
                        System.out.println("valid operation! operation done");
                    } else {
                    	playLoseSound();
                    	echec.setVisible(true);labelEchec.setVisible(true);
            			buttonQuit.setVisible(true);
            			buttonReplay.setVisible(true);
            			can1.setDisable(true);can2.setDisable(true);can3.setDisable(true);
            			mis1.setDisable(true);mis2.setDisable(true);mis3.setDisable(true);
            			button.setDisable(true);
                        System.out.println("Invalid operation! Cannibals ate the missionaries. You lost!");
                    }
        		} else {
        			operation = new Retour_un_miss();
        			nextState = operation.action(currentState);
                    if (nextState != null) {
                    	steps++;
                        currentState = nextState;
                        boatDist[2]=0;
                        playWaterSound();
                        boattransitionRToL.play();
                      //test mis1
                        if(mis1Spot == 4) {
                        	mis1transitionLToLBoatre.play();
                        	mis1Spot = 2;
                        } else if(mis1Spot == 5) {
                        	mis1transitionRToRBoatre.play();
                        	mis1Spot = 3;
                        }
                        //test mis2
                        if(mis2Spot == 4) {
                        	mis2transitionLToLBoatre.play();
                        	mis2Spot = 2;
                        } else if(mis2Spot == 5) {
                        	mis2transitionRToRBoatre.play();
                        	mis2Spot = 3;
                        }
                      //test mis3
                        if(mis3Spot == 4) {
                        	mis3transitionLToLBoatre.play();
                        	mis3Spot = 2;
                        } else if(mis3Spot == 5) {
                        	mis3transitionRToRBoatre.play();
                        	mis3Spot = 3;
                        }
                        System.out.println("valid operation! operation done");
                    } else {
                    	playLoseSound();
                    	echec.setVisible(true);labelEchec.setVisible(true);
            			buttonQuit.setVisible(true);
            			buttonReplay.setVisible(true);
            			can1.setDisable(true);can2.setDisable(true);can3.setDisable(true);
            			mis1.setDisable(true);mis2.setDisable(true);mis3.setDisable(true);
            			button.setDisable(true); 
                        System.out.println("Invalid operation! Cannibals ate the missionaries. You lost!");
                    }
        		}
        	} 
        	
        	if (currentState.test_but()) {  
        			solved = true;
        			/*System.out.println("Congratulations! You've solved the problem!");
                    System.out.println("Your Score is : "+steps+" steps!");       */		   
            }
        });   
        
        // Création du conteneur pour les rectangles et les boutons
        Pane root = new Pane(ciel, premierRive, deuxiemeRive, rive, boat, can1, can2, can3, 
        					mis1, mis2, mis3, button, label, congrats, echec, buttonQuit, buttonReplay,
        					labelCongrats, labelEchec);
        
        // Création de la scène
        Scene scene = new Scene(root, 1000, 600);

        scene.getStylesheets().add(getClass().getResource("/application/application.css").toExternalForm());
        // Configuration de la fenêtre
        primaryStage.setTitle("Missionaries and Cannibals Game");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.centerOnScreen();
        primaryStage.show();
	}
	
	private void playBgSound() {	 		      
		URL resource = getClass().getResource("/sounds/background.wav");
	    Media media = new Media(resource.toString());
	    bgMediaPlayer = new MediaPlayer(media);
	    bgMediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
	    bgMediaPlayer.setVolume(0.8);
	    bgMediaPlayer.play();
    }
	private void playJumpSound() {	 		
		if (jumpMediaPlayer != null) {
	        jumpMediaPlayer.stop();
	    }
	    URL resource = getClass().getResource("/sounds/jump.wav");
	    Media media = new Media(resource.toString());
	    jumpMediaPlayer = new MediaPlayer(media);
	    jumpMediaPlayer.setVolume(0.9);
	    jumpMediaPlayer.play();;
	}
	private void playWaterSound() {	
		if (waterMediaPlayer != null) {
	        waterMediaPlayer.stop();
	    }
	    URL resource = getClass().getResource("/sounds/water.wav");
	    Media media = new Media(resource.toString());
	    waterMediaPlayer = new MediaPlayer(media);
	    waterMediaPlayer.setVolume(0.8);
	    waterMediaPlayer.setStartTime(Duration.seconds(0.3));
	    waterMediaPlayer.setStopTime(Duration.seconds(2.8));
	    waterMediaPlayer.play();
	}
	private void playGoSound() {
		if (goMediaPlayer != null) {
	        goMediaPlayer.stop();
	    }
	    URL resource = getClass().getResource("/sounds/go.wav");
	    Media media = new Media(resource.toString());
	    goMediaPlayer = new MediaPlayer(media);
	    goMediaPlayer.play();
	}
	private void playWinSound() {
		if (winMediaPlayer != null) {
			winMediaPlayer.stop();
	    }
	    URL resource = getClass().getResource("/sounds/win.wav");
	    Media media = new Media(resource.toString());
	    winMediaPlayer = new MediaPlayer(media);	    
	    winMediaPlayer.play();
	}
	private void playLoseSound() {
		if (loseMediaPlayer != null) {
			loseMediaPlayer.stop();
	    }
	    URL resource = getClass().getResource("/sounds/lose.wav");
	    Media media = new Media(resource.toString());
	    loseMediaPlayer = new MediaPlayer(media);
	    loseMediaPlayer.setStartTime(Duration.seconds(1.1));
	    loseMediaPlayer.setStopTime(Duration.seconds(6));
	    loseMediaPlayer.play();
	}
}
