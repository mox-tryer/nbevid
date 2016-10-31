---
layout: page
title: Install
---

Na stranke [Downloads](downloads) si vyber verziu. Najnovsia (najvyssia) je najlepsia :-)

Ked si kliknes na verziu, otvori sa to stranka s danou verziou. Pre kazdu verziu su k dispozicii 4 subory na stiahnutie. 2 subory su zdrojovy kod a 2 su pre instalaciu. Pre instalaciu si mozes vybrat bud stiahnut zip, ktory len rozbalis,
alebo stiahnut instalacny program pre Windows.

Instalacia zipu je jednoduchsia, pretoze staci len rozbalit. Ale musis si byt ista, ze mas na pocitaci nainstalovanu Java 8. Instalacny program by mal skontrolovat, ci je Java nainstalovana a ak nie, tak ju stiahne a nainstaluje.
Instalacny program by aj mal pridat spustaciu ikonku na plochu.

V pripade, ze to len rozbalis tak v podadresari `bin` su subory `nbevid.exe` a `nbevid64.exe`. Kedze mate 64bitove Windows a asi aj 64bitovu Javu, tak spustat by sa to malo s `nbevid64.exe`.


### Prve spustenie

Po spusteni sa zobrazi okno aplikacie:
![Okno aplikacie]({{ site.baseurl}}public/screenshots/nbevid01.png "Okno aplikacie")

Standardne je aplikacia nastavena tak, ze nove updaty kontroluje maximalne raz za tyzden. Odporucam nastavit (aspon teraz pocas vyvoja) tak, aby sa updaty kontrolovali pri kazdom spusteni. Na to potrebujes ist do menu `Tools->Plugins`:

![Menu Tools]({{ site.baseurl}}public/screenshots/nbevid02.png "Menu Tools")

V zalozke `Settings` je nastavenie `Check Interval`, ktore odporucam zmenit z `Every Week` na `Every Startup`:

![Plugins Settings]({{ site.baseurl}}public/screenshots/nbevid03.png "Plugins Settings")

Kontrola updatv sa da aj vynutit a to bud rychlo v menu `Help->Check for Updates` alebo v menu `Tools->Plugins` zalozka `Updates`, tlacitko `Check for Updates`.

### dokoncit:
* popis instalacie
* ako sledovat, nastavit a robit updaty
* linka/linky na popis jednotlivych verzii, kde je popisane, co sa da robit a co sa robit este neda

![Notifikacia o updatoch]({{ site.baseurl}}public/screenshots/nbevid16.png "Notifikacia o updatoch")

![Notifikacia o updatoch]({{ site.baseurl}}public/screenshots/nbevid17.png "Notifikacia o updatoch")

![Okno so zoznamom updatov]({{ site.baseurl}}public/screenshots/nbevid18.png "Okno so zoznamom updatov")

![Okno s licenciami]({{ site.baseurl}}public/screenshots/nbevid19.png "Okno s licenciami")

![Okno s potrvdenim updatov]({{ site.baseurl}}public/screenshots/nbevid20.png "Okno s potrvdenim updatov")

![Notifikcia o restarte]({{ site.baseurl}}public/screenshots/nbevid21.png "Notifikcia o restarte")
