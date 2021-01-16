package de.gsc.processing;

import processing.core.PApplet;
import processing.core.PVector;

/**
 * Die Klasse Vektoren enthält auch die main Methode
 * Das Programm zeichnet einen (Gravitations-) Mittelpunkt und einen bewegten Körper
 * der nach dem Gravitationsgesetz  a = constM1/ AbstandM1 hoch 2 zum Gravitationsmittelpunkt 
 * beschleunigt wird und dann eine elliptische Flugbahn erhält.
 * Die Beschleunigung wird als Veränderung der Schrittweite pro Framerate eingebracht
 * Also: Nächste Koordinate = 
 * aktuelle Koordininate plus aktuelle Schrittweite v plus Änderung der Schrittweite a
 * Dargestellt werden diese Werte in (float) Pixel  
 * Ziel des nächsten Schrittes ist: Bestimmung der Beschleunigung  (= Änderung der Schrittweite)
 * im Rahmen einer Methode, die den Abstandsvektor erhält und den Beschleunigungsvektor zurückgibt.
 * Damit können dann zwei anziehende Massen abgebildet werden: Keplersches 3-Körperproblem vereinfacht
 *  auf zwei ruhende Massen (Erde M1, Mond M2) und  eine kleine bewegte Masse m (Sat, Raumschiff)   
 *  Stand 2021-01-13 12:20
 */

public class Vektoren extends PApplet {
	int cwidth = 1200;
	int cheight = 800;
	int bottomheight = cheight - 30; // Bodenlinie hat die y- Koordinate 50 höher als Untergrenze

	PVector pos1 = new PVector(100, 400); // Startvektor von pos1
	// PVector pos2 = new PVector(cwidth, 200);
	// float speeddelta = (float) 0.0;
	float yspeed = 1;
	PVector speed1 = new PVector(1, 1, 0); // fliegt mit 1 in x Richtung
	PVector speed2 = new PVector(-1, 1, 0); // fliegt mit in Gegenrichtung -1 in x Richtung
	PVector speedDelta1 = new PVector((float) 0.0, (float) 0.0); // speedDelta ist Beschleunigung Wert 0.1 nach unten
	PVector speedDelta2 = new PVector((float) 0.0, (float) 0.1); // speedDelta ist Beschleunigung - was sonst
	PVector speedDeltaMasse = new PVector(); // speedDeltaMasse ist umgekeht proportional zum Quadrat der Entfernung

	// Anziehung zum Mittelpunkt
	PVector centerOGM1 = new PVector(600, 400); // VEKTOR ZUR MASSE 1 IM MITTELPUNKT
	PVector centerOGM2 = new PVector(600, 400); // VEKTOR ZUR MASSE 2 RECHTS VOM MITTELPUNKT
	
	PVector distanceCOGM1 = new PVector(); // Abstand von pos1 ZUR MASSE 1 

	public static void main(String[] args) {
		PApplet.main("de.gsc.processing.Vektoren");

	}

	// method for setting the size of the window
	public void settings() {
		size(cwidth, cheight);
	}

	// identical use to setup in Processing IDE except for size()
	public void setup() {
		background(200, 240, 255); // ein hellblauer Hintergrund
		stroke(255);
		strokeWeight(5);
		frameRate(25);
	}

	/**
	 * draw scheint einen timer zu enthalten und stößt automatisch einen Loop an.
	 * draw() is called automatically and should never be called explicitly.
	 * 
	 */
	// identical use to draw in Prcessing IDE
	public void draw() {
		stroke(255, 0, 0);// ein roter Rand
		fill(220); // ein hellgrauer Hintergrund
		rectMode(CORNERS);
		// rect
		rect(0, bottomheight, cwidth, cheight); // Ein Rechteck als Boden mit rotem Horizont

		stroke(255, 100, 0);   // rot mit ewas grün: orange
		ellipse(pos1.x, pos1.y, 20, 20);// das Objekt pos 1, ein Kreis wird angelegt
//		stroke(255, 255, 0);

		distanceCOGM1.x = centerOGM1.x - pos1.x; // Anziehungspunkt - pos1: Das ist der Verbinudungsvektor zwischen Objekt
		distanceCOGM1.y = centerOGM1.y - pos1.y; // pos1 und Center of Gravity, dem Anziehungspunkt 
		System.out.println(
				" centerOGM1 X  " + centerOGM1.x + 
				" centerOGM1 Y " + centerOGM1.y + 
				" centerOGM1  mag " + centerOGM1.mag() + " |");
		System.out.println(
				"  Obj pos1 X " + pos1.x + 
				"  Objpos1 Y " + pos1.y + 
				"  pos1 mag " + pos1.mag() + " |");
		System.out.println(
				"distanceCOGM1 X " + distanceCOGM1.x +
				" distanceCOGM1 Y " + distanceCOGM1.y +
				" distanceCOGM1  mag " + distanceCOGM1.mag() + " |");

		/**
		 * aForce ist eine Vektor der Anziehungskraft Seine Richtung ist durch den
		 * Vektor distanceCOGM1 gegeben. Seine Größe ist const / (mag distanceCOGM1.mag) Der
		 * Kraftvektor wird NICHTbenutzt, weil direkt die Beschleunigung eingetragen wird.
		 */
		// PVector aForce = new PVector(); // VEKTOR ANZIEHUNGSKRAFT
		/**
		 * Beschleunigung in Richtung Anziehug 
		 * Vektor speedDeltaMasse (a) = const / (dist * dist) * Vektor distanceCOGM1.
		 */

		System.out.println(" ------ ");
// Zeichne den Ortsvektor von pos1
		translate(0, 0);  // Absolut: 0,0
		stroke(205, 255, 200);
		strokeWeight(1);
		line(0, 0, pos1.x, pos1.y); // ORTSVEKTOR pos1
// Zeichne den Ortsvektor von centerOGM1, dem Gravitationszentrum, ruhend 
		stroke(105, 200, 255);
		line(0, 0, centerOGM1.x, centerOGM1.y);//ORTSVEKTOR Gravitationszentrum
		fill(0); 
		circle(centerOGM1.x, centerOGM1.y, 22);

//		translate(cwidth / 4, 0);  
		stroke(255, 255, 0); // gelb
		strokeWeight(1);
		// line(0, 0, pos1.x, pos1.y);
		
		// Achtung: Mehrere translate Befehle addieren sich
		translate(cwidth / 2, 400);  // Absolut: (cwidth/2, 400)

		// Draw the resulting vector
		stroke(0, 255, 255); // grün-blau ist türkis
		strokeWeight(1);
		// Zeichne Abstandsvektor distanceCOGM1
// 		line(0, 0, distanceCOGM1.x, distanceCOGM1.y);

		float dist = distanceCOGM1.mag(); // speichert den aktuellen Wert des Abstandes
		distanceCOGM1.normalize(); // Die aktuelle Vektor größe wird auf 1 gesetzt, die Richtung bleibt erhalten
// für die Beschleunigung, Kopieren des normalisierten Vektors 
		speedDeltaMasse.x = distanceCOGM1.x; // der normalisiertte Abstandsvektor wird im Vektor 'speedDeltaMasse' ges
		speedDeltaMasse.y = distanceCOGM1.y; // gespeichert. distanceCOGM1 und speedDeltaMasse  sind ist jetzt Richtungsvektoren mit Betrag 1
		speedDeltaMasse.mult(4000 / (dist * dist)); // Hier wird der Betrag der Beschleunigung hinzugerechnet
// für die Darstellung
		distanceCOGM1.mult(100 * 200 / dist); // ein Vektor mit der invertierten Größe wird gezeichnet, simuliert die
											// Abnahme der Anziehung mit dem Anwachsen der Entfernung
		// Draw the resulting vector
		translate(200, 0); // Absolut: (cwidth/2+200 , 400)
		stroke(0, 255, 255); // grün-blau ist türkis
		strokeWeight(1);
		line(0, 0, distanceCOGM1.x, distanceCOGM1.y);

		/**
		 * Umkehrfunktion Zurückspringen vom Boden wie ein Ball
		 */
		if (pos1.y > bottomheight && speed1.y > 0) { // "größer" bedeutet hier "tiefer", unterhalb Bodenhöhen
			speed1.y = speed1.y * (float) (-0.9); // emuliert Richtungsumkehr, das Zurückspringen nach , verlangsamt
			// stellt sicher, dass nur einmal umgkehrt wird, falls der nächste Zeitpunkt
			// noch nicht im Feld zurück ist
			// das kann bei hoher Geschwindigkeit und hoher Dämpfung passieren
			// alternativ könnte ich auch die Geschwindigkeit überprüfen: Postiv = Fallen:
			// umkehren / Negativ = Steigen: Nicht umkehren
			// Noch besser und genauer wäre es, die Zeitschritte zu verkleinern, wenn der
			// Boden naht.
			// pos1.y = bottomheight ;
		}

//		if (pos2.y > bottomheight && speed2.y > 0) { // Umkehr falls noch fallend falls der nächste Zeitpunkt auch noch
//														// unterhalb der bottomheight
//
//			speed2.y = speed2.y * (float) (-0.9); // emuliert Richtungsumkehr, das Zurückspringen nach , verlangsamt
//			// pos2.y = cheight; // stellt sicher, dass nur einmal umgkehrt wird,
//		}

		speed1.add(speedDeltaMasse); // beschleunigtes Fallen, Die aktuelle Geschwindigkeit wird erhöht.
		pos1.add(speed1);// Der nächste Schritt wird ausgeführt, je höher die Geschwindigkeit um so
							// größer ist der Schritt
// 		pos2.add(speed2);
//		System.out.println(
//				pos1.y + " Speed " + speed1.y + " mag " + pos1.mag() + "  aZx " + distanceCOGM1.x + " aZy " + distanceCOGM1.y);
		if (pos1.y < 210) {
			noLoop();
		}

	}

	/**
	 * accelerate soll die aktuelle Geschwindigkeit einlesen und verändern Da Objekt
	 * pos2 (PVector) würde von einem Anziehungspunkt apunkt (PVector) angezogen
	 * (fest im Raum). Dann ist die Richtung der Anziehungskraft aF aZPunkt - pos2 /
	 * Betrag (aZPunkt - pos2) aZPunkt.sub(pos2) / (aZPunkt.sub(pos2).mag)
	 * 
	 * 
	 * ( Normierter Richtungsvektor mit Betrag 1 )
	 * 
	 * 
	 */

	public void accelerate() {

	}

}
