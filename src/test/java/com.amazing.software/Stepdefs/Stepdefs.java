package com.amazing.software.Stepdefs;

import com.amazing.software.Model.*;
import com.sun.javafx.scene.control.ControlAcceleratorSupport;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.Stack;

import static org.junit.Assert.assertEquals;


public class Stepdefs {

    private Player player1 = new Player();
    private Player player2 = new Player();
    private Board board= new Board(player1,player2);
    private Stack<Card> deck;

    @Given("^I have two players$")
    public void CreatePlayer() throws Throwable{
        player1 = new Player();
        player2 = new Player();
    }

    @Then("^they have (\\d+) cards$")
    public void NumberOfCard(int nbCard) throws Throwable{
        assertEquals("Player1 should have "+nbCard+" card",nbCard,player1.getHand().size());
        assertEquals("Player2 should have "+nbCard+" card",nbCard,player2.getHand().size());

    }

    @When("^I Distribute Cards$")
    public void LaunchGame() throws Throwable{
        board = new Board(player1,player2);
        board.DistributeCards();
    }

    @Given("^Player1 has (\\d+) cards$")
    public void Player1(int nbCard) throws Throwable{
        while (player1.getHand().size()< nbCard)
        {
            player1.Draw(board.getDeck());
        }

        assertEquals("Player1 should have "+nbCard+" card",nbCard,player1.getHand().size());
    }

    @Given("^Player2 has (\\d+) cards$")
    public void Player2(int nbCard) throws Throwable{
        while (player2.getHand().size()< nbCard)
        {
            player2.Draw(board.getDeck());
        }
        assertEquals("Player2 should have "+nbCard+" card",nbCard,player2.getHand().size());
    }
    @Then("^Player1 should have (\\d+) cards$")
    public void Player1Card(int nbCard) throws Throwable{
        assertEquals("Player1 should have "+nbCard+" card",nbCard,player1.getHand().size());
    }
    @Then("^Player2 should have (\\d+) cards$")
    public void Player2Card(int nbCard) throws Throwable{
        assertEquals("Player2 should have "+nbCard+" card",nbCard,player2.getHand().size());
    }

    @Given("^Player1 has a gobelin$")
    public void SetGobelin() throws Throwable{
        Card gobelin = new Card(new Gobelin());
        player1.getHand().add(gobelin);
    }

    @When("^Player1 use gobelin$")
    public void UseGobelin() throws Throwable{

        for (Card c:player1.getHand()) {
            if (c.getRace() instanceof Gobelin)
            {
                player1.Play(c);
                c.getRace().Power(player1,player2,board.getDeck(),c);
                break;

            }
        }
    }

    @Given("^Player1 has a Gnome$")
    public  void  SetGnome() throws Throwable{
        Card gnome = new Card(new Gnome());
        player1.getHand().add(gnome);
    }

    @When("^Player1 use Gnome$")
    public void UseGnome() throws Throwable {
        for (Card c:player1.getHand()) {
            if (c.getRace() instanceof Gnome)
            {
                player1.Play(c);
                c.getRace().Power(player1, player2, board.getDeck(),c);
                break;

            }
        }
    }

    @Given("^Player1 has a korrigan$")
    public  void  SetKorrignan() throws Throwable{
        Card korrigan = new Card(new Korrigan());
        player1.getHand().add(korrigan);
    }

    @When("^Player1 use korrigan$")
    public void UseKorrrigan() throws Throwable {
        for (Card c:player1.getHand()) {
            if (c.getRace() instanceof Korrigan)
            {
                player1.Play(c);
                c.getRace().Power(player1, player2, board.getDeck(),c);
                break;

            }
        }
    }

    @Given("^Player1 has (\\d+) cards on his board$")
    public void Player1Board(int nbCardBoard) throws Throwable{

        assertEquals(nbCardBoard+" card on player 1 board",nbCardBoard,player1.getBoard().size());
    }

    @Given("^Player2 has (\\d+) cards on his board$")
    public void SetPlayer2Board(int nbCardBoard)throws Throwable{
        player2 = new Player();
        board = new Board(player1,player2);
        while (player2.getBoard().size()<nbCardBoard) {
            Card c1 = new Card(new Gnome());
            player2.getBoard().add(c1);
        }
        assertEquals(nbCardBoard+" card on player 2 board",nbCardBoard,player2.getBoard().size());

    }

    @Given("^Player1 has a Troll$")
    public  void  SetTroll() throws Throwable{
        Card troll = new Card(new Troll());
        player1.getHand().add(troll);
    }

    @When("^Player1 use Troll$")
    public void UseTroll() throws Throwable {
        for (Card c:player1.getHand()) {
            if (c.getRace() instanceof Troll)
            {
                player1.Play(c);
                c.getRace().Power(player1, player2, board.getDeck(),c);

                break;

            }
        }
    }

    @Then("^Player2 has (\\d+) card on his board$")
    public void Player2Board(int nbCardBoard) throws Throwable{
        assertEquals(nbCardBoard+" card on player 2 board",nbCardBoard,player2.getBoard().size());
    }

    @Given("^Player1 has a Dryad$")
    public  void  SetDryad() throws Throwable{
        Card dryad = new Card(new Dryad());
        player1.getHand().add(dryad);
    }

    @When("^Player1 use Dryad$")
    public void UseDryad() throws Throwable {
        for (Card c:player1.getHand()) {
            if (c.getRace() instanceof Dryad)
            {
                player1.Play(c);
                c.getRace().Power(player1, player2, board.getDeck(),player2.getBoard().get(0));

                break;

            }
        }
    }

    @And("^Player2 has an elf$")
    public void playerHasAnElf() throws Throwable {
        Card elf = new Card(new Elf());
        player2.getHand().add(elf);
    }

    @When("^Player2 use elf$")
    public void playerUseElf() throws Throwable {
        for (Card c:player2.getHand()) {
            if (c.getRace() instanceof Elf)
            {
                player2.Play(c);
                c.getRace().Power(player2, player1, board.getDeck(),player2.getBoard().get(0));

                break;

            }
        }
    }

    @Given("^The Deck is empty$")
    public void EmptyDeck() throws  Throwable{
        Stack<Card> emptyDeck = new Stack<>();
        board.setDeck(emptyDeck);
        assertEquals("there is 0 card in the deck",0,board.getDeck().size());
    }

    @And("^There is one card in the deck$")
    public void thereIsOneCardInTheDeck() throws Throwable {
        Stack<Card> deckWithOneCard = new Stack<>();
        Card elf = new Card(new Elf());
        deckWithOneCard.add(elf);
        board.setDeck(deckWithOneCard);
        assertEquals("there is 1 card in the deck",1,board.getDeck().size());
    }

    @When("^Player1 draw a card$")
    public void playerDrawACard() throws Throwable {
        player1.Draw(board.getDeck());
    }
}
