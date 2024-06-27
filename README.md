Enescu Maria 321CA

# Tema 2 PA:

Toate problemele au structura scheletului problemelor din laboratoare,
logica de baza fiind implementata in metoda `getResult()`.

## Numarare

Pentru aceasta problema, care implica gasirea numarului de lanturi elementare
comune intre 2 grafuri, am folosit ca algoritmi sortarea topologica cu BFS si
programare dinamica, astfel:
- Stochez grafurile in liste de adiacenta;
- Construiesc un graf comun care pastreaza doar muchiile comune celor doua
grafuri initiale;
- Sortez graful comun folosind algoritmul lui Kahn pentru Sortare Topologica
[https://www.geeksforgeeks.org/topological-sorting-indegree-based-solution/ ];
- Folosind rezultatele sortarii topologice, programul calculeaza numarul de cai
folosind un vector dp in care se aduna rezultatele din nodurile vecine,
unde dp[i] = numar cai de la nodul de start la nodul curent i;
- Rezultatul final este in dp[N];

Complexitate: O(N + M), unde N = numarul de noduri,
                             M = numarul de muchii

## Trenuri

Pentru aceasta problema, care consta in gasirea numarului maxim de orase
diferite care se pot vizita de la orasul de start x la orasul 
destinatie y, am folosit un algoritm de sortare topologica cu BFS si programare
dinamica pentru a gasii cel mai lunga ruta, astfel:
- Stochez graful intr-un HashMap de forma Map<String, HashMap<String, Integer>> in
care cheia principala este orasul de plecare(fromCity), iar valoarea este un alt
HashMap care stocheaza orasul de sosire(toCity) si frecventa rutei;
- Sortez graful comun folosind algoritmul lui Kahn pentru Sortare Topologica la
care modific indegree cu un Map<String, Integer> pentru a contoriza fiecarui 
oras(nod) dat ca cheie numarul de muchii care intra in el ca valoarea mapului
  [https://www.geeksforgeeks.org/topological-sorting-indegree-based-solution/ ];
- In continuare, mai atipic, folosesc pentru programarea dinamica un
HashMap<String, Integer> dp pentru stocarea progresiva a numarului mai mare de
orase vizitate de la orasul de start la orsul curent;
- Returnez dp.get(end) care reprezinta numarul maxim de orase vizitate de la
orasul de start la orasul destinatie(end);

Complexitate: O(N + M), unde N = numarul de noduri/orase,
                             M = numarul de muchii/rute


## Drumuri

Pentru aceasta problema am folosit un algoritm Dijkstra in calculul drumulio
de cost minim de la punctul x la punctul z ce trece prin punctul y, astfel:
- Stochez graful intr-un Map de forma Map<Integer, Map<Integer, Integer>>
in care cheia principala este a, iar valoarea este un alt Map care
stocheaza b si costul c de la a la b;
- Asemanator stochez intr-un reverseGraph graful inversat, in care cheia
principala este b, iar valoarea Map cu cheia a si costul c;
- Folosesc un algoritm Dijkstra pentru a calcula distantele minime de la
nodurile x si y pana la toate celelalte noduri din graf si de la nodul z
la toate celelalte noduri din graful inversat;
- Algoritmul Dijkstra este implementat cu un PriorityQueue pentru a 
asigura procesarea nodurilor in ordine crescatoare dupa costurile date
si Map<Integer, Integer> dist pentru stocarea distantele minime de la nodul
de start la nodul curent intr-un mod mai ordonat si eficient ca timp O(1)
pentru insertie. Acest algoritm este inspirat din laboratorul 8 - task 1
pentru class Node si Dijkstra si de pe GeeksforGeeks
[ https://www.geeksforgeeks.org/dijkstras-shortest-path-algorithm-using-priority_queue-stl/ ];
- In continuare calculez si returnez suma drumurilor minime de la x la z prin y,
folosind rezultatele obtinute prin algoritmul Dijkstra stocate in 
distFromX, distFromY si distToZ;

Complexitate: O(M * log N), unde N = numarul de noduri,
                                 M = numarul de muchii