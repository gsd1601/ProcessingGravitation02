# ProcessingGravitation02

Das Programm zeichnet einen (Gravitations-) Mittelpunkt und einen bewegten Körper \* der nach dem Gravitationsgesetz a = constM1/ AbstandM1 hoch 2 zum Gravitationsmittelpunkt \* beschleunigt wird und dann eine elliptische Flugbahn erhält. 

 Die Beschleunigung wird als Veränderung der Schrittweite pro Framerate eingebracht. Also: Nächste Koordinate =  aktuelle Koordininate plus aktuelle Schrittweite plus Änderung der Schrittweite a.  Dargestellt werden diese Werte in (float) Pixel.

 Ziel des nächsten Schrittes ist: Bestimmung der Beschleunigung (= Änderung der Schrittweite) im Rahmen einer Methode, die den Abstandsvektor erhält und den Beschleunigungsvektor zurückgibt.

Aktuell können zwei anziehende Massen abgebildet werden: Keplersches 3-Körperproblem vereinfacht  auf zwei ruhende Massen (Erde M1, Mond M2) und eine kleine bewegte Masse m (Sat, Raumschiff)  Stand 2021-01-16 12:20

![GravitationMasse1undMasse2.png](GravitationMasse1undMasse2.png?fileId=76089#mimetype=image%2Fpng&hasPreview=true)