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


## Prve spustenie

Po spusteni sa zobrazi okno aplikacie:
![Okno aplikacie]({{ site.baseurl}}public/screenshots/nbevid01.png "Okno aplikacie")

Standardne je aplikacia nastavena tak, ze nove updaty kontroluje maximalne raz za tyzden. Odporucam nastavit (aspon teraz pocas vyvoja) tak, aby sa updaty kontrolovali pri kazdom spusteni. Na to potrebujes ist do menu `Tools->Plugins`:

![Menu Tools]({{ site.baseurl}}public/screenshots/nbevid02.png "Menu Tools")

V zalozke `Settings` je nastavenie `Check Interval`, ktore odporucam zmenit z `Every Week` na `Every Startup`:

![Plugins Settings]({{ site.baseurl}}public/screenshots/nbevid03.png "Plugins Settings")

Kontrola updatov sa da aj vynutit a to bud rychlo v menu `Help->Check for Updates` alebo v menu `Tools->Plugins` zalozka `Updates`, tlacitko `Check for Updates`.


## Updatovanie

Pokial program sam detekuje nove updaty, objavi sa v pravom dolnom rohu ikonka zemegule so sipkou:
![Notifikacia o updatoch]({{ site.baseurl}}public/screenshots/nbevid16.png "Notifikacia o updatoch")

Ak na nu kliknes, objavi sa bublina s textom o pocte updatov:
![Notifikacia o updatoch]({{ site.baseurl}}public/screenshots/nbevid17.png "Notifikacia o updatoch")

Ked kliknes na linku v bubline, objavi sa zoznam updatov:
![Okno so zoznamom updatov]({{ site.baseurl}}public/screenshots/nbevid18.png "Okno so zoznamom updatov")

Po stlaceni `Next` sa zobrazi okno s licenciami, kde musis zaskrtnut, ze akceptujes licencie:
![Okno s licenciami]({{ site.baseurl}}public/screenshots/nbevid19.png "Okno s licenciami")
Cely program je v licencii [GPL verzia 2](https://sk.wikipedia.org/wiki/GNU_General_Public_License), co je open source licencia.


Na zaver sa zobrazi okno s potvrdenim updatov. Updaty su rozdelene na podpisane (elektronicky) a nepodpisane. Ja zatial updaty nepodpisujem:
![Okno s potrvdenim updatov]({{ site.baseurl}}public/screenshots/nbevid20.png "Okno s potrvdenim updatov")

Po nainstalovani sa v pravom dolnom rohu zobrazi zatocena sipka, co znamena, ze program sa potrebuje restartovat, aby sa updaty prejavili. Ked na nu kliknes, objavi sa bublina:
![Notifikcia o restarte]({{ site.baseurl}}public/screenshots/nbevid21.png "Notifikcia o restarte")

V bubline je linka, na ktoru ked kliknes, program sa restartuje.


## Pouzivanie

Popis pouzivania je v [prirucke](guide). K jednotlivym [verziam](downloads) su linky do blogu, kde popisem, co je v danej verzii nove a co sa zmenilo. Pre prvu pouzitelnu verziu ([0.1.4]({{ site.baseurl }}{% link _posts/2016-10-29-v0.1.4.md %}))
popisem aj aky pristup som zvolil pri vyvoi a preco toho zatial vie program tak malo.
