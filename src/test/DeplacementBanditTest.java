package test;

import Controllers.Jeu;
import Enums.Direction;
import Enums.Position;
import Models.Bandit;
import Models.Wagon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DeplacementBanditTest {
    private Jeu jeu;
    private Bandit bandit;

    @BeforeEach
    public void setUp() {
        jeu = new Jeu();
        jeu.startGame();
        bandit = jeu.getJoueurCourant();
    }

    @Test
    public void testBanditSeDeplaceAvant() {
        Wagon wagonInitial = bandit.getWagon();
        bandit.seDeplacer(Direction.AVANT);
        Wagon wagonApresDeplacement = bandit.getWagon();

        assertNotEquals(wagonInitial, wagonApresDeplacement, "Le bandit n'a pas changé de wagon");
    }

    @Test
    public void testBanditSeDeplaceArriere() {
        Wagon wagonInitial = bandit.getWagon();
        bandit.seDeplacer(Direction.ARRIERE);
        Wagon wagonApresDeplacement = bandit.getWagon();

        assertNotEquals(wagonInitial, wagonApresDeplacement, "Le bandit n'a pas changé de wagon");
    }

    @Test
    public void testBanditSeDeplaceHaut() {
        Position positionInitiale = bandit.getPosition();
        bandit.seDeplacer(Direction.HAUT);
        Position positionApresDeplacement = bandit.getPosition();

        assertNotEquals(positionInitiale, positionApresDeplacement, "Le bandit n'a pas changé de position");
    }

    @Test
    public void testBanditSeDeplaceBas() {
        bandit.seDeplacer(Direction.HAUT);
        Position positionInitiale = bandit.getPosition();
        bandit.seDeplacer(Direction.BAS);
        Position positionApresDeplacement = bandit.getPosition();

        assertNotEquals(positionInitiale, positionApresDeplacement, "Le bandit n'a pas changé de position");
    }
    @Test
    public void testBanditSeDeplaceHautQuandIlEstDejaSurLeToit() {
        bandit.setPosition(Position.TOIT);
        bandit.seDeplacer(Direction.HAUT);
        assertEquals(Position.TOIT, bandit.getPosition(), "Le bandit a changé de position alors qu'il ne devrait pas");
    }

    @Test
    public void testBanditSeDeplaceBasQuandIlEstDejaALInterieur() {
        bandit.setPosition(Position.INTERIEUR);
        bandit.seDeplacer(Direction.BAS);
        assertEquals(Position.INTERIEUR, bandit.getPosition(), "Le bandit a changé de position alors qu'il ne devrait pas");
    }

    @Test
    public void testBanditSeDeplaceAvantQuandIlEstDansLaLocomotive() {
        bandit.setWagon(jeu.getTrain().getLocomotive());
        Wagon wagonInitial = bandit.getWagon();
        bandit.seDeplacer(Direction.AVANT);
        assertEquals(wagonInitial, bandit.getWagon(), "Le bandit a changé de wagon alors qu'il ne devrait pas");
    }

    @Test
    public void testBanditSeDeplaceArriereQuandIlEstDansLeDernierWagon() {
        bandit.setWagon(jeu.getTrain().getDernierWagon());
        Wagon wagonInitial = bandit.getWagon();
        bandit.seDeplacer(Direction.ARRIERE);
        assertEquals(wagonInitial, bandit.getWagon(), "Le bandit a changé de wagon alors qu'il ne devrait pas");
    }

}