---
layout: post
title:  "O aplikacii NbEvid"
date:   2016-11-05 16:05:58 +0200
categories: about
---

Uz dlhsi ca som premyslal, ze vytvorim novu evidenciu. Rozmyslal som nad tym ako budem ukladat data a ake nove vlastnosti pridat.

Co sa tyka ukladania dat, premyslal som nad moznostami od vlastneho binarneho formatu (ako bola v starej DOS verzii), cez nejake SQL databazy, ktore by boli sucastou programu (ako napriklad [Apache Derby][derby], alebo [HSQLDB][hsqldb])
az po key-value databazu [Berkely DB][bdb]. Vsetky moznosti boli svojim sposobom komplikovane a najviac ma trapilo, ako zabezpecit moznost opravy pripadne poskodenej databazy. Aj kvoli tymto veciam sa mi do novej verzie dlho nechcelo pustit.

Nakoniec som sa ale rozhodol ukladat databazu ako [JSON][json]. Je to textovy format, pomerne dobre zrozumitelny a preto aj lahko rucne editovatelny, co by mohlo pomoct pri pripadnej obnove dat. Da sa aj lahko archivovat a porovnavat rozne verzie.
Dokonca sa da rucne donho prepisat stara databaza (ale to by slo aj cez program). Co moze byt teoreticky problem je bezpecnost, ale tam nevidim az taky problem, kedze uz budes mat databazu len na vlastnom pocitaci. A je mozne do buducnosti
pridat moznost ukladat databazu sifrovanu.

  [derby]: https://en.wikipedia.org/wiki/Apache_Derby
  [hsqldb]: https://en.wikipedia.org/wiki/HSQLDB
  [bdb]: https://en.wikipedia.org/wiki/Berkeley_DB
  [json]: https://en.wikipedia.org/wiki/JSON
  
Druha vec bola ake nove vlastnosti pridat. Rozmyslal som nad vecami ako strukturovane polozky (napriklad polozka Domacnost by mala podpolozky Najomne a Elektrina a do kazdej (vratane hlavnej Domacnost) by bolo mozne pripadavat sumy, pricom celkova
suma Domacnosti by bola suctom jej sumy a sum jej podpoloziek).

Vzhladom na to, ze som sa chcel vyhnut zbytocnym komplikovanym uloham som sa rozhodol zachovat len povodnu funkcionalitu. Aj ta je vsak pomerne narocna. A preto, aby som sa nezasekol niekde v polovici nedokonceneho projektu, rozhodol som sa postupovat
inkrementalne. To znamena cim skor vytvorit funkcnu verziu s tym minimom vlastnosti, ktore su potrebne na jednoduchu pracu s evidenciou. Neskor budem doplnat vlastnosti, tak dokazal program vsetko co sucasna verzia, pripadne nieco navyse (napriklad grafy).

Na tento zamer mi dobre posluzila [platforma NetBeans][nbplatform], ktora umoznuje vyvoj na baze modulov a tieto moduly dokaze updatovat v uz nainstalovanom programe. Niekde som ale potreboval umiestnit stale dostupne centrum updatov odkial si moze
program kontrolovat a stahovat nove updaty. A to ma priviedlo k tomu, ze som cely program postavil ako open source a umiestnil ho [sem](https://github.com/mox-tryer/nbevid).

  [nbplatform]: https://netbeans.org/features/platform/
  
Ta prva funkcna verzia je 0.1.4 a o tom, co sa v nej da alebo neda si mozes precitat v [blogu]({{ site.baseurl }}{% link _posts/2016-10-29-v0.1.4.md %}) o tejto verzii.
