**Fordov–Fulkersonov algoritmus**
_____________________________________
Fordov – Fulkersonov algoritmus na hľadanie maximálneho toku v sieti.
________________________________________
**Popis fungovania algoritmu**:

-	Krok 1. Zvoľ v sieti začiatočný tok y, napríklad nulový tok.
-	Krok 2. Nájdi v sieti G s tokom y zväčšujúcu polocestu μ(z, u).
-	Krok 3. Ak zväčšujúca polocesta neexistuje, tok y je maximálny. STOP.
-	Krok 4. Ak zväčšujúca polocesta μ(z, u) existuje a ma rezervu r, zmeň tok y nasledujúco:
         / y(h)     ak _h_ neleží na ceste μ(z, u)
 	 y(h)= | y(h)+r   ak _h_ leží na ceste μ(z, u) v smere svojej orientácie
         \ y(h)-r   ak _h_ leží na ceste μ(z, u) proti smere svojej orientácie
 	
 **GOTO** Krok 2.

________________________________________
**Ako spustiť**:

Ku algoritmu sú dostupné aj vstupné údaje(siete). V main class stači premenovať názov vstupného súboru a spustiť.

________________________________________
Tento algoritmus som programoval na predmet Algoritmická teória grafov.
Ma slúžiť na inspiráciu a odkontrolovanie si vysledkov.
