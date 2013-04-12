package generated;

import java.io.File;
import java.util.Scanner;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;



public class Aufgabe4 {

	public static void main(String[] args) {
		try {
			Scanner s = new Scanner(System.in);

			int choice = -1;
			while (choice < 1 ||choice > 4) {
				System.out.println("Menü:");
				System.out.println("1. Liste aller Rezepte anzeigen");
				System.out.println("2. Ein Rezept anzeigen");
				System.out.println("3. Kommentar hinzufügen");
				System.out.println("4. Schließen");
				choice = s.nextInt();
				
				switch(choice){
				case 1:
					all();
					break;
				case 2:
					list();
					System.out.println("Wählen sie ein Rezept:");
					ausgeben(Integer.parseInt(s.next())-1);
					break;
				case 3:
					list();
					System.out.println("Wählen sie ein Rezept:");
					kommentar(Integer.parseInt(s.next())-1);
					break;
				case 4:
					choice =1;
					break;
				default:
					System.out.println("Falsche eingabe!");
				}
			}
			s.close();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}
	
	private static void list() throws JAXBException {
		System.out.println("Liste der Rezepte: ");
		Rezepte list = getrecipes();
		for (int i = 0; i < list.getRezept().size(); i++) {
			if (list.getRezept().get(i) instanceof Rezept) {
				System.out.print(i+1);
				if(i+1 < list.getRezept().size()) {
					System.out.print(", "); 
				}
			}
		}
		System.out.println();
	}

	private static void all() throws JAXBException {
		Rezepte list = getrecipes();
		for (int i = 0; i < list.getRezept().size(); i++) {
			if (list.getRezept().get(i) instanceof Rezept) {
				ausgeben(i);
			}
		}
	}
	
	private static void ausgeben(int a) throws JAXBException {
		
		Rezepte list = getrecipes();
		if(a < list.getRezept().size()) {
			if (list.getRezept().get(a) instanceof Rezept) {
				
				Rezept r = list.getRezept().get(a);
				
				System.out.println("Name: " + r.getTitle());
				
				System.out.println("Zutaten:");
				for (int i = 0; i < r.getZutaten().getZutat().size(); i++) {
					Zutat zutat = r.getZutaten().getZutat().get(i);
					System.out.println(zutat.getMenge() + " " + zutat.getArt());
				}
				System.out.println("");
				
				System.out.println("Zubereitung:");
				System.out.println("Arbeitszeit: " + r.getZubereitung().getArbeitszeit());
				System.out.println("Schwierigkeitsgrad: " + r.getZubereitung().getSchwierigkeit());
				System.out.println(r.getZubereitung().getBrennwert() + " kcal");
				System.out.println();
				
				for (int i = 0; i < r.getZubereitung().getSchritt().size(); i++) {
					System.out.println(r.getZubereitung().getSchritt().get(i));
				}
				System.out.println();
				
				if (r.getKommentare() instanceof Kommentare) {
					System.out.println("Kommentare:");
					for (int i = 0; i < r.getKommentare().getKommentar().size(); i++) {
						System.out.println("Kommentar " + i+1 + ": " + r.getKommentare().getKommentar().get(i));
					}
					System.out.println();
				}
			}
		}
	}
	
	private static void kommentar(int i) throws JAXBException {
		JAXBContext ctx = JAXBContext.newInstance("generated");
		Rezepte l = getrecipes();
		Rezept r = l.getRezept().get(i);
		
		Scanner scan = new Scanner(System.in);
		
		System.out.println("Neuer Kommentar:");
		String text = scan.nextLine();
		scan.close();
		
		r.getKommentare().getKommentar().add(text);

		Marshaller marshaller = ctx.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

		marshaller.marshal(l, (new File("src/Aufgabe3d.xml")));
	}

	
	private static Rezepte getrecipes() throws JAXBException {
		JAXBContext ctx = JAXBContext.newInstance("generated");
		Unmarshaller unmarshaller = ctx.createUnmarshaller();
		Rezepte list = (Rezepte) unmarshaller.unmarshal(new File("src/Aufgabe3d.xml"));
		return list;
	}
	
}
