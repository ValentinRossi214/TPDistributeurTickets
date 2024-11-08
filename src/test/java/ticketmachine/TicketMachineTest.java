package ticketmachine;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

class TicketMachineTest {
	private static final int PRICE = 50; // Une constante

	private TicketMachine machine; // l'objet à tester

	@BeforeEach
	public void setUp() {
		machine = new TicketMachine(PRICE); // On initialise l'objet à tester
	}

	@Test
	// On vérifie que le prix affiché correspond au paramètre passé lors de
	// l'initialisation
	// S1 : le prix affiché correspond à l’initialisation.
	void priceIsCorrectlyInitializedTest() {
		// Paramètres : valeur attendue, valeur effective, message si erreur
		assertEquals(PRICE, machine.getPrice(), "Initialisation incorrecte du prix");
	}

	@Test
	// S2 : la balance change quand on insère de l’argent
	void insertMoneyChangesBalanceTest() {
		machine.insertMoney(10);
		machine.insertMoney(20);
		// Les montants ont été correctement additionnés
		assertEquals(10 + 20, machine.getBalance(), "La balance n'est pas correctement mise à jour");
	}

	@Test
	// S3 : on n'imprime pas le ticket si le montant inséré est insuffisant
	void cantPrintIfInsufficientAmountTest() {
		machine.insertMoney(10);
		assertFalse(machine.printTicket(), "Le ticket a été imprimé alors que le montant inséré est insuffisant");
	}

	@Test
	// S4 : on imprime le ticket si le montant inséré est suffisant
	void canPrintIfSufficientAmountTest() {
		machine.insertMoney(PRICE);
		assertTrue(machine.printTicket(), "Le ticket n'a pas été imprimé alors que le montant inséré est suffisant");
	}

	@Test
	// S5 : Quand on imprime un ticket la balance est décrémentée du prix du ticket
	void printTicketDecrementsBalanceTest() {
		machine.insertMoney(60);
		machine.printTicket();
		assertEquals(60 - PRICE, machine.getBalance(), "La balance n'est pas correctement mise à jour après impression du ticket");
	}

	@Test
	// S6 : le montant collecté est mis à jour quand on imprime un ticket (pas avant)
	void totalIsUpdatedWhenTicketPrintedTest() {
		machine.insertMoney(PRICE);
		assertEquals(0, machine.getTotal(), "Le total est mis à jour avant impression du ticket");
		machine.printTicket();
		assertEquals(PRICE, machine.getTotal(), "Le total n'est pas mis à jour après impression du ticket");
	}

	@Test
	// S7 : refund() rend correctement la monnaie
	void refundGivesChangeTest() {
		machine.insertMoney(10);
		machine.insertMoney(20);
		// On vérifie que la méthode rend bien la monnaie
		assertEquals(10 + 20, machine.refund(), "Le montant rendu n'est pas correct");
	}

	@Test
	// S8 : refund() remet la balance à zéro
	void refundResetsBalanceTest() {
		machine.insertMoney(10);
		machine.refund();
		assertEquals(0, machine.getBalance(), "La balance n'est pas remise à zéro après un rendu de monnaie");
	}


	@Test
	// S9 : on ne peut pas insérer un montant négatif
	void cantInsertNegativeAmountTest() {
		try {
			machine.insertMoney(-10);
			fail("On peut insérer un montant négatif");
		} catch (IllegalArgumentException e) {
			// On s'attend à ce que l'exception soit levée
		}
	}

	@Test
	// S10 : on ne peut pas insérer un montant égal à 0
	void cantInsertNoAmountTest() {
		try {
			machine.insertMoney(0);
			fail("On peut insérer un montant égal à 0");
		} catch (IllegalArgumentException e) {
			// On s'attend à ce que l'exception soit levée
		}
	}

	@Test
	// S11 : on ne peut pas créer de machine qui délivre des tickets dont le prix est négatif
	void cantCreateMachineWithNegativePriceTest() {
		try {
			new TicketMachine(-10);
			fail("On peut créer une machine avec un prix négatif");
		} catch (IllegalArgumentException e) {
			// On s'attend à ce que l'exception soit levée
		}
	}

}
