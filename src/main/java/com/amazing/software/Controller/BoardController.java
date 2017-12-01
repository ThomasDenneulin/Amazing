package com.amazing.software.Controller;

import com.amazing.software.Model.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Stack;

public class BoardController implements Initializable {

    //region attributs
    //TODO assigné dans la vue
    @FXML
    private GridPane handUiP1; //Ui handPlayer du joueur 1
    @FXML
    private GridPane handUiP2; // Ui handPlayer du joueur 2
    @FXML
    private Pane pioche; //Ui pour la pioche
    @FXML
    private GridPane boardUiP1; //Ui pour le terrain du joueur 1
    @FXML
    private GridPane boardUiP2; //Ui pour le terrain du joueur 2


    //Variable pour le jeu
    private Player player1;
    private Player player2;

    public Stack<Card> getDeck() {
        return deck;
    }

    private Stack<Card> deck;
    //endregion


    public BoardController() throws Exception{
        this.player1 = new Player();
        this.player2 = new Player();
        this.deck = new Stack<Card>();
        Shuffle();
    }


    //region méthodes
    ///Initialize a shuffled deck this is the main function to generate the deck
    public void Shuffle(){
        List<Card> list = GenerateADeck();
        java.util.Collections.shuffle(list);
        this.deck.addAll(list);
    }
    ///Generate a deck with all different race
    public List<Card> GenerateADeck() {
        List<Card> allDeck = new ArrayList<>();
        //Dryad
        for (int i = 0; i < 7; i++) {
            allDeck.add(new Card(new Dryad()));
            allDeck.add(new Card(new Gobelin()));
            allDeck.add(new Card(new Troll()));
            allDeck.add(new Card(new Korrigan()));
            allDeck.add(new Card(new Gnome()));
            allDeck.add(new Card(new Elf()));
        }
        return allDeck;
    }



    public void DistributeCards() throws Exception{
        while(player1.getHand().size() < 5 || player2.getHand().size() < 5){
            //Creation d'une vue carte contronller
            CardController cardController = new CardController(player1.Draw(this.deck));
            cardController.parent = this;

            /*//Init de l'event onClick sur la carte.
            cardController.getPane().setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    System.out.println(cardController.getCard().toString());
                    cardController.getCard().getRace().Power(player1,player2,deck,0);
                    player1.Play(cardController.getCard());
                    ActionEvent activePower = new ActionEvent();
                    System.out.println("Le joueur a activé son pouvoir");
                }
            });*/


            //Création d'une nouvelle colonne dans le GridHandUi
            ColumnConstraints columnConstraints = new ColumnConstraints();
            handUiP1.getColumnConstraints().add(columnConstraints);
            //Bind de la CC au GridHandUi
            handUiP1.add(cardController.getPane(),player1.getHand().size(),0); //L'index de la colonne est donnée par la taille de la main

            CardController cardController1 = new CardController(player2.Draw(this.deck));
            ColumnConstraints columnConstraints1 = new ColumnConstraints();
            handUiP2.getColumnConstraints().add(columnConstraints);
            handUiP2.add(cardController1.getPane(),player2.getHand().size(),0);
        }
    }
    ///First function to call in the main
    public void StartGame()throws Exception{
        DistributeCards();
    }
    //endregion

    public void HandUpdate() throws Exception{
        //Refresh player1
        handUiP1.getColumnConstraints().remove(0,handUiP1.getColumnConstraints().size());
        int count = 0;
        for (Card card : player1.getHand()) {
            CardController cardController = new CardController(card);
            ColumnConstraints columnConstraints = new ColumnConstraints();
            handUiP1.getColumnConstraints().add(columnConstraints);
            handUiP1.add(cardController.getPane(),count,0);
            count++;
        }

        //Refresh player2
        handUiP2.getColumnConstraints().remove(0,handUiP2.getColumnConstraints().size());
        count = 0;
        for (Card card : getPlayer2().getHand()) {
            CardController cardController = new CardController(card);
            ColumnConstraints columnConstraints = new ColumnConstraints();
            handUiP1.getColumnConstraints().add(columnConstraints);
            handUiP1.add(cardController.getPane(),count,0);
            count++;
        }
    }

    //Cette fonction est appellé lorsque que BoardController est completement initialisé
    @Override
    public void initialize(URL location, ResourceBundle resources){
        GridsPanesInit();
    }

    //region InitMethods
    private void GridsPanesInit(){
        InitHandUi();
        InitBoardUi();
    }
    private void InitHandUi(){

        //Space between 2 card
        this.handUiP1.setVgap(15);
        this.handUiP1.setHgap(15);
        this.handUiP1.setAlignment(Pos.CENTER);
        //Initialisation du GridPane avec 1 ligne (handPlayer vide)
        final RowConstraints rowConstraints = new RowConstraints();
        this.handUiP1.getRowConstraints().add(rowConstraints);

        this.handUiP2.setVgap(15);
        this.handUiP2.setHgap(15);
        this.handUiP2.setAlignment(Pos.CENTER);
        //Initialisation du GridPane avec 1 ligne (handPlayer vide)
        final RowConstraints rowConstraints1 = new RowConstraints();
        this.handUiP2.getRowConstraints().add(rowConstraints1);
    }
    private void InitBoardUi(){
        //Space between 2 card
        this.boardUiP1.setVgap(15);
        this.boardUiP1.setHgap(15);
        this.boardUiP1.setAlignment(Pos.CENTER);
        //Initialisation du GridPane avec 1 ligne (boardPlayer vide)
        final RowConstraints rowConstraints = new RowConstraints();
        this.boardUiP1.getRowConstraints().add(rowConstraints);
    }
    //endregion
    //region Get/Set
    public Player getPlayer1() {
        return player1;
    }
    public Player getPlayer2() {
        return player2;
    }
    public GridPane getHandUiP1() {return handUiP1;}
    //endregion
}